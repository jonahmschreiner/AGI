package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Structure.Blob;
import Structure.Env;
import Structure.Pixel;
import Structure.PixelColorRange;

public class BufferedImageToBlobs {
	public static List<Blob> getBlobsFromImage(BufferedImage imageIn){
		List<Blob> blobsToReturn = new ArrayList<Blob>();
		List<Pixel> mainList = getPixelListFromImage(imageIn);
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
						currentTouchingPixel.color = new Color(imageIn.getRGB(currentTouchingPixel.position.x, currentTouchingPixel.position.y));
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
				blobsToReturn.add(currentBlob);
			}
		}
		return blobsToReturn;
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
	
	
	//for testing
	public static void main(String[] args) {
		Env env = new Env();
		List<Blob> resultBlobs = BufferedImageToBlobs.getBlobsFromImage(env.rawEnv.currentDisplay.getSubimage(0, 0, 200, 200));
		System.out.println("test");
	}
}
