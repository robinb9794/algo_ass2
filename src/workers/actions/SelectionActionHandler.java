package workers.actions;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import workers.PixelCoordinator;
import workers.SuperUserInteractionHandler;

public class SelectionActionHandler extends SuperUserInteractionHandler{	
	public static void handle() {
		if(userHasSelectedTwoImages()) {
			resetScreenListeners();
			addMouseMotionListenerToScreen();
			addMouseListenerToScreen();
		}
		else
			showErrorDialog("Please select exactly two images.");
	}
	
	private static boolean userHasSelectedTwoImages() {
		return viewModel.getSelectedImages().size() == 2;
	}	

	private static void addMouseMotionListenerToScreen() {
		viewModel.getScreen().addCustomMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				viewModel.setSelectionEndPoint(e.getX(), e.getY());
				PixelCoordinator.setTargetPixels(viewModel.getSourcePixels());
				select();
				gui.reloadScreen();
			}			
		});
	}
	
	private static void addMouseListenerToScreen() {
		viewModel.getScreen().addCustomMouseListener(new MouseAdapter() {	
			@Override
			public void mouseEntered(MouseEvent e) {
				viewModel.getScreen().setCustomCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				viewModel.setSelectionStartPoint(e.getX(), e.getY());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				viewModel.setSelectionEndPoint(e.getX(), e.getY());
				viewModel.setSelectionCenter();
				enableOrDisableButtonsMorphButtons(true);
				enableSingleButton("Reset");
				blockImageBar();
			}			
		});
	}
	
	private static void select() {
		int selectionStartX = getSelectionStartX();
		int selectionEndX= getSelectionEndX();
		int selectionStartY = getSelectionStartY();
		int selectionEndY = getSelectionEndY();
		for(int i = selectionStartX; i <= selectionEndX; i++) {
			for(int j = selectionStartY; j <= selectionEndY; j++) {
				if(pixelIsOnSelectionLine(i, j, selectionStartX, selectionEndX, selectionStartY, selectionEndY)) {
					int index = PixelCoordinator.getPixelIndex(i, j);
					int selectionColor = getSelectionColor();
					PixelCoordinator.setSingleTargetPixel(index, selectionColor);
				}							
			}
		}
	}
	
	private static boolean pixelIsOnSelectionLine(int i, int j, int startX, int endX, int startY, int endY) {
		return i == startX || i == endX || j == startY || j == endY ;
	}
	
	private static int getSelectionColor() {
		return 255 << 24 | 255 << 16 | 0 << 8 | 78;
	}
}
