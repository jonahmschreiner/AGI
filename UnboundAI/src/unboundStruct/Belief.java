package unboundStruct;

import java.util.*;

public class Belief {
	public List<Instruction> instructions;
	public String beliefName;
	public int beliefType; // Goal: 0   |  Sense: 1   |  Action: 2
	public List<KeyTag> keyTags;
	public Belief() {
		instructions = new ArrayList<Instruction>();
		beliefName = "noName";
		beliefType = -1;
		keyTags = new ArrayList<KeyTag>();
	}
	
	public Belief(String nameIn, int typeIn, List<Instruction> instsIn, List<KeyTag> keyTagsIn) {
		this.beliefName = nameIn;
		this.beliefType = typeIn;
		this.instructions = instsIn;
		this.keyTags = keyTagsIn;
	}
	
	@Override
	public String toString() {
		return this.beliefName + ":" + this.beliefType + ":" + this.keyTags + ":" + this.instructions;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Env){
			Belief c = (Belief) o;
			boolean output = false;
			if ((c.beliefType == this.beliefType) //variables (that describe conditions) are the same
					&& (c.beliefName.equals(this.beliefName))){ 
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

