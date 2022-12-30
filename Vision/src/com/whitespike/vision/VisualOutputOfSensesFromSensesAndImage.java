package com.whitespike.vision;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import com.whitespike.visionstructure.Sense;

public class VisualOutputOfSensesFromSensesAndImage {
	public static void execute(List<Sense> sensesIn, BufferedImage image1In, String imageNameIn) {
		//create deep copy of buffered image
		ColorModel cm = image1In.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		
		//for testing
		Rectangle rec = new Rectangle(
			      Toolkit.getDefaultToolkit().getScreenSize());
			    Robot robot;
				try {
					robot = new Robot();
					image1In = robot.createScreenCapture(rec);
				} catch (AWTException e1) {

				}
			     
		//
		WritableRaster raster = image1In.copyData(null);
		BufferedImage imageIn = new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(605, 271, 468, 398);
		
		//
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
			outputFile = new File("/home/agi/Desktop/output.jpg");
		} else {
			outputFile = new File("/home/agi/Desktop/" + imageNameIn + ".jpg");
		}
		
		try {
		ImageIO.write(imageIn, "jpg", outputFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
