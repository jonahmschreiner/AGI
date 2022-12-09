package ActivitySolverServiceMethods;

import MainLLF.Constants;
import Structure.ExecutionPrepWrapper;
import Structure.LFInputWrapper;
import Structure.Sense;

public class UpdateSolutionStatus {
	public static LFInputWrapper exec(LFInputWrapper LFInput, ExecutionPrepWrapper epw) {
		Sense s = GetSenseAssociatedWithActivity.execute(LFInput.sw.env, LFInput.queues.activitiesToSolveQueue.get(0), LFInput.sw.myConnection);
		if (s != null || LFInput.queues.activitiesToSolveQueue.get(0) <= Constants.numOfCoreActions + Constants.numOfRawPropActivities) { //the sense to solve a prop for is currently present in the env
			LFInput = UpdateSolutionWhenTargetSensePresent.exec(LFInput, epw, s);
		} else {
			LFInput = UpdateSolutionWhenTargetSenseMissing.exec(LFInput);
		}
		return LFInput;
	}
}
