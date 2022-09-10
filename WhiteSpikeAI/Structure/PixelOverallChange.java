package Structure;

public class PixelOverallChange {
	public String changeType; //Straight, RightTurn, LeftTurn, StartLeftUpDiagonal, MaintainLeftUpDiagonal, StartRightUpDiagonal, MaintainRightUpDiagonal, LeftDownDiagonal, RightDownDiagonal
	public PixelOverallChange() {}
	public PixelOverallChange(String changeTypeIn) {
		this.changeType = changeTypeIn;
	}
}
