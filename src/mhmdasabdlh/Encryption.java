package mhmdasabdlh;
import java.util.ArrayList;

public class Encryption {

	private ArrayList<Character> textList = new ArrayList<>();
	private ArrayList<Character> shuffledList;
	private String key = "-vt_K5gZh%JH!b\\M?\"`f0|<@}.2O^N)1iIVF;dl3un:y]Ep~e*'a& w$4ro9DmCGSB7j=W+cXYAxU(z>R6QqT/#{[P,k8sL";

	public Encryption() {
		setKey();
	}

	public String encrypt(String finalText) {
		char[] encryText = new char[finalText.length()];
		encryText = finalText.toCharArray();
		for (int i = 0; i < finalText.length(); i++)
			for (int j = 0; j < key.length(); j++)
				if (encryText[i] == textList.get(j)) {
					encryText[i] = shuffledList.get(j);
					break;
				}
		return toString(encryText);
	}

	public String decrypt(String finalText) {
		char[] decryText = new char[finalText.length()];
		decryText = finalText.toCharArray();
		for (int i = 0; i < finalText.length(); i++)
			for (int j = 0; j < shuffledList.size(); j++)
				if (decryText[i] == shuffledList.get(j)) {
					decryText[i] = textList.get(j);
					break;
				}
		return toString(decryText);
	}

	private String toString(char[] a) {
		// Creating object of String class
		String string = new String(a);

		return string;
	}

	private void setKey() {
		char character = ' ';
		textList.clear();
		for (int i = 32; i < 127; i++) {
			textList.add(Character.valueOf(character));
			character++;
		}
		shuffledList = new ArrayList<>();
		for (int i = 0; i < key.length(); i++)
			shuffledList.add(key.charAt(i));
	}

}
