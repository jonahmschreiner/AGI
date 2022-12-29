package LogsAndTestingUtils;

import Structure.ExecuteActivityInputWrapper;

public class WriteSubActivityIsNotACoreAction {
	public static void exec(ExecuteActivityInputWrapper eaiw) {
		try {
			eaiw.fw.append(" EXECACT: Executed Sub-Activity is not a core action\n");
			eaiw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
