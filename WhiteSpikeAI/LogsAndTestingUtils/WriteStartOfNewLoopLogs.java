package LogsAndTestingUtils;

import Structure.StartupWrapper;

public class WriteStartOfNewLoopLogs {
	public static void exec(StartupWrapper sw) {
		try {
			System.out.println("new loop");
			sw.fw.append("\n\n.....................\n\n");
			sw.fw.append(" start of new life loop iteration\n");
			sw.fw.flush();
		} catch (Exception e) {
			
		}
	}
}
