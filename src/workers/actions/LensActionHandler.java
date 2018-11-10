package workers.actions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import models.LoadedImage;
import models.Mode;
import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class LensActionHandler extends SuperUserInteractionHandler {
	public static void handle() {
		if(userHasSelectedImagesToLens()) {
			setCurrentMode(Mode.LENSING);
			disableAllButtonsExceptOf("Reset");
			resetScreenListeners();
			addMouseMotionListenerToScreen();
		}else
			showErrorDialog("Please select exactly two images.");
	}
	
	private static boolean userHasSelectedImagesToLens() {
		return viewModel.getSelectedImages().size() == 2;
	}
	
	private static void addMouseMotionListenerToScreen() {
		viewModel.getScreen().addCustomMouseMotionListener(new MouseMotionAdapter() {			
			@Override
			public void mouseMoved(MouseEvent e) {
				int mouseX = e.getX();
				int mouseY = e.getY();
				lens(mouseX, mouseY);
			}			
		});
	}
	
	private static void lens(int mouseX, int mouseY) {
		PixelCoordinator.setSourcePixels(viewModel.getSelectedImages().getFirst().getGrabbedPixels());
		LoadedImage imageToLensThrough = viewModel.getSelectedImages().getLast();
		for(int i = 0; i < viewModel.getScreenWidth(); i++) {
			for(int j = 0; j < viewModel.getScreenHeight(); j++) {
				int index = PixelCoordinator.getPixelIndex(i, j);
				double dx = mouseX - i;
				double dy = mouseY - j;
				double lensValue = (Math.pow(dx, 2) + Math.pow(dy,  2)) / 100;
				lensValue = lensValue > 100 ? 100 : lensValue;
				int backgroundPixel = PixelCoordinator.getSinglePixelFromImage(imageToLensThrough, index);
				int sourcePixel = PixelCoordinator.getSingleSourcePixel(index);
				int lensPixel = PixelCoordinator.colorShuffle(sourcePixel, backgroundPixel, (int) lensValue);
				PixelCoordinator.setSingleTargetPixel(index, lensPixel);
			}
		}
		gui.reloadScreen();
	}
}
