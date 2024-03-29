//needs to be rewritten to allow for dynamic adjustment (allow the ai to adjust structure)
package agiStruct;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.*;
import java.util.List;

import javax.imageio.ImageIO;

public class Env {
	//instance variables for each sense
	
	//mouse location (x, y)
	Point mouseLocation;
	
	//is mouse pressed ([0] = left mouse data; [1] = middle mouse data; [2] = right mouse data;
	//[3] = scroll wheel up data; [4] = scroll wheel down data;)
	int[] mouseButtonPressedInfo;
	boolean leftMouseButtonPressed;
	boolean middleMouseButtonPressed;
	boolean rightMouseButtonPressed;
	boolean scrollWheelUpPressed;
	boolean scrollWheelDownPressed;
	
	//keypress info (the series of strings pressed at the current moment)
	String keyPressed;
	
	//current monitor display (2d array of pixels)
	BufferedImage currentDisplay;
	
	//prev monitor display (2d array of pixels)
	BufferedImage prevDisplay;
	//number of mouse buttons
	int numOfMouseButtons;
	
	//current date, time, timezone, etc.
	LocalDateTime calendar;
	
	//CPU Usage
	Double currentCpuUsage;
	
	//Error Locations
	List<Instruction> errorLocations;
	
	public Env() {
		
	}
	//gets
	public String getEnvVarsString() throws InterruptedException {
		String mousePointVar = "java.awt.Point mouseLocation = new java.awt.Point(" + (int)this.getMouseLocation().getX() + ", " + (int)this.getMouseLocation().getY() + ");";
		String leftMousePressedVar = "boolean leftMouseButtonPressed = " + this.getLeftMouseButtonPressed() + ";";
		String middleMousePressedVar = "boolean middleMouseButtonPressed = " + this.getMiddleMouseButtonPressed() + ";";
		String rightMousePressedVar = "boolean rightMouseButtonPressed = " + this.getRightMouseButtonPressed() + ";";
		String keyPressedVar = "String keyPressed = \"" + this.getKeyPressed() + "\";";
		String numOfMouseButtonsVar = "int numOfMouseButtons = " + this.getNumOfMouseButtons() + ";";
		String currentDisplayInitVar = "java.awt.image.BufferedImage currentDisplay = null;try {" +
				"currentDisplay = javax.imageio.ImageIO.read(new File(\"/home/agi/Desktop/eclipse/AGI/bin/CurrentDisplayVisuals.jpg\"));}catch(Exception e){}" ;
		String tagsRecursivelyCalledVar = "String tagsRecursivelyCalled = \"\";";
		String calendarVar = "java.time.LocalDateTime calendar = java.time.LocalDateTime.parse(\"" + this.getCalendar().toString() + "\");";
		String currentCpuUsageVar = "Double currentCpuUsage = " + this.getCurrentCpuUsage() + ";";
		String errorLocationsVar ="java.util.List<agiStruct.Instruction> currentErrorLocations = new ArrayList<agiStruct.Instruction>();";
		String errorLocationNumVar = "int errorLocationNum = 0;";
		
		if (errorLocations != null) {
			for (int i = 0; i < errorLocations.size(); i++) {
				errorLocationsVar = errorLocationsVar + "agiStruct.Instruction inst" + i + " = new agiStruct.Instruction(";
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).getParentClass();
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).getInstructionNumber();
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).getInstruction();
				errorLocationsVar = errorLocationsVar + "currentErrorLocations.add(inst" + i + ")";
			}
		}
		String envVars = mousePointVar + leftMousePressedVar + middleMousePressedVar + rightMousePressedVar 
				+ keyPressedVar + numOfMouseButtonsVar + currentDisplayInitVar + calendarVar 
				+ currentCpuUsageVar + errorLocationsVar + errorLocationNumVar + tagsRecursivelyCalledVar;
		return envVars;
	}
	
	public String getPrevEnvVarsString() throws InterruptedException {
		String mousePointVar = "java.awt.Point prevMouseLocation = new java.awt.Point(" + (int)this.getMouseLocation().getX() + ", " + (int)this.getMouseLocation().getY() + ");";
		String leftMousePressedVar = "boolean prevLeftMouseButtonPressed = " + this.getLeftMouseButtonPressed() + ";";
		String middleMousePressedVar = "boolean prevMiddleMouseButtonPressed = " + this.getMiddleMouseButtonPressed() + ";";
		String rightMousePressedVar = "boolean prevRightMouseButtonPressed = " + this.getRightMouseButtonPressed() + ";";
		String keyPressedVar = "String prevKeyPressed = \"" + this.getKeyPressed() + "\";";
		String numOfMouseButtonsVar = "int prevNumOfMouseButtons = " + this.getNumOfMouseButtons() + ";";
		String currentDisplayInitVar = "java.awt.image.BufferedImage prevDisplay = null;try {" +
				"prevDisplay = javax.imageio.ImageIO.read(new File(\"/home/agi/Desktop/eclipse/AGI/bin/CurrentDisplayVisuals.jpg\"));}catch(Exception e){}" ;
		String tagsRecursivelyCalledVar = "String prevTagsRecursivelyCalled = \"\";";
		String calendarVar = "java.time.LocalDateTime prevCalendar = java.time.LocalDateTime.parse(\"" + this.getCalendar().toString() + "\");";
		String errorLocationsVar ="java.util.List<agiStruct.Instruction> prevErrorLocations = new ArrayList<agiStruct.Instruction>();";
		if (errorLocations != null) {
			for (int i = 0; i < errorLocations.size(); i++) {
				errorLocationsVar = errorLocationsVar + "agiStruct.Instruction inst" + i + " = new agiStruct.Instruction(";
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).getParentClass();
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).getInstructionNumber();
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).getInstruction();
				errorLocationsVar = errorLocationsVar + "prevErrorLocations.add(inst" + i + ")";
			}
		}
		String currentCpuUsageVar = "Double prevCpuUsage = " + this.getCurrentCpuUsage() + ";";
		String prevEnvVars = mousePointVar + leftMousePressedVar + middleMousePressedVar + rightMousePressedVar 
				+ keyPressedVar + numOfMouseButtonsVar + currentDisplayInitVar + calendarVar
				+ currentCpuUsageVar + errorLocationsVar + tagsRecursivelyCalledVar;
		return prevEnvVars;
	}
	
	public List<Instruction> getErrorLocations(){
		return this.errorLocations;
	}
	
	public Double getCurrentCpuUsage() {
		return this.currentCpuUsage;
	}
	public String getKeyPressed() {
		return keyPressed;
	}
	public BufferedImage getCurrentDisplay() {
		return currentDisplay;
	}
	public BufferedImage getPrevDisplay() {
		return prevDisplay;
	}
	public boolean getLeftMouseButtonPressed() {
		return this.leftMouseButtonPressed;
	}
	
	public boolean getMiddleMouseButtonPressed() {
		return this.middleMouseButtonPressed;
	}
	
	public boolean getRightMouseButtonPressed() {
		return this.rightMouseButtonPressed;
	}
	
	public boolean getScrollWheelUpPressed() {
		return this.scrollWheelUpPressed;
	}
	
	public boolean getScrollWheelDownPressed() {
		return this.scrollWheelDownPressed;
	}
	
	public Point getMouseLocation() {
		return mouseLocation;
	}
	
	public int getNumOfMouseButtons() {
		return numOfMouseButtons;
	}
	
	public LocalDateTime getCalendar() {
		return calendar;
	}
	//sets
	public void setErrorLocations(List<Instruction> instIn) {
		this.errorLocations = instIn;
	}
	public void addErrorLocation(Instruction instIn) {
		this.errorLocations.add(instIn);
	}
	public void setCurrentCpuUsage(Double usageIn) {
		this.currentCpuUsage = usageIn;
	}
	public void setKeyPressed(String keyIn) {
		this.keyPressed = keyIn;
	}
	public void setCurrentDisplay(BufferedImage imageIn) {
		this.currentDisplay = imageIn;
		File displayOut = new File("/home/agi/Desktop/eclipse/AGI/bin/CurrentDisplayVisuals.jpg");
		try {
			ImageIO.write(this.currentDisplay, "jpg", displayOut);
		} catch (Exception e) {
		
		}
	}
//	public void setPrevDisplay(BufferedImage imageIn) {
//		this.prevDisplay = imageIn;
//		File prevDisplayOut = new File("/home/agi/Desktop/eclipse/AGI/bin/PrevDisplayVisuals.jpg");
//		try {
//			ImageIO.write(this.prevDisplay, "jpg", prevDisplayOut);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//	}
//	public void obtainPrevDisplay() {
//		File prevDisplayIn = new File("/home/agi/Desktop/eclipse/AGI/bin/PrevDisplayVisuals.jpg");
//		try {
//			BufferedImage prevDisplayImage = javax.imageio.ImageIO.read(prevDisplayIn);
//			this.prevDisplay = prevDisplayImage;
//		} catch (Exception e) {
//			
//		}
//	}
	public void setMouseLocation(Point inputLocation) {
		this.mouseLocation = inputLocation;
	}

	public void setMouseButtonsPressed (int leftMouseInfo, int middleMouseInfo, int rightMouseInfo) throws Exception {
		if (leftMouseInfo == 0) {
			this.leftMouseButtonPressed = false;
		} else {
			this.leftMouseButtonPressed = true;
		}
		
		if (middleMouseInfo == 0) {
			this.middleMouseButtonPressed = false;
		} else {
			this.middleMouseButtonPressed = true;
		}
		
		if (rightMouseInfo == 0) {
			this.rightMouseButtonPressed = false;
		} else {
			this.rightMouseButtonPressed = true;
		}
	}
	
	public void setNumOfMouseButtons(int numButtons) {
		numOfMouseButtons = numButtons;
	}
	
	public void setCalendar (LocalDateTime c) {
		calendar = c;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Env){
			Env c = (Env) o;
			boolean output = false;
			if ((c.getMouseLocation() == this.getMouseLocation()) 
					&& (c.leftMouseButtonPressed == this.leftMouseButtonPressed)
					&& (c.rightMouseButtonPressed == this.rightMouseButtonPressed)
					&& (c.middleMouseButtonPressed == this.middleMouseButtonPressed)
					&& (c.getNumOfMouseButtons() == this.getNumOfMouseButtons())
					&& (c.getCalendar().toString() == this.getCalendar().toString())){ //variables (that describe conditions) are the same
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}
	}

	@Override
	public String toString(){
		String output = (int)this.getMouseLocation().getX() + "," + (int)this.getMouseLocation().getY() + "," ;
		output += this.getKeyPressed() + ",";
		output += this.getNumOfMouseButtons() + ",";
		if (this.leftMouseButtonPressed) {
			output += 1 + ",";
		} else {
			output += 0 + ",";
		}
		if (this.middleMouseButtonPressed) {
			output += 1 + ",";
		} else {
			output += 0 + ",";
		}
		if (this.rightMouseButtonPressed) {
			output += 1 + ",";
		} else {
			output += 0 + ",";
		}
		output += this.getCalendar().toString() + ",";
		output += this.getCurrentCpuUsage().toString() + ",";
		if (this.errorLocations != null) {
			for (int i = 0; i < this.getErrorLocations().size(); i++) {
				output += this.getErrorLocations().get(i).toString();
				output += ",";
			}
		}
		return output;
	}
}
