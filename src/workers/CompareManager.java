package workers;

import java.util.Comparator;

import models.approximation.HistogramColor;

public class CompareManager {
	public static Comparator<HistogramColor> getNumberComparator(){
		return new Comparator<HistogramColor>() {
			@Override
			public int compare(HistogramColor color1, HistogramColor color2) {
				return color2.getNumber().compareTo(color1.getNumber());
			}			
		};
	}
	
	public static Comparator<HistogramColor> getRedComparator(){
		return new Comparator<HistogramColor>() {
			@Override
			public int compare(HistogramColor color1, HistogramColor color2) {
				return color2.getRed().compareTo(color1.getRed());
			}
		};
	}
	
	public static Comparator<HistogramColor> getGreenComparator(){
		return new Comparator<HistogramColor>() {
			@Override
			public int compare(HistogramColor color1, HistogramColor color2) {
				return color2.getGreen().compareTo(color1.getGreen());
			}
		};
	}
	
	public static Comparator<HistogramColor> getBlueComparator(){
		return new Comparator<HistogramColor>() {
			@Override
			public int compare(HistogramColor color1, HistogramColor color2) {
				return color2.getBlue().compareTo(color1.getBlue());
			}
		};
	}
}
