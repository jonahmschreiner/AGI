package com.whitespike.visionstructure;
/**
 * Represents the min/max x & y, average x & y, average RGB values of pixels in a blob.
 * @author Jonah Schreiner
 *
 */
public class MinAndMaxes {
		//for use in BlobEdgeFromBlob and sense's height and width values
		public int minY = 100000;
		public int minX = 100000;
		public int maxY = 0;
		public int maxX = 0;
		
		//for use in sense's color value
		public int averageRValue = 0;
		public int averageGValue = 0;
		public int averageBValue = 0;
		
		//for use in sense's position value
		public int averageXValue = 0;
		public int averageYValue = 0;
		
		//for use in sense's rotation value
		public double slope = 0;
		public MinAndMaxes() {}
}
