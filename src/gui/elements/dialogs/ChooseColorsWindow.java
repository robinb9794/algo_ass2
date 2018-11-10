package gui.elements.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class ChooseColorsWindow extends JDialog{
	public JButton firstColorPicker, secondColorPicker;

	public ChooseColorsWindow() {
		setTitle("Choose two colors:");
		setModal(false);
		setLayout(new GridLayout(2, 2));
		setPreferredSize(new Dimension(400, 100));
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
	}
	
	public void initLabelsAndButtons(Color firstColor, Color secondColor) {
		add(new JLabel(" First color:"));
		firstColorPicker = new JButton();
		firstColorPicker.setBackground(firstColor);
		add(firstColorPicker);
		
		add(new JLabel(" Second color:"));
		secondColorPicker = new JButton();
		secondColorPicker.setBackground(secondColor);
		add(secondColorPicker);
	}
}
