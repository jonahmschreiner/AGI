package com.whitespike.vision;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.whitespike.visionstructure.*;

public class SenseCombinerBlobEdgeFromBlob {
	public static Blob getEdge(Blob blobIn){
		MinAndMaxes m = BlobEdgeFromBlob.getMinAndMaxes(blobIn.edgePixels); //changed this to blobIn.pixels to fix color identification issue where edges being a different color made the whole sense not that color
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
				if (blobIn.edgePixels.contains(currentPixel)) {
					if (currentPixel.position.x < furthestLeftPixel.position.x) {
						currentPixel.color = blobIn.edgePixels.get(blobIn.edgePixels.indexOf(currentPixel)).color;
						furthestLeftPixel = currentPixel;
					}
					
					if (currentPixel.position.x > furthestRightPixel.position.x) {
						currentPixel.color = blobIn.edgePixels.get(blobIn.edgePixels.indexOf(currentPixel)).color;
						furthestRightPixel = currentPixel;
					}
				}
			}
			if (furthestLeftPixel.position.x == 10000) {
				System.out.println();
			}
			leftEdge.add(furthestLeftPixel);
			rightEdge.add(furthestRightPixel);
		}
		//top edge (min Y)
		for (int i = m.minX; i < m.maxX + 1; i++) {
			Pixel currentPixel = new Pixel(new Point(i, m.minY));
			if (blobIn.edgePixels.contains(currentPixel)) {
				currentPixel.color = blobIn.edgePixels.get(blobIn.edgePixels.indexOf(currentPixel)).color;
				topEdge.add(blobIn.edgePixels.get(blobIn.edgePixels.indexOf(currentPixel)));
			}
		}
		
		//bottom edge (max Y)
		for (int i = m.minX; i < m.maxX + 1; i++) {
			Pixel currentPixel = new Pixel(new Point(i, m.maxY));
			if (blobIn.edgePixels.contains(currentPixel)) {
				currentPixel.color = blobIn.edgePixels.get(blobIn.edgePixels.indexOf(currentPixel)).color;
				bottomEdge.add(blobIn.edgePixels.get(blobIn.edgePixels.indexOf(currentPixel)));
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

		
		blobIn.edgePixels = BlobEdgeFromBlob.mergeEdges(leftEdge, rightEdge, topEdge, bottomEdge);
		return blobIn;
	}
}
