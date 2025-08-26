package mhmdsabdlh.dialog;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.formdev.flatlaf.FlatClientProperties;

public class PasswordDialog extends JDialog {

	private JPanel buttonPanel, inPanel;
	private JLabel messageLabel, iconLabel, tryLeft;
	private JPasswordField passwordField;
	private String correctPassword;
	private Color panelColor, txtColor;
	private MessageType messageType = MessageType.CANCEL;
	private JButton okButton;
	private JButton noButton;

	public PasswordDialog(JFrame parent) {
		super(parent, "LOCK", true);

		this.txtColor = Color.BLACK;

		// Customize dialog's look
		setUndecorated(true); // Removes the default window frame
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(400, 300);

		// Apply rounded shape to the dialog
		setShape(new RoundRectangle2D.Double(0, 0, 400, 300, 10, 10)); // Rounded corners

		// Background panel with rounded corners and a custom color
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set background color
				g2.setColor(panelColor);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

				// Set border color and thickness
				g2.setColor(txtColor); // Example border color
				g2.setStroke(new BasicStroke(1)); // Example border thickness (3 pixels)
				g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10); // Draw border with small padding
			}
		};
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Create icon label
		iconLabel = new JLabel();
		iconLabel.setIcon(new ImageIcon(getClass().getResource("lock.png")));
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Add panel 1
		inPanel = new JPanel();
		inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.Y_AXIS));
		inPanel.setOpaque(false);

		messageLabel = new JLabel("PASSWORD");
		messageLabel.setPreferredSize(new Dimension(400, 60));
		messageLabel.setForeground(txtColor);
		messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(300, 40));
		passwordField.setMinimumSize(new Dimension(300, 40));
		passwordField.setMaximumSize(new Dimension(300, 40));
		passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
		passwordField.setHorizontalAlignment(0);
		passwordField.setForeground(Color.black);
		passwordField.putClientProperty(FlatClientProperties.OUTLINE, Color.black);
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if ((ke.getKeyCode() == KeyEvent.VK_C) && ((ke.getModifiers() & InputEvent.CTRL_MASK) != 0)) {
					parent.setState(Frame.ICONIFIED); // Minimize the main frame
				} else if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					getRootPane().setDefaultButton(okButton);
				else if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
					cancelAction();
			}
		});
		passwordField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkPassword();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkPassword();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkPassword();
			}

			private void checkPassword() {
				// Get the current input from the password field
				String enteredPassword = new String(passwordField.getPassword());

				// Check if it matches the correct password
				if (enteredPassword.equals(correctPassword)) {
					startFadeOut(() -> dispose());
				}
			}
		});

		tryLeft = new JLabel("TRY LEFT: 5");
		tryLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
		tryLeft.setFont(new Font("Arial", Font.ITALIC, 14)); // NOI18N
		tryLeft.setForeground(txtColor);

		inPanel.add(Box.createVerticalGlue());
		inPanel.add(messageLabel);
		inPanel.add(Box.createVerticalStrut(8));
		inPanel.add(passwordField);
		inPanel.add(Box.createVerticalStrut(8));
		inPanel.add(tryLeft);
		inPanel.add(Box.createVerticalGlue());

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonPanel.setOpaque(false); // Transparent background for buttons

		okButton = new JButton("OK");
		String colors[] = new String[] { "#1EA97C", "#1EA97C" };
		okButton.putClientProperty(FlatClientProperties.STYLE,
				"" + "arc:999;" + "margin:3,33,3,33;" + "borderWidth:1;" + "focusWidth:0;" + "innerFocusWidth:0.5;"
						+ "background:null;" + "[light]borderColor:" + colors[0] + ";" + "[dark]borderColor:"
						+ colors[1] + ";" + "[light]focusedBorderColor:" + colors[0] + ";" + "[dark]focusedBorderColor:"
						+ colors[1] + ";" + "[light]focusColor:" + colors[0] + ";" + "[dark]focusColor:" + colors[1]
						+ ";" + "[light]hoverBorderColor:" + colors[0] + ";" + "[dark]hoverBorderColor:" + colors[1]
						+ ";" + "[light]foreground:" + colors[0] + ";" + "[dark]foreground:" + colors[1] + ";");

		okButton.addActionListener(e -> okAction());

		noButton = new JButton("NO");
		String noColors[] = new String[] { "#FF5757", "#FF5757" };
		noButton.putClientProperty(FlatClientProperties.STYLE,
				"" + "arc:999;" + "margin:3,33,3,33;" + "borderWidth:1;" + "focusWidth:0;" + "innerFocusWidth:0.5;"
						+ "background:null;" + "[light]borderColor:" + noColors[0] + ";" + "[dark]borderColor:"
						+ noColors[1] + ";" + "[light]focusedBorderColor:" + noColors[0] + ";"
						+ "[dark]focusedBorderColor:" + noColors[1] + ";" + "[light]focusColor:" + noColors[0] + ";"
						+ "[dark]focusColor:" + noColors[1] + ";" + "[light]hoverBorderColor:" + noColors[0] + ";"
						+ "[dark]hoverBorderColor:" + noColors[1] + ";" + "[light]foreground:" + noColors[0] + ";"
						+ "[dark]foreground:" + noColors[1] + ";");
		noButton.addActionListener(e -> cancelAction());

		Dimension size = okButton.getPreferredSize();
		Dimension size2 = noButton.getPreferredSize();

		// Use the largest preferred size
		int width = Math.max(size.width, size2.width);
		int height = Math.max(size.height, size2.height);
		Dimension maxSize = new Dimension(width, height);

		okButton.setPreferredSize(maxSize);
		noButton.setPreferredSize(maxSize);

		buttonPanel.add(okButton);
		buttonPanel.add(noButton);

		// Spacing
		iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Adds space below the icon
		inPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Adds space above and below the message
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Adds space above the button panel

		panel.add(iconLabel, BorderLayout.NORTH); // Icon on top
		panel.add(inPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(panel);

		// Set position
		this.setLocationRelativeTo(parent);

		// Key listener to close the dialog on Esc key press
		this.getRootPane().registerKeyboardAction(e -> {
			cancelAction();
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
	}

	public void showMessage(String closeMessage, String tLeft) {
		messageLabel.setText(closeMessage);
		tryLeft.setText(tLeft);
		passwordField.setText("");
		setOpacity(0f);
		Timer fadeInTimer = new Timer(20, new ActionListener() {
			float opacity = 0f;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity += 0.1f;
				setOpacity(Math.min(opacity, 1.0f));
				if (opacity >= 1.0f)
					((Timer) e.getSource()).stop();
				repaint();
			}
		});
		fadeInTimer.start();
		setVisible(true);
	}

	public void setFillColor(Color bgColor) {
		this.panelColor = bgColor;
	}

	public void setTextColor(Color textC) {
		this.txtColor = textC;
		tryLeft.setForeground(txtColor);
		messageLabel.setForeground(txtColor);
	}

	public void setPassword(String password) {
		this.correctPassword = password;
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public static enum MessageType {
		CANCEL, OK
	}

	public MessageType getMessageType() {
		return messageType;
	}

	private void cancelAction() {
		messageType = MessageType.CANCEL;
		dispose();
	}

	private void okAction() {
		messageType = MessageType.OK;
		dispose();
	}

	public void autoUnlock() {
		passwordField.setText(correctPassword);
		okAction();
	}

	private void startFadeOut(Runnable action) {
		Timer fadeOutTimer = new Timer(20, new ActionListener() {
			float opacity = 1.0f;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity -= 0.1f;
				setOpacity(Math.max(opacity, 0.0f));
				if (opacity <= 0.0f) {
					((Timer) e.getSource()).stop();
					dispose();
					action.run();
				}
				repaint();
			}
		});
		fadeOutTimer.start();
	}
}
