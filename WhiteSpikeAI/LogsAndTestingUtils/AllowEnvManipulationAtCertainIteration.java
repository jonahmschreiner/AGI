package LogsAndTestingUtils;

import java.awt.MouseInfo;
import java.awt.Point;

import EnvAndDatabaseServiceMethods.Util;
import Structure.LFInputWrapper;

public class AllowEnvManipulationAtCertainIteration {
	public static void exec(int testCount, LFInputWrapper LFInput) {
		if (testCount == 5 || (testCount > 5 && LFInput.queues.activitiesToTryQueue.size() == 0)) {
			Point testPoint = MouseInfo.getPointerInfo().getLocation();
			System.out.println("");
			try {
				Thread.sleep(4000);
				Util util = new Util();
				util.moveMouseToCoordinates(testPoint.x, testPoint.y);
			} catch (Exception e) {
				
			}
		}
	}
}
