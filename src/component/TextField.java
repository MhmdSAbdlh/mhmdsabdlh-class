package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTextFieldUI;

public class TextField extends JTextField {

	private static final long serialVersionUID = 1L;

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
		repaint();
	}

	private int round = 10;
	private final Insets shadowSize = new Insets(2, 5, 8, 5);

	public TextField() {
		setUI(new TextUI());
		setOpaque(false);
		setForeground(new Color(80, 80, 80));
		setSelectedTextColor(new Color(255, 255, 255));
		setSelectionColor(new Color(133, 209, 255));
		setBorder(new EmptyBorder(10, 12, 15, 12));
		setBackground(new Color(255, 255, 255));
	}

	@Override
	protected void paintComponent(Graphics grphcs) {
		Graphics2D g2 = (Graphics2D) grphcs.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double width = getWidth() - (shadowSize.left + shadowSize.right);
		double height = getHeight() - (shadowSize.top + shadowSize.bottom);
		double x = shadowSize.left;
		double y = shadowSize.top;

		// Create Background Color
		g2.setColor(getBackground());
		Area area = new Area(new RoundRectangle2D.Double(x, y, width, height, round, round));
		g2.fill(area);

		// Draw Border
		if (getBorder() != null) {
			g2.setColor(Color.BLACK); // Customize border color
			g2.setStroke(new java.awt.BasicStroke(2)); // Customize border thickness
			g2.draw(new RoundRectangle2D.Double(x, y, width, height, round, round));
		}

		g2.dispose();
		super.paintComponent(grphcs);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

	private class TextUI extends BasicTextFieldUI {

		// Override this method to remove background or not paint background
		@Override
		protected void paintBackground(Graphics grphcs) {

		}
	}
}
