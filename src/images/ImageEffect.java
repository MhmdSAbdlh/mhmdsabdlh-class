package mhmdsabdlh.images;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageEffect {

	public static ImageIcon convertIconToImageIcon(Icon icon) {
		if (icon instanceof ImageIcon) {
			return (ImageIcon) icon;
		}

		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = bufferedImage.createGraphics();
		icon.paintIcon(null, g2d, 0, 0);
		g2d.dispose();

		return new ImageIcon(bufferedImage);
	}

	// Method to change the opacity of an image
	public static ImageIcon changeOpacity(Image srcImage, float opacity) {
		BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)); // Set opacity level
		g2.drawImage(srcImage, 0, 0, null);
		g2.dispose();
		return new ImageIcon(bufferedImage);
	}

	public static ImageIcon getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return new ImageIcon(resizedImg);
	}

	public static ImageIcon invertColor(ImageIcon originalIcon) {

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

		return convertIconToImageIcon(invertedIcon);
	}

	public static ImageIcon createImageIconFromText(String text, int width, int height, Color textColor,
			Color backgroundColor, Font font) {
		// Create a BufferedImage with given dimensions
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		// Get the Graphics2D context of the image
		Graphics2D g2d = image.createGraphics();

		// Set rendering hints for better text quality
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// Fill the background
		g2d.setColor(backgroundColor);
		g2d.fillRect(0, 0, width, height);

		// Set the text color and font
		g2d.setColor(textColor);
		g2d.setFont(font);

		// Center the text in the image
		FontMetrics fm = g2d.getFontMetrics();
		int x = (width - fm.stringWidth(text)) / 2;
		int y = (height - fm.getHeight()) / 2 + fm.getAscent();

		// Draw the text
		g2d.drawString(text, x, y);

		// Dispose of the graphics context and create an ImageIcon from the
		// BufferedImage
		g2d.dispose();

		return new ImageIcon(image);
	}

	public static ImageIcon createIconWithText(ImageIcon icon, String text, Font font, Color textColor) {
		// Create a buffered image with enough space for the icon and text
		int width = icon.getIconWidth() + getTextWidth(text, font) + 10; // 10 pixels padding
		int height = Math.max(icon.getIconHeight(), getTextHeight(font)) + 10; // 10 pixels padding

		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = combined.createGraphics();

		// Draw the icon
		g.drawImage(icon.getImage(), 0, (height - icon.getIconHeight()) / 2, null);

		// Set the font and color for the text
		g.setFont(font);
		g.setColor(textColor);

		// Draw the text to the right of the icon
		g.drawString(text, icon.getIconWidth() + 5, (height + getTextHeight(font)) / 2 - 5); // 5 pixels padding

		g.dispose();
		return new ImageIcon(combined);
	}

	private static int getTextWidth(String text, Font font) {
		FontMetrics metrics = new Canvas().getFontMetrics(font);
		return metrics.stringWidth(text);
	}

	private static int getTextHeight(Font font) {
		FontMetrics metrics = new Canvas().getFontMetrics(font);
		return metrics.getHeight();
	}

}
