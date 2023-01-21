package ca.ulaval.glo2004.domaine.DTO;

public class SalleDTO {
    public CoteDTO coteNord;
    
    public CoteDTO coteSud;
    
    public CoteDTO coteOuest;
    
    public CoteDTO coteEst;
    
    public FractionImperialeDTO longueurSalle;
    
    public FractionImperialeDTO largeurSalle;
    
    public FractionImperialeDTO epaisseurDesMurs;
    
    public FractionImperialeDTO longueurDesPlis;
    
    public FractionImperialeDTO margeDesSoudures;
    
    public double angleDesSoudures = 45.0;
    
    public FractionImperialeDTO margeDesPlis;
    
    public FractionImperialeDTO hauteurDesMurs;

    public FractionImperialeDTO hauteurDesRetoursAir;
    
    public FractionImperialeDTO hauteurSolDesRetoursAir;
    
    public FractionImperialeDTO margeDesFenetres;
}
