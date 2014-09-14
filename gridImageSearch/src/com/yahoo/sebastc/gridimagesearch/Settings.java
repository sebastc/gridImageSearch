package com.yahoo.sebastc.gridimagesearch;

import java.io.Serializable;

public class Settings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7654206045766945726L;
	public Size imageSize = Size.ALL;
	public ImageColor imageColor = ImageColor.ALL;
	public ImageType imageType = ImageType.ALL;
	public String imageSite = null;

	public enum Size {
		ALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE
	}

	public enum ImageColor {
		ALL, BLACK, BLUE, BROWN, GRAY, GREEN
	}

	public enum ImageType {
		ALL, FACES, PHOTO, CLIP_ART, LINE_ART
	}
}
