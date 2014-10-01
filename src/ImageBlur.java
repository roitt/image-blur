import java.awt.image.BufferedImage;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageBlur {
	private static SampleModel sampleModel;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File imageFile = new File("./sample-images/hyd_back.jpg");
		int[][] pixelsMatrix = getPixelMatrix(imageFile);
		int[][] modifiedPixelsMatrix = getAveragedPixelMatrix(pixelsMatrix);
		File blurredImageFile = getImageFile(modifiedPixelsMatrix);
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
			if (bufferedImage.getType() == BufferedImage.TYPE_INT_RGB) {
				pixels = imageToPixels(bufferedImage);
			} else {
				BufferedImage tmpImage = new BufferedImage(
						bufferedImage.getWidth(null),
						bufferedImage.getHeight(null),
						BufferedImage.TYPE_INT_RGB);
				tmpImage.createGraphics().drawImage(bufferedImage, 0, 0, null);
				pixels = imageToPixels(tmpImage);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pixels;
	}

	public static int[][] imageToPixels(BufferedImage image)
			throws IllegalArgumentException {
		if (image == null) {
			throw new IllegalArgumentException();
		}

		int width = image.getWidth();
		int height = image.getHeight();
		int[][] pixels = new int[height][width];
		for (int row = 0; row < height; row++) {
			image.getRGB(0, row, width, 1, pixels[row], 0, width);
		}
		return pixels;
	}

	public static int[][] getAveragedPixelMatrix(int[][] inputMatrix) {
		for (int i = 0; (i + 3) < inputMatrix.length; i = i + 3) {
			for (int j = 0; (j + 3) < inputMatrix[0].length; j = j + 3) {
				// Replace the center value with average of pixels
				int sum = 0;
				for (int k = i; k < i + 3; k++) {
					for (int l = j; l < j + 3; l++) {
						sum += inputMatrix[k][l];
					}
				}
				inputMatrix[i + 1][j + 1] = sum / 9;
			}
		}
		return inputMatrix;
	}
	
	 public static BufferedImage pixelsToImage(int[][] pixels) throws IllegalArgumentException {
	        if (pixels == null) {
	            throw new IllegalArgumentException();
	        }
	        
	        int width = pixels[0].length;
	        int height = pixels.length;
	        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        for (int row = 0; row < height; row++) {
	            image.setRGB(0, row, width, 1, pixels[row], 0, width);
	        }
	        return image;
	    }

	public static File getImageFile(int[][] pixelMatrix) {
		BufferedImage blurredImage = pixelsToImage(pixelMatrix);
		File outputfile = new File("./output-images/blurred.jpg");
		try {
			ImageIO.write(blurredImage, "jpg", outputfile);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outputfile;
	}
}
