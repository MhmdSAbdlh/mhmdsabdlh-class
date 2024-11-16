package mhmdsabdlh.images;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageBlur {

	public BufferedImage blurImageFromResource(String resourcePath) {
		BufferedImage image = null;

		try {
			// Load the image from the resources folder
			URL imageUrl = getClass().getResource(resourcePath);
			if (imageUrl != null) {
				image = ImageIO.read(imageUrl);

				// Apply blur if the image is successfully loaded
				if (image != null) {
					return blurImage(image);
				} else {
					System.out.println("Could not load the image from the resource path.");
				}
			} else {
				System.out.println("Resource not found: " + resourcePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public BufferedImage blurImageFromFile(String imagePath) {
		BufferedImage image = null;

		try {
			// Load the image from the file system (from the folder next to the JAR file)
			File imageFile = new File(imagePath);
			if (imageFile.exists()) {
				image = ImageIO.read(imageFile);

				// Apply blur if the image is successfully loaded
				if (image != null) {
					return blurImage(image);
				} else {
					System.out.println("Could not load the image from the file.");
				}
			} else {
				System.out.println("File not found: " + imagePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static BufferedImage blurImage(BufferedImage image) {
		float[] blurKernel = new float[100]; // 7x7 kernel

		// Initialize the kernel with equal values for a simple blur
		for (int i = 0; i < blurKernel.length; i++) {
			blurKernel[i] = 1f / 100f;
		}

		Kernel kernel = new Kernel(10, 10, blurKernel);
		ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

		return convolveOp.filter(image, null);
	}
}
