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
import models.approximation.HistogramColor;
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
				String[] values = line.split("_");
				int red = Integer.parseInt(values[0]);
				int green = Integer.parseInt(values[1]);
				int blue = Integer.parseInt(values[2]);
				int number = Integer.parseInt(values[3]);
				HistogramColor histogramColor = new HistogramColor(red, green, blue, number);
				approximationModel.addHistogramColor(histogramColor);
			}
			fileReader.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private static void sortHistogramColors() {
		Comparator<HistogramColor> numberComparator = CompareManager.getNumberComparator();
		Vector<HistogramColor> colorsSortedByNumber = approximationModel.getHistogramColors();
		Collections.sort(colorsSortedByNumber, numberComparator);
		approximationModel.setColorsSortedByNumber(colorsSortedByNumber);
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
				int percent = approximationWindow.slider.getValue();
				startApproximation(percent);
				approximationWindow.screen.repaint();
			}			
		});
	}
	
	private static void startApproximation(int percent) {
		int numberOfColorsToReplace = approximationModel.getHistogramColors().size() / 100 * percent;
		HistogramColor[] colorsToBeReplaced = new HistogramColor[numberOfColorsToReplace];
		Vector<HistogramColor> colorsSortedByNumber = approximationModel.getColorsSortedBy("Number");
		for(int i = 0; i < numberOfColorsToReplace; i++) {
			colorsToBeReplaced[i] = colorsSortedByNumber.get(i);
		}
		findNewPixels(colorsToBeReplaced);
		System.out.println("jo");
	}
	
	private static void findNewPixels(HistogramColor[] colorsToBeReplaced) {
		Vector<HistogramColor> colorsSortedByNumber = approximationModel.getColorsSortedBy("Number");
		final int LIMIT = colorsSortedByNumber.size() - colorsToBeReplaced.length;
		for(int i = 0; i < colorsToBeReplaced.length; i++) {
			HistogramColor colorToBeReplaced = colorsToBeReplaced[i];
			final int OLD_RED = colorToBeReplaced.getRed();
			final int OLD_GREEN = colorToBeReplaced.getGreen();
			final int OLD_BLUE = colorToBeReplaced.getBlue();
			for(int j = 0; j < LIMIT; j++) {
				
			}
		}
	}
}
