package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.ShearActionHandler;

public class ShearButton extends SuperButton{
	public ShearButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Shear");
		setEnabled(false);
		setIconFromUrl("http://cdn.onlinewebfonts.com/svg/img_524800.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ShearActionHandler.handle();
	}

}
