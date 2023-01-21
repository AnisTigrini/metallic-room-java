package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.FractionImperiale;

public class Trou implements Cloneable, java.io.Serializable {
    
    // ------------- Attributs ------------- //
    protected FractionImperiale largeur = new FractionImperiale(0,0,1);
    
    protected FractionImperiale hauteur = new FractionImperiale(0,0,1);
    
    protected FractionImperiale coordonneeX = new FractionImperiale(0, 0, 1);
    
    protected FractionImperiale coordonneeY = new FractionImperiale(0, 0, 1);
    
    
    // ------------- Constructeurs ------------- //
    public Trou() {}
    
    public Trou(FractionImperiale largeur, FractionImperiale hauteur, FractionImperiale x, FractionImperiale y) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.coordonneeX = x;
        this.coordonneeY = y;
    }


    // ------------- Setters ------------- //
    public void setLargeur(FractionImperiale largeur) {
        if (!(largeur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.largeur = largeur;
    }
    
    public void setHauteur(FractionImperiale hauteur) {
        if (!(hauteur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.hauteur = hauteur;
    }
    
    public void setCoordonneeX(FractionImperiale x) {
        if (!(x instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.coordonneeX = x;
    }
    
    public void setCoordonneeY(FractionImperiale y) {
        if (!(y instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.coordonneeY = y;
    }

    // ------------- Getters ------------- //
    public FractionImperiale getLargeur() {
        return this.largeur;
    }
    
    public FractionImperiale getHauteur() {
        return this.hauteur;
    }
    
    public FractionImperiale getCoordooneeX() {
        return this.coordonneeX;
    }
    
    public FractionImperiale getCoordooneeY() {
        return this.coordonneeY;
    }
    
    // ------------- Utilitaires ------------- //
    protected double aire() {
        return this.hauteur.toDouble() * this.largeur.toDouble();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Trou trouClone = (Trou) super.clone();
        
        trouClone.setCoordonneeX((FractionImperiale) this.coordonneeX.clone());
        trouClone.setCoordonneeY((FractionImperiale) this.coordonneeY.clone());
        trouClone.setHauteur((FractionImperiale) this.hauteur.clone());
        trouClone.setLargeur((FractionImperiale) this.largeur.clone());
        
        return trouClone;
    }        
}
