package unboundStruct;
import java.util.*;
public class Context {
	public Env env;
	public int satisfaction;
	public String prevExecBeliefName;
	public List<Instruction> errors;
	
	public Context() {}
	public Context(Env envIn, int satisfactionIn, String prevExecBeliefNameIn, List<Instruction> errorsIn) {
		this.env = envIn;
		this.satisfaction = satisfactionIn;
		this.prevExecBeliefName = prevExecBeliefNameIn;
		this.errors = errorsIn;
	}
}
