package mhmdasabdlh;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class ImageEffect {
	
	// Method to change the opacity of an image
	public static Image changeOpacity(Image srcImage, float opacity) {
		BufferedImage bufferedImage = new BufferedImage(srcImage.getWidth(null), srcImage.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity)); // Set opacity level
		g2.drawImage(srcImage, 0, 0, null);
		g2.dispose();
		return bufferedImage;
	}


	public static Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

}
