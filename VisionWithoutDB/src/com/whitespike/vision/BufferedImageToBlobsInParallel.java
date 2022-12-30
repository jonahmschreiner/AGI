package com.whitespike.vision;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.whitespike.visionstructure.*;
/**
 * Service method to extract blobs from bufferedimage using parallel processing.
 * @author Jonah Schreiner
 *
 */
public class BufferedImageToBlobsInParallel {
	/**
	 * Used when combining row blobs into their larger forms using parallel processing.
	 * @author Jonah Schreiner
	 *
	 */
	public static class BlobParallelCombiner implements Callable<BlobCombinerThreadResult>{
		private final int iValue;
		private final List<BlobThreadResult> blobThreadResults;
		private final int minX;
		private final int maxX;
		public BlobParallelCombiner(int iValueIn, List<BlobThreadResult> blobThreadResultsIn, int minXIn, int maxXIn, BufferedImage testImageIn) {
			this.iValue = iValueIn;
			this.blobThreadResults = blobThreadResultsIn;
			this.minX = minXIn;
			this.maxX = maxXIn;
		}
		/**
		 * @return The result from the combining in this thread.
		 */
		public BlobCombinerThreadResult call() {
			List<Blob> outputBlobs = new ArrayList<Blob>();
			for (int i = 0; i < blobThreadResults.size() - 1; i++) {
				if (this.iValue == 0) {
					System.out.println();
				}
				BlobThreadResult currentResult = blobThreadResults.get(i);
				BlobThreadResult nextResult = blobThreadResults.get(i + 1);
				List<Pixel> currentBottomRowPixelQueue = new ArrayList<Pixel>();
				for (int j = minX; j < maxX; j++) {
					currentBottomRowPixelQueue.add(currentResult.bottomRowOfPixels.get(j));
				}
				for (int j = 0; j < currentBottomRowPixelQueue.size(); j++) {
					
					Pixel currentBotRowPixel = currentBottomRowPixelQueue.get(j);
					Pixel currentLeftTopRowPixel = null;
					PixelColorRange currentLeftTopRowPixelRange = null;
					if (j > 0) {
						currentLeftTopRowPixel = nextResult.topRowOfPixels.get(j - 1);
						currentLeftTopRowPixelRange = new PixelColorRange(currentLeftTopRowPixel.color);
					}
					Pixel currentRightTopRowPixel = null;
					PixelColorRange currentRightTopRowPixelRange = null;
					if (j < currentBottomRowPixelQueue.size() - 1) {
						currentRightTopRowPixel = nextResult.topRowOfPixels.get(j + 1);
						currentRightTopRowPixelRange = new PixelColorRange(currentRightTopRowPixel.color);
					}
					Pixel currentMiddleTopRowPixel = nextResult.topRowOfPixels.get(j);
					
					PixelColorRange currentBotRowPixelRange = new PixelColorRange(currentBotRowPixel.color);
					 
					PixelColorRange currentMiddleTopRowPixelRange = new PixelColorRange(currentMiddleTopRowPixel.color);
					 
					boolean leftMatches = false;
					boolean middleMatches = false;
					boolean rightMatches = false;
					if (currentLeftTopRowPixelRange != null) {
						if (currentLeftTopRowPixelRange.equals(currentBotRowPixelRange)) {
							leftMatches = true;
						}
					}
					
					if (currentMiddleTopRowPixelRange.equals(currentBotRowPixelRange)) {
						middleMatches = true;
					}
					
					if (currentRightTopRowPixelRange != null) {
						if (currentRightTopRowPixelRange.equals(currentBotRowPixelRange)) {
							rightMatches = true;
						}
					}
					Blob bottomRowBlob = null;
					for (int k = 0; k < currentResult.blobList.size(); k++) {
						if (currentResult.blobList.get(k).pixels.contains(currentBotRowPixel)) {
							bottomRowBlob = currentResult.blobList.get(k);
							break;
						}
					}
					Blob topRowBlob1 = null;
					for (int k = 0; k < nextResult.blobList.size(); k++) {
						if (nextResult.blobList.get(k).pixels.contains(currentLeftTopRowPixel)) {
							topRowBlob1 = nextResult.blobList.get(k);
							break;
						}
						if (nextResult.blobList.get(k).pixels.contains(currentMiddleTopRowPixel)) {
							topRowBlob1 = nextResult.blobList.get(k);
							break;
						}
						if (nextResult.blobList.get(k).pixels.contains(currentRightTopRowPixel)) {
							topRowBlob1 = nextResult.blobList.get(k);
							break;
						}
					}
					//account for case where the bot row pixel is the only connection between two blobs from
					Blob topRowBlob2 = null;
					if (leftMatches && !middleMatches && rightMatches && topRowBlob1 != null) {
						if (!topRowBlob1.pixels.contains(currentRightTopRowPixel)) {
							for (int k = 0; k < nextResult.blobList.size(); k++) {
								if (nextResult.blobList.get(k).pixels.contains(currentRightTopRowPixel)) {
									topRowBlob2 = nextResult.blobList.get(k);
									break;
								}
							}
						}
					} else if (leftMatches && !middleMatches && rightMatches) {
						for (int k = 0; k < nextResult.blobList.size(); k++) {
							if (nextResult.blobList.get(k).pixels.contains(currentRightTopRowPixel)) {
								topRowBlob1 = nextResult.blobList.get(k);
								break;
							}
						}
					}
					
					if (bottomRowBlob != null && topRowBlob1 != null) {
						bottomRowBlob.pixels.addAll(topRowBlob1.pixels);
					}
					
					if (bottomRowBlob != null && topRowBlob2 != null) {
						bottomRowBlob.pixels.addAll(topRowBlob2.pixels);
					}

					if (bottomRowBlob != null) {
						outputBlobs.add(bottomRowBlob);
					}
					
				}
			}
			BlobCombinerThreadResult result = new BlobCombinerThreadResult(iValue, outputBlobs);
			return result;
		}
	}
	/**
	 * Used when finding blobs from a row using parallel processing.
	 * @author Jonah Schreiner
	 *
	 */
	public static class BlobParallelFinder implements Callable<BlobThreadResult> {
		private final int iValue;
		private final BufferedImage initialImage;
		private BufferedImage image;
		private final int numOfThreads;
		int minY;
		int maxY;
		public BlobParallelFinder(int iValueIn, BufferedImage imageIn, int numOfThreadsIn) {
			this.iValue = iValueIn;
			this.initialImage = imageIn;
			this.numOfThreads = numOfThreadsIn;
			this.minY = (this.initialImage.getHeight()/this.numOfThreads) * this.iValue;
			this.maxY = this.minY + (this.initialImage.getHeight()/this.numOfThreads) - 1;
			this.image = this.initialImage.getSubimage(0, this.minY, this.initialImage.getWidth(), (this.maxY - minY) + 1);
		}
		/**
		 * @return The result of blobs found in this row.
		 */
		public BlobThreadResult call(){
			List<Blob> blobsOut = new ArrayList<Blob>();
			List<Pixel> mainList = getPixelListFromImage(this.image);

			while(mainList.size() > 0) {
				List<Pixel> blobPixelToCheckQueue = new ArrayList<Pixel>();
				Pixel initialPixel = mainList.get(0);	
				blobPixelToCheckQueue.add(initialPixel);
				Blob currentBlob = new Blob();
				PixelColorRange range = new PixelColorRange(initialPixel.color);
				while(blobPixelToCheckQueue.size() > 0) {
					Pixel currentPixel = blobPixelToCheckQueue.get(0);
					Pixel globalPixel = new Pixel(new Point(currentPixel.position.x, currentPixel.position.y + this.minY), currentPixel.color);
					currentBlob.pixels.add(globalPixel);
					List<Pixel> touchingPixels = getTouchingPixels(currentPixel, this.image);
					for (int i = 0; i < touchingPixels.size(); i++) {
						Pixel currentTouchingPixel = touchingPixels.get(i);
						try {
							currentTouchingPixel.color = new Color(this.image.getRGB(currentTouchingPixel.position.x, currentTouchingPixel.position.y));
							if(PixelWithinRange(currentTouchingPixel, range)) {
								if (mainList.contains(currentTouchingPixel)) {
									if (!blobPixelToCheckQueue.contains(currentTouchingPixel)) {
										blobPixelToCheckQueue.add(currentTouchingPixel);
									}
									mainList.remove(currentTouchingPixel);
								}	
							}
						} catch (Exception e) {
							//that pixel doesn't exist (trying a pixel with a negative x or y value when doing an edge pixel)
						}
					}
					blobPixelToCheckQueue.remove(currentPixel);
					mainList.remove(currentPixel);
				}
				
				if (currentBlob.pixels.size() > 1) {
					blobsOut.add(currentBlob);
				}
			}
			
			List<Pixel> topRow = getTopRowOfPixels(this.image, this.minY);
			List<Pixel> bottomRow = getBottomRowOfPixels(this.image, this.maxY);
			BlobThreadResult result = new BlobThreadResult(this.iValue, blobsOut, topRow, bottomRow);
			return result;
		}
	}
	/**
	 * The driving code of the parallel processing for obtaining and combining blobs from an image.
	 * @param imageIn The bufferedimage to extract the blobs from.
	 * @return The blobs found in the image.
	 */
	public static List<Blob> getBlobsFromImage(BufferedImage imageIn){
		List<Blob> blobsToReturn = new ArrayList<Blob>();
		int numOfThreads = (imageIn.getHeight() / 10);

		//Parallel execution
		ExecutorService EXEC = Executors.newFixedThreadPool(numOfThreads);
		List<Callable<BlobThreadResult>> tasks = new ArrayList<Callable<BlobThreadResult>>();
		for (int i = 0; i < numOfThreads; i++) {
			BlobParallelFinder bpf = new BlobParallelFinder(i, imageIn, numOfThreads);
			tasks.add(bpf);
		}
		
		List<Future<BlobThreadResult>> blobsOutFromParallel;
		List<BlobThreadResult> blobThreadResults = new ArrayList<BlobThreadResult>();
		try {
			blobsOutFromParallel = EXEC.invokeAll(tasks);
			for (Future<BlobThreadResult> FutureResult : blobsOutFromParallel) {
				BlobThreadResult futureResult = FutureResult.get();
				blobThreadResults.add(futureResult);
				blobsToReturn.addAll(futureResult.blobList);
			}
		} catch (Exception e) {

		} finally {
			EXEC.shutdown();
		}
		
		return blobsToReturn;
	}
	/**
	 * Cleans up a list of blobs to ensure that they are combined properly and no pixels are used in more than one blob.
	 * @param blobsIn The list of blobs to clean up.
	 * @return The cleaned up list of blobs.
	 */
	public static List<Blob> removeDuplicateAndCompositionBlobsAndCombineLinkedBlobs(List<Blob> blobsIn){
		List<Blob> blobsOut = new ArrayList<Blob>();
		for (int i = 0; i < blobsIn.size(); i++) {
			Blob currBlob = blobsIn.get(i);
			if (!blobsOut.contains(currBlob) && currBlob != null) { //this if statement removes exact duplicates
				boolean wasNotComped = true;
				for (int j = 0; j < blobsOut.size(); j++) { //this for loop removes composition blobs
					Blob currBlobToCheck = blobsOut.get(j);
					boolean potentialComp = false;
					for (Pixel pixelToCheck : currBlobToCheck.pixels) {
						if (currBlob.pixels.contains(pixelToCheck)) {
							potentialComp = true;
							break;
						}
					}
					if (potentialComp) {
						//check if the blobs are linked (they both have unique parts) or if one is a composition
						Set<Pixel> currBlobToCheckSet1 = new HashSet<Pixel>(currBlobToCheck.pixels);
						Set<Pixel> currBlobSet1 = new HashSet<Pixel>(currBlob.pixels);
						Set<Pixel> currBlobToCheckSet2 = new HashSet<Pixel>(currBlobToCheck.pixels);
						Set<Pixel> currBlobSet2 = new HashSet<Pixel>(currBlob.pixels);
						
						currBlobSet1.removeAll(currBlobToCheckSet1);
						currBlobToCheckSet2.removeAll(currBlobSet2);
						if (currBlobSet1.size() == 0 || currBlobToCheckSet2.size() == 0) { //if one is a composition
							if (currBlobToCheck.pixels.size() < currBlob.pixels.size()) {
								blobsOut.remove(j);
								blobsOut.add(currBlob);
							}
						} else { //handle the blobs being linked instead of a composition
							currBlobToCheck.pixels.addAll(currBlobSet1);
						}

						wasNotComped = false;
						break;
					}
				}
				if (wasNotComped) {
					blobsOut.add(currBlob);
				}
			}
		}
		return blobsOut;
	}
	
	
	/**
	 * Service method to turn an image into a list of pixels.
	 * @param bImageIn The image to extract the list of pixels from.
	 * @return The list of pixels extracted.
	 */
	public static List<Pixel> getPixelListFromImage(BufferedImage bImageIn){
		List<Pixel> pixelListToReturn = new ArrayList<Pixel>();
		for (int i = 0; i < bImageIn.getHeight(); i++) {
			for (int j = 0; j < bImageIn.getWidth(); j++) {
				Point positionOfPixel = new Point(j, i);
				Pixel pixelToAdd = new Pixel(positionOfPixel, new Color(bImageIn.getRGB(j, i)));
				pixelListToReturn.add(pixelToAdd);
			}
		}
		return pixelListToReturn;
	}
	/**
	 * Determine if a pixel is approximately a certain color.
	 * @param pixelIn The pixel to test.
	 * @param rangeIn The color to see if the pixel is.
	 * @return Result of if the pixel was the color or not.
	 */
	public static boolean PixelWithinRange(Pixel pixelIn, PixelColorRange rangeIn) {
		boolean output = false;
		PixelColorRange pixelColor = new PixelColorRange(pixelIn.color);
		if (pixelColor.color.equals(rangeIn.color)) {
			output = true;
		}
		return output;
	}
	/**
	 * Get all pixels touching the pixel passed in.
	 * @param pixelIn The pixel that all touching pixels must be touching.
	 * @param imageIn The image to pull the touching pixels from.
	 * @return The list of touching pixels extracted.
	 */
	public static List<Pixel> getTouchingPixels(Pixel pixelIn, BufferedImage imageIn){ //need to come back and add ifs for other touching pixels (pass in BufferedImage)
		List<Pixel> touchingPixels = new ArrayList<Pixel>();
		int minX = imageIn.getMinX();
		int minY = imageIn.getMinY();
		int maxX = imageIn.getWidth() - 1;
		int maxY = imageIn.getHeight() - 1;
		if (pixelIn.position.y > minY) {
			Pixel upPixel = new Pixel(new Point(pixelIn.position.x, pixelIn.position.y - 1));
			touchingPixels.add(upPixel);
		}
		
		if (pixelIn.position.y < maxY) {
			Pixel downPixel = new Pixel(new Point(pixelIn.position.x, pixelIn.position.y + 1));
			touchingPixels.add(downPixel);
		}

		
		if (pixelIn.position.x > minX) {
			Pixel leftPixel = new Pixel(new Point(pixelIn.position.x - 1, pixelIn.position.y));
			touchingPixels.add(leftPixel);
		}

		if (pixelIn.position.x < maxX) {
			Pixel rightPixel = new Pixel(new Point(pixelIn.position.x + 1, pixelIn.position.y));
			touchingPixels.add(rightPixel);
		}
		
		if (pixelIn.position.x > minX && pixelIn.position.y > minY) {
			Pixel upLeftPixel = new Pixel(new Point(pixelIn.position.x - 1, pixelIn.position.y - 1));
			touchingPixels.add(upLeftPixel);
		}

		if (pixelIn.position.x < maxX && pixelIn.position.y > minY) {
			Pixel upRightPixel = new Pixel(new Point(pixelIn.position.x + 1, pixelIn.position.y - 1));
			touchingPixels.add(upRightPixel);
		}

		if (pixelIn.position.y < maxY && pixelIn.position.x > minX) {
			Pixel downLeftPixel = new Pixel(new Point(pixelIn.position.x - 1, pixelIn.position.y + 1));
			touchingPixels.add(downLeftPixel);
		}

		if (pixelIn.position.y < maxY && pixelIn.position.x < maxX) {
			Pixel downRightPixel = new Pixel(new Point(pixelIn.position.x + 1, pixelIn.position.y + 1));
			touchingPixels.add(downRightPixel);	
		}
		return touchingPixels;
	}
	/**
	 * Get the row of pixels from the image at minYIn.
	 * @param bImageIn Image to extract pixels from.
	 * @param minYIn What Y value to extract the row from.
	 * @return The list of pixels extracted.
	 */
	private static List<Pixel> getTopRowOfPixels(BufferedImage bImageIn, int minYIn){
		List<Pixel> pixelListToReturn = new ArrayList<Pixel>();
		for (int j = 0; j < bImageIn.getWidth(); j++) {
			Point positionOfPixel = new Point(j, minYIn);
			Pixel pixelToAdd = new Pixel(positionOfPixel, new Color(bImageIn.getRGB(j, 0)));
			pixelListToReturn.add(pixelToAdd);
		}
		return pixelListToReturn;
	}
	/**
	 * Get the row of pixels from the image at maxYIn.
	 * @param bImageIn Image to extract pixels from.
	 * @param maxYIn What Y value to extract the row from.
	 * @return The list of pixels extracted.
	 */
	public static List<Pixel> getBottomRowOfPixels(BufferedImage bImageIn, int maxYIn){
		List<Pixel> pixelListToReturn = new ArrayList<Pixel>();
		for (int j = 0; j < bImageIn.getWidth(); j++) {
			int height = bImageIn.getHeight() - 1;
			Point positionOfPixel = new Point(j, maxYIn);
			Pixel pixelToAdd = new Pixel(positionOfPixel, new Color(bImageIn.getRGB(j, height)));
			pixelListToReturn.add(pixelToAdd);
		}
		return pixelListToReturn;
	}
}
