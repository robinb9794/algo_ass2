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
}
