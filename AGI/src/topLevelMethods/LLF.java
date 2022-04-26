package topLevelMethods;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Scanner;

import agiStruct.BeliefConnector;
import agiStruct.Env;
import coreMethods.SenseEnv;
import agiStruct.GoalResult;
//import java.io.*;
//import java.util.*;
//import java.lang.StringBuilder;
//import AGIUtil.Util;
//main class

public class LLF {
	public Env prevEnv;
	public LLF(){
		
	}
	//main function loops life function
	public static void main(String[] args) throws Exception{
		int satisfaction;
		String prevExecBelief = "";
		BeliefConnector nada = new BeliefConnector();
		String path = nada.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		LLF agi = new LLF();
		
		//form context variables from contextSource.txt
		try {		
			File contextSource = new File(path + "contextSource.txt");
			Scanner satScanner = new Scanner(contextSource);
			satScanner.useDelimiter(",");
			satisfaction = satScanner.nextInt();
			prevExecBelief = satScanner.next();
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
			int scrollUpPressed = satScanner.nextInt();
			int scrollDownPressed = satScanner.nextInt();
			btnsPressed[0] = leftMousePressed;
			btnsPressed[1] = middleMousePressed;
			btnsPressed[2] = rightMousePressed;
			
			//Form Prev Calendar
			LocalDateTime calendar = java.time.LocalDateTime.parse(satScanner.next());
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
			
			agi.prevEnv = recEnv;
			satScanner.close();
		} catch (Exception e) {
			satisfaction = 0;
			File contextSource = new File(path + "contextSource.txt");
			FileWriter writer = new FileWriter(contextSource);
			agi.prevEnv = new Env();
			agi.prevEnv.setCalendar(LocalDateTime.parse("2022-04-26T14:06:39.601"));
			File prevDisplayFile = new File(path + "CurrentDisplayVisuals.jpg");
			BufferedImage prevDisplay = javax.imageio.ImageIO.read(prevDisplayFile);
			agi.prevEnv.setCurrentDisplay(prevDisplay);
			agi.prevEnv.setKeyPressed("");
			int[] btnsPressed = new int[] {0,0,0,0,0};
			agi.prevEnv.setMouseButtonsPressed(btnsPressed[0], btnsPressed[1],btnsPressed[2]);
			agi.prevEnv.setMouseLocation(new Point(0,0));
			agi.prevEnv.setNumOfMouseButtons(8);
			writer.write("0,none,");
			writer.close();
		}
		while (true){
			File contextSource = new File(path + "contextSource.txt");
			Scanner satScanner = new Scanner(contextSource);
			satScanner.useDelimiter(",");
			satisfaction = satScanner.nextInt();
			prevExecBelief = satScanner.next();
			satScanner.close();		
			agi.prevEnv = agi.LF(satisfaction, prevExecBelief, agi.prevEnv);
			//break;
		}
	}
	//agi life force
	public Env LF(int satisfactionIn, String prevExecBeliefIn, Env prevEnvIn) throws Exception{ //Context = previous environment data
		
		//Setup
		int satisfaction = satisfactionIn;
		String prevExecBelief = prevExecBeliefIn;
		Env prevEnv = prevEnvIn;
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		File contextSource = new File(path + "contextSource.txt");
		FileWriter writer = new FileWriter(contextSource, false);
		BeliefConnector init = new BeliefConnector();
		LinkedList<String> tagsIn = new LinkedList<String>();
		
		//Pull in current sensing data
		SenseEnv sense = new SenseEnv();
		Env currentEnv = sense.recordEnv();

		
		//read in and evaluate goals, and package the evals into a GoalResult LinkedList
		//answers the question: were my goals accomplished ?
		tagsIn.add("Goal");
		LinkedList<BeliefConnector> goalBeliefs = init.retrieveBeliefs(tagsIn);
		LinkedList<GoalResult> goalResults = new LinkedList<GoalResult>();
		File goalResultFile = new File(path + "GoalEvalResult.java");
		FileWriter fileWriter = new FileWriter(goalResultFile, false);
		fileWriter.write("");
		Scanner fileScanner = new Scanner(goalResultFile);
		fileScanner.useDelimiter(":");
		for (int i = 0; i < goalBeliefs.size(); i++) {
			init = goalBeliefs.get(i);
			init.setEnvHardcodedVariables(currentEnv.getEnvVarsString());
			init.setPrevEnvHardcodedVariables(prevEnv.getPrevEnvVarsString());
			init.Fire("Goal");
			GoalResult goalResult = new GoalResult();
			String className = fileScanner.next();
			int result = fileScanner.nextInt();
			boolean complete = Boolean.valueOf(fileScanner.next());
			goalResult.setClassName(className);
			goalResult.setResult(result);
			goalResult.setComplete(complete);
			goalResults.add(goalResult);
			fileWriter.write("");
		}
		Runtime run2 = Runtime.getRuntime();
		String removeCommand = "rm " + path + "GoalEvalResult.java";
		run2.exec(removeCommand);
		fileScanner.close();
		fileWriter.close();
		//test
		System.out.println("Goal Result Class Name: " + goalResults.get(0).getClassName());
		System.out.println("Goal Result Complete: " + goalResults.get(0).getComplete());
		System.out.println("Goal Result Result: " + goalResults.get(0).getResult());
		//Copy data to appropriate places (prevbeliefs, optbeliefs, etc.)
		
		
		
		//output determined total satisfaction
		writer.write(String.valueOf(satisfaction));
		writer.close();
		
		
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
			
			//output the executed belief and currentEnv to context file for use in the next iteration
			FileWriter writer2 = new FileWriter(contextSource, true);
			writer2.write("," + init.getClassName() + "," + currentEnv.toString());
			writer2.close();
			return currentEnv;
		
	}
}
