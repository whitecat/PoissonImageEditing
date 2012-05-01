package imageImporter;

public class Gradient {
	private ColorBean[][] pic;
	private ColorBean[][] colorDivG;
	private ColorBean[][] colorGradientX;
	private ColorBean[][] colorGradientY;
	int width = 0;
	int height = 0;
	int[] dataX = null;

	/**
	 * Gradient Constructor It calculates the gradient of an image
	 * 
	 * @param picture
	 *            Picture to be decomposed.
	 */
	public Gradient(PPM picture) {
		pic = picture.getPicture();
		height = pic.length;
		width = pic[0].length;
		colorDivG = new ColorBean[height][width];
		colorGradientX = new ColorBean[height][width];
		colorGradientY = new ColorBean[height][width];
		calculateColorGradient();
	}

	/**
	 * Calculates both X and Y gradient of an image
	 */
	private void calculateColorGradient() {
		int gradSumXRed, gradSumYRed;
		int gradSumXBlue, gradSumYBlue;
		int gradSumXGreen, gradSumYGreen;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				gradSumXRed = (getChangeX(pic, j, i, 'r'));
				gradSumXBlue = (getChangeX(pic, j, i, 'b'));
				gradSumXGreen = (getChangeX(pic, j, i, 'g'));

				gradSumYRed = (getChangeY(pic, j, i, 'r'));
				gradSumYBlue = (getChangeY(pic, j, i, 'b'));
				gradSumYGreen = (getChangeY(pic, j, i, 'g'));

				colorGradientX[i][j] = new ColorBean(gradSumXRed, gradSumXGreen, gradSumXBlue);
				colorGradientY[i][j] = new ColorBean(gradSumYRed, gradSumYGreen, gradSumYBlue);
			}
		}
	}

	/**
	 * Calculates the divergence of the image
	 */
	private void calculageDivG() {
		int i, j;
		int gradSumXRed, gradSumYRed;
		int gradSumXBlue, gradSumYBlue;
		int gradSumXGreen, gradSumYGreen;

		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				gradSumXRed = (getChangeX(colorGradientX, j, i, 'r'));
				gradSumXGreen = (getChangeX(colorGradientX, j, i, 'g'));
				gradSumXBlue = (getChangeX(colorGradientX, j, i, 'b'));
				gradSumYRed = (getChangeY(colorGradientY, j, i, 'r'));
				gradSumYGreen = (getChangeY(colorGradientY, j, i, 'g'));
				gradSumYBlue = (getChangeY(colorGradientY, j, i, 'b'));

				colorDivG[i][j] = new ColorBean((int) ((gradSumXRed + gradSumYRed)),
						(int) ((gradSumXGreen + gradSumYGreen)),
						(int) ((gradSumXBlue + gradSumYBlue)));
			}
		}
	}

	/**
	 * Calculates change between two pixels in the X direction
	 * 
	 * @param gradient
	 *            gradient to be calculated
	 * @param j
	 *            y coordinate
	 * @param i
	 *            x coordinate
	 * @param type
	 *            color to be calculated
	 * @return the color difference between pixels
	 */
	private int getChangeX(ColorBean[][] gradient, int j, int i, int type) {
		// Reuse the pixel at the start, and end of each array.
		if (j == 0) {
			switch (type) {
			case 'r':
				return gradient[i][j + 1].getRed() - gradient[i][j].getRed();
			case 'b':
				return gradient[i][j + 1].getBlue() - gradient[i][j].getBlue();
			case 'g':
				return gradient[i][j + 1].getGreen() - gradient[i][j].getGreen();
			default:
				return 0;
			}
		} else if (j == width - 1) {
			switch (type) {
			case 'r':
				return gradient[i][j].getRed() - gradient[i][j - 1].getRed();
			case 'b':
				return gradient[i][j].getBlue() - gradient[i][j - 1].getBlue();
			case 'g':
				return gradient[i][j].getGreen() - gradient[i][j - 1].getGreen();
			default:
				return 0;
			}
		} else {
			switch (type) {
			case 'r':
				return gradient[i][j + 1].getRed() - gradient[i][j - 1].getRed();
			case 'b':
				return gradient[i][j + 1].getBlue() - gradient[i][j - 1].getBlue();
			case 'g':
				return gradient[i][j + 1].getGreen() - gradient[i][j - 1].getGreen();
			default:
				return 0;
			}
		}
	}

	/**
	 * Calculates change between two pixels in the Y direction
	 * 
	 * @param gradient
	 *            gradient to be calculated
	 * @param j
	 *            y coordinate
	 * @param i
	 *            x coordinate
	 * @param type
	 *            color to be calculated
	 * @return the color difference between pixels
	 */
	private int getChangeY(ColorBean[][] gradient, int j, int i, int type) {
		// Reuse the pixel at the start, and end of each array.
		if (i == 0) {
			switch (type) {
			case 'r':
				return gradient[i + 1][j].getRed() - gradient[i][j].getRed();
			case 'b':
				return gradient[i + 1][j].getBlue() - gradient[i][j].getBlue();
			case 'g':
				return gradient[i + 1][j].getGreen() - gradient[i][j].getGreen();
			default:
				return 0;
			}
		} else if (i == height - 1) {
			switch (type) {
			case 'r':
				return gradient[i][j].getRed() - gradient[i - 1][j].getRed();
			case 'b':
				return gradient[i][j].getBlue() - gradient[i - 1][j].getBlue();
			case 'g':
				return gradient[i][j].getGreen() - gradient[i - 1][j].getGreen();
			default:
				return 0;
			}

		} else {
			switch (type) {
			case 'r':
				return gradient[i + 1][j].getRed() - gradient[i - 1][j].getRed();
			case 'b':
				return gradient[i + 1][j].getBlue() - gradient[i - 1][j].getBlue();
			case 'g':
				return gradient[i + 1][j].getGreen() - gradient[i - 1][j].getGreen();
			default:
				return 0;
			}

		}
	}

	/**
	 * Merges two gradients together
	 * 
	 * @param gradientToMerge
	 *            The gradient you wish to be merged.
	 */
	public void mergeGradient(Gradient gradientToMerge) {
		int i, j;
		int gradSumXRed, gradSumYRed;
		int gradSumXBlue, gradSumYBlue;
		int gradSumXGreen, gradSumYGreen;
		ColorBean[][] colorGradientX2 = gradientToMerge.getGradientX();
		ColorBean[][] colorGradientY2 = gradientToMerge.getGradientY();
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				gradSumXRed = (colorGradientX[i][j].getRed() + colorGradientX2[i][j].getRed());
				gradSumXGreen = (colorGradientX[i][j].getGreen() + colorGradientX2[i][j].getGreen());
				gradSumXBlue = (colorGradientX[i][j].getBlue() + colorGradientX2[i][j].getBlue());
				colorGradientX[i][j] = new ColorBean(gradSumXRed, gradSumXGreen, gradSumXBlue);

				gradSumYRed = (colorGradientY[i][j].getRed() + colorGradientY2[i][j].getRed());
				gradSumYGreen = (colorGradientY[i][j].getGreen() + colorGradientY2[i][j].getGreen());
				gradSumYBlue = (colorGradientY[i][j].getBlue() + colorGradientY2[i][j].getBlue());
				colorGradientY[i][j] = new ColorBean(gradSumYRed, gradSumYGreen, gradSumYBlue);
			}
		}
	}

	public ColorBean[][] getGradientX() {

		return colorGradientX;

	}

	public ColorBean[][] getGradientY() {
		return colorGradientY;

	}

	public ColorBean[][] getDivG() {
		calculageDivG();
		return colorDivG;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
