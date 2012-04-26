package imageImporter;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class PoissonSolver {

	private int height;
	private int width;
	private int[] solution;
	Color[][] result;

	public PoissonSolver() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Compute the integral of pixels
	 * 
	 * @param grad
	 *            Gradients to
	 * @return
	 */
	public void integrate(Gradient grad, ColorBean[][] divG) {
		double r[][] = null;
		double b[][] = null;
		double g[][] = null;
		double outr[][] = null;
		double outb[][] = null;
		double outg[][] = null;
		int nn = 513;
		width = 513;
		height = 513;

		b = new double[height + 1][width + 1];
		r = new double[height + 1][width + 1];
		g = new double[height + 1][width + 1];
		outb = new double[height + 1][width + 1];
		outr = new double[height + 1][width + 1];
		outg = new double[height + 1][width + 1];
		
		for (int i = 1; i <= height; i++) {
			for (int j = 1; j <= width; j++) {
				r[i][j] = divG[i - 1][j +200].getRed();
				b[i][j] = divG[i - 1][j +200].getBlue();
				g[i][j] = divG[i - 1][j +200].getGreen();
			}
		}

		int Vcycles = 4;
		// solve linear equation
		Integration inte = new Integration();

		try {
			inte.mglin(r, nn, Vcycles);
			inte.mglin(b, nn, Vcycles);
			inte.mglin(g, nn, Vcycles);
		} catch (Exception e) {
			e.printStackTrace();
		}
		grad.imageNormalize(r, outr, nn, nn);
		grad.imageNormalize(b, outb, nn, nn);
		grad.imageNormalize(g, outg, nn, nn);

		Color[][] result = new Color[nn][nn];
		for (int i = 1; i <= nn; i++) {
			for (int j = 1; j <= nn; j++) {
				result[i - 1][j - 1] = new Color((int) outr[i][j], (int) outg[i][j],
						(int) outb[i][j]);
			}
		}

		solution = grad.getArray(result, nn, nn);
	}

	public Image getImage() {

		MemoryImageSource memoryImageSource = new MemoryImageSource(width, height, solution, 0,
				width);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);

	}

}
