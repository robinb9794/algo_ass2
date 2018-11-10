package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.SuperUserInteractionHandler;
import workers.actions.FadeActionHandler;

public class FadeButton extends SuperButton {	
	public FadeButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Fade");
		setIconFromUrl("https://static.thenounproject.com/png/151942-200.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		FadeActionHandler.handle();
	}
}
