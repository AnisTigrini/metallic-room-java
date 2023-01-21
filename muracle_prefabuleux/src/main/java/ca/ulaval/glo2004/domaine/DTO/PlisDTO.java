package ca.ulaval.glo2004.domaine.DTO;

import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;

public class PlisDTO {
    public PositionPlis position = PositionPlis.UNKNOWN;
    
    public ConfigurationPlis configuration = ConfigurationPlis.UNKNOWN;
    
    public FractionImperialeDTO longueur;
    
    public FractionImperialeDTO epaisseurMur;
    
    public FractionImperialeDTO margeSoudure;
    
    public double angleSoudure;
    
    public FractionImperialeDTO margePlis;
    
    public TrouDTO trouVentilation = null;
}
