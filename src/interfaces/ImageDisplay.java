package interfaces;

import java.awt.Cursor;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public interface ImageDisplay extends GUIElement{
	int getScreenWidth();
	int getScreenHeight();
	void addCustomMouseMotionListener(MouseMotionListener mouseMotionLilstener);
	void addCustomMouseListener(MouseListener mouseListener);
	void setCustomCursor(Cursor cursor);
	void resetListeners();
}
