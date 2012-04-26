package imageImporter;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class sandbox {
	public static void main(String[] args) throws IOException {
		String filename = "./family.ppm";
		PrintWriter out = new PrintWriter(new FileWriter("./outputfile2.txt"));
		
		
		
		PPM picture = new PPM(filename);
		Gradient converted = new Gradient(picture);
//		converted.getImageGradientX();
//		converted.getImageGradientY();
		
		PoissonSolver test = new PoissonSolver();
		
		test.integrate(converted, converted.getColorDivG());
		
		out.close();
	}
	
}
