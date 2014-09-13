package com.yahoo.sebastc.gridimagesearch;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {

	private EditText etQuery;
	private ImageButton btnSearch;
	private GridView gvResults;

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

	private void setupView() {
		btnSearch = (ImageButton) findViewById(R.id.btnSearch);
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
						"Failure: " + statusCode+" / "+throwable, Toast.LENGTH_SHORT).show();
			}
		});

	}
}
