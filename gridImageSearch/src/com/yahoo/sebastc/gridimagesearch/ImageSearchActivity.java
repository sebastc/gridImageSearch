package com.yahoo.sebastc.gridimagesearch;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {

	private EditText etQuery;
	private GridView gvResults;
	private Settings settings;

	private List<ImageResult> images;
	private ImageResultArrayAdaptor aImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);

		images = new ArrayList<ImageResult>();
		aImages = new ImageResultArrayAdaptor(this, images);

		setupView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	private void setupView() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setAdapter(aImages);
	}

	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "searching: " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		String requestUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q="
				+ URLEncoder.encode(query);
		client.get(requestUrl, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					JSONArray results = response.getJSONObject("responseData")
							.getJSONArray("results");
					aImages.clear();
					aImages.addAll(ImageResult.fromJSONArray(results));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				Toast.makeText(ImageSearchActivity.this,
						"Failure: " + statusCode + " / " + throwable,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void onSettingsClick(MenuItem v) {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra("settings", settings);
		startActivityForResult(intent, 1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			if(requestCode==1) {
				if(resultCode==RESULT_OK) {
				Toast.makeText(this, "Got new settings", Toast.LENGTH_SHORT).show();
				this.settings = (Settings) data.getSerializableExtra("settings");
			} else {
				Toast.makeText(this, "Preserved existing settings", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
