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
public class WarnaTableKamarInap extends DefaultTableCellRenderer {
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row % 2 == 1){
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(0,0,0));
        }else{
            component.setBackground(new Color(255,255,255));
            component.setForeground(new Color(0,0,0));
        } 
        if(table.getValueAt(row,19).toString().equals("")){
            component.setBackground(new Color(200,0,0));
            component.setForeground(new Color(255,230,230));
        }else if(table.getValueAt(row,10).toString().equals("Batal")){
            component.setBackground(new Color(255,243,109));
            component.setForeground(new Color(120,110,50));
        }else if(table.getValueAt(row,10).toString().equals("Dirawat")){
            component.setBackground(new Color(119,221,119));
            component.setForeground(new Color(245,255,245));
        }
        if(table.getValueAt(row,15).toString().equals("Sudah Bayar")){
            component.setBackground(new Color(50,50,50));
            component.setForeground(new Color(255,255,255));
        }
        
        // JIKA ROW DIPILIH -> tandai dengan warna merah sebagai indikator visual
        if (isSelected) {
            component.setBackground(new Color(0,50,150));
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
