package models.approximation;

public class ApproximationPoint {
	private int x, y, z;
	
	public ApproximationPoint(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public double distance(ApproximationPoint p) {
		return Math.abs(Math.sqrt(Math.pow(Math.abs(x - p.getX()), 2) + Math.pow(Math.abs(y - p.getY()), 2) + Math.pow(Math.abs(z - p.getZ()), 2)));
	}
}
