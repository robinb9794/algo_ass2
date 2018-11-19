package models.approximation;

public class ProjectionResult {
	private int middle;
	private double range;
	private ApproximationPoint approximationPoint;
	
	public ProjectionResult(int middle, double range, ApproximationPoint approximationPoint) {
		this.middle = middle;
		this.range = range;
		this.approximationPoint = approximationPoint;
	}
	
	public int getMiddle() {
		return this.middle;
	}
	
	public double getRange() {
		return this.range;
	}
	
	public ApproximationPoint getApproximationPoint() {
		return this.approximationPoint;
	}
}
