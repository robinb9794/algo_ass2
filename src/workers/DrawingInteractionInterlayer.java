package workers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import gui.elements.dialogs.ChooseColorsWindow;

public class DrawingInteractionInterlayer extends SuperUserInteractionHandler{
	protected static ChooseColorsWindow colorWindow;
	
	public static void closeColorWindow() {
		if(colorWindow != null)
			colorWindow.dispose();
	}	

	protected static boolean colorWindowIsAlreadyInitialized() {
		return colorWindow != null;
	}

	protected static void initColorWindow() {
		colorWindow = new ChooseColorsWindow();
		colorWindow.initLabelsAndButtons(viewModel.getFirstColor(), viewModel.getSecondColor());
		addChangeListenerToColorPickers();
		colorWindow.setLocationRelativeTo(null);
		colorWindow.pack();
		colorWindow.setVisible(true);
	}

	protected static void addChangeListenerToColorPickers() {
		colorWindow.firstColorPicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color firstColor = JColorChooser.showDialog(null, "Pick first color:", viewModel.getFirstColor());
				if (firstColor == null)
					firstColor = viewModel.getFirstColor();
				viewModel.setFirstColor(firstColor);
				colorWindow.firstColorPicker.setBackground(firstColor);
			}
		});

		colorWindow.secondColorPicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color secondColor = JColorChooser.showDialog(null, "Pick second color:", viewModel.getSecondColor());
				if (secondColor == null)
					secondColor = viewModel.getSecondColor();
				viewModel.setSecondColor(secondColor);
				colorWindow.secondColorPicker.setBackground(secondColor);
			}
		});
	}
	
	
}
