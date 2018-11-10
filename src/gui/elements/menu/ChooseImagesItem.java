package gui.elements.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import factories.FactoryProducer;
import factories.SuperFactory;
import interfaces.GUIElement;

public class ChooseImagesItem extends JMenuItem implements GUIElement, ActionListener{
	private SuperFactory guiElementFactory;
	
	private GUIElement imageChooser;
	
	public ChooseImagesItem() {
		this.guiElementFactory = FactoryProducer.getFactory("GUIElement");	
		this.imageChooser = guiElementFactory.getGUIElement("ImageChooser");
	}
	
	@Override
	public void init() {
		setText("Choose images");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		imageChooser.init();
	}
}
