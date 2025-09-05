package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

import javax.swing.JLabel;

public class BorderLabel extends JLabel {
	private Color shadowColor;
	private float shadowOffsetX;
	private float shadowOffsetY;

	public BorderLabel(String text) {
		this(text, Color.BLACK, 2.0f, 2.0f);
	}

	public BorderLabel(String text, Color shadowColor, float shadowOffsetX, float shadowOffsetY) {
		super(text);
		this.shadowColor = shadowColor;
		this.shadowOffsetX = shadowOffsetX;
		this.shadowOffsetY = shadowOffsetY;
		setForeground(Color.WHITE); // Default text color
		setOpaque(false); // Transparent background
		setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		String text = getText();
		if (text == null || text.isEmpty()) {
			g2d.dispose();
			return;
		}

		// Get font and metrics
		Font font = getFont();
		FontRenderContext frc = g2d.getFontRenderContext();
		TextLayout textLayout = new TextLayout(text, font, frc);

		// Calculate position to center the text
		Rectangle bounds = textLayout.getBounds().getBounds();
		int x = (getWidth() - bounds.width) / 2 - bounds.x;
		int y = (getHeight() - bounds.height) / 2 - bounds.y;

		// Draw the shadow
		g2d.setColor(shadowColor);
		AffineTransform.getTranslateInstance(x + shadowOffsetX, y + shadowOffsetY);
		textLayout.draw(g2d, (float) (x + shadowOffsetX), (float) (y + shadowOffsetY));

		// Draw the main text
		g2d.setColor(getForeground());
		textLayout.draw(g2d, x, y);

		g2d.dispose();
	}

	// Setters for customization
	public void setShadowColor(Color shadowColor) {
		this.shadowColor = shadowColor;
		repaint();
	}

	public void setShadowOffsetX(float shadowOffsetX) {
		this.shadowOffsetX = shadowOffsetX;
		repaint();
	}

	public void setShadowOffsetY(float shadowOffsetY) {
		this.shadowOffsetY = shadowOffsetY;
		repaint();
	}
}