package imageImporter;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

/**
 * This class demonstrates how to load an Image from an external file
 */
public class LoadImageApp2 extends Component {

	Image img;

	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	public LoadImageApp2() {
		try {
			String filename1 = "./family.ppm";
			String filename2 = "./mergetime.ppm";
			PPM picture1 = new PPM(filename1);
			PPM picture2 = new PPM(filename2);
			Gradient converted1 = new Gradient(picture1);
			Gradient converted2 = new Gradient(picture2);
			converted1.mergeGradient(converted2);
			
			PoissonSolver test = new PoissonSolver();
			test.integrate(converted1, converted1.getColorDivG());
			img = test.getImage();

//			img = converted2.getImageGradient(converted2.changeColorBean(converted2.getColorDivG()));
//			img = converted.getImageGradient(converted.changeColorBean(converted.getColorGradientY()));
//			img = converted.getImageGradient(converted.changeColorBean(converted.getColorGradientX()));

		} catch (IOException e) {
		}

	}
	public Dimension getPreferredSize() {
		if (img == null) {
			return new Dimension(2000, 2000);
		} else {
			return new Dimension(img.getWidth(null), img.getHeight(null));
		}
	}

	public static void main(String[] args) {

		JFrame f = new JFrame("Load Image Sample");

		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		f.add(new LoadImageApp2());
		f.pack();
		f.setVisible(true);
	}
}
