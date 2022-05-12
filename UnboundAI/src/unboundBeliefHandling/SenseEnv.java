//needs to be rewritten to allow for dynamic adjustment (allow the ai to adjust structure)
package unboundBeliefHandling;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import unboundStruct.*;
public class SenseEnv {
	public Util util = new Util();
	public SenseEnv() {}
	public Env recordEnv() throws Exception {
		Env currentEnv = new Env();
		currentEnv.setMouseLocation(MouseInfo.getPointerInfo().getLocation());
		currentEnv.setNumOfMouseButtons(MouseInfo.getNumberOfButtons());
		currentEnv.setMouseButtonsPressed(util.leftMousePressed, util.middleMousePressed,util.rightMousePressed);
		LocalDateTime curr = LocalDateTime.now();
		currentEnv.setCalendar(curr);
		currentEnv.setCurrentDisplay(getScreenshot());
		currentEnv.setKeyPressed(util.getCurrentKeyPressData());		
		currentEnv.setCurrentCpuUsage(util.getCurrentCpuUsage());
		return currentEnv;
	}
	
	public BufferedImage getScreenshot() throws Exception {
	    Rectangle rec = new Rectangle(
	      Toolkit.getDefaultToolkit().getScreenSize());
	    Robot robot = new Robot();
	    BufferedImage img = robot.createScreenCapture(rec); 
	    return img;
	}
	
}
