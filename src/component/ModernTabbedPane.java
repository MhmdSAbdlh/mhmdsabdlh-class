package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;

public class ModernTabbedPane extends JTabbedPane {

	public enum TAB_TYPE {
		CARD, UNDERLINE
	}

	public enum TAB_LAYOUT {
		WRAP, SCROLL
	}

	public enum TAB_ALIGNMENT {
		DEFAULT, CENTER, END, FILL
	}

	static {
		UIManager.put("TabbedPane.tabType", "card");
		UIManager.put("TabbedPane.tabAreaAlignment", "center");
		UIManager.put("TabbedPane.tabInsets", new Insets(5, 15, 5, 15));
		UIManager.put("TabbedPane.closeArc", 999);
		UIManager.put("TabbedPane.tabArc", 15);
		UIManager.put("TabbedPane.underlineColor", new Color(51, 153, 255));
		UIManager.put("TabbedPane.tabSelectionHeight", 3);
		UIManager.put("TabbedPane.closeHoverForeground", Color.red);

		FlatLaf.updateUI();
	}

	public ModernTabbedPane() {
		super();
		setFont(new Font("SansSerif", Font.BOLD, 16));
	}

	public void isClosable(boolean close) {
		if (close) {
			putClientProperty("JTabbedPane.tabClosable", true);
			putClientProperty("JTabbedPane.tabCloseCallback", (java.util.function.IntConsumer) tabIndex -> {
				removeTabAt(tabIndex);
			});
		} else
			putClientProperty("JTabbedPane.tabClosable", false);
	}

	public void tabType(TAB_TYPE type) {
		if (type == TAB_TYPE.CARD)
			putClientProperty("JTabbedPane.tabType", "card");
		else
			putClientProperty("JTabbedPane.tabType", "underlined");
	}

	public void tabLayout(TAB_LAYOUT type) {
		if (type == TAB_LAYOUT.WRAP)
			setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		else {
			setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			putClientProperty("JTabbedPane.tabsPopupPolicy", "asNeeded");
			putClientProperty("JTabbedPane.scrollButtonsPolicy", "asNeeded");
			putClientProperty("JTabbedPane.scrollButtonsPlacement", "both");
		}
	}

	public void tabAlignment(TAB_ALIGNMENT type) {
		if (type == TAB_ALIGNMENT.DEFAULT)
			putClientProperty("JTabbedPane.tabAreaAlignment", "leading");
		else if (type == TAB_ALIGNMENT.END)
			putClientProperty("JTabbedPane.tabAreaAlignment", "trailing");
		else if (type == TAB_ALIGNMENT.CENTER)
			putClientProperty("JTabbedPane.tabAreaAlignment", "center");
		else if (type == TAB_ALIGNMENT.FILL)
			putClientProperty("JTabbedPane.tabAreaAlignment", "fill");
	}

	public void showSeparator(boolean show) {
		if (show)
			putClientProperty("JTabbedPane.showTabSeparators", true);
		else
			putClientProperty("JTabbedPane.showTabSeparators", true);

	}
}
