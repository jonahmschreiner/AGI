package LogsAndTestingUtils;

import Structure.ExecuteActivityInputWrapper;

public class WriteNarrowedDownConditionEnvLog {
	public static void exec(ExecuteActivityInputWrapper eaiw) {
		try {
			eaiw.fw.append(" EXECACT: narrowed-down condition env uploading to db finished\n");
			eaiw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
