package unboundTopLevel;

import java.util.*;
import java.util.concurrent.*;

import unboundBeliefHandling.BeliefFileAssembler;
import unboundBeliefHandling.BeliefParser;
import unboundBeliefHandling.FileCompilerRunner;
import unboundBeliefHandling.SenseParser;
import unboundBeliefRetrievers.SenseRetriever;
import unboundStruct.*;
public class ExecSenses {
	@SuppressWarnings("unchecked")
	public static List<Sense> exec(Context context, Context prevContext) throws Exception {
		List<Sense> abstractEnv = new ArrayList<Sense>();
		List<String> tagsIn = new ArrayList<String>();
		tagsIn.add("Sense");
		List<String> sensePaths = SenseRetriever.retrieve(tagsIn);
		//determine number of threads
		int numOfThreads = 1;
		if (sensePaths.size() > Runtime.getRuntime().availableProcessors() - 1) {
			numOfThreads = Runtime.getRuntime().availableProcessors();
		}
		//create callables based on number of threads needed
		int threadSize = sensePaths.size() / numOfThreads;
		ExecutorService exec = Executors.newFixedThreadPool(numOfThreads);
		List<Callable<List<Sense>>> processes = new ArrayList<Callable<List<Sense>>>();
		//for (int i = 0; i < numOfThreads; i++) {
			final int count = 0; //was i
			Callable<List<Sense>> callableTask = () -> {
			    List<Sense> senses = new ArrayList<Sense>();
			    System.out.println(1);
			    List<String> currSensePaths = sensePaths.subList(threadSize * count, (threadSize * (count + 1)) - 1);
			    System.out.println(2);
			    List<Belief> currBeliefs = BeliefParser.parseBeliefs(currSensePaths, 1);
			    System.out.println(3);
			    Runtime run = Runtime.getRuntime();
			    String command = "touch /home/agi/Desktop/eclipse/UnboundAI/bin/senses" + count;
			    run.exec(command);
			    System.out.println(4);
			    System.out.println("curr: " + currBeliefs.size());
			    for (int j = 0; j < currBeliefs.size(); j++) {
			    	System.out.println(5);
			    	String source = BeliefFileAssembler.assembleSense(currBeliefs.get(count), context.env, prevContext.env);
			    	System.out.println(5);
			    	FileCompilerRunner.compile(source, currBeliefs.get(count).beliefName, context);
					System.out.println(5);
			    }
			    senses = SenseParser.parse("/home/agi/Desktop/eclipse/UnboundAI/bin/senses" + count);
			    System.out.println(6);
			    command = "rm /home/agi/Desktop/eclipse/UnboundAI/bin/senses" + count;
			    run.exec(command);
			    //abstractEnv.addAll(senses);	    
			    return senses;
			};
			processes.add(callableTask);
	//	}
		//Execute callables and combine lists into abstractEnv
		List<Future<List<Sense>>> stuff = exec.invokeAll(processes);
		while (stuff.size() < numOfThreads) {}
		for (int i = 0; i < stuff.size(); i++) {
			List<Sense> senseInd = new ArrayList<Sense>();
			while (senseInd.size() == 0) {
				try {
					System.out.println("I: " + i);
					System.out.println("Stuff GetI: " + stuff.get(0));
					System.out.println("Stuff GetIget: " + stuff.get(0).get());
					senseInd = stuff.get(i).get();
					abstractEnv.addAll(senseInd);
				} catch (Exception e) {
				}
			}		
		}
		exec.shutdown();
		return abstractEnv;
	}
}
