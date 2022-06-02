package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

	private static final Border yellowBorder = BorderFactory
			.createLineBorder(Color.YELLOW);
	private static final Border whiteBorder = BorderFactory
			.createLineBorder(Color.WHITE);
	private static final Border grayBorder = BorderFactory
			.createLineBorder(Color.LIGHT_GRAY);
        
        private static final Border emptyBorder = BorderFactory.createEmptyBorder();

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int col) {
            
            
		JComponent c = (JComponent) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, col);
		
                
                if (isSelected) {
			//c.setBackground(Color.YELLOW);
			c.setBorder(emptyBorder);
		}
                
		return c;
	}
}