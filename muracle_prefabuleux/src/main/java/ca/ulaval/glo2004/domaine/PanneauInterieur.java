package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.SensPanneau;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;
import java.util.ArrayList;

public class PanneauInterieur extends Panneau implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    private Plis plisHaut = new Plis(PositionPlis.HAUT, ConfigurationPlis.COIN);
    
    private Plis plisBas = new Plis(PositionPlis.BAS, ConfigurationPlis.COIN);
    
    protected FractionImperiale largeur = new FractionImperiale(50,0,1);
    
    protected FractionImperiale hauteur = new FractionImperiale(96,0,1);

    
    // ------------- Constructeurs ------------- //
    public PanneauInterieur(){
        this.setSens(SensPanneau.INTERIEUR);
        this.setSelectionStatus(false);
    }
    
    public PanneauInterieur(FractionImperiale largeur, FractionImperiale hauteur, Plis plisHaut, Plis plisBas){
        this.setSens(SensPanneau.INTERIEUR);
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.plisBas = plisBas;
        this.plisHaut = plisHaut;
    }

    
    // ------------- Getters ------------- //
    public Plis getPlisHaut(){
        return this.plisHaut;
    }
    
    public Plis getPlisBas(){
        return this.plisBas;
    }
    
    public FractionImperiale getLargeur(){
        return this.largeur;
    }
    
    public FractionImperiale getHauteur(){
        return this.hauteur;
    }
    
    public Trou getTrouAvecPlusGrandeLargeur() {
        Trou largestTrou = null;
        double largest = 0.0;
        for (Trou trou : this.trous) {
            double mesure = trou.getLargeur().toDouble();
            if (mesure > largest) {
                largest = mesure;
                largestTrou = trou;
            }
        }
        return largestTrou;
    }
    
    public Trou getTrouAvecPlusGrandeHauteur() {
        Trou highestTrou = null;
        double highest = 0.0;
        for (Trou trou : this.trous) {
            double mesure = trou.getHauteur().toDouble();
            if (mesure > highest) {
                highest = mesure;
                highestTrou = trou;
            }
        }
        return highestTrou;
    }

    
    // ------------- Setters ------------- //
    public void setPlisHaut(Plis plis){
        this.plisHaut = plis;
    }
    
    public void setPlisBas(Plis plis){
        this.plisBas = plis;
    }
    
    public void setLargeur(FractionImperiale largeur) throws IllegalArgumentException {
        if (!(largeur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.largeur = largeur;  
    }
    
    public void setHauteur(FractionImperiale hauteur) throws IllegalArgumentException {
        if (!(hauteur instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }   
        this.hauteur = hauteur;
    }
    
    // ------------- Add ------------- //
    public int addTrou(Trou trou) {
        int initialCount = this.trous.size();
        this.trous.add(trou);
        int newCount = this.trous.size();
        int added = newCount - initialCount;
        return added;
    }
    
    // ------------- Delete ------------- //
    public int deleteAllTrous() {
        int initialCount = this.trous.size();
        this.trous.clear();
        //Si le plis du haut est percé pour un retour d'air, supprimer le trou également
        if (this.plisHaut.hasTrouVentilation()) {
            this.plisHaut.deleteTrouVentilation();
        }
        int newCount = this.trous.size();
        int deleted = initialCount - newCount == initialCount ? initialCount : initialCount - newCount;
        return deleted;
    }
    
    public int deleteTrou(Trou trou) {
        // Attention : Si le Trou à supprimer est celui d'un retour d'air, appelez la méthode deleteTrouVentilation() du PlisHaut également.
        int initialCount = this.trous.size();
        this.trous.remove(trou);
        int newCount = this.trous.size();
        int deleted = initialCount - newCount == 1 ? 1 : 0;
        return deleted;
    }

    // ------------- Utilitaires ------------- //
    public double aire(){
        double aireTotalTrous = 0.0;
        double aireTotalPlis = 0.0;
        if (this.estTrouee()) {
            for(Trou trou : this.getTrous()){
                aireTotalTrous += trou.aire();
            }
        }
        
        aireTotalPlis += this.getPlisBas().aire();
        aireTotalPlis += this.getPlisHaut().aire();

        return this.getLargeur().toDouble() * this.getHauteur().toDouble() + aireTotalPlis - aireTotalTrous;
    }
    
    public double poids(){
        return this.aire() * this.POIDS_MATIERE_PREMIERE;
    }
    
    //--------------- Getters et Setters en Cascade --------------//
    
    public void setEpaisseurDesMurs(FractionImperiale epaisseurMur) {
        this.plisHaut.setEpaisseurMurs(epaisseurMur);
        this.plisBas.setEpaisseurMurs(epaisseurMur);
    }
    
    public void setLongueurDesPlis(FractionImperiale longueur) {
        this.plisHaut.setLongueur(longueur);
        this.plisBas.setLongueur(longueur);
    }
    
    public void setMargeDesSoudures(FractionImperiale marge) {
        this.plisHaut.setMargeSoudure(marge);
        this.plisBas.setMargeSoudure(marge);
    }
    
    public void setAngleDesSoudures(double angle) {
        this.plisHaut.setAngleSoudure(angle);
        this.plisBas.setAngleSoudure(angle);
    }
    
    public void setMargeDesPlis(FractionImperiale marge) {
        this.plisHaut.setMargePlis(marge);
        this.plisBas.setMargePlis(marge);
    }
    
    public FractionImperiale getEpaisseurDesMurs() {
        return this.plisBas.getEpaisseurMurs();
    }
    
    public FractionImperiale getLongueurDesPlis() {
        return this.plisBas.getLongueur();
    }
    
    public FractionImperiale getMargeDesSoudures() {
        return this.plisBas.getMargeSoudure();
    }
    
    public double getAngleDesSoudures() {
        return this.plisBas.getAngleSoudure();
    }
    
    public FractionImperiale getMargeDesPlis() {
        return this.plisBas.getMargePlis();
    }
    
    @Override
    public PanneauInterieur clone() throws CloneNotSupportedException {
        PanneauInterieur panneauInterieurClone = (PanneauInterieur) super.clone();
        
        panneauInterieurClone.setPlisHaut((Plis) this.plisHaut.clone());
        panneauInterieurClone.setPlisBas((Plis) this.plisBas.clone());
        panneauInterieurClone.setLargeur((FractionImperiale) this.largeur.clone());
        panneauInterieurClone.setHauteur((FractionImperiale) this.hauteur.clone());
        
        ArrayList<Trou> trousClone = new ArrayList<Trou>();
        
        for (int i = 0; i < this.trous.size(); i++) {
            trousClone.add((Trou) this.trous.get(i).clone());
        }
        
        panneauInterieurClone.trous = trousClone;
                
        return panneauInterieurClone;
    }
}
