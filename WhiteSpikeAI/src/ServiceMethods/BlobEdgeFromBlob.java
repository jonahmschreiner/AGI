package ServiceMethods;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Structure.Blob;
import Structure.MinAndMaxes;
import Structure.Pixel;

public class BlobEdgeFromBlob {

	public static List<Pixel> getEdge(Blob blobIn){
		MinAndMaxes m = getMinAndMaxes(blobIn.pixels);
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
						furthestLeftPixel = currentPixel;
					}
					
					if (currentPixel.position.x > furthestRightPixel.position.x) {
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
		
		return mergeEdges(leftEdge, rightEdge, topEdge, bottomEdge);
	}
	
	private static MinAndMaxes getMinAndMaxes(List<Pixel> pixelsIn) {
		MinAndMaxes m = new MinAndMaxes();
		for (int i = 0; i < pixelsIn.size(); i++) {
			Pixel currentPixel = pixelsIn.get(i);
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
		return m;
	}	
	
	private static List<Pixel> mergeEdges(List<Pixel> leftEdge, List<Pixel> rightEdge, List<Pixel> topEdge, List<Pixel> bottomEdge){
		Collections.reverse(leftEdge);
		Collections.reverse(bottomEdge);
		List<Pixel> mergedList = leftEdge;
		Pixel startingPixel = leftEdge.get(0);
		if (mergedList.get(mergedList.size() - 1).equals(topEdge.get(0))) {
			topEdge.remove(0);
		}
		mergedList.addAll(topEdge);
		if (mergedList.get(mergedList.size() - 1).equals(rightEdge.get(0))) {
			rightEdge.remove(0);
		}
		mergedList.addAll(rightEdge);
		if (mergedList.get(mergedList.size() - 1).equals(bottomEdge.get(0))) {
			bottomEdge.remove(0);
		}
		
		if (bottomEdge.get(bottomEdge.size() - 1).equals(startingPixel)) {
			bottomEdge.remove(bottomEdge.size() - 1);
		}
		mergedList.addAll(bottomEdge);
		return mergedList;
	}
	
	
	//for testing
	public static void main(String[] args) {
		Pixel pixel1 = new Pixel(new Point(100, 100));
		Pixel pixel2 = new Pixel(new Point(101, 100));
		Pixel pixel3 = new Pixel(new Point(102, 100));
		Pixel pixel4 = new Pixel(new Point(102, 99));
		Pixel pixel5 = new Pixel(new Point(102, 98));
		Pixel pixel6 = new Pixel(new Point(101, 98));
		Pixel pixel7 = new Pixel(new Point(100, 98));
		Pixel pixel8 = new Pixel(new Point(100, 99));
		Pixel pixel9 = new Pixel(new Point(101, 99));
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
		Blob blob = new Blob();
		blob.pixels = pixelsToAddToBlob;
		List<Pixel> edge = BlobEdgeFromBlob.getEdge(blob);
		System.out.println(edge.size());
	}
}
