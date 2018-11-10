package workers.actions;

import java.util.Map.Entry;

import interfaces.buttons.ButtonField;
import models.LoadedImage;
import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class StopActionHandler extends SuperUserInteractionHandler{
	public static void handle() {
		isFading = false;
		resetScreenListeners();
		enableAndDisableButtons();
		resetDisplayedImage();
	}
	
	private static void enableAndDisableButtons() {
		for(Entry<ButtonField, String> entry : viewModel.getButtons().entrySet()) {
			ButtonField button = entry.getKey();
			String value = entry.getValue();
			switch(value) {
			case "Selection":
				button.enableButton(true);
				break;
			case "Fade":
				button.enableButton(true);
				break;
			case "Lens":
				button.enableButton(true);
				break;
			case "Lines":
				button.enableButton(true);
				break;
			case "Circles":
				button.enableButton(true);
				break;
			default:
				button.enableButton(false);
			}
		}
	}
	
	private static void resetDisplayedImage() {
		LoadedImage displayedImage = viewModel.getSelectedImages().getLast();
		PixelCoordinator.setSourcePixels(displayedImage.getGrabbedPixels());
		PixelCoordinator.setTargetPixels(displayedImage.getGrabbedPixels());
		gui.reloadScreen();
	}
}
