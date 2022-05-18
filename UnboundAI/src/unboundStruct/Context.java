package unboundStruct;

import java.util.*;

public class Context {
	public Env env;
	public int satisfaction;
	public String prevExecBeliefName;
	public List<Hypothesis> hypothesi;
	
	public Context() {
		this.env = new Env();
		this.hypothesi = new ArrayList<Hypothesis>();
	}
	public Context(Env envIn, int satisfactionIn, String prevExecBeliefNameIn, List<Hypothesis> hypothesiIn) {
		this.env = envIn;
		this.satisfaction = satisfactionIn;
		this.prevExecBeliefName = prevExecBeliefNameIn;
		this.hypothesi = hypothesiIn;
	}
}
