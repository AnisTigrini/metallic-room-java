package ca.ulaval.glo2004.domaine.afficheur;
import ca.ulaval.glo2004.domaine.ControlleurSalle;

public abstract class Afficheur {
    
    protected final ControlleurSalle controller;
    protected double zoomValue;
    
    public Afficheur(ControlleurSalle controller, double zoomValue){
        this.controller = controller;
        this.zoomValue = zoomValue;
    }
    

}
