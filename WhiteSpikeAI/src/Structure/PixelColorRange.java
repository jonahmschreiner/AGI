package Structure;

import java.awt.Color;

public class PixelColorRange {
//	public int minR;
//	public int maxR;
//	public int minG;
//	public int maxG;
//	public int minB;
//	public int maxB;
	public String color;
	//final int rangeDistance = 20;
	
	public PixelColorRange(Color colorIn) {
		float hsb[] = new float[3];
		Color.RGBtoHSB(colorIn.getRed(), colorIn.getGreen(), colorIn.getBlue(), hsb);
		if (hsb[1] < 0.1 && hsb[2] > 0.9) {
			this.color = "White";
		} else if (hsb[2] < 0.1) {
			this.color = "Black";
		} else {
			float deg = hsb[0] * 360;
			if (deg >= 0 && deg < 30) {
				this.color = "Red";
			} else if (deg >= 30 && deg < 45) {
				this.color = "Orange";
			} else if (deg >= 45 && deg < 70) {
				this.color = "Yellow";
			} else if (deg >= 70 && deg < 170) {
				this.color = "Green";
			} else if (deg >= 170 && deg < 185) {
				this.color = "Teal";
			} else if (deg >= 185 && deg < 260) {
				this.color = "Blue";
			} else if (deg >= 260 && deg < 275) {
				this.color = "Purple";
			} else if (deg >= 275 && deg < 320) {
				this.color = "Pink";
			} else if (deg >= 320) {
				this.color = "Red";
			}
		}
//		this.minR = colorIn.getRed() - rangeDistance;
//		this.maxR = colorIn.getRed() + rangeDistance;
//		this.minG = colorIn.getGreen() - rangeDistance;
//		this.maxG = colorIn.getGreen() + rangeDistance;
//		this.minB = colorIn.getBlue() - rangeDistance;
//		this.maxB = colorIn.getBlue() + rangeDistance;
	}
}
