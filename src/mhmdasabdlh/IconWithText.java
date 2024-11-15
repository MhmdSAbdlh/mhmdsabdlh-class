package mhmdasabdlh;

import javax.swing.*;
import java.awt.*;

public class IconWithText implements Icon {

	// Define additional positions for the text
	public enum Position {
		TOP, BOTTOM, LEFT, RIGHT, CENTER, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
	}

	private final ImageIcon imageIcon;
	private final String text;
	private final Color textColor;
	private final Position position;
	private final Font font; // Add fontSize as a parameter

	public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position, Font font) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = position;
		this.font = font;
	}

	public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = position;
		this.font = new Font("Arial", Font.BOLD, 18);
	}

	public IconWithText(ImageIcon imageIcon, String text, Color textColor) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = Position.CENTER;
		this.font = new Font("Arial", Font.BOLD, 18);
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// Cast Graphics to Graphics2D for advanced drawing features
		Graphics2D g2d = (Graphics2D) g;

		// Enable anti-aliasing for smoother text
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the image
		imageIcon.paintIcon(c, g, x, y);

		// Set the text color and font
		g.setColor(textColor);
		g.setFont(font);

		// Font metrics for text size calculation
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(text);
		int textHeight = fm.getAscent();

		// Get image dimensions
		int imageWidth = imageIcon.getIconWidth();
		int imageHeight = imageIcon.getIconHeight();

		// Determine text position
		int textX = 0, textY = 0;

		switch (position) {
		case TOP:
			textX = x + (imageWidth - textWidth) / 2; // Center horizontally
			textY = y + textHeight; // Above the image
			break;
		case BOTTOM:
			textX = x + (imageWidth - textWidth) / 2; // Center horizontally
			textY = y + imageHeight; // Below the image
			break;
		case LEFT:
			textX = x - textWidth - 5; // Left of the image with spacing
			textY = y + (imageHeight + textHeight) / 2; // Center vertically
			break;
		case RIGHT:
			textX = x + imageWidth + 5; // Right of the image with spacing
			textY = y + (imageHeight + textHeight) / 2; // Center vertically
			break;
		case CENTER:
		default:
			textX = x + (imageWidth - textWidth) / 2; // Center horizontally
			textY = y + ((imageHeight - textHeight) / 2) + fm.getAscent(); // Center vertically
			break;

		// Additional positions for corners
		case TOP_LEFT:
			textX = x; // Left of the image
			textY = y + textHeight; // Above the image
			break;
		case TOP_RIGHT:
			textX = x + imageWidth - textWidth; // Right of the image
			textY = y + textHeight; // Above the image
			break;
		case BOTTOM_LEFT:
			textX = x; // Left of the image
			textY = y + imageHeight - 5; // Below the image
			break;
		case BOTTOM_RIGHT:
			textX = x + imageWidth - textWidth - 5; // Right of the image
			textY = y + imageHeight - 5; // Below the image
			break;
		}
		// Set the background color to semi-transparent white (alpha value < 255)
		g2d.setColor(new Color(255, 255, 255, 180)); // Semi-transparent white
		int padding = 4; // Padding around the text
		// Draw the transparent background for the text
		g2d.fillRect(textX - padding, textY - textHeight, textWidth + 2 * padding, textHeight + 2 * padding);

		// Set the text color back to the desired text color
		g.setColor(textColor);
		// Draw the text at the calculated position
		g.drawString(text, textX, textY);
	}

	@Override
	public int getIconWidth() {
		return imageIcon.getIconWidth();
	}

	@Override
	public int getIconHeight() {
		return imageIcon.getIconHeight();
	}
}
