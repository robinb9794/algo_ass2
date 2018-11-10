package gui.elements.containers;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import factories.FactoryProducer;
import factories.SuperFactory;
import interfaces.GUIElement;

public class MenuItemContainer extends JMenu implements GUIElement{
	private SuperFactory guiElementFactory;
	
	private GUIElement chooseImagesItem;
	private GUIElement createHistogramItem;
	private GUIElement approximationItem;
	
	public MenuItemContainer() {
		this.guiElementFactory = FactoryProducer.getFactory("GUIElement");
	}
	
	@Override
	public void init() {
		setText("Actions");
		
		this.chooseImagesItem = guiElementFactory.getGUIElement("ChooseImages");
		chooseImagesItem.init();
		this.createHistogramItem = guiElementFactory.getGUIElement("CreateHistogram");
		createHistogramItem.init();
		this.approximationItem = guiElementFactory.getGUIElement("ApproximationItem");
		approximationItem.init();
		
		add((JMenuItem) chooseImagesItem);
		add((JMenuItem) createHistogramItem);
		addSeparator();
		add((JMenuItem) approximationItem);
	}
}
