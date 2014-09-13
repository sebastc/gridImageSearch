package com.yahoo.sebastc.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

public class ImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		SmartImageView view = (SmartImageView) findViewById(R.id.iv_image);
		String url = getIntent().getExtras().getString("url");
		view.setImageUrl(url);
	}
}
