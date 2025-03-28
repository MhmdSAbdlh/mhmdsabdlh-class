package mhmdsabdlh.text;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Random;

import javax.swing.JTextField;

public class TextEffect {
	private static final Random random = new Random();

	public static String scrambleText(String text) {
		char[] chars = text.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			int j = random.nextInt(chars.length);
			// Swap characters
			char temp = chars[i];
			chars[i] = chars[j];
			chars[j] = temp;
		}
		return new String(chars);
	}

	// Get the first 3 letter for any string
	public static String getFirstThreeLetters(String str) {
		if (str.length() < 3) {
			return str; // Return the whole string if it's less than 3 characters.
		}
		return str.substring(0, 3); // Get the first three characters.
	}

	// Capitalize word
	public static String capitalizeString(String string) {
		char[] chars = string.toLowerCase().toCharArray();
		boolean found = false;
		for (int i = 0; i < chars.length; i++) {
			if (!found && Character.isLetter(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
				found = true;
			} else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') {
				found = false;
			}
		}
		return String.valueOf(chars);
	}

	// Check the input text if it is a number
	public static boolean isNumeric(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Adjust text size depend the width
	public static void adjustFontSize(JTextField textField) {
		String text = textField.getText();
		Font currentFont = textField.getFont();
		FontMetrics fontMetrics = textField.getFontMetrics(currentFont);
		int textWidth = fontMetrics.stringWidth(text);
		int defaultFontSize = textField.getFont().getSize();

		// Calculate the available width in the text field
		int availableWidth = textField.getWidth() - 10; // Subtract some padding

		if (textWidth > availableWidth) {
			// Decrease the font size until the text fits
			int newFontSize = defaultFontSize;
			while (textWidth > availableWidth && newFontSize > 1) {
				newFontSize--;
				textField.setFont(new Font(currentFont.getName(), currentFont.getStyle(), newFontSize));
				fontMetrics = textField.getFontMetrics(textField.getFont());
				textWidth = fontMetrics.stringWidth(text);
			}
		} else {
			// Reset to the default font size
			textField.setFont(new Font(currentFont.getName(), currentFont.getStyle(), defaultFontSize));
		}
	}
}
