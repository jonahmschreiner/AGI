package Structure;

import java.util.ArrayList;
import java.util.List;

public class ChangedPixelsBlobThreadResult {
	public int iValue;
	public List<Blob> blobList = new ArrayList<Blob>();
	public ChangedPixelsBlobThreadResult(int iValueIn, List<Blob> blobListIn) {
		this.iValue = iValueIn;
		this.blobList = blobListIn;
	}
}
