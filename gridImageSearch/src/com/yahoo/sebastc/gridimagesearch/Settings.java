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
	public String imageDomain = null;

	public enum Size {
		ALL(null), SMALL("icon"), MEDIUM("small|medium|large|xlarge"), LARGE(
				"xxlarge"), XLARGE("huge");

		private String value;

		private Size(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	public enum ImageColor {
		ALL(null), BLACK("black"), BLUE("blue"), BROWN("brown"), GRAY("gray"), GREEN(
				"green");

		private String value;

		private ImageColor(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

	public enum ImageType {
		ALL(null), FACE("face"), PHOTO("photo"), CLIPART("clipart"), LINEART(
				"lineart");

		private String value;

		private ImageType(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
}
