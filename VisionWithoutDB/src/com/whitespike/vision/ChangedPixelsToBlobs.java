package com.whitespike.vision;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.whitespike.vision.ChangedPixelsFromOldEnv.junctionList;
import com.whitespike.visionstructure.*;
import com.whitespike.vision.BufferedImageToBlobsInParallel;
/**
 * Service method that takes in the changed pixels and turns them into blobs.
 * @author Jonah Schreiner
 *
 */
public class ChangedPixelsToBlobs {
	public static class ChangedPixelsBlobParallelFinder implements Callable<ChangedPixelsBlobThreadResult> {
		private final int iValue;
		private final BufferedImage initialImage;
		private BufferedImage image;
		private final int numOfThreads;
		int minY;
		int maxY;
		List<Pixel> changedPixels;
		public ChangedPixelsBlobParallelFinder(int iValueIn, BufferedImage imageIn, int numOfThreadsIn, junctionList changedPixelsIn) {
			this.iValue = iValueIn;
			this.changedPixels = changedPixelsIn.pixels;
			this.initialImage = imageIn;
			this.numOfThreads = numOfThreadsIn;
			this.minY = (this.initialImage.getHeight()/this.numOfThreads) * this.iValue;
			this.maxY = this.minY + (this.initialImage.getHeight()/this.numOfThreads) - 1;
			this.image = this.initialImage.getSubimage(0, this.minY, this.initialImage.getWidth(), (this.maxY - minY) + 1);
		}
		/**
		 * Finds the changed pixel blobs for this object's associated row.
		 */
		public ChangedPixelsBlobThreadResult call() {
			List<Blob> blobsOut = new ArrayList<Blob>();
			while(this.changedPixels.size() > 0) { //looping through all changed pixels in the thread
				List<Pixel> blobPixelToCheckQueue = new ArrayList<Pixel>();
				Pixel initialPixel = this.changedPixels.get(0);
				this.changedPixels.remove(0);
				blobPixelToCheckQueue.add(initialPixel);
				Blob currentBlob = new Blob();
				PixelColorRange range = new PixelColorRange(initialPixel.color);//add initial pixel remove from changedPixels list right after this
				List<Pixel> pixelsChecked = new ArrayList<Pixel>();
				
				while(blobPixelToCheckQueue.size() > 0) { //loop through pixels that may be in the blob
					
					Pixel currentPixel = blobPixelToCheckQueue.get(0);
					pixelsChecked.add(new Pixel(new Point(currentPixel.position.x, currentPixel.position.y)));
					
					//add currentPixel to blob (and changing the position data so it matches the global position instead of the subimage
					Pixel globalPixel = new Pixel(new Point(currentPixel.position.x, currentPixel.position.y), currentPixel.color);
					currentBlob.pixels.add(globalPixel);
					
					
					//storing the current pixel global value? and adjusting it to be its subimage value
					int currPixInitY = currentPixel.position.y;
					currentPixel.position.y = currentPixel.position.y % 10;

					List<Pixel> touchingPixels = BufferedImageToBlobsInParallel.getTouchingPixels(currentPixel, this.image);
					for (int i = 0; i < touchingPixels.size(); i++) { //loop through every pixel touching the current pixel
						
						
						Pixel currentTouchingPixel = touchingPixels.get(i);
						try {
							//get and insert the color value for the touching pixel into its container
							currentTouchingPixel.color = new Color(this.image.getRGB(currentTouchingPixel.position.x, currentTouchingPixel.position.y));
							
							
							if(BufferedImageToBlobsInParallel.PixelWithinRange(currentTouchingPixel, range)) {
								//if touching pixel is the same color as the initial pixel
								
								
								//adds the touching pixel to the pixels that need to be checked and removes it from
								//the initial pixels list so this blob isn't duplicated
								Pixel initCurrTP = new Pixel(new Point(currentTouchingPixel.position.x, currPixInitY + (currentTouchingPixel.position.y - currentPixel.position.y)), currentTouchingPixel.color);
								if (!blobPixelToCheckQueue.contains(initCurrTP) && !pixelsChecked.contains(initCurrTP)) {
									blobPixelToCheckQueue.add(initCurrTP);
								}
								if (changedPixels.contains(initCurrTP)) {
									changedPixels.remove(initCurrTP);
								}	
							}
							
							
						} catch (Exception e) {
							//that pixel doesn't exist (trying a pixel with a negative x or y value when doing an edge pixel)
						}
					}
					blobPixelToCheckQueue.remove(0);
				}
				
				if (currentBlob.pixels.size() > 1) {
					blobsOut.add(currentBlob);
				}
			}
			ChangedPixelsBlobThreadResult cpbtr = new ChangedPixelsBlobThreadResult(this.iValue, blobsOut);
			return cpbtr;
		}	
	}
	/**
	 * Service method that takes in the changed pixels and turns them into blobs. (Parallel processing driver)
	 * @param changedPixelsByThread Input from "ChangedPixelsFromOldEnv" class.
	 * @param imageIn Current visual input.
	 * @return The blobs found.
	 */
	public static List<Blob> exec(List<junctionList> changedPixelsByThread, BufferedImage imageIn){
		List<Blob> output = new ArrayList<Blob>();
		
		int numOfThreads = (imageIn.getHeight() / 10);

		//Parallel execution
		ExecutorService EXEC = Executors.newFixedThreadPool(numOfThreads);
		List<Callable<ChangedPixelsBlobThreadResult>> tasks = new ArrayList<Callable<ChangedPixelsBlobThreadResult>>();
		for (int i = 0; i < numOfThreads; i++) {
			ChangedPixelsBlobParallelFinder bpf = new ChangedPixelsBlobParallelFinder(i, imageIn, numOfThreads, changedPixelsByThread.get(i));
			tasks.add(bpf);
		}
		
		List<Future<ChangedPixelsBlobThreadResult>> blobsOutFromParallel;
		List<ChangedPixelsBlobThreadResult> blobThreadResults = new ArrayList<ChangedPixelsBlobThreadResult>();
		try {
			blobsOutFromParallel = EXEC.invokeAll(tasks);
			for (Future<ChangedPixelsBlobThreadResult> FutureResult : blobsOutFromParallel) {
				ChangedPixelsBlobThreadResult futureResult = FutureResult.get();
				blobThreadResults.add(futureResult);
				output.addAll(futureResult.blobList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EXEC.shutdown();
		}
		
		return output;
	}
}
