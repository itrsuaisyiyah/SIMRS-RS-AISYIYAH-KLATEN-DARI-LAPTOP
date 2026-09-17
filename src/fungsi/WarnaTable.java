/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTable extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        // WARNA BARIS NORMAL
        if (!isSelected) {

            if (row % 2 == 1) {
                component.setBackground(new Color(255,244,244));
            } else {
                component.setBackground(Color.WHITE);
            }

            component.setForeground(Color.BLACK);

            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.PLAIN,
                    11
            ));

        } else {

            // WARNA SAAT ROW DIPILIH
            component.setBackground(new Color(255,220,220));
            component.setForeground(Color.RED);

            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.PLAIN,
                    14
            ));
        }

        return component;
    }
}