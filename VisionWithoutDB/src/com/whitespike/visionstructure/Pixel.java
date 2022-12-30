package com.whitespike.visionstructure;

import java.awt.Color;
import java.awt.Point;
/**
 * Represents a pixel in the env.
 * @author Jonah Schreiner
 *
 */
public class Pixel {
	/**
	 * Where the pixel is located on the image.
	 */
	public Point position;
	/**
	 * Color value of pixel.
	 */
	public Color color;
	public Pixel() {}
	public Pixel(Point positionIn, Color colorIn) {
		this.position = positionIn;
		this.color = colorIn;
	}
	public Pixel(Point positionIn) {
		this.position = positionIn;
	}
	public Pixel(Color colorIn) {
		this.color = colorIn;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Pixel){
			Pixel c = (Pixel) o;
			boolean output = false;
			if (c.position.x == this.position.x && c.position.y == this.position.y){ //variables (that describe conditions) are the same
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
