package imageImporter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

public class PoissonImageEditor {

	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			new PoissonImageEditor();
		} else
			new PoissonImageEditor();
	}

	public PoissonImageEditor() throws UnsupportedEncodingException, IOException {
		// String filename = "./boxes_1.ppm";
		// PPM picture = new PPM(filename);
		// Gradient converted = new Gradient(picture);
		// PrintWriter out = new PrintWriter(new
		// FileWriter("./outputfile.txt"));

		String filename1 = "./images/family.ppm";
		// String filename2 = "./watermark.ppm";
		PPM picture1 = new PPM(filename1);
		// PPM picture2 = new PPM(filename2);
		Gradient converted1 = new Gradient(picture1);
		// Gradient converted2 = new Gradient(picture2);
		// converted1.mergeGradient(converted2);

		 PoissonSolver test = new PoissonSolver();
		 test.integrate(converted1, converted1.getColorDivG());

		JFrame frame = new JFrame("ShowImage");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(converted1.getWidth(), converted1.getHeight());

		
//		ShowImage panel = new ShowImage(converted1.getImageGradient(converted1.getColorGradientX()));
		ShowImage panel = new ShowImage(test.getImage());
		frame.setContentPane(panel);
		frame.setVisible(true);

		// img =
		// converted2.getImageGradient(converted2.changeColorBean(converted2.getColorDivG()));
		// img =
		// converted.getImageGradient(converted.changeColorBean(converted.getColorGradientY()));
		// img =
		// converted.getImageGradient(converted.changeColorBean(converted.getColorGradientX()));

	}

}
