package unboundStruct;
import java.io.File;
import java.util.List;
public class SenseRating {
	public File senseFile;
	public String senseName;
	public int rating;	
	public List<PropRating> propRatings;
	public SenseRating() {
		this.senseName = "sense not set";
		this.rating = 0;
	}
	public SenseRating(String senseFilePath, String senseNameIn, int ratingIn, List<PropRating> propRatingsIn) {
		this.senseFile = new File(senseFilePath);
		this.senseName = senseNameIn;
		this.rating = ratingIn;
		this.propRatings = propRatingsIn;
	}
	@Override
	public String toString(){
		return "Name: " + this.senseName + "\nRating: " + this.rating;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof SenseRating){
			SenseRating c = (SenseRating) o;
			boolean output = false;
			if (c.senseName.equals(this.senseName)){
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
