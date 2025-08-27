package mhmdsabdlh.dialog;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;

import mhmdsabdlh.component.OverlayPanel.OverlayPanel;
import mhmdsabdlh.images.ImageEffect;

public class UpdateDialog extends JDialog {

	private JPanel buttonPanel, messagePanel;
	private JLabel messageLabel, iconLabel, subtitleText;
	private String closeMessage;
	private Color panelColor, txtColor;
	private OverlayPanel overlay;
	private final JFrame superF;
	private Runnable onDisposeCallback;
	public JComboBox<String> versionList;
	private IconType iconT;

	public UpdateDialog(JFrame parent, String closeMessage, IconType iconType) {
		super(parent, "Exit Application", false);
		this.superF = parent;
		overlay = new OverlayPanel();
		overlay.setBounds(0, 0, parent.getWidth(), parent.getHeight());
		overlay.setOpaque(false);
		parent.getLayeredPane().add(overlay, JLayeredPane.PALETTE_LAYER);
		this.closeMessage = closeMessage; // Store the message
		this.txtColor = Color.BLACK;
		this.iconT = iconType;

		// Customize dialog's look
		setUndecorated(true); // Removes the default window frame
		setLayout(new BorderLayout());

		// Create icon label
		iconLabel = new JLabel();
		setIcon(iconType); // Set the icon based on the type
		iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Apply rounded shape to the dialog
		setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 10, 10)); // Rounded corners

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
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10); // Draw border with small padding
			}
		};
		panel.setLayout(new BorderLayout(0, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Add multiline support using HTML in JLabel
		messagePanel = new JPanel(new BorderLayout(0, 0));
		messagePanel.setOpaque(false);
		messageLabel = new JLabel(
				"<html><div style='font-family: Arial; font-size: 14px; color:" + getHexColor(txtColor)
						+ "; font-style: bold;'>" + closeMessage.replace("\n", "<br>") + "</div></html>",
				JLabel.CENTER);
		subtitleText = new JLabel("<html><div style='font-family: Arial; font-size: 10px; color:"
				+ getHexColor(txtColor) + "; font-style: italic;'>" + "".replace("\n", "<br>") + "</div></html>",
				JLabel.CENTER);

		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonPanel.setOpaque(false); // Transparent background for buttons

		JPanel versionPanel = new JPanel();
		versionPanel.setOpaque(false);
		versionList = new JComboBox<String>();
		versionList.setPreferredSize(new Dimension(200, 30));
		versionList.setMinimumSize(new Dimension(200, 30));
		versionList.setMaximumSize(new Dimension(200, 30));
		versionList.putClientProperty(FlatClientProperties.OUTLINE, Color.black);
		versionList.putClientProperty(FlatClientProperties.STYLE, "arc: 15");
		versionList.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				label.setHorizontalAlignment(SwingConstants.CENTER);
				return label;
			}
		});
		if (iconType == IconType.DOWNGRADE) {
			// message panel
			versionPanel.add(versionList);
			messagePanel.add(iconLabel, BorderLayout.NORTH);
			messagePanel.add(messageLabel, BorderLayout.CENTER);
			messagePanel.add(versionPanel, BorderLayout.SOUTH);
			// Spacing
			iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
			messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0));
			subtitleText.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));

			panel.add(messagePanel, BorderLayout.NORTH);
			panel.add(subtitleText, BorderLayout.CENTER);
			panel.add(buttonPanel, BorderLayout.SOUTH);
			messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0));
			subtitleText.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0));
		} else {
			messagePanel.add(messageLabel, BorderLayout.CENTER);
			messagePanel.add(subtitleText, BorderLayout.SOUTH);
			messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
			subtitleText.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

			panel.add(iconLabel, BorderLayout.NORTH);
			panel.add(messagePanel, BorderLayout.CENTER);
			panel.add(buttonPanel, BorderLayout.SOUTH);
			iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
			messagePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 8, 0));
		}
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		add(panel);

		// Adjust dialog size based on the message height
		pack(); // Adjust size to fit content
		adjustDialogSize();

		// Set position
		setLocationRelativeTo(parent);

		// Add a MouseAdapter to detect clicks outside the dialog
		overlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point mousePoint = e.getPoint();
				SwingUtilities.convertPointToScreen(mousePoint, overlay); // Convert to screen coordinates

				Rectangle dialogBounds = getBounds();
				if (!dialogBounds.contains(mousePoint)) {
					startFadeOut(() -> dispose());
				}
			}
		});

		// Inside the constructor, after the dialog size and location configuration
		setOpacity(0f);
		Timer fadeInTimer = new Timer(20, new ActionListener() {
			float opacity = 0f;
			float overlayAlpha = 0f;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity += 0.1f;
				overlayAlpha += 0.05f;
				setOpacity(Math.min(opacity, 1.0f));
				overlay.setAlpha(Math.min(overlayAlpha, 0.5f));
				if (opacity >= 1.0f)
					((Timer) e.getSource()).stop();
				repaint();
			}
		});
		fadeInTimer.start();

		// Key listener to close the dialog on Esc key press
		getRootPane().registerKeyboardAction(_ -> startFadeOut(() -> dispose()),
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

		// Request focus for the dialog
		setFocusableWindowState(true);
		requestFocusInWindow();
		setModalityType(Dialog.ModalityType.MODELESS);
		// Add a listener to handle the dispose event
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if (onDisposeCallback != null) {
					onDisposeCallback.run();
				}
			}
		});
	}

	public void addComboBox(ArrayList<String> versions) {
		for (int index = 0; index < versions.size(); index++)
			versionList.addItem(versions.get(index));
		adjustDialogSize();
	}

	public JComboBox<String> getComboBoc() {
		return versionList;
	}

	public void addTooltip(List<String> tooltip) {
		if (versionList != null) {
			versionList.setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(JList<?> list, Object value, int index,
						boolean isSelected, boolean cellHasFocus) {
					JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					if (index >= 0)
						label.setToolTipText(tooltip != null ? tooltip.get(index) : null);
					else
						label.setToolTipText(null);
					label.setHorizontalAlignment(0);
					return label;
				}
			});
		}
	}

	// method to add a subtitle
	public void addSubText(String text, Color color) {
		String formattedText = text.replace("\n", "<br>");
		subtitleText.setText("<html><div style='font-family: Arial; font-size: 10px; color:" + getHexColor(color)
				+ "; font-style: italic;'>" + formattedText.replace("\n", "<br>") + "</div></html>");
		adjustDialogSize();
	}

	// Method to set the appropriate icon
	private void setIcon(IconType iconType) {
		switch (iconType) {
		case DOWNGRADE:
			iconLabel.setIcon(ImageEffect
					.getScaledImage(new ImageIcon(getClass().getResource("downgrade.png")).getImage(), 50, 50));
			break;
		case UPDATE:
			iconLabel.setIcon(
					ImageEffect.getScaledImage(new ImageIcon(getClass().getResource("update.png")).getImage(), 50, 50));
			break;
		default:
			iconLabel.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
			break;
		}
	}

	// Adjusts the dialog's height based on the preferred size of the message label
	private void adjustDialogSize() {
		if (iconT == IconType.DOWNGRADE) {
			int messageHeight = messagePanel.getPreferredSize().height;
			int buttonPanelHeight = buttonPanel.getPreferredSize().height;
			int versionHeigh = subtitleText.getPreferredSize().height;

			// Calculate preferred width based on the buttons
			int totalButtonWidth = calculateTotalButtonWidth();
			int messageWidth = messagePanel.getPreferredSize().width;

			int dialogWidth = Math.max(totalButtonWidth, messageWidth) + 60; // Add padding
			int dialogHeight = messageHeight + buttonPanelHeight + versionHeigh + 60; // Add padding for margins

			this.setSize(dialogWidth, dialogHeight);

			// Update rounded shape based on new size
			this.setShape(new RoundRectangle2D.Double(0, 0, dialogWidth, dialogHeight, 10, 10));
		} else {
			int messageHeight = messagePanel.getPreferredSize().height;
			int buttonPanelHeight = buttonPanel.getPreferredSize().height;
			int iconHeight = iconLabel.getPreferredSize().height;

			// Calculate preferred width based on the buttons
			int totalButtonWidth = calculateTotalButtonWidth();
			int messageWidth = messagePanel.getPreferredSize().width;

			int dialogWidth = Math.max(totalButtonWidth, messageWidth) + 60; // Add padding
			int dialogHeight = messageHeight + buttonPanelHeight + iconHeight + 60; // Add padding for margins

			this.setSize(dialogWidth, dialogHeight);

			// Update rounded shape based on new size
			this.setShape(new RoundRectangle2D.Double(0, 0, dialogWidth, dialogHeight, 10, 10));
		}
		setLocationRelativeTo(superF);
	}

	// Method to calculate total width of buttons
	private int calculateTotalButtonWidth() {
		int totalWidth = 0;
		for (Component button : buttonPanel.getComponents()) {
			totalWidth += button.getPreferredSize().width;
		}
		return totalWidth + (buttonPanel.getComponentCount() - 1) * 20; // Adding space between buttons
	}

	public void setColor(Color bgColor) {
		this.panelColor = bgColor;
	}

	public void setTextColor(Color textC) {
		this.txtColor = textC;
		updateLabelText();
	}

	private void updateLabelText() {
		if (txtColor != null && messageLabel != null)
			messageLabel.setText("<html><div style='font-family: Arial; font-size: 14px; color:" + getHexColor(txtColor)
					+ "; font-style: bold'>" + closeMessage.replace("\n", "<br>") + "</div></html>");
	}

	private String getHexColor(Color color) {
		return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
	}

	// Method to add a main button (e.g., "Yes", "No")
	public void addButton(String text, IconType iconType, Runnable action) {
		JButton button = new JButton(text);
		if (iconType == null)
			button.putClientProperty(FlatClientProperties.STYLE, "" + "arc:999;" + "margin:3,33,3,33;"
					+ "borderWidth:1;" + "focusWidth:0;" + "innerFocusWidth:0.5;" + "background:null;");
		else {
			String colors[] = getColorKey(iconType);
			button.putClientProperty(FlatClientProperties.STYLE,
					"" + "arc:999;" + "margin:3,33,3,33;" + "borderWidth:1;" + "focusWidth:0;" + "innerFocusWidth:0.5;"
							+ "background:null;" + "[light]borderColor:" + colors[0] + ";" + "[dark]borderColor:"
							+ colors[1] + ";" + "[light]focusedBorderColor:" + colors[0] + ";"
							+ "[dark]focusedBorderColor:" + colors[1] + ";" + "[light]focusColor:" + colors[0] + ";"
							+ "[dark]focusColor:" + colors[1] + ";" + "[light]hoverBorderColor:" + colors[0] + ";"
							+ "[dark]hoverBorderColor:" + colors[1] + ";" + "[light]foreground:" + colors[0] + ";"
							+ "[dark]foreground:" + colors[1] + ";");
		}
		button.addActionListener(e -> startFadeOut(action));
		buttonPanel.add(button);
		buttonPanel.revalidate(); // Refresh the button panel to show the new button
		buttonPanel.repaint();
		adjustDialogSize(); // Adjust size when a new button is added
	}

	protected String[] getColorKey(IconType type) {
		switch (type) {
		case WARNING:
			return new String[] { "#CC8925", "#CC8925" };
		case ERROR:
			return new String[] { "#FF5757", "#FF5757" };
		case INFO:
			return new String[] { "#3B82F6", "#3B82F6" };
		case QUESTION:
			return new String[] { "#1EA97C", "#1EA97C" };
		default:
			return new String[] { "#1EA97C", "#1EA97C" };
		}
	}

	public void setOverlayColor(Color color) {
		if (overlay != null) {
			overlay.setOverlayColor(color);
		}
	}

	// Method to set the callback
	public void setOnDisposeCallback(Runnable callback) {
		this.onDisposeCallback = callback;
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

	private void startFadeOut(Runnable action) {
		Timer fadeOutTimer = new Timer(20, new ActionListener() {
			float opacity = 1.0f;
			float overlayAlpha = 0.5f;

			@Override
			public void actionPerformed(ActionEvent e) {
				opacity -= 0.1f;
				overlayAlpha -= 0.05f;
				setOpacity(Math.max(opacity, 0.0f));
				overlay.setAlpha(Math.max(overlayAlpha, 0f));
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
