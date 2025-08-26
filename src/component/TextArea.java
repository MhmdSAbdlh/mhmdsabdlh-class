package mhmdsabdlh.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class TextArea extends JTextArea {

	private static final long serialVersionUID = 1L;

	private int round = 10;

	public TextArea() {
		setOpaque(false);
		setForeground(new Color(80, 80, 80));
		setSelectedTextColor(new Color(255, 255, 255));
		setSelectionColor(new Color(133, 209, 255));
		setBorder(new EmptyBorder(10, 12, 15, 12));
		setBackground(new Color(255, 255, 255));
		setLineWrap(true); // Enable line wrapping
		setWrapStyleWord(true); // Wrap at word boundaries
		enableEnterKey();
		setRows(3);
        setAutoscrolls(true);
	}

	// Ensure the Enter key works as expected
	private void enableEnterKey() {
		getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "insert-break");
		getActionMap().put("insert-break", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				replaceSelection("\n"); // Inserts a newline character
			}
		});
	}

	@Override
	protected void paintComponent(Graphics grphcs) {
		Graphics2D g2 = (Graphics2D) grphcs.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Create Background Color
		g2.setColor(getBackground());
		Area area = new Area(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, round, round));
		g2.fill(area);

		// Draw Border
		if (getBorder() != null) {
			g2.setColor(Color.BLACK); // Customize border color
			g2.setStroke(new java.awt.BasicStroke(1)); // Customize border thickness
			g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, round, round));
		}

		g2.dispose();
		super.paintComponent(grphcs);
	}

}
