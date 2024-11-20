package mhmdsabdlh.dialog;

import javax.swing.*;
import javax.swing.border.LineBorder;

import mhmdsabdlh.component.RoundButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class ModernDialog extends JDialog {

	private JPanel buttonPanel;
	private JLabel messageLabel;
	private JLabel iconLabel;
	private String closeMessage;
	private Color borderColor, panelColor, txtColor;
	private OverlayPanel overlay;
	private JFrame superF;

	// Enum to define icon types
	public enum IconType {
		WARNING, ERROR, INFO, QUESTION
	}

	public ModernDialog(JFrame parent, String closeMessage, IconType iconType) {
		super(parent, "Exit Application", false);
		this.superF = parent;
		overlay = new OverlayPanel();
		overlay.setBounds(0, 0, parent.getWidth(), parent.getHeight());
		overlay.setOpaque(false);
		parent.getLayeredPane().add(overlay, JLayeredPane.PALETTE_LAYER);
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

		// Add a MouseAdapter to detect clicks outside the dialog
		overlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point mousePoint = e.getPoint();
				SwingUtilities.convertPointToScreen(mousePoint, overlay); // Convert to screen coordinates

				Rectangle dialogBounds = getBounds();
				if (!dialogBounds.contains(mousePoint)) {
					Timer fadeOutTimer = new Timer(10, null);
					fadeOutTimer.addActionListener(e1 -> {
						float opacity = getOpacity();
						float currentAlpha = overlay.getAlpha();
						if (opacity > 0.05f) {
							setOpacity(opacity - 0.05f); // Decrease opacity gradually
						} else {
							fadeOutTimer.stop(); // Stop timer when fully transparent
							dispose(); // Dispose the dialog
						}
						if (currentAlpha > 0.05f) {
							overlay.setAlpha(currentAlpha - 0.05f); // Increase opacity gradually
						} else {
							overlay.setAlpha(currentAlpha);
						}
					});
					fadeOutTimer.start();
				}
			}
		});

		// Inside the constructor, after the dialog size and location configuration
		this.setOpacity(0f); // Set initial opacity to 0 (fully transparent)
		Timer fadeInTimer = new Timer(10, null);
		fadeInTimer.addActionListener(e -> {
			float opacity = getOpacity();
			float currentAlpha = overlay.getAlpha();
			if (opacity < 0.95f) {
				setOpacity(opacity + 0.05f); // Increase opacity gradually
			} else {
				setOpacity(1f); // Increase opacity gradually
				fadeInTimer.stop(); // Stop timer when fully visible
			}
			if (currentAlpha < 0.5f) {
				overlay.setAlpha(currentAlpha + 0.05f); // Increase opacity gradually
			} else {
				overlay.setAlpha(currentAlpha);
			}
		});
		fadeInTimer.start(); // Start fade-in effect

		// Key listener to close the dialog on Esc key press
		this.getRootPane().registerKeyboardAction(e -> {
			Timer fadeOutTimer = new Timer(10, null);
			fadeOutTimer.addActionListener(e1 -> {
				float opacity = getOpacity();
				float currentAlpha = overlay.getAlpha();
				if (opacity > 0.05f) {
					setOpacity(opacity - 0.05f); // Decrease opacity gradually
				} else {
					fadeOutTimer.stop(); // Stop timer when fully transparent
					dispose(); // Dispose the dialog
				}
				if (currentAlpha > 0.05f) {
					overlay.setAlpha(currentAlpha - 0.05f); // Increase opacity gradually
				} else {
					overlay.setAlpha(currentAlpha);
				}
			});
			fadeOutTimer.start(); // Start fade-out effect
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Request focus for the dialog
		this.setFocusableWindowState(true);
		this.requestFocusInWindow();
		setModalityType(Dialog.ModalityType.MODELESS);
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

	// Method to add a main button (e.g., "Yes", "No")
	public void addMainButton(String text, Color color, Runnable action) {
		RoundButton button = new RoundButton(text, 10);
		button.setFillColor(color);
		button.setForeground(Color.WHITE);
		button.setBorderColor(borderColor);
		button.setBorder(BorderFactory.createCompoundBorder(new LineBorder(borderColor, 1),
				BorderFactory.createEmptyBorder(10, 20, 10, 20)));
		button.addActionListener(e -> {
			Timer fadeOutTimer = new Timer(10, null);
			fadeOutTimer.addActionListener(e1 -> {
				float opacity = getOpacity();
				float currentAlpha = overlay.getAlpha();
				if (opacity > 0.05f) {
					setOpacity(opacity - 0.05f); // Decrease opacity gradually
				} else {
					fadeOutTimer.stop(); // Stop timer when fully transparent
					dispose(); // Dispose the dialog
					action.run();
				}
				if (currentAlpha > 0.05f) {
					overlay.setAlpha(currentAlpha - 0.05f); // Increase opacity gradually
				} else {
					overlay.setAlpha(currentAlpha);
				}
			});
			fadeOutTimer.start(); // Start fade-out effect
		});
		buttonPanel.add(button);
		buttonPanel.revalidate(); // Refresh the button panel to show the new button
		buttonPanel.repaint();
		adjustDialogSize(); // Adjust size when a new button is added
	}

	// Method to add an extra button (e.g., "Cancel" or other actions)
	public void addExtraButton(String text, Color color, Runnable action) {
		RoundButton extraButton = new RoundButton(text, 10);
		extraButton.setFillColor(color);
		extraButton.setForeground(Color.WHITE);
		extraButton.setBorderColor(borderColor);
		extraButton.setBorder(BorderFactory.createCompoundBorder(new LineBorder(borderColor, 2),
				BorderFactory.createEmptyBorder(10, 20, 10, 20)));
		extraButton.addActionListener(e -> {
			Timer fadeOutTimer = new Timer(10, null);
			fadeOutTimer.addActionListener(e1 -> {
				float opacity = getOpacity();
				float currentAlpha = overlay.getAlpha();
				if (opacity > 0.05f) {
					setOpacity(opacity - 0.05f); // Decrease opacity gradually
				} else {
					fadeOutTimer.stop(); // Stop timer when fully transparent
					dispose(); // Dispose the dialog
					action.run();
				}
				if (currentAlpha > 0.05f) {
					overlay.setAlpha(currentAlpha - 0.05f); // Increase opacity gradually
				} else {
					overlay.setAlpha(currentAlpha);
				}
			});
			fadeOutTimer.start(); // Start fade-out effect
		});
		buttonPanel.add(extraButton);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		adjustDialogSize(); // Adjust size when a new button is added
	}

	public void setOverlayColor(Color color) {
		if (overlay != null) {
			overlay.setOverlayColor(color);
		}
	}

	// Don't forget to clean up overlay when disposing
	@Override
	public void dispose() {
		removeOverlay();
		super.dispose();
	}

	public void removeOverlay() {
		if (overlay != null) {
			superF.getLayeredPane().remove(overlay);
			superF.getLayeredPane().repaint();
		}
	}

	// Create a custom overlay panel
	class OverlayPanel extends JPanel {
		private float alpha = 0f; // Starting alpha (0% transparency)
		private Color overlayColor = new Color(255, 255, 255); // Default color: white

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(new Color(overlayColor.getRed(), overlayColor.getGreen(), overlayColor.getBlue(),
					(int) (alpha * 255))); // Alpha transparency
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}

		public void setAlpha(float alpha) {
			this.alpha = alpha;
			repaint(); // Repaint the panel with updated alpha
		}

		public float getAlpha() {
			return this.alpha;
		}

		public void setOverlayColor(Color color) {
			this.overlayColor = color; // Update the overlay color
			repaint(); // Repaint the panel with the new color
		}

		public Color getOverlayColor() {
			return this.overlayColor;
		}
	}
}
