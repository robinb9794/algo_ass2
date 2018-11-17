package workers.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import gui.elements.dialogs.ApproximationWindow;
import models.ApproximationModel;
import models.Mode;
import models.approximation.ApproximationPoint;
import models.approximation.ColorOccurence;
import workers.CompareManager;
import workers.PixelCoordinator;
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
				ApproximationPoint approximationPoint = new ApproximationPoint(red, green, blue, colorOccurence);
				approximationModel.addHistogramColor(approximationPoint);
			}
			fileReader.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void sortHistogramColors() {		
		Vector<ApproximationPoint> valuesToSort = approximationModel.getHistogramColors();
		
		Comparator<ApproximationPoint> comparator = CompareManager.getNumberComparator();
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByNumber((Vector<ApproximationPoint>) valuesToSort.clone());
		
		
		comparator = CompareManager.getRedComparator();
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByRed((Vector<ApproximationPoint>) valuesToSort.clone());
		
		comparator = CompareManager.getGreenComparator();
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByGreen((Vector<ApproximationPoint>) valuesToSort.clone());
		
		comparator = CompareManager.getBlueComparator();
		Collections.sort(valuesToSort, comparator);
		approximationModel.setColorsSortedByBlue((Vector<ApproximationPoint>) valuesToSort.clone());		
	}
	
	private static void initDialog() {
		approximationWindow = new ApproximationWindow(viewModel);
		approximationWindow.initElements();
		addChangeListenerToSlider();
		approximationWindow.setLocationRelativeTo(null);
		approximationWindow.pack();
		approximationWindow.setVisible(true);
	}
	
	private static void addChangeListenerToSlider() {
		approximationWindow.approximateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(enteredPercentageIsValid()) {
					String text = approximationWindow.textField.getText();
					double reductionFactor = Double.parseDouble(text) / 100;
					startApproximation(reductionFactor);
					approximationWindow.screen.repaint();
				}else
					showErrorDialog("Please enter a number between 0 and 100.");				
			}			
		});
	}
	
	private static boolean enteredPercentageIsValid() {
		String text = approximationWindow.textField.getText();
		try {
			int percentage = Integer.parseInt(text);
			if(percentage >= 0 && percentage <= 100)
				return true;
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	private static void startApproximation(double reductionFactor) {
		int numberOfAllHistogramColors = approximationModel.getHistogramColors().size();
		int numberOfColorsToReplace = (int) Math.round(numberOfAllHistogramColors - numberOfAllHistogramColors * reductionFactor);
		final int CUT = numberOfAllHistogramColors - numberOfColorsToReplace;
		Vector<ApproximationPoint> colorsSortedByNumber = approximationModel.getColorsSortedBy("Number");
		ApproximationPoint colorSortedByNumber, pointToApproximate, approximatedPoint;
		Color approximatedColors;
		for(int i = 0; i < numberOfColorsToReplace; i++) {
			colorSortedByNumber = colorsSortedByNumber.elementAt(i);
			pointToApproximate = new ApproximationPoint(colorSortedByNumber.getRed(), colorSortedByNumber.getGreen(), colorSortedByNumber.getBlue());
			approximatedPoint = getApproximatedPoint(CUT, pointToApproximate);
			approximatedColors = new Color(approximatedPoint.getRed(), approximatedPoint.getGreen(), approximatedPoint.getBlue());
			colorSortedByNumber.setColorValues(approximatedColors);
		}
		setReducedPixels(colorsSortedByNumber);
	}
	
	private static ApproximationPoint getApproximatedPoint(int CUT, ApproximationPoint pointToApproximate) {
		double rangeRed, rangeGreen, rangeBlue;
		int[] colorsSortedByRed = new int[approximationModel.getColorsSortedBy("Red").size()];
		for(int i = 0; i < colorsSortedByRed.length; i++) {
			colorsSortedByRed[i] = approximationModel.getSingleColorSortedBy("Red", i).getRed();
		}
		int middleRed = binarySearch(colorsSortedByRed, pointToApproximate.getRed(), CUT, colorsSortedByRed.length - 1);
		
		double distanceLeft, distanceRight;
		if(!searchedValueIsAvailableInQuantity()) {
			if(pointToApproximate.getRed() < colorsSortedByRed[middleRed]) {
				distanceRight = approximationModel.getSingleColorSortedBy("Red", middleRed - 1).distance(pointToApproximate);
				if(middleRed > 0) {
					distanceLeft = approximationModel.getSingleColorSortedBy("Red", middleRed - 1).distance(pointToApproximate);
					if(distanceLeft <= distanceRight) {
						rangeRed = distanceLeft;
						middleRed -= 1;
					}else
						rangeRed = distanceRight;
				}
				else
					rangeRed = distanceRight;
			}else {
				distanceLeft = approximationModel.getSingleColorSortedBy("Red", middleRed).distance(pointToApproximate);
				if(middleRed < colorsSortedByRed.length - 1) {
					distanceRight = approximationModel.getSingleColorSortedBy("Red", middleRed + 1).distance(pointToApproximate);
					if(distanceLeft <= distanceRight)
						rangeRed = distanceLeft;
					else {
						rangeRed = distanceRight;
						middleRed += 1;
					}
				}else
					rangeRed = distanceLeft;
			}
		}else
			rangeRed = approximationModel.getSingleColorSortedBy("Red", middleRed).distance(pointToApproximate);
		
		int[] colorsSortedByGreen = new int[approximationModel.getColorsSortedBy("Green").size()];
		for(int i = 0; i < colorsSortedByGreen.length; i++) {
			colorsSortedByGreen[i] = approximationModel.getSingleColorSortedBy("Green", i).getGreen();
		}
		int middleGreen = binarySearch(colorsSortedByGreen, pointToApproximate.getGreen(), CUT, colorsSortedByGreen.length - 1);
		
		if(!searchedValueIsAvailableInQuantity()) {
			if(pointToApproximate.getGreen() < colorsSortedByGreen[middleGreen]) {
				distanceRight = approximationModel.getSingleColorSortedBy("Green", middleGreen - 1).distance(pointToApproximate);
				if(middleGreen > 0) {
					distanceLeft = approximationModel.getSingleColorSortedBy("Green", middleGreen - 1).distance(pointToApproximate);
					if(distanceLeft <= distanceRight) {
						rangeGreen = distanceLeft;
						middleGreen -= 1;
					}else
						rangeGreen = distanceRight;
				}
				else
					rangeGreen = distanceRight;
			}else {
				distanceLeft = approximationModel.getSingleColorSortedBy("Green", middleGreen).distance(pointToApproximate);
				if(middleGreen < colorsSortedByGreen.length - 1) {
					distanceRight = approximationModel.getSingleColorSortedBy("Green", middleGreen + 1).distance(pointToApproximate);
					if(distanceLeft <= distanceRight)
						rangeGreen = distanceLeft;
					else {
						rangeGreen = distanceRight;
						middleGreen += 1;
					}
				}else
					rangeGreen = distanceLeft;
			}
		}else
			rangeGreen = approximationModel.getSingleColorSortedBy("Green", middleGreen).distance(pointToApproximate);
		
		int[] colorsSortedByBlue = new int[approximationModel.getColorsSortedBy("Blue").size()];
		for(int i = 0; i < colorsSortedByBlue.length; i++) {
			colorsSortedByBlue[i] = approximationModel.getSingleColorSortedBy("Blue", i).getBlue();
		}
		int middleBlue = binarySearch(colorsSortedByBlue, pointToApproximate.getBlue(), CUT, colorsSortedByBlue.length - 1);
		
		if(!searchedValueIsAvailableInQuantity()) {
			if(pointToApproximate.getBlue() < colorsSortedByBlue[middleBlue]) {
				distanceRight = approximationModel.getSingleColorSortedBy("Blue", middleBlue - 1).distance(pointToApproximate);
				if(middleBlue > 0) {
					distanceLeft = approximationModel.getSingleColorSortedBy("Blue", middleBlue - 1).distance(pointToApproximate);
					if(distanceLeft <= distanceRight) {
						rangeBlue = distanceLeft;
						middleBlue -= 1;
					}else
						rangeBlue = distanceRight;
				}
				else
					rangeBlue = distanceRight;
			}else {
				distanceLeft = approximationModel.getSingleColorSortedBy("Blue", middleBlue).distance(pointToApproximate);
				if(middleBlue < colorsSortedByBlue.length - 1) {
					distanceRight = approximationModel.getSingleColorSortedBy("Blue", middleBlue + 1).distance(pointToApproximate);
					if(distanceLeft <= distanceRight)
						rangeBlue = distanceLeft;
					else {
						rangeBlue = distanceRight;
						middleBlue += 1;
					}
				}else
					rangeBlue = distanceLeft;
			}
		}else
			rangeBlue = approximationModel.getSingleColorSortedBy("Blue", middleBlue).distance(pointToApproximate);
		
		double startRange = 0;
		ApproximationPoint nearestPoint = new ApproximationPoint();
		
		if(rangeRed < rangeGreen) {
			startRange = rangeRed;
			nearestPoint.setRed(approximationModel.getSingleColorSortedBy("Red", middleRed).getRed());
			nearestPoint.setGreen(approximationModel.getSingleColorSortedBy("Red", middleRed).getGreen());
			nearestPoint.setRed(approximationModel.getSingleColorSortedBy("Red", middleRed).getBlue());
		}else {
			startRange = rangeGreen;
			nearestPoint.setRed(approximationModel.getSingleColorSortedBy("Green", middleGreen).getRed());
			nearestPoint.setGreen(approximationModel.getSingleColorSortedBy("Green", middleGreen).getGreen());
			nearestPoint.setRed(approximationModel.getSingleColorSortedBy("Green", middleGreen).getBlue());
		}
		if(rangeBlue < startRange) {
			startRange = rangeBlue;
			nearestPoint.setRed(approximationModel.getSingleColorSortedBy("Blue", middleBlue).getRed());
			nearestPoint.setGreen(approximationModel.getSingleColorSortedBy("Blue", middleBlue).getGreen());
			nearestPoint.setRed(approximationModel.getSingleColorSortedBy("Blue", middleBlue).getBlue());
		}
		
		double shortestRange = startRange;
		double distRed = shortestRange;
		
		for(int i = 1; (middleRed + i) < approximationModel.getColorsSortedBy("Red").size(); i++) {
			if(approximationModel.getSingleColorSortedBy("Red", middleRed + i).getRed() <= (pointToApproximate.getRed() + distRed)) {
				double distance = approximationModel.getSingleColorSortedBy("Red", middleRed + i).distance(pointToApproximate);
				if(distance < distRed) {
					distRed = distance;
					int red = approximationModel.getSingleColorSortedBy("Red", middleRed + i).getRed();
					int green = approximationModel.getSingleColorSortedBy("Red", middleRed + i).getGreen();
					int blue = approximationModel.getSingleColorSortedBy("Red", middleRed + i).getBlue();
					nearestPoint = new ApproximationPoint(red, green, blue);
				}
			}else
				break;
		}
		
		for(int i = 0; (middleRed - i) >= 0; i++) {
			if(approximationModel.getSingleColorSortedBy("Red", middleRed - i).getRed() >= (pointToApproximate.getRed() - shortestRange)) {
				double distance = approximationModel.getSingleColorSortedBy("Red", middleRed - i).distance(pointToApproximate);
				if(distance < shortestRange) {
					shortestRange = distance;
					int red = approximationModel.getSingleColorSortedBy("Red", middleRed - i).getRed();
					int green = approximationModel.getSingleColorSortedBy("Red", middleRed - i).getGreen();
					int blue = approximationModel.getSingleColorSortedBy("Red", middleRed - i).getBlue();
					nearestPoint = new ApproximationPoint(red, green, blue);
				}
			}else
				break;
		}
		return nearestPoint;
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
	
	private static boolean searchedValueIsAvailableInQuantity() {
		return approximationModel.valueWasFound();
	}
	
	private static void setReducedPixels(Vector<ApproximationPoint> colorsSortedByNumber) {
		for(int i = 0; i < colorsSortedByNumber.size(); i++) {
			for(int j = 0; j < colorsSortedByNumber.elementAt(i).getColorOccurence().getIndices().size(); j++) {
				int index = colorsSortedByNumber.elementAt(i).getColorOccurence().getIndices().elementAt(j);
				int pixel = colorsSortedByNumber.elementAt(i).toRGB();
				PixelCoordinator.setSingleTargetPixel(index, pixel);
			}
		}
	}
}
