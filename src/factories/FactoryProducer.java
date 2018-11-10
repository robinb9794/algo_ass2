package factories;

import workers.GUIManager;

public class FactoryProducer extends GUIManager{
	public static SuperFactory getFactory(String type) {
		switch(type) {
		case "GUIElement":
			return new GUIElementFactory(gui, viewModel);
		case "Button":
			return new ButtonFactory(viewModel);
		}
		return null;		
	}
}
