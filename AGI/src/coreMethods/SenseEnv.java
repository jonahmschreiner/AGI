//needs to be rewritten to allow for dynamic adjustment (allow the ai to adjust structure)
package coreMethods;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import AGIUtil.Util;
import agiStruct.Env;
public class SenseEnv {
	public Util util = new Util();
	public SenseEnv() {}
	public Env recordEnv() throws Exception {
		Env currentEnv = new Env();
		currentEnv.setMouseLocation(MouseInfo.getPointerInfo().getLocation());
		currentEnv.setNumOfMouseButtons(MouseInfo.getNumberOfButtons());
		currentEnv.setMouseButtonsPressed(util.getCurrentMouseData()[0], util.getCurrentMouseData()[1],util.getCurrentMouseData()[2]);
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
	//for testing
	public static void main(String[] args) throws Exception {
		SenseEnv sense = new SenseEnv();
		//sense.util.pressAltTab();
		//sense.util.leftMousePress();
		//sense.util.middleMousePress();
		//sense.util.rightMousePress();
		Env env = sense.recordEnv();
		System.out.println("Key Pressed: " + env.getKeyPressed());
		System.out.println("Left Mouse Pressed: " + env.getLeftMouseButtonPressed());
		System.out.println("Middle Mouse Pressed: " + env.getMiddleMouseButtonPressed());
		System.out.println("Right Mouse Pressed: " + env.getRightMouseButtonPressed());
		System.out.println("Scroll Wheel Up Pressed: " + env.getScrollWheelUpPressed());
		System.out.println("Scroll Wheel Down Pressed: " + env.getScrollWheelDownPressed());
		System.out.println("Num of mouse buttons: " + env.getNumOfMouseButtons());
		System.out.println("Calendar: " + env.getCalendar());
		System.out.println("Mouse Location: " + env.getMouseLocation());
		System.out.println("Cal To String: " + env.getCalendar().toString());
		//File test = new File("/home/agi/Desktop/eclipse/AGI/src/testCurrentDisplayVisuals.jpg");
		//ImageIO.write(env.getCurrentDisplay(), "jpg", test);
		//String command = "pinta /home/agi/Desktop/eclipse/AGI/src/testCurrentDisplayVisuals.jpg";
		//Runtime run = Runtime.getRuntime();
		//run.exec(command);
	}
	
}
