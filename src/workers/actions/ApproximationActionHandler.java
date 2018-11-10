package workers.actions;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.elements.dialogs.ApproximationWindow;
import models.Mode;
import workers.SuperUserInteractionHandler;

public class ApproximationActionHandler extends SuperUserInteractionHandler{
	private static ApproximationWindow approximationWindow;
	
	public static void handle() {
		if(screenIsLoaded() && userHasSelectedAtLeastOneImage()) {
			CreateHistogramActionHandler.handle();
			setCurrentMode(Mode.APPROXIMATION);
			resetScreenListeners();
			initDialog();
		}else
			showErrorDialog("First select at least one image.");
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
				int approximationValue = approximationWindow.slider.getValue();
				findNewColors(approximationValue);
				approximationWindow.screen.repaint();
			}			
		});
	}
	
	private static void findNewColors(int approximationValue) {
		
	}
}
