package imageImporter;


public class Gradient {
	private ColorBean[][] pic;
	private ColorBean[][] colorDivG;
	private ColorBean[][] colorGradientX;
	private ColorBean[][] colorGradientY;
	int width = 0;
	int height = 0;
	int[] dataX = null;

	public Gradient(PPM picture) {
		pic = picture.getPicture();
		height = pic.length;
		width = pic[0].length;
		colorDivG = new ColorBean[height][width];
		colorGradientX = new ColorBean[height][width];
		colorGradientY = new ColorBean[height][width];
		calculateColorGradient();
	}

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

	private void calculageDivGPerColor() {
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





	public ColorBean[][] getColorGradientX() {

		return colorGradientX;

	}

	public ColorBean[][] getColorGradientY() {
		return colorGradientY;

	}

	public ColorBean[][] getColorDivG() {
		calculageDivGPerColor();
		return colorDivG;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void mergeGradient(Gradient converted2) {
		int i, j;
		int gradSumXRed, gradSumYRed;
		int gradSumXBlue, gradSumYBlue;
		int gradSumXGreen, gradSumYGreen;
		ColorBean[][] colorGradientX2 = converted2.getColorGradientX();
		ColorBean[][] colorGradientY2 = converted2.getColorGradientY();
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				gradSumXRed = (colorGradientX[i][j].getRed() + colorGradientX2[i][j].getRed());
				gradSumXGreen = (colorGradientX[i][j].getGreen() + colorGradientX2[i][j].getGreen());
				gradSumXBlue = (colorGradientX[i][j].getBlue() + colorGradientX2[i][j].getBlue());
				colorGradientX[i][j] = new ColorBean(gradSumXRed, gradSumXGreen, gradSumXBlue);
				
				gradSumYRed = (colorGradientY[i][j].getRed() + colorGradientY2[i][j].getRed());
				gradSumYGreen = (colorGradientY[i][j].getGreen() + colorGradientY2[i][j].getGreen());
				gradSumYBlue = (colorGradientY[i][j].getBlue() + colorGradientY2[i][j].getBlue());
				colorGradientY[i][j] = new ColorBean(gradSumYRed, gradSumYBlue, gradSumYGreen);
			}
		}
	}
}
