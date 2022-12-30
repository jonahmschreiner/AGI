package com.whitespike.visionstructure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents the pixels that make up a sense
 * @author Jonah Schreiner
 *
 */
public class Blob {
	/**
	 * All pixels in blob.
	 */
	public List<Pixel> pixels = new ArrayList<Pixel>();
	/**
	 * Pixels that make up the blob's edge.
	 */
	public List<Pixel> edgePixels = new ArrayList<Pixel>();
	/**
	 * The minAndMaxes object for the blob.
	 */
	public MinAndMaxes minAndMaxes = new MinAndMaxes();
	public List<Color> prevColors = new ArrayList<Color>();
	public Blob() {}
	/**
	 * Two blobs are equal if their "pixels" lists are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Blob){
			Blob c = (Blob) o;
			boolean output = false;
			if (c.pixels.equals(this.pixels)){ //variables (that describe conditions) are the same
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
