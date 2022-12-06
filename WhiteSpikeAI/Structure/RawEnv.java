package Structure;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class RawEnv {
	public BufferedImage currentDisplay;
	public Double currentCpuUsage;
	public Point mouseLocation = new Point();
	public LocalDateTime currentDateTime;
	public int cpuUsageChange = 0;
	public int mouseXChange = 0;
	public int mouseYChange = 0;
	public RawEnv() {}
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
