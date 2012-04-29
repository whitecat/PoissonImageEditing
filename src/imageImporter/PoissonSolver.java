package imageImporter;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PoissonSolver {

	private int height;
	private int width;
	private int[] solution;
	ColorBean[][] result;

	public PoissonSolver() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Compute the integral of pixels
	 * 
	 * @param grad
	 *            Gradients to convert
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public void integrate(Gradient grad, ColorBean[][] divG) throws UnsupportedEncodingException,
			IOException {
		// String filename1 = "./mask.ppm";
		// ColorBean [][]maskOriginal = new PPM(filename1).getPicture();

		double r[][] = null;
		double b[][] = null;
		double g[][] = null;
		double mask[][] = null;
		double outr[][] = null;
		double outb[][] = null;
		double outg[][] = null;
		height = divG.length;
		width = divG[0].length;
		Jacobi matrix;

		b = new double[height][width];
		r = new double[height][width];
		g = new double[height][width];
		outb = new double[height][width];
		outr = new double[height][width];
		outg = new double[height][width];
		mask = new double[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// mask[i][j] = maskOriginal[i][j].getRed();
				r[i][j] = divG[i][j].getRed();
				b[i][j] = divG[i][j].getBlue();
				g[i][j] = divG[i][j].getGreen();
			}
		}

		int Vcycles = 50000;

		matrix = new Jacobi(r, Vcycles);
		r = matrix.getResult();
		matrix = new Jacobi(g, Vcycles);
		g = matrix.getResult();
		matrix = new Jacobi(b, Vcycles);
		b = matrix.getResult();

		grad.imageNormalize(r, outr, width, height);
		grad.imageNormalize(b, outb, width, height);
		grad.imageNormalize(g, outg, width, height);

		result = new ColorBean[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				result[i][j] = new ColorBean((int) outr[i][j], (int) outg[i][j], (int) outb[i][j]);
			}
		}

		solution = grad.getArray(grad.changeColorBean(result), width, height);
	}

	public Image getImage() {

		MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, solution, 0,
				width);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);

	}

	public ColorBean[][] getResult() {
		return result;
	}
}