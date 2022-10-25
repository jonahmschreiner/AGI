package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Structure.Blob;
import Structure.MinAndMaxes;
import Structure.Pixel;

public class SenseCombinerBlobEdgeFromBlob {
	public static Blob getEdge(Blob blobIn){
		MinAndMaxes m = BlobEdgeFromBlob.getMinAndMaxes(blobIn.edgePixels);
		blobIn.minAndMaxes = m;
		List<Pixel> leftEdge = new ArrayList<Pixel>();
		List<Pixel> rightEdge = new ArrayList<Pixel>();
		List<Pixel> topEdge = new ArrayList<Pixel>();
		List<Pixel> bottomEdge = new ArrayList<Pixel>();
		
		for (int i = m.minY; i < m.maxY + 1; i++) {//TODO pixel of position (10000, 0) getting through (think minAndMaxes is fucked up)
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
	
	public static void main(String[] args) {
		Pixel pixel1 = new Pixel(new Point(100, 100), new Color(0, 0, 0));
		Pixel pixel2 = new Pixel(new Point(101, 100), new Color(0, 0, 0));
		Pixel pixel3 = new Pixel(new Point(102, 100), new Color(0, 0, 0));
		Pixel pixel4 = new Pixel(new Point(102, 99), new Color(0, 0, 0));
		Pixel pixel5 = new Pixel(new Point(102, 98), new Color(0, 0, 0));
		Pixel pixel6 = new Pixel(new Point(101, 98), new Color(0, 0, 0));
		Pixel pixel7 = new Pixel(new Point(100, 98), new Color(0, 0, 0));
		Pixel pixel8 = new Pixel(new Point(100, 99), new Color(0, 0, 0));
		
		Pixel pixel9 = new Pixel(new Point(100, 97), new Color(0, 0, 0));
		Pixel pixel10 = new Pixel(new Point(101, 97), new Color(0, 0, 0));
		Pixel pixel11 = new Pixel(new Point(102, 97), new Color(0, 0, 0));
		Pixel pixel12 = new Pixel(new Point(102, 96), new Color(0, 0, 0));
		Pixel pixel13 = new Pixel(new Point(102, 95), new Color(0, 0, 0));
		Pixel pixel14 = new Pixel(new Point(101, 95), new Color(0, 0, 0));
		Pixel pixel15 = new Pixel(new Point(100, 95), new Color(0, 0, 0));
		Pixel pixel16 = new Pixel(new Point(100, 96), new Color(0, 0, 0));
		List<Pixel> pixelsToAddToBlob = new ArrayList<Pixel>();
		pixelsToAddToBlob.add(pixel1);
		pixelsToAddToBlob.add(pixel2);
		pixelsToAddToBlob.add(pixel3);
		pixelsToAddToBlob.add(pixel4);
		pixelsToAddToBlob.add(pixel5);
		pixelsToAddToBlob.add(pixel6);
		pixelsToAddToBlob.add(pixel7);
		pixelsToAddToBlob.add(pixel8);
		pixelsToAddToBlob.add(pixel9);
		pixelsToAddToBlob.add(pixel10);
		pixelsToAddToBlob.add(pixel11);
		pixelsToAddToBlob.add(pixel12);
		pixelsToAddToBlob.add(pixel13);
		pixelsToAddToBlob.add(pixel14);
		pixelsToAddToBlob.add(pixel15);
		pixelsToAddToBlob.add(pixel16);
		Blob blob = new Blob();
		blob.edgePixels = pixelsToAddToBlob;
		Blob edge = SenseCombinerBlobEdgeFromBlob.getEdge(blob);
		System.out.println("");
	}
}
