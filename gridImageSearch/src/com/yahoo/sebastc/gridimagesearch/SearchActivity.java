package com.yahoo.sebastc.gridimagesearch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {

	private EditText etQuery;
	private GridView gvResults;
	private Settings settings;

	private List<ImageResult> images;
	private ImageResultArrayAdaptor aImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		images = new ArrayList<ImageResult>();
		aImages = new ImageResultArrayAdaptor(this, images);
		settings = new Settings();

		setupView();
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			etQuery.setText(query);
			onImageSearch();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search_activity, menu);
		return true;
	}

	private void setupView() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		etQuery.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				onImageSearch();
				return true;
			}
		});
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setAdapter(aImages);
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				customLoadMoreDataFromApi(totalItemsCount);
				// or customLoadMoreDataFromApi(totalItemsCount);
			}
		});
	}

	// Append more data into the adapter
	public void customLoadMoreDataFromApi(int offset) {
		// This method probably sends out a network request and appends new data
		// items to your adapter.
		// Use the offset value and add it as a parameter to your API request to
		// retrieve paginated data.
		// Deserialize API response and then construct new objects to append to
		// the adapter
		String query = etQuery.getText().toString();
		AsyncHttpClient client = new AsyncHttpClient();
		StringBuilder requestUrl = new StringBuilder(
				"https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8");
		addParam(requestUrl, "q", query);
		addParam(requestUrl, "start", Integer.toString(offset));
		addParam(requestUrl, "imgsz", settings.imageSize);
		addParam(requestUrl, "imgcolor", settings.imageColor);
		addParam(requestUrl, "imgtype", settings.imageType);
		addParam(requestUrl, "as_sitesearch", settings.imageDomain);
		Toast.makeText(this, "searching: " + query + "\nurl: " + requestUrl,
				Toast.LENGTH_SHORT).show();
		client.get(requestUrl.toString(), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					JSONArray results = response.getJSONObject("responseData")
							.getJSONArray("results");
					aImages.addAll(ImageResult.fromJSONArray(results));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Toast.makeText(SearchActivity.this,
						"Failure: " + statusCode + " / " + throwable,
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void addParam(StringBuilder url, String paramName, Enum<?> value) {
		addParam(url, paramName, value.toString());
	}

	private void addParam(StringBuilder url, String paramName, String value) {
		if (value == null || value.equals("ALL"))
			return;
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		url.append("&").append(paramName).append("=").append(value);
	}

	public void onImageSearch(View v) {
		onImageSearch();
	}

	private void onImageSearch() {
		aImages.clear();
		etQuery.clearFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
		customLoadMoreDataFromApi(0);
	}

	public void onSettingsClick(MenuItem v) {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra("settings", settings);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "Got updated settings", Toast.LENGTH_SHORT)
						.show();
				this.settings = (Settings) data
						.getSerializableExtra("settings");
				aImages.clear();
				if (etQuery.getText().toString().trim().isEmpty()) {
					etQuery.requestFocus();
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.showSoftInputFromInputMethod(etQuery.getWindowToken(),
							0); // FIXME the soft keyboard is not showing !!??
				} else {
					onImageSearch();
				}
			} else {
				Toast.makeText(this, "Preserved existing settings",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
