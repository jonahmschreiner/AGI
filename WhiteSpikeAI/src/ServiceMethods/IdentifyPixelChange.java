package ServiceMethods;

import Structure.PixelChange;
import Structure.PixelPosChange;
import java.util.HashMap;

public class IdentifyPixelChange {
	public static PixelChange IdentifyPixelChange(PixelPosChange change1In, PixelPosChange change2In) {
		//None, RightTurn, LeftTurn, RightDownDiagonal, RightUpDiagonal, LeftDownDiagonal, LeftUpDiagonal
		//change1In x: 0 (<>), 1(increase), -1(decrease)
		//change1In y: 0 (<>), 1(increase), -1(decrease)
		//change2In x: 0 (<>), 1(increase), -1(decrease)
		//change2In y: 0 (<>), 1(increase), -1(decrease)\
		//key string: change1InXchange2InXchange1InYchange2InY Examples: 0000, -101-1
		PixelChange pixelChange = new PixelChange();
		if (change1In.xChange == change2In.xChange && change1In.yChange == change2In.yChange) {
			pixelChange.changeType = "None";
		} else {
			HashMap<String, String> pixelChanges = new HashMap<String, String>();
			pixelChanges.put("0000", "None");
			pixelChanges.put("", "None");
			pixelChanges.put("0000", "None");
			pixelChanges.put("0000", "None");
			pixelChanges.put("0000", "None");
		}

		return pixelChange;
	}
}
