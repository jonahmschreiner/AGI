package com.whitespike.visionstructure;
/**
 * Represents the literal change between two consecutive pixels.
 * @author Jonah Schreiner
 *
 */
public class PixelPosChange {
	public Pixel currentPixel;
	public Pixel nextPixel;
	public int xChange; //-1 = decrease, 0 = no change, 1 = increase
	public int yChange; //-1 = decrease, 0 = no change, 1 = increase
	
	public PixelPosChange() {}
	public PixelPosChange (Pixel currentPixelIn, Pixel nextPixelIn) {
		this.currentPixel = currentPixelIn;
		this.nextPixel = nextPixelIn;
		checkChange();
	}
	/**
	 * Determine what change occurred between the two pixels.
	 */
	public void checkChange() {
		int xSub = this.nextPixel.position.x - this.currentPixel.position.x;
		int ySub = this.nextPixel.position.y - this.currentPixel.position.y;
		if (xSub > 0) {
			this.xChange = 1;
		} else if (xSub == 0) {
			this.xChange = 0;
		} else {
			this.xChange = -1;
		}
		
		if (ySub > 0) {
			this.yChange = 1;
		} else if (ySub == 0) {
			this.yChange = 0;
		} else {
			this.yChange = -1;
		}
	}
}
