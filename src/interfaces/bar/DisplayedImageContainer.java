package interfaces.bar;

import java.awt.Color;

import interfaces.GUIElement;
import models.LoadedImage;

public interface DisplayedImageContainer extends GUIElement{
	void passImageIconFromLoadedImageToClickableImage(LoadedImage loadedImage);
	void setContainerBackground(Color color);
	void enableDisplayedImage();
	void blockDisplayedImage();
}
