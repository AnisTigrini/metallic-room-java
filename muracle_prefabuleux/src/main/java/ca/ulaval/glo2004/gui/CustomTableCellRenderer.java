package ca.ulaval.glo2004.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

// Classe pour changer la couleur des cellules de la table.
public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
        Component component = super.getTableCellRendererComponent(jtable, o, bln1, bln1, i, i1);
        
        if (i1 == 1 || i1 == 2) {
            Integer valeur = (Integer) jtable.getModel().getValueAt(i, i1);
            
            // Colorier le component en consequence.
            component.setBackground(valeur == null ? Color.RED : valeur < 0 ? Color.RED : Color.getHSBColor(195,207,217));
            
            // Attraper l'autre component.            
            Integer valeurPartieEntiere = (Integer) jtable.getModel().getValueAt(i, i1 == 1 ? 2 : 1);
            
            // Si les deux colonnes contienne 0 c'est invalide.
            if (valeur != null && valeurPartieEntiere != null) {
                if (valeurPartieEntiere == 0 && valeur == 0) {
                    super.getTableCellRendererComponent(jtable, o, bln1, bln1, i, i1 == 1 ? 2 : 1).setBackground(Color.RED);
                }
            }
        } 
        else if (i1 == 3) { // Colomne 3 pour les choix de dénominateur de fractions.
            String valeur = (String) jtable.getModel().getValueAt(i, i1);
            component.setBackground(valeur == null ? Color.RED : Color.getHSBColor(195,207,217));
        }
   
        return component;
    }
}
