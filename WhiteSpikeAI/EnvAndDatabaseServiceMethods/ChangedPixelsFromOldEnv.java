package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Structure.Pixel;

public class ChangedPixelsFromOldEnv {
	public static List<List<Pixel>> find(BufferedImage newImage, BufferedImage oldImage){
		int height = newImage.getHeight();
		int width = newImage.getWidth();
		int threadHeight = 10;
		List<List<Pixel>> output = new ArrayList<List<Pixel>>(height/threadHeight);
		
		//in serial for now (come back and change this to parallel later if slow)

		
		if (height == oldImage.getHeight() && width == oldImage.getWidth()) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					if (newImage.getRGB(j, i) != oldImage.getRGB(j, i)) {
						Pixel changedPixel = new Pixel(new Point(j, i), new Color(newImage.getRGB(j, i)));
						output.get(i/threadHeight).add(changedPixel);
					}
				}
			}
		} else {
			
		}
		
		
		return output;
	}
}
