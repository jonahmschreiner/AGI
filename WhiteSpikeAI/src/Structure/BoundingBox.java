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
		this.minX = minXIn; //- 1;
		this.minY = minYIn; //- 1;
		this.maxX = maxXIn; //+ 1;
		this.maxY = maxYIn; //+ 1;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof BoundingBox){
			BoundingBox c = (BoundingBox) o;
			boolean output = false;
			if (c.minX < this.minX + 2 && c.minX > this.minX - 2
					&& c.maxX < this.maxX + 2 && c.maxX > this.maxX - 2
					&& c.minY < this.minY + 2 && c.minY > this.minY - 2
					&& c.maxY < this.maxY + 2 && c.maxY > this.maxY - 2){ //variables (that describe conditions) are the same
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
