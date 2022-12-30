package com.whitespike.visionstructure;
/**
 * Represents the comparison between two consecutive pixel changes.
 * @author Jonah Schreiner
 *
 */
public class PixelOverallChange {
	/**
	 * What change occurred. (Possible options: Straight, RightTurn, LeftTurn, StartLeftUpDiagonal, MaintainLeftUpDiagonal, StartRightUpDiagonal, MaintainRightUpDiagonal, LeftDownDiagonal, RightDownDiagonal).
	 */
	public String changeType; //Straight, RightTurn, LeftTurn, StartLeftUpDiagonal, MaintainLeftUpDiagonal, StartRightUpDiagonal, MaintainRightUpDiagonal, LeftDownDiagonal, RightDownDiagonal
	public PixelOverallChange() {}
	public PixelOverallChange(String changeTypeIn) {
		this.changeType = changeTypeIn;
	}
}
