import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBlur {
	private SampleModel sampleModel;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File imageFile = new File("./sample-images/hyd_back.jpg");
		int[][] pixelsMatrix = getPixelMatrix(imageFile);
		
	}

	public static void printPixels(int[][] pixelsMartix) {
		for (int x = 0; x < pixelsMartix.length; x++) {
			System.out.println();
			for (int y = 0; y < pixelsMartix[0].length; y++) {
				System.out.print(pixelsMartix[x][y] + " ");
			}
		}
	}

	public static int[][] getPixelMatrix(File imageFile) {
		int[][] pixels = null;
		try {
			BufferedImage bufferedImage = ImageIO.read(imageFile);
			Raster raster = bufferedImage.getData();
			int w = raster.getWidth();
			int h = raster.getHeight();
			pixels = new int[w][h];
			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					pixels[x][y] = raster.getSample(x, y, 0);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pixels;
	}
}
