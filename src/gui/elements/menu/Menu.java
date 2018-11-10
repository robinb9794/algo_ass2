package gui.elements.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import factories.FactoryProducer;
import factories.SuperFactory;
import interfaces.GUIElement;

public class Menu extends JMenuBar implements GUIElement{
	private SuperFactory guiElementFactory;
	
	private GUIElement menuItemContainer;
	
	public Menu() {
		this.guiElementFactory = FactoryProducer.getFactory("GUIElement");		
	}
	
	@Override
	public void init() {
		this.menuItemContainer = guiElementFactory.getGUIElement("MenuItemContainer");
		menuItemContainer.init();		
		add((JMenu) menuItemContainer);
	}
}
