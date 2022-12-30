package com.whitespike.visionstructure;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents a row's result when finding changed pixels in order to update the env.
 * @author Jonah Schreiner
 *
 */
public class ChangedPixelsBlobThreadResult {
	/**
	 * Represents which row this object is associated with.
	 */
	public int iValue;
	/**
	 * Blobs discovered in this row.
	 */
	public List<Blob> blobList = new ArrayList<Blob>();
	public ChangedPixelsBlobThreadResult(int iValueIn, List<Blob> blobListIn) {
		this.iValue = iValueIn;
		this.blobList = blobListIn;
	}
}
