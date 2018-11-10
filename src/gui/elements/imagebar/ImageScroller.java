package gui.elements.imagebar;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import interfaces.GUIElement;
import models.ViewModel;

public class ImageScroller extends JScrollPane implements GUIElement{
	private ViewModel viewModel;
	
	public ImageScroller(ViewModel viewModel) {
		super((JComponent) viewModel.getImageBar());
		this.viewModel = viewModel;
	}
	
	@Override
	public void init() {
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		setBounds(0, 0, viewModel.getGUIWidth(), 50);
	}
}
