package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Structure.Pixel;

public class ChangedPixelsFromOldEnv {
	
	public static class junctionList {
		List<Pixel> pixels = new ArrayList<Pixel>();
		public junctionList() {}
	}
	public static List<junctionList> find(BufferedImage newImage, BufferedImage oldImage){
		int height = newImage.getHeight();
		int width = newImage.getWidth();
		int threadHeight = 10;
		//List<List<Pixel>> output = new ArrayList<List<Pixel>>(height/threadHeight);
		List<junctionList> output = Arrays.asList(new junctionList[height/threadHeight]);
		for (int i = 0; i < output.size(); i++) {
			output.set(i, new junctionList());
		}
		//in serial for now (come back and change this to parallel later if slow)

		
		if (height == oldImage.getHeight() && width == oldImage.getWidth()) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (newImage.getRGB(j, i) != oldImage.getRGB(j, i)) {
						Pixel changedPixel = new Pixel(new Point(j, i), new Color(newImage.getRGB(j, i)));
						output.get(i/threadHeight).pixels.add(changedPixel);			
					}
				}
			}
		} else {
			
		}
		
		
		return output;
	}
}
