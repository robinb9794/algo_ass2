package gui.elements.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import interfaces.GUIElement;
import workers.actions.ApproximationActionHandler;

public class ApproximationItem extends JMenuItem implements GUIElement, ActionListener{
	@Override
	public void init() {
		setText("Approximation");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ApproximationActionHandler.handle();
	}
}