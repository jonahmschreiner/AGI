package LogsAndTestingUtils;

import Structure.ExecuteActivityInputWrapper;

public class WriteSubActivityWorkedLog {
	public static void exec(ExecuteActivityInputWrapper eaiw) {
		try {
			eaiw.fw.append(" EXECACT: Sub-Activity Worked Correctly\n");
			eaiw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
