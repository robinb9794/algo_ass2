package gui.elements.imagebar;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import factories.FactoryProducer;
import factories.SuperFactory;
import interfaces.bar.DisplayedImage;
import interfaces.bar.DisplayedImageContainer;
import models.LoadedImage;

public class ImageField extends JPanel implements DisplayedImageContainer{	
	private SuperFactory guiElementFactory;
	
	private DisplayedImage clickableImage;
	private JPanel background = new JPanel();
	
	public ImageField() {
		this.guiElementFactory = FactoryProducer.getFactory("GUIElement");
		this.clickableImage = (DisplayedImage) this.guiElementFactory.getGUIElement("ClickableImage");
		this.clickableImage.init();
		this.clickableImage.setDisplayedImageContainer(this);
	}
	
	@Override
	public void init() {
		setOpaque(true);
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));	
		setBorder(BorderFactory.createEmptyBorder(3,5,12,0));
		add((JLabel) clickableImage);
	}

	@Override
	public void passImageIconFromLoadedImageToClickableImage(LoadedImage loadedImage) {
		clickableImage.addImageIconFromLoadedImage(loadedImage);
	}
	
	@Override
	public void setContainerBackground(Color color) {
		setBackground(color);
	}
	
	@Override
	public void enableDisplayedImage() {
		clickableImage.enableImage();
	}

	@Override
	public void blockDisplayedImage() {
		clickableImage.blockImage();
	}
}