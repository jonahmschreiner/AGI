package unboundStruct;
public class KeyTag {
	public String label;
	public int confidenceRating;
	
	public KeyTag(String LabelIn, int confidenceRatingIn){
		this.label = LabelIn;
		this.confidenceRating = confidenceRatingIn;
	}
	public KeyTag() {}	
	public void setLabel(String LabelIn){
		this.label = LabelIn;
	}	
	public void setConfidenceRating(int ratingIn){
		this.confidenceRating = ratingIn;
	}
	
	@Override
	public String toString(){
		return "Label: " + this.label + "\nConfidence Rating: " + this.confidenceRating;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof KeyTag){
			KeyTag c = (KeyTag) o;
			boolean output = false;
			if (c.label == this.label){
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
