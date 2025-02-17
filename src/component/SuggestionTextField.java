package mhmdsabdlh.component;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

public class SuggestionTextField extends JTextField {
	private JWindow suggestionWindow;
	private JPanel suggestionPanel;
	private List<String> suggestions;
	private List<JButton> suggestionButtons;
	private int selectedIndex = -1;

	public SuggestionTextField() {
		super();
		init();
	}

	private void init() {
		// Initialize the list of suggestions and buttons
		suggestions = new ArrayList<>();
		suggestionButtons = new ArrayList<>();

		// Set up the suggestion window
		suggestionWindow = new JWindow();
		suggestionPanel = new JPanel();
		suggestionPanel.setLayout(new BoxLayout(suggestionPanel, BoxLayout.Y_AXIS));
		suggestionWindow.add(suggestionPanel);

		// Add a KeyListener to handle user input
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String text = getText();
				String currentWord = getCurrentWord(text);
				if (!currentWord.isEmpty()) {
					showSuggestions(currentWord);
				} else {
					suggestionWindow.setVisible(false);
				}

				// Handle up/down arrow keys and Enter key
				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					selectNextSuggestion();
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					selectPreviousSuggestion();
				} else if (e.getKeyCode() == KeyEvent.VK_ENTER && selectedIndex != -1) {
					selectSuggestion(selectedIndex);
				}
			}
		});

		// Ensure the text field retains focus
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!e.isTemporary()) {
					suggestionWindow.setVisible(false);
				}
			}
		});
	}

	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}

	public void addSuggestion(String suggestion) {
		this.suggestions.add(suggestion);
	}

	public void clearSuggestions() {
		this.suggestions.clear();
	}

	private void showSuggestions(String input) {
		suggestionPanel.removeAll(); // Clear previous suggestions
		suggestionButtons.clear(); // Clear the list of buttons
		selectedIndex = -1; // Reset the selected index

		int count = 0;
		for (String suggestion : suggestions) {
			if (suggestion.toLowerCase().contains(input.toLowerCase()) && count < 5) {
				JButton suggestionButton = new JButton(suggestion);
				suggestionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
				suggestionButton.setBorderPainted(false);
				suggestionButton.setContentAreaFilled(false);
				suggestionButton.setFocusPainted(false);
				suggestionButton.addActionListener(e -> {
					replaceCurrentWord(suggestion); // Set the text field to the selected suggestion
					suggestionWindow.setVisible(false); // Hide the suggestion window
					requestFocusInWindow(); // Retain focus in the text field
				});
				suggestionButtons.add(suggestionButton);
				suggestionPanel.add(suggestionButton);
				count++;
			}
		}

		if (suggestionPanel.getComponentCount() > 0) {
			// Position the suggestion window below the text field
			Point location = getLocationOnScreen();
			suggestionWindow.setLocation(location.x, location.y + getHeight());
			suggestionWindow.pack();
			suggestionWindow.setVisible(true);
		} else {
			suggestionWindow.setVisible(false);
		}
	}

	private void selectNextSuggestion() {
		if (suggestionButtons.isEmpty())
			return;

		// Move to the next suggestion
		selectedIndex = (selectedIndex + 1) % suggestionButtons.size();

		String suggestion = suggestionButtons.get(selectedIndex).getText();
		updateTextWithSuggestion(suggestion);
	}

	private void selectPreviousSuggestion() {
		if (suggestionButtons.isEmpty())
			return;

		// Move to the previous suggestion
		selectedIndex = (selectedIndex - 1 + suggestionButtons.size()) % suggestionButtons.size();

		String suggestion = suggestionButtons.get(selectedIndex).getText();
		updateTextWithSuggestion(suggestion);
	}

	private void updateTextWithSuggestion(String suggestion) {
		String currentText = getText();
		int lastSpaceIndex = currentText.lastIndexOf(" ");
		String currentWord = lastSpaceIndex == -1 ? currentText : currentText.substring(lastSpaceIndex + 1);

		if (suggestion.toLowerCase().contains(currentWord.toLowerCase())) {
			String newText = (lastSpaceIndex == -1 ? "" : currentText.substring(0, lastSpaceIndex + 1)) + suggestion;
			setText(newText);
			// Select the added part of the suggestion
			setSelectionStart(currentText.length());
			setSelectionEnd(newText.length());
		}
	}

	private void selectSuggestion(int index) {
		if (index >= 0 && index < suggestionButtons.size()) {
			setText(suggestionButtons.get(index).getText()); // Set the text field to the selected suggestion
			suggestionWindow.setVisible(false); // Hide the suggestion window
			requestFocusInWindow(); // Retain focus in the text field
		}
	}

	private String getCurrentWord(String text) {
		if (text == null || text.isEmpty()) {
			return "";
		}
		int lastSpaceIndex = text.lastIndexOf(" ");
		if (lastSpaceIndex == -1) {
			return text; // No spaces, return the whole text
		}
		return text.substring(lastSpaceIndex + 1); // Return the text after the last space
	}

	private void replaceCurrentWord(String suggestion) {
		String text = getText();
		int lastSpaceIndex = text.lastIndexOf(" ");
		if (lastSpaceIndex == -1) {
			setText(suggestion); // If no space, replace the whole text
		} else {
			setText(text.substring(0, lastSpaceIndex + 1) + suggestion); // Replace the current word
		}
		setSelectionStart(text.length()); // Move the cursor to the end
		setSelectionEnd(text.length());
	}

}