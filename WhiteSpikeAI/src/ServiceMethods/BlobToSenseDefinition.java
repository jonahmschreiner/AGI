package ServiceMethods;

import java.util.ArrayList;
import java.util.List;

import Structure.Blob;
import Structure.Pixel;
import Structure.PixelOverallChange;
import Structure.PixelPosChange;
import Structure.Sense;
import Structure.SenseDefinition;

public class BlobToSenseDefinition {
	public static SenseDefinition getSense(Blob blobIn) {
		//blob to edge
		List<Pixel> edge = BlobEdgeFromBlob.getEdge(blobIn);
		
		//edge to posChanges
		List<PixelPosChange> posChanges = new ArrayList<PixelPosChange>();
		for (int i = 1; i < edge.size(); i++) {
			PixelPosChange posChange = new PixelPosChange(edge.get(i - 1), edge.get(i));
			posChanges.add(posChange);
		}
		PixelPosChange posChange = new PixelPosChange(edge.get(edge.size() - 1), edge.get(0));
		posChanges.add(posChange);
		
		//posChanges to PixelChanges, pixelchanges to overallchanges
		return new SenseDefinition(OverallPixelChangesFromPixelChanges.exec(IdentifyPixelChange.exec(posChanges)));

	}
}
