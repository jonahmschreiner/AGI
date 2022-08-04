package ServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Structure.Blob;
import Structure.Orientation;
import Structure.PixelPosChange;
import Structure.Sense;
import Structure.SenseDefinition;

public class BlobToSense {
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
		orientation.height = blob.minAndMaxes.maxY - blob.minAndMaxes.minY;
		orientation.width = blob.minAndMaxes.maxX - blob.minAndMaxes.minX;
		orientation.rotation = blob.minAndMaxes.slope;
		orientation.color = new Color(blob.minAndMaxes.averageRValue, blob.minAndMaxes.averageGValue, blob.minAndMaxes.averageBValue);
		orientation.position = new Point(blob.minAndMaxes.averageXValue, blob.minAndMaxes.averageYValue);
		sense.orientation = orientation;
		return sense;

	}
}
