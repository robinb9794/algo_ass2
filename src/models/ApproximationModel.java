package models;

import java.util.Vector;

import models.approximation.HistogramColor;

public class ApproximationModel {
	private Vector<HistogramColor> histogramColors;
	private Vector<HistogramColor> colorsSortedByNumber;
	private Vector<HistogramColor> colorsSortedByRed;
	private Vector<HistogramColor> colorsSortedByGreen;
	private Vector<HistogramColor> colorsSortedByBlue;
	
	private boolean foundValue;
	
	public ApproximationModel() {
		this.histogramColors = new Vector<HistogramColor>();
		this.colorsSortedByNumber = new Vector<HistogramColor>();
		this.colorsSortedByRed = new Vector<HistogramColor>();
		this.colorsSortedByGreen = new Vector<HistogramColor>();
		this.colorsSortedByBlue = new Vector<HistogramColor>();
		this.foundValue = false;
	}
	
	public Vector<HistogramColor> getHistogramColors(){
		return this.histogramColors;
	}
	
	public void addHistogramColor(HistogramColor histogramColor) {
		this.histogramColors.addElement(histogramColor);
	}
	
	public Vector<HistogramColor> getColorsSortedBy(String sortedBy){
		switch(sortedBy) {
		case "Number":
			return this.colorsSortedByNumber;
		case "Red":
			return this.colorsSortedByRed;
		case "Green":
			return this.colorsSortedByGreen;
		case "Blue":
			return this.colorsSortedByBlue;
		}
		return null;
	}
	
	public HistogramColor getSingleColorSortedBy(String sortedBy, int index){
		switch(sortedBy) {
		case "Number":
			return this.colorsSortedByNumber.get(index);
		case "Red":
			return this.colorsSortedByRed.get(index);
		case "Green":
			return this.colorsSortedByGreen.get(index);
		case "Blue":
			return this.colorsSortedByBlue.get(index);
		}
		return null;
	}
	
	public void setColorsSortedByNumber(Vector<HistogramColor> colorsSortedByNumber) {
		this.colorsSortedByNumber = colorsSortedByNumber;
	}
	
	public void setColorsSortedByRed(Vector<HistogramColor> colorsSortedByRed) {
		this.colorsSortedByRed = colorsSortedByRed;
	}
	
	public void setColorsSortedByGreen(Vector<HistogramColor> colorsSortedByGreen) {
		this.colorsSortedByGreen = colorsSortedByGreen;
	}
	
	public void setColorsSortedByBlue(Vector<HistogramColor> colorsSortedByBlue) {
		this.colorsSortedByBlue = colorsSortedByBlue;
	}
	
	public boolean valueWasFound() {
		return this.foundValue;
	}
	
	public void setValueWasFound(boolean foundValue) {
		this.foundValue = foundValue;
	}
}
