package MainLLF;

import Structure.LFInputWrapper;
import Structure.Queues;
import Structure.StartupWrapper;

public class CreateInitialLFInputWrapper {
	public static LFInputWrapper exec() {
		StartupWrapper sw = Startup.exec();
		Queues queues = new Queues();
		LFInputWrapper LFInput = new LFInputWrapper(sw, queues);
		return LFInput;
	}
}
