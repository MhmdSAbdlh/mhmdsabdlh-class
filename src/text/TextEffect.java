package mhmdsabdlh.text;

import java.util.Random;

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
}
