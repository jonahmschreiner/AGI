package ActivitySolverServiceMethods;

import java.util.HashMap;

import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
import Structure.AbstractEnvClosingPrepWrapper;
import Structure.AbstractEnvClosingWrapper;
import Structure.Env;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.PixelColorRange;
import Structure.Sense;

public class AbstractEnvClosing {
	public static AbstractEnvClosingWrapper exec(Env newConditionEnv, ExecuteCurrentActivityWrapper ecaw, ExecuteActivityPrepWrapper eapw, AbstractEnvClosingPrepWrapper aecpw, ExecuteActivityInputWrapper eaiw) {
		HashMap<Integer, Integer> map = GetSenseAssociatedWithActivity.createIdToIndexMap(eapw.prevEnv.abstractEnv.senses);
		for (int j = 0; j < ecaw.currPrevEnv.abstractEnv.recentlyChangedOldSenses.size(); j++) {
			int currIndex = ecaw.currPrevEnv.abstractEnv.recentlyChangedOldSenses.get(j);
			Sense currSense = eapw.prevEnv.abstractEnv.senses.get(map.get(currIndex));
			Sense currPrevEnvSense = aecpw.currPrevEnvSensesList.get(map.get(currIndex));
			PixelColorRange currConSensepcr = new PixelColorRange(currPrevEnvSense.orientation.color);
			PixelColorRange currSensepcr = null;
			if (currSense.orientation.color != null) {
				currSensepcr = new PixelColorRange(currSense.orientation.color);
			}
			if (currSense.definition.overallChangeDefString != null) {
				if (!currPrevEnvSense.definition.toString().equals(currSense.definition.toString())){
					currSense.definition.overallChangeDefString = null;
				}
			}
			if (currPrevEnvSense.orientation.height != currSense.orientation.height) {
				currSense.orientation.height = -1;
			}
			if (currPrevEnvSense.orientation.width != currSense.orientation.width) {
				currSense.orientation.width = -1;	
			}
			if (currPrevEnvSense.orientation.rotation != currSense.orientation.rotation) {
				currSense.orientation.rotation = -999599;	
			}
			if (currPrevEnvSense.orientation.position.x != currSense.orientation.position.x) {
				currSense.orientation.position.x = -1;	
			}
			if (currPrevEnvSense.orientation.position.y != currSense.orientation.position.y) {
				currSense.orientation.position.y = -1;
			}
			if (currSense.orientation.color != null) {
				if (!currConSensepcr.color.equals(currSensepcr.color)) {
					currSense.orientation.color = null;
				}
			}
			
			newConditionEnv.abstractEnv.senses.add(currSense);
			if (newConditionEnv.abstractEnv.senses.size() == aecpw.conditionEnvSensesSize) {
				break;
			}
		}
		
					
		newConditionEnv = UploadConditionEnvToDB.exec(newConditionEnv, eaiw.myConnection);
		//newConditionEnv = UpdateConditionEnvInDBToBeThisEnv.update(newConditionEnv, currActivityId, myConnection, fw);
		//
		//for testing TODO
		newConditionEnv.dbId = newConditionEnv.dbId + 1;
		//
		AbstractEnvClosingWrapper aecw = new AbstractEnvClosingWrapper();
		aecw.aecpw = aecpw;
		aecw.eaiw = eaiw;
		aecw.eapw = eapw;
		aecw.ecaw = ecaw;
		aecw.newConditionEnv = newConditionEnv;
		return aecw;
	}
}
