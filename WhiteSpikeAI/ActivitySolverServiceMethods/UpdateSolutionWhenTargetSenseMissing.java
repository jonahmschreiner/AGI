package ActivitySolverServiceMethods;

import java.sql.Statement;

import Structure.LFInputWrapper;

public class UpdateSolutionWhenTargetSenseMissing {
	public static LFInputWrapper exec(LFInputWrapper LFInput) {
		int currentActivityToSolveID = LFInput.queues.activitiesToSolveQueue.get(0);
		try {
			LFInput.sw.fw.append(" activity " + currentActivityToSolveID + " was not solved using activity or activity combo: " + LFInput.queues.activitiesToTryQueue.get(0) + " because the associated sense was not present in the env\n");
			LFInput.sw.fw.flush();
			Statement myState = LFInput.sw.myConnection.createStatement();
			String sqlCommand = "UPDATE Activity SET numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			try {
				LFInput.sw.fw.append(e.getMessage() + "\n");
				LFInput.sw.fw.flush();
			} catch (Exception f) {
				
			}
		}		
		LFInput.queues.activitiesToTryQueue.clear();
		LFInput.queues.actionQueue.clear();
		LFInput.queues.activitiesToSolveQueue.remove(0);
		return LFInput;
	}
}
