package com.whitespike.visionstructure;
/**
 * The sense's changes in orientation compared to the previous instance.
 * @author Jonah Schreiner
 *
 */
public class OrientationChanges {
	/**
	 * Did the orientation height value change (1 = increased, 0 = no change, -1 = decreased).
	 */
	public int heightChange;
	/**
	 * Did the orientation width value change (1 = increased, 0 = no change, -1 = decreased).
	 */
	public int widthChange;
	/**
	 * Did the orientation rotation value change (1 = increased, 0 = no change, -1 = decreased).
	 */
	public int rotationChange;
	/**
	 * Did the orientation color change (what color it changed into).
	 */
	public String colorChange;
	/**
	 * Did the orientation x pos change (1 = increased, 0 = no change, -1 = decreased).
	 */
	public int xChange;
	/**
	 * Did the orientation y pos change (1 = increased, 0 = no change, -1 = decreased).
	 */
	public int yChange;
	/**
	 * Did the orientation sense definition change (what it changed to).
	 */
	public String defChange;
	
	public OrientationChanges() {}
}
