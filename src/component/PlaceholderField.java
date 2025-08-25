package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class PlaceholderField extends JTextField {
    private String placeholder;

    public PlaceholderField(String placeholder) {
        this.placeholder = placeholder;
    }

    public PlaceholderField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
    }

    public PlaceholderField(Document doc, String text, int columns, String placeholder) {
        super(doc, text, columns);
        this.placeholder = placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && placeholder != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(new Font(getFont().getName(),Font.PLAIN,10));
            Insets insets = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int y = (getHeight() - fm.getHeight()) / 2;
            g2.drawString(placeholder, insets.left + 2, y-5);
            g2.dispose();
        }
    }
}
