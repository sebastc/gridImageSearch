package com.yahoo.sebastc.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsActivity extends Activity {
	private Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		settings = (Settings) getIntent().getSerializableExtra("settings");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_ok, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	public void onOkClick(MenuItem v) {
		Intent data = new Intent();
		data.putExtra("settings", settings);
		setResult(RESULT_OK, data);
		finish();
	}
}
