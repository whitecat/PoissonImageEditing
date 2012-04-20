package imageImporter;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;

public class GradientConverter {
	private Color[][] gradientX;
	private Color[][] gradientY;
	private PPM picture;
	int[] dataX = null;
	
	
	public GradientConverter(PPM picture) {
		this.picture = picture;
		//changeToColorArray(picture);
		calculateGradient(picture);
	}

	
	private void changeToColorArray(PPM picture) {

		
		
	}


	private void calculateGradient(PPM picture){
		
				
				
		Color[][] pic = picture.getPicture();
		gradientX = new Color[pic.length][pic[0].length-2];
		
		for(int i = 0; i < pic.length; i++){
			for(int j = 1; j<pic[i].length-1; j++){				
				//System.out.println(pic[i][j+1].getRGB() - pic[i][j-1].getRGB());
				gradientX[i][j-1] = new Color(pic[i][j+1].getRGB() - pic[i][j-1].getRGB()); 
			}
		}
		
		gradientY = new Color[pic.length-2][pic[0].length];
		for(int i = 0; i < pic[0].length; i++){
			for(int j = 1; j<pic.length-1; j++){				
				gradientY[j-1][i] = new Color(pic[j+1][i].getRGB() - pic[j-1][i].getRGB()); 
			}
		}
		
	}
	
	
	public Color[][] getGradientX() {
		return gradientX;

	}

	public Color[][] getGradientY() {
		return gradientY;

	}
	
	public Image getImageGradientX(){
		
		int[] data = null;
		data = new int[gradientX.length*(gradientX[0].length)];
		for (int i = 0; i < gradientX.length; i++) {
			for (int j = 0; j < gradientX[i].length; j++) {
				data[(gradientX[0].length - 1) *i+j] = (gradientX[i][j].getRGB());
			}
		}
		
		
		MemoryImageSource memoryImageSource = new MemoryImageSource(gradientX.length,
				gradientX.length, data, 0, gradientX[0].length - 1);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);
	}
	
	public Image getImageGradientY(){
		
		int[] data = null;
		data = new int[gradientY.length*(gradientY[0].length)];
		for (int i = 0; i < gradientY.length; i++) {
			for (int j = 0; j < gradientY[i].length; j++) {
				data[(gradientY[0].length - 1) *i+j] = (gradientY[i][j].getRGB());
			}
		}
		
		
		MemoryImageSource memoryImageSource = new MemoryImageSource(gradientY.length,
				gradientY.length, data, 0, gradientY[0].length - 1);
		return Toolkit.getDefaultToolkit().createImage(memoryImageSource);
	}

}
