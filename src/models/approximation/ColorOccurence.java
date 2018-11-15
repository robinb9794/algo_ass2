package models.approximation;

import java.util.Vector;

public class ColorOccurence {
	private int number;
	private Vector<Integer> indices = new Vector<Integer>();
	
	public ColorOccurence(int number, int index) {
		this.number = number;
		this.indices.addElement(index);
	}
	
	public ColorOccurence(int number, Vector<Integer> indices) {
		this.number = number;
		this.indices = indices;
	}
	
	public Integer getNumber() {
		return this.number;
	}
	
	public void incrNumber() {
		this.number++;
	}
	
	public Vector<Integer> getIndices(){
		return this.indices;
	}
	
	public void addIndex(int index) {
		this.indices.add(index);
	}
	
	public String occurenceToString() {
		String occurence = Integer.toString(number);
		for(int i = 0; i < indices.size(); i++) {
			int index = indices.get(i);
			occurence += "," + Integer.toString(index);
		}
		return occurence;
	}
}
