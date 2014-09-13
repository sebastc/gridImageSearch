package com.yahoo.sebastc.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdaptor extends ArrayAdapter<ImageResult> {

	public ImageResultArrayAdaptor(Context context, List<ImageResult> objects) {
//		super(context, android.R.layout.simple_list_item_1, objects);
		super(context, R.layout.item_image_result, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext())
					.inflate(R.layout.item_image_result, parent, false);
		}
		
		ImageResult imageResult = getItem(position);
		final String imageUrl = imageResult.getImageUrl();
		String thumbnailUrl = imageResult.getThumbnailUrl();
		
		SmartImageView ivImage = (SmartImageView) convertView.findViewById(R.id.item_image_result);
		ivImage.setImageResource(android.R.color.transparent);
		ivImage.setImageUrl(thumbnailUrl);
		ivImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), ImageActivity.class);
				intent.putExtra("url", imageUrl);
				getContext().startActivity(intent);
				
			}
		});
		return ivImage;
	}

}
