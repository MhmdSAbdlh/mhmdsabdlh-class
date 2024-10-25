package mhmdasabdlh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JButton {
	private int radius;
	private Color fillColor;
	private Color borderColor;
	private Color hoverColor; // Static darker color for hover effect
	private boolean isHovering = false; // Tracks hover state

	public RoundButton(String text, int radius) {
		super(text);
		this.radius = radius;
		this.borderColor = Color.white;
		this.fillColor = Color.LIGHT_GRAY; // Default color
		this.hoverColor = fillColor.darker(); // Calculate hover color once

		setContentAreaFilled(false); // Prevent default button painting
		setFocusPainted(false); // Remove focus painting

		// Add mouse listener to handle hover effect
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				isHovering = true; // Set hovering state to true
				repaint(); // Repaint to apply hover color
			}

			@Override
			public void mouseExited(MouseEvent e) {
				isHovering = false; // Set hovering state to false
				repaint(); // Repaint to revert to original color
			}
		});
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		this.hoverColor = fillColor.darker(); // Update hover color based on new fill color
		repaint(); // Repaint to apply color change
	}

	public void setBorderColor(Color borderC) {
		this.borderColor = borderC;
		repaint(); // Repaint to apply color change
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		// Enable anti-aliasing for smooth edges
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Use hover color if hovering, else use fill color
		g2.setColor(isHovering ? hoverColor : fillColor);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

		// Draw the text in the center of the button
		FontMetrics fm = g2.getFontMetrics();
		int textX = (getWidth() - fm.stringWidth(getText())) / 2;
		int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

		g2.setColor(getForeground());
		g2.drawString(getText(), textX, textY);

		g2.dispose();

		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the border with rounded corners
		g2.setColor(borderColor);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

		g2.dispose();
	}
}
