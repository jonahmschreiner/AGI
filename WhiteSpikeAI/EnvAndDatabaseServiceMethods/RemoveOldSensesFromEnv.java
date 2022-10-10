package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import Structure.AbstractEnv;
import Structure.Blob;
import Structure.BoundingBox;
import Structure.DBObjectCountResults;
import Structure.Env;
import Structure.Pixel;
import Structure.PixelColorRange;
import Structure.Sense;
import EnvAndDatabaseServiceMethods.UpdateSenses;
import MainLLF.Constants;

public class RemoveOldSensesFromEnv {
	public static Env exec(List<Sense> newSensesIn, Env oldEnvIn){
		
		try {
			List<Sense> sensesCopied = new ArrayList<Sense>();
			sensesCopied.addAll(oldEnvIn.abstractEnv.senses);
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=0;";
			Statement removeChecksState = myConnection.createStatement();
			removeChecksState.execute(removeForeignKeyChecksCommand);
			for (int i = 0; i < sensesCopied.size(); i++) {
				Sense currSense = sensesCopied.get(i);
				List<Sense> overlappingSenses = getOverlappingSenses(newSensesIn, currSense);
				boolean flag2 = true;
				for (int l = 0; l < overlappingSenses.size(); l++) {
					Sense currOvSense2 = overlappingSenses.get(l);
					if (currOvSense2.blob.pixels.containsAll(currSense.blob.pixels) && !currOvSense2.blob.pixels.equals(currSense.blob.pixels)) {
						removeSenseFromAbstractEnvDBSenseListInJavaAndDB(currSense.dbId, oldEnvIn);
						oldEnvIn.abstractEnv.senses.remove(currSense);
						flag2 = false;
					}
				}
				
				
				if (overlappingSenses.size() > 0 && !newSensesIn.contains(currSense) && flag2) {
					boolean flag = false;
					for (int j = 0; j < currSense.blob.pixels.size(); j++) {
						Pixel currPixel = currSense.blob.pixels.get(j);
						Color checkRGB = new Color (oldEnvIn.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50).getRGB(currPixel.position.x, currPixel.position.y));
						PixelColorRange currPixelPCR = new PixelColorRange(currPixel.color);
						PixelColorRange checkRGBPCR = new PixelColorRange(checkRGB);
						if (!currPixelPCR.color.equals(checkRGBPCR.color)) {
							flag = true;
							break;
						}
					}				
					if (flag) {
						removeSenseFromAbstractEnvDBSenseListInJavaAndDB(currSense.dbId, oldEnvIn);
						oldEnvIn.abstractEnv.senses.remove(currSense);
					} else {
						
					}
				}
			}
			DBObjectCountResults dbocr = new DBObjectCountResults();
			String updateEnvSQLCommand = "UPDATE Env SET Senses=\"" + oldEnvIn.abstractEnv.dbSenseList + "\" WHERE id=" + dbocr.envCount + ";";
			myState.execute(updateEnvSQLCommand);
			String readdForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
			Statement readdChecksState = myConnection.createStatement();
			readdChecksState.execute(readdForeignKeyChecksCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return oldEnvIn;
	}
	
	public static void removeSenseFromAbstractEnvDBSenseListInJavaAndDB (int dbIdIn, Env envIn) {
		//remove from java env db sense list
		String[] array = envIn.abstractEnv.dbSenseList.split(" ");
		List<String> strList = Arrays.asList(array);
		List<String> strList2 = new ArrayList<String>(strList);
		strList2.remove("" + dbIdIn);
		String composite = "";
		for (int i = 0; i < strList2.size(); i++) {
			composite = composite + strList2.get(i) + " ";
		}
		envIn.abstractEnv.dbSenseList = composite;
		
		//update env sense list in db
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "UPDATE Env SET Senses=\"" + envIn.abstractEnv.dbSenseList + "\" WHERE id=" + envIn.dbId + ";";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Blob> findBlobOfSameColor (List<Pixel> pixelsToCheckQueue, BufferedImage imageIn) {
		//For Testing
		BufferedImage bi = new BufferedImage(50, 50, 1);
		for (int i = 0; i < pixelsToCheckQueue.size(); i++) {
			Pixel p = pixelsToCheckQueue.get(i);
			bi.setRGB(p.position.x, p.position.y, p.color.hashCode());
		}
		File outputFile = new File("/home/agi/Desktop/testingthis.jpg");
		File outputFile2 = new File("/home/agi/Desktop/againstthis.jpg");
		try {
			ImageIO.write(bi, "jpg", outputFile);
			ImageIO.write(imageIn, "jpg", outputFile2);
		} catch (Exception e) {
			
		}
		
		//
		
		List<Blob> blobsOut = new ArrayList<Blob>();
		while(pixelsToCheckQueue.size() > 0) { //looping through all changed pixels in the thread
			List<Pixel> blobPixelToCheckQueue = new ArrayList<Pixel>();
			Pixel initialPixel = pixelsToCheckQueue.get(0);
			pixelsToCheckQueue.remove(0);
			blobPixelToCheckQueue.add(initialPixel);
			Blob currentBlob = new Blob();
			PixelColorRange range = new PixelColorRange(initialPixel.color);//add initial pixel remove from changedPixels list right after this
			List<Pixel> pixelsChecked = new ArrayList<Pixel>();
			
			while(blobPixelToCheckQueue.size() > 0) { //loop through pixels that may be in the blob
				Pixel currentPixel = blobPixelToCheckQueue.get(0);
				pixelsChecked.add(new Pixel(new Point(currentPixel.position.x, currentPixel.position.y)));
				currentBlob.pixels.add(currentPixel);
				
				

				List<Pixel> touchingPixels = BufferedImageToBlobsInParallel.getTouchingPixels(currentPixel, imageIn);
				for (int i = 0; i < touchingPixels.size(); i++) { //loop through every pixel touching the current pixel
					
					
					Pixel currentTouchingPixel = touchingPixels.get(i);
					try {
						//get and insert the color value for the touching pixel into its container
						currentTouchingPixel.color = new Color(imageIn.getRGB(currentTouchingPixel.position.x, currentTouchingPixel.position.y));
						
						
						if(BufferedImageToBlobsInParallel.PixelWithinRange(currentTouchingPixel, range)) {
							//if touching pixel is the same color as the initial pixel
							
							
							//adds the touching pixel to the pixels that need to be checked and removes it from
							//the initial pixels list so this blob isn't duplicated
							Pixel initCurrTP = new Pixel(new Point(currentTouchingPixel.position.x, currentTouchingPixel.position.y), currentTouchingPixel.color);
							if (!blobPixelToCheckQueue.contains(initCurrTP) && !pixelsChecked.contains(initCurrTP)) {
								blobPixelToCheckQueue.add(initCurrTP);
							}
							if (pixelsToCheckQueue.contains(initCurrTP)) {
								pixelsToCheckQueue.remove(initCurrTP);
							}	
						} else {
							if (pixelsToCheckQueue.contains(currentTouchingPixel)) {
								pixelsToCheckQueue.remove(currentTouchingPixel);
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
		return blobsOut;
	}
	
	
	
	
	public static List<Sense> getOverlappingSenses (List<Sense> sensesIn, Sense currSense) {
		boolean output = false;
		List<Sense> sensesOut = new ArrayList<Sense>();
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currSensesInSense = sensesIn.get(i);
			output = boxesOverlap(currSensesInSense.orientation.boundingBox, currSense.orientation.boundingBox);
			if (output) {
				sensesOut.add(currSensesInSense);
				output = false;
			}
		}
		return sensesOut;
	}
	
	public static boolean boxesOverlap (BoundingBox newSense, BoundingBox potOldSense) {
		boolean output = false;
		if (xInRange(newSense, potOldSense) && yInRange(newSense, potOldSense)) {
			output = true;
		}
		return output;
	}
	
	public static boolean xInRange(BoundingBox newSense, BoundingBox potOldSense) {
		boolean output = false;
		if ((newSense.maxX >= potOldSense.minX && newSense.maxX <= potOldSense.maxX)
				|| newSense.minX >= potOldSense.minX && newSense.minX <= potOldSense.maxX
				|| newSense.minX < potOldSense.minX && newSense.maxX > potOldSense.maxX) {
			output = true;
		}
		return output;
	}
	
	public static boolean yInRange(BoundingBox newSense, BoundingBox potOldSense) {
		boolean output = false;
		if ((newSense.maxY >= potOldSense.minY && newSense.maxY <= potOldSense.maxY)
				|| newSense.minY >= potOldSense.minY && newSense.minY <= potOldSense.maxY
				|| newSense.minY < potOldSense.minY && newSense.maxY > potOldSense.maxY) {
			output = true;
		}
		return output;
	}
}
