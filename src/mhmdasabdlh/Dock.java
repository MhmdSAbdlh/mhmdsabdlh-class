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

		item1 = new JButton(ImageEffect.getScaledImage(img1.getImage(), h, h));
		item2 = new JButton(ImageEffect.getScaledImage(img2.getImage(), h, h));
		item3 = new JButton(ImageEffect.getScaledImage(img3.getImage(), h, h));
		item4 = new JButton(ImageEffect.getScaledImage(img4.getImage(), h, h));

		item1.setContentAreaFilled(false);
		item1.setBorderPainted(false);
		item1.setFocusPainted(false);
		item2.setContentAreaFilled(false);
		item2.setBorderPainted(false);
		item2.setFocusPainted(false);
		item3.setContentAreaFilled(false);
		item3.setBorderPainted(false);
		item3.setFocusPainted(false);
		item4.setContentAreaFilled(false);
		item4.setBorderPainted(false);
		item4.setFocusPainted(false);

		item1.setSize(h, h);
		item2.setSize(h, h);
		item3.setSize(h, h);
		item4.setSize(h, h);

		item1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				item1.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img1.getImage(), h, h).getImage(), 0.35f));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				item1.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img1.getImage(), h, h).getImage(), 1f));
			}
		});
		item2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				item2.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img2.getImage(), h, h).getImage(), 0.35f));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				item2.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img2.getImage(), h, h).getImage(), 1f));
			}
		});
		item3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				item3.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img3.getImage(), h, h).getImage(), 0.35f));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				item3.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img3.getImage(), h, h).getImage(), 1f));
			}
		});
		item4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				item4.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img4.getImage(), h, h).getImage(), 0.35f));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				item4.setIcon(
						ImageEffect.changeOpacity(ImageEffect.getScaledImage(img4.getImage(), h, h).getImage(), 1f));
			}
		});

		this.add(item1);
		this.add(item2);
		this.add(item3);
		this.add(item4);
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
		item1.setIcon(ImageEffect.getScaledImage(img1.getImage(), h, h));
		item2.setIcon(ImageEffect.getScaledImage(img2.getImage(), h, h));
		item3.setIcon(ImageEffect.getScaledImage(img3.getImage(), h, h));
		item4.setIcon(ImageEffect.getScaledImage(img4.getImage(), h, h));
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