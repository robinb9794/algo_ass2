package gui.elements.dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;

public class RotateWindow extends JDialog{
	public JButton rotateClockwise, rotateCounterClockwise;
	
	public RotateWindow() {
		setTitle("Rotate");
		setModal(true);
		setLayout(new FlowLayout());
		rotateClockwise = new JButton("Clockwise");
		add(rotateClockwise);
		rotateCounterClockwise = new JButton("Counter clockwise");
		add(rotateCounterClockwise);
		setPreferredSize(new Dimension(300, 80));
	}
}
