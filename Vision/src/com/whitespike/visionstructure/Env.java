package com.whitespike.visionstructure;

import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import com.whitespike.vision.*;
public class Env {
		public AbstractEnv abstractEnv = new AbstractEnv();
		public RawEnv rawEnv = new RawEnv();
		public Util util = new Util();
		public int dbId;
		
		public Env() {
			long envStart = System.currentTimeMillis();
			senseEnv();
			long envEnd = System.currentTimeMillis();
			System.out.println("Total Env Creation Time: " + (envEnd-envStart));
			System.out.println("Number of Sense in Env: " + this.abstractEnv.senses.size());
		}
		
		public Env(int code) {
			
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
			//long rawEnvStart = System.currentTimeMillis();
			this.rawEnv = senseRawEnv(this);
			//long rawEnvEnd = System.currentTimeMillis();
			//System.out.println("Total Raw Env Creation Time: " + (rawEnvEnd-rawEnvStart));
			
			//long abstractEnvStart = System.currentTimeMillis();
			this.abstractEnv = senseAbstractEnv();
			//long abstractEnvEnd = System.currentTimeMillis();
			//System.out.println("Total Abstract Env Creation Time: " + (abstractEnvEnd-abstractEnvStart));
		}
		
		public RawEnv senseRawEnv(Env envIn) {
			RawEnv currentRawEnv = new RawEnv();
			currentRawEnv.currentDateTime = LocalDateTime.now();
			try {
				currentRawEnv.currentDisplay = getScreenshot();
			} catch (Exception e) {
				e.printStackTrace();
			}
			currentRawEnv.currentCpuUsage = util.getCurrentCpuUsage();
			currentRawEnv.mouseLocation = MouseInfo.getPointerInfo().getLocation();
			if (currentRawEnv.mouseLocation.x > envIn.rawEnv.mouseLocation.x) {
				currentRawEnv.mouseXChange = 1;
			} else if (currentRawEnv.mouseLocation.x < envIn.rawEnv.mouseLocation.x) {
				currentRawEnv.mouseXChange = -1;
			} else {
				currentRawEnv.mouseXChange = 0;
			}
			
			if (currentRawEnv.mouseLocation.y > envIn.rawEnv.mouseLocation.y) {
				currentRawEnv.mouseYChange = 1;
			} else if (currentRawEnv.mouseLocation.y < envIn.rawEnv.mouseLocation.y) {
				currentRawEnv.mouseYChange = -1;
			} else {
				currentRawEnv.mouseYChange = 0;
			}
			this.rawEnv = currentRawEnv;
			return currentRawEnv;
		}
		
		public AbstractEnv senseAbstractEnv() {
			return RawEnvToAbstractEnv.extract(this.rawEnv);
		}
		
		public BufferedImage getScreenshot() throws Exception {
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
