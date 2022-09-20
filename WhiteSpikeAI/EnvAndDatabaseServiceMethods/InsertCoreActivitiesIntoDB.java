package EnvAndDatabaseServiceMethods;

import java.util.*;
import Structure.*;

public class InsertCoreActivitiesIntoDB {
	public static void insert() {
		//create list of core activities
		List<Activity> coreActivitiesToInsert = new ArrayList<Activity>();
		for (int i = 0; i < 442; i++) {
			CoreAction coreAction = new CoreAction(i);
			Activity coreActivity = new Activity(coreAction);
			coreActivity.activityName = "CoreAction" + i;
			coreActivitiesToInsert.add(coreActivity);
		}
		
		//insert the list to DB
	}
}
