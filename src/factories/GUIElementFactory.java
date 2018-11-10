package factories;

import gui.elements.LoadingWindow;
import gui.elements.Screen;
import gui.elements.containers.ButtonContainer;
import gui.elements.containers.ImageContainer;
import gui.elements.containers.MenuItemContainer;
import gui.elements.imagebar.ClickableImage;
import gui.elements.imagebar.ImageField;
import gui.elements.imagebar.ImageScroller;
import gui.elements.menu.ApproximationItem;
import gui.elements.menu.ChooseImagesItem;
import gui.elements.menu.CreateHistogramItem;
import gui.elements.menu.ImageChooser;
import gui.elements.menu.Menu;
import interfaces.GUIElement;
import interfaces.View;
import models.ViewModel;

public class GUIElementFactory extends SuperFactory{
	private View gui;
	private ViewModel viewModel;
	
	public GUIElementFactory(View gui, ViewModel viewModel) {
		this.gui = gui;
		this.viewModel = viewModel;
	}
	
	@Override
	public GUIElement getGUIElement(String type) {
		switch(type) {
		case "Screen":
			return new Screen(viewModel);
		case "Menu":
			return new Menu();
		case "MenuItemContainer":
			return new MenuItemContainer();
		case "ChooseImages":
			return new ChooseImagesItem();
		case "CreateHistogram":
			return new CreateHistogramItem();
		case "ApproximationItem":
			return new ApproximationItem();
		case "ImageChooser":
			return new ImageChooser(viewModel);
		case "ImageContainer":
			return new ImageContainer();
		case "ImageScroller":
			return new ImageScroller(viewModel);
		case "LoadingWindow":
			return new LoadingWindow();
		case "ImageField":
			return new ImageField();
		case "ClickableImage":
			return new ClickableImage(gui, viewModel);
		case "ButtonContainer":
			return new ButtonContainer();
		}
		return null;
	}

	@Override
	public GUIElement getButtonField(String type) {
		// TODO Auto-generated method stub
		return null;
	}
}