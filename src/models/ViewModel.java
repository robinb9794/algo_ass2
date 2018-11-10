package models;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import interfaces.ImageDisplay;
import interfaces.bar.ImageBar;
import interfaces.buttons.ButtonField;
import models.math.Matrix;
import workers.PixelCoordinator;

public class ViewModel {	
	private String guiTitle;
	private int guiWidth, guiHeight;
	private int screenWidth, screenHeight;	
	private boolean userHasClosedGUI;

	private ImageDisplay screen;
	private int[] sourcePixels, targetPixels;
	private MemoryImageSource memoryImageSource;
	
	private File[] selectedFiles;
	private List<LoadedImage> loadedImages;
	
	private boolean guiIsLoaded;
	private ImageBar imageBar;
	
	private LinkedList<LoadedImage> selectedImages;
	
	private Map<ButtonField, String> buttons;
	
	private Point selectionStartPoint, selectionEndPoint;
	private Point selectionCenter;
	
	private Mode currentMode;
	
	private Matrix morphMatrix;
	
	private Color firstColor, secondColor;
	private Point drawingStartPoint, drawingEndPoint;
	
	public ViewModel(String guiTitle, int guiWidth, int guiHeight) {
		this.guiTitle = guiTitle;
		this.guiWidth = guiWidth;
		this.guiHeight = guiHeight;
		this.userHasClosedGUI = false;
		this.loadedImages = new ArrayList<LoadedImage>();
		this.guiIsLoaded = false;
		this.selectedImages = new LinkedList<LoadedImage>();
		this.buttons = new HashMap<ButtonField, String>();
		this.selectionStartPoint = new Point();
		this.selectionEndPoint = new Point();
		this.selectionCenter = new Point();
		this.morphMatrix = new Matrix();
		this.firstColor = new Color(0);
		this.secondColor = new Color(0);
		this.drawingStartPoint = new Point();
		this.drawingEndPoint = new Point();
	}
	
	public String getGUITitle() {
		return this.guiTitle;
	}
	
	public int getGUIWidth() {
		return this.guiWidth;
	}
	
	public int getGUIHeight() {
		return this.guiHeight;
	}
	
	public boolean userHasClosedGUI() {
		return this.userHasClosedGUI;
	}
	
	public void setUserHasClosedGUI(boolean userHasClosedGUI) {
		this.userHasClosedGUI = userHasClosedGUI;
	}
	
	public ImageDisplay getScreen() {
		return this.screen;
	}
	
	public void setScreen(ImageDisplay screen) {
		this.screen = screen;
		this.screenWidth = screen.getScreenWidth() - 25;
		this.screenHeight = screen.getScreenHeight() - 150;
	}
	
	public int getScreenWidth() {
		return this.screenWidth;
	}
	
	public int getScreenHeight() {
		return this.screenHeight;
	}
	
	public void initPixels() {
		this.sourcePixels = new int[this.screenWidth * this.screenHeight];
		this.targetPixels = new int[this.screenWidth * this.screenHeight];
	}
	
	public int[] getSourcePixels() {
		return this.sourcePixels;
	}
	
	public int[] getTargetPixels() {
		return this.targetPixels;
	}
	
	public void initMemoryImageSource() {
		this.memoryImageSource = new MemoryImageSource(this.screenWidth, this.screenHeight, this.targetPixels, 0, this.screenWidth);
		this.memoryImageSource.setAnimated(true);
	}
	
	public MemoryImageSource getMemoryImageSource() {
		return this.memoryImageSource;
	}
	
	public File[] getSelectedFiles() {
		return this.selectedFiles;
	}
	
	public void setSelectedFiles(File[] selectedFiles) {
		this.selectedFiles = selectedFiles;
	}
	
	public List<LoadedImage> getLoadedImages(){
		return this.loadedImages;
	}
	
	public void addLoadedImage(LoadedImage loadedImage) {
		this.loadedImages.add(loadedImage);
	}
	
	public void addSelectedImage(LoadedImage loadedImage) {
		this.selectedImages.add(loadedImage);
	}
	
	public LoadedImage getSelectedImageByIndex(int index) {
		return this.selectedImages.get(index);
	}
	
	public LinkedList<LoadedImage> getSelectedImages(){
		return this.selectedImages;
	}
	
	public void removeSelectedImage(LoadedImage selectedImage) {
		this.selectedImages.remove(selectedImage);
	}
	
	public void updateDisplayedImage() {
		LoadedImage nextDisplayedImage = null;
		if(this.selectedImages.size() > 0) {
			nextDisplayedImage = this.selectedImages.getLast();
			PixelCoordinator.setSourceAndTargetPixels(nextDisplayedImage.getGrabbedPixels());
		}else {
			PixelCoordinator.resetSourcePixels();
			PixelCoordinator.resetTargetPixels();
			screen.resetListeners();
		}
	}
	
	public synchronized boolean guiIsLoaded() {
		return this.guiIsLoaded;
	}
	
	public void setGUIIsLoaded(boolean guiIsLoaded) {
		this.guiIsLoaded = guiIsLoaded;
	}
	
	public void setImageBar(ImageBar imageBar) {
		this.imageBar = imageBar;
	}
	
	public ImageBar getImageBar() {
		return this.imageBar;
	}
	
	public void addButton(ButtonField button, String type) {
		buttons.put(button, type);
	}
	
	public Map<ButtonField, String> getButtons(){
		return this.buttons;
	}
	
	public Point getSelectionStartPoint() {
		return this.selectionStartPoint;
	}
	
	public void setSelectionStartPoint(int x, int y) {
		this.selectionStartPoint.setLocation(x, y);
	}
	
	public Point getSelectionEndPoint() {
		return this.selectionEndPoint;
	}
	
	public void setSelectionEndPoint(int x, int y) {
		this.selectionEndPoint.setLocation(x, y);
	}
	
	public void setSelectionCenter(){
		int startX = Math.min((int) getSelectionStartPoint().getX(), (int) getSelectionEndPoint().getX());
		int endX = Math.max((int) getSelectionStartPoint().getX(), (int) getSelectionEndPoint().getX());
		int startY = Math.min((int) getSelectionStartPoint().getY(), (int) getSelectionEndPoint().getY());
		int endY = Math.max((int) getSelectionStartPoint().getY(), (int) getSelectionEndPoint().getY());
		int centerX = startX + ((endX - startX) / 2);
		int centerY = startY + ((endY - startY) / 2);
		this.selectionCenter.setLocation(centerX, centerY);
	}
	
	public void upateSelectionCenter(int x, int y) {
		int centerX = (int) this.selectionCenter.getX() + x;
		int centerY = (int) this.selectionCenter.getY() + y;
		this.selectionCenter.setLocation(centerX, centerY);
	}
	
	public Point getSelectionCenter() {
		return this.selectionCenter;
	}
	
	public void resetSelectionPoints() {
		this.selectionStartPoint = new Point();
		this.selectionEndPoint = new Point();
		this.selectionCenter = new Point();
	}
	
	public Mode getCurrentMode() {
		return this.currentMode;
	}
	
	public void setCurrentMode(Mode currentMode) {
		this.currentMode = currentMode;
	}
	
	public Matrix getMorphMatrix() {
		return this.morphMatrix;
	}
	
	public void setMorphMatrix(Matrix morphMatrix) {
		this.morphMatrix = morphMatrix;
	}
	
	public Color getFirstColor() {
		return this.firstColor;
	}
	
	public void setFirstColor(Color firstColor) {
		this.firstColor = firstColor;
	}
	
	public Color getSecondColor() {
		return this.secondColor;
	}
	
	public void setSecondColor(Color secondColor) {
		this.secondColor = secondColor;
	}
	
	public Point getDrawingStartPoint() {
		return this.drawingStartPoint;
	}
	
	public void setDrawingStartPoint(Point drawingStartPoint) {
		this.drawingStartPoint = drawingStartPoint;
	}
	
	public Point getDrawingEndPoint() {
		return this.drawingEndPoint;
	}
	
	public void setDrawingEndPoint(Point drawingEndPoint) {
		this.drawingEndPoint = drawingEndPoint;
	}
	
	public void resetDrawingPoints() {
		this.drawingStartPoint = new Point();
		this.drawingEndPoint = new Point();
	}
}
