package ca.ulaval.glo2004.domaine.DTO;

import ca.ulaval.glo2004.domaine.DTO.accessoires.AccessoireRetourAirDTO;

public class MurDTO {
    public int position = 0;
    
    public PanneauInterieurDTO panneauInterieur;
    
    public PanneauExterieurDTO panneauExterieur;
    
    public boolean selectionStatus = false;
    
    public AccessoireRetourAirDTO retourAir = null;
    
    public FractionImperialeDTO hauteurSolRetourAir;

}
