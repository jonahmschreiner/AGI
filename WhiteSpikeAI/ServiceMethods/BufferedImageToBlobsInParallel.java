package ServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import javax.imageio.ImageIO;

import Structure.Blob;
import Structure.BlobCombinerThreadResult;
import Structure.BlobThreadResult;
import Structure.Pixel;
import Structure.PixelColorRange;

public class BufferedImageToBlobsInParallel {
	public static class BlobParallelCombiner implements Callable<BlobCombinerThreadResult>{
		private final int iValue;
		private final List<BlobThreadResult> blobThreadResults;
		private final int minX;
		private final int maxX;
		public BlobParallelCombiner(int iValueIn, List<BlobThreadResult> blobThreadResultsIn, int minXIn, int maxXIn) {
			this.iValue = iValueIn;
			this.blobThreadResults = blobThreadResultsIn;
			this.minX = minXIn;
			this.maxX = maxXIn;
		}
		
		public BlobCombinerThreadResult call() {
			List<Blob> outputBlobs = new ArrayList<Blob>();
			for (int i = 0; i < blobThreadResults.size() - 1; i++) {
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
							for (Pixel currPixel : bottomRowBlob.pixels) {
								if (currentBottomRowPixelQueue.contains(currPixel)) {
									currentBottomRowPixelQueue.remove(currentBottomRowPixelQueue.indexOf(currPixel));
								}
							}
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
					//nextThread
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
					
					//combine found blobs
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
			this.image = this.initialImage.getSubimage(0, this.minY, this.initialImage.getWidth() - 1, (this.maxY - minY) + 1);
//			System.out.println(this.iValue + ", " + this.minY + ", " + this.maxY);
		}
		public BlobThreadResult call(){
			// turn subimage into blobs
			List<Blob> blobsOut = new ArrayList<Blob>();
			List<Pixel> mainList = getPixelListFromImage(this.image);
//			System.out.println(mainList.size());

			while(mainList.size() > 0) {
				List<Pixel> blobPixelToCheckQueue = new ArrayList<Pixel>();
				Pixel initialPixel = mainList.get(0);
				blobPixelToCheckQueue.add(initialPixel);
				Blob currentBlob = new Blob();
				PixelColorRange range = new PixelColorRange(initialPixel.color);
				while(blobPixelToCheckQueue.size() > 0) {
					Pixel currentPixel = blobPixelToCheckQueue.get(0);
					currentBlob.pixels.add(currentPixel);
					List<Pixel> touchingPixels = getTouchingPixels(currentPixel);
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
							//System.out.println(e);
							//that pixel doesn't exist (trying a pixel with a negative x or y value when doing an edge pixel)
						}
					}
					blobPixelToCheckQueue.remove(currentPixel);
					mainList.remove(currentPixel);
				}
				
				if (currentBlob.pixels.size() > 1) {
					blobsOut.add(currentBlob);
//					System.out.println("blob found in thread" + this.iValue + ": current total of " + blobsOut.size());
//					System.out.println("mainList pixels remaining for thread" + this.iValue + ": " + mainList.size());
				}
			}
//			System.out.println("blobs returned for thread" + this.iValue);
			
//			List<Sense> sensesToPrint = new ArrayList<Sense>();
//			for (int i = 0; i < blobsOut.size(); i++) {
//				Sense sense = BlobToSense.getSense(blobsOut.get(i));
//				sensesToPrint.add(sense);
//			}
//			VisualOutputOfSensesFromSensesAndImage.execute(sensesToPrint, this.image, "thread" + this.iValue);
//			
//			if (this.iValue == 1) {
//				System.out.println();
//			}
			
			List<Pixel> topRow = getTopRowOfPixels(this.image);
			List<Pixel> bottomRow = getBottomRowOfPixels(this.image);
			BlobThreadResult result = new BlobThreadResult(this.iValue, blobsOut, topRow, bottomRow);
			return result;
		}
	}
	
	public static List<Blob> getBlobsFromImage(BufferedImage imageIn){
		List<Blob> blobsToReturn = new ArrayList<Blob>();
		//TODO determine number of threads based on mainList size
		int numOfThreads = 500;
		int numOfCombiningThreads = 500;
		
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
				//System.out.println("future list found, size: " + futureListBlob.size());
				blobThreadResults.add(futureResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EXEC.shutdown();
		}
		System.out.println("all threads done, time to combine them");
		
		//TODO combine blocks in parallel
		ExecutorService EXEC2 = Executors.newFixedThreadPool(numOfCombiningThreads);
		List<Callable<BlobCombinerThreadResult>> combiningTasks = new ArrayList<Callable<BlobCombinerThreadResult>>();
		int xRangePerThread = imageIn.getWidth()/numOfCombiningThreads;
		for (int i = 0; i < numOfCombiningThreads; i++) {
			int minX = i * xRangePerThread;
			BlobParallelCombiner bpc = new BlobParallelCombiner(i, blobThreadResults, minX, minX + xRangePerThread);
			combiningTasks.add(bpc);
		}
		
		List<Future<BlobCombinerThreadResult>> combinersOutFromParallel;
		List<BlobCombinerThreadResult> blobCombinerThreadResults = new ArrayList<BlobCombinerThreadResult>();
		try {
			combinersOutFromParallel = EXEC2.invokeAll(combiningTasks);
			for (Future<BlobCombinerThreadResult> FutureResult : combinersOutFromParallel) {
				BlobCombinerThreadResult combinerFutureResult = FutureResult.get();
				blobCombinerThreadResults.add(combinerFutureResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			EXEC2.shutdown();
		}
		
		

		
		
		
		//combine blobs with blobs of other threads using blobThreadResults (initially sequentially, then in parallel)
//		for (int i = 0; i < blobThreadResults.size() - 1; i++) {
//			BlobThreadResult currentResult = blobThreadResults.get(i);
//			BlobThreadResult nextResult = blobThreadResults.get(i + 1);
//			List<Pixel> currentBottomRowPixelQueue = new ArrayList<Pixel>();
//			currentBottomRowPixelQueue.addAll(currentResult.bottomRowOfPixels);
//			for (int j = 0; j < currentBottomRowPixelQueue.size(); j++) {
//				Pixel currentBotRowPixel = currentBottomRowPixelQueue.get(j);
//				Pixel currentLeftTopRowPixel = null;
//				PixelColorRange currentLeftTopRowPixelRange = null;
//				if (j > 0) {
//					currentLeftTopRowPixel = nextResult.topRowOfPixels.get(j - 1);
//					currentLeftTopRowPixelRange = new PixelColorRange(currentLeftTopRowPixel.color);
//				}
//				Pixel currentRightTopRowPixel = null;
//				PixelColorRange currentRightTopRowPixelRange = null;
//				if (j < currentBottomRowPixelQueue.size() - 1) {
//					currentRightTopRowPixel = nextResult.topRowOfPixels.get(j + 1);
//					currentRightTopRowPixelRange = new PixelColorRange(currentRightTopRowPixel.color);
//				}
//				Pixel currentMiddleTopRowPixel = nextResult.topRowOfPixels.get(j);
//				
//				PixelColorRange currentBotRowPixelRange = new PixelColorRange(currentBotRowPixel.color);
//				 
//				PixelColorRange currentMiddleTopRowPixelRange = new PixelColorRange(currentMiddleTopRowPixel.color);
//				 
//				boolean leftMatches = false;
//				boolean middleMatches = false;
//				boolean rightMatches = false;
//				if (currentLeftTopRowPixelRange != null) {
//					if (currentLeftTopRowPixelRange.equals(currentBotRowPixelRange)) {
//						leftMatches = true;
//					}
//				}
//				
//				if (currentMiddleTopRowPixelRange.equals(currentBotRowPixelRange)) {
//					middleMatches = true;
//				}
//				
//				if (currentRightTopRowPixelRange != null) {
//					if (currentRightTopRowPixelRange.equals(currentBotRowPixelRange)) {
//						rightMatches = true;
//					}
//				}
//				Blob bottomRowBlob = null;
//				for (int k = 0; k < currentResult.blobList.size(); k++) {
//					if (currentResult.blobList.get(k).pixels.contains(currentBotRowPixel)) {
//						bottomRowBlob = currentResult.blobList.get(k);
//						for (Pixel currPixel : bottomRowBlob.pixels) {
//							if (currentBottomRowPixelQueue.contains(currPixel)) {
//								currentBottomRowPixelQueue.remove(currentBottomRowPixelQueue.indexOf(currPixel));
//							}
//						}
//						break;
//					}
//				}
//				
//				Blob topRowBlob1 = null;
//				for (int k = 0; k < nextResult.blobList.size(); k++) {
//					if (nextResult.blobList.get(k).pixels.contains(currentLeftTopRowPixel)) {
//						topRowBlob1 = nextResult.blobList.get(k);
//						break;
//					}
//					if (nextResult.blobList.get(k).pixels.contains(currentMiddleTopRowPixel)) {
//						topRowBlob1 = nextResult.blobList.get(k);
//						break;
//					}
//					if (nextResult.blobList.get(k).pixels.contains(currentRightTopRowPixel)) {
//						topRowBlob1 = nextResult.blobList.get(k);
//						break;
//					}
//				}
//				
//				//account for case where the bot row pixel is the only connection between two blobs from
//				//nextThread
//				Blob topRowBlob2 = null;
//				if (leftMatches && !middleMatches && rightMatches && topRowBlob1 != null) {
//					if (!topRowBlob1.pixels.contains(currentRightTopRowPixel)) {
//						for (int k = 0; k < nextResult.blobList.size(); k++) {
//							if (nextResult.blobList.get(k).pixels.contains(currentRightTopRowPixel)) {
//								topRowBlob2 = nextResult.blobList.get(k);
//								break;
//							}
//						}
//					}
//				} else if (leftMatches && !middleMatches && rightMatches) {
//					for (int k = 0; k < nextResult.blobList.size(); k++) {
//						if (nextResult.blobList.get(k).pixels.contains(currentRightTopRowPixel)) {
//							topRowBlob1 = nextResult.blobList.get(k);
//							break;
//						}
//					}
//				}
//				
//				//combine found blobs
//				if (bottomRowBlob != null && topRowBlob1 != null) {
//					bottomRowBlob.pixels.addAll(topRowBlob1.pixels);
//				}
//				
//				if (bottomRowBlob != null && topRowBlob2 != null) {
//					bottomRowBlob.pixels.addAll(topRowBlob2.pixels);
//				}
//				
//			}
//		}
			
		//add all blobs to blobsToReturn (avoiding duplicates)
		for (int k = 0; k < blobCombinerThreadResults.size(); k++) {
			BlobCombinerThreadResult currResult = blobCombinerThreadResults.get(k);
			List<Blob> blobsToAdd = removeDuplicateAndCompositionBlobs(currResult.blobList);
			blobsToReturn.addAll(blobsToAdd);

		}	
		blobsToReturn = removeDuplicateAndCompositionBlobs(blobsToReturn);
		System.out.println("done combining");
		return blobsToReturn;
	}
	
	private static List<Blob> removeDuplicateAndCompositionBlobs(List<Blob> blobsIn){
		List<Blob> blobsOut = new ArrayList<Blob>();
		for (int i = 0; i < blobsIn.size(); i++) {
			Blob currBlob = blobsIn.get(i);
			if (!blobsOut.contains(currBlob)) {
				for (Blob currBlobToCheck : blobsOut) {
					for (Pixel pixelToCheck : currBlobToCheck.pixels) {
						if () {
							
						}
					}
				}
			}
		}
		return blobsOut;
	}
	
	
	private static List<Pixel> getPixelListFromImage(BufferedImage bImageIn){
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
	
	private static boolean PixelWithinRange(Pixel pixelIn, PixelColorRange rangeIn) {
		boolean output = false;
		PixelColorRange pixelColor = new PixelColorRange(pixelIn.color);
		if (pixelColor.color.equals(rangeIn.color)) {
			output = true;
		}
		return output;
	}
	
	private static List<Pixel> getTouchingPixels(Pixel pixelIn){
		List<Pixel> touchingPixels = new ArrayList<Pixel>();
		Pixel upPixel = new Pixel(new Point(pixelIn.position.x, pixelIn.position.y - 1));
		touchingPixels.add(upPixel);
		Pixel downPixel = new Pixel(new Point(pixelIn.position.x, pixelIn.position.y + 1));
		touchingPixels.add(downPixel);
		Pixel leftPixel = new Pixel(new Point(pixelIn.position.x - 1, pixelIn.position.y));
		touchingPixels.add(leftPixel);
		Pixel rightPixel = new Pixel(new Point(pixelIn.position.x + 1, pixelIn.position.y));
		touchingPixels.add(rightPixel);
		Pixel upLeftPixel = new Pixel(new Point(pixelIn.position.x - 1, pixelIn.position.y - 1));
		touchingPixels.add(upLeftPixel);
		Pixel upRightPixel = new Pixel(new Point(pixelIn.position.x + 1, pixelIn.position.y - 1));
		touchingPixels.add(upRightPixel);
		Pixel downLeftPixel = new Pixel(new Point(pixelIn.position.x - 1, pixelIn.position.y + 1));
		touchingPixels.add(downLeftPixel);
		Pixel downRightPixel = new Pixel(new Point(pixelIn.position.x + 1, pixelIn.position.y + 1));
		touchingPixels.add(downRightPixel);
		return touchingPixels;
	}
	
	private static List<Pixel> getTopRowOfPixels(BufferedImage bImageIn){
		List<Pixel> pixelListToReturn = new ArrayList<Pixel>();
		for (int j = 0; j < bImageIn.getWidth(); j++) {
			Point positionOfPixel = new Point(j, 0);
			Pixel pixelToAdd = new Pixel(positionOfPixel, new Color(bImageIn.getRGB(j, 0)));
			pixelListToReturn.add(pixelToAdd);
		}
		return pixelListToReturn;
	}
	
	private static List<Pixel> getBottomRowOfPixels(BufferedImage bImageIn){
		List<Pixel> pixelListToReturn = new ArrayList<Pixel>();
		int height = bImageIn.getHeight() - 1;
		for (int j = 0; j < bImageIn.getWidth(); j++) {
			Point positionOfPixel = new Point(j, height);
			Pixel pixelToAdd = new Pixel(positionOfPixel, new Color(bImageIn.getRGB(j, height)));
			pixelListToReturn.add(pixelToAdd);
		}
		return pixelListToReturn;
	}
}
