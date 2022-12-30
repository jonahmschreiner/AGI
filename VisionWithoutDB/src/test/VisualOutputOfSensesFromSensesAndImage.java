package test;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import com.whitespike.visionstructure.Sense;


/**
 * Service method that visually outlines senses in an image for debugging purposes. 
 * @author Jonah Schreiner
 *
 */
public class VisualOutputOfSensesFromSensesAndImage {
	/**
	 * Service method that visually outlines senses in an image for debugging purposes.
	 * @param sensesIn Senses to outline.
	 * @param image1In Image to write outlines to (creates a deep copy).
	 * @param imageNameIn The file name of the output image.
	 * @param pathIn The path to where the output image will be created.
	 */
	public static void execute(List<Sense> sensesIn, BufferedImage image1In, String imageNameIn, String pathIn) {
		//create deep copy of buffered image
		ColorModel cm = image1In.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image1In.copyData(null);
		BufferedImage imageIn = new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(605, 271, 468, 398);
		
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currentSense = sensesIn.get(i);
			Point startingPoint = currentSense.orientation.boundingBox.MinXMinY.position;
			if (startingPoint.x < 0) {
				startingPoint.x = 0;
			}
			if (startingPoint.y < 0) {
				startingPoint.y = 0;
			}
			while (startingPoint.x < currentSense.orientation.boundingBox.maxX) {
				try {
				imageIn.setRGB(startingPoint.x, startingPoint.y, new Color(245, 66, 66).getRGB());
				startingPoint.x = startingPoint.x + 1;
				} catch (Exception e) {
					startingPoint.x = startingPoint.x - 1;
					break;
				}
			}
			while (startingPoint.y < currentSense.orientation.boundingBox.maxY) {
				try {
					imageIn.setRGB(startingPoint.x, startingPoint.y, new Color(245, 66, 66).getRGB());
					startingPoint.y = startingPoint.y + 1;
				} catch (Exception e) {
					startingPoint.y = startingPoint.y - 1;
					break;
				}
			}
			while (startingPoint.x > currentSense.orientation.boundingBox.minX) {
				try {	
					imageIn.setRGB(startingPoint.x, startingPoint.y, new Color(245, 66, 66).getRGB());
					startingPoint.x = startingPoint.x - 1;
				} catch (Exception e) {
					startingPoint.x = startingPoint.x + 1;
					break;
				}	
			}
			while (startingPoint.y > currentSense.orientation.boundingBox.minY) {
				try {	
					imageIn.setRGB(startingPoint.x, startingPoint.y, new Color(245, 66, 66).getRGB());
					startingPoint.y = startingPoint.y - 1;
				} catch (Exception e) {
					startingPoint.y = startingPoint.y + 1;
					break;
				}	
			}
		}
		
		File outputFile;
		if (imageNameIn == null) {
			imageNameIn = "output";
		}
		if (pathIn.endsWith("/")) {
			pathIn = pathIn.substring(0, pathIn.length() - 2);
		}
		outputFile = new File(pathIn + "/" + imageNameIn + ".jpg");

		try {
			ImageIO.write(imageIn, "jpg", outputFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
