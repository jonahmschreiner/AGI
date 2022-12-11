package ActivitySolverServiceMethods;

import java.time.LocalDateTime;

import Structure.Env;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.RawEnvClosingPrepWrapper;

public class RawEnvClosing {
	public static Env exec(ExecuteActivityPrepWrapper eapw, RawEnvClosingPrepWrapper recpw, ExecuteActivityInputWrapper eaiw) {
		//for each sense in current Env, check to see if the current sense has the same values as any result in the condition env results and if so adds them to a condition env
		Env newConditionEnv = new Env(0);
		//raw env closing
		if (eapw.prevEnv.rawEnv.currentDateTime.equals(recpw.existingConEnvLDT)) {
			newConditionEnv.rawEnv.currentDateTime = recpw.existingConEnvLDT;
		} else {
			newConditionEnv.rawEnv.currentDateTime = LocalDateTime.MIN;
		}
		
		if (eapw.prevEnv.rawEnv.currentCpuUsage.compareTo(recpw.existingConEnvCpuUsage) == 0) {
			newConditionEnv.rawEnv.currentCpuUsage = recpw.existingConEnvCpuUsage;
		} else {
			newConditionEnv.rawEnv.currentCpuUsage = -1.0;
		}
		
		if (eapw.prevEnv.rawEnv.mouseLocation.x == recpw.existingConEnvMouseX) {
			newConditionEnv.rawEnv.mouseLocation.x = recpw.existingConEnvMouseX;
		} else {
			newConditionEnv.rawEnv.mouseLocation.x = -1;
		}
		
		if (eapw.prevEnv.rawEnv.mouseLocation.y == recpw.existingConEnvMouseY) {
			newConditionEnv.rawEnv.mouseLocation.y = recpw.existingConEnvMouseY;
		} else {
			newConditionEnv.rawEnv.mouseLocation.y = -1;
		}
		
		newConditionEnv.rawEnv.currentDisplay = eaiw.envIn.rawEnv.currentDisplay;
		
		return newConditionEnv;
	}
}
