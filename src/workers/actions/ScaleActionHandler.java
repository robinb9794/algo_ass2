package workers.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gui.elements.dialogs.ScaleWindow;
import models.Mode;
import models.MorphValues;
import models.math.Matrix;
import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class ScaleActionHandler extends SuperUserInteractionHandler {
	private static ScaleWindow scaleWindow;
	
	public static void handle() {
		setCurrentMode(Mode.MORPHING);
		resetScreenListeners();
		initDialog();
	}
	
	private static void initDialog() {
		scaleWindow = new ScaleWindow();
		addWindowListener();
		addListenersToButtons();
		scaleWindow.pack();
		scaleWindow.setLocationRelativeTo(null);
		scaleWindow.setVisible(true);
	}
	
	private static void addWindowListener() {
		scaleWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				enableSingleButton("Save");
				scaleWindow.dispose();
			}
		});
	}
	
	private static void addListenersToButtons() {
		scaleWindow.scaleBigger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double scale = MorphValues.SCALE_BIGGER;
				PixelCoordinator.setTargetPixels(viewModel.getSourcePixels());
				setScaleMatrix(scale);
				morph();
			}
		});
		
		scaleWindow.scaleSmaller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double scale = MorphValues.SCALE_SMALLER;
				PixelCoordinator.setTargetPixels(viewModel.getSourcePixels());
				setScaleMatrix(scale);
				morph();
			}
		});
	}
	
	private static void setScaleMatrix(double scale) {
		int centerX = (int) viewModel.getSelectionCenter().getX();
		int centerY = (int) viewModel.getSelectionCenter().getY();
		Matrix shiftMatrix = Matrix.translate(-centerX, -centerY);
		Matrix morphMatrix = viewModel.getMorphMatrix();
		morphMatrix = Matrix.multiply(shiftMatrix, morphMatrix);
		Matrix scaleMatrix = Matrix.scale(scale);
		morphMatrix = Matrix.multiply(scaleMatrix, morphMatrix);
		shiftMatrix = Matrix.translate(centerX, centerY);
		morphMatrix = Matrix.multiply(shiftMatrix, morphMatrix);
		viewModel.setMorphMatrix(morphMatrix);
	}
}
