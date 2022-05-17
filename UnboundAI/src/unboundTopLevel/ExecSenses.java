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
		for (int i = 0; i < numOfThreads; i++) {
			final int count = i; //was i
			Callable<List<Sense>> callableTask = () -> {
			    List<Sense> senses = new ArrayList<Sense>();
			    List<String> currSensePaths = new ArrayList<String>();
			    if (sensePaths.size() > 1) {
			    	currSensePaths = sensePaths.subList(threadSize * count, (threadSize * (count + 1)) - 1);
			    } else {
			    	currSensePaths = sensePaths;
			    }
			    List<Belief> currBeliefs = BeliefParser.parseBeliefs(currSensePaths, 1);
			    Runtime run = Runtime.getRuntime();
			    String command = "touch /home/agi/Desktop/eclipse/UnboundAI/bin/sense" + count;
			    run.exec(command);
			    for (int j = 0; j < currBeliefs.size(); j++) {
			    	String source = BeliefFileAssembler.assembleSense(currBeliefs.get(count), context.env, prevContext.env, count);
			    	FileCompilerRunner.compile(source, currBeliefs.get(count).beliefName, context);
			    }
			    senses = SenseParser.parse("/home/agi/Desktop/eclipse/UnboundAI/bin/sense" + count);
			    command = "rm /home/agi/Desktop/eclipse/UnboundAI/bin/senses" + count;
			    run.exec(command);    
			    return senses;
			};
			processes.add(callableTask);
		}
		//Execute callables and combine lists into abstractEnv
		List<Future<List<Sense>>> stuff = exec.invokeAll(processes);
		while (stuff.size() < numOfThreads) {}
		for (int i = 0; i < stuff.size(); i++) {
			List<Sense> senseInd = new ArrayList<Sense>();
			while (senseInd.size() == 0) {
				try {
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
