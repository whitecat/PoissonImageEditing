package imageImporter;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class Util {

	public static void checkError(double[][] result, double[][] previousIteration, int height,
			int width) {
		double matrixSum = 0;
		double sum = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				sum = previousIteration[i][j] - result[i][j];
				matrixSum += (sum * sum);
			}
		}
		// Math.sqrt(matrixSum * matrixSum);
		System.out.println("Error between matrix: " + Math.sqrt(matrixSum));
	}

	public static Image getImage(ColorBean[][] image) {
		int height = image.length;
		int width = image[0].length;

		int[] data = getArray(changeColorBean(image), width, height);

		MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, data, 0, width);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);
	}

	public static Image getImage(Color[][] image) {
		int height = image.length;
		int width = image[0].length;

		int[] data = getArray((image), width, height);

		MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, data, 0, width);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);
	}
	
	public static int[] getArray(Color[][] gradient, int width, int height) {
		int[] data = new int[height * width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				data[(width) * i + j] = (gradient[i][j].getRGB());
			}
		}
		return data;
	}

	public static Color[][] changeColorBean(ColorBean[][] gradient) {
		double r[][] = null;
		double b[][] = null;
		double g[][] = null;
		double outr[][] = null;
		double outb[][] = null;
		double outg[][] = null;

		int height = gradient.length;
		int width = gradient[0].length;

		b = new double[height][width];
		r = new double[height][width];
		g = new double[height][width];
		outb = new double[height][width];
		outr = new double[height][width];
		outg = new double[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				r[i][j] = gradient[i][j].getRed();
				b[i][j] = gradient[i][j].getBlue();
				g[i][j] = gradient[i][j].getGreen();
			}
		}

		imageNormalize(r, outr, width, height);
		imageNormalize(b, outb, width, height);
		imageNormalize(g, outg, width, height);

		Color[][] result = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				result[i][j] = new Color((int) outr[i][j], (int) outg[i][j], (int) outb[i][j]);
			}
		}

		return result;
	}

	static void imageNormalize(double[][] u, double[][] out, int width, int height) {
		int i, j;

		double max = -10000000.0, min = 1000000000.0;

		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				if (u[i][j] > max)
					max = u[i][j];
				if (u[i][j] < min)
					min = u[i][j];
			}
		}
		for (i = 0; i < height; i++) {
			for (j = 0; j < width; j++) {
				if ((u[i][j] - min) / (max - min) * 255 < 0) {
					System.out.println((u[i][j] - min) / (max - min) * 255);
				}
				out[i][j] = 255 - ((u[i][j] - min) / (max - min) * 255);
			}
		}
	}

}
