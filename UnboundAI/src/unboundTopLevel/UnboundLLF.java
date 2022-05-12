package unboundTopLevel;
import unboundBeliefHandling.*;
import unboundBeliefRetrievers.*;
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
		//Senses
		
		//Goals
		contextIn.satisfaction = ExecGoals.exec(currentEnv, contextIn.env, contextIn);
		
		contextIn.errors = ContextParser.parse().errors;
		//Actions (contextIn.prevExecBeliefName = ExecBestAction.exec();)
		
		
		//Prep for next cycle
		contextIn.env = currentEnv;
		System.out.println("Num of errors: " + contextIn.errors.size());
		ContextWriter.writeContext(contextIn, true);
		return contextIn;
	}
}
