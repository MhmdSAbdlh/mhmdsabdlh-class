package mhmdasabdlh;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Dock extends JPanel {
	int cornerRadius;
	private JButton item1, item2, item3, item4;
	private int h = 40;
	private ImageIcon img1, img2, img3, img4;
	private Color dockColor;

	public Dock(int cornerRadius) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.cornerRadius = cornerRadius;
		setOpaque(false); // Make the panel transparent
		// Set a default color
		this.dockColor = Color.white;

		img1 = new ImageIcon();
		img2 = new ImageIcon();
		img3 = new ImageIcon();
		img4 = new ImageIcon();

		item1 = new JButton();
		item2 = new JButton();
		item3 = new JButton();
		item4 = new JButton();

		item1.setSize(h, h);
		item2.setSize(h, h);
		item3.setSize(h, h);
		item4.setSize(h, h);

		this.add(item1);
		this.add(item2);
		this.add(item3);
		this.add(item4);
	}

	public void setMouseListener() {

		opacityEffect(item1, ImageEffect.getScaledImage(img1.getImage(), h, h));
		opacityEffect(item2, ImageEffect.getScaledImage(img2.getImage(), h, h));
		opacityEffect(item3, ImageEffect.getScaledImage(img3.getImage(), h, h));
		opacityEffect(item4, ImageEffect.getScaledImage(img4.getImage(), h, h));
	}

	/* Fade opacity */
	private void applyFadeEffect(JButton button, ImageIcon darkIcon, float startOpacity, float endOpacity, int delay) {
		float step = (endOpacity - startOpacity) / 50;
		final float[] opacity = { startOpacity };

		javax.swing.Timer timer = new javax.swing.Timer(delay, null);
		timer.addActionListener(e -> {
			// Adjust opacity
			opacity[0] += step;
			if ((step > 0 && opacity[0] >= endOpacity) || (step < 0 && opacity[0] <= endOpacity)) {
				opacity[0] = endOpacity;
				timer.stop();
			}

			// Update button icon with the new opacity
			button.setIcon(ImageEffect.changeOpacity(darkIcon.getImage(), opacity[0]));
		});

		timer.start();
	}

	public void opacityEffect(JButton button, ImageIcon notIcon) {
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				applyFadeEffect(button, notIcon, 0.7f, 1.0f, 5);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				applyFadeEffect(button, notIcon, 1.0f, 0.7f, 5);
			}
		});
	}

	public void setIcon(ImageIcon icon, ImageIcon icon2, ImageIcon icon3, ImageIcon icon4) {
		img1 = icon;
		img2 = icon2;
		img3 = icon3;
		img4 = icon4;
	}

	public void setToolText(String text1, String text2, String text3, String text4) {
		item1.setToolTipText(" " + text1 + " ");
		item2.setToolTipText(" " + text2 + " ");
		item3.setToolTipText(" " + text3 + " ");
		item4.setToolTipText(" " + text4 + " ");
	}

	public void setHyW(int height) {
		this.h = height;

		item1.setSize(h, h);
		item2.setSize(h, h);
		item3.setSize(h, h);
		item4.setSize(h, h);
		item1.setIcon(ImageEffect.changeOpacity(ImageEffect.getScaledImage(img1.getImage(), h, h).getImage(),0.7f));
		item2.setIcon(ImageEffect.changeOpacity(ImageEffect.getScaledImage(img2.getImage(), h, h).getImage(),0.7f));
		item3.setIcon(ImageEffect.changeOpacity(ImageEffect.getScaledImage(img3.getImage(), h, h).getImage(),0.7f));
		item4.setIcon(ImageEffect.changeOpacity(ImageEffect.getScaledImage(img4.getImage(), h, h).getImage(),0.7f));
	}

	public void addActionLis1(ActionListener al) {
		item1.addActionListener(al);
	}

	public void addActionLis2(ActionListener al) {
		item2.addActionListener(al);
	}

	public void addActionLis3(ActionListener al) {
		item3.addActionListener(al);
	}

	public void addActionLis4(ActionListener al) {
		item4.addActionListener(al);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		Graphics2D g2d = (Graphics2D) g.create();

		// Set rendering hints for smoother graphics
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the rounded rectangle with the specified corner radius
		g2d.setColor(dockColor); // You can choose any color you prefer
		g2d.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius));

		g2d.dispose();
	}

	// Method to dynamically change the color
	public void setDockColor(Color newColor) {
		this.dockColor = newColor; // Update the color
		repaint(); // Repaint the component to reflect the color change
	}
}