package workers.actions;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.elements.dialogs.ApproximationWindow;
import models.ApproximationModel;
import models.Mode;
import models.approximation.ApproximationPoint;
import models.approximation.ColorOccurence;
import models.approximation.HistogramColor;
import workers.CompareManager;
import workers.SuperUserInteractionHandler;

public class ApproximationActionHandler extends SuperUserInteractionHandler{
	private static ApproximationModel approximationModel;
	
	private static ApproximationWindow approximationWindow;
	
	public static void handle() {
		if(screenIsLoaded() && userHasSelectedAtLeastOneImage()) {
			CreateHistogramActionHandler.handle();
			setCurrentMode(Mode.APPROXIMATION);
			resetScreenListeners();
			readHistogramColors();
			sortHistogramColors();
			initDialog();
		}else
			showErrorDialog("First select at least one image.");
	}
	
	private static void readHistogramColors() {
		try {
			ApproximationActionHandler.approximationModel = new ApproximationModel();
			BufferedReader fileReader = new BufferedReader(new FileReader("./histogram.txt"));
			String line;
			while((line = fileReader.readLine()) != null) {
				String[] values = line.split(",");
				int red = Integer.parseInt(values[0]);
				int green = Integer.parseInt(values[1]);
				int blue = Integer.parseInt(values[2]);
				int number = Integer.parseInt(values[3]);
				Vector<Integer> indices = new Vector<Integer>();
				for(int i = 4; i < values.length; i++)
					indices.add(Integer.parseInt(values[i]));
				ColorOccurence colorOccurence = new ColorOccurence(number, indices);
				HistogramColor histogramColor = new HistogramColor(red, green, blue, colorOccurence);
				approximationModel.addHistogramColor(histogramColor);
			}
			fileReader.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void sortHistogramColors() {
		Comparator<HistogramColor> comparator = CompareManager.getNumberComparator();
		Vector<HistogramColor> valuesToSort = approximationModel.getHistogramColors();
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByNumber(valuesToSort);
		
		comparator = CompareManager.getRedComparator();
		valuesToSort = approximationModel.getColorsSortedBy("Red");
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByRed(valuesToSort);
		
		comparator = CompareManager.getGreenComparator();
		valuesToSort = approximationModel.getColorsSortedBy("Green");
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByGreen(valuesToSort);
		
		comparator = CompareManager.getBlueComparator();
		valuesToSort = approximationModel.getColorsSortedBy("Blue");
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByBlue(valuesToSort);
	}
	
	private static void initDialog() {
		approximationWindow = new ApproximationWindow(viewModel);
		approximationWindow.initScreenAndSlider();
		addChangeListenerToSlider();
		approximationWindow.setLocationRelativeTo(null);
		approximationWindow.pack();
		approximationWindow.setVisible(true);
	}
	
	private static void addChangeListenerToSlider() {
		approximationWindow.slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				double reductionFactor = approximationWindow.slider.getValue() / 100;;
				startApproximation(reductionFactor);
				approximationWindow.screen.repaint();
			}			
		});
	}
	
	private static void startApproximation(double reductionFactor) {
		int numberOfColorsToReplace = (int) Math.round(approximationModel.getHistogramColors().size() * reductionFactor);
		Vector<HistogramColor> colorsSortedByNumber = approximationModel.getColorsSortedBy("Number");
		for(int i = 0; i < numberOfColorsToReplace; i++) {
			HistogramColor colorSortedByNumber = colorsSortedByNumber.get(i);
			ApproximationPoint pointToApproximate = new ApproximationPoint(colorSortedByNumber.getRed(), colorSortedByNumber.getGreen(), colorSortedByNumber.getBlue());
			ApproximationPoint approximatedPoint = getApproximatedPoint(pointToApproximate);
			Color approximatedColors = new Color(approximatedPoint.getX(), approximatedPoint.getY(), approximatedPoint.getZ());
			colorSortedByNumber.setColorValues(approximatedColors);
		}
		setReducedPixels(colorsSortedByNumber);
	}
	
	private static ApproximationPoint getApproximatedPoint(ApproximationPoint pointToApproximate) {
		double rangeX, rangeY, rangeZ;
		int[] colorsSortedByRed = new int[approximationModel.getColorsSortedBy("Red").size()];
		for(int i = 0; i < colorsSortedByRed.length; i++) {
			colorsSortedByRed[i] = approximationModel.getSingleColorSortedBy("Red", i).getRed();
		}
		int middleX = binarySearch(colorsSortedByRed, pointToApproximate.getX(), 0, colorsSortedByRed.length - 1);
		
		if(!approximationModel.valueWasFound()) {
			if(pointToApproximate.getX() < colorsSortedByRed[middleX]) {
				
			}
		}
		return null;
	}
	
	private static int binarySearch(int[] values, int searched, int leftEdge, int rightEdge) {
		int iL = leftEdge;
		int iR = rightEdge;
		int middle = 0;
		while(iL <= iR) {
			middle = (iL + iR) / 2;
			final int RES = Integer.compare(values[middle], searched);
			if(RES == 0) {
				approximationModel.setValueWasFound(true);
				return middle;
			}else if(RES < 0)
				iL = middle + 1;
			else
				iR = middle - 1;
		}
		approximationModel.setValueWasFound(false);
		return middle;
	}
	
	private static void setReducedPixels(Vector<HistogramColor> colorsSortedByNumber) {
		for(int i = 0; i < colorsSortedByNumber.size(); i++) {
			for(int j = 0; j < colorsSortedByNumber.get(i).getColorOccurence().getIndices().size(); j++) {
				int index = colorsSortedByNumber.get(i).getColorOccurence().getIndices().get(j);
			}
		}
	}
}
