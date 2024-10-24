package mhmdasabdlh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

public class ModernDialog extends JDialog {

	private JPanel buttonPanel;
	private JLabel messageLabel;
	private JLabel iconLabel;
	private String closeMessage;
	private Color borderColor, panelColor, txtColor;

	// Enum to define icon types
	public enum IconType {
		WARNING, ERROR, INFO, QUESTION
	}

	public ModernDialog(JFrame parent, String closeMessage, IconType iconType) {
		super(parent, "Exit Application", true);
		this.closeMessage = closeMessage; // Store the message
		this.txtColor = Color.BLACK;

		// Customize dialog's look
		this.setUndecorated(true); // Removes the default window frame
		this.setLayout(new BorderLayout());

		// Create icon label
		iconLabel = new JLabel();
		setIcon(iconType); // Set the icon based on the type

		// Apply rounded shape to the dialog
		this.setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 20, 20)); // Rounded corners

		// Background panel with rounded corners and a custom color
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				// Set background color
				g2.setColor(panelColor);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

				// Set border color and thickness
				g2.setColor(borderColor); // Example border color
				g2.setStroke(new BasicStroke(2)); // Example border thickness (3 pixels)
				g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20); // Draw border with small padding
			}
		};
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Add multiline support using HTML in JLabel
		messageLabel = new JLabel("<html><font color='" + getHexColor(txtColor) + "'>"
				+ closeMessage.replace("\n", "<br>") + "</font></html>", JLabel.CENTER);
		messageLabel.setFont(new Font("Arial", Font.BOLD, 16));

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonPanel.setOpaque(false); // Transparent background for buttons

		// Spacing
		iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0)); // Adds space below the icon
		messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0)); // Adds space above and below the message
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0)); // Adds space above the button panel

		panel.add(iconLabel, BorderLayout.NORTH); // Icon on top
		panel.add(messageLabel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(panel);

		// Adjust dialog size based on the message height
		this.pack(); // Adjust size to fit content
		adjustDialogSize();

		// Set position
		this.setLocationRelativeTo(parent);

		// Inside the constructor, after the dialog size and location configuration
		this.setOpacity(0f); // Set initial opacity to 0 (fully transparent)
		Timer fadeInTimer = new Timer(10, null);
		fadeInTimer.addActionListener(e -> {
			float opacity = ModernDialog.this.getOpacity();
			if (opacity < 0.95f) {
				ModernDialog.this.setOpacity(opacity + 0.05f); // Increase opacity gradually
			} else {
				ModernDialog.this.setOpacity(1f); // Increase opacity gradually
				fadeInTimer.stop(); // Stop timer when fully visible
			}
		});
		fadeInTimer.start(); // Start fade-in effect

		// Key listener to close the dialog on Esc key press
		this.getRootPane().registerKeyboardAction(e -> {
			Timer fadeOutTimer = new Timer(10, null);
			fadeOutTimer.addActionListener(e1 -> {
				float opacity = ModernDialog.this.getOpacity();
				if (opacity > 0.05f) {
					ModernDialog.this.setOpacity(opacity - 0.05f); // Decrease opacity gradually
				} else {
					fadeOutTimer.stop(); // Stop timer when fully transparent
					dispose(); // Dispose the dialog
				}
			});
			fadeOutTimer.start(); // Start fade-out effect
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Request focus for the dialog
		this.setFocusableWindowState(true);
		this.requestFocusInWindow();
	}

	// Method to set the appropriate icon
	private void setIcon(IconType iconType) {
		switch (iconType) {
		case WARNING:
			iconLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
			break;
		case ERROR:
			iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
			break;
		case INFO:
			iconLabel.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
			break;
		case QUESTION:
			iconLabel.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
			break;
		}
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
	}

	// Adjusts the dialog's height based on the preferred size of the message label
	private void adjustDialogSize() {
		int messageHeight = messageLabel.getPreferredSize().height;
		int buttonPanelHeight = buttonPanel.getPreferredSize().height;
		int iconHeigh = iconLabel.getPreferredSize().height;

		// Calculate preferred width based on the buttons
		int totalButtonWidth = calculateTotalButtonWidth();
		int messageWidth = messageLabel.getPreferredSize().width;

		int dialogWidth = Math.max(totalButtonWidth, messageWidth) + 60; // Add padding
		int dialogHeight = messageHeight + buttonPanelHeight + iconHeigh + 60; // Add padding for margins

		this.setSize(dialogWidth, dialogHeight);

		// Update rounded shape based on new size
		this.setShape(new RoundRectangle2D.Double(0, 0, dialogWidth, dialogHeight, 20, 20));
	}

	// Method to calculate total width of buttons
	private int calculateTotalButtonWidth() {
		int totalWidth = 0;
		for (Component button : buttonPanel.getComponents()) {
			totalWidth += button.getPreferredSize().width;
		}
		return totalWidth + (buttonPanel.getComponentCount() - 1) * 20; // Adding space between buttons
	}

	// Method to add a main button (e.g., "Yes", "No")
	public void addMainButton(String text, Color color, Runnable action) {
		JButton button = new JButton(text);
		button.setBackground(color);
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.addActionListener(e -> {
			Timer fadeOutTimer = new Timer(10, null);
			fadeOutTimer.addActionListener(e1 -> {
				float opacity = ModernDialog.this.getOpacity();
				if (opacity > 0.05f) {
					ModernDialog.this.setOpacity(opacity - 0.05f); // Decrease opacity gradually
				} else {
					fadeOutTimer.stop(); // Stop timer when fully transparent
					dispose(); // Dispose the dialog
					action.run();
				}
			});
			fadeOutTimer.start(); // Start fade-out effect
		});
		buttonPanel.add(button);
		buttonPanel.revalidate(); // Refresh the button panel to show the new button
		buttonPanel.repaint();
		adjustDialogSize(); // Adjust size when a new button is added
	}

	public void setBorderColor(Color newColor) {
		this.borderColor = newColor;
	}

	public void setColor(Color bgColor) {
		this.panelColor = bgColor;
	}

	public void setTextColor(Color textC) {
		this.txtColor = textC;
		updateLabelText();
	}

	private void updateLabelText() {
		if (txtColor != null && messageLabel != null) {
			messageLabel.setText("<html><font color='" + getHexColor(txtColor) + "'>"
					+ closeMessage.replace("\n", "<br>") + "</font></html>");
		}
	}

	private String getHexColor(Color color) {
		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	}

	// Method to add an extra button (e.g., "Cancel" or other actions)
	public void addExtraButton(String text, Color color, Runnable action) {
		JButton extraButton = new JButton(text);
		extraButton.setBackground(color);
		extraButton.setForeground(Color.WHITE);
		extraButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		extraButton.addActionListener(e -> {
			Timer fadeOutTimer = new Timer(10, null);
			fadeOutTimer.addActionListener(e1 -> {
				float opacity = ModernDialog.this.getOpacity();
				if (opacity > 0.05f) {
					ModernDialog.this.setOpacity(opacity - 0.05f); // Decrease opacity gradually
				} else {
					fadeOutTimer.stop(); // Stop timer when fully transparent
					dispose(); // Dispose the dialog
					action.run();
				}
			});
			fadeOutTimer.start(); // Start fade-out effect
		});
		buttonPanel.add(extraButton);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		adjustDialogSize(); // Adjust size when a new button is added
	}
}
