package com.whitespike.visionstructure;

import java.awt.Color;
/**
 * Represents the english name for the RGB value of the pixel (or sense).
 * @author Jonah Schreiner
 *
 */
public class PixelColorRange {
	public String color;
	
	public PixelColorRange(Color colorIn) {
		float hsb[] = new float[3];
		Color.RGBtoHSB(colorIn.getRed(), colorIn.getGreen(), colorIn.getBlue(), hsb);
		if (hsb[1] < 0.05 && hsb[2] > 0.6) {
			this.color = "White";
		} else if (hsb[1] < 0.05 && hsb[2] > 0.15) {
			this.color = "Grey";
		} else if (hsb[1] < 0.05) {
			this.color = "Black";
		} else {
			float deg = hsb[0] * 360;
			if (deg < 16) {
				this.color = "Red";
			} else if (deg < 42 && deg >= 16 && hsb[2] < 0.6) {
				this.color = "Brown";
			} else if (deg < 42 && deg >= 16) {
				this.color = "Orange";
			} else if (deg < 75 && deg >= 42 ) {
				this.color = "Yellow";
			} else if (deg >= 75 && deg < 145) {
				this.color = "Green";
			} else if (deg >= 145 && deg < 196) {
				this.color = "Teal";
			} else if (deg >= 196 && deg < 260) {
				this.color = "Blue";
			} else if (deg >= 260 && deg < 290) {
				this.color = "Purple";
			} else if (deg >= 290 && deg < 340) {
				this.color = "Pink";
			} else if (deg >= 340) {
				this.color = "Red";
			}
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof PixelColorRange){
			PixelColorRange c = (PixelColorRange) o;
			boolean output = false;
			if (c.color.equals(this.color)){ //variables (that describe conditions) are the same
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
