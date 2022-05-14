package unboundStruct;
public class Context {
	public Env env;
	public int satisfaction;
	public String prevExecBeliefName;
	
	public Context() {}
	public Context(Env envIn, int satisfactionIn, String prevExecBeliefNameIn) {
		this.env = envIn;
		this.satisfaction = satisfactionIn;
		this.prevExecBeliefName = prevExecBeliefNameIn;
	}
}
