package mhmdsabdlh.images;

import javax.swing.*;
import java.awt.*;

public class IconWithText implements Icon {

	// Define additional positions for the text
	public enum Position {
		TOP, BOTTOM, LEFT, RIGHT, CENTER, TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
	}

	private ImageIcon imageIcon;
	private String text;
	private Color textColor;
	private Position position;
	private Font font; // Add fontSize as a parameter
	private boolean round;

	public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position, Font font,
			boolean round) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = position;
		this.font = font;
		this.round = round;
	}

	public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position, Font font) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = position;
		this.font = font;
		this.round = true;
	}

	public IconWithText(ImageIcon imageIcon, String text, Color textColor, Position position) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = position;
		this.font = new Font("Arial", Font.BOLD, 18);
		this.round = true;
	}

	public IconWithText(ImageIcon imageIcon, String text, Color textColor) {
		this.imageIcon = imageIcon;
		this.text = text;
		this.textColor = textColor;
		this.position = Position.CENTER;
		this.font = new Font("Arial", Font.BOLD, 18);
		this.round = true;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTextColor(Color color) {
		this.textColor = color;
	}

	public void setPosition(Position pos) {
		this.position = pos;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public void setRound(boolean round) {
		this.round = round;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
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
		if (round) {
			g2d.setColor(new Color(255, 255, 255, 180)); // Semi-transparent white
			int padding = 3; // Padding around the text
			g2d.fillRoundRect(textX - padding, textY - textHeight, textWidth + 2 * padding, textHeight + 2 * padding,
					15, 15 // arc width/height -> more = rounder
			);
		} else {
			g2d.setColor(new Color(255, 255, 255, 180));
			int padding = 4;
			g2d.fillRect(textX - padding, textY - textHeight, textWidth + 2 * padding, textHeight + 2 * padding);
		}

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
