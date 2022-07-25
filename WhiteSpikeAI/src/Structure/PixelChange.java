package Structure;

public class PixelChange {
	public String changeType; //None, RightTurn, LeftTurn, RightDownDiagonal, RightUpDiagonal, LeftDownDiagonal, LeftUpDiagonal
	public PixelChange() {}
	public PixelChange(String typeIn) {
		this.changeType = typeIn;
	}
}
