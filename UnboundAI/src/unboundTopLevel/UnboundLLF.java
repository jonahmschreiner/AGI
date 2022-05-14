package unboundTopLevel;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import unboundBeliefHandling.*;
import unboundContextHandling.*;
import unboundStruct.*;
public class UnboundLLF {
	public Context context;
	public UnboundLLF() {}
	public static void main(String[] args) throws Exception{
		UnboundLLF LLF = new UnboundLLF();
		LLF.context = ContextParser.parse();
		while (true) {
			LLF.context = LLF.LF(LLF.context);
		}
	}
	
	public Context LF (Context contextIn) throws Exception {
		//Raw Env Info
		SenseEnv sense = new SenseEnv();
		Env currentEnv = sense.recordEnv();
		Context context = new Context(currentEnv, contextIn.satisfaction, "");
		//Senses
		context.env.abstractEnv = ExecSenses.exec(context, contextIn);
		//Action Decider
		

		String executeThisBelief = "TestBelief";
		//Action
		context.prevExecBeliefName = ExecAction.exec(executeThisBelief, contextIn.env, context);

		//Goals
		ExecGoals.exec(currentEnv, contextIn.env, context);
			
		//Error Goals
		context.satisfaction += ExecErrorGoals.exec(context.env, contextIn.env, context);
		System.out.println("Total Satisfaction: " + context.satisfaction);
		//Prep for next cycle
		ContextWriter.writeContext(context, true);
		return context;
	}
}
