package mhmdasabdlh;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Dock extends JPanel {
	int cornerRadius;
	private JButton item1, item2, item3;
	private int h = 40;
	private ImageIcon img1, img2, img3;

	public Dock(int cornerRadius, String btn1, String btn2, String btn3) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.cornerRadius = cornerRadius;
		setOpaque(false); // Make the panel transparent

		img1 = new ImageIcon(getClass().getResource(btn1 + ".png"));
		img2 = new ImageIcon(getClass().getResource(btn2 + ".png"));
		img3 = new ImageIcon(getClass().getResource(btn3 + ".png"));

		item1 = new JButton(new ImageIcon(getScaledImage(img1.getImage(), h, h)));
		item2 = new JButton(new ImageIcon(getScaledImage(img2.getImage(), h, h)));
		item3 = new JButton(new ImageIcon(getScaledImage(img3.getImage(), h, h)));

		item1.setContentAreaFilled(false);
		item1.setBorderPainted(false);
		item1.setFocusPainted(false);
		item2.setContentAreaFilled(false);
		item2.setBorderPainted(false);
		item2.setFocusPainted(false);
		item3.setContentAreaFilled(false);
		item3.setBorderPainted(false);
		item3.setFocusPainted(false);

		item1.setSize(h, h);
		item2.setSize(h, h);
		item3.setSize(h, h);

		item1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				for (int i = 0; i < 5; i++) {
					item1.setLocation(item1.getX(), item1.getY() - i);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				for (int i = 0; i < 5; i++) {
					item1.setLocation(item1.getX(), item1.getY() + i);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
			}
		});
		item2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				for (int i = 0; i < 5; i++) {
					item2.setLocation(item2.getX(), item2.getY() - i);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				for (int i = 0; i < 5; i++) {
					item2.setLocation(item2.getX(), item2.getY() + i);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
			}
		});
		item3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				for (int i = 0; i < 5; i++) {
					item3.setLocation(item3.getX(), item3.getY() - i);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				for (int i = 0; i < 5; i++) {
					item3.setLocation(item3.getX(), item3.getY() + i);
					try {
						Thread.sleep(25);
					} catch (InterruptedException e1) {
					}
				}
			}
		});

		this.add(item1);
		this.add(item2);
		this.add(item3);
	}

	public void setHyW(int height) {
		this.h = height;

		item1.setSize(h, h);
		item2.setSize(h, h);
		item3.setSize(h, h);
		item1.setIcon(new ImageIcon(getScaledImage(img1.getImage(), h, h)));
		item2.setIcon(new ImageIcon(getScaledImage(img2.getImage(), h, h)));
		item3.setIcon(new ImageIcon(getScaledImage(img3.getImage(), h, h)));
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

	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
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
		g2d.setColor(getBackground());
		g2d.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, cornerRadius, cornerRadius));

		g2d.dispose();
	}
}
