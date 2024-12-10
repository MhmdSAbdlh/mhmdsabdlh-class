package mhmdsabdlh.images;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import javax.swing.Icon;
import javax.swing.ImageIcon;

 public class GlowingIcon implements Icon {
	private final ImageIcon originalIcon;
	private final int glowRadius;

	public GlowingIcon(ImageIcon originalIcon, int glowRadius) {
		this.originalIcon = originalIcon;
		this.glowRadius = glowRadius;
	}

	public ImageIcon getOriginalIcon() {
		return originalIcon;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int width = getIconWidth();
		int height = getIconHeight();

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the original icon's image with a glow effect
		g2d.drawImage(originalIcon.getImage(), glowRadius, glowRadius, null);

		// Create a mask based on the alpha channel
		BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D maskG2d = mask.createGraphics();
		maskG2d.drawImage(originalIcon.getImage(), glowRadius, glowRadius, null);
		maskG2d.dispose();

		// Apply the glow effect using the mask
		float[] kernel = { 0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f }; // Adjust the kernel values
		ConvolveOp convolveOp = new ConvolveOp(new Kernel(3, 3, kernel));
		BufferedImage glowImage = convolveOp.filter(mask, null);

		g2d.drawImage(glowImage, 0, 0, null);
		g2d.dispose();

		g.drawImage(image, x, y, null);
	}

	@Override
	public int getIconWidth() {
		return originalIcon.getIconWidth() + 2 * glowRadius;
	}

	@Override
	public int getIconHeight() {
		return originalIcon.getIconHeight() + 2 * glowRadius;
	}
}
 