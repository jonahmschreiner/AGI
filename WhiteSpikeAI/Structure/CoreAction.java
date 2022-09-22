package Structure;

import EnvAndDatabaseServiceMethods.ExecuteCoreAction;

public class CoreAction {
	int coreActionIndex;
	public CoreAction() {}
	public CoreAction(int indexIn) {
		this.coreActionIndex = indexIn;
	}
	public void execute() {
		ExecuteCoreAction.exec(this.coreActionIndex);
	}
	
}
