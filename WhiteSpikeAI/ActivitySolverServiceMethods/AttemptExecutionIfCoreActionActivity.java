package ActivitySolverServiceMethods;

import java.sql.SQLException;

import EnvAndDatabaseServiceMethods.ExecuteCoreAction;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;

public class AttemptExecutionIfCoreActionActivity {
	public static ExecuteActivityPrepWrapper exec(ExecuteActivityPrepWrapper eapw, ExecuteActivityInputWrapper eaiw) throws Exception {
		eapw.activityRS.next();	
		try {
			eapw.propId = eapw.activityRS.getInt("PropertyId");
			eapw.increaseOrDecreaseProp = eapw.activityRS.getInt("increaseOrDecreaseProp");
			eapw.associatedSense = eapw.activityRS.getInt("AssociatedSense");
		} catch (Exception e) {
			e.printStackTrace();
		}
		eapw.coreActivityToExecute = eapw.activityRS.getInt("CoreActivity");
		if (eapw.coreActivityToExecute == -1) {
			throw new Exception();
		}
		eaiw.fw.append(" EXECACT: Core Activity Found\n");
		eaiw.fw.flush();
		ExecuteCoreAction.exec(eapw.coreActivityToExecute, eaiw.fw, eaiw.envIn.rawEnv); //TODO remove the envIn.rawEnv when done testing
		return eapw;
	}
}
