package Structure;

import java.awt.image.BufferedImage;

public class RawEnv {
	public BufferedImage currentDisplay;
	public Double currentCpuUsage;
	
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
