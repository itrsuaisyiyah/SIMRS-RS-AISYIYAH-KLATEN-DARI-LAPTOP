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
public class WarnaTableIgd extends DefaultTableCellRenderer {

    private Color bg;
    private Color fg;

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        // WARNA DASAR SELANG SELING
        if (row % 2 == 1) {
            component.setBackground(new Color(255, 244, 244));
            component.setForeground(new Color(50, 50, 50));
        } else {
            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(50, 50, 50));
        }

        // AMBIL DATA DENGAN AMAN
        String caraBayar = "";
        String status = "";

        if (table.getValueAt(row, 17) != null) {
            caraBayar = table.getValueAt(row, 17).toString();
        }

        if (table.getValueAt(row, 10) != null) {
            status = table.getValueAt(row, 10).toString();
        }

        // WARNA BERDASARKAN CARA BAYAR / STATUS
        if (caraBayar.equals("UMUM")) {

            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(0, 0, 0));

        } else if (caraBayar.equals("BPJS")) {

            component.setBackground(new Color(143, 188, 144));
            component.setForeground(new Color(0, 0, 0));

        } else if (caraBayar.equals("LAIN-LAIN")
                || status.equals("Meninggal")
                || status.equals("Pulang Paksa")) {

            component.setBackground(new Color(50, 205, 50));
            component.setForeground(new Color(0, 0, 0));

        } else if (caraBayar.equals("BON KARYAWAN")) {

            component.setBackground(new Color(0, 0, 0));
            component.setForeground(new Color(255, 255, 255));

        } else if (caraBayar.equals("JAMPERSAL")) {

            component.setBackground(new Color(252, 182, 193));
            component.setForeground(new Color(0, 0, 0));
        }

        // JIKA ROW DIPILIH
        if (isSelected) {

            component.setForeground(Color.RED);

            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.PLAIN,
                    component.getFont().getSize() + 3
            ));

        } else {

            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.PLAIN,
                    11
            ));
        }

        return component;
    }
}