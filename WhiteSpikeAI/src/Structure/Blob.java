package Structure;

import java.util.ArrayList;
import java.util.List;

public class Blob {
	public List<Pixel> pixels = new ArrayList<Pixel>();
	public List<Pixel> edgePixels = new ArrayList<Pixel>();
	public MinAndMaxes minAndMaxes = new MinAndMaxes();
	public Blob() {}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Blob){
			Blob c = (Blob) o;
			boolean output = false;
			if (c.pixels.equals(this.pixels)){ //variables (that describe conditions) are the same
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
