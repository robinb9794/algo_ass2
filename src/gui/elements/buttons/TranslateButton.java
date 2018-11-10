package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.TranslateActionHandler;

public class TranslateButton extends SuperButton{
	public TranslateButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Translate");
		setEnabled(false);
		setIconFromUrl("http://icons.iconarchive.com/icons/iconsmind/outline/256/Cursor-Move-icon.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TranslateActionHandler.handle();
	}	
}