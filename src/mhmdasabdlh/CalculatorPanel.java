package mhmdasabdlh;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CalculatorPanel extends JPanel implements ActionListener {
	private JTextField display;
	private double result;
	private String operator;
	private boolean startOfNumber;
	private String lastEntry;
	@SuppressWarnings("unused")
	private JFrame parentFrame; // Reference to the parent frame

	public CalculatorPanel(JFrame parentFrame) {
		setLayout(new BorderLayout());

		this.parentFrame = parentFrame;
		display = new JTextField("0");
		display.setEditable(false);
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setFont(new Font("Arial", Font.PLAIN, 32));
		display.setBackground(Color.black);
		display.setForeground(Color.white);
		add(display, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 4)); // Changed to 6x4 to accommodate the new buttons

		String[] buttons = { "7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "0", "00", ".", "+", "C",
				"Undo", "Copy", "=" };

		for (String text : buttons) {
			JButton button = new JButton(text);
			button.addActionListener(this);
			// Set button colors
			if ("0123456789.".contains(text) || text.equals("00")) {
				button.setBackground(new Color(0x781f19)); // Red for numbers
				button.setForeground(Color.WHITE); // White text on red buttons
			} else if ("/*-+=.".contains(text)) {
				button.setBackground(new Color(0x091727)); // White for operators
				button.setForeground(Color.WHITE); // Black text on white buttons
			} else if ("C".equals(text) || "Undo".equals(text) || "Copy".equals(text)) {
				button.setBackground(new Color(0xa4973f)); // Yellow for 'C' and 'Undo'
				button.setForeground(Color.BLACK); // Black text on yellow buttons
			} else {
				button.setBackground(Color.LIGHT_GRAY); // Default color for others
				button.setForeground(Color.BLACK); // Black text on light gray buttons
			}

			panel.add(button);
		}

		add(panel, BorderLayout.CENTER);
		result = 0;
		operator = "=";
		startOfNumber = true;
		lastEntry = "";

		parentFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Copy the non-0 to the clipboard
				if (!display.getText().equalsIgnoreCase("0") && !display.getText().equalsIgnoreCase("0.0"))
					copyToClipboard(display.getText());

			}
		});
		display.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if (Character.isDigit(c)) { // the key is number
					if (startOfNumber) {
						display.setText(c + "");
						startOfNumber = false;
					} else {
						lastEntry = display.getText();
						display.setText(display.getText() + c);
					}
				} else if (c == '.' || c == ',') { // the key is ,
					if (startOfNumber) {
						display.setText(0 + ".");
						startOfNumber = false;
					} else {
						lastEntry = display.getText();
						display.setText(display.getText() + c);
					}
				} else if (c == '+' || c == '+' || c == '-' || c == '*' || c == '/') {
					if (startOfNumber) {
						operator = c + "";
					} else {
						calculate(Double.parseDouble(display.getText()));
						operator = c + "";
						startOfNumber = true;
					}
				} else if (c == 'c') {
					display.setText("0");
					result = 0;
					operator = "=";
					startOfNumber = true;
					lastEntry = "";
				}
			}

			@Override
			public void keyPressed(KeyEvent ke) {
				// Check if 'Esc' key is pressed
				if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (!display.getText().equalsIgnoreCase("0") && !display.getText().equalsIgnoreCase("0.0"))
						copyToClipboard(display.getText());
					parentFrame.dispose(); // Close the frame
				} else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!startOfNumber) {
						calculate(Double.parseDouble(display.getText()));
						operator = "=";
						startOfNumber = true;
					}
				} else if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (display.getText().length() > 0) {
						display.setText(display.getText().substring(0, display.getText().length() - 1));
					}

				}

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();

		if ("0123456789.".contains(command)) {
			if (startOfNumber) {
				display.setText(command);
				startOfNumber = false;
			} else {
				lastEntry = display.getText();
				display.setText(display.getText() + command);
			}
		} else if (command.equals("00")) {
			if (startOfNumber) {
				display.setText("0");
				startOfNumber = false;
			} else {
				lastEntry = display.getText();
				display.setText(display.getText() + "00");
			}
		} else if (command.equals("Undo")) {
			if (!startOfNumber) {
				display.setText(lastEntry);
				startOfNumber = false;
			}
		} else if (command.equals("Copy")) {
			copyToClipboard(display.getText());
		} else if (command.equals("C")) {
			display.setText("0");
			result = 0;
			operator = "=";
			startOfNumber = true;
			lastEntry = "";
		} else {
			if (startOfNumber) {
				if (command.equals("-")) {
					display.setText(command);
					startOfNumber = false;
				} else {
					operator = command;
				}
			} else {
				calculate(Double.parseDouble(display.getText()));
				operator = command;
				startOfNumber = true;
			}
		}
	}

	private void calculate(double number) {
		switch (operator) {
		case "+":
			result += number;
			break;
		case "-":
			result -= number;
			break;
		case "*":
			result *= number;
			break;
		case "/":
			if (number != 0) {
				result /= number;
			} else {
				JOptionPane.showMessageDialog(this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "=":
			result = number;
			break;
		}
		display.setText("" + result);
	}

	public void copyToClipboard(String text) {
		// Convert the text to a long (integer without commas or decimals)
		long integerValue = (long) Double.parseDouble(text);
		StringSelection stringSelection = new StringSelection(String.valueOf(integerValue));
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}

	public String getDisplayText() {
		return display.getText();
	}
}
