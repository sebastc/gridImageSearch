package com.yahoo.sebastc.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;

import com.loopj.android.image.SmartImageTask.OnCompleteListener;
import com.loopj.android.image.SmartImageView;

public class ImageActivity extends Activity {

	private SmartImageView ivImage;
	private ShareActionProvider miShareAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);
		ivImage = (SmartImageView) findViewById(R.id.iv_image);
		String url = getIntent().getExtras().getString("url");
		ivImage.setImageUrl(url, android.R.drawable.ic_dialog_alert,
				new OnCompleteListener() {
					@Override
					public void onComplete() {
						// Setup share intent now that image has loaded
						setupShareIntent();
					}
				});
	}

	public void setupShareIntent() {
		// Fetch Bitmap Uri locally
		Uri bmpUri = getLocalBitmapUri(ivImage); // see previous remote images
													// section
		// Create share intent as described above
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
		shareIntent.setType("image/*");
		// Attach share event to the menu item provider
		miShareAction.setShareIntent(shareIntent);
	}// Returns the URI path to the Bitmap displayed in specified ImageView

	public Uri getLocalBitmapUri(ImageView imageView) {
		// Extract Bitmap from ImageView drawable
		Drawable drawable = imageView.getDrawable();
		Bitmap mBitmap = ((BitmapDrawable)drawable).getBitmap();

		String path = Images.Media.insertImage(getContentResolver(), 
		    mBitmap, "Image Description", null);

		Uri uri = Uri.parse(path);
		return uri;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_image_activity, menu);
		// Locate MenuItem with ShareActionProvider
		MenuItem item = menu.findItem(R.id.share);
		// Fetch reference to the share action provider
		miShareAction = (ShareActionProvider) item.getActionProvider();

		return true;
	}
}
