package test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import com.whitespike.visionstructure.Pixel;
/**
 * Service method that visually displays where a list of pixels are in an image for debugging purposes.
 * @author Jonah Schreiner
 *
 */
public class FillInWholeSense {
	/**
	 * Service method that visually displays where a list of pixels are in an image for debugging purposes.
	 * @param pixelsIn The pixels to be displayed (usually the pixels list of a sense's blob).
	 * @param image1In The image to write the pixels to (usually the currentDisplay of the associated env).
	 * @param imageNameIn The file name of the output image.
	 * @param pathIn The path to where the output image will be created.
	 */
	public static void exec(List<Pixel> pixelsIn, BufferedImage image1In, String imageNameIn, String pathIn) {
		ColorModel cm = image1In.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image1In.copyData(null);
		BufferedImage imageIn = new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage(605, 271, 468, 398);
		
		for (int i = 0; i < pixelsIn.size(); i++) {
			Pixel currPixel = pixelsIn.get(i);
			imageIn.setRGB(currPixel.position.x, currPixel.position.y, new Color(255, 0, 0).getRGB());
		}
		
		File outputFile;
		if (imageNameIn == null) {
			imageNameIn = "output";
		}
		if (pathIn.endsWith("/")) {
			pathIn = pathIn.substring(0, pathIn.length() - 2);
		}
		outputFile = new File(pathIn + "/" + imageNameIn + ".jpg");
		try {
			ImageIO.write(imageIn, "jpg", outputFile);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
