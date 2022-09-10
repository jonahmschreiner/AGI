package Structure;

import java.util.ArrayList;
import java.util.List;

public class BlobCombinerThreadResult {
	public int iValue;
	public List<Blob> blobList = new ArrayList<Blob>();
	
	public BlobCombinerThreadResult(int iValueIn, List<Blob> blobListIn) {
		this.iValue = iValueIn;
		this.blobList = blobListIn;
	}

}
