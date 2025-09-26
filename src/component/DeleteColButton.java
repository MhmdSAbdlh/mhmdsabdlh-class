package mhmdsabdlh.component;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class DeleteColButton extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
	private final JTable table;
	private final Action action;
	private final JButton renderButton;
	private final JButton editButton;
	private Object editorValue;

	public DeleteColButton(JTable table, Action action, int column) {
		this.table = table;
		this.action = action;

		renderButton = new JButton("Delete");
		editButton = new JButton("Delete");

		renderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		editButton.setFocusPainted(false);
		editButton.addActionListener(this);

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer(this);
		columnModel.getColumn(column).setCellEditor(this);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		return renderButton;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		editorValue = value;
		return editButton;
	}

	@Override
	public Object getCellEditorValue() {
		return editorValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getEditingRow();
		fireEditingStopped();
		ActionEvent event = new ActionEvent(table, ActionEvent.ACTION_PERFORMED, String.valueOf(row));
		action.actionPerformed(event);
	}
}
