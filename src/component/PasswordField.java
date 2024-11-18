package mhmdsabdlh.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPasswordFieldUI;

public class PasswordField extends JPasswordField {

	private static final long serialVersionUID = 1L;

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
		repaint();
	}

	private int round = 10;
	private boolean isPasswordVisible = false; // To toggle password visibility
	private JButton toggleButton;
	private JLabel capsLock;
	private final Insets shadowSize = new Insets(2, 5, 8, 5);

	public PasswordField() {
		setUI(new TextUI());
		setOpaque(false);
		setForeground(new Color(80, 80, 80));
		setSelectedTextColor(new Color(255, 255, 255));
		setSelectionColor(new Color(133, 209, 255));
		setBorder(new EmptyBorder(10, 12, 15, 12));
		setBackground(new Color(255, 255, 255));
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				updateCapsLockState();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				updateCapsLockState();
			}

			private void updateCapsLockState() {
				boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				capsLock.setVisible(isCapsLockOn);
			}
		});

		// Create the toggle button
		toggleButton = new JButton() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set a larger font size
				g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25)); // Increase font size
				FontMetrics fm = g2.getFontMetrics();
				String symbol = isPasswordVisible ? "🙈" : "👁️";

				// Calculate centered position for the symbol
				int x = (getWidth() - (shadowSize.left + shadowSize.right) - fm.stringWidth(symbol));
				int y = (getHeight() - shadowSize.top + fm.getAscent()) / 2 - fm.getDescent() + 1;

				// Draw the symbol
				g2.drawString(symbol, x, y);
			}
		};
		toggleButton.setBorderPainted(false);
		toggleButton.setFocusPainted(false);
		toggleButton.setContentAreaFilled(false);
		toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		toggleButton.setPreferredSize(new Dimension(50, 50));
		toggleButton.addActionListener(new ToggleAction());

		// Create the caps lock button
		capsLock = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set a larger font size
				g2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 25)); // Increase font size
				FontMetrics fm = g2.getFontMetrics();
				String symbol = "⇪";

				// Calculate centered position for the symbol
				int x = 10;
				int y = (getHeight() - shadowSize.top + fm.getAscent()) / 2 - fm.getDescent();

				// Draw the symbol
				g2.drawString(symbol, x, y);
			}
		};
		capsLock.setPreferredSize(new Dimension(50, 50));
		capsLock.setVisible(false);

		// Add a layout manager to handle the toggle button's position
		setLayout(new BorderLayout());
		add(toggleButton, BorderLayout.EAST);
		add(capsLock, BorderLayout.WEST);
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

	// Action listener to toggle password visibility
	private class ToggleAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			isPasswordVisible = !isPasswordVisible;
			setEchoChar(isPasswordVisible ? '\0' : '•'); // Show or hide the password
			toggleButton.setText(isPasswordVisible ? "🙈" : "👁️"); // Update button icon
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
	}

	private class TextUI extends BasicPasswordFieldUI {

		// Override this method to remove background or not paint background
		@Override
		protected void paintBackground(Graphics grphcs) {

		}
	}
}
