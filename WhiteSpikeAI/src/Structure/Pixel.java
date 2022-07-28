package Structure;

import java.awt.Color;
import java.awt.Point;

public class Pixel {
	public Point position;
	public Color color;
	public Pixel() {}
	public Pixel(Point positionIn, Color colorIn) {
		this.position = positionIn;
		this.color = colorIn;
	}
	public Pixel(Point positionIn) {
		this.position = positionIn;
	}
	public Pixel(Color colorIn) {
		this.color = colorIn;
	}
}
