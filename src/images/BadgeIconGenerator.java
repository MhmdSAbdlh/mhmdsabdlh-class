package mhmdsabdlh.images;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class BadgeIconGenerator {

	public static ImageIcon generateBadgeIcon(ImageIcon baseIcon, int number) {
		// Original icon dimensions
		int width = baseIcon.getIconWidth();
		int height = baseIcon.getIconHeight();

		// Create a buffered image with transparency
		BufferedImage badgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = badgeImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the original icon
		g2d.drawImage(baseIcon.getImage(), 0, 0, null);

		// Badge size (adjust as needed)
		int badgeSize = Math.min(width, height) / 2;
		int badgeX = width - badgeSize - 2; // Offset from right edge
		int badgeY = 2; // Offset from top edge

		// Get base color (average of icon colors)
		Color iconColor = getAverageColor(baseIcon, 0.8f);
		Color badgeColor = iconColor;
		Color iconFullColor = getAverageColor(baseIcon, 1);
		Color textColor = getInvertedColor(iconFullColor);

		// Draw the border first
		g2d.setColor(textColor); // Color of the border
		g2d.setStroke(new BasicStroke(2));
		g2d.drawOval(badgeX - 2 / 2, badgeY - 2 / 2, badgeSize + 1, badgeSize + 1);

		// Draw badge
		g2d.setColor(badgeColor);
		g2d.fillOval(badgeX, badgeY, badgeSize, badgeSize);

		// Draw number on badge
		g2d.setColor(textColor);
		Font font = new Font("Arial", Font.BOLD, badgeSize / 2);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		String numberText = String.valueOf(number);
		int textX = badgeX + (badgeSize - fm.stringWidth(numberText)) / 2;
		int textY = badgeY + (badgeSize + fm.getAscent()) / 2 - 2;
		g2d.drawString(numberText, textX, textY);

		g2d.dispose();

		return new ImageIcon(badgeImage);
	}

	private static Color getAverageColor(ImageIcon icon, float adjustmentFactor) {
		// Validate the adjustment factor
		if (adjustmentFactor < 0.0f || adjustmentFactor > 1.0f) {
			throw new IllegalArgumentException("Adjustment factor must be between 0.0 and 1.0");
		}

		// Create a buffered image from the icon
		BufferedImage img = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = img.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		g.dispose();

		// Variables to store RGB sums
		long r = 0, gSum = 0, b = 0;
		int count = 0;

		// Loop through all pixels to calculate the average RGB
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				Color color = new Color(img.getRGB(x, y));
				r += color.getRed();
				gSum += color.getGreen();
				b += color.getBlue();
				count++;
			}
		}

		// Calculate the average RGB values
		int avgR = (int) (r / count);
		int avgG = (int) (gSum / count);
		int avgB = (int) (b / count);

		// Adjust the color toward white using the adjustment factor
		avgR = (int) (avgR + (255 - avgR) * adjustmentFactor);
		avgG = (int) (avgG + (255 - avgG) * adjustmentFactor);
		avgB = (int) (avgB + (255 - avgB) * adjustmentFactor);

		// Return the brighter average color
		return new Color(avgR, avgG, avgB);
	}

	private static Color getInvertedColor(Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}
}
