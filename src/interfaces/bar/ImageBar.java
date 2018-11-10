package interfaces.bar;

import interfaces.GUIElement;

public interface ImageBar extends GUIElement{
	void addImageField(GUIElement imageField);
	void reload();
	void enableImages();
	void blockImages();
}
