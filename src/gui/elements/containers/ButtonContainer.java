package gui.elements.containers;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import factories.FactoryProducer;
import factories.SuperFactory;
import interfaces.GUIElement;
import interfaces.buttons.ButtonSelection;

public class ButtonContainer extends JPanel implements ButtonSelection {	
	@Override
	public void init() {
		setLayout(new GridLayout(6, 2));
	}
	
	@Override
	public void addButton(GUIElement button) {
		add((JButton) button);
	}
}
