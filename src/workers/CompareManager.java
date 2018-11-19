package workers;

import java.util.Comparator;

import models.approximation.ApproximationPoint;

public class CompareManager {
	public static Comparator<ApproximationPoint> getNumberComparator(){
		return new Comparator<ApproximationPoint>() {
			@Override
			public int compare(ApproximationPoint color1, ApproximationPoint color2) {
				return color1.getColorOccurence().getNumber().compareTo(color2.getColorOccurence().getNumber());
			}			
		};
	}
	
	public static Comparator<ApproximationPoint> getRedComparator(){
		return new Comparator<ApproximationPoint>() {
			@Override
			public int compare(ApproximationPoint color1, ApproximationPoint color2) {
				int result =  color1.getRed().compareTo(color2.getRed());
				if(result == 0) {
					result =  color1.getGreen().compareTo(color2.getGreen());
					if(result == 0) {
						result =  color1.getBlue().compareTo(color2.getBlue());
					}
				}
				return result;
			}
		};
	}
	
	public static Comparator<ApproximationPoint> getGreenComparator(){
		return new Comparator<ApproximationPoint>() {
			@Override
			public int compare(ApproximationPoint color1, ApproximationPoint color2) {
				int result = color1.getGreen().compareTo(color2.getGreen());
				if(result == 0) {
					result =  color1.getRed().compareTo(color2.getRed());
					if(result == 0) {
						result =  color1.getBlue().compareTo(color2.getBlue());
					}
				}
				return result;
			}
		};
	}
	
	public static Comparator<ApproximationPoint> getBlueComparator(){
		return new Comparator<ApproximationPoint>() {
			@Override
			public int compare(ApproximationPoint color1, ApproximationPoint color2) {
				int result = color1.getBlue().compareTo(color2.getBlue());
				if(result == 0) {
					result =  color1.getRed().compareTo(color2.getRed());
					if(result == 0) {
						result =  color1.getGreen().compareTo(color2.getGreen());
					}
				}
				return result;
			}
		};
	}
}
