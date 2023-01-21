package ca.ulaval.glo2004.domaine.accessoires;

import ca.ulaval.glo2004.domaine.FractionImperiale;

public class AccessoirePriseElectrique extends Accessoire implements java.io.Serializable {
    
    public AccessoirePriseElectrique() {
        this.hauteur = new FractionImperiale(4,0,1);
        this.largeur = new FractionImperiale(2,0,1);
        this.marge = new FractionImperiale(0,0,1);
        this.traversant = false;
        this.coordonneeX = new FractionImperiale(10,0,1);
        this.coordonneeY = new FractionImperiale(10,0,1);
        this.selectionStatus = false;
    }

    public AccessoirePriseElectrique(FractionImperiale hauteur, FractionImperiale largeur, FractionImperiale marge, boolean traversant, boolean selectionStatus) {
        super(hauteur, largeur, marge, traversant, selectionStatus);
    }
    
    
    //A VALIDER
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoirePriseElectrique(double pointx, double pointy){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(4,0,1);
        this.largeur = new FractionImperiale(2,0,1);
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
    
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoirePriseElectrique(double pointx, double pointy, FractionImperiale largeur){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(4,0,1);
        this.largeur = largeur;
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
}
