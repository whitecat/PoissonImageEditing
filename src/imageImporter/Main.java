package imageImporter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) throws IOException {
		String filename = "./boxes_1.ppm";
		PPM picture = new PPM(filename);
		Gradient converted = new Gradient(picture);
		PrintWriter out = new PrintWriter(new FileWriter("./outputfile.txt")); 
		
	}
}
