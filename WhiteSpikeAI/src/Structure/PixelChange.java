package Structure;

public class PixelChange {
	public String changeType; //None, RightTurn, LeftTurn, RightDownDiagonal, RightUpDiagonal, LeftDownDiagonal, LeftUpDiagonal
	public int changeCode;
	public PixelChange() {}
	public PixelChange(String typeIn) {
		setChange(typeIn);
	}
	public void setChange(String changeTypeIn) {
		switch (changeTypeIn) {
		case "None": {this.changeCode = 0; this.changeType = changeTypeIn; break;}
		case "LeftTurn": {this.changeCode = 1; this.changeType = changeTypeIn; break;}
		case "RightTurn": {this.changeCode = 2; this.changeType = changeTypeIn; break;}
		case "RightDownDiagonal": {this.changeCode = 6; this.changeType = changeTypeIn; break;}
		case "RightUpDiagonal": {this.changeCode = 4; this.changeType = changeTypeIn; break;}
		case "LeftDownDiagonal": {this.changeCode = 5; this.changeType = changeTypeIn; break;}
		case "LeftUpDiagonal": {this.changeCode = 3; this.changeType = changeTypeIn; break;}
		}
		 
	}
}
