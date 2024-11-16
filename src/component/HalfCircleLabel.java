package mhmdsabdlh.component;
import javax.swing.*;
import java.awt.*;

public class HalfCircleLabel extends JLabel {

	private Color circleColor;

	public HalfCircleLabel(String text) {
		super(text); // Set the text of the JLabel
		setHorizontalAlignment(CENTER); // Center the text in the JLabel
		setVerticalAlignment(CENTER);

		// Set a default color
		circleColor = Color.BLUE;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// Cast the Graphics object to Graphics2D for more control
		Graphics2D g2d = (Graphics2D) g;

		// Enable anti-aliasing for smoother edges
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Get the width and height of the component
		int width = getWidth();
		int height = getHeight();

		// Set the background color for the half-circle
		g2d.setColor(circleColor); // You can choose any color you prefer
		g2d.fillArc(0, -height, width, height * 2, 180, 180); // Draw a bottom half-circle

		// Now draw the text (call the super method to handle text rendering)
		super.paintComponent(g);
	}

	// Method to dynamically change the color
	public void setCircleColor(Color newColor) {
		this.circleColor = newColor; // Update the color
		repaint(); // Repaint the component to reflect the color change
	}

	@Override
	public Dimension getPreferredSize() {
		// Set preferred size for the half-circle, adjust the height to half the width
		// for the effect
		return new Dimension(200, 100); // Width is 200, height is 100 (half-circle)
	}
}
