package Structure;

import java.util.List;

public class AbstractEnv {
	List<Sense> senses;
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof AbstractEnv){
			AbstractEnv c = (AbstractEnv) o;
			boolean output = false;
			if (c.senses.equals(this.senses)){ //variables (that describe conditions) are the same
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}
	}
}
