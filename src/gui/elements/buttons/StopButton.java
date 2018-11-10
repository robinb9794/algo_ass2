package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.StopActionHandler;

public class StopButton extends SuperButton{
	public StopButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Stop");
		setEnabled(false);
		setIconFromUrl("https://cdn2.iconfinder.com/data/icons/general-ui-icons/800/pause46-512.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StopActionHandler.handle();
	}

}
