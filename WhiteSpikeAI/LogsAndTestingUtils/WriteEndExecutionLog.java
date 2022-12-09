package LogsAndTestingUtils;

import Structure.LFInputWrapper;

public class WriteEndExecutionLog {
	public static void exec(LFInputWrapper LFInput) {
		System.out.println("end execution");
		try {
			LFInput.sw.fw.append("end\n");
			LFInput.sw.fw.flush();
		} catch (Exception e) {
			
		}
	}
}
