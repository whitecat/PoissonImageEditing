package imageImporter;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ShowImage extends JPanel {
	Image image;

	public ShowImage(Image result) {
		super();
		image = result;
	}

	public void paintComponent(Graphics g) {
		// Draw our Image object.
		g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), this);
	}
}