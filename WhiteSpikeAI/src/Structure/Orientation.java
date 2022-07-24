package Structure;

import java.awt.Color;
import java.awt.Point;

public class Orientation {
	int size; //length of longer dimension
	double rotation; //how much the rotation of the sense has changed compared to the originally-discovered instance
	Color color; //middle color of blob's range
	Point position; //where the middle of the sense is on the screen
	int thickness; //length of smaller dimension
}
