package mhmdsabdlh.component.OverlayPanel;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.Timer;

public class OverlayFrame {
	
	private OverlayPanel overlay;
	private JFrame fram;
	
	public OverlayFrame(JFrame frame) {
		this.fram = frame;
		init();
	}
	
	private void init() {
		overlay = new OverlayPanel();
		overlay.setBounds(0, 0, fram.getWidth(), fram.getHeight());
		overlay.setOpaque(false);
		overlay.setOverlayColor(fram.getBackground());
		overlay.setVisible(false);
		fram.getLayeredPane().add(overlay, JLayeredPane.PALETTE_LAYER);
	}
	
	public void showOverlay() {
		overlay.setAlpha(0);
		overlay.setVisible(true);
		Timer fadeInTimer = new Timer(5, null);
		fadeInTimer.addActionListener(_ -> {
			float currentAlpha = overlay.getAlpha();
			if (currentAlpha < 0.5f) {
				overlay.setAlpha(currentAlpha + 0.1f); // Increase opacity gradually
			} else {
				overlay.setAlpha(0.5f);
				fadeInTimer.stop(); // Stop timer when fully visible
			}
		});
		fadeInTimer.start(); // Start fade-in effect
	}

	public void hideOverlay() {
		Timer fadeOutTimer = new Timer(5, null);
		fadeOutTimer.addActionListener(_ -> {
			float currentAlpha = overlay.getAlpha();
			if (currentAlpha > 0.1f) {
				overlay.setAlpha(currentAlpha - 0.1f); // Increase opacity gradually
			} else {
				overlay.setAlpha(0f);
				fadeOutTimer.stop(); // Stop timer when fully transparent
				overlay.setVisible(false);
			}
		});
		fadeOutTimer.start(); // Start fade-out effect
	}
	
}
