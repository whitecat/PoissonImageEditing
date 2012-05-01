package imageImporter;

import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PoissonSolver {
	private int iterations;
	private int height;
	private int width;
	Color[][] result;

	public PoissonSolver(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Compute the integral of pixels
	 * 
	 * @param grad
	 *            Gradients to convert
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public void integrate(ColorBean[][] divG) throws UnsupportedEncodingException, IOException {
		double r[][] = null;
		double b[][] = null;
		double g[][] = null;
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

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				r[i][j] = divG[i][j].getRed();
				b[i][j] = divG[i][j].getBlue();
				g[i][j] = divG[i][j].getGreen();
			}
		}
		matrix = new Jacobi(r, iterations);
		r = matrix.getResult();
		matrix = new Jacobi(g, iterations);
		g = matrix.getResult();
		matrix = new Jacobi(b, iterations);
		b = matrix.getResult();

		Util.imageNormalize(r, outr, width, height);
		Util.imageNormalize(b, outb, width, height);
		Util.imageNormalize(g, outg, width, height);

		result = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {

				result[i][j] = new Color((int) outr[i][j], (int) outg[i][j], (int) outb[i][j]);
			}
		}
	}

	public Color[][] getResult() {
		return result;
	}
}