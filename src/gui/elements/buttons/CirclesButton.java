package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.CirclesActionHandler;

public class CirclesButton extends SuperButton{
	public CirclesButton() {
		super();
	}

	@Override
	public void init() {
		setToolTipText("Draw circles");
		setEnabled(true);
		setIconFromUrl("https://cdn4.iconfinder.com/data/icons/religion-6/53/unification-one-three-circles-512.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CirclesActionHandler.handle();
	}
}