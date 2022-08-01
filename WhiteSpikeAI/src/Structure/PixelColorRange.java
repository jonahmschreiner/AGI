package Structure;

import java.awt.Color;

public class PixelColorRange {
	public int minR;
	public int maxR;
	public int minG;
	public int maxG;
	public int minB;
	public int maxB;
	final int rangeDistance = 60;
	
	public PixelColorRange(Color colorIn) {
		this.minR = colorIn.getRed() - rangeDistance;
		this.maxR = colorIn.getRed() + rangeDistance;
		this.minG = colorIn.getGreen() - rangeDistance;
		this.maxG = colorIn.getGreen() + rangeDistance;
		this.minB = colorIn.getBlue() - rangeDistance;
		this.maxB = colorIn.getBlue() + rangeDistance;
	}
}
