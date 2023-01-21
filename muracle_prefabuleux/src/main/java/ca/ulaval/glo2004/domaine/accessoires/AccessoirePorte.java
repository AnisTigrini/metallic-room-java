package ca.ulaval.glo2004.domaine.accessoires;

import ca.ulaval.glo2004.domaine.FractionImperiale;

public class AccessoirePorte extends Accessoire implements java.io.Serializable {
    
    public AccessoirePorte() {
        this.hauteur = new FractionImperiale(88,0,1);
        this.largeur = new FractionImperiale(38,0,1);
        this.marge = new FractionImperiale(0,0,1);
        this.traversant = true;
        this.coordonneeX = new FractionImperiale(10,0,1);
        this.coordonneeY = new FractionImperiale(10,0,1);
        this.selectionStatus = false;
    }

    public AccessoirePorte(FractionImperiale hauteur, FractionImperiale largeur, FractionImperiale marge, boolean traversant, boolean selectionStatus) {
        super(hauteur, largeur, marge, traversant, selectionStatus);
    }
    
    
    //A VALIDER
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoirePorte(double pointx, double pointy){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(88,0,1);
        this.largeur = new FractionImperiale(38,0,1);
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
    
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoirePorte(double pointx, double pointy, FractionImperiale hauteurDesMurs){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = new FractionImperiale(hauteurDesMurs.getPartieEntiere()-88,0,1);
        this.hauteur = new FractionImperiale(88,0,1);
        this.largeur = new FractionImperiale(38,0,1);
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
}
