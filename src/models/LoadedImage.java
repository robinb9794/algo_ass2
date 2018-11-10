package models;

import javax.swing.ImageIcon;

public class LoadedImage {
	private ImageIcon icon;
	private int[] grabbedPixels;
	
	public LoadedImage(ImageIcon icon, int[] grabbedPixels) {
		this.icon = icon;
		this.grabbedPixels = grabbedPixels;
	}	
	
	public int[] getGrabbedPixels() {
		return this.grabbedPixels;
	}
	
	public ImageIcon getIcon() {
		return this.icon;
	}
}
