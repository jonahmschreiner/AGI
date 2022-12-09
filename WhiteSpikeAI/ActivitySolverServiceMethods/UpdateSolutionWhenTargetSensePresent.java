package ActivitySolverServiceMethods;

import java.sql.Statement;

import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
import Structure.ExecutionPrepWrapper;
import Structure.LFInputWrapper;
import Structure.Sense;

public class UpdateSolutionWhenTargetSensePresent {
	public static LFInputWrapper exec(LFInputWrapper LFInput, ExecutionPrepWrapper epw, Sense s) {
		int currentActivityToSolveID = LFInput.queues.activitiesToSolveQueue.get(0);
		if (CheckIfActivityWasSolved.execute(s, currentActivityToSolveID, LFInput.sw.env, LFInput.sw.myConnection)) {
			//update the solved's db entry
			try {
				LFInput.sw.fw.append(" activity " + currentActivityToSolveID + " was solved\n");
				LFInput.sw.fw.flush();
				Statement myState = LFInput.sw.myConnection.createStatement();
				
				epw.conditionEnv = UploadConditionEnvToDB.exec(epw.conditionEnv, LFInput.sw.myConnection);
				
				String sqlCommand = "UPDATE Activity SET ConditionEnv=" + epw.conditionEnv.dbId + ", SubActivities=\"" + LFInput.queues.activitiesToTryQueue.get(0) + "\", SolvedStatus=1, numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
				myState.execute(sqlCommand);
			} catch (Exception e) {
				
			}
			LFInput.queues.activitiesToTryQueue.clear();
			LFInput.queues.actionQueue.clear();
			LFInput.queues.activitiesToSolveQueue.remove(0);
		}else {
			try {
				LFInput.sw.fw.append(" activity " + currentActivityToSolveID + " was not solved using activity or activity combo: " + LFInput.queues.activitiesToTryQueue.get(0) + "\n");
				LFInput.sw.fw.flush();
			} catch (Exception e) {
				
			}

			LFInput.queues.activitiesToTryQueue.remove(0);
			try {
				if (LFInput.queues.activitiesToTryQueue.size() == 0) {
					LFInput.queues.activitiesToSolveQueue.remove(0);
					//update numOfSolveAttempts in database
					try {
						Statement myState = LFInput.sw.myConnection.createStatement();
						String sqlCommand = "UPDATE Activity SET numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
						myState.execute(sqlCommand);
					} catch (Exception e) {
						LFInput.sw.fw.append(e.getMessage() + "\n");
						LFInput.sw.fw.flush();
					}		
				}
			} catch (Exception e) {
				try {
					LFInput.sw.fw.append(e.getMessage() + "\n");
					LFInput.sw.fw.flush();
				} catch (Exception f) {
					
				}
				
			}
		}
		return LFInput;
	}
}
