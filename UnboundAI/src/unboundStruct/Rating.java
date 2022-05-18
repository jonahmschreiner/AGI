package unboundStruct;
import java.io.File;
public class Rating {
	public File goalFile;
	public String goalName;
	public int rating;	
	public Rating() {
		this.goalName = "goal not set";
		this.rating = 0;
	}
	public Rating(String goalFilePath, String goalNameIn, int ratingIn) {
		this.goalFile = new File(goalFilePath);
		this.goalName = goalNameIn;
		this.rating = ratingIn;
	}
	@Override
	public String toString(){
		return "Name: " + this.goalName + "\nRating: " + this.rating;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Rating){
			Rating c = (Rating) o;
			boolean output = false;
			if (c.goalName.equals(this.goalName)){
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
