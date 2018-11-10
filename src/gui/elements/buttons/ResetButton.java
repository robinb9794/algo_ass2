package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.ResetActionHandler;

public class ResetButton extends SuperButton{	
	public ResetButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Reset");
		setEnabled(false);
		setIconFromUrl("https://image.flaticon.com/icons/png/512/51/51032.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ResetActionHandler.handle();
	}
}
