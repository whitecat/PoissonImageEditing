package imageImporter;

import java.awt.Image;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

public class PoissonImageEditor {

	public static void main(String[] args) throws IOException {

		if ((args.length == 3) && args[0].equals("-poisson")) {
			new PoissonImageEditor(args[0], args[1], args[2], args[3]);
		} else if ((args.length == 4) && args[0].equals("-watermark")) {
			new PoissonImageEditor(args[0], args[1], args[2], args[3]);
		} else if ((args.length == 3) && args[0].equals("-div")) {
			new PoissonImageEditor(args[0], args[1], args[2], "0");
		} else if ((args.length == 3) && args[0].equals("-gradX")) {
			new PoissonImageEditor(args[0], args[1], args[2], "0");
		} else if ((args.length == 3) && args[0].equals("-gradY")) {
			new PoissonImageEditor(args[0], args[1], args[2], "0");
		} else
			System.out.println("Please use readme to see an example of inputs");
		// new PoissonImageEditor();
	}

	public PoissonImageEditor(String type, String file1, String file2, String iterations)
			throws UnsupportedEncodingException, IOException {
		int height;
		int width;
		Image result;

		// calculate gradient of picture 1
		PPM picture1 = new PPM(file1);
		Gradient converted1 = new Gradient(picture1);

		// calculate gradient of picture 2 if desired
		if (!file2.equals("null")) {
			PPM picture2 = new PPM(file2);
			Gradient converted2 = new Gradient(picture2);
			converted1.mergeGradient(converted2);
		}

		height = picture1.getPicture().length;
		width = picture1.getPicture()[0].length;

		if (type.equals("-poisson") || type.equals("-watermark")) {
			PoissonSolver test = new PoissonSolver(Integer.parseInt(iterations));
			test.integrate(converted1.getDivG());
			result = Util.getImage(test.getResult());
		} else if (type.equals("-div")) {
			result = Util.getImage(Util.changeColorBean(converted1.getGradientX()));
		} else if (type.equals("-gradX")) {
			result = Util.getImage(Util.changeColorBean(converted1.getGradientX()));
		} else if (type.equals("-gradY")) {
			result = Util.getImage(Util.changeColorBean(converted1.getGradientY()));
		} else
			result = picture1.getImage();

		JFrame frame = new JFrame("ShowImage");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width + 18, height + 25);

		ShowImage panel = new ShowImage(result);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
