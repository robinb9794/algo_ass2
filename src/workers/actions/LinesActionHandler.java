package workers.actions;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import models.Mode;
import workers.DrawingInteractionInterlayer;
import workers.PixelCoordinator;

public class LinesActionHandler extends DrawingInteractionInterlayer {

	public static void handle() {
		if (userHasSelectedAtLeastOneImage()) {
			if (colorWindowIsAlreadyInitialized())
				colorWindow.setVisible(true);
			else
				initColorWindow();
			if (userIsMorphing())
				PixelCoordinator.setSourceAndTargetPixels(viewModel.getTargetPixels());
			setCurrentMode(Mode.DRAWING);
			resetScreenListeners();
			addMouseMotionListenerToScreen();
			addMouseListenerToScreen();
		} else
			showErrorDialog("Please select at least one image first.");
	}

	private static void addMouseMotionListenerToScreen() {
		viewModel.getScreen().addCustomMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				viewModel.setDrawingEndPoint(e.getPoint());
				PixelCoordinator.setTargetPixels(viewModel.getSourcePixels());
				drawLine();
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
				viewModel.setDrawingStartPoint(e.getPoint());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				PixelCoordinator.setSourcePixels(viewModel.getTargetPixels());
				viewModel.setDrawingEndPoint(e.getPoint());
				enableSingleButton("Save");
				enableSingleButton("Reset");
			}
		});
	}

	private static void drawLine() {
		int x0 = (int) viewModel.getDrawingStartPoint().getX();
		int y0 = (int) viewModel.getDrawingStartPoint().getY();
		int x1 = (int) viewModel.getDrawingEndPoint().getX();
		int y1 = (int) viewModel.getDrawingEndPoint().getY();

		final int FIRST_COLOR = viewModel.getFirstColor().getRGB();
		final int SECOND_COLOR = viewModel.getSecondColor().getRGB();

		final int dx = Math.abs(x0 - x1);
		final int dy = Math.abs(y0 - y1);
		final int sgnDx = x0 < x1 ? 1 : -1;
		final int sgnDy = y0 < y1 ? 1 : -1;

		int shortD, longD, incXshort, incXlong, incYshort, incYlong;

		if (dx > dy) {
			shortD = dy;
			longD = dx;
			incXlong = sgnDx;
			incXshort = 0;
			incYlong = 0;
			incYshort = sgnDy;
		} else {
			shortD = dx;
			longD = dy;
			incXlong = 0;
			incXshort = sgnDx;
			incYlong = sgnDy;
			incYshort = 0;
		}

		int d = longD / 2;
		int x = x0;
		int y = y0;
		int i = 0;
		
		int index = PixelCoordinator.getPixelIndex(x, y);
		
		while(PixelCoordinator.pixelIndexIsInScreenArea(index) && i <= longD) {
			final int P = 100 * i / longD;
			final int MIXED_COLOR = PixelCoordinator.colorShuffle(FIRST_COLOR, SECOND_COLOR, P);
			PixelCoordinator.setSingleTargetPixel(index, MIXED_COLOR);
			x += incXlong;
			y += incYlong;
			d += shortD;
			if (d >= longD) {
				d -= longD;
				x += incXshort;
				y += incYshort;
			}
			++i;
			index = PixelCoordinator.getPixelIndex(x, y);
		}
	}
}
