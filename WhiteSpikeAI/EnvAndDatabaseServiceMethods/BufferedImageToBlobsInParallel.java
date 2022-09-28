package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import Structure.Sense;

public class BufferedImageToBlobsInParallel {
	public static class BlobParallelCombiner implements Callable<BlobCombinerThreadResult>{
		private final int iValue;
		private final List<BlobThreadResult> blobThreadResults;
		private final int minX;
		private final int maxX;
		private BufferedImage testImage;//remove testImage and its parameter after testing is complete
		public BlobParallelCombiner(int iValueIn, List<BlobThreadResult> blobThreadResultsIn, int minXIn, int maxXIn, BufferedImage testImageIn) {
			this.iValue = iValueIn;
			this.blobThreadResults = blobThreadResultsIn;
			this.minX = minXIn;
			this.maxX = maxXIn;
			this.testImage = testImageIn;
		}
		
		public BlobCombinerThreadResult call() {
			List<Blob> outputBlobs = new ArrayList<Blob>();
			for (int i = 0; i < blobThreadResults.size() - 1; i++) {
				System.out.println("processed threadFinderResult " + i + " for combinerThread " + this.iValue);
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
//							for (int l = 0; l < bottomRowBlob.pixels.size(); l++) {
//								Pixel currPixel = bottomRowBlob.pixels.get(l);
//								if (currentBottomRowPixelQueue.contains(currPixel)) {
//									currentBottomRowPixelQueue.remove(currentBottomRowPixelQueue.indexOf(currPixel));
//								}
//							}
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
					
					//combine found blobs (might be able to remove the second addAll to make it faster)
					if (bottomRowBlob != null && topRowBlob1 != null) {
						//List<Pixel> tempPixelList = new ArrayList<Pixel>();
						//tempPixelList.addAll(bottomRowBlob.pixels);
						bottomRowBlob.pixels.addAll(topRowBlob1.pixels);
						//topRowBlob1.pixels.addAll(tempPixelList);
					}
					
					if (bottomRowBlob != null && topRowBlob2 != null) {
						//List<Pixel> tempPixelList = new ArrayList<Pixel>();
						//tempPixelList.addAll(bottomRowBlob.pixels);
						bottomRowBlob.pixels.addAll(topRowBlob2.pixels);
						//topRowBlob2.pixels.addAll(tempPixelList);
					}

					if (bottomRowBlob != null) {
						outputBlobs.add(bottomRowBlob);
					}
					
				}
			}
			
			List<Sense> sensesToPrint = new ArrayList<Sense>();
			for (int i = 0; i < outputBlobs.size(); i++) {
				Sense sense = BlobToSense.getSense(outputBlobs.get(i));
				sensesToPrint.add(sense);
			}
			VisualOutputOfSensesFromSensesAndImage.execute(sensesToPrint, this.testImage, "combinerThread" + this.iValue);

			BlobCombinerThreadResult result = new BlobCombinerThreadResult(iValue, outputBlobs);
			System.out.println("result returned");
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
							System.out.println("HERE2");
							System.out.println(e);
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
			
//			List<Sense> sensesToPrint = new ArrayList<Sense>();
//			for (int i = 0; i < blobsOut.size(); i++) {
//				Sense sense = BlobToSense.getSense(blobsOut.get(i));
//				sensesToPrint.add(sense);
//			}
//			VisualOutputOfSensesFromSensesAndImage.execute(sensesToPrint, this.image, "finderThread" + this.iValue);
//			
//			if (this.iValue == 1) {
//				System.out.println();
//			}
			
			List<Pixel> topRow = getTopRowOfPixels(this.image, this.minY);
			List<Pixel> bottomRow = getBottomRowOfPixels(this.image, this.maxY);
			BlobThreadResult result = new BlobThreadResult(this.iValue, blobsOut, topRow, bottomRow);
			return result;
		}
	}
	
	public static List<Blob> getBlobsFromImage(BufferedImage imageIn){
		List<Blob> blobsToReturn = new ArrayList<Blob>();
		int numOfThreads = (imageIn.getHeight() / 10);

		//int numOfCombiningThreads = imageIn.getWidth() / 10;
		//int numOfCombiningThreads = 2;
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
				blobsToReturn.addAll(futureResult.blobList);
			}
		} catch (Exception e) {
			System.out.println("HERE");
			e.printStackTrace();
		} finally {
			EXEC.shutdown();
		}
//		System.out.println("all threads done, time to combine them");
		
		//combine thread result blobs in parallel
//		ExecutorService EXEC2 = Executors.newFixedThreadPool(numOfCombiningThreads);
//		List<Callable<BlobCombinerThreadResult>> combiningTasks = new ArrayList<Callable<BlobCombinerThreadResult>>();
//		int xRangePerThread = imageIn.getWidth()/numOfCombiningThreads;
//		for (int i = 0; i < numOfCombiningThreads; i++) {
//			int minX = i * xRangePerThread;
//			if (i < numOfCombiningThreads - 1) {
//				BlobParallelCombiner bpc = new BlobParallelCombiner(i, blobThreadResults, minX, minX + xRangePerThread, imageIn);
//				combiningTasks.add(bpc);
//			} else {
//				BlobParallelCombiner bpc = new BlobParallelCombiner(i, blobThreadResults, minX, imageIn.getWidth() - 1, imageIn);
//				combiningTasks.add(bpc);
//			}
//
//		}
//		
//		List<Future<BlobCombinerThreadResult>> combinersOutFromParallel;
//		List<BlobCombinerThreadResult> blobCombinerThreadResults = new ArrayList<BlobCombinerThreadResult>();
//		try {
//			combinersOutFromParallel = EXEC2.invokeAll(combiningTasks);
//			for (Future<BlobCombinerThreadResult> FutureResult : combinersOutFromParallel) {
//				BlobCombinerThreadResult combinerFutureResult = FutureResult.get();
//				blobCombinerThreadResults.add(combinerFutureResult);
//				System.out.println("future result added");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			EXEC2.shutdown();
//		}
		
		

		
		
		
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
//		for (int k = 0; k < blobCombinerThreadResults.size(); k++) {
//			BlobCombinerThreadResult currResult = blobCombinerThreadResults.get(k);
//			List<Blob> blobsToAdd = removeDuplicateAndCompositionBlobsAndCombineLinkedBlobs(currResult.blobList);
//			blobsToReturn.addAll(blobsToAdd);
//
//		}	
//		blobsToReturn = removeDuplicateAndCompositionBlobsAndCombineLinkedBlobs(blobsToReturn);
//		System.out.println("done combining");
		
		
		return blobsToReturn;
	}
	
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
	
	public static boolean PixelWithinRange(Pixel pixelIn, PixelColorRange rangeIn) {
		boolean output = false;
		PixelColorRange pixelColor = new PixelColorRange(pixelIn.color);
		if (pixelColor.color.equals(rangeIn.color)) {
			output = true;
		}
		return output;
	}
	
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
	
	private static List<Pixel> getTopRowOfPixels(BufferedImage bImageIn, int minYIn){
		List<Pixel> pixelListToReturn = new ArrayList<Pixel>();
		for (int j = 0; j < bImageIn.getWidth(); j++) {
			Point positionOfPixel = new Point(j, minYIn);
			Pixel pixelToAdd = new Pixel(positionOfPixel, new Color(bImageIn.getRGB(j, 0)));
			pixelListToReturn.add(pixelToAdd);
		}
		return pixelListToReturn;
	}
	
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
	
	
	
	//for testing
	public static void main (String[] args) {
		Blob blob1 = new Blob();
		Pixel b1p1 = new Pixel(new Point(0,0));
		blob1.pixels.add(b1p1);
		Pixel b1p2 = new Pixel(new Point(1,0));
		blob1.pixels.add(b1p2);
//		Pixel b1p3 = new Pixel(new Point(0,1));
//		blob1.pixels.add(b1p3);
		
		Blob blob2 = new Blob();
		Pixel b2p1 = new Pixel(new Point(1,0));
		blob2.pixels.add(b2p1);
		Pixel b2p2 = new Pixel(new Point(0,0));
		blob2.pixels.add(b2p2);
		Pixel b2p3 = new Pixel(new Point(0,1));
		blob2.pixels.add(b2p3);
		
		
		List<Blob> blobsToTest = new ArrayList<Blob>();
		blobsToTest.add(blob1);
		blobsToTest.add(blob2);
		
		List<Blob> blobsOut = removeDuplicateAndCompositionBlobsAndCombineLinkedBlobs(blobsToTest);
		System.out.println(blobsOut.size());
	}
}
