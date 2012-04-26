package imageImporter;

import java.io.IOException;

public class Integration {

	int NPRE = 1;
	int NPOST = 1;
	int NGMAX = 15;



	public void mglin(double[][] u, int n, int ncycle) throws Exception {
		int j, jcycle, jj, jpost, jpre, nf, ng = 0, ngrid, nn;

		// [NGMAX+1]
		double[][][] ires = new double[NGMAX + 1][][];
		double[][][] irho = new double[NGMAX + 1][][];
		double[][][] irhs = new double[NGMAX + 1][][];
		double[][][] iu = new double[NGMAX + 1][][];

		nn = n;
		while (nn > 1) {
			nn = nn >> 1;
			ng++;
		}
		if (n != 1 + (1L << ng))
			throw new Exception("n-1 must be a power of 2 in mglin.");
		if (ng > NGMAX)
			throw new Exception("increase NGMAX in mglin.");
		nn = n / 2 + 1;
		ngrid = ng - 1;

		irho[ngrid] = new double[nn+1][nn+1];
		rstrct(irho[ngrid], u, nn);
		while (nn > 3) {
			nn = nn / 2 + 1;
			irho[--ngrid] = new double[nn+1][nn+1];
			rstrct(irho[ngrid], irho[ngrid + 1], nn);
		}
		nn = 3;
		iu[1] = new double[nn+1][nn+1];
		irhs[1] = new double[nn+1][nn+1];
		slvsml(iu[1], irho[1]);
		ngrid = ng;
		for (j = 2; j <= ngrid; j++) {
			nn = 2 * nn - 1;
			iu[j] = new double[nn+1][nn+1];
			irhs[j] = new double[nn+1][nn+1];
			ires[j] = new double[nn+1][nn+1];
			interp(iu[j], iu[j - 1], nn);
			copy(irhs[j], (j != ngrid ? irho[j] : u), nn);
			for (jcycle = 1; jcycle <= ncycle; jcycle++) {
				nf = nn;
				for (jj = j; jj >= 2; jj--) {
					for (jpre = 1; jpre <= NPRE; jpre++)
						relax(iu[jj], irhs[jj], nf);
					resid(ires[jj], iu[jj], irhs[jj], nf);
					nf = nf / 2 + 1;
					rstrct(irhs[jj - 1], ires[jj], nf);
					fill0(iu[jj - 1], nf);
				}
				slvsml(iu[1], irhs[1]);
				nf = 3;
				for (jj = 2; jj <= j; jj++) {
					nf = 2 * nf - 1;
					addint(iu[jj], iu[jj - 1], ires[jj], nf);
					for (jpost = 1; jpost <= NPOST; jpost++)
						relax(iu[jj], irhs[jj], nf);
				}
			}
		}

		copy(u, iu[ngrid], n);

	}

	void resid(double[][] res, double[][] u, double[][] rhs, int n) {
		int i, j;
		double h, h2i;

		h = 1.0 / (n - 1);
		h2i = 1.0 / (h * h);
		for (j = 2; j < n; j++)
			for (i = 2; i < n; i++)
				res[i][j] = -h2i
						* (u[i + 1][j] + u[i - 1][j] + u[i][j + 1] + u[i][j - 1] - 4.0 * u[i][j])
						+ rhs[i][j];
		for (i = 1; i <= n; i++)
			res[i][1] = res[i][n] = res[1][i] = res[n][i] = 0.0;
	}

	void copy(double[][] aout, double[][] ain, int n) {
		int i, j;
		for (i = 1; i <= n; i++)
			for (j = 1; j <= n; j++)
				aout[j][i] = ain[j][i];

	}

	void interp(double[][] uf, double[][] uc, int nf) {
		int ic, iif, jc, jf, nc;
		nc = nf / 2 + 1;
		for (jc = 1, jf = 1; jc <= nc; jc++, jf += 2)
			for (ic = 1; ic <= nc; ic++)
				uf[2 * ic - 1][jf] = uc[ic][jc];

		for (jf = 1; jf <= nf; jf += 2)
			for (iif = 2; iif < nf; iif += 2)
				uf[iif][jf] = 0.5 * (uf[iif + 1][jf] + uf[iif - 1][jf]);

		for (jf = 2; jf < nf; jf += 2)
			for (iif = 1; iif <= nf; iif++)
				uf[iif][jf] = 0.5 * (uf[iif][jf + 1] + uf[iif][jf - 1]);
	}

	void fill0(double[][] u, int n) {
		int i, j;
		for (j = 1; j <= n; j++)
			for (i = 1; i <= n; i++)
				u[i][j] = 0.0;
	}

	void addint(double[][] uf, double[][] uc, double[][] res, int nf) {
		int i, j;

		interp(res, uc, nf);
		for (j = 1; j <= nf; j++)
			for (i = 1; i <= nf; i++)
				uf[i][j] += res[i][j];
	}

	void relax(double[][] u, double[][] rhs, int n) {
		int i, ipass, isw, j, jsw = 1;
		double h, h2;

		h = 1.0 / (n - 1);
		h2 = h * h;
		for (ipass = 1; ipass <= 2; ipass++, jsw = 3 - jsw) {
			isw = jsw;
			for (j = 2; j < n; j++, isw = 3 - isw)
				for (i = isw + 1; i < n; i += 2)
					u[i][j] = 0.25 * (u[i + 1][j] + u[i - 1][j] + u[i][j + 1] + u[i][j - 1] - h2
							* rhs[i][j]);
		}
	}

	void relax2(double[][] u, double[][] rhs, int n) {
		int i, ipass, isw, j, jsw = 1;
		double foh2, h, h2i, res;

		h = 1.0 / (n - 1);
		h2i = 1.0 / (h * h);
		foh2 = -4.0 * h2i;
		for (ipass = 1; ipass <= 2; ipass++, jsw = 3 - jsw) {
			isw = jsw;
			for (j = 2; j < n; j++, isw = 3 - isw) {
				for (i = isw + 1; i < n; i += 2) {
					res = h2i
							* (u[i + 1][j] + u[i - 1][j] + u[i][j + 1] + u[i][j - 1] - 4.0 * u[i][j])
							+ u[i][j] * u[i][j] - rhs[i][j];
					u[i][j] -= res / (foh2 + 2.0 * u[i][j]);
				}
			}
		}
	}

	void rstrct(double[][] uc, double[][] uf, int nc) {
		int ic, iif, jc, jf, ncc = 2 * nc - 1;
		nc = nc-1;
		
		for (jf = 2, jc = 1; jc < nc - 1; jc++, jf += 2) {
			for (iif = 2, ic = 1; ic < nc - 1; ic++, iif += 2) {
				uc[ic][jc] = 0.5 * uf[iif][jf] + 0.125
						* (uf[iif + 1][jf] + uf[iif - 1][jf] + uf[iif][jf + 1] + uf[iif][jf - 1]);
			}
		}

		for (jc = 1, ic = 1; ic <= nc; ic++, jc += 2) {
			uc[ic][1] = uf[jc][1];
			uc[ic][nc] = uf[jc][ncc];
		}
		for (jc = 1, ic = 1; ic <= nc; ic++, jc += 2) {
			uc[1][ic] = uf[1][jc];
			uc[nc][ic] = uf[ncc][jc];
		}
	}

	void slvsml(double[][] u, double[][] rhs) {
		double h = 0.5;

		fill0(u, 3);
		u[2][2] = -h * h * rhs[2][2] / 4.0;
	}

	void slvsm2(double[][] u, double[][] rhs) {
		double disc, fact, h = 0.5;

		fill0(u, 3);
		fact = 2.0 / (h * h);
		disc = Math.sqrt(fact * fact + rhs[2][2]);
		u[2][2] = -rhs[2][2] / (fact + disc);
	}
}
