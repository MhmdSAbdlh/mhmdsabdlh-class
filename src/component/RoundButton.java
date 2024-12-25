package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

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
				isHovering = true;
				repaint(getBounds()); // Only repaint the button
			}

			@Override
			public void mouseExited(MouseEvent e) {
				isHovering = false;
				repaint(getBounds()); // Only repaint the button
			}
		});
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		this.hoverColor = fillColor.brighter().equals(fillColor) ? fillColor.darker() : fillColor.brighter();
		repaint();
	}

	public void setBorderColorAndRadius(Color borderColor) {
		this.borderColor = borderColor;

		if (borderColor == null)
			setBorder(null);
		else
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, 1),
					BorderFactory.createEmptyBorder(10, 20, 10, 20)));

		repaint(); // Repaint to apply changes
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		GradientPaint gradient = new GradientPaint(0, 0, isHovering ? hoverColor : fillColor, 0, getHeight(),
				fillColor.darker());
		g2.setPaint(gradient);
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

		// Draw the icon if it exists
		Icon icon = getIcon();
		if (icon != null) {
			int iconX = (getWidth() - icon.getIconWidth()) / 2;
			int iconY = (getHeight() - icon.getIconHeight()) / 2;
			icon.paintIcon(this, g2, iconX, iconY);
		}

		if (getText() != null && !getText().isEmpty()) {
			FontMetrics fm = g2.getFontMetrics();
			int textX = (getWidth() - fm.stringWidth(getText())) / 2;
			int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

			// Draw actual text
			g2.setColor(getForeground());
			g2.drawString(getText(), textX, textY);
		}

		g2.dispose();
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
