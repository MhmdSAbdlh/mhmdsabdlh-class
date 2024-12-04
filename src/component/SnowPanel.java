package mhmdsabdlh.component;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnowPanel extends JPanel {
	private final ArrayList<Snowflake> snowflakes = new ArrayList<>();
	private final Random random = new Random();
	private final Timer timer;
	private Color fillColor, borderColor;
	private Shape shape;

	public enum Shape {
		CIRCLE, STAR, BALLON, MOON, FRACTAL, CHRISTMAS_TREE, CHRISTMAS_STAR, BLOOD, FIREWORK, CONFETTI
	}

	public SnowPanel() {
		setOpaque(false); // Ensure the panel is transparent
		this.fillColor = Color.WHITE;
		this.borderColor = Color.black;
		this.shape = Shape.CIRCLE;
		// Create a timer to update snowflakes
		timer = new Timer(30, e -> {
			for (Snowflake snowflake : snowflakes) {
				snowflake.update();
			}
			repaint();
		});
	}

	public void setFillColor(Color col) {
		this.fillColor = col;
		int red = 255 - col.getRed();
		int green = 255 - col.getGreen();
		int blue = 255 - col.getBlue();
		this.borderColor = new Color(red, green, blue);
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public void startSnow() {
		if (!timer.isRunning()) {
			// Generate snowflakes if not already present
			if (snowflakes.isEmpty()) {
				for (int i = 0; i < 100; i++) {
					Snowflake snowflake = new Snowflake(random.nextInt(getWidth()), random.nextInt(getHeight()));
					if (shape == Shape.BALLON) {
						// Assign random color only for balloons
						snowflake.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
					}
					snowflakes.add(snowflake);
				}
			}
			timer.start();
		}
	}

	public void stopNow() {
	    if (timer != null && timer.isRunning()) {
	        timer.stop(); // Stop the timer
	    }
	    snowflakes.clear(); // Remove all snowflakes
	    repaint(); // Redraw the panel to reflect the changes
	}

	private void drawStar(Graphics2D g2d, int x, int y, int size, double rotation) {
		int numPoints = 5; // Points for the star
		double outerRadius = size; // Outer radius
		double innerRadius = size * 0.5; // Inner radius

		// Arrays for points
		int[] xPoints = new int[numPoints * 2];
		int[] yPoints = new int[numPoints * 2];

		for (int i = 0; i < numPoints * 2; i++) {
			double angle = Math.PI / numPoints * i - Math.PI / 2; // Rotate star upwards
			double radius = (i % 2 == 0) ? outerRadius : innerRadius;
			xPoints[i] = (int) (Math.cos(angle) * radius);
			yPoints[i] = (int) (Math.sin(angle) * radius);
		}

		// Save the original transform
		g2d.translate(x, y); // Move to the center of the star
		g2d.rotate(rotation); // Apply the rotation

		// Draw the star
		g2d.fillPolygon(xPoints, yPoints, xPoints.length);

		// Restore the original transform
		g2d.rotate(-rotation);
		g2d.translate(-x, -y);
	}

	private void drawBalloon(Graphics2D g2d, int x, int y, int size, Color color) {
		int balloonHeight = (int) (size * 1.5); // Scale height
		int stringLength = (int) (size * 1.2); // Scale string length

		// Draw the balloon (oval)
		g2d.setColor(color);
		g2d.fillOval(x, y, size, balloonHeight);

		// Draw the string
		g2d.setColor(Color.BLACK); // Strings are black
		g2d.setStroke(new BasicStroke(2)); // Adjust thickness of the string
		g2d.drawLine(x + size / 2, y + balloonHeight, x + size / 2, y + balloonHeight + stringLength);
	}

	private void drawCrescentMoon(Graphics2D g2d, int x, int y, int size) {
		// Create a transparent off-screen image
		BufferedImage moonImage = new BufferedImage(size * 2, size * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2dImage = moonImage.createGraphics();

		// Enable anti-aliasing
		g2dImage.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the full outer circle (moon)
		g2dImage.setColor(fillColor);
		g2dImage.fillOval(0, 0, size, size);

		// Set composite for transparency and draw the inner circle (cutout)
		g2dImage.setComposite(AlphaComposite.Clear);
		g2dImage.fillOval(size / 4, 0, size, size);

		// Dispose the off-screen graphics
		g2dImage.dispose();

		// Draw the crescent moon image onto the panel
		g2d.drawImage(moonImage, x, y, null);
	}

	private void drawFractalSnowflake(Graphics2D g2d, int x, int y, int size) {
		g2d.setStroke(new BasicStroke(2));
		for (int i = 0; i < 6; i++) {
			double angle = Math.PI / 3 * i;
			int x1 = (int) (x + size * Math.cos(angle));
			int y1 = (int) (y + size * Math.sin(angle));
			g2d.drawLine(x, y, x1, y1);
		}
	}

	private void drawChristmasTree(Graphics2D g2d, int x, int y, int size) {
		// Draw the tree using triangles
		g2d.setColor(Color.GREEN);
		g2d.fillPolygon(new int[] { x, x - size, x + size }, new int[] { y - size, y + size, y + size }, 3);
		g2d.fillPolygon(new int[] { x, x - size / 2, x + size / 2 }, new int[] { y - size / 2, y + size, y + size }, 3);

		// Draw the trunk
		g2d.setColor(new Color(139, 69, 19)); // Brown color for the trunk
		g2d.fillRect(x - size / 4, y + size, size / 2, size / 2);
	}

	private void drawColoredSnowflake(Graphics2D g2d, int x, int y, int size, double rotation) {
		g2d.setColor(Color.RED);
		g2d.fillOval(x, y, size, size); // Draw snowflake base with red

		g2d.setColor(Color.GREEN);
		drawStar(g2d, x, y, size, rotation); // Green star on top for a holiday touch
	}

	private void drawBlood(Graphics2D g2d, int x, int y, int size) {
		g2d.setColor(Color.RED); // Blood red color

		// Blood droplet shape (ellipse-like)
		g2d.fillOval(x, y, size, size * 2); // Draw the blood drop shape

		// Optional: Add a slightly darker outline to make it more realistic
		g2d.setColor(fillColor);
		g2d.drawOval(x, y, size, size * 2);
	}

	private void drawConfetti(Graphics2D g2d, int x, int y, int size) {
		g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // Random confetti
																								// colors
		g2d.fillRect(x, y, size, size); // Draw small rectangle as confetti
	}

	private void drawFirework(Graphics2D g2d, int x, int y, int size) {
		int numPoints = 12; // Number of points in the explosion
		int radius = size;

		for (int i = 0; i < numPoints; i++) {
			double angle = Math.PI * 2 * i / numPoints;
			int x2 = (int) (x + Math.cos(angle) * radius);
			int y2 = (int) (y + Math.sin(angle) * radius);

			g2d.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // Random colors for
																									// fireworks
			g2d.drawLine(x, y, x2, y2);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw snowflakes
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setStroke(new BasicStroke(0.5f));

		for (Snowflake snowflake : snowflakes) {
			if (shape == Shape.CIRCLE) {
				g2d.setColor(fillColor);
				g2d.fillOval(snowflake.x, snowflake.y, snowflake.size, snowflake.size);
				// Draw the border (outer color)
				g2d.setColor(borderColor); // Define borderColor as a Color variable
				g2d.drawOval(snowflake.x, snowflake.y, snowflake.size, snowflake.size);
			} else if (shape == Shape.STAR) {
				g2d.setColor(fillColor);
				drawStar(g2d, snowflake.x, snowflake.y, snowflake.size * 2, snowflake.rotation);
			} else if (shape == Shape.BALLON) {
				g2d.setColor(snowflake.color); // Use unique color for balloons
				drawBalloon(g2d, snowflake.x, snowflake.y, snowflake.size * 2, snowflake.color);
			} else if (shape == Shape.MOON) {
				g2d.setColor(fillColor); // Set the fill color for the moon
				drawCrescentMoon(g2d, snowflake.x, snowflake.y, snowflake.size * 4);
			} else if (shape == Shape.CHRISTMAS_TREE) {
				g2d.setColor(fillColor);
				drawChristmasTree(g2d, snowflake.x, snowflake.y, snowflake.size * 2);
			} else if (shape == Shape.CHRISTMAS_STAR) {
				g2d.setColor(fillColor);
				drawColoredSnowflake(g2d, snowflake.x, snowflake.y, snowflake.size * 2, snowflake.rotation);
			} else if (shape == Shape.FRACTAL) {
				g2d.setColor(fillColor);
				drawFractalSnowflake(g2d, snowflake.x, snowflake.y, snowflake.size);
			} else if (shape == Shape.BLOOD) {
				g2d.setColor(fillColor);
				drawBlood(g2d, snowflake.x, snowflake.y, snowflake.size);
			} else if (shape == Shape.FIREWORK) {
				g2d.setColor(fillColor);
				drawFirework(g2d, snowflake.x, snowflake.y, snowflake.size * 2);
			} else if (shape == Shape.CONFETTI) {
				g2d.setColor(fillColor);
				drawConfetti(g2d, snowflake.x, snowflake.y, snowflake.size);
			}
		}
	}

	private class Snowflake {
		int x, y, size, speed;
		double dx; // Horizontal movement speed
		Color color; // For balloons only
		double rotation; // Rotation angle in radians
		double rotationSpeed; // Speed of rotation

		public Snowflake(int x, int y) {
			Random random = new Random();
			this.x = x;
			this.y = y;
			this.size = random.nextInt(5) + 2; // Random size between 2 and 7
			this.speed = random.nextInt(3) + 1; // Random vertical speed between 1 and 3
			this.dx = random.nextDouble() * 2 - 1; // Random horizontal speed (-1 to 1)
			this.rotation = random.nextDouble() * 2 * Math.PI; // Random initial rotation (0 to 2π radians)
			this.rotationSpeed = random.nextDouble() * 0.1 - 0.05;
			this.color = null; // Default color, used for balloons only
		}

		public void update() {
			y += speed; // Continue falling vertically
			rotation += rotationSpeed; // Update rotation

			// Update horizontal movement only if the shape is CIRCLE
			if (shape == Shape.CIRCLE || shape == Shape.FRACTAL) {
				x += dx; // Update horizontal position for circles

				// Add a random horizontal motion to simulate drifting (like wind)
				if (random.nextInt(10) == 0) { // Chance of changing direction every frame
					dx = random.nextDouble() * 2 - 1; // Randomize the horizontal speed (-1 to 1)
				}

				// Horizontal boundary handling for circles
				if (x < 0) {
					x = 0; // Prevent going off the left edge
					dx = random.nextDouble() * 2 - 1; // Assign a new random direction
				} else if (x + size > getWidth()) {
					x = getWidth() - size; // Prevent going off the right edge
					dx = random.nextDouble() * 2 - 1; // Assign a new random direction
				}
			}

			// Update for vertical movement (same for all shapes)
			if (y > getHeight()) {
				y = 0;
				x = new Random().nextInt(getWidth());
			}
		}

	}

}