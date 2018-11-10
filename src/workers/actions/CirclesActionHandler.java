package workers.actions;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import models.Mode;
import workers.DrawingInteractionInterlayer;
import workers.PixelCoordinator;

public class CirclesActionHandler extends DrawingInteractionInterlayer{
	private static boolean shouldFill;
	
	public static void handle() {
		if (userHasSelectedAtLeastOneImage()) {
			if (colorWindowIsAlreadyInitialized())
				colorWindow.setVisible(true);
			else {
				showInfoDialog("Help?", "Keep right mouse button pressed to fill circles.");
				initColorWindow();
			}
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
				drawCircle();
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
				shouldFill = isRightClick(e);
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
	
	private static boolean isRightClick(MouseEvent e) {
		return e.getButton() == MouseEvent.BUTTON3;
	}
	
	private static void drawCircle() {
		int x0 = (int) viewModel.getDrawingStartPoint().getX();
		int y0 = (int) viewModel.getDrawingStartPoint().getY();
		int x1 = (int) viewModel.getDrawingEndPoint().getX();
		int y1 = (int) viewModel.getDrawingEndPoint().getY();		

		final int dx = Math.abs(x0 - x1);
		int dy = Math.abs(y0 - y1);
		
		final int RADIUS = dx > dy ? dx : dy;
		int y = 0;
		int x = RADIUS;
		int F = -RADIUS;
		dy = 1;
		int dyx = -2 * RADIUS + 3;
		
		while(y <= x && circleIsStillInScreenArea(x0, y0, RADIUS)) {			
			setCirclePixelsAndFillIfNecessary(x0, y0, x, y);
			++y;
			dy += 2;
			dyx += 2;
			if(F > 0) {
				F += dyx;
				--x;
				dyx += 2;
			}else {
				F += dy;
			}		
		}		
	}
	
	private static boolean circleIsStillInScreenArea(int x0, int y0, int r) {
		return x0 - r >= 0 && x0 + r <= viewModel.getScreenWidth() && y0 - r >= 0 && y0 + r <= viewModel.getScreenHeight();
	}
	
	private static void setCirclePixelsAndFillIfNecessary(int x0, int y0, int x, int y) {
		int p0 = PixelCoordinator.getPixelIndex(x0 + x, y0 + y);
		int p1 = PixelCoordinator.getPixelIndex(x0 - x, y0 + y);
		int p2 = PixelCoordinator.getPixelIndex(x0 + x, y0 - y);
		int p3 = PixelCoordinator.getPixelIndex(x0 - x, y0 - y);
		int p4 = PixelCoordinator.getPixelIndex(x0 + y, y0 + x);
		int p5 = PixelCoordinator.getPixelIndex(x0 - y, y0 + x);
		int p6 = PixelCoordinator.getPixelIndex(x0 + y, y0 - x);
		int p7 = PixelCoordinator.getPixelIndex(x0 - y, y0 - x);
		
		if(shouldFill) {
			Point startPoint = new Point(x0 - y, y0 + x);
			Point endPoint = new Point(x0 + y, y0 + x);
			fill(startPoint, endPoint);
			
			startPoint = new Point(x0 - x, y0 + y);
			endPoint = new Point(x0 + x, y0 + y);
			fill(startPoint, endPoint);
			
			startPoint = new Point(x0 - x, y0 - y);
			endPoint = new Point(x0 + x, y0 - y);
			fill(startPoint, endPoint);
			
			startPoint = new Point(x0 - y, y0 - x);
			endPoint = new Point(x0 + y, y0 - x);
			fill(startPoint, endPoint);
		}else {
			PixelCoordinator.setSingleTargetPixel(p0, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p1, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p2, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p3, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p4, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p5, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p6, 0xff000000);
			PixelCoordinator.setSingleTargetPixel(p7, 0xff000000);	
		}			
	}
	
	private static void fill(Point startPoint, Point endPoint) {
		int startX = (int) startPoint.getX();
		int endX = (int) endPoint.getX();
		
		int startY = (int) startPoint.getY();
		int endY = (int) endPoint.getY();
		
		final int FIRST_COLOR = viewModel.getFirstColor().getRGB();
		final int SECOND_COLOR = viewModel.getSecondColor().getRGB();
		
		for(int i = startX; i <= endX; i++) {
			final int P = Math.round(100 * i / endX);
			final int MIXED_COLOR = 0xff000000 | PixelCoordinator.colorShuffle(FIRST_COLOR, SECOND_COLOR, P);
			for(int j = startY; j <= endY; j++) {	
				int index = PixelCoordinator.getPixelIndex(i, j);
				if(PixelCoordinator.pixelIndexIsInScreenArea(index))
					PixelCoordinator.setSingleTargetPixel(index, MIXED_COLOR);
			}
		}
	}
}
