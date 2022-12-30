package com.whitespike.vision;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.whitespike.visionstructure.*;
/**
 * Service method that turns a blob into a sense.
 * @author Jonah Schreiner
 *
 */
public class BlobToSense {
	//pass blob in with only pixels variable filled out (can have more but they'll be overrided)
	/**
	 * Service method that turns a blob into a sense.
	 * @param blobIn The blob to turn into a sense.
	 * @param imageIn The raw env image used to create the blob.
	 * @return The sense created from the blob.
	 */
	public static Sense getSense(Blob blobIn, BufferedImage imageIn) {
		//blob to edge
		Blob blob = BlobEdgeFromBlob.getEdge(blobIn, imageIn);
		if (blob.edgePixels.contains(new Pixel(new Point(10000, 0)))) {
			System.out.println();
		}
		//edge to posChanges
		List<PixelPosChange> posChanges = new ArrayList<PixelPosChange>();
		for (int i = 1; i < blob.edgePixels.size(); i++) {
			PixelPosChange posChange = new PixelPosChange(blob.edgePixels.get(i - 1), blob.edgePixels.get(i));
			posChanges.add(posChange);
		}
		if (blob.edgePixels.size() == 0) {
			System.out.println();
		}
		if (blob.edgePixels.size() > 0) {
			PixelPosChange posChange = new PixelPosChange(blob.edgePixels.get(blob.edgePixels.size() - 1), blob.edgePixels.get(0));
			posChanges.add(posChange);
		}

		
		//posChanges to PixelChanges, pixelchanges to overallchanges (sense definition)
		Sense sense = new Sense();
		sense.definition = new SenseDefinition(OverallPixelChangesFromPixelChanges.exec(IdentifyPixelChange.exec(posChanges)));
		sense.blob = blob;
		//orientation creation (from minAndMax class values)
		Orientation orientation = new Orientation();
		orientation.boundingBox = new BoundingBox(blob.minAndMaxes.minX, blob.minAndMaxes.minY, blob.minAndMaxes.maxX, blob.minAndMaxes.maxY);
		orientation.height = (blob.minAndMaxes.maxY - blob.minAndMaxes.minY) + 1;
		orientation.width = (blob.minAndMaxes.maxX - blob.minAndMaxes.minX) + 1;
		orientation.size = orientation.height * orientation.width;
		orientation.rotation = blob.minAndMaxes.slope;
		orientation.color = new Color(blob.minAndMaxes.averageRValue, blob.minAndMaxes.averageGValue, blob.minAndMaxes.averageBValue);
		//orientation.position = new Point(blob.minAndMaxes.averageXValue, blob.minAndMaxes.averageYValue);
		orientation.position = new Point(blob.minAndMaxes.minX + ((blob.minAndMaxes.maxX - blob.minAndMaxes.minX) / 2), blob.minAndMaxes.minY + ((blob.minAndMaxes.maxY - blob.minAndMaxes.minY) / 2));
		sense.orientation = orientation;
		return sense;
	}
}
