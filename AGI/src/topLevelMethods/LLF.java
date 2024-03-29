package topLevelMethods;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import agiStruct.BeliefConnector;
import agiStruct.Env;
import coreMethods.SenseEnv;
import agiStruct.GoalResult;
import agiStruct.Instruction;
//import java.io.*;
//import java.util.*;
//import java.lang.StringBuilder;
import AGIUtil.Util;
//main class

public class LLF {
	public Env prevEnv;
	public LLF(){
		
	}
	//main function loops life function
	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception{
		int satisfaction;
		String prevExecBelief = "";
		BeliefConnector nada = new BeliefConnector();
		String path = nada.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		LLF agi = new LLF();
		
		//form context/prevEnv from contextSource.txt
		try {		
			File contextSource = new File(path + "contextSource.txt");
			Scanner satScanner = new Scanner(contextSource);
			satScanner.useDelimiter(",");
			satisfaction = satScanner.nextInt();
			prevExecBelief = satScanner.next();
			if (agi.prevEnv == null) {
				//Form Mouse Location
				int mlX = satScanner.nextInt();
				int mlY = satScanner.nextInt();
				Point mlPoint = new Point(mlX, mlY);
			
				//Key Pressed
				String prevKeyPress = satScanner.next();
			
				//Num of Mouse Buttons
				int mbNum = satScanner.nextInt();
			
				//Form Mouse Buttons Pressed
				int [] btnsPressed = new int[5];
				int leftMousePressed = satScanner.nextInt();
				int middleMousePressed = satScanner.nextInt();
				int rightMousePressed = satScanner.nextInt();
				btnsPressed[0] = leftMousePressed;
				btnsPressed[1] = middleMousePressed;
				btnsPressed[2] = rightMousePressed;
			
				//Form Prev Calendar
				LocalDateTime calendar = java.time.LocalDateTime.parse(satScanner.next());
				
				//Form Prev CPU Usage
				Double cpuUsage = Double.valueOf(satScanner.next());
				
				//Form Error Locations
				List<Instruction> errors = null;
				while (satScanner.hasNext()) {
					String instructionObj = satScanner.next();
					if (instructionObj.equals("ENDERRORS")) {
						break;
					}
					Scanner instScan = new Scanner(instructionObj);
					instScan.useDelimiter(":");
					String parentClass = instScan.next();
					int instNum = Integer.valueOf(instScan.next());
					String instructionCode = instScan.next();
					Instruction inst = new Instruction(parentClass, instNum, instructionCode, null);
					errors.add(inst);
					instScan.close();
				}
				
				//Form Prev Display
				File prevDisplayFile = new File(path + "CurrentDisplayVisuals.jpg");
				BufferedImage prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
			
				//Assemble Prev Env
				Env recEnv = new Env();
				recEnv.setCalendar(calendar);
				recEnv.setCurrentDisplay(prevDisplay);
				recEnv.setKeyPressed(prevKeyPress);
				recEnv.setMouseButtonsPressed(btnsPressed[0], btnsPressed[1],btnsPressed[2]);
				recEnv.setMouseLocation(mlPoint);
				recEnv.setNumOfMouseButtons(mbNum);
				recEnv.setCurrentCpuUsage(cpuUsage);
				recEnv.setErrorLocations(errors);
				agi.prevEnv = recEnv;
			}
			satScanner.close();
		} catch (Exception e) {
			try {
				File contextSource = new File(path + "contextBackupSource.txt");
				Scanner satScanner = new Scanner(contextSource);
				satScanner.useDelimiter(",");
				satisfaction = satScanner.nextInt();
				prevExecBelief = satScanner.next();
				if (agi.prevEnv == null) {
					//Form Mouse Location
					int mlX = satScanner.nextInt();
					int mlY = satScanner.nextInt();
					Point mlPoint = new Point(mlX, mlY);
				
					//Key Pressed
					String prevKeyPress = satScanner.next();
				
					//Num of Mouse Buttons
					int mbNum = satScanner.nextInt();
				
					//Form Mouse Buttons Pressed
					int [] btnsPressed = new int[5];
					int leftMousePressed = satScanner.nextInt();
					int middleMousePressed = satScanner.nextInt();
					int rightMousePressed = satScanner.nextInt();
					btnsPressed[0] = leftMousePressed;
					btnsPressed[1] = middleMousePressed;
					btnsPressed[2] = rightMousePressed;
				
					//Form Prev Calendar
					LocalDateTime calendar = java.time.LocalDateTime.parse(satScanner.next());
					
					//Form Prev Cpu Usage
					Double cpuUsage = Double.valueOf(satScanner.next());
					
					//Form Error Locations
					List<Instruction> errors = new ArrayList<Instruction>();
					while (satScanner.hasNext()) {
						String instructionObj = satScanner.next();
						if (instructionObj.equals("ENDERRORS")) {
							break;
						}
						Scanner instScan = new Scanner(instructionObj);
						instScan.useDelimiter(":");
						String parentClass = instScan.next();
						int instNum = Integer.valueOf(instScan.next());
						String instructionCode = instScan.next();
						
						//
						System.out.println("Current Error: " + parentClass + "," + instNum
								+ "," + instructionCode);
						Instruction inst = new Instruction(parentClass, instNum, instructionCode, "");
						errors.add(inst);
						instScan.close();
					}

					//Form Prev Display
					BufferedImage prevDisplay;
					try{
						File prevDisplayFile = new File(path + "CurrentDisplayVisuals.jpg");
						prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
					} catch (Exception whatever) {
						File prevDisplayFile = new File(path + "PrevDisplayVisuals.jpg");
						prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
					}
					
				
					//Assemble Prev Env
					Env recEnv = new Env();
					recEnv.setCalendar(calendar);
					recEnv.setCurrentDisplay(prevDisplay);
					recEnv.setKeyPressed(prevKeyPress);
					recEnv.setMouseButtonsPressed(btnsPressed[0], btnsPressed[1],btnsPressed[2]);
					recEnv.setMouseLocation(mlPoint);
					recEnv.setNumOfMouseButtons(mbNum);
					recEnv.setCurrentCpuUsage(cpuUsage);
					recEnv.setErrorLocations(errors);
					agi.prevEnv = recEnv;
				}
				satScanner.close();
			} catch (Exception f) {
				System.out.println("had to overwrite contextSource");
				f.printStackTrace();
				satisfaction = 0;
				File contextSource = new File(path + "contextSource.txt");
				FileWriter writer = new FileWriter(contextSource);
				agi.prevEnv = new Env();
				agi.prevEnv.setCalendar(LocalDateTime.parse("2022-04-26T14:06:39.601"));
				BufferedImage prevDisplay;
				try {
					File prevDisplayFile = new File(path + "CurrentDisplayVisuals.jpg");
					prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
				} catch (Exception g) {
					try {
					File prevDisplayFile = new File(path + "PrevDisplayVisuals.jpg");
					prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
					} catch (Exception h) {
						File prevDisplayFile = new File(path + "ErrorImage.png");
						prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
					}
				}
				agi.prevEnv.setCurrentDisplay(prevDisplay);
				agi.prevEnv.setKeyPressed("");
				int[] btnsPressed = new int[] {0,0,0,0,0};
				agi.prevEnv.setMouseButtonsPressed(btnsPressed[0], btnsPressed[1],btnsPressed[2]);
				agi.prevEnv.setMouseLocation(new Point(0,0));
				agi.prevEnv.setNumOfMouseButtons(8);
				agi.prevEnv.setCurrentCpuUsage(16.25);
				writer.write("0,none,0,0,,8,0,0,0,2022-04-27T15:13:28.516,16.25,");			
				writer.close();
			}
		}
		Util util = new Util();
		while (true){
			File contextSource;
			try {
				contextSource = new File(path + "contextSource.txt");
				Scanner satScanner = new Scanner(contextSource);
				satScanner.useDelimiter(",");
				satisfaction = satScanner.nextInt();
				prevExecBelief = satScanner.next();
				satScanner.close();	
			} catch (Exception e) {
				contextSource = new File(path + "contextBackupSource.txt");
				Scanner satScanner = new Scanner(contextSource);
				satScanner.useDelimiter(",");
				satisfaction = satScanner.nextInt();
				prevExecBelief = satScanner.next();
				satScanner.close();	
			}
			agi.prevEnv = agi.LF(satisfaction, prevExecBelief, agi.prevEnv);
			File contextBackupSource = new File(path + "contextBackupSource.txt");
			util.copyContent(contextSource, contextBackupSource);
			//break;
		}
	}
	//agi life force
	public Env LF(int satisfactionIn, String prevExecBeliefIn, Env prevEnvIn) throws Exception{ //Context = previous environment data
		
		//Setup
		int satisfaction = satisfactionIn;
		@SuppressWarnings("unused")
		String prevExecBelief = prevExecBeliefIn;
		Env prevEnv = prevEnvIn;
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		File contextSource = new File(path + "contextSource.txt");
		BeliefConnector init = new BeliefConnector();
		LinkedList<String> tagsIn = new LinkedList<String>();
		
		//Pull in current sensing data
		SenseEnv sense = new SenseEnv();
		Env currentEnv = sense.recordEnv();
		//System.out.println("prevEnvStr: " + prevEnv.getPrevEnvVarsString());
		
		//read in and evaluate goals, and package the evals into a GoalResult LinkedList
		//answers the question: were my goals accomplished ?
		tagsIn.add("Goal");
		LinkedList<BeliefConnector> goalBeliefs = init.retrieveBeliefs(tagsIn);
		LinkedList<GoalResult> goalResults = new LinkedList<GoalResult>();
		File goalResultFile = new File(path + "GoalEvalResult.java");
		FileWriter fileWriter = new FileWriter(goalResultFile, false);
		fileWriter.write("");
		fileWriter.close();
		Scanner fileScanner = new Scanner(goalResultFile);
		fileScanner.useDelimiter(":");
		for (int i = 0; i < goalBeliefs.size(); i++) {
			init = goalBeliefs.get(i);
			init.setEnvHardcodedVariables(currentEnv.getEnvVarsString());
			init.setPrevEnvHardcodedVariables(prevEnv.getPrevEnvVarsString());
			init.Fire("Goal");
			GoalResult goalResult = new GoalResult();
			Scanner fileScannerIn = new Scanner(goalResultFile);
			fileScannerIn.useDelimiter(":");
			String className = fileScannerIn.next();
			int result;
			try{
				result = fileScannerIn.nextInt();
			} catch (Exception e) {
				String negativeResult = fileScannerIn.next();
				result = Integer.valueOf(negativeResult);
			}
			boolean complete = Boolean.valueOf(fileScannerIn.next());
			goalResult.setClassName(className);
			goalResult.setResult(result);
			goalResult.setComplete(complete);
			goalResults.add(goalResult);
			fileScannerIn.close();
			FileWriter fileWriter2 = new FileWriter(goalResultFile, false);
			fileWriter2.write("");
			fileWriter2.close();
		}
		Runtime run2 = Runtime.getRuntime();
		String removeCommand = "rm " + path + "GoalEvalResult.java";
		run2.exec(removeCommand);
		fileScanner.close();
		fileWriter.close();
		
		//test
//		for (int i = 0; i < goalResults.size(); i++) {
//			System.out.println("Goal Result Class Name: " + goalResults.get(i).getClassName());
//			System.out.println("Goal Result Complete: " + goalResults.get(i).getComplete());
//			System.out.println("Goal Result Result: " + goalResults.get(i).getResult());
//		}



		
		//calculate and output determined total satisfaction
		//System.out.println("Sat before iteration: " + satisfaction);
		for (int i = 0; i < goalResults.size(); i++) {
			satisfaction = satisfaction + goalResults.get(i).getResult();
		}	
		System.out.println("Total Satisfaction: " + satisfaction);
		FileWriter writer = new FileWriter(contextSource, false);
		writer.write(String.valueOf(satisfaction));
		writer.close();
		//Copy data to appropriate places (prevbeliefs, optbeliefs, etc.)
		
		//Call Modify Behavior Block
		
		
		//Call Execution Block
		
			//Sense Beliefs
			tagsIn.clear();
			tagsIn.add("Sense");
			LinkedList<BeliefConnector> senseBeliefs = init.retrieveBeliefs(tagsIn);
			for (int i = 0; i < senseBeliefs.size(); i++) {
				init = senseBeliefs.get(i);
				init.setEnvHardcodedVariables(currentEnv.getEnvVarsString());
				init.setPrevEnvHardcodedVariables(prevEnv.getPrevEnvVarsString());
				init.Fire("Sense");
			}
			tagsIn.clear();
			
			//Action Beliefs
			File source = new File("/home/agi/Desktop/eclipse/AGI/bin/tagsCalled.java");
			Scanner reader = new Scanner(source);
			reader.useDelimiter(",");
			while(reader.hasNext()) {
				String currentTag = reader.next();
				if (tagsIn.contains(currentTag)) {
				
				} else {
					tagsIn.add(currentTag);
				}
			}
			LinkedList<BeliefConnector> beliefsToRun = init.retrieveBeliefs(tagsIn);
			init.updateNeuralToHighestConf(beliefsToRun, tagsIn);
			init.Fire("Behavior");
			reader.close();
			
			//clean up previous iteration recursiveTags
			Runtime run = Runtime.getRuntime();
			String command = "rm " + path + "tagsCalled.java";
			run.exec(command);
			
			
			//read in current errors
			File errorFile = new File(path + "errorContext.txt");
			Scanner errorFileScanner = new Scanner(errorFile);
			String errorInput = "";
			while (errorFileScanner.hasNext()) {
				errorInput = errorInput + errorFileScanner.nextLine();
			}
			
			
			//output the executed belief, currentEnv and errors to context file for use in the next iteration
			FileWriter writer2 = new FileWriter(contextSource, true);
			writer2.write("," + init.getClassName() + "," + currentEnv.toString());
			writer2.write(errorInput);
			writer2.write("ENDERRORS");
			writer2.close();
			

			
			errorFileScanner.close();
			
			//write currentDisplay to prevDisplay for use in next iteration
			File prevDisplayOut = new File("/home/agi/Desktop/eclipse/AGI/bin/PrevDisplayVisuals.jpg");
			try {
				ImageIO.write(currentEnv.getCurrentDisplay(), "jpg", prevDisplayOut);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return currentEnv;
		
	}
}
