package mhmdasabdlh;

import javax.swing.*;
import java.awt.*;

//Custom Icon class to draw image and text
public class IconWithText implements Icon {
	private final ImageIcon imageIcon;
    private final String text;
    private final Color textColor;

    public IconWithText(ImageIcon imageIcon, String text, Color textColor) {
        this.imageIcon = imageIcon;
        this.text = text;
        this.textColor = textColor;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        // Draw the image
        imageIcon.paintIcon(c, g, x, y);

        // Set the text color
        g.setColor(textColor);
        g.setFont(g.getFont().deriveFont(Font.BOLD));  // Optionally set the font

        // Calculate the position to center the text horizontally and vertically
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();  // Height of the text (top to baseline)

        // Calculate the coordinates to center the text
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();

        int textX = x + (imageWidth - textWidth) / 2;  // Center horizontally
        int textY = y + ((imageHeight - textHeight) / 2) + fm.getAscent();  // Center vertically

        // Draw the text at the calculated position
        g.drawString(text, textX, textY);
    }

    @Override
    public int getIconWidth() {
        return imageIcon.getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return imageIcon.getIconHeight();
    }
}
