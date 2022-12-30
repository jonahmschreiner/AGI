package com.whitespike.vision;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.whitespike.visionstructure.*;
/**
 * Service method that extracts a blob edge from a blob and adds it to the blob.
 * @author Jonah Schreiner
 *
 */
public class BlobEdgeFromBlob {
	/**
	 * Service method that extracts a blob edge from a blob and adds it to the blob.
	 * @param blobIn Blob to extract the edge from.
	 * @param imageIn Image that the blob was created from.
	 * @return The blob with the edge added.
	 */
	public static Blob getEdge(Blob blobIn, BufferedImage imageIn){
		MinAndMaxes m = getMinAndMaxes(blobIn.pixels);
		blobIn.minAndMaxes = m;
		List<Pixel> leftEdge = new ArrayList<Pixel>();
		List<Pixel> rightEdge = new ArrayList<Pixel>();
		List<Pixel> topEdge = new ArrayList<Pixel>();
		List<Pixel> bottomEdge = new ArrayList<Pixel>();
		
		for (int i = m.minY; i < m.maxY + 1; i++) {
			Pixel furthestLeftPixel = new Pixel(new Point(10000, 0));
			Pixel furthestRightPixel = new Pixel(new Point(-1, 0));
			
			for (int j = m.minX; j < m.maxX + 1; j++) {
				Pixel currentPixel = new Pixel(new Point(j, i));
				if (blobIn.pixels.contains(currentPixel)) {
					if (currentPixel.position.x < furthestLeftPixel.position.x) {
						currentPixel.color = new Color(imageIn.getRGB(j, i));
						furthestLeftPixel = currentPixel;
					}
					
					if (currentPixel.position.x > furthestRightPixel.position.x) {
						currentPixel.color = new Color(imageIn.getRGB(j, i));
						furthestRightPixel = currentPixel;
					}
				}
			}
			leftEdge.add(furthestLeftPixel);
			rightEdge.add(furthestRightPixel);
		}
		//top edge (min Y)
		for (int i = m.minX; i < m.maxX + 1; i++) {
			Pixel currentPixel = new Pixel(new Point(i, m.minY));
			if (blobIn.pixels.contains(currentPixel)) {
				topEdge.add(blobIn.pixels.get(blobIn.pixels.indexOf(currentPixel)));
			}
		}
		
		//bottom edge (max Y)
		for (int i = m.minX; i < m.maxX + 1; i++) {
			Pixel currentPixel = new Pixel(new Point(i, m.maxY));
			if (blobIn.pixels.contains(currentPixel)) {
				bottomEdge.add(blobIn.pixels.get(blobIn.pixels.indexOf(currentPixel)));
			}
		}
		
		Pixel leftEdgeZero = leftEdge.get(0);
		Pixel leftEdgeEnd = leftEdge.get(leftEdge.size() - 1);
		
		if (leftEdge.size() > 1 && leftEdgeEnd.position.x != leftEdgeZero.position.x) {
			blobIn.minAndMaxes.slope = (leftEdgeEnd.position.y - leftEdgeZero.position.y)/(leftEdgeEnd.position.x - leftEdgeZero.position.x);
		} else {
			if (leftEdgeEnd.position.x == leftEdgeZero.position.x && leftEdge.size() > 1) {
				blobIn.minAndMaxes.slope = 10000;
			} else {
				blobIn.minAndMaxes.slope = 0;
			}
			
		}

		
		blobIn.edgePixels = mergeEdges(leftEdge, rightEdge, topEdge, bottomEdge);
		return blobIn;
	}
	/**
	 * Generate a MinAndMaxes object for the blob.
	 * @param pixelsIn The blob's pixels.
	 * @return MinAndMaxes object to be used in edge generation and stored in the blob for later use.
	 */
	public static MinAndMaxes getMinAndMaxes(List<Pixel> pixelsIn) {
		MinAndMaxes m = new MinAndMaxes();
		int numOfPixels = pixelsIn.size();
		for (int i = 0; i < numOfPixels; i++) {
			Pixel currentPixel = pixelsIn.get(i);
			m.averageXValue = m.averageXValue + currentPixel.position.x;
			m.averageYValue = m.averageYValue + currentPixel.position.y;
			m.averageRValue = m.averageRValue + currentPixel.color.getRed();
			m.averageGValue = m.averageGValue + currentPixel.color.getGreen();
			m.averageBValue = m.averageBValue + currentPixel.color.getBlue();

			if (currentPixel.position.x > m.maxX) {
				m.maxX = currentPixel.position.x;
			}
			if (currentPixel.position.x < m.minX) {
				m.minX = currentPixel.position.x;
			}
			if (currentPixel.position.y > m.maxY) {
				m.maxY = currentPixel.position.y;
			}
			if (currentPixel.position.y < m.minY) {
				m.minY = currentPixel.position.y;
			}
		}	
		m.averageXValue = m.averageXValue/numOfPixels;
		m.averageYValue = m.averageYValue/numOfPixels;
		m.averageRValue = m.averageRValue/numOfPixels;
		m.averageGValue = m.averageGValue/numOfPixels;
		m.averageBValue = m.averageBValue/numOfPixels;
		return m;
	}	
	/**
	 * Turn the four edges found into one complete, connected edge.
	 * @param leftEdge Left edge of blob.
	 * @param rightEdge Right edge of blob.
	 * @param topEdge Top edge of blob.
	 * @param bottomEdge Bottom edge of blob.
	 * @return Complete, connected edge of blob.
	 */
	public static List<Pixel> mergeEdges(List<Pixel> leftEdge, List<Pixel> rightEdge, List<Pixel> topEdge, List<Pixel> bottomEdge){
		Collections.reverse(leftEdge);
		Collections.reverse(bottomEdge);
		List<Pixel> mergedList = new ArrayList<Pixel>();
		Pixel startingPixel = new Pixel();
		Boolean leftEdgeExists = true;
		if (leftEdge.size() > 0) {
			mergedList = leftEdge;
			startingPixel = leftEdge.get(0);
		} else {
			leftEdgeExists = false;
		}
		
		if (leftEdgeExists) {
			if (topEdge.size() > 0) {
				if (mergedList.size() > 1) {
					if (mergedList.get(mergedList.size() - 1).equals(topEdge.get(0))) {
						topEdge.remove(0);
					}
				} else if (mergedList.size() > 0) {
					if (mergedList.get(0).equals(topEdge.get(0))) {
						topEdge.remove(0);
					}
				}
				mergedList.addAll(topEdge);
			}
		} else {
			mergedList.addAll(topEdge);
		}

		
		if (rightEdge.size() > 0) {
			if (mergedList.size() > 1) {
				if (mergedList.get(mergedList.size() - 1).equals(rightEdge.get(0))) {
					rightEdge.remove(0);
				}
			} else if (mergedList.size() > 0) {
				if (mergedList.get(0).equals(rightEdge.get(0))) {
					rightEdge.remove(0);
				}
			}
			mergedList.addAll(rightEdge);
		}

		
		if (bottomEdge.size() > 1) {
			if (mergedList.size() > 1) {
				if (mergedList.get(mergedList.size() - 1).equals(bottomEdge.get(0))) {
					bottomEdge.remove(0);
				}
			} else if (mergedList.size() > 0) {
				if (mergedList.get(0).equals(bottomEdge.get(0))) {
					bottomEdge.remove(0);
				}
			}
		}
	
		if (bottomEdge.size() > 1) {
			if (bottomEdge.get(bottomEdge.size() - 1).equals(startingPixel)) {
				bottomEdge.remove(bottomEdge.size() - 1);
			}
		} else if (bottomEdge.size() > 0){
			if (bottomEdge.get(0).equals(startingPixel)) {
				bottomEdge.remove(bottomEdge.size() - 1);
				if (mergedList.get(mergedList.size() - 1).equals(startingPixel)) {
					mergedList.remove(mergedList.size() - 1);
				}
			}
		} else {
			if (mergedList.get(mergedList.size() - 1).equals(startingPixel)) {
				mergedList.remove(mergedList.size() - 1);
			}
		}

		mergedList.addAll(bottomEdge);
		return mergedList;
	}
	
	
	//for testing
//	public static void main(String[] args) {
//		Pixel pixel1 = new Pixel(new Point(100, 100));
//		Pixel pixel2 = new Pixel(new Point(101, 100));
//		Pixel pixel3 = new Pixel(new Point(102, 100));
//		Pixel pixel4 = new Pixel(new Point(102, 99));
//		Pixel pixel5 = new Pixel(new Point(102, 98));
//		Pixel pixel6 = new Pixel(new Point(101, 98));
//		Pixel pixel7 = new Pixel(new Point(100, 98));
//		Pixel pixel8 = new Pixel(new Point(100, 99));
//		Pixel pixel9 = new Pixel(new Point(101, 99));
//		List<Pixel> pixelsToAddToBlob = new ArrayList<Pixel>();
//		pixelsToAddToBlob.add(pixel1);
//		pixelsToAddToBlob.add(pixel2);
//		pixelsToAddToBlob.add(pixel3);
//		pixelsToAddToBlob.add(pixel4);
//		pixelsToAddToBlob.add(pixel5);
//		pixelsToAddToBlob.add(pixel6);
//		pixelsToAddToBlob.add(pixel7);
//		pixelsToAddToBlob.add(pixel8);
//		pixelsToAddToBlob.add(pixel9);
//		Blob blob = new Blob();
//		blob.pixels = pixelsToAddToBlob;
//		Blob edge = BlobEdgeFromBlob.getEdge(blob);
//		System.out.println("");
//	}
}
