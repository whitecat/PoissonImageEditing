package imageImporter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
 
/**
 * This class demonstrates how to load an Image from an external file
 */
public class LoadImageApp extends Component {
           
    Image img;
 
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
 
    public LoadImageApp() {
       try {
   		String filename = "./family.ppm";
   		PPM picture = new PPM(filename);
		GradientConverter converted = new GradientConverter(picture);

   		
           img = converted.getImageGradientX();
       } catch (IOException e) {
       }
 
    }
 
    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(200000,200000);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
 
    public static void main(String[] args) {
 
        JFrame f = new JFrame("Load Image Sample");
             
        f.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
 
        f.add(new LoadImageApp());
        f.pack();
        f.setVisible(true);
    }
}
