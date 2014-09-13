package com.yahoo.sebastc.gridimagesearch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ImageResult {
	private String imageUrl;
	private String thumbnailUrl;
	
	public ImageResult(JSONObject json) {
		try {
			this.imageUrl = json.getString("url");
			this.thumbnailUrl = json.getString("tbUrl");
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	@Override
	public String toString() {
		return imageUrl;
	}

	public static Collection<? extends ImageResult> fromJSONArray(
			JSONArray results) throws JSONException {
		List<ImageResult> res = new ArrayList<ImageResult>();
		for (int i = 0; i < results.length(); i++) {
			res.add(new ImageResult(results.getJSONObject(i)));
		}
		return res;
	}

}
