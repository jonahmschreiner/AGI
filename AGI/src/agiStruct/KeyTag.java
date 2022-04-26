package agiStruct;
public class KeyTag {

	private String Label;
	private String subLabel;
	private int confidenceRating;
	
	public KeyTag(String LabelIn, String subLabelIn, int confidenceRatingIn){
		this.Label = LabelIn;
		this.subLabel = subLabelIn;
		this.confidenceRating = confidenceRatingIn;
	}
	public KeyTag() {
		
	}
	public String getLabel(){
		return Label;
	}
	
	public String getSubLabel(){
		return subLabel;
	}
	
	public int getConfidenceRating(){
		return confidenceRating;
	}
	
	public void setLabel(String LabelIn){
		this.Label = LabelIn;
	}
	
	public void setSubLabel(String SubLabelIn){
		this.subLabel = SubLabelIn;
	}
	
	public void setConfidenceRating(int ratingIn){
		this.confidenceRating = ratingIn;
	}
	
	@Override
	public String toString(){
		return "Label: " + this.Label + "\nSubLabel: " + this.subLabel + "\nConfidence Rating: " + this.confidenceRating;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof KeyTag){
			KeyTag c = (KeyTag) o;
			boolean output = false;
			if ((c.getLabel() == this.getLabel()) && (c.getSubLabel() == this.getSubLabel()) && (c.getConfidenceRating() == this.getConfidenceRating())){
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
