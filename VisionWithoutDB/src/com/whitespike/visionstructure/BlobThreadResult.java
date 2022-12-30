package com.whitespike.visionstructure;
import java.util.List;
import java.util.ArrayList;
/**
 * Represents the blobs found from a certain row of a screenshot
 * @author Jonah Schreiner
 *
 */
public class BlobThreadResult {
	/**
	 * Which row this object represents.
	 */
	public int iValue;
	/**
	 * Blobs found in the row.
	 */
	public List<Blob> blobList = new ArrayList<Blob>();
	/**
	 * Top row of pixels in the row.
	 */
	public List<Pixel> topRowOfPixels = new ArrayList<Pixel>();
	/**
	 * Bottom row of pixels in the row.
	 */
	public List<Pixel> bottomRowOfPixels = new ArrayList<Pixel>();
	
	public BlobThreadResult(int iValueIn, List<Blob> blobListIn, List<Pixel> topRowIn, List<Pixel> bottomRowIn) {
		this.iValue = iValueIn;
		this.blobList = blobListIn;
		this.topRowOfPixels = topRowIn;
		this.bottomRowOfPixels = bottomRowIn;
	}
}
