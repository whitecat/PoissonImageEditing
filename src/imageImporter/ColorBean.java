package imageImporter;

public class ColorBean {
	int red;
	int blue;
	int green;

	/**
	 * Color object that has positive and negative values
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public ColorBean(int red, int green, int blue) {
		this.red = red;
		this.blue = blue;
		this.green = green;

	}

	public int getRed() {
		return red;
	}

	public int getBlue() {
		return blue;
	}

	public int getGreen() {
		return green;
	}

}
