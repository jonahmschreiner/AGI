package EnvAndDatabaseServiceMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import Structure.Blob;
import Structure.BoundingBox;
import Structure.PixelColorRange;
import Structure.Sense;

public class SenseCombiner {
	public static List<Sense> exec(List<Sense> listIn){
		List<Sense> sensesOut = new ArrayList<Sense>();
		Map<String, List<Sense>> sensesByColor = getSensesByColorRange(listIn);
		Iterator<String> iter = sensesByColor.keySet().iterator();
		
		ExecutorService EXEC = Executors.newFixedThreadPool(sensesByColor.size());
		List<SenseParallelCombiner> tasks = new ArrayList<SenseParallelCombiner>();
		for (int i = 0; i < sensesByColor.size(); i++) {
			String currKey = iter.next();
			SenseParallelCombiner spc = new SenseParallelCombiner(sensesByColor.get(currKey));
			tasks.add(spc);
		}
		
		List<Future<List<Sense>>> blobsOutFromParallel;
		try {
			blobsOutFromParallel = EXEC.invokeAll(tasks);
			for (Future<List<Sense>> FutureResult : blobsOutFromParallel) {
				List<Sense> futureResult = FutureResult.get();
				sensesOut.addAll(futureResult);
			}
		} catch (Exception e) {
			System.out.println("HERE");
			e.printStackTrace();
		} finally {
			EXEC.shutdown();
		}
		
		return sensesOut;
	}
	
	public static Map<String, List<Sense>> getSensesByColorRange(List<Sense> sensesIn){
		Map<String, List<Sense>> output = new HashMap<String, List<Sense>>();
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currSense = sensesIn.get(i);
			String colorRange = new PixelColorRange(currSense.orientation.color).color;
			if (output.containsKey(colorRange)) {
				output.get(colorRange).add(currSense);
			} else {
				output.put(colorRange, new ArrayList<Sense>());
				output.get(colorRange).add(currSense);
			}
		}
		
		return output;
	}
	
	public static class SenseParallelCombiner implements Callable<List<Sense>>{
		private final List<Sense> input;
	
		public SenseParallelCombiner(List<Sense> inputList) {
			this.input = inputList;
		}
		
		public List<Sense> call() { //uses the sense list where they're all the same color
			List<Sense> output = exec(this.input);
			int prevSize;
			boolean continueFlag = true;
			while (continueFlag){
				continueFlag = false;
				prevSize = output.size();
				output = exec(output);
				if (output.size() < prevSize) {
					continueFlag = true;
				}
			}
			return output;
		}
		
		public List<Sense> exec(List<Sense> sensesIn){
			List<Sense> output = new ArrayList<Sense>();
			List<Integer> usedSenses = new ArrayList<Integer>();
			for (int i = 0; i < sensesIn.size(); i++) {
				Blob outputBlob = new Blob();
				if (!usedSenses.contains(i)) {
					Sense currSense = sensesIn.get(i);
					outputBlob.pixels.addAll(currSense.blob.pixels);
					BoundingBox currSenseBox = currSense.orientation.boundingBox;
					for (int j = 0; j < sensesIn.size(); j++) {
						if (!usedSenses.contains(j) && j != i) {
							Sense currCompSense = sensesIn.get(j);
							BoundingBox currCompBox = currCompSense.orientation.boundingBox;
							boolean yWithinRange = false;
							boolean xWithinRange = false;
							boolean xTouching = false;
							boolean yTouching = false;
							if ((currSenseBox.minY >= currCompBox.minY && currSenseBox.minY <= currCompBox.maxY)
									|| (currSenseBox.maxY >= currCompBox.minY && currSenseBox.maxY <= currCompBox.maxY)
									|| (currSenseBox.maxY >= currCompBox.maxY && currSenseBox.minY <= currCompBox.minY)) {
								yWithinRange = true;
							}
							if ((currSenseBox.minX >= currCompBox.minX && currSenseBox.minX <= currCompBox.maxX)
									|| (currSenseBox.maxX >= currCompBox.minX && currSenseBox.maxX <= currCompBox.maxX)
									|| (currSenseBox.maxX >= currCompBox.maxX && currSenseBox.minX <= currCompBox.minX)) {
								xWithinRange = true;
							}
							if (currSenseBox.maxX == currCompBox.minX - 1 || currSenseBox.minX == currCompBox.maxX + 1) {
								xTouching = true;
							}
							if (currSenseBox.maxY == currCompBox.minY - 1 || currSenseBox.minY == currCompBox.maxY + 1) {
								yTouching = true;
							}
							
							if ((yTouching && xWithinRange) || (xTouching && yWithinRange)) {
								outputBlob.pixels.addAll(currCompSense.blob.pixels);
								usedSenses.add(j);
							}
						}
					}
					if (outputBlob.pixels.size() > currSense.blob.pixels.size()) {
						Sense newSense = BlobToSense.getSense(outputBlob);
						output.add(newSense);
					} else {
						output.add(currSense);
					}
				}
			}
			
			return output;
		}
	}
}
