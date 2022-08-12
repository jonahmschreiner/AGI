package Structure;

import java.awt.Point;

public class BoundingBox {
	public Pixel MinXMinY;
	public Pixel MinXMaxY;
	public Pixel MaxXMinY;
	public Pixel MaxXMaxY;
	public int minX;
	public int maxX;
	public int minY;
	public int maxY;
	
	public BoundingBox() {}
	public BoundingBox(int minXIn, int minYIn, int maxXIn, int maxYIn) {
		this.MinXMinY = new Pixel(new Point(minXIn, minYIn));
		this.MinXMaxY = new Pixel(new Point(minXIn, maxYIn));
		this.MaxXMinY = new Pixel(new Point(maxXIn, minYIn));
		this.MaxXMaxY = new Pixel(new Point(maxXIn, maxYIn));
		this.minX = minXIn - 1;
		this.minY = minYIn - 1;
		this.maxX = maxXIn + 1;
		this.maxY = maxYIn + 1;
	}
}
