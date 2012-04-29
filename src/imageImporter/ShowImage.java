package imageImporter;

// Import the basic graphics classes.
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ShowImage extends JPanel{
  Image image; // Declare a name for our Image object.

  public ShowImage(Image result){
   super();
   image = result;
  }

// The following methods are instance methods.

  public void paintComponent(Graphics g){
   // Draw our Image object.
   g.drawImage(image,0,0,image.getWidth(null),image.getHeight(null), this); // at location 50,10
  }
}