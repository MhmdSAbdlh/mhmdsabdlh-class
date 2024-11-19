package mhmdsabdlh.text;

import javax.swing.*;
import java.awt.*;

public class RotatedLabel extends JLabel {
	private double rotationDegree; // Rotation degree

	public RotatedLabel(String text, double rotationDegree) {
		super(text);
		this.rotationDegree = rotationDegree;
		setPreferredSize(new Dimension(200, 200)); // Adjust dimensions as needed
	}

	public void setRotationDegree(double degree) {
		this.rotationDegree = degree;
		repaint(); // Redraw the label with the new rotation
	}

	public double getRotationDegree() {
		return rotationDegree;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Paint background if opaque
		if (isOpaque()) {
			g2d.setColor(getBackground());
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}

		// Rotate the text
		double radians = Math.toRadians(rotationDegree);
		g2d.rotate(radians, getWidth() / 2.0, getHeight() / 2.0);

		// Center the text
		FontMetrics fm = g2d.getFontMetrics();
		int textWidth = fm.stringWidth(getText());
		int textHeight = fm.getHeight();
		int x = (getWidth() - textWidth) / 2;
		int y = (getHeight() + textHeight) / 2 - fm.getDescent();

		// Draw the text
		g2d.setColor(getForeground());
		g2d.drawString(getText(), x, y);

		g2d.dispose();
	}

}