package Structure;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import ServiceMethods.*;
public class Env {
		AbstractEnv abstractEnv;
		RawEnv rawEnv;
		Util util = new Util();
		
		public Env() {
			senseEnv();
		}
		
		public Env(RawEnv rawEnvIn) {
			this.rawEnv = rawEnvIn;
			senseAbstractEnv();
		}
		
		public Env(RawEnv rawEnvIn, AbstractEnv abstractEnvIn) {
			this.rawEnv = rawEnvIn;
			this.abstractEnv = abstractEnvIn;
		}
		
		public void senseEnv() {
			this.rawEnv = senseRawEnv();
			this.abstractEnv = senseAbstractEnv();
		}
		
		private RawEnv senseRawEnv() {
			RawEnv currentRawEnv = new RawEnv();
			try {
				currentRawEnv.currentDisplay = getScreenshot();
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentRawEnv.currentCpuUsage = util.getCurrentCpuUsage();
			return currentRawEnv;
		}
		
		private AbstractEnv senseAbstractEnv() {
			return RawEnvToAbstractEnv.extract(this.rawEnv);
		}
		
		private BufferedImage getScreenshot() throws Exception {
		    Rectangle rec = new Rectangle(
		      Toolkit.getDefaultToolkit().getScreenSize());
		    Robot robot = new Robot();
		    BufferedImage img = robot.createScreenCapture(rec); 
		    return img;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == this){
				return true;
			}
			if (o instanceof Env){
				Env c = (Env) o;
				boolean output = false;
				if (c.abstractEnv.equals(this.abstractEnv)){ //variables (that describe conditions) are the same
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
