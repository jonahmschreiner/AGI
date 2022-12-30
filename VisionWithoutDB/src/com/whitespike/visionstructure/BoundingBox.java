package com.whitespike.visionstructure;

import java.awt.Point;
/**
 * Represents the "collision box" that minimally contains a sense. (Location 0,0 is at the top left of the image)
 * @author Jonah Schreiner
 *
 */
public class BoundingBox {
	/**
	 * Furthest top left pixel in the sense.
	 */
	public Pixel MinXMinY;
	/**
	 * Furthest bottom left pixel in the sense.
	 */
	public Pixel MinXMaxY;
	/**
	 * Furthest top right pixel in the sense.
	 */
	public Pixel MaxXMinY;
	/**
	 * Furthest bottom right pixel in the sense.
	 */
	public Pixel MaxXMaxY;
	/**
	 * Furthest left x value of all pixels in the sense.
	 */
	public int minX;
	/**
	 * Furthest right x value of all pixels in the sense.
	 */
	public int maxX;
	/**
	 * Furthest top y value of all pixels in the sense.
	 */
	public int minY;
	/**
	 * Furthest bottom y value of all pixels in the sense.
	 */
	public int maxY;
	
	public BoundingBox() {}
	/**
	 * Creates a boundingbox using the four min/max values (x and y).
	 * @param minXIn X value of the left edge of the box.
	 * @param minYIn Y value of the top edge of the box.
	 * @param maxXIn X value of the right edge of the box.
	 * @param maxYIn Y value of the bottom edge of the box.
	 */
	public BoundingBox(int minXIn, int minYIn, int maxXIn, int maxYIn) {
		this.MinXMinY = new Pixel(new Point(minXIn, minYIn));
		this.MinXMaxY = new Pixel(new Point(minXIn, maxYIn));
		this.MaxXMinY = new Pixel(new Point(maxXIn, minYIn));
		this.MaxXMaxY = new Pixel(new Point(maxXIn, maxYIn));
		this.minX = minXIn; //- 1;
		this.minY = minYIn; //- 1;
		this.maxX = maxXIn; //+ 1;
		this.maxY = maxYIn; //+ 1;
	}
	/**
	 * Two boundingboxes are equal if their four min/max values are within two pixels of each other (to account for java rounding of integer division).
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof BoundingBox){
			BoundingBox c = (BoundingBox) o;
			boolean output = false;
			if (c.minX < this.minX + 2 && c.minX > this.minX - 2
					&& c.maxX < this.maxX + 2 && c.maxX > this.maxX - 2
					&& c.minY < this.minY + 2 && c.minY > this.minY - 2
					&& c.maxY < this.maxY + 2 && c.maxY > this.maxY - 2){ //variables (that describe conditions) are the same
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
