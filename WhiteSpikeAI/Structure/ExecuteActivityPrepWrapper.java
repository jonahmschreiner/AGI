package Structure;

import java.sql.ResultSet;
import java.util.List;

public class ExecuteActivityPrepWrapper {
	public Env prevEnv;
	public List<Sense> prevEnvSenses2;
	public ResultSet activityRS; //instead of just rs
	public int propId = -1;
	public int increaseOrDecreaseProp = -1;
	public int coreActivityToExecute = -1;
	public int associatedSense = -99999;
	public int conditionEnvDBId = -1;
	
	public ExecuteActivityPrepWrapper() {}
}
