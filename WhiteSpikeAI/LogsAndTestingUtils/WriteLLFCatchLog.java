package LogsAndTestingUtils;

import MainLLF.CreateInitialLFInputWrapper;
import Structure.LFInputWrapper;

public class WriteLLFCatchLog {
	public static void exec(Exception e) {
		e.printStackTrace();
		LFInputWrapper LFInput = CreateInitialLFInputWrapper.exec();
		StackTraceElement[] ste = e.getStackTrace();
		try {
			for (int i = 0; i < ste.length; i++) {
				LFInput.sw.fw.append("Stack: " + ste[i].toString() + "\n");
				LFInput.sw.fw.flush();
			}
			LFInput.sw.fw.append("Stack: " + e.getMessage() + "\n");
			LFInput.sw.fw.flush();
		} catch (Exception f) {
			f.printStackTrace();
		}
	}
}
