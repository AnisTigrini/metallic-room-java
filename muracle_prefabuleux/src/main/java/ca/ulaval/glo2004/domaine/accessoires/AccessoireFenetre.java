package ca.ulaval.glo2004.domaine.accessoires;

import ca.ulaval.glo2004.domaine.FractionImperiale;

public class AccessoireFenetre extends Accessoire implements java.io.Serializable {
    
    public AccessoireFenetre() {
        this.hauteur = new FractionImperiale(12,0,1);
        this.largeur = new FractionImperiale(12,0,1);
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.coordonneeX = new FractionImperiale(10,0,1);
        this.coordonneeY = new FractionImperiale(10,0,1);
        this.selectionStatus = false;
    }

    public AccessoireFenetre(FractionImperiale hauteur, FractionImperiale largeur, FractionImperiale marge, boolean traversant, boolean selectionStatus) {
        super(hauteur, largeur, marge, traversant, selectionStatus);
    }
    
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoireFenetre(double pointx, double pointy){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(24,0,1);
        this.largeur = new FractionImperiale(24,0,1);
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
    
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoireFenetre(double pointx, double pointy, FractionImperiale largeur){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(24,0,1);
        this.largeur = largeur;
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
}