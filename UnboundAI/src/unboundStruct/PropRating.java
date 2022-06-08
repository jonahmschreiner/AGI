package unboundStruct;

public class PropRating {
	public String propName;
	public String propValue;
	public int rating;
	public Sense parentSense;
	
	public PropRating() {
		this.propName = "name not set";
		this.rating = 0;
	}
	public PropRating(String nameIn, String valueIn, int ratingIn, Sense senseIn) {
		this.propName = nameIn;
		this.rating = ratingIn;
		this.parentSense = senseIn;
		this.propValue = valueIn;
	}
	
	@Override
	public String toString(){
		return "Name: " + this.propName + "\nRating: " + this.rating;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof PropRating){
			PropRating c = (PropRating) o;
			boolean output = false;
			if (c.propName.equals(this.propName)){
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
