package agiStruct;


public class Instruction {
	private String parentClass;
	private int instructionNumber;
	private String instruction;
	private String subCodeBlock;
	
	public Instruction() {
		this.parentClass = "none";
		this.instructionNumber = 0;
		this.instruction = "";
	}
	
	public Instruction(String parentClassIn, int numberIn, String instructionIn, String subSourceIn) {
		this.parentClass = parentClassIn;
		this.instructionNumber = numberIn;
		this.instruction = instructionIn;
		this.subCodeBlock = subSourceIn;
	}
	
	//gets
	public String getSubCodeBlock(){
		return this.subCodeBlock;
	}
	public String getParentClass() {
		return this.parentClass;
	}
	
	public String getInstruction() {
		return this.instruction;
	}
	
	public int getInstructionNumber() {
		return this.instructionNumber;
	}
	
	//sets
	public void setSubCodeBlock(String subCodeIn) {
		this.subCodeBlock = subCodeIn;
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
	
	//Overrides
	
	@Override
	public String toString() {
		return this.parentClass + ":" + this.instructionNumber + ":" + this.instruction;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Env){
			Instruction c = (Instruction) o;
			boolean output = false;
			if ((c.getParentClass() == this.getParentClass()) //variables (that describe conditions) are the same
					&& (c.getInstruction() == this.getInstruction())
					&& (c.getInstructionNumber() == this.getInstructionNumber())){ 
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
