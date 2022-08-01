package ServiceMethods;

import java.awt.Point;
import java.util.ArrayList;
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
		
		for (int i = m.minY; i < m.maxY; i++) {
			Pixel furthestLeftPixel = new Pixel(new Point(10000, 0));
			Pixel furthestRightPixel = new Pixel(new Point(-1, 0));
			
			for (int j = m.minX; j < m.maxX; j++) {
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
		for (int i = m.minX; i < m.maxX; i++) {
			Pixel currentPixel = new Pixel(new Point(i, m.minY));
			if (blobIn.pixels.contains(currentPixel)) {
				topEdge.add(blobIn.pixels.get(blobIn.pixels.indexOf(currentPixel)));
			}
		}
		
		//bottom edge (max Y)
		for (int i = m.minX; i < m.maxX; i++) {
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
		
		return new ArrayList<Pixel>();
	}
}
