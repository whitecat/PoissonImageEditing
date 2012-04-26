package imageImporter;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class Gradient {
	private Color[][] pic;
	private Color[][] gradientX;
	private Color[][] gradientY;
	private Color[][] divG;
	private ColorBean[][] colorDivG;
	private ColorBean[][] colorGradientX;
	private ColorBean[][] colorGradientY;
	private ColorBean[][] mergedGrad;
	int width = 0;
	int height = 0;
	int[] dataX = null;

	public Gradient(PPM picture) {
		pic = picture.getPicture();
		height = pic.length;
		width = pic[0].length;
		gradientX = new Color[height][width];
		gradientY = new Color[height][width];
		divG = new Color[height][width];
		colorDivG = new ColorBean[height][width];
		colorGradientX = new ColorBean[height][width];
		colorGradientY = new ColorBean[height][width];
		calculateGradient();
		calculateColorGradient();
	}

	private void calculateGradient() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				gradientX[i][j] = new Color(getChangeX(pic, j, i, 0));
				gradientY[i][j] = new Color(getChangeY(pic, j, i, 0));
			}
		}
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

	public Image getImageColorDivG() {
		int[] data = getArray(divG, width, height);

		MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, data, 0, width);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);

	}

	public int[] getArray(Color[][] gradient, int width, int height) {
		int[] data = null;
		data = new int[height * width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				data[(width) * i + j] = (gradient[i][j].getRGB());
			}
		}
		return data;
	}

	private void calculateDivG() {
		int i, j;
		int gradSumX, gradSumY;

		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				gradSumX = getChangeX(gradientX, j, i, 0);
				gradSumY = getChangeY(gradientY, j, i, 0);

				divG[i][j] = new Color((gradSumX + gradSumY) / 2);

				// ImageSetPixelf(divG, i, j, (gradSumX + gradSumY)/255.0,
				// width, height) ;
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

	private int getChangeX(Color[][] gradient, int j, int i, int type) {
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
				return gradient[i][j + 1].getRGB() - gradient[i][j].getRGB();
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
				return gradient[i][j].getRGB() - gradient[i][j - 1].getRGB();
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
				return gradient[i][j + 1].getRGB() - gradient[i][j - 1].getRGB();
			}
		}
	}

	private int getChangeY(Color[][] gradient, int j, int i, int type) {
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
				return gradient[i + 1][j].getRGB() - gradient[i][j].getRGB();
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
				return gradient[i][j].getRGB() - gradient[i - 1][j].getRGB();
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
				return gradient[i + 1][j].getRGB() - gradient[i - 1][j].getRGB();
			}

		}
	}

	void imageNormalize(double[][] u, double[][] out, int width, int height) {
		int i, j;

		double max = -10000000.0, min = 1000000000.0;

		for (i = 1; i <= height; i++) {
			for (j = 1; j <= width; j++) {
				if (u[i][j] > max)
					max = u[i][j];
				if (u[i][j] < min)
					min = u[i][j];
			}
		}
		for (i = 1; i <= height; i++) {
			for (j = 1; j <= width; j++) {
				if ((u[i][j] - min) / (max - min) * 255 < 0) {
					System.out.println((u[i][j] - min) / (max - min) * 255);
				}
				out[i][j] = (u[i][j] - min) / (max - min) * 255;
			}
		}
	}

	public Color[][] changeColorBean(ColorBean[][] gradient) {
		double r[][] = null;
		double b[][] = null;
		double g[][] = null;
		double outr[][] = null;
		double outb[][] = null;
		double outg[][] = null;

		int height = gradient.length;
		int width = gradient[0].length;

		b = new double[height + 1][width + 1];
		r = new double[height + 1][width + 1];
		g = new double[height + 1][width + 1];
		outb = new double[height + 1][width + 1];
		outr = new double[height + 1][width + 1];
		outg = new double[height + 1][width + 1];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				r[i + 1][j + 1] = gradient[i][j].getRed();
				b[i + 1][j + 1] = gradient[i][j].getBlue();
				g[i + 1][j + 1] = gradient[i][j].getGreen();
			}
		}

		imageNormalize(r, outr, width, height);
		imageNormalize(b, outb, width, height);
		imageNormalize(g, outg, width, height);

		Color[][] result = new Color[height][width];
		for (int i = 1; i <= height; i++) {
			for (int j = 1; j <= width; j++) {
				result[i - 1][j - 1] = new Color((int) outr[i][j], (int) outg[i][j],
						(int) outb[i][j]);
			}
		}

		return result;

	}

	public Color[][] getGradientX() {
		return gradientX;

	}

	public Color[][] getGradientY() {
		return gradientY;

	}

	public ColorBean[][] getColorGradientX() {

		return colorGradientX;

	}

	public ColorBean[][] getColorGradientY() {
		return colorGradientY;

	}

	public Color[][] getDivG() {
		calculateDivG();
		return divG;
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

	public Image getImageGradient(Color[][] gradient) {

		int[] data = getArray(gradient, width, height);

		MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, data, 0, width);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);
	}

	public void mergeGradient(Gradient converted2) {
		int i, j;
		int gradSumXRed, gradSumYRed;
		int gradSumXBlue, gradSumYBlue;
		int gradSumXGreen, gradSumYGreen;
		ColorBean[][] colorGradientX2 = converted2.getColorGradientX();
		ColorBean[][] colorGradientY2 = converted2.getColorGradientY();
		mergedGrad = new ColorBean[height][width];
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
