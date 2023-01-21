package ca.ulaval.glo2004.domaine.accessoires;

import ca.ulaval.glo2004.domaine.FractionImperiale;

public class AccessoireRetourAir extends Accessoire implements java.io.Serializable {
    
    protected int indexMurCorrespondant = 0;
    
    public AccessoireRetourAir() {
        this.hauteur = new FractionImperiale(8,0,1);
        this.largeur = new FractionImperiale(24,0,1);
        this.marge = new FractionImperiale(0,0,1);
        this.traversant = false;
        this.coordonneeX = new FractionImperiale(10,0,1);
        this.coordonneeY = new FractionImperiale(10,0,1);
        this.selectionStatus = false;
    }
    
    public AccessoireRetourAir(int index) {
        this.hauteur = new FractionImperiale(8,0,1);
        this.largeur = new FractionImperiale(24,0,1);
        this.marge = new FractionImperiale(0,0,1);
        this.traversant = false;
        this.coordonneeX = new FractionImperiale(10,0,1);
        this.coordonneeY = new FractionImperiale(10,0,1);
        this.selectionStatus = false;
        this.indexMurCorrespondant = index;
    }

    public AccessoireRetourAir(FractionImperiale hauteur, FractionImperiale largeur, FractionImperiale marge, boolean traversant, boolean selectionStatus) {
        super(hauteur, largeur, marge, traversant, selectionStatus);
    }
    
    
    //A VALIDER
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoireRetourAir(double pointx, double pointy){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(8,0,1);
        this.largeur = new FractionImperiale(24,0,1);
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
    
    //IMPORTANT DE CONSERVER CE CONSTRUCTEUR
    public AccessoireRetourAir(double pointx, double pointy, FractionImperiale largeur){
        this.coordonneeX = FractionImperiale.doubleToFraction(pointx);
        this.coordonneeY = FractionImperiale.doubleToFraction(pointy);
        this.hauteur = new FractionImperiale(8,0,1);
        this.largeur = largeur;
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
    }
    
    public AccessoireRetourAir(int index, FractionImperiale largeur){
        this.coordonneeX = new FractionImperiale(10,0,1);
        this.coordonneeY = new FractionImperiale(10,0,1);
        this.hauteur = new FractionImperiale(8,0,1);
        this.largeur = largeur;
        this.marge = new FractionImperiale(0,1,8);
        this.traversant = true;
        this.selectionStatus = false;
        this.indexMurCorrespondant = index;
    }
    
    public void setIndexMurCorrespondant(int index){
        this.indexMurCorrespondant = index;
    }
    
    public int getIndexMurCorrespondant(){
        return this.indexMurCorrespondant;
    }
}