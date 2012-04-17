package imageImporter;
//
// PPM file manipulation class.  Allows read/write of RAWBITS PPM files.
//
// Written by Prof. Golden G. Richard III, October 1997/September 1998.
//

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PPMFile {

    private char bytes[][]=null;      // bytes which make up binary PPM image
    private String filename=null;     // filename for PPM image


    public PPMFile(String filename) {
	this.filename = filename;
	ReadImage();
    }


    public PPMFile(char bytes[][]) {
	this.bytes = bytes;
    }


    public char[][] GetBytes() {
	return bytes;
    }


    public void WriteImage(String fn) {
	
	// write PPM image from current 'bytes' 

	if (bytes != null) {
	    try {
		FileOutputStream fos = new FileOutputStream(fn);
		fos.write(new String("P6\n").getBytes());
		fos.write(new String( (bytes[0].length / 3) + " " + (bytes.length) + "\n").getBytes());
		fos.write(new String("255\n").getBytes());
		
		for (int y = 0; y < bytes.length; y++) {
		    for (int x=0; x < bytes[0].length; x++) {
			fos.write((byte)bytes[y][x]);
		    }
		}
		fos.close();
	    }
	    catch (FileNotFoundException e) {
		System.err.println("Error opening PPM file, write image cancelled.");
	    }
	    catch (IOException e) {
		System.err.println("Error writing PPM file, write image cancelled.");
	    }
	}
	else {
	    System.out.println("No current image in WriteImage, nothing written.");
	}
    }
	

    public void ReadImage() {

	// read PPM format image 

	try {
	    char buffer;                   // character in PPM header
	    String id = new String();      // PPM magic number ("P6")
	    String dim = new String();     // image dimension as a string
	    FileInputStream fis = new FileInputStream(filename);
	    InputStreamReader isr = new InputStreamReader(fis);

	    do {
		buffer = (char)isr.read();
		id = id + buffer;
	    } while (buffer != '\n' && buffer != ' ');
	    if (id.charAt(0) == 'P' && id.charAt(1) == '6') {
		System.out.print("Image is ");
		System.out.flush();
		buffer = (char)isr.read();
		do {                          // second header line is "width height\n"
		    dim = dim + buffer;
		    buffer = (char)isr.read();
		} while (buffer != ' ' && buffer != '\n');

		int width = Integer.parseInt(dim);
		System.out.print(width);
		System.out.flush();
		dim = new String();
		buffer = (char)isr.read();
		do {
		    dim = dim + buffer;
		    buffer = (char)isr.read();
		} while (buffer != ' ' && buffer != '\n');
		int height = Integer.parseInt(dim);
		System.out.println(" X " + height + " pixels.");
		do {                          // third header line is max RGB value, e.g., "255\n"
		    buffer = (char)isr.read();
		} while (buffer != ' ' && buffer != '\n');
		
		System.out.print("Reading image...");
		System.out.flush();
		
		// remainder of file is width*height*3 bytes (red/green/blue triples)

		bytes = new char[height][width*3];
		
		for (int y=0; y < height; y++) {
			isr.read(bytes[y]);
		}
		
		System.out.println("\nDone.");
		fis.close();
	    }
	    else {
		System.out.println("Unsupported file format, no image read.");
		bytes=null;
	    }
	}
	catch (FileNotFoundException e) {
	    System.out.println("\nError opening PPM file, no image read.");
	    bytes = null;
	}
	catch (IOException e) {
	    System.out.println("\nError reading PPM file, no image read.");
	    bytes = null;
	}
	catch (NumberFormatException e) {
	    System.out.println("\nSomething's wrong with the PPM header?  No image read.");
	}
    }
}
