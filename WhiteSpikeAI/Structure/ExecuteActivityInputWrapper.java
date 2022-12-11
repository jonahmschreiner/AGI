package Structure;

import java.io.FileWriter;
import java.sql.Connection;

public class ExecuteActivityInputWrapper {
	public Env envIn;
	public int activityId;
	public int currActivityId;
	public FileWriter fw;
	public Connection myConnection;
	public ExecuteActivityInputWrapper() {}
}
