package fr.killax;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class AssetsList extends JLabel implements ListCellRenderer<Assets> {

	private static final long serialVersionUID = 1L;
	Color selectColor = Color.GREEN;

	@Override
	public Component getListCellRendererComponent(JList<? extends Assets> list, Assets value, int index,
			boolean isSelected, boolean cellHasFocus) {
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(selectColor);
			setText(value.getName());
			setIcon(value.getIcon());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
			setText(value.getName());
			setIcon(value.getIcon());
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		return this;
	}

}
