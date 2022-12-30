package com.whitespike.visionstructure;

import java.awt.Color;
import java.awt.Point;

public class Orientation {
	public int height; //length of y
	public double rotation; //how much the rotation of the sense has changed compared to the originally-discovered instance
	public Color color; //average color of blob's range
	public Point position = new Point(); //where the average position of the sense is on the screen
	public int width; //length of x
	public int size; //height * width
	public BoundingBox boundingBox = new BoundingBox(); //corners of abstract box that covers the amount of screen space this sense takes up
	public Orientation() {}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Orientation){
			Orientation c = (Orientation) o;
			boolean output = false;
			if (c.height == this.height 
					&& c.rotation == this.rotation 
					&& c.color.equals(this.color)
					&& c.position.equals(this.position)
					&& c.width == this.width
					&& c.size == this.size
					&& c.boundingBox.equals(this.boundingBox)){ //variables (that describe conditions) are the same
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}
	}
}
