package ca.ulaval.glo2004.domaine.DTO.accessoires;

import ca.ulaval.glo2004.domaine.DTO.FractionImperialeDTO;

public abstract class AccessoireDTO {
    public FractionImperialeDTO hauteur;
    
    public FractionImperialeDTO largeur;
    
    public FractionImperialeDTO marge;
    
    public boolean traversant;
    
    public FractionImperialeDTO coordonneeX;
    
    public FractionImperialeDTO coordonneeY;
    
    public boolean selectionStatus;
}
