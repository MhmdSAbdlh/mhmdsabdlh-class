package mhmdasabdlh;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageEffect {
	
	// Method to change the opacity of an image
	public static Image changeOpacity(Image srcImage, float opacity) {
		BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)); // Set opacity level
		g2.drawImage(srcImage, 0, 0, null);
		g2.dispose();
		return bufferedImage;
	}

	public static Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}
	
	static public Icon invertColor(Icon originalIcon) {

		// Convert the icon to BufferedImage
		BufferedImage image = new BufferedImage(originalIcon.getIconWidth(), originalIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		originalIcon.paintIcon(null, g, 0, 0);
		g.dispose();

		// Invert the colors of the image
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int rgba = image.getRGB(x, y);
				Color col = new Color(rgba, true);
				Color invertedCol = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue(),
						col.getAlpha());
				image.setRGB(x, y, invertedCol.getRGB());
			}
		}

		// Create a new icon with the inverted colors
		Icon invertedIcon = new ImageIcon(image);

		return invertedIcon;

	}
}
