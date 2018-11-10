package factories;

import gui.elements.buttons.*;
import interfaces.GUIElement;
import models.ViewModel;

public class ButtonFactory extends SuperFactory{
	private ViewModel viewModel;
	
	public ButtonFactory(ViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	@Override
	public GUIElement getGUIElement(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GUIElement getButtonField(String type) {
		switch(type) {		
		case "Selection":
			return new SelectionButton();
		case "Reset":
			return new ResetButton();
		case "Fade":
			return new FadeButton();
		case "Translate":
			return new TranslateButton();
		case "Rotate":
			return new RotateButton();
		case "Lens":
			return new LensButton();
		case "Scale":
			return new ScaleButton();
		case "Shear":
			return new ShearButton();
		case "Lines":
			return new LinesButton();
		case "Circles":
			return new CirclesButton();
		case "Save":
			return new SaveButton();
		case "Stop":
			return new StopButton();
		}
		return null;
	}

}
