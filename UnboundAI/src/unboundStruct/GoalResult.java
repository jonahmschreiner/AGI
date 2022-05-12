package unboundStruct;

public class GoalResult {
	public String className;	
	public int result; //The satisfaction output of this goal evaluation
	public boolean complete;
	public GoalResult() {}
	public GoalResult(String classNameIn, int resultIn, boolean completeIn) {
		this.className = classNameIn;
		this.result = resultIn;
		this.complete = completeIn;
	}
		
	public void setResult(int resultIn) {
		this.result = resultIn;
	}
	
	public void setClassName(String nameIn) {
		this.className = nameIn;
	}
	
	public void setComplete(boolean completeIn) {
		this.complete = completeIn;
	}
	
	@Override
	public String toString() {
		return className + ":" + result + ":" + complete;
	}
}
