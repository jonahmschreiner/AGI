package unboundStruct;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Env {
	//instance variables for each sense
	public Point mouseLocation;
	public boolean leftMouseButtonPressed;
	public boolean middleMouseButtonPressed;
	public boolean rightMouseButtonPressed;
	public boolean scrollWheelUpPressed;
	public boolean scrollWheelDownPressed;	
	public String keyPressed;
	public BufferedImage currentDisplay;
	public BufferedImage prevDisplay;
	public int numOfMouseButtons;
	public LocalDateTime calendar;
	public Double currentCpuUsage;
	public List<Instruction> errorLocations = new ArrayList<Instruction>();
	public List<Sense> abstractEnv = new ArrayList<Sense>();
	public Env() {}
	
	public String getEnvVarsString() throws InterruptedException {
		String mousePointVar = "java.awt.Point mouseLocation = new java.awt.Point(" + (int)this.mouseLocation.getX() + ", " + (int)this.mouseLocation.getY() + ");\n";
		String leftMousePressedVar = "boolean leftMouseButtonPressed = " + this.leftMouseButtonPressed + ";\n";
		String middleMousePressedVar = "boolean middleMouseButtonPressed = " + this.middleMouseButtonPressed + ";\n";
		String rightMousePressedVar = "boolean rightMouseButtonPressed = " + this.rightMouseButtonPressed + ";\n";
		String keyPressedVar = "String keyPressed = \"" + this.keyPressed + "\";\n";
		String numOfMouseButtonsVar = "int numOfMouseButtons = " + this.numOfMouseButtons + ";";
		String currentDisplayInitVar = "java.awt.image.BufferedImage currentDisplay = null;\ntry {\n" +
				"currentDisplay = javax.imageio.ImageIO.read(new File(\"/home/agi/Desktop/eclipse/UnboundAI/bin/CurrentDisplayVisuals.jpg\"));\n}catch(Exception e){\n}\n" ;
		String tagsRecursivelyCalledVar = "String tagsRecursivelyCalled = \"\";\n";
		String calendarVar = "java.time.LocalDateTime calendar = java.time.LocalDateTime.parse(\"" + this.calendar.toString() + "\");\n";
		String currentCpuUsageVar = "Double currentCpuUsage = " + this.currentCpuUsage + ";\n";
		String errorLocationsVar ="java.util.List<unboundStruct.Instruction> currentErrorLocations = new ArrayList<unboundStruct.Instruction>();\n";
		String errorLocationNumVar = "int errorLocationNum = 0;\n";
		
		if (errorLocations != null) {
			for (int i = 0; i < errorLocations.size(); i++) {
				errorLocationsVar = errorLocationsVar + "unboundStruct.Instruction inst" + i + " = new unboundStruct.Instruction(";
				errorLocationsVar = errorLocationsVar + "\"" + errorLocations.get(i).parentClass + "\", ";
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).instructionNumber + ", ";
				errorLocationsVar = errorLocationsVar + "\"" + errorLocations.get(i).instruction + "\");\n";
				errorLocationsVar = errorLocationsVar + "currentErrorLocations.add(inst" + i + ");\n";
			}
		}
		String sensesVar = "java.util.List<unboundStruct.Sense> abstractEnv = new java.util.ArrayList<unboundStruct.Sense>();\n";	
		if (abstractEnv != null) {
			for (int i = 0; i < abstractEnv.size(); i++) {
				String propertyListStr = "List<unboundStruct.Property> sense" + i + "Props = new ArrayList<unboundStruct.Property>();"; ;
				sensesVar = sensesVar + propertyListStr;
				for (int j = 0; j < abstractEnv.get(i).properties.size(); j++) {
					String indPropStr = "unboundStruct.Property sense" + i + "Prop" + j 
							+ " = new unboundStruct.Property(\"" + abstractEnv.get(i).properties.get(j).propType
							+ "\", \"" + abstractEnv.get(i).properties.get(j).propLabel + "\", \"" + abstractEnv.get(i).properties.get(j).valuesString + "\");\n";
					String indPropAddStr = "sense" + i + "Props.add(sense" + i + "Prop" + j + ");\n";
					sensesVar = sensesVar + indPropStr + indPropAddStr;
				}
				sensesVar = sensesVar + "unboundStruct.Sense sense" + i + " = new unboundStruct.Sense(";
				sensesVar = sensesVar + "\"" + abstractEnv.get(i).label + "\", sense" + i + "Props";
				//add properties list to the constructor
			}
		}
		String envVars = mousePointVar + leftMousePressedVar + middleMousePressedVar + rightMousePressedVar 
				+ keyPressedVar + numOfMouseButtonsVar + currentDisplayInitVar + calendarVar 
				+ currentCpuUsageVar + errorLocationsVar + errorLocationNumVar + sensesVar +tagsRecursivelyCalledVar;
		return envVars;
	}
	
	public String getPrevEnvVarsString() throws InterruptedException {
		String mousePointVar = "java.awt.Point prevMouseLocation = new java.awt.Point(" + (int)this.mouseLocation.getX() + ", " + (int)this.mouseLocation.getY() + ");\n";
		String leftMousePressedVar = "boolean prevLeftMouseButtonPressed = " + this.leftMouseButtonPressed + ";\n";
		String middleMousePressedVar = "boolean prevMiddleMouseButtonPressed = " + this.middleMouseButtonPressed + ";\n";
		String rightMousePressedVar = "boolean prevRightMouseButtonPressed = " + this.rightMouseButtonPressed + ";\n";
		String keyPressedVar = "String prevKeyPressed = \"" + this.keyPressed + "\";\n";
		String numOfMouseButtonsVar = "int prevNumOfMouseButtons = " + this.numOfMouseButtons + ";\n";
		String currentDisplayInitVar = "java.awt.image.BufferedImage prevDisplay = null;\ntry {\n" +
				"prevDisplay = javax.imageio.ImageIO.read(new File(\"/home/agi/Desktop/eclipse/UnboundAI/bin/PrevDisplayVisuals.jpg\"));\n}catch(Exception e){\n}\n" ;
		String tagsRecursivelyCalledVar = "String prevTagsRecursivelyCalled = \"\";\n";
		String calendarVar = "java.time.LocalDateTime prevCalendar = java.time.LocalDateTime.parse(\"" + this.calendar.toString() + "\");\n";
		String errorLocationsVar ="java.util.List<unboundStruct.Instruction> prevErrorLocations = new ArrayList<unboundStruct.Instruction>();\n";
		if (errorLocations != null) {
			for (int i = 0; i < errorLocations.size(); i++) {
				errorLocationsVar = errorLocationsVar + "unboundStruct.Instruction prevInst" + i + " = new unboundStruct.Instruction(";
				errorLocationsVar = errorLocationsVar + "\"" + errorLocations.get(i).parentClass + "\", ";
				errorLocationsVar = errorLocationsVar + errorLocations.get(i).instructionNumber + ", ";
				errorLocationsVar = errorLocationsVar + "\"" + errorLocations.get(i).instruction + "\");\n";
				errorLocationsVar = errorLocationsVar + "prevErrorLocations.add(prevInst" + i + ");\n";
			}
		}
		String sensesVar = "java.util.List<unboundStruct.Sense> abstractEnv = new java.util.ArrayList<unboundStruct.Sense>();\n";	
		if (abstractEnv != null) {
			for (int i = 0; i < abstractEnv.size(); i++) {
				String propertyListStr = "List<unboundStruct.Property> sense" + i + "Props = new ArrayList<unboundStruct.Property>();"; ;
				sensesVar = sensesVar + propertyListStr;
				for (int j = 0; j < abstractEnv.get(i).properties.size(); j++) {
					String indPropStr = "unboundStruct.Property sense" + i + "Prop" + j 
							+ " = new unboundStruct.Property(\"" + abstractEnv.get(i).properties.get(j).propType
							+ "\", \"" + abstractEnv.get(i).properties.get(j).propLabel + "\", \"" + abstractEnv.get(i).properties.get(j).valuesString + "\");\n";
					String indPropAddStr = "sense" + i + "Props.add(sense" + i + "Prop" + j + ");\n";
					sensesVar = sensesVar + indPropStr + indPropAddStr;
				}
				sensesVar = sensesVar + "unboundStruct.Sense sense" + i + " = new unboundStruct.Sense(";
				sensesVar = sensesVar + "\"" + abstractEnv.get(i).label + "\", sense" + i + "Props";
				//add properties list to the constructor
			}
		}
		String currentCpuUsageVar = "Double prevCpuUsage = " + this.currentCpuUsage + ";\n";
		String prevEnvVars = mousePointVar + leftMousePressedVar + middleMousePressedVar + rightMousePressedVar 
				+ keyPressedVar + numOfMouseButtonsVar + currentDisplayInitVar + calendarVar
				+ currentCpuUsageVar + errorLocationsVar + sensesVar + tagsRecursivelyCalledVar;
		return prevEnvVars;
	}
	
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
	public void setCurrentDisplay(BufferedImage imageIn) throws Exception {
		this.currentDisplay = imageIn;
		File displayOut = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/CurrentDisplayVisuals.jpg");
		ImageIO.write(this.currentDisplay, "jpg", displayOut);
	}

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
		this.numOfMouseButtons = numButtons;
	}
	
	public void setCalendar (LocalDateTime c) {
		this.calendar = c;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Env){
			Env c = (Env) o;
			boolean output = false;
			if ((c.mouseLocation == this.mouseLocation) 
					&& (c.leftMouseButtonPressed == this.leftMouseButtonPressed)
					&& (c.rightMouseButtonPressed == this.rightMouseButtonPressed)
					&& (c.middleMouseButtonPressed == this.middleMouseButtonPressed)
					&& (c.numOfMouseButtons == this.numOfMouseButtons)
					&& (c.calendar.toString() == this.calendar.toString())){ //variables (that describe conditions) are the same
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
		String output = (int)this.mouseLocation.getX() + "," + (int)this.mouseLocation.getY() + "," ;
		output += this.keyPressed + ",";
		output += this.numOfMouseButtons + ",";
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
		output += this.calendar.toString() + ",";
		output += this.currentCpuUsage.toString() + ",";
		if (this.errorLocations != null) {
			for (int i = 0; i < this.errorLocations.size(); i++) {
				output += this.errorLocations.get(i).toString();
				output += ",";
			}
		}
		return output;
	}
}
