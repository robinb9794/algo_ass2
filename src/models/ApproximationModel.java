package models;

import java.util.Vector;

import models.approximation.ApproximationPoint;

public class ApproximationModel {
	private Vector<ApproximationPoint> histogramColors;
	private Vector<ApproximationPoint> colorsSortedByNumber;
	private Vector<ApproximationPoint> colorsSortedByRed;
	private Vector<ApproximationPoint> colorsSortedByGreen;
	private Vector<ApproximationPoint> colorsSortedByBlue;
	
	private boolean foundValue;
	
	public ApproximationModel() {
		this.histogramColors = new Vector<ApproximationPoint>();
		this.colorsSortedByNumber = new Vector<ApproximationPoint>();
		this.colorsSortedByRed = new Vector<ApproximationPoint>();
		this.colorsSortedByGreen = new Vector<ApproximationPoint>();
		this.colorsSortedByBlue = new Vector<ApproximationPoint>();
		this.foundValue = false;
	}
	
	public Vector<ApproximationPoint> getHistogramColors(){
		return this.histogramColors;
	}
	
	public void addHistogramColor(ApproximationPoint histogramColor) {
		this.histogramColors.addElement(histogramColor);
	}
	
	public Vector<ApproximationPoint> getColorsSortedBy(String sortedBy){
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
	
	public ApproximationPoint getSingleColorSortedBy(String sortedBy, int index){
		switch(sortedBy) {
		case "Number":
			return this.colorsSortedByNumber.elementAt(index);
		case "Red":
			return this.colorsSortedByRed.elementAt(index);
		case "Green":
			return this.colorsSortedByGreen.elementAt(index);
		case "Blue":
			return this.colorsSortedByBlue.elementAt(index);
		}
		return null;
	}
	
	public void setColorsSortedByNumber(Vector<ApproximationPoint> colorsSortedByNumber) {
		this.colorsSortedByNumber = colorsSortedByNumber;
	}
	
	public void setColorsSortedByRed(Vector<ApproximationPoint> colorsSortedByRed) {
		this.colorsSortedByRed = colorsSortedByRed;
	}
	
	public void setColorsSortedByGreen(Vector<ApproximationPoint> colorsSortedByGreen) {
		this.colorsSortedByGreen = colorsSortedByGreen;
	}
	
	public void setColorsSortedByBlue(Vector<ApproximationPoint> colorsSortedByBlue) {
		this.colorsSortedByBlue = colorsSortedByBlue;
	}
	
	public boolean valueWasFound() {
		return this.foundValue;
	}
	
	public void setValueWasFound(boolean foundValue) {
		this.foundValue = foundValue;
	}
}
