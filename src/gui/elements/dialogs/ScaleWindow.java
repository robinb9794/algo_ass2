package gui.elements.dialogs;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;

public class ScaleWindow extends JDialog{
	public JButton scaleBigger, scaleSmaller;
	
	public ScaleWindow() {
		setTitle("Scale");
		setModal(true);
		setLayout(new FlowLayout());
		scaleSmaller = new JButton("Smaller");
		add(scaleSmaller);
		scaleBigger = new JButton("Bigger");
		add(scaleBigger);
		setPreferredSize(new Dimension(200, 80));
	}
}
