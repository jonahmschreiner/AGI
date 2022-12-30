package com.whitespike.visionstructure;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents the result of a parallel thread during blob combining
 * @author Jonah Schreiner
 *
 */
public class BlobCombinerThreadResult {
	public int iValue;
	/**
	 * Blobs resulting from the combining.
	 */
	public List<Blob> blobList = new ArrayList<Blob>();
	
	public BlobCombinerThreadResult(int iValueIn, List<Blob> blobListIn) {
		this.iValue = iValueIn;
		this.blobList = blobListIn;
	}

}
