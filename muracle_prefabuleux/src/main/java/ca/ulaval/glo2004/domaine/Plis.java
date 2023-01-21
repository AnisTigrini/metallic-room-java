package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;
import java.lang.Math;

public class Plis implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    private PositionPlis position = PositionPlis.UNKNOWN;
    
    private ConfigurationPlis configuration = ConfigurationPlis.UNKNOWN;
    
    private FractionImperiale longueur = new FractionImperiale(95,0,1);
    
    private FractionImperiale epaisseurMur = new FractionImperiale(4,0,1);
    
    private FractionImperiale margeSoudure = new FractionImperiale(2,0,1);
    
    private double angleSoudure = 45.0;
    
    private FractionImperiale margePlis = new FractionImperiale(0,1,4);
    
    private Trou trouVentilation = null;
    
    // ------------- Constructeurs ------------- //
    public Plis(){}
    
    public Plis(PositionPlis position, ConfigurationPlis configuration, FractionImperiale longueur, FractionImperiale epaisseurMur, FractionImperiale margeSoudure, double angleSoudure, FractionImperiale margePlis) {
        this.position = position;
        this.configuration = configuration;
        this.longueur = longueur;
        this.epaisseurMur = epaisseurMur;
        this.margeSoudure = margeSoudure;
        this.angleSoudure = angleSoudure;
        this.margePlis = margePlis;
    }
    
    public Plis(PositionPlis position, ConfigurationPlis configuration, FractionImperiale longueur, FractionImperiale epaisseurMur, FractionImperiale margeSoudure, double angleSoudure, FractionImperiale margePlis, Trou trouVentilation) {
        this.position = position;
        this.configuration = configuration;
        this.longueur = longueur;
        this.epaisseurMur = epaisseurMur;
        this.margeSoudure = margeSoudure;
        this.angleSoudure = angleSoudure;
        this.margePlis = margePlis;
        this.trouVentilation = trouVentilation;
    }
    
    public Plis(PositionPlis position, ConfigurationPlis configuration){
        this.position = position;
        this.configuration = configuration;
    }

    // ------------- Setters ------------- //
    public void setPosition(PositionPlis position){
        this.position = position;
    }
    
    public void setConfiguration(ConfigurationPlis configuration){
        this.configuration = configuration;
    }
    
    public void setLongueur(FractionImperiale longueur){
        if (!(longueur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.longueur = longueur;
    }
    
    public void setEpaisseurMurs(FractionImperiale epaisseurMur) throws IllegalArgumentException {
        if (!(epaisseurMur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.epaisseurMur = epaisseurMur;
    }
    
    public void setMargeSoudure(FractionImperiale margeSoudure){
        if (!(margeSoudure instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.margeSoudure = margeSoudure;
    }
    
    public void setAngleSoudure(double angleSoudure) throws IllegalArgumentException {
        if (angleSoudure < 0 || angleSoudure > 90) {
            throw new IllegalArgumentException("L'angle doit être entre 0 et 360 degrés.");
        }
        this.angleSoudure = angleSoudure;
    }
    
    public void setMargePlis(FractionImperiale margePlis) throws IllegalArgumentException {
        if (!(margePlis instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }     
        this.margePlis = margePlis;
    }
    
    public void setTrouVentilation(Trou trou) {
        this.trouVentilation = trou;
    }

    // ------------- Getters ------------- //
    public PositionPlis getPosition(){
        return this.position;
    }
    
    public ConfigurationPlis getConfiguration(){
        return this.configuration;
    }
    
    public FractionImperiale getLongueur(){
        return this.longueur;
    }
    
    public FractionImperiale getEpaisseurMurs(){
        return this.epaisseurMur;
    }
    
    public FractionImperiale getMargeSoudure(){
        return this.margeSoudure;
    }
    
    public double getAngleSoudure(){
        return this.angleSoudure;
    }
    
    public FractionImperiale getMargePlis(){
        return this.margePlis;
    }
    
    public Trou getTrouVentilation() {
        return this.trouVentilation;
    }

    // ------------- Delete ------------- //
    public int deleteTrouVentilation() {
        if (!this.hasTrouVentilation()) {
            throw new IllegalArgumentException("Le plis n'a pas de trou de ventilation à supprimer.");
        }
        this.setTrouVentilation(null);
        if (this.trouVentilation == null) {
            return 1;
        } else {
            return 0;
        }
    }

    // ------------- Utilitaires ------------- //
    public boolean hasTrouVentilation(){
        return this.trouVentilation == null ? false : true;
    }
    
    public double aire() {
        double largeur = this.epaisseurMur.toDouble() + (this.margePlis.toDouble() * 2);
        double hauteur = this.margeSoudure.toDouble();
        double angle = this.angleSoudure;
        double grandeBase = this.longueur.toDouble();
        double petiteBase = grandeBase - (hauteur * Math.cos(angle)) - (hauteur * Math.cos(angle)); // b−hcotα−hcotβ = b−h(cotα+cotβ) pour trouver petite base
        double partieTrapezoide;
        if (this.hasTrouVentilation()) {
            partieTrapezoide = (((petiteBase + grandeBase) * hauteur) / 2) - this.trouVentilation.aire();
        } else {
            partieTrapezoide = ((petiteBase + grandeBase) * hauteur) / 2;
        }
        double partieRectangulaire = (grandeBase * largeur);
        double surface = (partieRectangulaire + partieTrapezoide);
        return surface;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        Plis plisClone = (Plis) super.clone();
        
        plisClone.setEpaisseurMurs((FractionImperiale) this.epaisseurMur.clone());
        plisClone.setLongueur((FractionImperiale) this.longueur.clone());
        plisClone.setMargePlis((FractionImperiale) this.margePlis.clone());
        plisClone.setMargeSoudure((FractionImperiale) this.margeSoudure.clone());
        
        if (this.trouVentilation != null) {
            plisClone.setTrouVentilation(this.trouVentilation);
        }
        
        return plisClone;
    }
    
}
