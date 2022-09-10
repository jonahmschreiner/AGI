package Structure;

import java.util.List;

import ServiceMethods.ExecuteCoreAction;

public class Activity {
	public String activityName;
	public List<Activity> subActivities;
	public CoreAction associatedCoreAction;
	public Condition condition;
	
	public Activity() {}
	public Activity(CoreAction CAIn) {
		this.associatedCoreAction = CAIn;
	}
	public Activity(CoreAction CAIn, Condition conditionIn) {
		this.associatedCoreAction = CAIn;
		this.condition = conditionIn;
	}
	public Activity(CoreAction CAIn, Condition conditionIn, List<Activity> subActivitiesIn) {
		this.associatedCoreAction = CAIn;
		this.condition = conditionIn;
		this.subActivities = subActivitiesIn;
	}
	public Activity(List<Activity> subActivitiesIn) {
		this.subActivities = subActivitiesIn;
	}
	public Activity(Condition conditionIn, List<Activity> subActivitiesIn) {
		this.condition = conditionIn;
		this.subActivities = subActivitiesIn;
	}
	
	public void exec () {
		if (subActivities.size() > 0) {
			int executionIndex = 0;
			while (!condition.check()) {
				//execute sub-activities in order
				subActivities.get(executionIndex).exec();
				if (executionIndex < subActivities.size()) {
					executionIndex = executionIndex + 1;
				} else {
					executionIndex = 0;
				}
			}
		} else if (associatedCoreAction != null) {
			while (!condition.check()) {
				//execute core action
				ExecuteCoreAction.exec(associatedCoreAction.coreActionIndex);
			}
		}
	}
}
