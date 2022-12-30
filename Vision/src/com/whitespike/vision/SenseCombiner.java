package com.whitespike.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import com.whitespike.visionstructure.*;

public class SenseCombiner {
	public static List<Sense> exec(List<Sense> listIn, List<Sense> existingEnvListIn){
		System.out.println("Num of Senses: " + listIn.size());
		List<Sense> sensesOut = new ArrayList<Sense>();
		try {
			long blobStart = System.currentTimeMillis();
			Map<String, List<Sense>> sensesByColor = getSensesByColorRange(listIn);
			Map<String, List<Sense>> existingSensesByColor = null;
			if (existingEnvListIn != null) {
				existingSensesByColor = getSensesByColorRange(existingEnvListIn);
			}
			
			long blobEnd = System.currentTimeMillis();
			System.out.println("Map Senses By Color Creation Time: " + (blobEnd-blobStart));
			System.out.println("Number of keys in map: " + sensesByColor.size());
			Iterator<String> iter = sensesByColor.keySet().iterator();
			if (sensesByColor.size() < 1) {
				System.out.println();
				return sensesOut;
			}
			ExecutorService EXEC = Executors.newFixedThreadPool(sensesByColor.size());
			List<SenseParallelCombiner> tasks = new ArrayList<SenseParallelCombiner>();
			for (int i = 0; i < sensesByColor.size(); i++) {
				String currKey = iter.next();
				List<Sense> currSenseList = sensesByColor.get(currKey);
				List<Sense> currExistingList = null;
				if (existingSensesByColor != null) {
					currExistingList = existingSensesByColor.get(currKey);
				}
				System.out.println(currKey + ": " + currSenseList.size());
				SenseParallelCombiner spc = new SenseParallelCombiner(currSenseList, currExistingList);
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
		} catch (Exception e) {
			e.printStackTrace();
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
		private final String colorOfThread;
		private final List<Sense> existingSenseInput;
		private boolean exec2DidSomethingFlag = false;
	
		public SenseParallelCombiner(List<Sense> inputList, List<Sense> existingSensesInputList) {
			this.input = inputList;
			this.existingSenseInput = existingSensesInputList;
			PixelColorRange pcr = new PixelColorRange(inputList.get(0).orientation.color);
			this.colorOfThread = pcr.color;
		}
		
		public List<Sense> call() { //uses the sense list where they're all the same color
			List<Sense> output = exec(this.input);
			int prevSize;
			boolean continueFlag = true;
			while (continueFlag){
				continueFlag = false;
				prevSize = output.size();
				output = exec(output);
				System.out.println(colorOfThread + " thread: " + output.size() + " compared to prev size of " + prevSize);
				if (output.size() < prevSize) {
					continueFlag = true;
				}
			}
			if (this.existingSenseInput != null) {
				continueFlag = true;
				while (continueFlag) {
					continueFlag = false;
					prevSize = output.size();
					output = exec2(output, this.existingSenseInput);
					if (this.exec2DidSomethingFlag) {
						continueFlag = true;
						this.exec2DidSomethingFlag = false;
					}
				}
			}
			System.out.println("finished " + colorOfThread + " thread");
			return output;
		}
		
		public List<Sense> exec(List<Sense> sensesIn){
			List<Sense> output = new ArrayList<Sense>();
			List<Integer> usedSenses = new ArrayList<Integer>();
			int blackSensesSize = sensesIn.size();
			for (int i = 0; i < sensesIn.size(); i++) {
				Blob outputBlob = new Blob();
				if (!usedSenses.contains(i)) {
					Sense currSense = sensesIn.get(i);
					outputBlob.pixels.addAll(currSense.blob.pixels);
					outputBlob.edgePixels.addAll(currSense.blob.edgePixels);
					outputBlob.prevColors.add(currSense.orientation.color);
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
								outputBlob.edgePixels.addAll(currCompSense.blob.edgePixels);
								outputBlob.prevColors.add(currCompSense.orientation.color);
								usedSenses.add(j);
							}
						}
					}
					if (this.colorOfThread.equals("Black")) {
						System.out.println("black outputBlob size: " + outputBlob.pixels.size());
						System.out.println("finished black sense " + i + "out of " + blackSensesSize);
					}
					if (outputBlob.pixels.size() > currSense.blob.pixels.size()) {
						//Sense newSense = BlobToSense.getSense(outputBlob);
						Set<Pixel> edgeSet = new LinkedHashSet<>();
						edgeSet.addAll(outputBlob.edgePixels);
						outputBlob.edgePixels.clear();
						outputBlob.edgePixels.addAll(edgeSet);
						Set<Pixel> pixelSet = new LinkedHashSet<>();
						pixelSet.addAll(outputBlob.pixels);
						outputBlob.pixels.clear();
						outputBlob.pixels.addAll(pixelSet);
						Sense newSense = SenseCombinerBlobToSense.getSense(outputBlob);
						output.add(newSense);
					} else {
						output.add(currSense);
					}
				}
			}
			
			return output;
		}
		
		public List<Sense> exec2(List<Sense> sensesIn, List<Sense> existingSensesIn){
			List<Sense> output = new ArrayList<Sense>();
			List<Integer> usedSenses = new ArrayList<Integer>();
			int blackSensesSize = sensesIn.size();
			for (int i = 0; i < sensesIn.size(); i++) {
				Blob outputBlob = new Blob();
				if (!usedSenses.contains(i)) {
					Sense currSense = sensesIn.get(i);
					outputBlob.pixels.addAll(currSense.blob.pixels);
					outputBlob.edgePixels.addAll(currSense.blob.edgePixels);
					BoundingBox currSenseBox = currSense.orientation.boundingBox;
					for (int j = 0; j < existingSensesIn.size(); j++) {
						Sense currCompSense = existingSensesIn.get(j);
						if (!usedSenses.contains(j) && !currCompSense.equals(currSense)) {
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
							
							if ((yTouching && xWithinRange) || (xTouching && yWithinRange) || (xWithinRange && yWithinRange)) {
								outputBlob.pixels.addAll(currCompSense.blob.pixels);
								outputBlob.edgePixels.addAll(currCompSense.blob.edgePixels);
								usedSenses.add(j);
							}
						}
					}
					if (this.colorOfThread.equals("Black")) {
						System.out.println("black outputBlob size: " + outputBlob.pixels.size());
						System.out.println("finished black sense " + i + "out of " + blackSensesSize);
					}
					Set<Pixel> edgeSet = new LinkedHashSet<>();
					edgeSet.addAll(outputBlob.edgePixels);
					outputBlob.edgePixels.clear();
					outputBlob.edgePixels.addAll(edgeSet);
					Set<Pixel> pixelSet = new LinkedHashSet<>();
					pixelSet.addAll(outputBlob.pixels);
					outputBlob.pixels.clear();
					outputBlob.pixels.addAll(pixelSet);
					if (outputBlob.pixels.size() > currSense.blob.pixels.size()) {
						//Sense newSense = BlobToSense.getSense(outputBlob);
						Sense newSense = SenseCombinerBlobToSense.getSense(outputBlob);
						output.add(newSense);
						this.exec2DidSomethingFlag = true;
					} else {
						output.add(currSense);
					}
				}
			}
			return output;
		}
		
	}
}
