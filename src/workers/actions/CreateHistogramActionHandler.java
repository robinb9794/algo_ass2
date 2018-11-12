package workers.actions;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class CreateHistogramActionHandler extends SuperUserInteractionHandler {
	public static void handle() {
		if(screenIsLoaded() && userHasSelectedAtLeastOneImage())
			createHistogram();
		else
			showErrorDialog("First load and select at least one image.");
	}
	
	private static void createHistogram() {
		Writer writer;
		try {
			Map<String, Integer> histogram = new HashMap<String, Integer>();
			for (int i = 0; i < viewModel.getScreenWidth() * viewModel.getScreenHeight(); i++) {
				String color = binaryToDec(Integer.toBinaryString(PixelCoordinator.getSingleTargetPixel(i)));
				int number = 1;
				if (!histogram.containsKey(color)) {
					histogram.put(color, number);
				} else {
					number = histogram.get(color);
					histogram.replace(color, number + 1);
				}
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./histogram.txt"), "UTF-8"));
			for (Entry e : histogram.entrySet()) {
				writer.write(e.getKey() + "_" + e.getValue() + "\n");
			}
			writer.close();
			showInfoDialog("Success!", "./histogram.txt has been created!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String binaryToDec(String bin){
		String b="",g="",r="";
		for(int i=0; i<8;i++){
			r=r+bin.charAt(8+i);
			g=g+bin.charAt(8+i+8);
			b=b+bin.charAt(8+i+2*8);			
		}
		return new Integer(Integer.parseInt(r,2)).toString()+"_"+new Integer(Integer.parseInt(g,2)).toString()+"_"+new Integer(Integer.parseInt(b,2)).toString();
	}
}
