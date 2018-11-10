package interfaces;

public interface View {
	void init();
	void addElement(String position, GUIElement guiElement);
	void setMenuBar(GUIElement menuBar);
	void packAndShow();
	void reorder();
	void reloadScreen();
}
