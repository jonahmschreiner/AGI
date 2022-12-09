package Structure;

public class LFInputWrapper {
	public StartupWrapper sw;
	public Queues queues;
	public boolean continueLooping = true;
	public LFInputWrapper(StartupWrapper swIn, Queues queuesIn) {
		this.sw = swIn;
		this.queues = queuesIn;
	}
}
