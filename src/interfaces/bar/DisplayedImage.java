package interfaces.bar;

import interfaces.GUIElement;
import models.LoadedImage;

public interface DisplayedImage extends GUIElement{
	void setDisplayedImageContainer(DisplayedImageContainer displayedImageContainer);
	void addImageIconFromLoadedImage(LoadedImage loadedImage);
	void enableImage();
	void blockImage();
}
