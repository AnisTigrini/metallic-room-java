package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.SensPanneau;
import java.util.ArrayList;

public abstract class Panneau implements java.io.Serializable {
    
    // ------ Attributs ------ //
    protected SensPanneau sens;

    protected boolean selectionStatus = false;
    
    protected ArrayList<Trou> trous = new ArrayList<Trou>();
    
    protected static final double POIDS_MATIERE_PREMIERE = 6.3 * 144;
    
    protected FractionImperiale coordonneeX = new FractionImperiale(0,0,1);
    
    
    // ------ Getters ------ //
    public void setSens(SensPanneau sens){
        this.sens = sens;
    }

    public void setSelectionStatus(boolean selectionStatus){
        this.selectionStatus = selectionStatus;
    }
    
    public void setCoordonneeX(FractionImperiale coordonnee){
        this.coordonneeX = coordonnee;
    }

    // ------ Setters ------ //
    public SensPanneau getSens(){
        return this.sens;
    }

    public boolean getSelectionStatus(){
        return this.selectionStatus;
    }
    
    public ArrayList<Trou> getTrous() {
        return this.trous;
    }
    
    public FractionImperiale getCoordonneeX() {
        return this.coordonneeX;
    }
    
    public abstract FractionImperiale getLargeur();

    // ------ Utilitaires ------ //
    public int countTrous() {
        return this.trous.size();
    }
    
    public boolean estTrouee() {
        return (this.countTrous() > 0);
    }
    
    public abstract double aire();
    
}
