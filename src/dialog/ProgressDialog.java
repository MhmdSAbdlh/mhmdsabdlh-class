package mhmdsabdlh.dialog;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.*;

public class ProgressDialog {

	public static <T extends JDialog> void show(Supplier<T> dialogSupplier, Consumer<T> onReady) {
		// Loading dialog
		JDialog loadingDialog = new JDialog((Frame) null, false); // modeless
		loadingDialog.setUndecorated(true); // no title bar or border
		loadingDialog.setBackground(new Color(0, 0, 0, 0)); // fully transparent

		JLabel icon = new JLabel(new ImageIcon(ProgressDialog.class.getResource(("loading.gif"))));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setOpaque(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(icon, BorderLayout.CENTER);

		loadingDialog.setContentPane(panel);
		loadingDialog.pack();
		loadingDialog.setLocationRelativeTo(null); // center of screen
		loadingDialog.setAlwaysOnTop(true); // ensure it's visible
		loadingDialog.setVisible(true);

		new SwingWorker<T, Void>() {
			@Override
			protected T doInBackground() {
				return dialogSupplier.get();
			}

			@Override
			protected void done() {
				try {
					T dialog = get();
					loadingDialog.dispose(); // Close the loading spinner
					SwingUtilities.invokeLater(() -> onReady.accept(dialog));
				} catch (Exception e) {
					loadingDialog.dispose();
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}.execute();
	}

	public static void runWithLoading(Runnable backgroundTask, Runnable onCompleteUI) {
		// Loading dialog
		JDialog loadingDialog = new JDialog((Frame) null, false);
		loadingDialog.setUndecorated(true);
		loadingDialog.setBackground(new Color(0, 0, 0, 0));

		JLabel icon = new JLabel(new ImageIcon(ProgressDialog.class.getResource("loading.gif")));
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setOpaque(false);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setOpaque(false);
		panel.add(icon, BorderLayout.CENTER);

		loadingDialog.setContentPane(panel);
		loadingDialog.pack();
		loadingDialog.setLocationRelativeTo(null);
		loadingDialog.setAlwaysOnTop(true);
		loadingDialog.setVisible(true);

		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				backgroundTask.run(); // perform background task (e.g., save)
				return null;
			}

			@Override
			protected void done() {
				loadingDialog.dispose(); // Close the spinner
				SwingUtilities.invokeLater(onCompleteUI); // UI action after saving
			}
		}.execute();
	}

}
