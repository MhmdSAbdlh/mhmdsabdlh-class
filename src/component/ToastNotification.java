package mhmdsabdlh.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

public class ToastNotification {
	private JWindow toast;
	private JLabel messageLabel;
	private Timer timer;
	private JPanel panel;
	private Color panelColor, txtColor;

	public ToastNotification(JFrame parent, String message) {
		toast = new JWindow();
		toast.setSize(parent.getWidth() / 5, 70);
		toast.setLocationRelativeTo(null);
		toast.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - parent.getWidth() / 5) / 2, 50);
		toast.setShape(new RoundRectangle2D.Double(0, 0, parent.getWidth() / 5, 70, 20, 20));
		toast.setCursor(new Cursor(Cursor.HAND_CURSOR));

		panel = new JPanel(new BorderLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set background color
				g2.setColor(panelColor);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

				// Set border color and thickness
				g2.setColor(txtColor); // Example border color
				g2.setStroke(new BasicStroke(2)); // Example border thickness (3 pixels)
				g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20); // Draw border with small padding
			}
		};
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
		iconLabel.setForeground(Color.WHITE);

		messageLabel = new JLabel("<html><b><center>" + message + "</center></b></html>");
		messageLabel.setForeground(txtColor);
		messageLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

		panel.add(iconLabel, BorderLayout.WEST);
		panel.add(messageLabel, BorderLayout.CENTER);

		toast.add(panel);
		toast.setAlwaysOnTop(true);

		toast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Runtime.getRuntime().exec("C:\\Windows\\System32\\wfs.exe");
				} catch (IOException e1) {
				}
				toast.dispose();
			}
		});

		timer = new Timer(3000, e -> toast.dispose());
		timer.setRepeats(false);
	}

	public void setColor(Color bgColor) {
		this.panelColor = bgColor;
	}

	public void setTextColor(Color textC) {
		this.txtColor = textC;
		messageLabel.setForeground(txtColor);
	}

	public void show() {
		toast.setVisible(true);
		timer.start();
	}
}