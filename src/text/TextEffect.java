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
	public static boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDouble(String number) {
		try {
			Double.parseDouble(number);
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

	public static String toRoman(int number) {
		if (number <= 0 || number > 3999) {
			throw new IllegalArgumentException("Number must be between 1 and 3999");
		}

		String[] romanNumerals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

		StringBuilder result = new StringBuilder();

		for (int i = 0; i < values.length; i++) {
			while (number >= values[i]) {
				result.append(romanNumerals[i]);
				number -= values[i];
			}
		}
		return result.toString();
	}

	/* return the double */
	public static double roundedDouble(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	/* Convert double to int */
	public static int convertDoubleToInt(double input) {
		return (int) Math.round(input);
	}

	/* Add space between letters */
	public static String addSpaces(String input) {
		if (input == null || input.isEmpty())
			return input;

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			result.append(input.charAt(i));
			if (i < input.length() - 1)
				result.append(" ");
		}
		return result.toString();
	}

}
