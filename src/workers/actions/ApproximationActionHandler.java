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
import models.LoadedImage;
import models.Mode;
import models.approximation.ApproximationPoint;
import models.approximation.ColorOccurence;
import models.approximation.ProjectionResult;
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
			resetApproximationScreen();
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
		addButtonListener();
		approximationWindow.setLocationRelativeTo(null);
		approximationWindow.pack();
		approximationWindow.setVisible(true);
	}
	
	private static void addButtonListener() {
		approximationWindow.approximateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(enteredPercentageIsValid()) {
					resetApproximationScreen();
					
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
			if(percentage > 0 && percentage <= 100)
				return true;
			return false;
		}catch(Exception e) {
			return false;
		}
	}
	
	private static void resetApproximationScreen() {
		LoadedImage imageToApproximate = viewModel.getSelectedImages().getFirst();
		PixelCoordinator.setTargetPixels(imageToApproximate.getGrabbedPixels());
		approximationWindow.screen.repaint();
	}
	
	private static void startApproximation(double reductionFactor) {
		int numberOfAllHistogramColors = approximationModel.getHistogramColors().size();
		int numberOfColorsToReplace = (int) Math.round(numberOfAllHistogramColors - (numberOfAllHistogramColors * reductionFactor));
		final int CUT = numberOfAllHistogramColors - numberOfColorsToReplace;
		Vector<ApproximationPoint> colorsSortedByNumber = approximationModel.getColorsSortedBy("Number");
		ApproximationPoint colorSortedByNumber, pointToApproximate, approximatedPoint;
		Color approximatedColors;
		for(int i = 0; i < numberOfColorsToReplace; i++) {
			colorSortedByNumber = colorsSortedByNumber.elementAt(i);
			int red = colorSortedByNumber.getRed();
			int green = colorSortedByNumber.getGreen();
			int blue = colorSortedByNumber.getBlue();
			pointToApproximate = new ApproximationPoint(red, green, blue);
			approximatedPoint = getApproximatedPoint(numberOfAllHistogramColors, CUT, pointToApproximate);
			approximatedColors = new Color(approximatedPoint.getRed(), approximatedPoint.getGreen(), approximatedPoint.getBlue());
			colorSortedByNumber.setColorValues(approximatedColors);
		}
		setReducedPixels(colorsSortedByNumber);
	}
	
	private static ApproximationPoint getApproximatedPoint(int numberOfAllHistogramColors, int CUT, ApproximationPoint pointToApproximate) {
		ProjectionResult redProjection = getRedRange(numberOfAllHistogramColors, CUT, pointToApproximate);
		ProjectionResult greenProjection = getGreenRange(numberOfAllHistogramColors, CUT, pointToApproximate);
		ProjectionResult blueProjection = getBlueRange(numberOfAllHistogramColors, CUT, pointToApproximate);
		
		ApproximationPoint nearestPoint = getNearestPoint(pointToApproximate, redProjection, greenProjection, blueProjection);
		return nearestPoint;		
	}
	
	private static ProjectionResult getRedRange(int numberOfAllHistogramColors, int CUT, ApproximationPoint pointToApproximate) {
		int redValueThatHasToBeApproximated = pointToApproximate.getRed();
		int middleRed = binarySearch("Red", redValueThatHasToBeApproximated, CUT, numberOfAllHistogramColors - 1);
		ApproximationPoint pointFromBinarySearch = approximationModel.getSingleColorSortedBy("Red", middleRed);
		
		double redRange;
		double distanceToTheLeft, distanceToTheRight;
		
		if(lastBinarySearchWasSuccessful()) {
			redRange = pointFromBinarySearch.distance(pointToApproximate);
		}else {
			if(redValueThatHasToBeApproximated < pointFromBinarySearch.getRed()) {
				distanceToTheLeft = approximationModel.getSingleColorSortedBy("Red", middleRed - 1).distance(pointToApproximate);
				distanceToTheRight = pointFromBinarySearch.distance(pointToApproximate);
				middleRed -= 1;
			}else {
				distanceToTheLeft = pointFromBinarySearch.distance(pointToApproximate);
				distanceToTheRight = approximationModel.getSingleColorSortedBy("Red", middleRed + 1).distance(pointToApproximate);
				middleRed += 1;
			}			
			redRange = distanceToTheLeft < distanceToTheRight ? distanceToTheLeft : distanceToTheRight;
		}
		return new ProjectionResult(middleRed, redRange, approximationModel.getSingleColorSortedBy("Red", middleRed));
	}
	
	private static ProjectionResult getGreenRange(int numberOfAllHistogramColors, int CUT, ApproximationPoint pointToApproximate) {
		int greenValueThatHasToBeApproximated = pointToApproximate.getGreen();
		int middleGreen = binarySearch("Green", greenValueThatHasToBeApproximated, CUT, numberOfAllHistogramColors - 1);
		ApproximationPoint pointFromBinarySearch = approximationModel.getSingleColorSortedBy("Green", middleGreen);
		
		double greenRange;
		double distanceToTheLeft, distanceToTheRight;
		
		if(lastBinarySearchWasSuccessful()) {
			greenRange = pointFromBinarySearch.distance(pointToApproximate);
		}else {
			if(greenValueThatHasToBeApproximated < pointFromBinarySearch.getGreen()) {
				distanceToTheLeft = approximationModel.getSingleColorSortedBy("Green", middleGreen - 1).distance(pointToApproximate);
				distanceToTheRight = pointFromBinarySearch.distance(pointToApproximate);
				middleGreen -= 1;
			}else {
				distanceToTheLeft = pointFromBinarySearch.distance(pointToApproximate);
				distanceToTheRight = approximationModel.getSingleColorSortedBy("Green", middleGreen + 1).distance(pointToApproximate);
				middleGreen += 1;
			}			
			greenRange = distanceToTheLeft < distanceToTheRight ? distanceToTheLeft : distanceToTheRight;
		}
		return new ProjectionResult(middleGreen, greenRange, approximationModel.getSingleColorSortedBy("Green", middleGreen));
	}
	
	private static ProjectionResult getBlueRange(int numberOfAllHistogramColors, int CUT, ApproximationPoint pointToApproximate) {
		int blueValueThatHasToBeApproximated = pointToApproximate.getBlue();
		int middleBlue = binarySearch("Blue", blueValueThatHasToBeApproximated, CUT, numberOfAllHistogramColors - 1);
		ApproximationPoint pointFromBinarySearch = approximationModel.getSingleColorSortedBy("Blue", middleBlue);
		
		double blueRange;
		double distanceToTheLeft, distanceToTheRight;
		
		if(lastBinarySearchWasSuccessful()) {
			blueRange = pointFromBinarySearch.distance(pointToApproximate);
		}else {
			if(blueValueThatHasToBeApproximated < pointFromBinarySearch.getBlue()) {
				distanceToTheLeft = approximationModel.getSingleColorSortedBy("Blue", middleBlue - 1).distance(pointToApproximate);
				distanceToTheRight = pointFromBinarySearch.distance(pointToApproximate);
				middleBlue -= 1;
			}else {
				distanceToTheLeft = pointFromBinarySearch.distance(pointToApproximate);
				distanceToTheRight = approximationModel.getSingleColorSortedBy("Blue", middleBlue + 1).distance(pointToApproximate);
				middleBlue += 1;
			}			
			blueRange = distanceToTheLeft < distanceToTheRight ? distanceToTheLeft : distanceToTheRight;
		}
		return new ProjectionResult(middleBlue, blueRange, approximationModel.getSingleColorSortedBy("Blue", middleBlue));
	}
	
	private static int binarySearch(String color, int searched, int leftEdge, int rightEdge) {
		int tmpLeft = leftEdge;
		int tmpRight = rightEdge;
		int middle = 0;
		while(tmpLeft <= tmpRight) {
			middle = (tmpLeft + tmpRight) / 2;
			int result = 0; 
			switch(color) {
			case "Red":
				int red = approximationModel.getSingleColorSortedBy("Red", middle).getRed();
				result = Integer.compare(red, searched);
				break;
			case "Green":
				int green = approximationModel.getSingleColorSortedBy("Green", middle).getGreen();
				result = Integer.compare(green, searched);
				break;
			case "Blue":
				int blue = approximationModel.getSingleColorSortedBy("Blue", middle).getBlue();
				result = Integer.compare(blue, searched);
				break;
			}
			if(result == 0) {
				approximationModel.setValueWasFound(true);
				return middle;
			}else if(result < 0)
				tmpLeft = middle + 1;
			else
				tmpRight = middle - 1;
		}
		approximationModel.setValueWasFound(false);
		return middle;
	}
	
	private static boolean lastBinarySearchWasSuccessful() {
		return approximationModel.valueWasFound();
	}
	
	private static ApproximationPoint getNearestPoint(ApproximationPoint pointToApproximate, ProjectionResult redProjection, ProjectionResult greenProjection, ProjectionResult blueProjection) {
		double shortestRange;
		int nearestRed, nearestGreen, nearestBlue;
		
		if(redProjection.getRange() < greenProjection.getRange()) {
			shortestRange = redProjection.getRange();
			nearestRed = redProjection.getApproximationPoint().getRed();
			nearestGreen = redProjection.getApproximationPoint().getGreen();
			nearestBlue = redProjection.getApproximationPoint().getBlue();
		}else {
			shortestRange = greenProjection.getRange();
			nearestRed = greenProjection.getApproximationPoint().getRed();
			nearestGreen = greenProjection.getApproximationPoint().getGreen();
			nearestBlue = greenProjection.getApproximationPoint().getBlue();
		}
		if(blueProjection.getRange() < shortestRange) {
			shortestRange = blueProjection.getRange();
			nearestRed = blueProjection.getApproximationPoint().getRed();
			nearestGreen = blueProjection.getApproximationPoint().getGreen();
			nearestBlue = blueProjection.getApproximationPoint().getBlue();
		}		
		
		ApproximationPoint closerPoint;
		double tmpDistance;
		
		int middleRed = redProjection.getMiddle();
		
		for(int i = 1; (middleRed + i) < approximationModel.getColorsSortedBy("Red").size(); i++) {
			if(approximationModel.getSingleColorSortedBy("Red", middleRed + i).getRed() <= (pointToApproximate.getRed() + shortestRange)) {
				closerPoint = approximationModel.getSingleColorSortedBy("Red", middleRed + i);
				tmpDistance = closerPoint.distance(pointToApproximate);
				if(tmpDistance < shortestRange) {
					shortestRange = tmpDistance;
					nearestRed = closerPoint.getRed();
					nearestGreen = closerPoint.getGreen();
					nearestBlue = closerPoint.getBlue();
				}
			}else
				break;
		}
		
		for(int i = 0; (middleRed - i) >= 0; i++) {
			if(approximationModel.getSingleColorSortedBy("Red", i).getRed() >= (pointToApproximate.getRed() - shortestRange)) {
				closerPoint = approximationModel.getSingleColorSortedBy("Red", middleRed - i);
				tmpDistance = closerPoint.distance(pointToApproximate);
				if(tmpDistance < shortestRange) {
					shortestRange = tmpDistance;
					nearestRed = closerPoint.getRed();
					nearestGreen = closerPoint.getGreen();
					nearestBlue = closerPoint.getBlue();
				}
			}else
				break;
		}
		
		return new ApproximationPoint(nearestRed, nearestGreen, nearestBlue);
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
