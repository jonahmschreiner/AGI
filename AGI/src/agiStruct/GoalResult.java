package agiStruct;

public class GoalResult {
	String className;
	int result;
	boolean complete;
	public GoalResult() {
		
	}
	public GoalResult(String classNameIn, int resultIn, boolean completeIn) {
		this.className = classNameIn;
		this.result = resultIn;
		this.complete = completeIn;
	}
	
	public int getResult() {
		return this.result;
	}
	
	public String getClassName() {
		return this.className;
	}
	
	public boolean getComplete() {
		return this.complete;
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
