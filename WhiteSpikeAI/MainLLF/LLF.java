package MainLLF;

import LogsAndTestingUtils.AllowEnvManipulationAtCertainIteration;
import LogsAndTestingUtils.WriteEndExecutionLog;
import LogsAndTestingUtils.WriteLLFCatchLog;
import Structure.LFInputWrapper;

public class LLF {
	public static void main(String[] args) {
		try {
			
			//for testing
			int testCount = 0;
			//
		
		
			LFInputWrapper LFInput = CreateInitialLFInputWrapper.exec();
			
			while (LFInput.continueLooping) {
				//for testing
				testCount++;
				AllowEnvManipulationAtCertainIteration.exec(testCount, LFInput);
				//
			
			
				LFInput = LF.exec(LFInput);
			}
		
			WriteEndExecutionLog.exec(LFInput);
		} catch (Exception e) {
			WriteLLFCatchLog.exec(e);
		}
	}
}
