package ServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Structure.Blob;
import Structure.Orientation;
import Structure.Pixel;
import Structure.PixelPosChange;
import Structure.Sense;
import Structure.SenseDefinition;

public class BlobToSense {
	//pass blob in with only pixels variable filled out (can have more but they'll be overrided)
	public static Sense getSense(Blob blobIn) {
		//blob to edge
		Blob blob = BlobEdgeFromBlob.getEdge(blobIn);
		
		//edge to posChanges
		List<PixelPosChange> posChanges = new ArrayList<PixelPosChange>();
		for (int i = 1; i < blob.edgePixels.size(); i++) {
			PixelPosChange posChange = new PixelPosChange(blob.edgePixels.get(i - 1), blob.edgePixels.get(i));
			posChanges.add(posChange);
		}
		PixelPosChange posChange = new PixelPosChange(blob.edgePixels.get(blob.edgePixels.size() - 1), blob.edgePixels.get(0));
		posChanges.add(posChange);
		
		//posChanges to PixelChanges, pixelchanges to overallchanges (sense definition)
		Sense sense = new Sense();
		sense.definition = new SenseDefinition(OverallPixelChangesFromPixelChanges.exec(IdentifyPixelChange.exec(posChanges)));
		
		//orientation creation (from minAndMax class values)
		Orientation orientation = new Orientation();
		orientation.height = (blob.minAndMaxes.maxY - blob.minAndMaxes.minY) + 1;
		orientation.width = (blob.minAndMaxes.maxX - blob.minAndMaxes.minX) + 1;
		orientation.rotation = blob.minAndMaxes.slope;
		orientation.color = new Color(blob.minAndMaxes.averageRValue, blob.minAndMaxes.averageGValue, blob.minAndMaxes.averageBValue);
		orientation.position = new Point(blob.minAndMaxes.averageXValue, blob.minAndMaxes.averageYValue);
		sense.orientation = orientation;
		return sense;

	}
	
	public static void main(String[] args) {
		Blob blob = new Blob();
		Pixel pixel1 = new Pixel(new Point(100, 100), new Color(100, 50, 150));
		Pixel pixel2 = new Pixel(new Point(101, 100), new Color(110, 30, 140));
		Pixel pixel3 = new Pixel(new Point(102, 100), new Color(105, 55, 155));
		Pixel pixel4 = new Pixel(new Point(102, 99), new Color(102, 52, 132));
		Pixel pixel5 = new Pixel(new Point(102, 98), new Color(103, 58, 152));
		Pixel pixel6 = new Pixel(new Point(101, 98), new Color(112, 62, 132));
		Pixel pixel7 = new Pixel(new Point(100, 98), new Color(120, 55, 145));
		Pixel pixel8 = new Pixel(new Point(100, 99), new Color(101, 51, 147));
		List<Pixel> pixels = new ArrayList<Pixel>();
		pixels.add(pixel1);
		pixels.add(pixel2);
		pixels.add(pixel3);
		pixels.add(pixel4);
		pixels.add(pixel5);
		pixels.add(pixel6);
		pixels.add(pixel7);
		pixels.add(pixel8);
		blob.pixels = pixels;
		Sense sense1 = BlobToSense.getSense(blob);
		System.out.println("");
	}
}
