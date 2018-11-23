package models.approximation;

import java.awt.Color;

public class ApproximationPoint  {
	protected int red, green, blue;
	private ColorOccurence colorOccurence;
	
	public ApproximationPoint() {}
	
	public ApproximationPoint(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public ApproximationPoint(int red, int green, int blue, ColorOccurence colorOccurence) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.colorOccurence = colorOccurence;
	}
	
	public Integer getRed() {
		return this.red;
	}
	
	public void setRed(int red) {
		this.red = red;
	}
	
	public Integer getGreen() {
		return this.green;
	}
	
	public void setGreen(int green) {
		this.green = green;
	}
	
	public Integer getBlue() {
		return this.blue;
	}
	
	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public ColorOccurence getColorOccurence() {
		return this.colorOccurence;
	}
	
	public int toRGB() {
		return new Color(red, green, blue).getRGB();
	}
	
	public void setColorValues(Color color) {
		this.red = color.getRed();
		this.green = color.getGreen();
		this.blue = color.getBlue();
	}
	
	public double distance(ApproximationPoint p) {
		return Math.abs(Math.sqrt(Math.pow(Math.abs(red - p.getRed()), 2) + Math.pow(Math.abs(green - p.getGreen()), 2) + Math.pow(Math.abs(blue - p.getBlue()), 2)));
	}
}
