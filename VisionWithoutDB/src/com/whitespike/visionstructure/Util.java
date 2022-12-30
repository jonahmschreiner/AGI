package com.whitespike.visionstructure;

import java.awt.MouseInfo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
//whenever I add in the mouse button press util functions, be sure to write the update to CurrentMouseData
//		so SenseEnv gets the correct data
/**
 * Encapsulates a variety of raw-env-info-getting and core-action-executing actions.
 * @author Jonah Schreiner
 *
 */
public class Util {
	public Runtime run = Runtime.getRuntime();
	public String command = "";
	public String currentKeyPressData = "";
	public int leftMousePressed = 0;
	public int middleMousePressed = 0;
	public int rightMousePressed = 0;

	public Double getCurrentCpuUsage() {
		//Cpu Usage
		try {
			Runtime run = Runtime.getRuntime();
			//String command = "pidstat | grep \"java\"| awk -F \" \" '{print $9}'";
			String command = "pidstat | awk -F \" \" '{print $9}'";
	    	Process p = run.exec(new String[] {"/bin/bash","-c",command});
	    	BufferedReader stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			//BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String cpuUsage = "";
			double totalCpuUsage = 0;
			
			//useless variables to throw away the non double lines
			@SuppressWarnings("unused")
			String trash = stdOutput.readLine();
			@SuppressWarnings("unused")
			String trash2 = stdOutput.readLine();
			@SuppressWarnings("unused")
			String trash3 = stdOutput.readLine();
			
			cpuUsage = stdOutput.readLine();
			while (cpuUsage != null){
				double usage = Double.valueOf(cpuUsage);
				totalCpuUsage += usage;
				cpuUsage = stdOutput.readLine();
			}
			return totalCpuUsage;
	    } catch (Exception e){
	    	e.printStackTrace();
	    	return -1.0;
	    }
		
	}
	//write current variable data to MouseDataFile
	public String getCurrentKeyPressData() {
		return currentKeyPressData;
	}
	public void setCurrentKeyPressData(String keyDataIn) {
		this.currentKeyPressData = keyDataIn;
	}

	public void copyContent(File a, File b) throws Exception
    	{
        	FileInputStream in = new FileInputStream(a);
        	FileOutputStream out = new FileOutputStream(b);
  
        	try { 
            		int n;
            		while ((n = in.read()) != -1) {
                		out.write(n);
            		}
        	} finally {
            		if (in != null) {
                		in.close();
            		}
            		
            		if (out != null) {
                		out.close();
            		}
        	}
    	}
    //helper function called by others to reduce class size
	public void runCommand() {
		try {
			run.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
			
	//move mouse to coordinates (not an AGI core action atm)
	public void moveMouseToCoordinates(int x, int y) {
		command = "xdotool mousemove " + x + " " + y;
		runCommand();
	}
	
	//move mouse left
	public void moveMouseLeft() {
		if (MouseInfo.getPointerInfo().getLocation().getX() > 0) {
			command = "xdotool mousemove_relative -- -1 0";
			runCommand();
		}
	}
	//move mouse right
	public void moveMouseRight() {
		if (MouseInfo.getPointerInfo().getLocation().getX() < 1680) {
			command = "xdotool mousemove_relative 1 0";
			runCommand();
		}
	}
	//move mouse down
	public void moveMouseDown() {
		if (MouseInfo.getPointerInfo().getLocation().getY() < 1049) {
			command = "xdotool mousemove_relative 0 1";
			runCommand();
		}
	}
	//move mouse up
	public void moveMouseUp() {
		if (MouseInfo.getPointerInfo().getLocation().getY() > 0) {
			command = "xdotool mousemove_relative -- 0 -1";
			runCommand();
		}
	}
	
	//left mouse pressing
	public void leftMousePress() {
		command = "xdotool mousedown 1";
		runCommand();
		this.leftMousePressed = 1;

	}
	//left mouse release
	public void leftMouseRelease() {
		command = "xdotool mouseup 1";
		runCommand();
		this.leftMousePressed = 0;
	}
	//middle mouse pressing
	public void middleMousePress() {
		command = "xdotool mousedown 2";
		runCommand();
		this.middleMousePressed = 1;
	}
	//middle mouse release
	public void middleMouseRelease() {
		command = "xdotool mouseup 2";
		runCommand();
		this.middleMousePressed = 0;
	}
	//right mouse pressing
	public void rightMousePress() {
		command = "xdotool mousedown 3";
		runCommand();
		this.rightMousePressed = 1;
	}
	//right mouse release
	public void rightMouseRelease() {
		command = "xdotool mouseup 3";
		runCommand();
		this.rightMousePressed = 0;
	}

	public void scrollUp() {
		command = "xdotool click 4";
		runCommand();
	}
	//scroll down pressing
	public void scrollDown() {
		command = "xdotool click 5";
		runCommand();
	}
	
	//press esc key
	public void pressEscape() {
		command = "xdotool key Escape";
		runCommand();
		setCurrentKeyPressData("Escape");
	}
	//press F1 key
	public void pressF1() {
		command = "xdotool key F1";
		runCommand();
		setCurrentKeyPressData("F1");
	}
	//press F2 key
	public void pressF2() {
		command = "xdotool key F2";
		runCommand();
		setCurrentKeyPressData("F2");
	}
	//press F3 key
	public void pressF3() {
		command = "xdotool key F3";
		runCommand();
		setCurrentKeyPressData("F3");
	}
	//press F4 key
	public void pressF4() {
		command = "xdotool key F4";
		runCommand();
		setCurrentKeyPressData("F4");
	}
	//press F5 key
	public void pressF5() {
		command = "xdotool key F5";
		runCommand();
		setCurrentKeyPressData("F5");
	}
	//press F6 key
	public void pressF6() {
		command = "xdotool key F6";
		runCommand();
		setCurrentKeyPressData("F6");
	}
	//press F7 key
	public void pressF7() {
		command = "xdotool key F7";
		runCommand();
		setCurrentKeyPressData("F7");
	}
	//press F8 key
	public void pressF8() {
		command = "xdotool key F8";
		runCommand();
		setCurrentKeyPressData("F8");
	}
	//press F9 key
	public void pressF9() {
		command = "xdotool key F9";
		runCommand();
		setCurrentKeyPressData("F9");
	}
	//press F10 key
	public void pressF10() {
		command = "xdotool key F10";
		runCommand();
		setCurrentKeyPressData("F10");
	}
	//press F11 key
	public void pressF11() {
		command = "xdotool key F11";
		runCommand();
		setCurrentKeyPressData("F11");
	}
	//press F12 key
	public void pressF12() {
		command = "xdotool key F12";
		runCommand();
		setCurrentKeyPressData("F12");
	}
	//press print screen key
	public void pressPrintScreen() {
		command = "xdotool key Print";
		runCommand();
		setCurrentKeyPressData("Print");
	}
	//press scroll lock key
	public void pressScrollLock() {
		command = "xdotool key Scroll_Lock";
		runCommand();
		setCurrentKeyPressData("ScrollLock");
	}
	//press pause/break key
	public void pressPauseOrBreak() {
		command = "xdotool key Pause";
		runCommand();
		setCurrentKeyPressData("Pause");
	}
	//press tilde key
	public void pressRightQuote() {
		command = "xdotool key quoteright";
		runCommand();
		setCurrentKeyPressData("RightQuote");
	}
	//press 1 key
	public void press1() {
		command = "xdotool key 1";
		runCommand();
		setCurrentKeyPressData("1");
	}
	//press 2 key
	public void press2() {
		command = "xdotool key 2";
		runCommand();
		setCurrentKeyPressData("2");
	}
	//press 3 key
	public void press3() {
		command = "xdotool key 3";
		runCommand();
		setCurrentKeyPressData("3");
	}
	//press 4 key
	public void press4() {
		command = "xdotool key 4";
		runCommand();
		setCurrentKeyPressData("4");
	}
	//press 5 key
	public void press5() {
		command = "xdotool key 5";
		runCommand();
		setCurrentKeyPressData("5");
	}
	//press 6 key
	public void press6() {
		command = "xdotool key 6";
		runCommand();
		setCurrentKeyPressData("6");
	}
	//press 7 key
	public void press7() {
		command = "xdotool key 7";
		runCommand();
		setCurrentKeyPressData("7");
	}
	//press 8 key
	public void press8() {
		command = "xdotool key 8";
		runCommand();
		setCurrentKeyPressData("8");
	}
	//press 9 key
	public void press9() {
		command = "xdotool key 9";
		runCommand();
		setCurrentKeyPressData("9");
	}
	//press 0 key
	public void press0() {
		command = "xdotool key 0";
		runCommand();
		setCurrentKeyPressData("0");
	}
	//press - key
	public void pressMinus() {
		command = "xdotool key minus";
		runCommand();
		setCurrentKeyPressData("Minus");
	}
	//press = key
	public void pressEqual() {
		command = "xdotool key equal";
		runCommand();
		setCurrentKeyPressData("Equal");
	}
	//press backspace key
	public void pressBackspace() {
		command = "xdotool key BackSpace";
		runCommand();
		setCurrentKeyPressData("Backspace");
	}
	//press insert key
	public void pressInsert() {
		command = "xdotool key Insert";
		runCommand();
		setCurrentKeyPressData("Insert");
	}
	//press home key
	public void pressHome() {
		command = "xdotool key KP_Home";
		runCommand();
		setCurrentKeyPressData("Home");
	}
	//press page up key
	public void pressPageUp() {
		command = "xdotool key Page_Up";
		runCommand();
		setCurrentKeyPressData("PageUp");
	}
	//press tab key
	public void pressTab() {
		command = "xdotool key Tab";
		runCommand();
		setCurrentKeyPressData("Tab");
	}
	//press q key
	public void pressQ() {
		command = "xdotool key q";
		runCommand();
		setCurrentKeyPressData("Q");
	}
	//press w key
	public void pressW() {
		command = "xdotool key w";
		runCommand();
		setCurrentKeyPressData("W");
	}
	//press e key
	public void pressE() {
		command = "xdotool key e";
		runCommand();
		setCurrentKeyPressData("E");
	}
	//press r key
	public void pressR() {
		command = "xdotool key r";
		runCommand();
		setCurrentKeyPressData("R");
	}
	//press t key
	public void pressT() {
		command = "xdotool key t";
		runCommand();
		setCurrentKeyPressData("T");
	}
	//press y key
	public void pressY() {
		command = "xdotool key y";
		runCommand();
		setCurrentKeyPressData("Y");
	}
	//press u key
	public void pressU() {
		command = "xdotool key u";
		runCommand();
		setCurrentKeyPressData("U");
	}
	//press i key
	public void pressI() {
		command = "xdotool key i";
		runCommand();
		setCurrentKeyPressData("I");
	}
	//press o key
	public void pressO() {
		command = "xdotool key o";
		runCommand();
		setCurrentKeyPressData("O");
	}
	//press p key
	public void pressP() {
		command = "xdotool key p";
		runCommand();
		setCurrentKeyPressData("P");
	}
	//press [ key
	public void pressLeftBracket() {
		command = "xdotool key bracketleft";
		runCommand();
		setCurrentKeyPressData("LeftBracket");
	}
	//press ] key
	public void pressRightBracket() {
		command = "xdotool key bracketright";
		runCommand();
		setCurrentKeyPressData("RightBracket");
	}
	//press \ key
	public void pressBackslash() {
		command = "xdotool key backslash";
		runCommand();
		setCurrentKeyPressData("Backslash");
	}
	//press delete key
	public void pressDelete() {
		command = "xdotool key Delete";
		runCommand();
		setCurrentKeyPressData("Delete");
	}
	//press end key
	public void pressEnd() {
		command = "xdotool key End";
		runCommand();
		setCurrentKeyPressData("End");
	}
	//press page down key
	public void pressPageDown() {
		command = "xdotool key Page_Down";
		runCommand();
		setCurrentKeyPressData("PageDown");
	}
	//press a key
	public void pressA() {
		command = "xdotool key a";
		runCommand();
		setCurrentKeyPressData("A");
	}
	//press s key
	public void pressS() {
		command = "xdotool key s";
		runCommand();
		setCurrentKeyPressData("S");
	}
	//press d key
	public void pressD() {
		command = "xdotool key d";
		runCommand();
		setCurrentKeyPressData("D");
	}
	//press f key
	public void pressF() {
		command = "xdotool key f";
		runCommand();
		setCurrentKeyPressData("F");
	}
	//press g key
	public void pressG() {
		command = "xdotool key g";
		runCommand();
		setCurrentKeyPressData("G");
	}
	//press h key
	public void pressH() {
		command = "xdotool key h";
		runCommand();
		setCurrentKeyPressData("H");
	}
	//press j key
	public void pressJ() {
		command = "xdotool key j";
		runCommand();
		setCurrentKeyPressData("J");
	}
	//press k key
	public void pressK() {
		command = "xdotool key k";
		runCommand();
		setCurrentKeyPressData("K");
	}
	//press l key
	public void pressL() {
		command = "xdotool key l";
		runCommand();
		setCurrentKeyPressData("L");
	}
	//press ; key
	public void pressSemicolon() {
		command = "xdotool key semicolon";
		runCommand();
		setCurrentKeyPressData("Semicolon");
	}
	//press ' key
	public void pressLeftQuote() {
		command = "xdotool key quoteleft";
		runCommand();
		setCurrentKeyPressData("LeftQuote");
	}
	//press enter/return key
	public void pressEnter() {
		command = "xdotool key Return";
		runCommand();
		setCurrentKeyPressData("Enter");
	}
	//press shift key
	public void pressShift() {
		command = "xdotool key Shift_L";
		runCommand();
		setCurrentKeyPressData("Shift");
	}
	//press z key
	public void pressZ() {
		command = "xdotool key z";
		runCommand();
		setCurrentKeyPressData("Z");
	}
	//press x key
	public void pressX() {
		command = "xdotool key x";
		runCommand();
		setCurrentKeyPressData("X");
	}
	//press c key
	public void pressC() {
		command = "xdotool key c";
		runCommand();
		setCurrentKeyPressData("C");
	}
	//press v key
	public void pressV() {
		command = "xdotool key v";
		runCommand();
		setCurrentKeyPressData("V");
	}
	//press b key
	public void pressB() {
		command = "xdotool key b";
		runCommand();
		setCurrentKeyPressData("B");
	}
	//press n key
	public void pressN() {
		command = "xdotool key n";
		runCommand();
		setCurrentKeyPressData("N");
	}
	//press m key
	public void pressM() {
		command = "xdotool key m";
		runCommand();
		setCurrentKeyPressData("M");
	}
	//press , key
	public void pressComma() {
		command = "xdotool key KP_Separator";
		runCommand();
		setCurrentKeyPressData("Comma");
	}
	//press . key
	public void pressPeriod() {
		command = "xdotool key period";
		runCommand();
		setCurrentKeyPressData("Period");
	}
	//press / key
	public void pressSlash() {
		command = "xdotool key slash";
		runCommand();
		setCurrentKeyPressData("Slash");
	}
	//press ctrl key
	public void pressControl() {
		command = "xdotool key Control_L";
		runCommand();
		setCurrentKeyPressData("Control");
	}
	//press windows key
	public void pressWindowsKey() {
		command = "xdotool key Super_L";
		runCommand();
		setCurrentKeyPressData("Windows");
	}
	//press alt key
	public void pressAlt() {
		command = "xdotool key Alt_L";
		runCommand();
		setCurrentKeyPressData("Alt");
	}
	//press space key
	public void pressSpace() {
		command = "xdotool key KP_Space";
		runCommand();
		setCurrentKeyPressData("Space");
	}	
	//press menu key
	public void pressMenu() {
		command = "xdotool key Menu";
		runCommand();
		setCurrentKeyPressData("Menu");
	}
	//press up arrow key
	public void pressUpArrow() {
		command = "xdotool key Up";
		runCommand();
		setCurrentKeyPressData("UpArrow");
	}
	//press left arrow key
	public void pressLeftArrow() {
		command = "xdotool key Left";
		runCommand();
		setCurrentKeyPressData("LeftArrow");
	}
	//press down arrow key
	public void pressDownArrow() {
		command = "xdotool key Down";
		runCommand();
		setCurrentKeyPressData("DownArrow");
	}
	//press right arrow key
	public void pressRightArrow() {
		command = "xdotool key Right";
		runCommand();
		setCurrentKeyPressData("RightArrow");
	}
	//press Shift + RightQuote keys (~)
	public void pressShiftRightQuote() {
		command = "xdotool key shift+1";
		runCommand();
		setCurrentKeyPressData("Shift+RightQuote");
	}
	//press Shift + 1 keys (!)
	public void pressShift1() {
		command = "xdotool key shift+1";
		runCommand();
		setCurrentKeyPressData("Shift+1");
	}
	//press Shift + 2 keys (@)
	public void pressShift2() {
		command = "xdotool key shift+2";
		runCommand();
		setCurrentKeyPressData("Shift+2");
	}
	//press Shift + 3 keys (#)
	public void pressShift3() {
		command = "xdotool key shift+3";
		runCommand();
		setCurrentKeyPressData("Shift+3");
	}
	//press Shift + 4 keys ($)
	public void pressShift4() {
		command = "xdotool key shift+4";
		runCommand();
		setCurrentKeyPressData("Shift+4");
	}
	//press % key
	public void pressShift5() {
		command = "xdotool key shift+5";
		runCommand();
		setCurrentKeyPressData("Shift+5");
	}
	//press ^ key
	public void pressShift6() {
		command = "xdotool key shift+6";
		runCommand();
		setCurrentKeyPressData("Shift+6");
	}
	//press & key
	public void pressShift7() {
		command = "xdotool key shift+7";
		runCommand();
		setCurrentKeyPressData("Shift+7");
	}
	//press * key
	public void pressShift8() {
		command = "xdotool key shift+8";
		runCommand();
		setCurrentKeyPressData("Shift+8");
	}
	//press ( key
	public void pressShift9() {
		command = "xdotool key shift+9";
		runCommand();
		setCurrentKeyPressData("Shift+9");
	}
	//press ) key
	public void pressShift0() {
		command = "xdotool key shift+0";
		runCommand();
		setCurrentKeyPressData("Shift+0");
	}
	//press _ key
	public void pressShiftMinus() {
		command = "xdotool key shift+minus";
		runCommand();
		setCurrentKeyPressData("Shift+Minus");
	}
	//press + key
	public void pressShiftEqual() {
		command = "xdotool key shift+equal";
		runCommand();
		setCurrentKeyPressData("Shift+Equal");
	}
	//press shift + q keys
	public void pressShiftQ() {
		command = "xdotool key shift+q";
		runCommand();
		setCurrentKeyPressData("Shift+Q");
	}
	//press shift + tab keys
	public void pressShiftTab() {
		command = "xdotool key shift+Tab";
		runCommand();
		setCurrentKeyPressData("Shift+Tab");
	}
	//press shift + w key
	public void pressShiftW() {
		command = "xdotool key shift+w";
		runCommand();
		setCurrentKeyPressData("Shift+W");
	}
	//press shift + e key
	public void pressShiftE() {
		command = "xdotool key shift+e";
		runCommand();
		setCurrentKeyPressData("Shift+E");
	}
	//press shift + r key
	public void pressShiftR() {
		command = "xdotool key shift+r";
		runCommand();
		setCurrentKeyPressData("Shift+R");
	}
	//press shift + t key
	public void pressShiftT() {
		command = "xdotool key shift+t";
		runCommand();
		setCurrentKeyPressData("Shift+T");
	}
	//press shift + y key
	public void pressShiftY() {
		command = "xdotool key shift+y";
		runCommand();
		setCurrentKeyPressData("Shift+Y");
	}
	//press shift + u key
	public void pressShiftU() {
		command = "xdotool key shift+u";
		runCommand();
		setCurrentKeyPressData("Shift+U");
	}
	//press shift + i key
	public void pressShiftI() {
		command = "xdotool key shift+i";
		runCommand();
		setCurrentKeyPressData("Shift+I");
	}
	//press shift + o key
	public void pressShiftO() {
		command = "xdotool key shift+o";
		runCommand();
		setCurrentKeyPressData("Shift+O");
	}
	//press shift + p key
	public void pressShiftP() {
		command = "xdotool key shift+p";
		runCommand();
		setCurrentKeyPressData("Shift+P");
	}
	//press shift + [ key
	public void pressShiftLeftBracket() {
		command = "xdotool key shift+bracketleft";
		runCommand();
		setCurrentKeyPressData("Shift+LeftBracket");
	}
	//press shift + ] key
	public void pressShiftRightBracket() {
		command = "xdotool key shift+bracketright";
		runCommand();
		setCurrentKeyPressData("Shift+RightBracket");
	}
	//press shift + \ key
	public void pressShiftBackslash() {
		command = "xdotool key shift+backslash";
		runCommand();
		setCurrentKeyPressData("Shift+Backslash");
	}
	//press shift + delete key
	public void pressShiftDelete() {
		command = "xdotool key shift+Delete";
		runCommand();
		setCurrentKeyPressData("Shift+Delete");
	}
	//press shift + end key
	public void pressShiftEnd() {
		command = "xdotool key shift+End";
		runCommand();
		setCurrentKeyPressData("Shift+End");
	}
	//press shift + page down key
	public void pressShiftPageDown() {
		command = "xdotool key shift+Page_Down";
		runCommand();
		setCurrentKeyPressData("Shift+PageDown");
	}
	//press shift + a key
	public void pressShiftA() {
		command = "xdotool key shift+a";
		runCommand();
		setCurrentKeyPressData("Shift+A");
	}
	//press shift + s key
	public void pressShiftS() {
		command = "xdotool key shift+s";
		runCommand();
		setCurrentKeyPressData("Shift+S");
	}
	//press shift + d key
	public void pressShiftD() {
		command = "xdotool key shift+d";
		runCommand();
		setCurrentKeyPressData("Shift+D");
	}
	//press shift + f key
	public void pressShiftF() {
		command = "xdotool key shift+f";
		runCommand();
		setCurrentKeyPressData("Shift+F");
	}
	//press shift + g key
	public void pressShiftG() {
		command = "xdotool key shift+g";
		runCommand();
		setCurrentKeyPressData("Shift+G");
	}
	//press shift + h key
	public void pressShiftH() {
		command = "xdotool key shift+h";
		runCommand();
		setCurrentKeyPressData("Shift+H");
	}
	//press shift + j key
	public void pressShiftJ() {
		command = "xdotool key shift+j";
		runCommand();
		setCurrentKeyPressData("Shift+J");
	}
	//press shift + k key
	public void pressShiftK() {
		command = "xdotool key shift+k";
		runCommand();
		setCurrentKeyPressData("Shift+K");
	}
	//press shift + l key
	public void pressShiftL() {
		command = "xdotool key shift+l";
		runCommand();
		setCurrentKeyPressData("Shift+L");
	}
	//press shift + ; key
	public void pressShiftSemicolon() {
		command = "xdotool key shift+semicolon";
		runCommand();
		setCurrentKeyPressData("Shift+Semicolon");
	}
	//press " key
	public void pressShiftLeftQuote() {
		command = "xdotool key shift+quoteleft";
		runCommand();
		setCurrentKeyPressData("Shift+LeftQuote");
	}
	//press shift + enter key
	public void pressShiftEnter() {
		command = "xdotool key shift+Return";
		runCommand();
		setCurrentKeyPressData("Shift+Return");
	}
	//press shift + z key
	public void pressShiftZ() {
		command = "xdotool key shift+z";
		runCommand();
		setCurrentKeyPressData("Shift+Z");
	}
	//press shift + x key
	public void pressShiftX() {
		command = "xdotool key shift+x";
		runCommand();
		setCurrentKeyPressData("Shift+X");
	}
	//press shift + c key
	public void pressShiftC() {
		command = "xdotool key shift+c";
		runCommand();
		setCurrentKeyPressData("Shift+C");
	}
	//press shift + v key
	public void pressShiftV() {
		command = "xdotool key shift+v";
		runCommand();
		setCurrentKeyPressData("Shift+V");
	}
	//press shift + b key
	public void pressShiftB() {
		command = "xdotool key shift+b";
		runCommand();
		setCurrentKeyPressData("Shift+B");
	}
	//press shift + n key
	public void pressShiftN() {
		command = "xdotool key shift+n";
		runCommand();
		setCurrentKeyPressData("Shift+N");
	}
	//press shift + m key
	public void pressShiftM() {
		command = "xdotool key shift+m";
		runCommand();
		setCurrentKeyPressData("Shift+M");
	}
	//press shift + , key
	public void pressShiftComma() {
		command = "xdotool key shift+KP_Separator";
		runCommand();
		setCurrentKeyPressData("Shift+Separator");
	}
	//press shift + . key
	public void pressShiftPeriod() {
		command = "xdotool key shift+period";
		runCommand();
		setCurrentKeyPressData("Shift+Period");
	}
	//press shift + / key
	public void pressShiftSlash() {
		command = "xdotool key shift+slash";
		runCommand();
		setCurrentKeyPressData("Shift+Slash");
	}
	//press shift + up arrow key
	public void pressShiftUpArrow() {
		command = "xdotool key shift+Up";
		runCommand();
		setCurrentKeyPressData("Shift+UpArrow");
	}
	//press shift + left arrow key
	public void pressShiftLeftArrow() {
		command = "xdotool key shift+Left";
		runCommand();
		setCurrentKeyPressData("Shift+LeftArrow");
	}
	//press shift + down arrow key
	public void pressShiftDownArrow() {
		command = "xdotool key shift+Down";
		runCommand();
		setCurrentKeyPressData("Shift+DownArrow");
	}
	//press shift + right arrow key
	public void pressShiftRightArrow() {
		command = "xdotool key shift+Right";
		runCommand();
		setCurrentKeyPressData("Shift+RightArrow");
	}
	//press shift + space key
	public void pressShiftSpace() {
		command = "xdotool key shift+KP_Space";
		runCommand();
		setCurrentKeyPressData("Shift+Space");
	}

	//press control + shift + tilde keys
	public void pressControlShiftRightQuote() {
		command = "xdotool key Control_L+shift+quoteright";
		runCommand();
		setCurrentKeyPressData("Control+Shift+RightQuote");
	}
	//press control + shift + 1 keys
	public void pressControlShift1() {
		command = "xdotool key Control_L+shift+1";
		runCommand();
		setCurrentKeyPressData("Control+Shift+1");
	}
	//press control + shift + 2 keys
	public void pressControlShift2() {
		command = "xdotool key Control_L+shift+2";
		runCommand();
		setCurrentKeyPressData("Control+Shift+2");
	}
	//press control + shift + 3 keys
	public void pressControlShift3() {
		command = "xdotool key Control_L+shift+3";
		runCommand();
		setCurrentKeyPressData("Control+Shift+3");
	}
	//press control + shift + 4 keys
	public void pressControlShift4() {
		command = "xdotool key Control_L+shift+4";
		runCommand();
		setCurrentKeyPressData("Control+Shift+4");
	}
	//press control + shift + 5 keys
	public void pressControlShift5() {
		command = "xdotool key Control_L+shift+5";
		runCommand();
		setCurrentKeyPressData("Control+Shift+5");
	}
	//press control + shift + 6 keys
	public void pressControlShift6() {
		command = "xdotool key Control_L+shift+6";
		runCommand();
		setCurrentKeyPressData("Control+Shift+6");
	}
	//press control + shift + 7 keys
	public void pressControlShift7() {
		command = "xdotool key Control_L+shift+7";
		runCommand();
		setCurrentKeyPressData("Control+Shift+7");
	}
	//press control + shift + 8 keys
	public void pressControlShift8() {
		command = "xdotool key Control_L+shift+8";
		runCommand();
		setCurrentKeyPressData("Control+Shift+8");
	}
	//press control + shift + 9 keys
	public void pressControlShift9() {
		command = "xdotool key Control_L+shift+9";
		runCommand();
		setCurrentKeyPressData("Control+Shift+9");
	}
	//press control + shift + 0 keys
	public void pressControlShift0() {
		command = "xdotool key Control_L+shift+0";
		runCommand();
		setCurrentKeyPressData("Control+Shift+0");
	}
	//press control + shift + - keys
	public void pressControlShiftMinus() {
		command = "xdotool key Control_L+shift+minus";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Minus");
	}
	//press control + shift + = keys
	public void pressControlShiftEqual() {
		command = "xdotool key Control_L+shift+equal";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Equal");
	}
	//press control + shift + tab keys
	public void pressControlShiftTab() {
		command = "xdotool key Control_L+shift+Tab";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Tab");
	}
	//press control + shift + q key
	public void pressControlShiftQ() {
		command = "xdotool key Control_L+shift+q";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Q");
	}
	//press control + shift + w key
	public void pressControlShiftW() {
		command = "xdotool key Control_L+shift+w";
		runCommand();
		setCurrentKeyPressData("Control+Shift+W");
	}
	//press control + shift + e key
	public void pressControlShiftE() {
		command = "xdotool key Control_L+shift+e";
		runCommand();
		setCurrentKeyPressData("Control+Shift+E");
	}
	//press control + shift + r key
	public void pressControlShiftR() {
		command = "xdotool key Control_L+shift+r";
		runCommand();
		setCurrentKeyPressData("Control+Shift+R");
	}
	//press control + shift + t key
	public void pressControlShiftT() {
		command = "xdotool key Control_L+shift+t";
		runCommand();
		setCurrentKeyPressData("Control+Shift+T");
	}
	//press control + shift + y key
	public void pressControlShiftY() {
		command = "xdotool key Control_L+shift+y";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Y");
	}
	//press control + shift + u key
	public void pressControlShiftU() {
		command = "xdotool key Control_L+shift+u";
		runCommand();
		setCurrentKeyPressData("Control+Shift+U");
	}
	//press control + shift + i key
	public void pressControlShiftI() {
		command = "xdotool key Control_L+shift+i";
		runCommand();
		setCurrentKeyPressData("Control+Shift+I");
	}
	//press control + shift + o key
	public void pressControlShiftO() {
		command = "xdotool key Control_L+shift+o";
		runCommand();
		setCurrentKeyPressData("Control+Shift+O");
	}
	//press control + shift + p key
	public void pressControlShiftP() {
		command = "xdotool key Control_L+shift+p";
		runCommand();
		setCurrentKeyPressData("Control+Shift+P");
	}
	//press control + shift + [ key
	public void pressControlShiftLeftBracket() {
		command = "xdotool key Control_L+shift+bracketleft";
		runCommand();
		setCurrentKeyPressData("Control+Shift+LeftBracket");
	}
	//press control + shift + ] key
	public void pressControlShiftRightBracket() {
		command = "xdotool key Control_L+shift+bracketright";
		runCommand();
		setCurrentKeyPressData("Control+Shift+RightBracket");
	}
	//press control + shift + \ key
	public void pressControlShiftBackslash() {
		command = "xdotool key Control_L+shift+backslash";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Backslash");
	}
	//press control + shift + delete key
	public void pressControlShiftDelete() {
		command = "xdotool key Control_L+shift+Delete";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Delete");
	}
	//press control + shift + end key
	public void pressControlShiftEnd() {
		command = "xdotool key Control_L+shift+End";
		runCommand();
		setCurrentKeyPressData("Control+Shift+End");
	}
	//press control + shift + page down key
	public void pressControlShiftPageDown() {
		command = "xdotool key Control_L+shift+Page_Down";
		runCommand();
		setCurrentKeyPressData("Control+Shift+PageDown");
	}
	//press control + shift + a key
	public void pressControlShiftA() {
		command = "xdotool key Control_L+shift+a";
		runCommand();
		setCurrentKeyPressData("Control+Shift+A");
	}
	//press control + shift + s key
	public void pressControlShiftS() {
		command = "xdotool key Control_L+shift+s";
		runCommand();
		setCurrentKeyPressData("Control+Shift+S");
	}
	//press control + shift + d key
	public void pressControlShiftD() {
		command = "xdotool key Control_L+shift+d";
		runCommand();
		setCurrentKeyPressData("Control+Shift+D");
	}
	//press control + shift + f key
	public void pressControlShiftF() {
		command = "xdotool key Control_L+shift+f";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F");
	}
	//press control + shift + g key
	public void pressControlShiftG() {
		command = "xdotool key Control_L+shift+g";
		runCommand();
		setCurrentKeyPressData("Control+Shift+G");
	}
	//press control + shift + h key
	public void pressControlShiftH() {
		command = "xdotool key Control_L+shift+h";
		runCommand();
		setCurrentKeyPressData("Control+Shift+H");
	}
	//press control + shift + j key
	public void pressControlShiftJ() {
		command = "xdotool key Control_L+shift+j";
		runCommand();
		setCurrentKeyPressData("Control+Shift+J");
	}
	//press control + shift + k key
	public void pressControlShiftK() {
		command = "xdotool key Control_L+shift+k";
		runCommand();
		setCurrentKeyPressData("Control+Shift+K");
	}
	//press control + shift + l key
	public void pressControlShiftL() {
		command = "xdotool key Control_L+shift+l";
		runCommand();
		setCurrentKeyPressData("Control+Shift+L");
	}
	//press control + shift + ; key
	public void pressControlShiftSemicolon() {
		command = "xdotool key Control_L+shift+semicolon";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Semicolon");
	}
	//press control + shift + ' key
	public void pressControlShiftLeftQuote() {
		command = "xdotool key Control_L+shift+quoteleft";
		runCommand();
		setCurrentKeyPressData("Control+Shift+LeftQuote");
	}
	//press control + shift + enter key
	public void pressControlShiftEnter() {
		command = "xdotool key Control_L+shift+Return";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Enter");
	}
	//press control + shift + z key
	public void pressControlShiftZ() {
		command = "xdotool key Control_L+shift+z";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Z");
	}
	//press control + shift + x key
	public void pressControlShiftX() {
		command = "xdotool key Control_L+shift+x";
		runCommand();
		setCurrentKeyPressData("Control+Shift+X");
	}
	//press control + shift + c key
	public void pressControlShiftC() {
		command = "xdotool key Control_L+shift+c";
		runCommand();
		setCurrentKeyPressData("Control+Shift+C");
	}
	//press control + shift + v key
	public void pressControlShiftV() {
		command = "xdotool key Control_L+shift+v";
		runCommand();
		setCurrentKeyPressData("Control+Shift+V");
	}
	//press control + shift + b key
	public void pressControlShiftB() {
		command = "xdotool key Control_L+shift+b";
		runCommand();
		setCurrentKeyPressData("Control+Shift+B");
	}
	//press control + shift + n key
	public void pressControlShiftN() {
		command = "xdotool key Control_L+shift+n";
		runCommand();
		setCurrentKeyPressData("Control+Shift+N");
	}
	//press control + shift + m key
	public void pressControlShiftM() {
		command = "xdotool key Control_L+shift+m";
		runCommand();
		setCurrentKeyPressData("Control+Shift+M");
	}
	//press control + shift + , key
	public void pressControlShiftComma() {
		command = "xdotool key Control_L+shift+KP_Separator";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Comma");
	}
	//press control + shift + . key
	public void pressControlShiftPeriod() {
		command = "xdotool key Control_L+shift+period";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Period");
	}
	//press control + shift + / key
	public void pressControlShiftSlash() {
		command = "xdotool key Control_L+shift+slash";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Slash");
	}
	//press control + shift + up arrow key
	public void pressControlShiftUpArrow() {
		command = "xdotool key Control_L+shift+Up";
		runCommand();
		setCurrentKeyPressData("Control+Shift+UpArrow");
	}
	//press control + shift + left arrow key
	public void pressControlShiftLeftArrow() {
		command = "xdotool key Control_L+shift+Left";
		runCommand();
		setCurrentKeyPressData("Control+Shift+LeftArrow");
	}
	//press control + shift + down arrow key
	public void pressControlShiftDownArrow() {
		command = "xdotool key Control_L+shift+Down";
		runCommand();
		setCurrentKeyPressData("Control+Shift+DownArrow");
	}
	//press control + shift + right arrow key
	public void pressControlShiftRightArrow() {
		command = "xdotool key Control_L+shift+Right";
		runCommand();
		setCurrentKeyPressData("Control+Shift+RightArrow");
	}
	//press control + shift + space key
	public void pressControlShiftSpace() {
		command = "xdotool key Control_L+shift+KP_Space";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Space");
	}
	
	//press control + tilde keys
	public void pressControlRightQuote() {
		command = "xdotool key Control_L+quoteright";
		runCommand();
		setCurrentKeyPressData("Control+RightQuote");
	}
	//press control + 1 keys
	public void pressControl1() {
		command = "xdotool key Control_L+1";
		runCommand();
		setCurrentKeyPressData("Control+1");
	}
	//press control + 2 keys
	public void pressControl2() {
		command = "xdotool key Control_L+2";
		runCommand();
		setCurrentKeyPressData("Control+2");
	}
	//press control + 3 keys
	public void pressControl3() {
		command = "xdotool key Control_L+3";
		runCommand();
		setCurrentKeyPressData("Control+3");
	}
	//press control + 4 keys
	public void pressControl4() {
		command = "xdotool key Control_L+4";
		runCommand();
		setCurrentKeyPressData("Control+4");
	}
	//press control + 5 keys
	public void pressControl5() {
		command = "xdotool key Control_L+5";
		runCommand();
		setCurrentKeyPressData("Control+5");
	}
	//press control + 6 keys
	public void pressControl6() {
		command = "xdotool key Control_L+6";
		runCommand();
		setCurrentKeyPressData("Control+6");
	}
	//press control + 7 keys
	public void pressControl7() {
		command = "xdotool key Control_L+7";
		runCommand();
		setCurrentKeyPressData("Control+7");
	}
	//press control + 8 keys
	public void pressControl8() {
		command = "xdotool key Control_L+8";
		runCommand();
		setCurrentKeyPressData("Control+8");
	}
	//press control + 9 keys
	public void pressControl9() {
		command = "xdotool key Control_L+9";
		runCommand();
		setCurrentKeyPressData("Control+9");
	}
	//press control + 0 keys
	public void pressControl0() {
		command = "xdotool key Control_L+0";
		runCommand();
		setCurrentKeyPressData("Control+0");
	}
	//press control + - keys
	public void pressControlMinus() {
		command = "xdotool key Control_L+minus";
		runCommand();
		setCurrentKeyPressData("Control+Minus");
	}
	//press control + = keys
	public void pressControlEqual() {
		command = "xdotool key Control_L+equal";
		runCommand();
		setCurrentKeyPressData("Control+Equal");
	}
	//press control + tab keys
	public void pressControlTab() {
		command = "xdotool key Control_L+Tab";
		runCommand();
		setCurrentKeyPressData("Control+Tab");
	}
	//press control + q key
	public void pressControlQ() {
		command = "xdotool key Control_L+q";
		runCommand();
		setCurrentKeyPressData("Control+Q");
	}
	//press control + w key
	public void pressControlW() {
		command = "xdotool key Control_L+w";
		runCommand();
		setCurrentKeyPressData("Control+W");
	}
	//press control + e key
	public void pressControlE() {
		command = "xdotool key Control_L+e";
		runCommand();
		setCurrentKeyPressData("Control+E");
	}
	//press control + r key
	public void pressControlR() {
		command = "xdotool key Control_L+r";
		runCommand();
		setCurrentKeyPressData("Control+R");
	}
	//press control + t key
	public void pressControlT() {
		command = "xdotool key Control_L+t";
		runCommand();
		setCurrentKeyPressData("Control+T");
	}
	//press control + y key
	public void pressControlY() {
		command = "xdotool key Control_L+y";
		runCommand();
		setCurrentKeyPressData("Control+Y");
	}
	//press control + u key
	public void pressControlU() {
		command = "xdotool key Control_L+u";
		runCommand();
		setCurrentKeyPressData("Control+U");
	}
	//press control + i key
	public void pressControlI() {
		command = "xdotool key Control_L+i";
		runCommand();
		setCurrentKeyPressData("Control+I");
	}
	//press control + o key
	public void pressControlO() {
		command = "xdotool key Control_L+o";
		runCommand();
		setCurrentKeyPressData("Control+O");
	}
	//press control + p key
	public void pressControlP() {
		command = "xdotool key Control_L+p";
		runCommand();
		setCurrentKeyPressData("Control+P");
	}
	//press control + [ key
	public void pressControlLeftBracket() {
		command = "xdotool key Control_L+bracketleft";
		runCommand();
		setCurrentKeyPressData("Control+LeftBracket");
	}
	//press control + ] key
	public void pressControlRightBracket() {
		command = "xdotool key Control_L+bracketright";
		runCommand();
		setCurrentKeyPressData("Control+RightBracket");
	}
	//press control + \ key
	public void pressControlBackslash() {
		command = "xdotool key Control_L+backslash";
		runCommand();
		setCurrentKeyPressData("Control+Backslash");
	}
	//press control + delete key
	public void pressControlDelete() {
		command = "xdotool key Control_L+Delete";
		runCommand();
		setCurrentKeyPressData("Control+Delete");
	}
	//press control + end key
	public void pressControlEnd() {
		command = "xdotool key Control_L+End";
		runCommand();
		setCurrentKeyPressData("Control+End");
	}
	//press control + page down key
	public void pressControlPageDown() {
		command = "xdotool key Control_L+Page_Down";
		runCommand();
		setCurrentKeyPressData("Control+PageDown");
	}
	//press control + a key
	public void pressControlA() {
		command = "xdotool key Control_L+a";
		runCommand();
		setCurrentKeyPressData("Control+A");
	}
	//press control + s key
	public void pressControlS() {
		command = "xdotool key Control_L+s";
		runCommand();
		setCurrentKeyPressData("Control+S");
	}
	//press control + d key
	public void pressControlD() {
		command = "xdotool key Control_L+d";
		runCommand();
		setCurrentKeyPressData("Control+D");
	}
	//press control + f key
	public void pressControlF() {
		command = "xdotool key Control_L+f";
		runCommand();
		setCurrentKeyPressData("Control+F");
	}
	//press control + g key
	public void pressControlG() {
		command = "xdotool key Control_L+g";
		runCommand();
		setCurrentKeyPressData("Control+G");
	}
	//press control + h key
	public void pressControlH() {
		command = "xdotool key Control_L+h";
		runCommand();
		setCurrentKeyPressData("Control+H");
	}
	//press control + j key
	public void pressControlJ() {
		command = "xdotool key Control_L+j";
		runCommand();
		setCurrentKeyPressData("Control+J");
	}
	//press control + k key
	public void pressControlK() {
		command = "xdotool key Control_L+k";
		runCommand();
		setCurrentKeyPressData("Control+K");
	}
	//press control + l key
	public void pressControlL() {
		command = "xdotool key Control_L+l";
		runCommand();
		setCurrentKeyPressData("Control+L");
	}
	//press control + ; key
	public void pressControlSemicolon() {
		command = "xdotool key Control_L+semicolon";
		runCommand();
		setCurrentKeyPressData("Control+Semicolon");
	}
	//press control + ' key
	public void pressControlLeftQuote() {
		command = "xdotool key Control_L+quoteleft";
		runCommand();
		setCurrentKeyPressData("Control+LeftQuote");
	}
	//press control + enter key
	public void pressControlEnter() {
		command = "xdotool key Control_L+Return";
		runCommand();
		setCurrentKeyPressData("Control+Enter");
	}
	//press control + z key
	public void pressControlZ() {
		command = "xdotool key Control_L+z";
		runCommand();
		setCurrentKeyPressData("Control+Z");
	}
	//press control + x key
	public void pressControlX() {
		command = "xdotool key Control_L+x";
		runCommand();
		setCurrentKeyPressData("Control+X");
	}
	//press control + c key
	public void pressControlC() {
		command = "xdotool key Control_L+c";
		runCommand();
		setCurrentKeyPressData("Control+C");
	}
	//press control + v key
	public void pressControlV() {
		command = "xdotool key Control_L+v";
		runCommand();
		setCurrentKeyPressData("Control+V");
	}
	//press control + b key
	public void pressControlB() {
		command = "xdotool key Control_L+b";
		runCommand();
		setCurrentKeyPressData("Control+B");
	}
	//press control + n key
	public void pressControlN() {
		command = "xdotool key Control_L+n";
		runCommand();
		setCurrentKeyPressData("Control+N");
	}
	//press control + m key
	public void pressControlM() {
		command = "xdotool key Control_L+m";
		runCommand();
		setCurrentKeyPressData("Control+M");
	}
	//press control + , key
	public void pressControlComma() {
		command = "xdotool key Control_L+KP_Separator";
		runCommand();
		setCurrentKeyPressData("Control+Comma");
	}
	//press control + . key
	public void pressControlPeriod() {
		command = "xdotool key Control_L+period";
		runCommand();
		setCurrentKeyPressData("Control+Period");
	}
	//press control + / key
	public void pressControlSlash() {
		command = "xdotool key Control_L+slash";
		runCommand();
		setCurrentKeyPressData("Control+Slash");
	}
	//press control + up arrow key
	public void pressControlUpArrow() {
		command = "xdotool key Control_L+Up";
		runCommand();
		setCurrentKeyPressData("Control+UpArrow");
	}
	//press control + left arrow key
	public void pressControlLeftArrow() {
		command = "xdotool key Control_L+Left";
		runCommand();
		setCurrentKeyPressData("Control+LeftArrow");
	}
	//press control + down arrow key
	public void pressControlDownArrow() {
		command = "xdotool key Control_L+Down";
		runCommand();
		setCurrentKeyPressData("Control+DownArrow");
	}
	//press control + right arrow key
	public void pressControlRightArrow() {
		command = "xdotool key Control_L+Right";
		runCommand();
		setCurrentKeyPressData("Control+RightArrow");
	}
	//press control + space key
	public void pressControlSpace() {
		command = "xdotool key Control_L+KP_Space";
		runCommand();
		setCurrentKeyPressData("Control+Space");
	}
	
	//press alt + tilde keys
	public void pressAltRightQuote() {
		command = "xdotool key Alt_L+quoteright";
		runCommand();
		setCurrentKeyPressData("Alt+RightQuote");
	}
	//press alt + 1 keys
	public void pressAlt1() {
		command = "xdotool key Alt_L+1";
		runCommand();
		setCurrentKeyPressData("Alt+1");
	}
	//press alt + 2 keys
	public void pressAlt2() {
		command = "xdotool key Alt_L+2";
		runCommand();
		setCurrentKeyPressData("Alt+2");
	}
	//press alt + 3 keys
	public void pressAlt3() {
		command = "xdotool key Alt_L+3";
		runCommand();
		setCurrentKeyPressData("Alt+3");
	}
	//press alt + 4 keys
	public void pressAlt4() {
		command = "xdotool key Alt_L+4";
		runCommand();
		setCurrentKeyPressData("Alt+4");
	}
	//press alt + 5 keys
	public void pressAlt5() {
		command = "xdotool key Alt_L+5";
		runCommand();
		setCurrentKeyPressData("Alt+5");
	}
	//press alt + 6 keys
	public void pressAlt6() {
		command = "xdotool key Alt_L+6";
		runCommand();
		setCurrentKeyPressData("Alt+6");
	}
	//press alt + 7 keys
	public void pressAlt7() {
		command = "xdotool key Alt_L+7";
		runCommand();
		setCurrentKeyPressData("Alt+7");
	}
	//press alt + 8 keys
	public void pressAlt8() {
		command = "xdotool key Alt_L+8";
		runCommand();
		setCurrentKeyPressData("Alt+8");
	}
	//press alt + 9 keys
	public void pressAlt9() {
		command = "xdotool key Alt_L+9";
		runCommand();
		setCurrentKeyPressData("Alt+9");
	}
	//press alt + 0 keys
	public void pressAlt0() {
		command = "xdotool key Alt_L+0";
		runCommand();
		setCurrentKeyPressData("Alt+0");
	}
	//press alt + - keys
	public void pressAltMinus() {
		command = "xdotool key Alt_L+minus";
		runCommand();
		setCurrentKeyPressData("Alt+Minus");
	}
	//press alt + = keys
	public void pressAltEqual() {
		command = "xdotool key Alt_L+equal";
		runCommand();
		setCurrentKeyPressData("Alt+Equal");
	}
	//press alt + tab keys
	public void pressAltTab() {
		command = "xdotool key Alt_L+Tab";
		runCommand();
		setCurrentKeyPressData("Alt+Tab");
	}
	//press alt + q key
	public void pressAltQ() {
		command = "xdotool key Alt_L+q";
		runCommand();
		setCurrentKeyPressData("Alt+Q");
	}
	//press alt + w key
	public void pressAltW() {
		command = "xdotool key Alt_L+w";
		runCommand();
		setCurrentKeyPressData("Alt+W");
	}
	//press alt + e key
	public void pressAltE() {
		command = "xdotool key Alt_L+e";
		runCommand();
		setCurrentKeyPressData("Alt+E");
	}
	//press alt + r key
	public void pressAltR() {
		command = "xdotool key Alt_L+r";
		runCommand();
		setCurrentKeyPressData("Alt+R");
	}
	//press alt + t key
	public void pressAltT() {
		command = "xdotool key Alt_L+t";
		runCommand();
		setCurrentKeyPressData("Alt+T");
	}
	//press alt + y key
	public void pressAltY() {
		command = "xdotool key Alt_L+y";
		runCommand();
		setCurrentKeyPressData("Alt+Y");
	}
	//press alt + u key
	public void pressAltU() {
		command = "xdotool key Alt_L+u";
		runCommand();
		setCurrentKeyPressData("Alt+U");
	}
	//press alt + i key
	public void pressAltI() {
		command = "xdotool key Alt_L+i";
		runCommand();
		setCurrentKeyPressData("Alt+I");
	}
	//press alt + o key
	public void pressAltO() {
		command = "xdotool key Alt_L+o";
		runCommand();
		setCurrentKeyPressData("Alt+O");
	}
	//press alt + p key
	public void pressAltP() {
		command = "xdotool key Alt_L+p";
		runCommand();
		setCurrentKeyPressData("Alt+P");
	}
	//press alt + [ key
	public void pressAltLeftBracket() {
		command = "xdotool key Alt_L+bracketleft";
		runCommand();
		setCurrentKeyPressData("Alt+LeftBracket");
	}
	//press alt + ] key
	public void pressAltRightBracket() {
		command = "xdotool key Alt_L+bracketright";
		runCommand();
		setCurrentKeyPressData("Alt+RightBracket");
	}
	//press alt + \ key
	public void pressAltBackslash() {
		command = "xdotool key Alt_L+backslash";
		runCommand();
		setCurrentKeyPressData("Alt+Backslash");
	}
	//press alt + delete key
	public void pressAltDelete() {
		command = "xdotool key Alt_L+Delete";
		runCommand();
		setCurrentKeyPressData("Alt+Delete");
	}
	//press alt + end key
	public void pressAltEnd() {
		command = "xdotool key Alt_L+End";
		runCommand();
		setCurrentKeyPressData("Alt+End");
	}
	//press alt + page down key
	public void pressAltPageDown() {
		command = "xdotool key Alt_L+Page_Down";
		runCommand();
		setCurrentKeyPressData("Alt+PageDown");
	}
	//press alt + a key
	public void pressAltA() {
		command = "xdotool key Alt_L+a";
		runCommand();
		setCurrentKeyPressData("Alt+A");
	}
	//press alt + s key
	public void pressAltS() {
		command = "xdotool key Alt_L+s";
		runCommand();
		setCurrentKeyPressData("Alt+S");
	}
	//press alt + d key
	public void pressAltD() {
		command = "xdotool key Alt_L+d";
		runCommand();
		setCurrentKeyPressData("Alt+D");
	}
	//press alt + f key
	public void pressAltF() {
		command = "xdotool key Alt_L+f";
		runCommand();
		setCurrentKeyPressData("Alt+F");
	}
	//press alt + g key
	public void pressAltG() {
		command = "xdotool key Alt_L+g";
		runCommand();
		setCurrentKeyPressData("Alt+G");
	}
	//press alt + h key
	public void pressAltH() {
		command = "xdotool key Alt_L+h";
		runCommand();
		setCurrentKeyPressData("Alt+H");
	}
	//press alt + j key
	public void pressAltJ() {
		command = "xdotool key Alt_L+j";
		runCommand();
		setCurrentKeyPressData("Alt+J");
	}
	//press alt + k key
	public void pressAltK() {
		command = "xdotool key Alt_L+k";
		runCommand();
		setCurrentKeyPressData("Alt+K");
	}
	//press alt + l key
	public void pressAltL() {
		command = "xdotool key Alt_L+l";
		runCommand();
		setCurrentKeyPressData("Alt+L");
	}
	//press alt + ; key
	public void pressAltSemicolon() {
		command = "xdotool key Alt_L+semicolon";
		runCommand();
		setCurrentKeyPressData("Alt+Semicolon");
	}
	//press alt + ' key
	public void pressAltLeftQuote() {
		command = "xdotool key Alt_L+quoteleft";
		runCommand();
		setCurrentKeyPressData("Alt+LeftQuote");
	}
	//press alt + enter key
	public void pressAltEnter() {
		command = "xdotool key Alt_L+Return";
		runCommand();
		setCurrentKeyPressData("Alt+Enter");
	}
	//press alt + z key
	public void pressAltZ() {
		command = "xdotool key Alt_L+z";
		runCommand();
		setCurrentKeyPressData("Alt+Z");
	}
	//press alt + x key
	public void pressAltX() {
		command = "xdotool key Alt_L+x";
		runCommand();
		setCurrentKeyPressData("Alt+X");
	}
	//press alt + c key
	public void pressAltC() {
		command = "xdotool key Alt_L+c";
		runCommand();
		setCurrentKeyPressData("Alt+C");
	}
	//press alt + v key
	public void pressAltV() {
		command = "xdotool key Alt_L+v";
		runCommand();
		setCurrentKeyPressData("Alt+V");
	}
	//press alt + b key
	public void pressAltB() {
		command = "xdotool key Alt_L+b";
		runCommand();
		setCurrentKeyPressData("Alt+B");
	}
	//press alt + n key
	public void pressAltN() {
		command = "xdotool key Alt_L+n";
		runCommand();
		setCurrentKeyPressData("Alt+N");
	}
	//press alt + m key
	public void pressAltM() {
		command = "xdotool key Alt_L+m";
		runCommand();
		setCurrentKeyPressData("Alt+M");
	}
	//press alt + , key
	public void pressAltComma() {
		command = "xdotool key Alt_L+KP_Separator";
		runCommand();
		setCurrentKeyPressData("Alt+Comma");
	}
	//press alt + . key
	public void pressAltPeriod() {
		command = "xdotool key Alt_L+period";
		runCommand();
		setCurrentKeyPressData("Alt+Period");
	}
	//press alt + / key
	public void pressAltSlash() {
		command = "xdotool key Alt_L+slash";
		runCommand();
		setCurrentKeyPressData("Alt+Slash");
	}
	//press alt + up arrow key
	public void pressAltUpArrow() {
		command = "xdotool key Alt_L+Up";
		runCommand();
		setCurrentKeyPressData("Alt+UpArrow");
	}
	//press alt + left arrow key
	public void pressAltLeftArrow() {
		command = "xdotool key Alt_L+Left";
		runCommand();
		setCurrentKeyPressData("Alt+LeftArrow");
	}
	//press alt + down arrow key
	public void pressAltDownArrow() {
		command = "xdotool key Alt_L+Down";
		runCommand();
		setCurrentKeyPressData("Alt+DownArrow");
	}
	//press alt + right arrow key
	public void pressAltRightArrow() {
		command = "xdotool key Alt_L+Right";
		runCommand();
		setCurrentKeyPressData("Alt+RightArrow");
	}
	//press alt + space key
	public void pressAltSpace() {
		command = "xdotool key Alt_L+KP_Space";
		runCommand();
		setCurrentKeyPressData("Alt+Space");
	}
	
	//press control + alt + tilde keys
	public void pressControlAltRightQuote() {
		command = "xdotool key Control_L+Alt_L+quoteright";
		runCommand();
		setCurrentKeyPressData("Control+Alt+RightQuote");
	}
	//press control + alt + 1 keys
	public void pressControlAlt1() {
		command = "xdotool key Control_L+Alt_L+1";
		runCommand();
		setCurrentKeyPressData("Control+Alt+1");
	}
	//press control + alt + 2 keys
	public void pressControlAlt2() {
		command = "xdotool key Control_L+Alt_L+2";
		runCommand();
		setCurrentKeyPressData("Control+Alt+2");
	}
	//press control + alt + 3 keys
	public void pressControlAlt3() {
		command = "xdotool key Control_L+Alt_L+3";
		runCommand();
		setCurrentKeyPressData("Control+Alt+3");
	}
	//press control + alt + 4 keys
	public void pressControlAlt4() {
		command = "xdotool key Control_L+Alt_L+4";
		runCommand();
		setCurrentKeyPressData("Control+Alt+4");
	}
	//press control + alt + 5 keys
	public void pressControlAlt5() {
		command = "xdotool key Control_L+Alt_L+5";
		runCommand();
		setCurrentKeyPressData("Control+Alt+5");
	}
	//press control + alt + 6 keys
	public void pressControlAlt6() {
		command = "xdotool key Control_L+Alt_L+6";
		runCommand();
		setCurrentKeyPressData("Control+Alt+6");
	}
	//press control + alt + 7 keys
	public void pressControlAlt7() {
		command = "xdotool key Control_L+Alt_L+7";
		runCommand();
		setCurrentKeyPressData("Control+Alt+7");
	}
	//press control + alt + 8 keys
	public void pressControlAlt8() {
		command = "xdotool key Control_L+Alt_L+8";
		runCommand();
		setCurrentKeyPressData("Control+Alt+8");
	}
	//press control + alt + 9 keys
	public void pressControlAlt9() {
		command = "xdotool key Control_L+Alt_L+9";
		runCommand();
		setCurrentKeyPressData("Control+Alt+9");
	}
	//press control + alt + 0 keys
	public void pressControlAlt0() {
		command = "xdotool key Control_L+Alt_L+0";
		runCommand();
		setCurrentKeyPressData("Control+Alt+0");
	}
	//press control + alt + - keys
	public void pressControlAltMinus() {
		command = "xdotool key Control_L+Alt_L+minus";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Minus");
	}
	//press control + alt + = keys
	public void pressControlAltEqual() {
		command = "xdotool key Control_L+Alt_L+equal";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Equal");
	}
	//press control + alt + tab keys
	public void pressControlAltTab() {
		command = "xdotool key Control_L+Alt_L+Tab";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Tab");
	}
	//press control + alt + q key
	public void pressControlAltQ() {
		command = "xdotool key Control_L+Alt_L+q";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Q");
	}
	//press control + alt + w key
	public void pressControlAltW() {
		command = "xdotool key Control_L+Alt_L+w";
		runCommand();
		setCurrentKeyPressData("Control+Alt+W");
	}
	//press control + alt + e key
	public void pressControlAltE() {
		command = "xdotool key Control_L+Alt_L+e";
		runCommand();
		setCurrentKeyPressData("Control+Alt+E");
	}
	//press control + alt + r key
	public void pressControlAltR() {
		command = "xdotool key Control_L+Alt_L+r";
		runCommand();
		setCurrentKeyPressData("Control+Alt+R");
	}
	//press control + alt + t key
	public void pressControlAltT() {
		command = "xdotool key Control_L+Alt_L+t";
		runCommand();
		setCurrentKeyPressData("Control+Alt+T");
	}
	//press control + alt + y key
	public void pressControlAltY() {
		command = "xdotool key Control_L+Alt_L+y";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Y");
	}
	//press control + alt + u key
	public void pressControlAltU() {
		command = "xdotool key Control_L+Alt_L+u";
		runCommand();
		setCurrentKeyPressData("Control+Alt+U");
	}
	//press control + alt + i key
	public void pressControlAltI() {
		command = "xdotool key Control_L+Alt_L+i";
		runCommand();
		setCurrentKeyPressData("Control+Alt+I");
	}
	//press control + alt + o key
	public void pressControlAltO() {
		command = "xdotool key Control_L+Alt_L+o";
		runCommand();
		setCurrentKeyPressData("Control+Alt+O");
	}
	//press control + alt + p key
	public void pressControlAltP() {
		command = "xdotool key Control_L+Alt_L+p";
		runCommand();
		setCurrentKeyPressData("Control+Alt+P");
	}
	//press control + alt + [ key
	public void pressControlAltLeftBracket() {
		command = "xdotool key Control_L+Alt_L+bracketleft";
		runCommand();
		setCurrentKeyPressData("Control+Alt+LeftBracket");
	}
	//press control + alt + ] key
	public void pressControlAltRightBracket() {
		command = "xdotool key Control_L+Alt_L+bracketright";
		runCommand();
		setCurrentKeyPressData("Control+Alt+RightBracket");
	}
	//press control + alt + \ key
	public void pressControlAltBackslash() {
		command = "xdotool key Control_L+Alt_L+backslash";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Backslash");
	}
	public void pressControlAltDelete() {
		command = "xdotool key Control_L+Alt_L+Delete";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Delete");
	}
	//press control + alt + end key
	public void pressControlAltEnd() {
		command = "xdotool key Control_L+Alt_L+End";
		runCommand();
		setCurrentKeyPressData("Control+Alt+End");
	}
	//press control + alt + page down key
	public void pressControlAltPageDown() {
		command = "xdotool key Control_L+Alt_L+Page_Down";
		runCommand();
		setCurrentKeyPressData("Control+Alt+PageDown");
	}
	//press control + alt + a key
	public void pressControlAltA() {
		command = "xdotool key Control_L+Alt_L+A";
		runCommand();
		setCurrentKeyPressData("Control+Alt+A");
	}
	//press control + alt + s key
	public void pressControlAltS() {
		command = "xdotool key Control_L+Alt_L+s";
		runCommand();
		setCurrentKeyPressData("Control+Alt+S");
	}
	//press control + alt + d key
	public void pressControlAltD() {
		command = "xdotool key Control_L+Alt_L+d";
		runCommand();
		setCurrentKeyPressData("Control+Alt+D");
	}
	//press control + alt + f key
	public void pressControlAltF() {
		command = "xdotool key Control_L+Alt_L+f";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F");
	}
	//press control + alt + g key
	public void pressControlAltG() {
		command = "xdotool key Control_L+Alt_L+g";
		runCommand();
		setCurrentKeyPressData("Control+Alt+G");
	}
	//press control + alt + h key
	public void pressControlAltH() {
		command = "xdotool key Control_L+Alt_L+h";
		runCommand();
		setCurrentKeyPressData("Control+Alt+H");
	}
	//press control + alt + j key
	public void pressControlAltJ() {
		command = "xdotool key Control_L+Alt_L+j";
		runCommand();
		setCurrentKeyPressData("Control+Alt+J");
	}
	//press control + alt + k key
	public void pressControlAltK() {
		command = "xdotool key Control_L+Alt_L+k";
		runCommand();
		setCurrentKeyPressData("Control+Alt+K");
	}
	//press control + alt + l key
	public void pressControlAltL() {
		command = "xdotool key Control_L+Alt_L+l";
		runCommand();
		setCurrentKeyPressData("Control+Alt+L");
	}
	//press control + alt + ; key
	public void pressControlAltSemicolon() {
		command = "xdotool key Control_L+Alt_L+semicolon";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Semicolon");
	}
	//press control + alt + ' key
	public void pressControlAltLeftQuote() {
		command = "xdotool key Control_L+Alt_L+quoteleft";
		runCommand();
		setCurrentKeyPressData("Control+Alt+LeftQuote");
	}
	//press control + alt + enter key
	public void pressControlAltEnter() {
		command = "xdotool key Control_L+Alt_L+Return";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Enter");
	}
	//press control + alt + z key
	public void pressControlAltZ() {
		command = "xdotool key Control_L+Alt_L+z";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Z");
	}
	//press control + alt + x key
	public void pressControlAltX() {
		command = "xdotool key Control_L+Alt_L+x";
		runCommand();
		setCurrentKeyPressData("Control+Alt+X");
	}
	//press control + alt + c key
	public void pressControlAltC() {
		command = "xdotool key Control_L+Alt_L+c";
		runCommand();
		setCurrentKeyPressData("Control+Alt+C");
	}
	//press control + alt + v key
	public void pressControlAltV() {
		command = "xdotool key Control_L+Alt_L+v";
		runCommand();
		setCurrentKeyPressData("Control+Alt+V");
	}
	//press control + alt + b key
	public void pressControlAltB() {
		command = "xdotool key Control_L+Alt_L+b";
		runCommand();
		setCurrentKeyPressData("Control+Alt+B");
	}
	//press control + alt + n key
	public void pressControlAltN() {
		command = "xdotool key Control_L+Alt_L+n";
		runCommand();
		setCurrentKeyPressData("Control+Alt+N");
	}
	//press control + alt + m key
	public void pressControlAltM() {
		command = "xdotool key Control_L+Alt_L+m";
		runCommand();
		setCurrentKeyPressData("Control+Alt+M");
	}
	//press control + alt + , key
	public void pressControlAltComma() {
		command = "xdotool key Control_L+Alt_L+KP_Separator";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Comma");
	}
	//press control + alt + . key
	public void pressControlAltPeriod() {
		command = "xdotool key Control_L+Alt_L+period";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Period");
	}
	//press control + alt + / key
	public void pressControlAltSlash() {
		command = "xdotool key Control_L+Alt_L+slash";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Slash");
	}
	//press control + alt + up arrow key
	public void pressControlAltUpArrow() {
		command = "xdotool key Control_L+Alt_L+Up";
		runCommand();
		setCurrentKeyPressData("Control+Alt+UpArrow");
	}
	//press control + alt + left arrow key
	public void pressControlAltLeftArrow() {
		command = "xdotool key Control_L+Alt_L+Left";
		runCommand();
		setCurrentKeyPressData("Control+Alt+LeftArrow");
	}
	//press control + alt + down arrow key
	public void pressControlAltDownArrow() {
		command = "xdotool key Control_L+Alt_L+Down";
		runCommand();
		setCurrentKeyPressData("Control+Alt+DownArrow");
	}
	//press control + alt + right arrow key
	public void pressControlAltRightArrow() {
		command = "xdotool key Control_L+Alt_L+Right";
		runCommand();
		setCurrentKeyPressData("Control+Alt+RightArrow");
	}
	//press control + alt + space key
	public void pressControlAltSpace() {
		command = "xdotool key Control_L+Alt_L+KP_Space";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Space");
	}
	
	//press control + esc key
	public void pressControlEscape() {
		command = "xdotool key Control_L+Escape";
		runCommand();
		setCurrentKeyPressData("Control+Escape");
	}
	//press control + F1 key
	public void pressControlF1() {
		command = "xdotool key Control_L+F1";
		runCommand();
		setCurrentKeyPressData("Control+F1");
	}
	//press control + F2 key
	public void pressControlF2() {
		command = "xdotool key Control_L+F2";
		runCommand();
		setCurrentKeyPressData("Control+F2");
	}
	//press control + F3 key
	public void pressControlF3() {
		command = "xdotool key Control_L+F3";
		runCommand();
		setCurrentKeyPressData("Control+F3");
	}
	//press control + F4 key
	public void pressControlF4() {
		command = "xdotool key Control_L+F4";
		runCommand();
		setCurrentKeyPressData("Control+F4");
	}
	//press control + F5 key
	public void pressControlF5() {
		command = "xdotool key Control_L+F5";
		runCommand();
		setCurrentKeyPressData("Control+F5");
	}
	//press control + F6 key
	public void pressControlF6() {
		command = "xdotool key Control_L+F6";
		runCommand();
		setCurrentKeyPressData("Control+F6");
	}
	//press control + F7 key
	public void pressControlF7() {
		command = "xdotool key Control_L+F7";
		runCommand();
		setCurrentKeyPressData("Control+F7");
	}
	//press control + F8 key
	public void pressControlF8() {
		command = "xdotool key Control_L+F8";
		runCommand();
		setCurrentKeyPressData("Control+F8");
	}
	//press control + F9 key
	public void pressControlF9() {
		command = "xdotool key Control_L+F9";
		runCommand();
		setCurrentKeyPressData("Control+F9");
	}
	//press control + F10 key
	public void pressControlF10() {
		command = "xdotool key Control_L+F10";
		runCommand();
		setCurrentKeyPressData("Control+F10");
	}
	//press control + F11 key
	public void pressControlF11() {
		command = "xdotool key Control_L+F11";
		runCommand();
		setCurrentKeyPressData("Control+F11");
	}
	//press control + F12 key
	public void pressControlF12() {
		command = "xdotool key Control_L+F12";
		runCommand();
		setCurrentKeyPressData("Control+F12");
	}
	
	//press shift + esc key
	public void pressShiftEscape() {
		command = "xdotool key shift+Escape";
		runCommand();
		setCurrentKeyPressData("Shift+Escape");
	}
	//press shift + F1 key
	public void pressShiftF1() {
		command = "xdotool key shift+F1";
		runCommand();
		setCurrentKeyPressData("Shift+F1");
	}
	//press shift + F2 key
	public void pressShiftF2() {
		command = "xdotool key shift+F2";
		runCommand();
		setCurrentKeyPressData("Shift+F2");
	}
	//press shift + F3 key
	public void pressShiftF3() {
		command = "xdotool key shift+F3";
		runCommand();
		setCurrentKeyPressData("Shift+F3");
	}
	//press shift + F4 key
	public void pressShiftF4() {
		command = "xdotool key shift+F4";
		runCommand();
		setCurrentKeyPressData("Shift+F4");
	}
	//press shift + F5 key
	public void pressShiftF5() {
		command = "xdotool key shift+F5";
		runCommand();
		setCurrentKeyPressData("Shift+F5");
	}
	//press shift + F6 key
	public void pressShiftF6() {
		command = "xdotool key shift+F6";
		runCommand();
		setCurrentKeyPressData("Shift+F6");
	}
	//press shift + F7 key
	public void pressShiftF7() {
		command = "xdotool key shift+F7";
		runCommand();
		setCurrentKeyPressData("Shift+F7");
	}
	//press shift + F8 key
	public void pressShiftF8() {
		command = "xdotool key shift+F8";
		runCommand();
		setCurrentKeyPressData("Shift+F8");
	}
	//press shift + F9 key
	public void pressShiftF9() {
		command = "xdotool key shift+F9";
		runCommand();
		setCurrentKeyPressData("Shift+F9");
	}
	//press shift + F10 key
	public void pressShiftF10() {
		command = "xdotool key shift+F10";
		runCommand();
		setCurrentKeyPressData("Shift+F10");
	}
	//press shift + F11 key
	public void pressShiftF11() {
		command = "xdotool key shift+F11";
		runCommand();
		setCurrentKeyPressData("Shift+F11");
	}
	//press shift + F12 key
	public void pressShiftF12() {
		command = "xdotool key shift+F12";
		runCommand();
		setCurrentKeyPressData("Shift+F12");
	}
	
	//press alt + esc key
	public void pressAltEscape() {
		command = "xdotool key Alt_L+Escape";
		runCommand();
		setCurrentKeyPressData("Alt+Escape");
	}
	//press alt + F1 key
	public void pressAltF1() {
		command = "xdotool key Alt_L+F1";
		runCommand();
		setCurrentKeyPressData("Alt+F1");
	}
	//press alt + F2 key
	public void pressAltF2() {
		command = "xdotool key Alt_L+F2";
		runCommand();
		setCurrentKeyPressData("Alt+F2");
	}
	//press alt + F3 key
	public void pressAltF3() {
		command = "xdotool key Alt_L+F3";
		runCommand();
		setCurrentKeyPressData("Alt+F3");
	}
	//press alt + F4 key
	public void pressAltF4() {
		command = "xdotool key Alt_L+F4";
		runCommand();
		setCurrentKeyPressData("Alt+F4");
	}
	//press alt + F5 key
	public void pressAltF5() {
		command = "xdotool key Alt_L+F5";
		runCommand();
		setCurrentKeyPressData("Alt+F5");
	}
	//press alt + F6 key
	public void pressAltF6() {
		command = "xdotool key Alt_L+F6";
		runCommand();
		setCurrentKeyPressData("Alt+F6");
	}
	//press alt + F7 key
	public void pressAltF7() {
		command = "xdotool key Alt_L+F7";
		runCommand();
		setCurrentKeyPressData("Alt+F7");
	}
	//press alt + F8 key
	public void pressAltF8() {
		command = "xdotool key Alt_L+F8";
		runCommand();
		setCurrentKeyPressData("Alt+F8");
	}
	//press alt + F9 key
	public void pressAltF9() {
		command = "xdotool key Alt_L+F9";
		runCommand();
		setCurrentKeyPressData("Alt+F9");
	}
	//press alt + F10 key
	public void pressAltF10() {
		command = "xdotool key Alt_L+F10";
		runCommand();
		setCurrentKeyPressData("Alt+F10");
	}
	//press alt + F11 key
	public void pressAltF11() {
		command = "xdotool key Alt_L+F11";
		runCommand();
		setCurrentKeyPressData("Alt+F11");
	}
	//press alt + F12 key
	public void pressAltF12() {
		command = "xdotool key Alt_L+F12";
		runCommand();
		setCurrentKeyPressData("Alt+F12");
	}
	
	//press control + shift + esc key
	public void pressControlShiftEscape() {
		command = "xdotool key Control_L+shift+Escape";
		runCommand();
		setCurrentKeyPressData("Control+Shift+Escape");
	}
	//press control + shift + F1 key
	public void pressControlShiftF1() {
		command = "xdotool key Control_L+shift+F1";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F1");
	}
	//press control + shift + F2 key
	public void pressControlShiftF2() {
		command = "xdotool key Control_L+shift+F2";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F2");
	}
	//press control + shift + F3 key
	public void pressControlShiftF3() {
		command = "xdotool key Control_L+shift+F3";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F3");
	}
	//press control + shift + F4 key
	public void pressControlShiftF4() {
		command = "xdotool key Control_L+shift+F4";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F4");
	}
	//press control + shift + F5 key
	public void pressControlShiftF5() {
		command = "xdotool key Control_L+shift+F5";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F5");
	}
	//press control + shift + F6 key
	public void pressControlShiftF6() {
		command = "xdotool key Control_L+shift+F6";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F6");
	}
	//press control + shift + F7 key
	public void pressControlShiftF7() {
		command = "xdotool key Control_L+shift+F7";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F7");
	}
	//press control + shift + F8 key
	public void pressControlShiftF8() {
		command = "xdotool key Control_L+shift+F8";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F8");
	}
	//press control + shift + F9 key
	public void pressControlShiftF9() {
		command = "xdotool key Control_L+shift+F9";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F9");
	}
	//press control + shift + F10 key
	public void pressControlShiftF10() {
		command = "xdotool key Control_L+shift+F10";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F10");
	}
	//press control + shift + F11 key
	public void pressControlShiftF11() {
		command = "xdotool key Control_L+shift+F11";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F11");
	}
	//press control + shift + F12 key
	public void pressControlShiftF12() {
		command = "xdotool key Control_L+shift+F12";
		runCommand();
		setCurrentKeyPressData("Control+Shift+F12");
	}
	
	//press control + alt + esc key
	public void pressControlAltEscape() {
		command = "xdotool key Control_L+Alt_L+Escape";
		runCommand();
		setCurrentKeyPressData("Control+Alt+Escape");
	}
	//press control + alt + F1 key
	public void pressControlAltF1() {
		command = "xdotool key Control_L+Alt_L+F1";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F1");
	}
	//press control + alt + F2 key
	public void pressControlAltF2() {
		command = "xdotool key Control_L+Alt_L+F2";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F2");
	}
	//press control + alt + F3 key
	public void pressControlAltF3() {
		command = "xdotool key Control_L+Alt_L+F3";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F3");
	}
	//press control + alt + F4 key
	public void pressControlAltF4() {
		command = "xdotool key Control_L+Alt_L+F4";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F4");
	}
	//press control + alt + F5 key
	public void pressControlAltF5() {
		command = "xdotool key Control_L+Alt_L+F5";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F5");
	}
	//press control + alt + F6 key
	public void pressControlAltF6() {
		command = "xdotool key Control_L+Alt_L+F6";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F6");
	}
	//press control + alt + F7 key
	public void pressControlAltF7() {
		command = "xdotool key Control_L+Alt_L+F7";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F7");
	}
	//press control + alt + F8 key
	public void pressControlAltF8() {
		command = "xdotool key Control_L+Alt_L+F8";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F8");
	}
	//press control + alt + F9 key
	public void pressControlAltF9() {
		command = "xdotool key Control_L+Alt_L+F9";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F9");
	}
	//press control + alt + F10 key
	public void pressControlAltF10() {
		command = "xdotool key Control_L+Alt_L+F10";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F10");
	}
	//press control + alt + F11 key
	public void pressControlAltF11() {
		command = "xdotool key Control_L+Alt_L+F11";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F11");
	}
	//press control + alt + F12 key
	public void pressControlAltF12() {
		command = "xdotool key Control_L+Alt_L+F12";
		runCommand();
		setCurrentKeyPressData("Control+Alt+F12");
	}
}
