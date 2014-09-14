package com.yahoo.sebastc.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;

import com.yahoo.sebastc.gridimagesearch.Settings.ImageColor;
import com.yahoo.sebastc.gridimagesearch.Settings.ImageType;
import com.yahoo.sebastc.gridimagesearch.Settings.Size;

public class SettingsActivity extends Activity {
	private Settings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		settings = (Settings) getIntent().getSerializableExtra("settings");
		setupSpinner(R.id.spinnerSize, settings.imageSize,
				new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						settings.imageSize = Size.values()[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						settings.imageSize = Size.ALL;
					}
				});
		setupSpinner(R.id.spinnerColor, settings.imageColor,
				new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						settings.imageColor = ImageColor.values()[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						settings.imageColor = ImageColor.ALL;
					}
				});
		setupSpinner(R.id.spinnerType, settings.imageType,
				new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						settings.imageType = ImageType.values()[position];
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						settings.imageSize = Size.ALL;
					}
				});
		setupTextField(R.id.etDomain, settings.imageDomain, new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				settings.imageDomain = s.toString();
			}
		});

	}

	private void setupSpinner(int id, Enum<?> value,
			OnItemSelectedListener listener) {
		Spinner spinner = (Spinner) findViewById(id);
		spinner.setSelection(value.ordinal());
		spinner.setOnItemSelectedListener(listener);
	}

	private void setupTextField(int id, String value, TextWatcher listener) {
		EditText spinner = (EditText) findViewById(id);
		if (value != null && value.trim().isEmpty())
			value = null;
		spinner.setText(value);
		spinner.addTextChangedListener(listener);
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
