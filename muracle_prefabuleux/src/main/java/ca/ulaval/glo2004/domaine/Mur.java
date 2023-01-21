package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.accessoires.AccessoireRetourAir;
import java.util.ArrayList;

public class Mur implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    protected int position = 0;
    
    protected FractionImperiale coordonneeX = new FractionImperiale(0,0,1);
    
    protected PanneauInterieur panneauInterieur = new PanneauInterieur();
    
    protected PanneauExterieur panneauExterieur = new PanneauExterieur();
    
    protected boolean selectionStatus = false;
    
    protected AccessoireRetourAir retourAir = null;
    
    protected FractionImperiale hauteurSolRetourAir = new FractionImperiale(12, 0,1);

    // ------------- Constructeurs ------------- //
    public Mur(){}
    
    public Mur(int position){
        this.setPosition(position);
    }
    
    public Mur(int position, PanneauInterieur panneauInterieur, PanneauExterieur panneauExterieur, boolean selectionStatus){
        this.setPosition(position);
        this.setPanneauInterieur(panneauInterieur);
        this.setPanneauExterieur(panneauExterieur);
        this.setSelectionStatus(selectionStatus);
    }
    
    public Mur(int position, PanneauInterieur panneauInterieur, PanneauExterieur panneauExterieur, boolean selectionStatus, AccessoireRetourAir retourAir){
        this.setPosition(position);
        this.setPanneauInterieur(panneauInterieur);
        this.setPanneauExterieur(panneauExterieur);
        this.setSelectionStatus(selectionStatus);
        this.setRetourAir(retourAir);
    }
    
    public Mur(int position, PanneauInterieur panneauInterieur, PanneauExterieur panneauExterieur, boolean selectionStatus, AccessoireRetourAir retourAir, FractionImperiale hauteur){
        this.setPosition(position);
        this.setPanneauInterieur(panneauInterieur);
        this.setPanneauExterieur(panneauExterieur);
        this.setSelectionStatus(selectionStatus);
        this.setRetourAir(retourAir);
        this.setHauteurSolRetourAir(hauteur);
    }
    
    public Mur(int position, PanneauInterieur panneauInterieur, PanneauExterieur panneauExterieur, boolean selectionStatus, AccessoireRetourAir retourAir, FractionImperiale hauteur, FractionImperiale coordonneeX){
        this.setPosition(position);
        this.setPanneauInterieur(panneauInterieur);
        this.setPanneauExterieur(panneauExterieur);
        this.setSelectionStatus(selectionStatus);
        this.setRetourAir(retourAir);
        this.setHauteurSolRetourAir(hauteur);
        this.setCoordonneeX(coordonneeX);
    }
    
    // ------------- Setters ------------- //
    public void setPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("On ne peut pas entrer une position de mur négative.");
        }
        this.position = position;
    }
    
    public void setPanneauInterieur(PanneauInterieur panneauInterieur) throws IllegalArgumentException {
        if (!(panneauInterieur instanceof PanneauInterieur)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de panneau intérieur.");
        }
        this.panneauInterieur = panneauInterieur;
    }
    
    public void setPanneauExterieur(PanneauExterieur panneauExterieur) throws IllegalArgumentException {
        if (!(panneauExterieur instanceof PanneauExterieur)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de panneau extérieur.");
        }
        this.panneauExterieur = panneauExterieur;
    }
    
    public void setSelectionStatus(boolean selectionStatus){
        this.selectionStatus = selectionStatus;
    }
    
    public void setRetourAir(AccessoireRetourAir retourAir){
        this.retourAir = retourAir;
    }
    
    public void setHauteurSolRetourAir(FractionImperiale hauteur){
        this.hauteurSolRetourAir = hauteur;
    }
    
    public void setLargeurExterieure(FractionImperiale dimension){
        this.panneauExterieur.setLargeur(dimension);
    }
    
    public void setLargeurInterieure(FractionImperiale dimension){
        this.panneauInterieur.setLargeur(dimension);
    }
    
    public void setCoordonneeX(FractionImperiale coordonnee){
        this.coordonneeX = coordonnee;
    }
    
    public void setHauteurDesPanneaux(FractionImperiale hauteur) {
        this.panneauExterieur.setHauteur(hauteur);
        this.panneauInterieur.setHauteur(hauteur);
    }

    
    // ------------- Getters ------------- //
    public int getPosition(){
        return this.position;
    }
    
    public PanneauInterieur getPanneauInterieur(){
        return this.panneauInterieur;
    }
    
    public PanneauExterieur getPanneauExterieur(){
        return this.panneauExterieur;
    }

    public ArrayList<Panneau> getBothPanneaux(){
        ArrayList<Panneau> panneaux = new ArrayList<Panneau>();
        panneaux.add(this.panneauInterieur);
        panneaux.add(this.panneauExterieur);
        return panneaux;
    }
    
    public boolean getSelectionStatus(){
        return this.selectionStatus;
    }
    
    public AccessoireRetourAir getRetourAir(){
        return this.retourAir;
    }
    
    public FractionImperiale getHauteurSolRetourAir(){
        return this.hauteurSolRetourAir;
    }

    public FractionImperiale getLargeurExterieure(){
        return panneauExterieur.getLargeur();
    }
    
    public FractionImperiale getLargeurInterieure(){
        return panneauInterieur.getLargeur();
    }
    
    public FractionImperiale getCoordonneeX(){
        return coordonneeX;
    }

    
    // ------------- Utilitaires ------------- //

    public boolean hasRetourAir(){
        return this.retourAir != null ? true : false;
    }
    
    
    //--------------- Getters et Setters en Cascade --------------//
    
    public void setEpaisseurDesMurs(FractionImperiale epaisseurMur) {
        this.panneauInterieur.setEpaisseurDesMurs(epaisseurMur);
        this.panneauExterieur.setEpaisseurDesMurs(epaisseurMur);
    }
    
    public void setLongueurDesPlis(FractionImperiale longueur) {
        this.panneauInterieur.setLongueurDesPlis(longueur);
        this.panneauExterieur.setLongueurDesPlis(longueur);
    }
    
    public void setMargeDesSoudures(FractionImperiale marge) {
        this.panneauInterieur.setMargeDesSoudures(marge);
        this.panneauExterieur.setMargeDesSoudures(marge);
    }
    
    public void setAngleDesSoudures(double angle) {
        this.panneauInterieur.setAngleDesSoudures(angle);
        this.panneauExterieur.setAngleDesSoudures(angle);
    }
    
    public void setMargeDesPlis(FractionImperiale marge) {
        this.panneauInterieur.setMargeDesPlis(marge);
        this.panneauExterieur.setMargeDesPlis(marge);
    }
    
    public FractionImperiale getEpaisseurDesMurs() {
        return this.panneauExterieur.getEpaisseurDesMurs();
    }
    
    public FractionImperiale getLongueurDesPlis() {
        return this.panneauExterieur.getLongueurDesPlis();
    }
    
    public FractionImperiale getMargeDesSoudures() {
        return this.panneauExterieur.getMargeDesSoudures();
    }
    
    public double getAngleDesSoudures() {
        return this.panneauExterieur.getAngleDesSoudures();
    }
    
    public FractionImperiale getMargeDesPlis() {
        return this.panneauExterieur.getMargeDesPlis();
    }
    
    @Override
    public Mur clone() throws CloneNotSupportedException {
        Mur murClone = (Mur) super.clone();
        
        murClone.setPanneauInterieur((PanneauInterieur) this.panneauInterieur.clone());
        murClone.setPanneauExterieur((PanneauExterieur) this.panneauExterieur.clone());
        
        return murClone;
    }
}
