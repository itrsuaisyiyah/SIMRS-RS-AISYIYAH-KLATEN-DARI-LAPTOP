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
public class WarnaTableRegistrasi extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        Color bg;
        Color fg;

        // WARNA DASAR ROW
        if (row % 2 == 1) {
            bg = new Color(255,244,244);
            fg = new Color(50,50,50);
        } else {
            bg = new Color(255,255,255);
            fg = new Color(50,50,50);
        }

        // AMAN DARI NULL
        String penjamin = "";
        String status = "";

        if (table.getColumnCount() > 12 && table.getValueAt(row, 12) != null) {
            penjamin = table.getValueAt(row, 12).toString().trim();
        }

        if (table.getColumnCount() > 10 && table.getValueAt(row, 10) != null) {
            status = table.getValueAt(row, 10).toString().trim();
        }

        // WARNA BERDASARKAN PENJAMIN
        if ("UMUM".equals(penjamin)) {

            bg = new Color(255,255,255);
            fg = new Color(0,0,0);

        } else if ("BPJS".equals(penjamin)) {

            bg = new Color(143,188,144);
            fg = new Color(0,0,0);

        } else if ("LAIN-LAIN".equals(penjamin)
                || "Meninggal".equals(status)
                || "Pulang Paksa".equals(status)) {

            bg = new Color(50,205,50);
            fg = new Color(0,0,0);

        } else if ("BON KARYAWAN".equals(penjamin)) {

            bg = new Color(0,0,0);
            fg = new Color(255,255,255);

        } else if ("JAMPERSAL".equals(penjamin)) {

            bg = new Color(252,182,193);
            fg = new Color(0,0,0);
        }

                // WARNA NORMAL
                component.setBackground(bg);
                component.setForeground(fg);

                // JIKA ROW DIPILIH
        if (isSelected) {
            component.setForeground(Color.RED);

            // FONT MENJADI BOLD DAN LEBIH BESAR
            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.PLAIN,
                    component.getFont().getSize() + 3
            ));
        } else {

            // FONT NORMAL
            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.PLAIN,
                    11
            ));
        }



                // KOLOM 23 FONT MERAH TERUS
                if (column == 23) {
            component.setForeground(Color.RED);
        }

                return component;
            }
}