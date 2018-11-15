package models.approximation;

import java.awt.Color;

public class HistogramColor  {
	private int red, green, blue;
	private ColorOccurence colorOccurence;
	
	public HistogramColor(int red, int green, int blue, ColorOccurence colorOccurence) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.colorOccurence = colorOccurence;
	}
	
	public Integer getRed() {
		return this.red;
	}
	
	public Integer getGreen() {
		return this.green;
	}
	
	public Integer getBlue() {
		return this.blue;
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
}
