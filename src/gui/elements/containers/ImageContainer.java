package gui.elements.containers;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import interfaces.GUIElement;
import interfaces.bar.DisplayedImageContainer;
import interfaces.bar.ImageBar;

public class ImageContainer extends JPanel implements ImageBar{	
	private List<DisplayedImageContainer> imageFields;
	
	@Override
	public void init() {
		imageFields = new ArrayList<DisplayedImageContainer>();
		setBackground(Color.WHITE);
		setLayout(new FlowLayout());
	}

	@Override
	public void addImageField(GUIElement imageField) {
		imageFields.add((DisplayedImageContainer) imageField);
		add((JPanel) imageField);
	}
	
	@Override
	public void reload() {
		validate();
	}
	
	@Override
	public void enableImages() {
		for(DisplayedImageContainer imageField : imageFields) {
			imageField.enableDisplayedImage();
		}
	}	

	@Override
	public void blockImages() {
		for(DisplayedImageContainer imageField : imageFields) {
			imageField.blockDisplayedImage();
		}
	}
}