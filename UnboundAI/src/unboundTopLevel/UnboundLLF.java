package unboundTopLevel;

import java.util.List;

import unboundBeliefHandling.*;
import unboundContextHandling.*;
import unboundStruct.*;
public class UnboundLLF {
	public Context context;
	public UnboundLLF() {}
	public static void main(String[] args) throws Exception{
		UnboundLLF LLF = new UnboundLLF();
		LLF.context = ContextParser.parse();
		if (LLF.context.hypothesi.isEmpty()) {
			LLF.context.hypothesi = InitializeNewHypothesi.init();
		}
		while (true) {
			LLF.context = LLF.LF(LLF.context);
		}
	}
	
	public Context LF (Context contextIn) throws Exception {
		//Raw Env Info
		SenseEnv sense = new SenseEnv();
		Env currentEnv = sense.recordEnv();
		Context context = new Context(currentEnv, contextIn.satisfaction, "", contextIn.hypothesi);
		//Senses
		context.env.abstractEnv = ExecSenses.exec(context, contextIn);
		//Action Decider
		String executeThisBelief = ExecDecider.exec(context);
		//Action
		context.prevExecBeliefName = ExecAction.exec(executeThisBelief, contextIn.env, context);
		//Goals
		ExecGoals.exec(currentEnv, contextIn.env, context);	
		//Error Goals
		List<GoalResult> results = ExecErrorGoals.exec(context.env, contextIn.env, context);
		//Handle Goal Results (take individual ratings of each goal and add them to the total as well as append them to hypothesis
		context.satisfaction += GoalResultCombiner.combine(results);
		Hypothesis executionAssociatedHypo = new Hypothesis();
		executionAssociatedHypo.actionName = executeThisBelief;
		context.hypothesi.get(context.hypothesi.indexOf(executionAssociatedHypo)).updateRatings(HandleGoalResults.handle(results));
		System.out.println("Total Satisfaction: " + context.satisfaction);
		//Rewrite Block
		
		
		
		//Prep for next cycle
		ContextWriter.writeContext(context, true);
		return context;
	}
}
