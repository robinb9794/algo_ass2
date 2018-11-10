package gui.elements;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

import models.ViewModel;

public class ApproximationScreen extends JComponent{
	private ViewModel viewModel;
	
	private Image displayedImage;
	
	public ApproximationScreen(ViewModel viewModel) {
		this.viewModel = viewModel;
		setPreferredSize(new Dimension(viewModel.getScreenWidth(), viewModel.getScreenHeight()));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(viewModel.getMemoryImageSource() != null) {
			viewModel.getMemoryImageSource().newPixels();
			this.displayedImage = createImage(viewModel.getMemoryImageSource());
			g.drawImage(displayedImage, 10, 10, getWidth(), getHeight(), null);
		}
	}
}
