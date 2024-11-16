package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

public class RoundedCornerBorder extends AbstractBorder {
	private Color borderColor;
	private int borderThickness;

	public RoundedCornerBorder(Color borderColor, int borderThickness) {
		this.borderColor = borderColor;
		this.borderThickness = borderThickness;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		super.paintBorder(c, g, x, y, width, height);

		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(borderColor);

		// Enable anti-aliasing for smoother graphics
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the rounded rectangle with the specified corner radius and border
		// thickness
		g2d.drawRoundRect(x, y, width - 1, height - 1, ((Dock) c).cornerRadius, ((Dock) c).cornerRadius);

		g2d.dispose();
	}

	@Override
	public Insets getBorderInsets(Component c) {
		int padding = borderThickness + 1; // Add 1 to avoid clipping
		return new Insets(padding, padding, padding, padding);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.left = insets.right = insets.bottom = insets.top = borderThickness + 1;
		return insets;
	}
}