package unboundStruct;
public class Instruction {
	public String parentClass;
	public int instructionNumber;
	public String instruction;
	
	public Instruction() {
		this.parentClass = "none";
		this.instructionNumber = 0;
		this.instruction = "";
	}
	
	public Instruction(String parentClassIn, int numberIn, String instructionIn) {
		this.parentClass = parentClassIn;
		this.instructionNumber = numberIn;
		this.instruction = instructionIn;
	}
	
	public void setParentClass(String parentClassIn) {
		this.parentClass = parentClassIn;
	}
	
	public void setInstruction(String instructionIn) {
		this.instruction = instructionIn;
	}
	
	public void setInstructionNumber(int numberIn) {
		this.instructionNumber = numberIn;
	}

	@Override
	public String toString() {
		return this.parentClass + ":" + this.instructionNumber + ":" + this.instruction;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Instruction){
			Instruction c = (Instruction) o;
			boolean output = false;
			if ((c.parentClass.equals(this.parentClass)) //variables (that describe conditions) are the same
					&& (c.instruction.equals(this.instruction))
					&& (c.instructionNumber == this.instructionNumber)){ 
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
