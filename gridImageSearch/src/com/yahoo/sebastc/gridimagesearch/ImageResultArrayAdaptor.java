package com.yahoo.sebastc.gridimagesearch;

import java.util.Collection;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdaptor extends GenericAdapter<ImageResult> {

	public ImageResultArrayAdaptor(Activity activity, List<ImageResult> objects) {
		super(activity, objects);
	}

	@Override
	public View getDataRow(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mActivity).inflate(
					R.layout.item_image_result, parent, false);
		}

		ImageResult imageResult = getItem(position);
		final String imageUrl = imageResult.getImageUrl();
		String thumbnailUrl = imageResult.getThumbnailUrl();

		SmartImageView ivImage = (SmartImageView) convertView
				.findViewById(R.id.item_image_result);
		ivImage.setImageResource(android.R.color.transparent);
		ivImage.setImageUrl(thumbnailUrl);
		ivImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mActivity, ImageActivity.class);
				intent.putExtra("url", imageUrl);
				mActivity.startActivity(intent);

			}
		});
		return ivImage;
	}

	public void clear() {
		dataList.clear();
		notifyDataSetChanged();
	}

	public void addAll(Collection<? extends ImageResult> collection) {
		dataList.addAll(collection);
		notifyDataSetChanged();
	}

}
