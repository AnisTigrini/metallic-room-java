package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.SensPanneau;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;
import java.util.ArrayList;

public class PanneauExterieur extends Panneau implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    private Plis plisDroite = new Plis(PositionPlis.DROITE, ConfigurationPlis.COIN);
    
    private Plis plisGauche = new Plis(PositionPlis.GAUCHE, ConfigurationPlis.COIN);
    
    protected FractionImperiale largeur = new FractionImperiale(50,0,1);
    
    protected FractionImperiale hauteur = new FractionImperiale(96,0,1);

    
    // ------------- Constructeurs ------------- //
    public PanneauExterieur(){
        this.setSens(SensPanneau.EXTERIEUR);
        this.setSelectionStatus(false);
    }
    
    public PanneauExterieur(FractionImperiale largeur, FractionImperiale hauteur, Plis plisDroite, Plis plisGauche){
        this.setSens(SensPanneau.EXTERIEUR);
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.plisDroite = plisDroite;
        this.plisGauche = plisGauche;
    }
    
    // ------------- Getters ------------- //
    public Plis getPlisDroite(){
        return this.plisDroite;
    }
    
    public Plis getPlisGauche(){
        return this.plisGauche;
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
    public void setPlisDroite(Plis plis){
        this.plisDroite = plis;
    }
    
    public void setPlisGauche(Plis plis){
        this.plisGauche = plis;
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
        int newCount = this.trous.size();
        int deleted = initialCount - newCount == initialCount ? initialCount : initialCount - newCount;
        return deleted;
    }
    
    public int deleteTrou(Trou trou) {
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
        this.getPlisDroite().aire();
        aireTotalPlis += this.getPlisDroite().aire();
        this.getPlisGauche().aire();
        aireTotalPlis += this.getPlisGauche().aire();

        return this.getLargeur().toDouble() * this.getHauteur().toDouble() + aireTotalPlis - aireTotalTrous;
    }
    
    public double poids(){
        return this.aire() * this.POIDS_MATIERE_PREMIERE;
    }

    //--------------- Getters et Setters en Cascade --------------//
    
    public void setEpaisseurDesMurs(FractionImperiale epaisseurMur) {
        this.plisDroite.setEpaisseurMurs(epaisseurMur);
        this.plisGauche.setEpaisseurMurs(epaisseurMur);
    }
    
    public void setLongueurDesPlis(FractionImperiale longueur) {
        this.plisDroite.setLongueur(longueur);
        this.plisGauche.setLongueur(longueur);
    }
    
    public void setMargeDesSoudures(FractionImperiale marge) {
        this.plisDroite.setMargeSoudure(marge);
        this.plisGauche.setMargeSoudure(marge);
    }
    
    public void setAngleDesSoudures(double angle) {
        this.plisDroite.setAngleSoudure(angle);
        this.plisGauche.setAngleSoudure(angle);
    }
    
    public void setMargeDesPlis(FractionImperiale marge) {
        this.plisDroite.setMargePlis(marge);
        this.plisGauche.setMargePlis(marge);
    }
    
    public FractionImperiale getEpaisseurDesMurs() {
        return this.plisDroite.getEpaisseurMurs();
    }
    
    public FractionImperiale getLongueurDesPlis() {
        return this.plisDroite.getLongueur();
    }
    
    public FractionImperiale getMargeDesSoudures() {
        return this.plisDroite.getMargeSoudure();
    }
    
    public double getAngleDesSoudures() {
        return this.plisDroite.getAngleSoudure();
    }
    
    public FractionImperiale getMargeDesPlis() {
        return this.plisDroite.getMargePlis();
    }
    
    @Override
    public PanneauExterieur clone() throws CloneNotSupportedException {
        PanneauExterieur panneauExterieureClone = (PanneauExterieur) super.clone();
        
        panneauExterieureClone.setPlisDroite((Plis) this.plisDroite.clone());
        panneauExterieureClone.setPlisGauche((Plis) this.plisGauche.clone());
        panneauExterieureClone.setLargeur((FractionImperiale) this.largeur.clone());
        panneauExterieureClone.setHauteur((FractionImperiale) this.hauteur.clone());
        
        ArrayList<Trou> trousClone = new ArrayList<Trou>();
        
        for (int i = 0; i < this.trous.size(); i++) {
            trousClone.add((Trou) this.trous.get(i).clone());
        }
        
        panneauExterieureClone.trous = trousClone;
                
        return panneauExterieureClone;
    }
}
