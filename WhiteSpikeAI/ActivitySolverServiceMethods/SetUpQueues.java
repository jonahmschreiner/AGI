package ActivitySolverServiceMethods;

import Structure.Queues;
import Structure.StartupWrapper;

public class SetUpQueues {
	public static Queues exec(Queues queues, StartupWrapper sw) {
		try {
			queues.activitiesToSolveQueue = SetUpActivitiesToSolveQueueIfNecessary.setup(queues.activitiesToSolveQueue, sw.fw, sw.myConnection);
			System.out.println("activities to solve set up if necessary");
			sw.fw.append(" activities to solve queue set up (count: " + queues.activitiesToSolveQueue.size() + ")\n");
			sw.fw.flush();
			queues.activitiesToTryQueue = SetUpActivitiesToTryQueueIfNecessary.setup(queues.activitiesToTryQueue, queues.activitiesToSolveQueue.get(0), sw.myConnection, sw.fw);
			System.out.println("activities to try set up if necessary");
			sw.fw.append(" activities to try queue set up (count: " + queues.activitiesToTryQueue.size() + ")\n");
			sw.fw.flush();
			queues.actionQueue = SetUpActionQueueIfNecessary.setup(queues.actionQueue, queues.activitiesToTryQueue, sw.myConnection);
			System.out.println("action queue set up if necessary");
			sw.fw.append(" action queue set up (count: " + queues.actionQueue.size() + ")\n");
			sw.fw.flush();
		} catch (Exception e) {
			System.out.println("Queue set up couldn't write logs to file.");
			e.printStackTrace();
		}

		return queues;
	}
}
