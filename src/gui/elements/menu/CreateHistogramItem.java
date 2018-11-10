package gui.elements.menu;

import javax.swing.JMenuItem;

import factories.FactoryProducer;
import factories.SuperFactory;
import interfaces.GUIElement;
import workers.actions.CreateHistogramActionHandler;

public class CreateHistogramItem extends JMenuItem implements GUIElement{
	private SuperFactory guiElementFactory;
		
	public CreateHistogramItem() {
		this.guiElementFactory = FactoryProducer.getFactory("GUIElement");	
	}
	
	@Override
	public void init() {
		setText("Create histogram");
		addActionListener(e -> CreateHistogramActionHandler.handle());
	}
}
