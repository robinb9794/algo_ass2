package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.SaveActionHandler;

public class SaveButton extends SuperButton{
	public SaveButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Save");
		setEnabled(false);
		setIconFromUrl("https://cdn2.iconfinder.com/data/icons/apple-classic/100/Apple_classic_10Icon_5px_grid-04-512.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SaveActionHandler.handle();
	}
}
