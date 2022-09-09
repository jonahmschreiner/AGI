import java.util.List;
import java.util.ArrayList;

public class BlobThreadResult {
	public int iValue;
	public List<Blob> blobList = new ArrayList<Blob>();
	public List<Pixel> topRowOfPixels = new ArrayList<Pixel>();
	public List<Pixel> bottomRowOfPixels = new ArrayList<Pixel>();
	
	public BlobThreadResult(int iValueIn, List<Blob> blobListIn, List<Pixel> topRowIn, List<Pixel> bottomRowIn) {
		this.iValue = iValueIn;
		this.blobList = blobListIn;
		this.topRowOfPixels = topRowIn;
		this.bottomRowOfPixels = bottomRowIn;
	}
}
