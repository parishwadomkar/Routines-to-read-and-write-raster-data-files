package kth.ag2411.mapalgebra;

import java.io.IOException;

public class Ex01 {
public static void main(String[] args) throws IOException {
	if(args.length == 3){
		//Instantiate a layer
		Layer Layer = new Layer(args[0], args[1]);
		//Printing it on the console
		Layer.print();
		//Saving it to the file output.txt
		Layer.save(args[2]);
		}
		else {
		System.out.println("Too many or few arguments......");
		}
		}
}