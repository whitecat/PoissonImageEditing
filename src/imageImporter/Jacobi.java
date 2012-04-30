package imageImporter;

import Jama.Matrix;

public class Jacobi {

	private double[][] result;
	private double[][] previousIteration;

	private int width = 0;
	private int height = 0;

	public Jacobi(double[][] matrix, int iterations) {
		height = matrix.length;
		width = matrix[0].length;

		result = new double[height][width];
		previousIteration = new double[height][width];
		solve(matrix, iterations);

	}

	public Jacobi(double[][] matrix, double[][] mask, int iterations) {
		height = matrix.length;
		width = matrix[0].length;

		previousIteration = new double[height][width];
		solve(matrix, mask, iterations);

	}

	private void solve(double[][] matrix, int iterations) {
		for (int k = 0; k <= iterations; k++) {
			result = new double[height][width];
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {

					if (i == 0) {
//						if (j == 0) {
//							result[i][j] = (previousIteration[i][j] + previousIteration[i + 1][j]
//									+ previousIteration[i][j] + previousIteration[i][j + 1] + matrix[i][j]) / 4;
//						} else if (j > width - 2) {
//							result[i][j] = (previousIteration[i][j] + previousIteration[i + 1][j]
//									+ previousIteration[i][j - 1] + previousIteration[i][j] + matrix[i][j]) / 4;
//						} else
//							result[i][j] = (previousIteration[i][j] + previousIteration[i + 1][j]
//									+ previousIteration[i][j - 1] + previousIteration[i][j + 1] + matrix[i][j]) / 4;

					} else if (i > height - 2) {
//						if (j == 0) {
//							result[i][j] = (previousIteration[i - 1][j] + previousIteration[i][j]
//									+ previousIteration[i][j] + previousIteration[i][j + 1] + matrix[i][j]) / 4;
//						} else if (j > width - 2) {
//							result[i][j] = (previousIteration[i - 1][j] + previousIteration[i][j]
//									+ previousIteration[i][j - 1] + previousIteration[i][j] + matrix[i][j]) / 4;
//						} else {
//							result[i][j] = (previousIteration[i - 1][j] + previousIteration[i][j]
//									+ previousIteration[i][j - 1] + previousIteration[i][j + 1] + matrix[i][j]) / 4;
//						}

					} else if (j == 0) {
//						result[i][j] = (previousIteration[i - 1][j] + previousIteration[i + 1][j]
//								+ previousIteration[i][j] + previousIteration[i][j + 1] + matrix[i][j]) / 4;
					} else if (j > width - 2) {
//						result[i][j] = (previousIteration[i - 1][j] + previousIteration[i + 1][j]
//								+ previousIteration[i][j - 1] + previousIteration[i][j] + matrix[i][j]) / 4;
					} else {

						result[i][j] = (previousIteration[i - 1][j] + previousIteration[i + 1][j]
								+ previousIteration[i][j - 1] + previousIteration[i][j + 1] + matrix[i][j]) / 4;
					}
				}
			}
			Util.checkError(result, previousIteration, height, width);
			previousIteration = result;

		}
	}

	private void solve(double[][] matrix, double[][] mask, int iterations) {
		for (int k = 0; k <= iterations; k++) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (i == 0) {
						if (j == 0) {
							// result[i][j] = (previousIteration[i][j] +
							// previousIteration[i + 1][j]
							// + previousIteration[i][j] +
							// previousIteration[i][j + 1] + matrix[i][j]) / 4;
						} else if (j > width - 2) {
							// result[i][j] = (previousIteration[i][j] +
							// previousIteration[i + 1][j]
							// + previousIteration[i][j - 1] +
							// previousIteration[i][j] + matrix[i][j]) / 4;
						}
						// else
						// result[i][j] = (previousIteration[i][j] +
						// previousIteration[i + 1][j]
						// + previousIteration[i][j - 1] +
						// previousIteration[i][j + 1] + matrix[i][j]) / 4;

					} else if (i > height - 2) {
						if (j == 0) {
							// result[i][j] = (previousIteration[i - 1][j] +
							// previousIteration[i][j]
							// + previousIteration[i][j] +
							// previousIteration[i][j + 1] + matrix[i][j]) / 4;
						} else if (j > width - 2) {
							// result[i][j] = (previousIteration[i - 1][j] +
							// previousIteration[i][j]
							// + previousIteration[i][j - 1] +
							// previousIteration[i][j ] + matrix[i][j]) / 4;
						}
						// else {
						// result[i][j] = (previousIteration[i-1][j] +
						// previousIteration[i ][j]
						// + previousIteration[i][j - 1] +
						// previousIteration[i][j + 1] + matrix[i][j]) / 4;
						// }

					} else if (j == 0) {
						// result[i][j] = (previousIteration[i - 1][j] +
						// previousIteration[i + 1][j]
						// + previousIteration[i][j] + previousIteration[i][j +
						// 1] + matrix[i][j]) / 4;
					} else if (j > width - 2) {
						// result[i][j] = (previousIteration[i - 1][j] +
						// previousIteration[i + 1][j]
						// + previousIteration[i][j - 1] +
						// previousIteration[i][j] + matrix[i][j]) / 4;
					} else {
						if (mask[i][j] > 100) {
							result[i][j] = matrix[i][j];
						}
						if (mask[i + 1][j] > 100) {
							result[i][j] += previousIteration[i + 1][j];
						}
						if (mask[i - 1][j] > 100) {
							result[i][j] += previousIteration[i - 1][j];

						}
						if (mask[i][j + 1] > 100) {
							result[i][j] += previousIteration[i][j + 1];
						}
						if (mask[i][j - 1] > 100) {
							result[i][j] += previousIteration[i][j - 1];
						}
						result[i][j] = result[i][j] / 4;
					}
				}
			}
			previousIteration = result;
		}
	}

	public double[][] getResult() {
		return result;
	}

}
