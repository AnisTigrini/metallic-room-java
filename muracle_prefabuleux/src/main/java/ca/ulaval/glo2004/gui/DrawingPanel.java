package ca.ulaval.glo2004.gui;

import java.awt.Graphics;
import ca.ulaval.glo2004.domaine.afficheur.AfficheurPlanSalle;
import ca.ulaval.glo2004.domaine.afficheur.AfficheurElevationCote;
import ca.ulaval.glo2004.domaine.afficheur.AfficheurProfilDecoupagePanneau;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import ca.ulaval.glo2004.domaine.enums.Sens;
import ca.ulaval.glo2004.domaine.enums.Vue;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;

public class DrawingPanel extends javax.swing.JPanel {
    
    private MainWindow mainWindow;
    public int height = 8000000;
    public int width = 8000000;
    private Vue currentVue;
    private Orientation currentOrientation;
    private Sens currentSens;
    private int indexPanneau = 0;
    protected double zoomValue = 3.0d;
    protected boolean modeAjoutFenetre = false;
    protected boolean modeAjoutPorte = false;
    protected boolean modeAjoutPriseElectrique = false;
    protected boolean modeAjoutRetourAir = false;
    
    /*
    No selection = 0
    Separateur  Nord et Sud = 1
    Separateur Est et Ouest = 2
    Mur = 3
    Accessoire = 4
    Retour Air = 5
    */
    protected int typeSelected = 0;
    
    public DrawingPanel() {
        
    }
    
    public DrawingPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
       
        this.currentVue = Vue.PLAN;
        this.currentOrientation = Orientation.NORD;
        this.currentSens = Sens.INTERIEUR;
        setBorder(new javax.swing.border.BevelBorder(BevelBorder.LOWERED));
        setVisible(true);
    }
    
    public double getZoomValue(){
        return this.zoomValue;
    }
    
    public void setZoomValue(double zoomValue){
        this.zoomValue = zoomValue;
    }
    
    public void zoomIn(){
        setZoomValue(getZoomValue()*1.1);
        this.width = (int)(this.width*1.1);
        this.height = (int)(this.height*1.1);
        this.setPreferredSize(new Dimension(width, height));
        this.setSize(new Dimension(width, height));
        this.repaint();
    }
    
    public void zoomOut(){
        //TODO SET MINIMUM VALUES
        setZoomValue(getZoomValue()*0.9);
        this.width = (int)(this.width*0.9);
        this.height = (int)(this.height*0.9);
        this.setPreferredSize(new Dimension(width, height));
        this.setSize(new Dimension(width, height));
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (mainWindow != null) {
            switch (currentVue) {
                case PLAN:
                    {
                        super.paintComponent(g);
                        afficherLaGrille(g);
                        AfficheurPlanSalle mainDrawer = new AfficheurPlanSalle(mainWindow.getControlleur(), getZoomValue());
                        mainDrawer.draw(g);
                        break;
                    }
                case ELEVATION_COTE:
                    {
                        super.paintComponent(g);
                        afficherLaGrille(g);
                        AfficheurElevationCote mainDrawer = new AfficheurElevationCote(mainWindow.getControlleur(), 
                                getZoomValue(), currentOrientation, currentSens);
                        mainDrawer.draw(g);
                        break;
                    }
                case PROFIL_DECOUPAGE:
                    {
                        super.paintComponent(g);
                        afficherLaGrille(g);
                        AfficheurProfilDecoupagePanneau mainDrawer = new AfficheurProfilDecoupagePanneau(mainWindow.getControlleur(), 
                                getZoomValue(), currentOrientation, currentSens, indexPanneau);
                        mainDrawer.draw(g);
                        break;
                    }
                default:
                    break;
            }
        }
    }
    public Vue getCurrentVue(){
        return this.currentVue;
    }
    
    public void setCurrentVue(Vue vue){
        this.currentVue = vue;
    }
    
    public Orientation getCurrentOrientation(){
        return this.currentOrientation;
    }
    
    public void setCurrentOrientation(Orientation orientation){
        this.currentOrientation = orientation;
    }
    
    public Sens getCurrentSens(){
        return this.currentSens;
    }
    
    public void setCurrentSens(Sens sens){
        this.currentSens = sens;
    }
    
    public int getTypeSelected(){
        return this.typeSelected;
    }
    
    public void setTypeSelected(int type){
        this. typeSelected = type;
    }
    
    public boolean getModeAjoutFenetre(){
        return this.modeAjoutFenetre;
    }
    
    public void setModeAjoutFenetre(boolean bool){
        this.modeAjoutFenetre = bool;
    }
    
    public boolean getModeAjoutPorte(){
        return this.modeAjoutPorte;
    }
    
    public void setModeAjoutPorte(boolean bool){
        this.modeAjoutPorte = bool;
    }
    
    public boolean getModeAjoutPriseElectrique(){
        return this.modeAjoutPriseElectrique;
    }
    
    public void setModeAjoutPriseElectrique(boolean bool){
        this.modeAjoutPriseElectrique = bool;
    }
    
    public boolean getModeAjoutRetourAir(){
        return this.modeAjoutRetourAir;
    }
    
    public void setModeAjoutRetourAir(boolean bool){
        this.modeAjoutRetourAir = bool;
    }
    
    public void afficherLaGrille(Graphics g) {
        boolean afficherGrile = mainWindow.getControlleur().getAfficherGrille();

        if (afficherGrile) {
            int largeureGrille = (int) Math.ceil(mainWindow.getControlleur().getLargeurGrille().toDouble() * zoomValue);
            int hauteurGrille = (int) Math.ceil(mainWindow.getControlleur().getHauteurGrille().toDouble() * zoomValue);
            
            if (largeureGrille != 0 && hauteurGrille != 0) {
                int hauteurDrawingPanel = this.getSize().height;
                int largeureDrawingPanel = this.getSize().width;
                int nombreDeLignes = hauteurDrawingPanel / hauteurGrille;
                int nombreDeColonnes = largeureDrawingPanel / largeureGrille;
                
                for (int i = 0; i < nombreDeLignes; i++) {
                    g.drawLine(0, i * hauteurGrille, largeureDrawingPanel, i * hauteurGrille);
                }
                
                for (int i = 0; i < nombreDeColonnes; i++) {
                    g.drawLine(i * largeureGrille , 0, i * largeureGrille, hauteurDrawingPanel);
                }
            }
        }
    }
    
    public int gatIndexPanneau() {
        return this.indexPanneau;
    }
    
    public void setIndexPanneau(int nouvelIndex) {
        this.indexPanneau = nouvelIndex;
    }
}
