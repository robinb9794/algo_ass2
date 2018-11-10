package models.math;

public class Vector {
	public int[] values;
	
	public Vector(int x, int y) {
		values = new int[3];
		values[0] = x;
		values[1] = y;
		values[2] = 1;
	}
	
	public int getX() {
		return values[0];
	}
	
	public int getY() {
		return values[1];
	}
	
	public int get(int i) {
		return values[i];
	}
}
