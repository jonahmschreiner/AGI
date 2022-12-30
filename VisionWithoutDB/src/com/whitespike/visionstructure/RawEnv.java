package com.whitespike.visionstructure;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
/**
 * Represents the raw data that is used to generate an abstract env.
 * @author Jonah Schreiner
 *
 */
public class RawEnv {
	/**
	 * The current raw visual input.
	 */
	public BufferedImage currentDisplay;
	/**
	 * The current cpu usage input.
	 */
	public Double currentCpuUsage;
	/**
	 * The current mouseLocation input.
	 */
	public Point mouseLocation = new Point();
	/**
	 * The current LocalDateTime object input.
	 */
	public LocalDateTime currentDateTime;
	/**
	 * The current cpu usage change from previous iteration input.
	 */
	public int cpuUsageChange = 0;
	/**
	 * The mouse X pos change from previous iteration input.
	 */
	public int mouseXChange = 0;
	/**
	 * The mouse Y pos change from previous iteration input.
	 */
	public int mouseYChange = 0;
	public RawEnv() {}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof RawEnv){
			RawEnv c = (RawEnv) o;
			boolean output = false;
			if (c.currentDisplay.equals(this.currentDisplay) && (c.currentCpuUsage.equals(this.currentCpuUsage))){ //variables (that describe conditions) are the same
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}
	}
}
