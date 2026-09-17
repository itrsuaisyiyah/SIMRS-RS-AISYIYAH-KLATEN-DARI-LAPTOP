/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Owner
 */
public class WarnaTablePermintaanResepRanap extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,244,244));
            component.setForeground(new Color(188,33,114));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(188,33,114));
        } 
        // DIPERBAIKI: sebelumnya table.getValueAt(row,7).toString() dipanggil langsung tanpa
        // cek null - berpotensi NullPointerException di tengah render (lihat komentar yang
        // sama di WarnaTablePermintaanResepRalan.java).
        Object nilaiStatus = (table.getColumnCount() > 7) ? table.getValueAt(row,7) : null;
        String status = (nilaiStatus == null) ? "" : nilaiStatus.toString();
        if(status.equals("Belum Terlayani")){
            component.setBackground(new Color(252,182,193));
            component.setForeground(new Color(0,0,0));
        }else if(status.equals("Sudah Terlayani")){
            component.setBackground(new Color(143,188,144));
            component.setForeground(new Color(0,0,0));
      
        }
        
         if (isSelected) {
            component.setForeground(new Color(255,255,255));
        }
        return component;
    }

}
