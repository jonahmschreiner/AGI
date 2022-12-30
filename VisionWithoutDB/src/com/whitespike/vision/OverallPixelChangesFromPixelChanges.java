package com.whitespike.vision;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import com.whitespike.visionstructure.*;
//Straight, RightTurn, LeftTurn, StartLeftUpDiagonal, MaintainLeftUpDiagonal, StartRightUpDiagonal, MaintainRightUpDiagonal, LeftDownDiagonal, RightDownDiagonal
/**
 * Service method that creates a list of PixelOverallChanges from a list of PixelChanges.
 * @author Jonah Schreiner
 *
 */
public class OverallPixelChangesFromPixelChanges {
	/**
	 * Service method that creates a list of PixelOverallChanges from a list of PixelChanges.
	 * @param pixelChangesIn The list of PixelChanges to extract from.
	 * @return The extracted list of PixelOverallChanges.
	 */
	public static List<PixelOverallChange> exec(List<PixelChange> pixelChangesIn){
		//if pixelchange is none and the pixelchange after it isn't none, the overallchange is straight
		
		//if pixel change is none and the pixelchange after it is none, no overallchange is added
		
		//if the pixelchange is rightturn, the overallchange is rightturn
		
		//if the pixelchange is leftturn, the overallchange is leftturn
		
		//if the pixelchange is leftupdiagonal and the one before it wasn't leftupdiagonal, the overallchange is startleftupdiagonal
		
		//if the pixel change is leftupdiagonal and the one before it was leftupdiagonal and the one after it is not, the overallchange is maintainleftupdiagonal
		
		//if the pixel change is leftupdiagonal and the one before it was leftupdiagonal and the one after it is as well, no overallchange is added to the list
		
		//if the pixel change is rightupdiagonal and the one before it was rightupdiagonal and the one after it is not, the overallchange is maintainrightupdiagonal
		
		//if the pixel change is rightupdiagonal and the one before it was rightupdiagonal and the one after it is as well, no overallchange is added to the list
		
		HashMap<String, String> map = getHashMap();
		HashMap<String, Boolean> map2 = new HashMap<String, Boolean>();
		map2.put("0", false);
		map2.put("1", true);
		map2.put("2", true);
		map2.put("3", false);
		map2.put("4", false);
		map2.put("5", true);
		map2.put("6", true);
		map2.put("7", true);
		String prevValue = "";
		String keyValue;
		String keyValue2;
		String changeValue;
		int irregularTurnCode = 0;
		
		List<PixelOverallChange> overallPixelChanges = new ArrayList<PixelOverallChange>();
		for (int i = 1; i < pixelChangesIn.size(); i++) {
			keyValue = "" + pixelChangesIn.get(i - 1).changeCode + pixelChangesIn.get(i).changeCode;
			changeValue = map.get(keyValue);
			if (changeValue == prevValue) {
				prevValue = changeValue;
				keyValue2 = "" + pixelChangesIn.get(i).changeCode;
				if (map2.get(keyValue2)) {
					PixelOverallChange overallChange = new PixelOverallChange(changeValue);
					
					if (irregularTurnCode != 0) {
						if (overallChange.changeType.equals("MaintainRightUpDiagonal") && irregularTurnCode == 1){
							irregularTurnCode = 10;
						}else if(overallChange.changeType.equals("MaintainLeftUpDiagonal") && irregularTurnCode == -1) {
							irregularTurnCode = 11;
						} else {
							if (overallChange.changeType.equals("Straight")) {
								overallPixelChanges.remove(overallPixelChanges.size() - 1);
								overallPixelChanges.remove(overallPixelChanges.size() - 1);
								
								if (irregularTurnCode == 10) {
									PixelOverallChange newTurn = new PixelOverallChange("RightTurn");
									overallPixelChanges.add(newTurn);
								} else if (irregularTurnCode == 11) {
									PixelOverallChange newTurn = new PixelOverallChange("LeftTurn");
									overallPixelChanges.add(newTurn);
								}	
							}
							irregularTurnCode = 0;
						}
					}
					
					if (overallChange.changeType.equals("StartRightUpDiagonal")) {
						irregularTurnCode = 1;
					} else if (overallChange.changeType.equals("StartLeftUpDiagonal")) {
						irregularTurnCode = -1;
					} else {
						irregularTurnCode = 0;
					}
					overallPixelChanges.add(overallChange);
				}
			} else {
				prevValue = changeValue;
				PixelOverallChange overallChange = new PixelOverallChange(changeValue);
				if (irregularTurnCode != 0) {
					if (overallChange.changeType.equals("MaintainRightUpDiagonal") && irregularTurnCode == 1){
						irregularTurnCode = 10;
					}else if(overallChange.changeType.equals("MaintainLeftUpDiagonal") && irregularTurnCode == -1) {
						irregularTurnCode = 11;
					} else {
						if (overallChange.changeType.equals("Straight")) {
							if (irregularTurnCode == 10 || irregularTurnCode == 11) {
								overallPixelChanges.remove(overallPixelChanges.size() - 1);
								overallPixelChanges.remove(overallPixelChanges.size() - 1);
							}
							
							if (irregularTurnCode == 10) {
								PixelOverallChange newTurn = new PixelOverallChange("RightTurn");
								overallPixelChanges.add(newTurn);
							} else if (irregularTurnCode == 11) {
								PixelOverallChange newTurn = new PixelOverallChange("LeftTurn");
								overallPixelChanges.add(newTurn);
							}	
						}
						irregularTurnCode = 0;
					}
				}
				
				if (overallChange.changeType.equals("StartRightUpDiagonal")) {
					irregularTurnCode = 1;
				} else if (overallChange.changeType.equals("StartLeftUpDiagonal")) {
					irregularTurnCode = -1;
				}
				overallPixelChanges.add(overallChange);
			}
		}
		
		keyValue = "" + pixelChangesIn.get(pixelChangesIn.size() - 1).changeCode + pixelChangesIn.get(0).changeCode;
		changeValue = map.get(keyValue);
		if (changeValue == prevValue || changeValue == overallPixelChanges.get(0).changeType) {
			prevValue = changeValue;
			keyValue2 = "" + pixelChangesIn.get(0).changeCode;
			if (map2.get(keyValue2) || irregularTurnCode != 0) {
				PixelOverallChange overallChange = new PixelOverallChange(changeValue);
				if (irregularTurnCode != 0) {
					if (overallChange.changeType.equals("MaintainRightUpDiagonal") && irregularTurnCode == 1){
						irregularTurnCode = 10;
					}else if(overallChange.changeType.equals("MaintainLeftUpDiagonal") && irregularTurnCode == -1) {
						irregularTurnCode = 11;
					} else {
						if (overallChange.changeType.equals("Straight")) {
							if (irregularTurnCode == 10 || irregularTurnCode == 11) {
								overallPixelChanges.remove(overallPixelChanges.size() - 1);
								overallPixelChanges.remove(overallPixelChanges.size() - 1);
								overallPixelChanges.remove(0);
							}
							if (irregularTurnCode == 10) {
								PixelOverallChange newTurn = new PixelOverallChange("RightTurn");
								overallPixelChanges.add(newTurn);
							} else if (irregularTurnCode == 11) {
								PixelOverallChange newTurn = new PixelOverallChange("LeftTurn");
								overallPixelChanges.add(newTurn);
							}	
						}
						irregularTurnCode = 0;
					}
				}
				
				if (overallChange.changeType.equals("StartRightUpDiagonal")) {
					irregularTurnCode = 1;
				} else if (overallChange.changeType.equals("StartLeftUpDiagonal")) {
					irregularTurnCode = -1;
				}
				overallPixelChanges.add(overallChange);
			}
		} else {
			prevValue = changeValue;
			PixelOverallChange overallChange = new PixelOverallChange(changeValue);
			if (irregularTurnCode != 0) {
				if (overallChange.changeType.equals("MaintainRightUpDiagonal") && irregularTurnCode == 1){
					irregularTurnCode = 10;
				}else if(overallChange.changeType.equals("MaintainLeftUpDiagonal") && irregularTurnCode == -1) {
					irregularTurnCode = 11;
				} else {
					if (overallChange.changeType.equals("Straight")) {
						if (irregularTurnCode == 10 || irregularTurnCode == 11) {
							overallPixelChanges.remove(overallPixelChanges.size() - 1);
							overallPixelChanges.remove(overallPixelChanges.size() - 1);
						}
						if (irregularTurnCode == 10) {
							PixelOverallChange newTurn = new PixelOverallChange("RightTurn");
							overallPixelChanges.add(newTurn);
						} else if (irregularTurnCode == 11) {
							PixelOverallChange newTurn = new PixelOverallChange("LeftTurn");
							overallPixelChanges.add(newTurn);
						}	
					}
					irregularTurnCode = 0;
				}
			}
			if (overallPixelChanges.get(0).changeType.equals("Straight")) {
				if (irregularTurnCode == 10 || irregularTurnCode == 11) {
					overallPixelChanges.remove(overallPixelChanges.size() - 1);
					overallPixelChanges.remove(overallPixelChanges.size() - 1);
				}
				if (irregularTurnCode == 10) {
					PixelOverallChange newTurn = new PixelOverallChange("RightTurn");
					overallPixelChanges.add(newTurn);
				} else if (irregularTurnCode == 11) {
					PixelOverallChange newTurn = new PixelOverallChange("LeftTurn");
					overallPixelChanges.add(newTurn);
				}	
			} else {
				overallPixelChanges.add(overallChange);
			}
			irregularTurnCode = 0;
			
			
			
			if (overallChange.changeType.equals("StartRightUpDiagonal")) {
				irregularTurnCode = 1;
			} else if (overallChange.changeType.equals("StartLeftUpDiagonal")) {
				irregularTurnCode = -1;
			}
			
		}
		return overallPixelChanges;
	}
	
	
	
	
	
	/**
	 * Create the hashmap key
	 * @return The hashmap (key) for conversion.
	 */
	private static HashMap<String, String> getHashMap(){
		//none = 0, leftturn = 1, rightturn = 2, leftupdiagonal = 3, rightupdiagonal = 4, leftdowndiagonal = 5, rightdowndiagonal = 6. turnaround = 7
		//key is prevChangecurrentChange    Examples: 00 12 34
		//StartLeftUpDiagonal     MaintainLeftUpDiagonal      StartRightUpDiagonal     MaintainRightUpDiagonal   
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("00", "Straight");
		map.put("01", "LeftTurn");
		map.put("02", "RightTurn");
		map.put("03", "StartLeftUpDiagonal");
		map.put("04", "StartRightUpDiagonal");
		map.put("05", "LeftDownDiagonal");
		map.put("06", "RightDownDiagonal");
		map.put("07", "TurnAround");
		map.put("10", "Straight");
		map.put("11", "LeftTurn");
		map.put("12", "RightTurn");
		map.put("13", "StartLeftUpDiagonal");
		map.put("14", "StartRightUpDiagonal");
		map.put("15", "LeftDownDiagonal");
		map.put("16", "RightDownDiagonal");
		map.put("17", "TurnAround");
		map.put("20", "Straight");
		map.put("21", "LeftTurn");
		map.put("22", "RightTurn");
		map.put("23", "StartLeftUpDiagonal");
		map.put("24", "StartRightUpDiagonal");
		map.put("25", "LeftDownDiagonal");
		map.put("26", "RightDownDiagonal");
		map.put("27", "TurnAround");
		map.put("30", "Straight");
		map.put("31", "LeftTurn");
		map.put("32", "RightTurn");
		map.put("33", "MaintainLeftUpDiagonal");
		map.put("34", "StartRightUpDiagonal");
		map.put("35", "LeftDownDiagonal");
		map.put("36", "RightDownDiagonal");
		map.put("37", "TurnAround");
		map.put("40", "Straight");
		map.put("41", "LeftTurn");
		map.put("42", "RightTurn");
		map.put("43", "StartLeftUpDiagonal");
		map.put("44", "MaintainRightUpDiagonal");
		map.put("45", "LeftDownDiagonal");
		map.put("46", "RightDownDiagonal");
		map.put("47", "TurnAround");
		map.put("50", "Straight");
		map.put("51", "LeftTurn");
		map.put("52", "RightTurn");
		map.put("53", "StartLeftUpDiagonal");
		map.put("54", "StartRightUpDiagonal");
		map.put("55", "LeftDownDiagonal");
		map.put("56", "RightDownDiagonal");
		map.put("57", "TurnAround");
		map.put("60", "Straight");
		map.put("61", "LeftTurn");
		map.put("62", "RightTurn");
		map.put("63", "StartLeftUpDiagonal");
		map.put("64", "StartRightUpDiagonal");
		map.put("65", "LeftDownDiagonal");
		map.put("66", "RightDownDiagonal");
		map.put("67", "TurnAround");
		map.put("70", "Straight");
		map.put("71", "LeftTurn");
		map.put("72", "RightTurn");
		map.put("73", "StartLeftUpDiagonal");
		map.put("74", "StartRightUpDiagonal");
		map.put("75", "LeftDownDiagonal");
		map.put("76", "RightDownDiagonal");
		map.put("77", "TurnAround");
		
		return map;
	}
}
