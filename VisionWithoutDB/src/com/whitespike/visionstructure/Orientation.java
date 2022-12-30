package com.whitespike.visionstructure;

import java.awt.Color;
import java.awt.Point;
/**
 * Represents the characteristics of a sense.
 * @author Jonah Schreiner
 *
 */
public class Orientation {
	/**
	 * The sense's height (maxY - minY).
	 */
	public int height; //length of y
	/**
	 * The sense's rotation (determined using the slope of the left edge).
	 */
	public double rotation; //how much the rotation of the sense has changed compared to the originally-discovered instance
	/**
	 * Average RGB value of blob pixels.
	 */
	public Color color; //average color of blob's range
	/**
	 * Average position of where the sense is (might've changed this to be the center of the bounding box instead).
	 */
	public Point position = new Point(); //where the average position of the sense is on the screen
	/**
	 * The sense's width (maxX - minX).
	 */
	public int width; //length of x
	/**
	 * Height * Width
	 */
	public int size; //height * width
	/**
	 * Sense's bounding box object (see bounding box for more details).
	 */
	public BoundingBox boundingBox = new BoundingBox(); //corners of abstract box that covers the amount of screen space this sense takes up
	public Orientation() {}
	/**
	 * Equal if all props are the same.
	 */
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
