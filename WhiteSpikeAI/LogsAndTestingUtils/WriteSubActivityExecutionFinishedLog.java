package LogsAndTestingUtils;

import Structure.ExecuteActivityInputWrapper;

public class WriteSubActivityExecutionFinishedLog {
	public static void exec(ExecuteActivityInputWrapper eaiw) {
		try {
			eaiw.fw.append(" EXECACT: Sub-Activity Execution Finished. Evaluating and Closing Down Condition Env of Top-Level Action\n");
			eaiw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
