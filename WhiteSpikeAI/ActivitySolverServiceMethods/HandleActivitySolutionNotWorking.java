package ActivitySolverServiceMethods;

import java.sql.Statement;

import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
import MainLLF.Constants;
import Structure.DBObjectHighestValueResults;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.HandleActivitySolutionWrapper;
import Structure.Sense;

public class HandleActivitySolutionNotWorking {
	public static HandleActivitySolutionWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteCurrentActivityWrapper ecaw, ExecuteActivityPrepWrapper eapw, Sense s) {
		//create new activity that does the same sense prop change but with the current Env as the condition env
		try {
			eaiw.fw.append(" EXECACT: Sub-Activity Did Not Work Correctly\n");
			eaiw.fw.flush();
			UploadConditionEnvToDB.exec(ecaw.currPrevEnv, eaiw.myConnection);
			eaiw.fw.append(" EXECACT: Condition Env Uploading To DB Finished\n");
			eaiw.fw.flush();
			DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(eaiw.myConnection);
			eaiw.fw.append("Condition Env Id of New Activity: " + dbocr.conditionEnvCount + ". New Activity Id: " + (dbocr.activityCount + 1));
			eaiw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(eaiw.myConnection);
		String sqlCommand = "";
		if (eapw.associatedSense < 0 && eapw.associatedSense > (-1 * Constants.numOfRawProps) - 3) {
			sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + eapw.associatedSense + ", " + eapw.propId + ", " + eapw.increaseOrDecreaseProp + ", " + eapw.coreActivityToExecute + ");";
		} else {
			sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + s.dbId + ", " + eapw.propId + ", " + eapw.increaseOrDecreaseProp + ", " + eapw.coreActivityToExecute + ");";
		}
		try {
			Statement myState = eaiw.myConnection.createStatement();
			myState.execute(sqlCommand);
		} catch (Exception f) {
			f.printStackTrace();
		}
		HandleActivitySolutionWrapper hasw = new HandleActivitySolutionWrapper();
		hasw.eaiw = eaiw;
		hasw.eapw = eapw;
		hasw.ecaw = ecaw;
		return hasw;
	}
}
