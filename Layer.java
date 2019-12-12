package kth.ag2411.mapalgebra;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Layer {
	// Attributes
	public String name; // name of this layer
	public int nRows; // number of rows
	public int nCols; // number of columns
	public double[] origin = new double[2]; // x,y-coordinates of lower-left corner
	public double resolution; // cell size
	public double[] values; // data. Alternatively, public double[][] values;
	public double nullValue;

	//Constructor
	public Layer(String layerName, String fileName) {
		try {
			File rFile = new File(fileName);
			// This object represents a stream of characters read from the file.
			FileReader fReader = new FileReader(rFile);
			// This object represents lines of Strings created from the stream of characters.
			BufferedReader bReader = new BufferedReader(fReader);
			this.name=layerName;
			// Read each line of Strings
			String text;
			text = bReader.readLine(); // first line is read
			String Om = text.substring(5).trim();
			this.nCols = Integer.parseInt(Om);
			text = bReader.readLine(); // second line is read
			Om = text.substring(5).trim();
			this.nRows = Integer.parseInt(Om);
			text = bReader.readLine(); // third line is read
			Om = text.substring(9).trim();
			double xll = Double.parseDouble(Om);
			text = bReader.readLine(); // forth line is read
			Om = text.substring(9).trim();
			double yll = Double.parseDouble(Om);
			this.origin[0] = xll;
			this.origin[1] = yll;
			text = bReader.readLine(); // fifth line is read
			Om = text.substring(8).trim();
			this.resolution = Double.parseDouble(Om);
			text = bReader.readLine(); // sixth line is read
			Om = text.substring(12).trim();
			this.nullValue = Double.parseDouble(Om);// Read until the last line when the number of lines is not known
			int num= this.nRows  * this.nCols;
			this.values = new double[num];
			int count = 0;
			text = bReader.readLine();
			while (text != null) { // Repeat the following until no more line to be read
				String[] str1 = text.split("\\s+");
				for (int i = 0; i < str1.length; i++) {
					this.values[count * nCols + i] = Double.valueOf(str1[i]);
				}
				count++;
				text = bReader.readLine();
			}
			bReader.close();
		}	catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public void print(){
		System.out.println("ncols "+nCols);
		System.out.println("nrows "+nRows);
		System.out.println("xllcorner "+origin[0]);
		System.out.println("yllcorner "+origin[1]);
		System.out.println("cellsize "+resolution);
		System.out.println("NODATA_value " + nullValue);
		for (int j = 0; j < nRows; j++) {
			for (int i = 0; i < nCols; i++) {
			System.out.print(this.values[j*nCols+ i] +" ");
			}
			System.out.println();
		}
	}
	public void save(String outputFileName) {
		// save this layer as an ASCII file that can be imported to ArcGIS
		try {	File file = new File(outputFileName);	// This object represents ASCII data (to be) stored in the file
		FileWriter fWriter = new FileWriter(file);
		fWriter.write("ncols "+ Integer.toString(this.nCols)+"\n");
		fWriter.append("nRows "+  Integer.toString(this.nRows)+"\n");
		fWriter.append("xllcorner "+ Double.toString(origin[0])+"\n");
		fWriter.append("yllcorner "+ Double.toString(origin[1])+"\n");
		fWriter.append("cellsize "+ Double.toString(resolution)+"\n");
		fWriter.append("NODATA_value "+ Double.toString(nullValue)+"\n");
		for (int j = 0; j < nRows; j++) {
			for (int i = 0; i < nCols; i++) {
				fWriter.append(this.values[j*nCols+ i] +" ");
			}
			fWriter.append("\n");
		}
		fWriter.close();
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
}