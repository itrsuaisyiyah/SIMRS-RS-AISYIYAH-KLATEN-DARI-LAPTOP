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

public class WarnaTableKasirRalan extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        Component component = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        // =========================================================
        // PENGAMAN AGAR TIDAK ERROR ArrayIndexOutOfBoundsException
        // ketika JTable sedang kosong / di-refresh
        // =========================================================
        if (row < 0 || row >= table.getRowCount()) {
            return component;
        }

        if (column < 0 || column >= table.getColumnCount()) {
            return component;
        }

        // =========================================================
        // WARNA DEFAULT
        // =========================================================
        component.setBackground(new Color(255, 255, 255));
        component.setForeground(new Color(0, 0, 0));

        // =========================================================
        // AMBIL STATUS
        // Kolom 10 harus tersedia
        // =========================================================
        Object nilaiStatus = null;

        if (table.getColumnCount() > 10) {
            nilaiStatus = table.getValueAt(row, 10);
        }

        String status = (nilaiStatus == null)
                ? ""
                : nilaiStatus.toString();

        // =========================================================
        // AMBIL STATUS BAYAR
        // Kolom 15 harus tersedia
        // =========================================================
        Object nilaiStatusBayar = null;

        if (table.getColumnCount() > 15) {
            nilaiStatusBayar = table.getValueAt(row, 15);
        }

        String statusBayar = (nilaiStatusBayar == null)
                ? ""
                : nilaiStatusBayar.toString();

        boolean statusKhusus = false;

        // =========================================================
        // STATUS PASIEN
        // =========================================================
        if (status.equals("Sudah")) {

            component.setBackground(new Color(150, 0, 0));
            component.setForeground(new Color(255, 255, 255));
            statusKhusus = true;

        } else if (status.equals("Batal")) {

            component.setBackground(new Color(255, 243, 109));
            component.setForeground(new Color(0, 0, 0));
            statusKhusus = true;

        } else if (status.equals("Berkas Diterima")
                || status.equals("Meninggal")
                || status.equals("Pulang Paksa")) {

            component.setBackground(new Color(50, 205, 50));
            component.setForeground(new Color(0, 0, 0));
            statusKhusus = true;

        } else if (status.equals("Dirawat")) {

            component.setBackground(new Color(119, 221, 119));
            component.setForeground(new Color(245, 255, 245));
            statusKhusus = true;
        }

        // =========================================================
        // STATUS BAYAR
        // =========================================================
        if (statusBayar.equals("Sudah Bayar")) {

            component.setBackground(new Color(25, 25, 25));
            component.setForeground(new Color(255, 255, 255));
            statusKhusus = true;
        }

        // =========================================================
        // KOLOM 8 = FONT BOLD + MERAH
        // =========================================================
        if (column == 8) {

            component.setFont(
                    component.getFont().deriveFont(Font.BOLD)
            );

            component.setForeground(Color.RED);

            if (component.getBackground().equals(
                    new Color(150, 0, 0))) {

                component.setForeground(Color.WHITE);
            }

            if (component.getBackground().equals(
                    new Color(25, 25, 25))) {

                component.setForeground(Color.WHITE);
            }
        }

        // =========================================================
        // JIKA ROW DIPILIH
        // =========================================================
        if (isSelected) {

            component.setBackground(new Color(0, 50, 150));
            component.setForeground(Color.WHITE);

            component.setFont(new Font(
                    component.getFont().getName(),
                    Font.BOLD,
                    component.getFont().getSize() + 2
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

    
    
