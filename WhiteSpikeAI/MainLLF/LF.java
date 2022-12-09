package MainLLF;
import ActivitySolverServiceMethods.ExecuteActionQueueAndUpdateEnv;
import ActivitySolverServiceMethods.PrepForExecution;
import ActivitySolverServiceMethods.SetUpQueues;
import ActivitySolverServiceMethods.UpdateSolutionStatus;
import LogsAndTestingUtils.WriteStartOfNewLoopLogs;
import Structure.ExecutionPrepWrapper;
import Structure.LFInputWrapper;


public class LF {
	public static LFInputWrapper exec (LFInputWrapper LFInput) {
		WriteStartOfNewLoopLogs.exec(LFInput.sw);
		
		LFInput.queues = SetUpQueues.exec(LFInput.queues, LFInput.sw);
		
		ExecutionPrepWrapper epw = PrepForExecution.exec(LFInput);
		
		LFInput = ExecuteActionQueueAndUpdateEnv.exec(LFInput, epw);
		
		LFInput = UpdateSolutionStatus.exec(LFInput, epw);
	
		LFInput = UpdateLoopingStatus.exec(LFInput);
		
		return LFInput;
	}
}
