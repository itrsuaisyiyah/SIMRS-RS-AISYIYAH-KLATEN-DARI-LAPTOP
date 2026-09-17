package fungsi;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
public class WarnaTableSoapRanap extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if (row % 2 == 1) {
            component.setBackground(new Color(255, 244, 244));
            component.setForeground(new Color(50, 50, 50));
        } else {
            component.setBackground(new Color(255, 255, 255));
            component.setForeground(new Color(50, 50, 50));
        }
        
        if (table.getColumnCount() > 23) {
            Object pemeriksa = table.getValueAt(row, 23);
            
            if (pemeriksa != null) {
                String dokterParamedis = pemeriksa.toString().toLowerCase();
                
                if (dokterParamedis.contains("dr") && dokterParamedis.contains("sp")) {
                    component.setBackground(new Color(255, 253, 208));
                    component.setForeground(new Color(0, 0, 0));
                }
                else if (dokterParamedis.contains("dr")) {
                    component.setBackground(new Color(220, 245, 220));
                    component.setForeground(new Color(0, 0, 0));
                } 
                else {
                    component.setBackground(Color.WHITE);
                    component.setForeground(Color.BLACK);
                }
            }
        }
        
        if (column == 1 || column == 2 || column == 4 || column == 5 || column == 6 
                || column == 7 || column == 8 || column == 9 || column == 10 || column == 11
                || column == 12 || column == 13 || column == 14) {
            setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            setHorizontalAlignment(SwingConstants.LEFT);
        }
        
        if (isSelected) {
            component.setBackground(Color.WHITE);
            component.setForeground(Color.RED);
        }
        
        return component;
    }
}