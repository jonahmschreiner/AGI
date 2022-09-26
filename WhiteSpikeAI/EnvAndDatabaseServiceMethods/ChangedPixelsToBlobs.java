package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import EnvAndDatabaseServiceMethods.BufferedImageToBlobsInParallel.BlobParallelFinder;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv.junctionList;
import Structure.Blob;
import Structure.BlobThreadResult;
import Structure.ChangedPixelsBlobThreadResult;
import Structure.Pixel;
import Structure.PixelColorRange;
import EnvAndDatabaseServiceMethods.BufferedImageToBlobsInParallel;

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
			this.image = this.initialImage.getSubimage(0, this.minY, this.initialImage.getWidth() - 1, (this.maxY - minY) + 1);
//			System.out.println(this.iValue + ", " + this.minY + ", " + this.maxY);
		}
		public ChangedPixelsBlobThreadResult call() {
			List<Blob> blobsOut = new ArrayList<Blob>();
			
			while(changedPixels.size() > 0) {
				List<Pixel> blobPixelToCheckQueue = new ArrayList<Pixel>();
				Pixel initialPixel = changedPixels.get(0);	
				blobPixelToCheckQueue.add(initialPixel);
				Blob currentBlob = new Blob();
				PixelColorRange range = new PixelColorRange(initialPixel.color);
				while(blobPixelToCheckQueue.size() > 0) {
					Pixel currentPixel = blobPixelToCheckQueue.get(0);
					Pixel globalPixel = new Pixel(new Point(currentPixel.position.x, currentPixel.position.y + this.minY), currentPixel.color);
					currentBlob.pixels.add(globalPixel);
					List<Pixel> touchingPixels = BufferedImageToBlobsInParallel.getTouchingPixels(currentPixel, this.image);
					for (int i = 0; i < touchingPixels.size(); i++) {
						Pixel currentTouchingPixel = touchingPixels.get(i);
						try {
							currentTouchingPixel.color = new Color(this.image.getRGB(currentTouchingPixel.position.x, currentTouchingPixel.position.y));
							if(BufferedImageToBlobsInParallel.PixelWithinRange(currentTouchingPixel, range)) {
								if (changedPixels.contains(currentTouchingPixel)) {
									if (!blobPixelToCheckQueue.contains(currentTouchingPixel)) {
										blobPixelToCheckQueue.add(currentTouchingPixel);
									}
									changedPixels.remove(currentTouchingPixel);
								}	
							}
						} catch (Exception e) {
							System.out.println("HERE2");
							System.out.println(e);
							//that pixel doesn't exist (trying a pixel with a negative x or y value when doing an edge pixel)
						}
					}
					blobPixelToCheckQueue.remove(currentPixel);
					changedPixels.remove(currentPixel);
				}
				
				if (currentBlob.pixels.size() > 1) {
					blobsOut.add(currentBlob);
//					System.out.println("blob found in thread" + this.iValue + ": current total of " + blobsOut.size());
//					System.out.println("mainList pixels remaining for thread" + this.iValue + ": " + mainList.size());
				}
			}
			
			ChangedPixelsBlobThreadResult cpbtr = new ChangedPixelsBlobThreadResult(this.iValue, blobsOut);
			return cpbtr;
		}	
	}
	
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
				//System.out.println("future list found, size: " + futureListBlob.size());
				blobThreadResults.add(futureResult);
				output.addAll(futureResult.blobList);
			}
		} catch (Exception e) {
			System.out.println("HERE");
			e.printStackTrace();
		} finally {
			EXEC.shutdown();
		}
		
		return output;
	}
}
