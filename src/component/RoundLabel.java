package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class RoundLabel extends JLabel {

	private int borderRadius;
	private Color fillColor;

	public RoundLabel(String text, int borderRadius) {
		super(text);
		this.borderRadius = borderRadius;
		this.fillColor = Color.LIGHT_GRAY; // Default color
		setOpaque(false);
		setPreferredSize(new Dimension(100, 50)); // Set the preferred size of the label
		setHorizontalAlignment(CENTER); // Center the text horizontally
		setVerticalAlignment(CENTER); // Center the text vertically
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Create a black border around the label
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int width = getWidth();
		int height = getHeight();

		// Draw the rounded border with the specified corner radius
		RoundRectangle2D roundedRect = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, borderRadius,
				borderRadius);

		// Fill the rounded rectangle with the background color
		g2d.setColor(fillColor);
		g2d.fill(roundedRect);

		super.paintComponent(g2d);
		g2d.dispose();
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setColor(getForeground());

		int width = getWidth();
		int height = getHeight();

		// Draw the rounded border with the specified corner radius
		RoundRectangle2D roundedRect = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, borderRadius,
				borderRadius);
		g2d.draw(roundedRect);

		g2d.dispose();
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
		setBackground(fillColor);
		repaint();
	}
}
