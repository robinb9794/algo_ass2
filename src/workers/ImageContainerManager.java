package workers;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import interfaces.GUIElement;
import interfaces.LoadingScreen;
import interfaces.bar.DisplayedImageContainer;
import interfaces.bar.ImageBar;
import models.LoadedImage;

public class ImageContainerManager extends GUIManager {		
	private static ImageBar imageBar;
	private static GUIElement scrollBar;
	
	private static int screenWidth, screenHeight;
	
	public static void init() {
		imageBar = (ImageBar) guiElementFactory.getGUIElement("ImageContainer");
	}
	
	public static void startWork() {
		setScreenValues();
		initImageBar();
		handleFiles(viewModel.getSelectedFiles());
		makeImageBarScrollable();
		updateGUIAndViewModel();		
	}
	
	private static void setScreenValues() {
		screenWidth = viewModel.getScreenWidth();
		screenHeight = viewModel.getScreenHeight();
	}
	
	private static void initImageBar() {
		imageBar.init();		
	}
	
	public static void handleFiles(File[] files) {
		for(int i = 0; i < files.length; i++) {
			File file = files[i];
			if(file.isDirectory()) {
				File[] filesFromDirectory = file.listFiles();
				handleFiles(filesFromDirectory);
			}else if(fileIsImage(file)){
				handleImage(file);
			}
		}	
	}
	
	private static boolean fileIsImage(File file) {
		return file.getPath().endsWith("png") || file.getPath().endsWith("jpg");
	}
	
	public static void handleImage(File selectedFile) {
		try {
			Image originalImage = ImageIO.read(selectedFile);
			int[] grabbedPixels = getGrabbedPixels(originalImage);
			ImageIcon icon = new ImageIcon(originalImage.getScaledInstance(95, 95, Image.SCALE_SMOOTH));
			LoadedImage loadedImage = getLoadedImage(icon, grabbedPixels);
			createIconAndAddToBar(loadedImage);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}	
	
	public static LoadedImage getLoadedImage(ImageIcon icon, int[] grabbedPixels) {
		LoadedImage loadedImage = new LoadedImage(icon, grabbedPixels);
		viewModel.addLoadedImage(loadedImage);
		return loadedImage;
	}
	
	private static int[] getGrabbedPixels(Image originalImage) {
		int[] pixels;
		try {
			originalImage = getScaledImage(originalImage);
			pixels = new int[screenWidth * screenHeight];
			PixelGrabber pixelGrabber = new PixelGrabber(originalImage, 0, 0, screenWidth, screenHeight, pixels, 0, screenWidth);
			pixelGrabber.grabPixels();
			return pixels;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private static BufferedImage getScaledImage(Image originalImage) {
		Image tmp = originalImage.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
		BufferedImage scaledImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = scaledImage.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return scaledImage;
	}
	
	public static void createIconAndAddToBar(LoadedImage loadedImage) {
		GUIElement imageField = getInitializedImageField(loadedImage);				
		imageBar.addImageField(imageField);
	}
	
	private static GUIElement getInitializedImageField(LoadedImage loadedImage) {
		DisplayedImageContainer imageField = (DisplayedImageContainer) guiElementFactory.getGUIElement("ImageField");
		imageField.init();
		imageField.passImageIconFromLoadedImageToClickableImage(loadedImage);
		return imageField;
	}
	
	private static void makeImageBarScrollable() {
		viewModel.setImageBar(imageBar);
		scrollBar = guiElementFactory.getGUIElement("ImageScroller");
		scrollBar.init();
	}
	
	private static void updateGUIAndViewModel() {
		gui.addElement(BorderLayout.SOUTH, scrollBar);
		viewModel.setGUIIsLoaded(true);
	}
	
	public static void reloadImageBar() {
		imageBar.reload();
	}
	
	public static void enableImageBar() {
		imageBar.enableImages();
	}
	
	public static void blockImageBar() {
		imageBar.blockImages();
	}	
}
