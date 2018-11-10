package gui.elements.buttons;

import java.awt.event.ActionEvent;

import workers.actions.LinesActionHandler;

public class LinesButton extends SuperButton{
	public LinesButton() {
		super();
	}
	
	@Override
	public void init() {
		setToolTipText("Draw lines");
		setEnabled(true);
		setIconFromUrl("https://cdn3.iconfinder.com/data/icons/math-1/96/icons__Angles_and_Parallel_Lines-512.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LinesActionHandler.handle();
	}
}