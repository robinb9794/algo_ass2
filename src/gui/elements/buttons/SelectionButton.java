package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.SuperUserInteractionHandler;
import workers.actions.SelectionActionHandler;

public class SelectionButton extends SuperButton{	
	public SelectionButton() {
		super();
	}

	@Override
	public void init() {
		setToolTipText("Area selection");
		setIconFromUrl("http://cdn.onlinewebfonts.com/svg/img_520210.png");	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SelectionActionHandler.handle();
	}
}
