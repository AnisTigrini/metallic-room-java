
package ca.ulaval.glo2004.domaine.accessoires;

import ca.ulaval.glo2004.domaine.FractionImperiale;
import java.awt.geom.Point2D.Double;

/**
 * Abstract parent class from which extends Accessories classes
 */
public abstract class Accessoire implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    protected FractionImperiale hauteur;
    
    protected FractionImperiale largeur;
    
    protected FractionImperiale marge;
    
    protected boolean traversant;
    
    protected FractionImperiale coordonneeX;
    
    protected FractionImperiale coordonneeY;
    
    protected boolean selectionStatus;

    
    // ------------- Constructeurs ------------- //
    public Accessoire() {}
    
    public Accessoire(FractionImperiale hauteur, FractionImperiale largeur, FractionImperiale marge, boolean traversant, boolean selectionStatus) {
        this.hauteur = hauteur;
        this.largeur = largeur;
        this.marge = marge;
        this.setSelectionStatus(selectionStatus);
    }
    
    
    
    
    public Accessoire(Double point) {
        this.coordonneeX = FractionImperiale.doubleToFraction(point.getX());
        this.coordonneeY = FractionImperiale.doubleToFraction(point.getY());
    }

    // ------------- Setters ------------- //
    public void setHauteur(FractionImperiale hauteur) throws IllegalArgumentException {
        if (!(hauteur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
                
        this.hauteur = hauteur;
    }
    
    public void setLargeur(FractionImperiale largeur) throws IllegalArgumentException {
        if (!(largeur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
                
        this.largeur = largeur;
    }
    
    public void setMarge(FractionImperiale marge) throws IllegalArgumentException {
        if (!(marge instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.marge = marge;
    }
    
    public void setCoordonneX(FractionImperiale coordonneX) throws IllegalArgumentException {
        if (!(coordonneX instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.coordonneeX = coordonneX;
    }
    
    public void setCoordonneY(FractionImperiale coordonneY) throws IllegalArgumentException {
        if (!(coordonneY instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.coordonneeY = coordonneY;
    }
    
    public void setTraversant(boolean value) {
        this.traversant = value;
    }
    
    public void setSelectionStatus(boolean status) {
        this.selectionStatus = status;
    }
    
    // ------------- Getters ------------- //
    public FractionImperiale getHauteur() {
        return this.hauteur;
    }
    
    public FractionImperiale getLargeur() {
        return this.largeur;
    }
    
    public FractionImperiale getMarge () {
        return this.marge;
    }
    
    public boolean getSelectionStatus() {
        return this.selectionStatus;
    }
    
    public FractionImperiale getCoordonneeX() {
        return this.coordonneeX;
    }
    
    public FractionImperiale getCoordonneeY() {
        return this.coordonneeY;
    }
    
    // ------------- Utilitaires ------------- //
    public double aire() {
        FractionImperiale hauteurAvecMarge = this.hauteur.additionnerFraction(this.marge);
        FractionImperiale largeurAvecMarge = this.largeur.additionnerFraction(this.marge);
        return hauteurAvecMarge.toDouble() * largeurAvecMarge.toDouble(); //surface;
    }
    
    public boolean estTraversant() {
        return this.traversant;
    }
    
    @Override
    public Accessoire clone() throws CloneNotSupportedException {
        Accessoire accessoireClone = (Accessoire) super.clone();
        
        accessoireClone.setHauteur((FractionImperiale) this.hauteur.clone());
        accessoireClone.setLargeur((FractionImperiale) this.largeur.clone());
        accessoireClone.setMarge((FractionImperiale) this.marge.clone());
        accessoireClone.setCoordonneX((FractionImperiale) this.coordonneeX.clone());
        accessoireClone.setCoordonneY((FractionImperiale) this.coordonneeY.clone());
        
        return accessoireClone;
    }
}
