package workers.actions;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import interfaces.LoadingScreen;
import models.approximation.ColorOccurence;
import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class CreateHistogramActionHandler extends SuperUserInteractionHandler {
	private static LoadingScreen loadingScreen;
	private static boolean histogramHasBeenCreated;
	
	public static void handle() {
		if(screenIsLoaded() && userHasSelectedAtLeastOneImage()) {
			loadingScreen = (LoadingScreen) guiElementFactory.getGUIElement("LoadingWindow");
			initLoadingScreen();
			createHistogram();
		}			
		else
			showErrorDialog("First load and select at least one image.");
	}
	
	private static void initLoadingScreen() {
		new Thread() {
			@Override
			public void run() {
				try {
					loadingScreen.setValues("Creating ./histogram.txt", "Histogram has been created!");
					loadingScreen.init();
					while(!histogramHasBeenCreated) {
						Thread.sleep(50);
					}
					loadingScreen.closeScreen();
				}catch(Exception ex) {
					ex.printStackTrace();
				}	
			}
		}.start();
	}
	
	private static void createHistogram() {
		Writer writer;
		try {
			Map<String, ColorOccurence> histogram = new HashMap<String, ColorOccurence>();
			for (int i = 0; i < viewModel.getScreenWidth(); i++) {
				for(int j = 0; j < viewModel.getScreenHeight(); j++) {
					int index = PixelCoordinator.getPixelIndex(i, j);
					int pixel = PixelCoordinator.getSingleTargetPixel(index);
					String rgb = binaryToRGB(Integer.toBinaryString(pixel));
					ColorOccurence colorOccurence = new ColorOccurence(1, index);
					if(histogram.containsKey(rgb)) {
						colorOccurence = histogram.get(rgb);
						colorOccurence.incrNumber();
						colorOccurence.addIndex(index);
						histogram.replace(rgb, colorOccurence);
					}else {
						histogram.put(rgb, colorOccurence);
					}
				}				
			}
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./histogram.txt"), "UTF-8"));
			for (Entry<String, ColorOccurence> entry : histogram.entrySet()) {
				String rgb = entry.getKey();
				ColorOccurence colorOccurence = entry.getValue();
				writer.write(rgb + "," + colorOccurence.occurenceToString() + "\n");
			}
			writer.close();
			histogramHasBeenCreated = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String binaryToRGB(String pixel){
		String red = "", green = "", blue = "";
		for(int i=0; i<8;i++){
			red += pixel.charAt(8+i);
			green += pixel.charAt(8+i+8);
			blue += pixel.charAt(8+i+2*8);			
		}
		return new Integer(Integer.parseInt(red,2)).toString()+","+new Integer(Integer.parseInt(green,2)).toString()+","+new Integer(Integer.parseInt(blue,2)).toString();
	}
}
