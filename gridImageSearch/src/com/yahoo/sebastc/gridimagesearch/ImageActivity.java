package com.yahoo.sebastc.gridimagesearch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.loopj.android.image.SmartImageView;

public class ImageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		SmartImageView view = (SmartImageView) findViewById(R.id.iv_image);
		String url = getIntent().getExtras().getString("url");
		view.setImageUrl(url, android.R.drawable.ic_dialog_alert, 0);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.menu_search, menu);
		
		return true;
	}
}
