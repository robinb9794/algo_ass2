package models;

import java.util.Vector;

import models.approximation.HistogramColor;

public class ApproximationModel {
	private Vector<HistogramColor> histogramColors;
	private Vector<HistogramColor> colorsSortedByNumber;
	private Vector<HistogramColor> colorsSortedByRed;
	private Vector<HistogramColor> colorsSortedByGreen;
	private Vector<HistogramColor> colorsSortedByBlue;
	
	public ApproximationModel() {
		this.histogramColors = new Vector<HistogramColor>();
		this.colorsSortedByNumber = new Vector<HistogramColor>();
		this.colorsSortedByRed = new Vector<HistogramColor>();
		this.colorsSortedByGreen = new Vector<HistogramColor>();
		this.colorsSortedByBlue = new Vector<HistogramColor>();
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
	
	public void setColorsSortedByNumber(Vector<HistogramColor> colorsSortedByNumber) {
		this.colorsSortedByNumber = colorsSortedByNumber;
	}
}
