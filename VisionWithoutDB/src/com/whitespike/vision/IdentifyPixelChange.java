package com.whitespike.vision;

import com.whitespike.visionstructure.*;

import java.awt.Point;
import java.util.*;
/**
 * Service method that turns a list of PixelPosChanges (literal changes) into a list of PixelChanges (Ex: RightTurn, RightDownDiagonal, etc.).
 * @author Jonah Schreiner
 *
 */
public class IdentifyPixelChange {
	/**
	 * Service method that turns a list of PixelPosChanges (literal changes) into a list of PixelChanges (Ex: RightTurn, RightDownDiagonal, etc.).
	 * @param posChangesIn list to turn into a list of PixelChanges.
	 * @return The resulting list of pixelchanges.
	 */
	public static List<PixelChange> exec(List<PixelPosChange> posChangesIn) {
		//None, RightTurn, LeftTurn, RightDownDiagonal, RightUpDiagonal, LeftDownDiagonal, LeftUpDiagonal
		//change1In x: 0 (<>), 1(increase), -1(decrease)
		//change1In y: 0 (<>), 1(increase), -1(decrease)
		//change2In x: 0 (<>), 1(increase), -1(decrease)
		//change2In y: 0 (<>), 1(increase), -1(decrease)\
		//key string: change1InXchange2InXchange1InYchange2InY Examples: 0000, -101-1
		
		//set up hashmap
		HashMap<String, String> pixelChanges = new HashMap<String, String>();
		pixelChanges.put("01-10", "RightTurn");
		pixelChanges.put("1001", "RightTurn");
		pixelChanges.put("0-110", "RightTurn");
		pixelChanges.put("-100-1", "RightTurn");
		pixelChanges.put("11-11", "RightTurn");
		pixelChanges.put("1-111", "RightTurn");
		pixelChanges.put("-1-11-1", "RightTurn");
		pixelChanges.put("-11-1-1", "RightTurn");
		
		
		pixelChanges.put("100-1", "LeftTurn");
		pixelChanges.put("0-1-10", "LeftTurn");
		pixelChanges.put("-1001", "LeftTurn");
		pixelChanges.put("0110", "LeftTurn");
		pixelChanges.put("1-1-1-1", "LeftTurn");
		pixelChanges.put("-1-1-11", "LeftTurn");
		pixelChanges.put("-1111", "LeftTurn");
		pixelChanges.put("111-1", "LeftTurn");
		
		
		pixelChanges.put("1-101", "RightDownDiagonal");
		pixelChanges.put("0-11-1", "RightDownDiagonal");
		pixelChanges.put("-110-1", "RightDownDiagonal");
		pixelChanges.put("01-11", "RightDownDiagonal");
		pixelChanges.put("10-11", "RightDownDiagonal");
		pixelChanges.put("1-110", "RightDownDiagonal");
		pixelChanges.put("-101-1", "RightDownDiagonal");
		pixelChanges.put("-11-10", "RightDownDiagonal");
		
		
		pixelChanges.put("1-10-1", "LeftDownDiagonal");
		pixelChanges.put("011-1", "LeftDownDiagonal");
		pixelChanges.put("-1101", "LeftDownDiagonal");
		pixelChanges.put("0-1-11", "LeftDownDiagonal");
		pixelChanges.put("1-1-10", "LeftDownDiagonal");
		pixelChanges.put("101-1", "LeftDownDiagonal");
		pixelChanges.put("-1110", "LeftDownDiagonal");
		pixelChanges.put("-10-11", "LeftDownDiagonal");
		
		
		pixelChanges.put("1101", "RightUpDiagonal");
		pixelChanges.put("0-111", "RightUpDiagonal");
		pixelChanges.put("-1-10-1", "RightUpDiagonal");
		pixelChanges.put("01-1-1", "RightUpDiagonal");
		pixelChanges.put("11-10", "RightUpDiagonal");
		pixelChanges.put("1011", "RightUpDiagonal");
		pixelChanges.put("-1-110", "RightUpDiagonal");
		pixelChanges.put("-10-1-1", "RightUpDiagonal");
		
		
		pixelChanges.put("110-1", "LeftUpDiagonal");
		pixelChanges.put("0111", "LeftUpDiagonal");
		pixelChanges.put("-1-101", "LeftUpDiagonal");
		pixelChanges.put("0-1-1-1", "LeftUpDiagonal");
		pixelChanges.put("10-1-1", "LeftUpDiagonal");
		pixelChanges.put("1110", "LeftUpDiagonal");
		pixelChanges.put("-1011", "LeftUpDiagonal");
		pixelChanges.put("-1-1-10", "LeftUpDiagonal");
		
		//change2In y: 0 (<>), 1(increase), -1(decrease)\
		//key string: change1InXchange2InXchange1InYchange2InY Examples: 0000, -101-1
		pixelChanges.put("001-1", "TurnAround");
		pixelChanges.put("00-11", "TurnAround");
		pixelChanges.put("1-100", "TurnAround");
		pixelChanges.put("-1100", "TurnAround");
		pixelChanges.put("1-11-1", "TurnAround");
		pixelChanges.put("-111-1", "TurnAround");
		pixelChanges.put("1-1-11", "TurnAround");
		pixelChanges.put("-11-11", "TurnAround");
		
		//convert pixelposchanges into a list of pixelchanges for use in sense identification
		List<PixelChange> pixelChangesOutput = new ArrayList<PixelChange>();
		for (int i = 0; i < posChangesIn.size() - 1; i++) {
			PixelPosChange change1 = posChangesIn.get(i);
			PixelPosChange change2 = posChangesIn.get(i + 1);
			PixelChange pixelChange = new PixelChange();
			if (change1.xChange == change2.xChange && change1.yChange == change2.yChange) {
				pixelChange.setChange("None");
			} else {
				String key = "" + change1.xChange + change2.xChange + change1.yChange + change2.yChange;
				try {
					pixelChange.setChange(pixelChanges.get(key));
				} catch (Exception e) {
					System.out.println();
					e.printStackTrace();
					System.out.println();
				}	
			}
			pixelChangesOutput.add(pixelChange);
		}
		PixelPosChange change1 = posChangesIn.get(posChangesIn.size() - 1);
		PixelPosChange change2 = posChangesIn.get(0);
		PixelChange pixelChange = new PixelChange();
		if (change1.xChange == change2.xChange && change1.yChange == change2.yChange) {
			pixelChange.setChange("None");
		} else {
			String key = "" + change1.xChange + change2.xChange + change1.yChange + change2.yChange;
			pixelChange.setChange(pixelChanges.get(key));
		}
		pixelChangesOutput.add(pixelChange);
		return pixelChangesOutput;
	}
	
	
	//for testing (all good atm)
	public static void main(String[] args) {
		Pixel pixel1 = new Pixel(new Point(1,0));
		Pixel pixel2 = new Pixel(new Point(0,1));
		Pixel pixel3 = new Pixel(new Point(1,1));
		Pixel pixel4 = new Pixel(new Point(2,1));
		PixelPosChange posChange1 = new PixelPosChange(pixel1, pixel2);
		PixelPosChange posChange2 = new PixelPosChange(pixel2, pixel3);
		PixelPosChange posChange3 = new PixelPosChange(pixel3, pixel4);
		PixelPosChange posChange4 = new PixelPosChange(pixel4, pixel1);
		List<PixelPosChange> pixelPosChanges = new ArrayList<PixelPosChange>();
		pixelPosChanges.add(posChange1);
		pixelPosChanges.add(posChange2);
		pixelPosChanges.add(posChange3);
		pixelPosChanges.add(posChange4);
		List<PixelChange> pixelChanges = IdentifyPixelChange.exec(pixelPosChanges);
		for (int i = 0; i < pixelChanges.size(); i++) {
			System.out.println(pixelChanges.get(i).changeType);
		}
		
	}
}
