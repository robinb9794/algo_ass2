package workers.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gui.elements.dialogs.RotateWindow;
import models.Mode;
import models.MorphValues;
import models.math.Matrix;
import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class RotateActionHandler extends SuperUserInteractionHandler{
	private static RotateWindow rotateWindow;
	
	public static void handle() {
		setCurrentMode(Mode.MORPHING);
		resetScreenListeners();
		initDialog();
	}
	
	private static void initDialog() {
		rotateWindow = new RotateWindow();
		addWindowListener();
		addListenersToButtons();
		rotateWindow.pack();
		rotateWindow.setLocationRelativeTo(null);
		rotateWindow.setVisible(true);
	}
	
	private static void addWindowListener() {
		rotateWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				rotateWindow.dispose();
				enableSingleButton("Save");
			}
		});
	}
	
	private static void addListenersToButtons() {
		rotateWindow.rotateClockwise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rotateAlpha = MorphValues.ROTATE_ALPHA;
				PixelCoordinator.setTargetPixels(viewModel.getSourcePixels());
				setRotationMatrix(rotateAlpha, true);
				morph();
			}
		});
		
		rotateWindow.rotateCounterClockwise.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int rotateAlpha = MorphValues.ROTATE_ALPHA;
				PixelCoordinator.setTargetPixels(viewModel.getSourcePixels());
				setRotationMatrix(rotateAlpha, false);
				morph();
			}
		});
	}
	
	private static void setRotationMatrix(int rotateAlpha, boolean shouldRotateClockwise) {
		int centerX = (int) viewModel.getSelectionCenter().getX();
		int centerY = (int) viewModel.getSelectionCenter().getY();
		Matrix shiftMatrix = Matrix.translate(-centerX, -centerY);
		Matrix morphMatrix = viewModel.getMorphMatrix();
		morphMatrix = Matrix.multiply(shiftMatrix, morphMatrix);
		Matrix rotationMatrix = shouldRotateClockwise ? Matrix.rotateClockwise(rotateAlpha) : Matrix.rotateCounterClockwise(rotateAlpha); 
		morphMatrix = Matrix.multiply(rotationMatrix, morphMatrix);
		shiftMatrix = Matrix.translate(centerX, centerY);
		morphMatrix = Matrix.multiply(shiftMatrix, morphMatrix);
		viewModel.setMorphMatrix(morphMatrix);
	}
}
