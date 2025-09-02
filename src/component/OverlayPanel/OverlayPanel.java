package mhmdsabdlh.component.OverlayPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

// Create a custom overlay panel
public class OverlayPanel extends JPanel {
	private float alpha = 0f; // Starting alpha (0% transparency)
	private Color overlayColor = new Color(255, 255, 255); // Default color: white

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(
				new Color(overlayColor.getRed(), overlayColor.getGreen(), overlayColor.getBlue(), (int) (alpha * 255)));
		g2d.fillRect(0, 0, getWidth(), getHeight());
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		repaint(); // Repaint the panel with updated alpha
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setOverlayColor(Color color) {
		this.overlayColor = color; // Update the overlay color
		repaint(); // Repaint the panel with the new color
	}

	public Color getOverlayColor() {
		return this.overlayColor;
	}

	public boolean isShowing() {
		if (alpha > 0)
			return true;
		else
			return false;
	}
}
