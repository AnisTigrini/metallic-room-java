package ca.ulaval.glo2004.domaine.afficheur;

import ca.ulaval.glo2004.domaine.ControlleurSalle;
import ca.ulaval.glo2004.domaine.DTO.FractionImperialeDTO;
import ca.ulaval.glo2004.domaine.FractionImperiale;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.Cote;
import ca.ulaval.glo2004.domaine.Panneau;
import ca.ulaval.glo2004.domaine.Separateur;
import ca.ulaval.glo2004.domaine.accessoires.Accessoire;
import ca.ulaval.glo2004.domaine.accessoires.AccessoireFenetre;
import ca.ulaval.glo2004.domaine.accessoires.AccessoirePorte;
import ca.ulaval.glo2004.domaine.accessoires.AccessoirePriseElectrique;
import ca.ulaval.glo2004.domaine.accessoires.AccessoireRetourAir;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import ca.ulaval.glo2004.domaine.enums.Sens;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;

public class AfficheurElevationCote extends Afficheur {
    protected Orientation orientation;
    protected Sens sens;
    protected static final double POIDS_MATIERE_PREMIERE = 6.3 / 144;
    
    public AfficheurElevationCote(ControlleurSalle controller, double zoomValue, Orientation orientation, Sens sens){
        super(controller, zoomValue);
        this.orientation = orientation;
        this.sens = sens;
    }
    
    public void draw(Graphics g){ 
        controller.setErrorType(100);
        drawMurs(g);
        drawAccessoires(g);
        afficherEpaisseurMurs(g);
    }
    
    public Orientation getOrientation(){
        return this.orientation;
    }
    
    private void drawMurs(Graphics g){
        
        Stroke dotted = new BasicStroke(2.0f,                       //Width
                                        BasicStroke.CAP_SQUARE,     //End Cap
                                        BasicStroke.JOIN_MITER,     //Join Style
                                        10.0f,                      //Miter Limit
                                        new float[] {2.0f, 8.0f}, //Dash Pattern
                                        0.0f);                      //Dash Phase
        
        int hauteurMur = (int)(controller.getHauteurMurs().toDouble()*zoomValue);
        g.setColor(Color.WHITE);
        
        Stroke stroke = new BasicStroke((float)(zoomValue*2),       //Width
                                        BasicStroke.CAP_SQUARE,     //End Cap
                                        BasicStroke.JOIN_MITER,     //Join Style
                                        10.0f,                      //Miter Limit
                                        new float[] {1.0f, 0.0f},   //Dash Pattern
                                        0.0f);                      //Dash Phase
        
        Graphics2D mur = (Graphics2D) g;
                
        mur.setStroke(stroke);
        ArrayList<Double> largeurs = controller.getDimensionsMurs(getOrientation());
        int currentPosition = 20;
        double currentMesure = 0.0d;
        int currentIndex;
        ArrayList<Mur> murs;
        
        
        //Affichage miroir
        if ( ((orientation == Orientation.NORD || orientation == Orientation.EST) && sens == Sens.EXTERIEUR) ||
             ((orientation == Orientation.SUD || orientation == Orientation.OUEST) && sens == Sens.INTERIEUR) ){
            Collections.reverse(largeurs);
            currentIndex = largeurs.size() - 1;
            currentMesure = 0.0d;
            for (Double largeur : largeurs){
                currentMesure += largeur;
            }
            for (Double largeur : largeurs){
                if (controller.murIsSelected(orientation, currentIndex) == true) { 
                    mur.setColor(Color.GREEN);
                }

                int largeurMur = (int)(largeur*zoomValue);
                if ((orientation == Orientation.NORD || orientation == Orientation.SUD)){
                    if (currentMesure > controller.getLongueurSalle().toDouble()){
                        mur.setColor(Color.ORANGE);
                        controller.setErrorType(5);
                        if (controller.murIsSelected(orientation, currentIndex) == true) { 
                            mur.setColor(Color.GREEN);
                        }
                    }
                }
                else {
                    if (currentMesure > controller.getLargeurSalle().toDouble()){
                        mur.setColor(Color.ORANGE);
                        controller.setErrorType(5);
                        if (controller.murIsSelected(orientation, currentIndex) == true) { 
                            mur.setColor(Color.GREEN);
                        }
                    }
                }
                
                ArrayList<Accessoire> accessoires = controller.getSalle().getCoteNord().getAccessoires();
                FractionImperialeDTO longueurCote;
                switch (orientation) {
                    case NORD:
                        murs = controller.getSalle().getCoteNord().getMurs();
                        longueurCote = controller.getLongueurSalle();
                        accessoires = controller.getSalle().getCoteNord().getAccessoires();
                        break;
                    case SUD:
                        murs = controller.getSalle().getCoteSud().getMurs();
                        longueurCote = controller.getLongueurSalle();
                        accessoires = controller.getSalle().getCoteSud().getAccessoires();
                        break;
                    case OUEST:
                        murs = controller.getSalle().getCoteOuest().getMurs();
                        longueurCote = controller.getLargeurSalle();
                        accessoires = controller.getSalle().getCoteOuest().getAccessoires();
                        break;
                    case EST:
                        murs = controller.getSalle().getCoteEst().getMurs();
                        longueurCote = controller.getLargeurSalle();
                        accessoires = controller.getSalle().getCoteEst().getAccessoires();
                        break;
                    default:
                        murs = controller.getSalle().getCoteNord().getMurs();
                        longueurCote = controller.getLongueurSalle();
                        break;
                }
                
                if (murIsOverweight(murs.get(currentIndex)) == true){
                    mur.setColor(Color.red);
                }
                
                
                /* AFFICHAGE RETOUR AIR MIROIR*/
                
                mur.fill3DRect(currentPosition, 20, largeurMur, hauteurMur,true);
                
                if (murs.get(currentIndex).hasRetourAir() && sens == Sens.INTERIEUR){
                    mur.setColor(Color.blue);
                    AccessoireRetourAir currentRetour = murs.get(currentIndex).getRetourAir();
                    double retourAirX1 = currentMesure - largeur/2 - currentRetour.getLargeur().toDouble()/2;
                    double retourAirY1 = controller.getHauteurMurs().toDouble() - controller.getSalle().getHauteurSolDesRetoursAir().toDouble() - controller.getSalle().getHauteurDesRetoursAir().toDouble();
                    
                    
                    if (currentRetour.getLargeur().toDouble() + 0.5 > largeur){
                        mur.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    if (currentRetour.getSelectionStatus() == true){
                        mur.setColor(Color.green);
                    }
                    
                    currentRetour.setCoordonneX(FractionImperiale.doubleToFraction(retourAirX1));
                    currentRetour.setCoordonneY(FractionImperiale.doubleToFraction(retourAirY1));
                    currentRetour.setHauteur(controller.getSalle().getHauteurDesRetoursAir());
                    
                    
                    for (Accessoire acc: accessoires){
                        if (((currentRetour.getCoordonneeX().toDouble()) >= acc.getCoordonneeX().toDouble() &&
                             (currentRetour.getCoordonneeX().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }   
                        if (((acc.getCoordonneeX().toDouble()) >= currentRetour.getCoordonneeX().toDouble() &&
                             (acc.getCoordonneeX().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble()) ||
                            ((acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) >= currentRetour.getCoordonneeX().toDouble() && 
                             (acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }
                    }  
                    
                    mur.fill3DRect((int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble() - currentRetour.getLargeur().toDouble())*zoomValue), 
                                   (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue), 
                                   (int)(currentRetour.getLargeur().toDouble()*zoomValue), 
                                   (int)(controller.getSalle().getHauteurDesRetoursAir().toDouble()*zoomValue),
                                   true);
                    
                    mur.setStroke(dotted);
                    
                    mur.drawLine((int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble() - currentRetour.getLargeur().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble() - currentRetour.getLargeur().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.drawLine((int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.setStroke(stroke);
                    
                }
                else if (murs.get(currentIndex).hasRetourAir() && sens == Sens.EXTERIEUR){
                    mur.setColor(Color.blue);
                    AccessoireRetourAir currentRetour = murs.get(currentIndex).getRetourAir();
                    double retourAirX1 = currentMesure - largeur/2 - currentRetour.getLargeur().toDouble()/2;
                    double retourAirY1 = controller.getHauteurMurs().toDouble() - controller.getSalle().getHauteurSolDesRetoursAir().toDouble() - controller.getSalle().getHauteurDesRetoursAir().toDouble();
                    
                    
                    if (currentRetour.getLargeur().toDouble() + 0.5 > largeur){
                        mur.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    if (currentRetour.getSelectionStatus() == true){
                        mur.setColor(Color.green);
                    }
                    
                    currentRetour.setCoordonneX(FractionImperiale.doubleToFraction(retourAirX1));
                    currentRetour.setCoordonneY(FractionImperiale.doubleToFraction(retourAirY1));
                    currentRetour.setHauteur(controller.getSalle().getHauteurDesRetoursAir());
                    
                    
                    for (Accessoire acc: accessoires){
                        if (((currentRetour.getCoordonneeX().toDouble()) >= acc.getCoordonneeX().toDouble() &&
                             (currentRetour.getCoordonneeX().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }
                        
                        if (((acc.getCoordonneeX().toDouble()) >= currentRetour.getCoordonneeX().toDouble() &&
                             (acc.getCoordonneeX().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble()) ||
                            ((acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) >= currentRetour.getCoordonneeX().toDouble() && 
                             (acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }
                    }  
                    

                    
                    mur.setStroke(dotted);
                    
                    mur.drawLine((int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble() - currentRetour.getLargeur().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble() - currentRetour.getLargeur().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.drawLine((int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.drawLine((int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble() - currentRetour.getLargeur().toDouble())*zoomValue),
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble() + controller.getHauteurDesRetoursAir().toDouble())*zoomValue), 
                                (int)(20 + (longueurCote.toDouble() - currentRetour.getCoordonneeX().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble() + controller.getHauteurDesRetoursAir().toDouble())*zoomValue));
                    
                    mur.setStroke(stroke);
                }
                currentMesure -= largeur;
                currentPosition += largeurMur;
                currentIndex -= 1;
                mur.setColor(Color.WHITE);
                
            }
        }
        //Affichage standard
        else {
            currentIndex = 0;
            for (Double largeur : largeurs){
                if (controller.murIsSelected(orientation, currentIndex) == true) { 
                    mur.setColor(Color.GREEN);
                }

                int largeurMur = (int)(largeur*zoomValue);
                if ((orientation == Orientation.NORD || orientation == Orientation.SUD)){
                    if (currentMesure + largeur > controller.getLongueurSalle().toDouble()){
                        mur.setColor(Color.ORANGE);
                        controller.setErrorType(5);
                        if (controller.murIsSelected(orientation, currentIndex) == true) { 
                            mur.setColor(Color.GREEN);
                        }
                    }
                }
                else {
                    if (currentMesure + largeur > controller.getLargeurSalle().toDouble()){
                        mur.setColor(Color.ORANGE);
                        controller.setErrorType(5);
                        if (controller.murIsSelected(orientation, currentIndex) == true) { 
                            mur.setColor(Color.GREEN);
                        }
                    }
                }
                
                ArrayList<Accessoire> accessoires = controller.getSalle().getCoteNord().getAccessoires();
                
                switch (orientation) {
                    case NORD:
                        murs = controller.getSalle().getCoteNord().getMurs();
                        accessoires = controller.getSalle().getCoteNord().getAccessoires();
                        break;
                    case SUD:
                        murs = controller.getSalle().getCoteSud().getMurs();
                        accessoires = controller.getSalle().getCoteSud().getAccessoires();
                        break;
                    case OUEST:
                        murs = controller.getSalle().getCoteOuest().getMurs();
                        accessoires = controller.getSalle().getCoteOuest().getAccessoires();
                        break;
                    case EST:
                        murs = controller.getSalle().getCoteEst().getMurs();
                        accessoires = controller.getSalle().getCoteEst().getAccessoires();
                        break;
                    default:
                        murs = controller.getSalle().getCoteNord().getMurs();
                        break;
                }
                
                if (murIsOverweight(murs.get(currentIndex)) == true){
                    mur.setColor(Color.red);
                }
                
                mur.fill3DRect(currentPosition, 20, largeurMur, hauteurMur,true);
                
                
                //Affichage retour d'airs standard
                
                
                
                
                if (murs.get(currentIndex).hasRetourAir() && sens == Sens.INTERIEUR){
                    mur.setColor(Color.blue);
                    AccessoireRetourAir currentRetour = murs.get(currentIndex).getRetourAir();
                    double retourAirX1 = currentMesure + largeur/2 - currentRetour.getLargeur().toDouble()/2;
                    double retourAirY1 = controller.getHauteurMurs().toDouble() - controller.getSalle().getHauteurSolDesRetoursAir().toDouble() - controller.getSalle().getHauteurDesRetoursAir().toDouble();
                    
                    if (currentRetour.getLargeur().toDouble() + 0.5 > largeur){
                        mur.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    if (currentRetour.getSelectionStatus() == true){
                        mur.setColor(Color.green);
                    }
                    
                    currentRetour.setCoordonneX(FractionImperiale.doubleToFraction(retourAirX1));
                    currentRetour.setCoordonneY(FractionImperiale.doubleToFraction(retourAirY1));
                    currentRetour.setHauteur(controller.getSalle().getHauteurDesRetoursAir());
                    
                    
                    
                    for (Accessoire acc: accessoires){
                        if (((currentRetour.getCoordonneeX().toDouble()) >= acc.getCoordonneeX().toDouble() &&
                             (currentRetour.getCoordonneeX().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }
                        if (((acc.getCoordonneeX().toDouble()) >= currentRetour.getCoordonneeX().toDouble() &&
                             (acc.getCoordonneeX().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble()) ||
                            ((acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) >= currentRetour.getCoordonneeX().toDouble() && 
                             (acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }
                    }  
                    
                    
                    
                    mur.fill3DRect((int)(20 + (currentRetour.getCoordonneeX().toDouble())*zoomValue), 
                                   (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue), 
                                   (int)(currentRetour.getLargeur().toDouble()*zoomValue), 
                                   (int)(controller.getSalle().getHauteurDesRetoursAir().toDouble()*zoomValue),
                                   true);
                    
                    mur.setStroke(dotted);
                    
                    mur.drawLine((int)(20 + (currentRetour.getCoordonneeX().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (currentRetour.getCoordonneeX().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.drawLine((int)(20 + (currentRetour.getCoordonneeX().toDouble() + currentRetour.getLargeur().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (currentRetour.getCoordonneeX().toDouble() + currentRetour.getLargeur().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.setStroke(stroke);
                }
                else if (murs.get(currentIndex).hasRetourAir() && sens == Sens.EXTERIEUR){
                    mur.setColor(Color.blue);
                    AccessoireRetourAir currentRetour = murs.get(currentIndex).getRetourAir();
                    double retourAirX1 = currentMesure + largeur/2 - currentRetour.getLargeur().toDouble()/2;
                    double retourAirY1 = controller.getHauteurMurs().toDouble() - controller.getSalle().getHauteurSolDesRetoursAir().toDouble() - controller.getSalle().getHauteurDesRetoursAir().toDouble();
                    
                    if (currentRetour.getLargeur().toDouble() + 0.5 > largeur){
                        mur.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    if (currentRetour.getSelectionStatus() == true){
                        mur.setColor(Color.green);
                    }
                    
                    currentRetour.setCoordonneX(FractionImperiale.doubleToFraction(retourAirX1));
                    currentRetour.setCoordonneY(FractionImperiale.doubleToFraction(retourAirY1));
                    currentRetour.setHauteur(controller.getSalle().getHauteurDesRetoursAir());
                    
                    for (Accessoire acc: accessoires){
                        if (((currentRetour.getCoordonneeX().toDouble()) >= acc.getCoordonneeX().toDouble() &&
                             (currentRetour.getCoordonneeX().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentRetour.getCoordonneeX().toDouble()) + (currentRetour.getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }   
                        if (((acc.getCoordonneeX().toDouble()) >= currentRetour.getCoordonneeX().toDouble() &&
                             (acc.getCoordonneeX().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble()) ||
                            ((acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) >= currentRetour.getCoordonneeX().toDouble() && 
                             (acc.getCoordonneeX().toDouble()) + (acc.getLargeur().toDouble()) <= currentRetour.getCoordonneeX().toDouble()+currentRetour.getLargeur().toDouble())){
                            mur.setColor(Color.orange);
                            controller.setErrorType(12);
                        }
                    }  
                    
                    
                    mur.setStroke(dotted);
                    
                    mur.drawLine((int)(20 + (currentRetour.getCoordonneeX().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (currentRetour.getCoordonneeX().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.drawLine((int)(20 + (currentRetour.getCoordonneeX().toDouble() + currentRetour.getLargeur().toDouble())*zoomValue),
                                20, 
                                (int)(20 + (currentRetour.getCoordonneeX().toDouble() + currentRetour.getLargeur().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble())*zoomValue));
                    
                    mur.drawLine((int)(20 + (currentRetour.getCoordonneeX().toDouble())*zoomValue),
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble() + controller.getHauteurDesRetoursAir().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeX().toDouble() + currentRetour.getLargeur().toDouble())*zoomValue), 
                                (int)(20 + (currentRetour.getCoordonneeY().toDouble() + controller.getHauteurDesRetoursAir().toDouble())*zoomValue));
                    
                    mur.setStroke(stroke);
                }
                currentMesure += largeur;
                currentPosition += largeurMur;
                currentIndex += 1;
                mur.setColor(Color.WHITE);
            }
        }
    }
    
    private void drawAccessoires(Graphics g){
        g.setColor(Color.cyan);
        Graphics2D accessoire = (Graphics2D) g;
        ArrayList<Accessoire> accessoires;
        ArrayList<Separateur> separateurs;
        switch (orientation){
            case NORD:
                accessoires = controller.getSalle().getCoteNord().getAccessoires();
                separateurs = controller.getSalle().getCoteNord().getSeparateurs();
                break;
            case SUD:
                accessoires = controller.getSalle().getCoteSud().getAccessoires();
                separateurs = controller.getSalle().getCoteSud().getSeparateurs();
                break;
            case OUEST:
                accessoires = controller.getSalle().getCoteOuest().getAccessoires();
                separateurs = controller.getSalle().getCoteOuest().getSeparateurs();
                break;
            case EST:
                accessoires = controller.getSalle().getCoteEst().getAccessoires();
                separateurs = controller.getSalle().getCoteEst().getSeparateurs();
                break;
            default:
                accessoires = controller.getSalle().getCoteNord().getAccessoires();
                separateurs = controller.getSalle().getCoteNord().getSeparateurs();
                break;
        }
        
        
        //Affichage Accessoire Miroir
        if ( ((orientation == Orientation.NORD || orientation == Orientation.EST) && sens == Sens.EXTERIEUR) ||
             ((orientation == Orientation.SUD || orientation == Orientation.OUEST) && sens == Sens.INTERIEUR) ){
            double longueurSalle;
            if (orientation == Orientation.NORD || orientation == Orientation.SUD){
                longueurSalle = controller.getSalle().getLongueurSalle().toDouble();
            }
            else{
                longueurSalle = controller.getSalle().getLargeurSalle().toDouble();
            }
            int i = 0;
            int j = 0;
            for (Accessoire acc : accessoires){
                for (Separateur separateur : separateurs){ // GESTION DES CONFLTS SEPARATEURS
                    if (separateur.getPosition().toDouble() > acc.getCoordonneeX().toDouble() - 0.5
                        && separateur.getPosition().toDouble() < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(6);
                    }
                }
                for (Accessoire acc2 : accessoires){ // GESTION DES CONFLITS ENTRE ACCESSOIRES
                    if (i != j){
                        double acc1X1 = acc.getCoordonneeX().toDouble();
                        double acc1X2 = acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble();
                        double acc1Y1 = acc.getCoordonneeY().toDouble();
                        double acc1Y2 = acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble();
                        
                        double acc2X1 = acc2.getCoordonneeX().toDouble();
                        double acc2X2 = acc2.getCoordonneeX().toDouble() + acc2.getLargeur().toDouble();
                        double acc2Y1 = acc2.getCoordonneeY().toDouble();
                        double acc2Y2 = acc2.getCoordonneeY().toDouble() + acc2.getHauteur().toDouble();
                        
                        if ( (acc2X1 >= acc1X1 && acc2X1 <= acc1X2 && acc2Y1 >= acc1Y1 && acc2Y1 <= acc1Y2) ||
                             (acc2X1 >= acc1X1 && acc2X1 <= acc1X2 && acc2Y2 >= acc1Y1 && acc2Y2 <= acc1Y2) ||
                             (acc2X2 >= acc1X1 && acc2X2 <= acc1X2 && acc2Y1 >= acc1Y1 && acc2Y1 <= acc1Y2) ||
                             (acc2X2 >= acc1X1 && acc2X2 <= acc1X2 && acc2Y2 >= acc1Y1 && acc2Y2 <= acc1Y2)
                            ){
                            g.setColor(Color.ORANGE);
                            controller.setErrorType(7);
                        }
                    }
                    j++;
                }
                if (acc.getSelectionStatus() == true){
                    g.setColor(Color.GREEN);
                }
                
                if (orientation == Orientation.NORD || orientation == Orientation.SUD){
                    if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                        acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(8);
                    }
                }
                else {
                    if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                        acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(8);
                    }
                }
                if (acc instanceof AccessoirePorte == false){
                    if (acc.getCoordonneeY().toDouble() < 0.5 ||
                        acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble() + 0.5 > controller.getHauteurMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(9);
                    }
                }
                
                
                if (acc instanceof AccessoireFenetre){
                    accessoire.fill3DRect((int)(20 + (longueurSalle - acc.getCoordonneeX().toDouble() - acc.getLargeur().toDouble())*zoomValue), 
                                          (int)(20 + acc.getCoordonneeY().toDouble()*zoomValue), 
                                          (int)(acc.getLargeur().toDouble()*zoomValue), 
                                          (int)(acc.getHauteur().toDouble()*zoomValue),
                                          true);
                }
                if (acc instanceof AccessoirePorte){
                    double coordY = controller.getHauteurMurs().toDouble() - acc.getHauteur().toDouble();
                    FractionImperiale coordonneY;
                    coordonneY = FractionImperiale.doubleToFraction(coordY);
                    acc.setCoordonneY(coordonneY);
                    
                    if (acc.getCoordonneeY().toDouble() < 0.5 ||
                        acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble() > controller.getHauteurMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(11);
                    }
                    accessoire.fill3DRect((int)(20 + (longueurSalle - acc.getCoordonneeX().toDouble() - acc.getLargeur().toDouble())*zoomValue), 
                                          (int)(20 + acc.getCoordonneeY().toDouble()*zoomValue), 
                                          (int)(acc.getLargeur().toDouble()*zoomValue), 
                                          (int)(acc.getHauteur().toDouble()*zoomValue),
                                          true);
                }
                if (acc instanceof AccessoirePriseElectrique){
                    if (sens == Sens.INTERIEUR){
                        accessoire.fill3DRect((int)(20 + (longueurSalle - acc.getCoordonneeX().toDouble() - acc.getLargeur().toDouble())*zoomValue), 
                                              (int)(20 + acc.getCoordonneeY().toDouble()*zoomValue), 
                                              (int)(acc.getLargeur().toDouble()*zoomValue), 
                                              (int)(acc.getHauteur().toDouble()*zoomValue),
                                              true);
                    }
                }               
                g.setColor(Color.cyan);
                i++;
                j = 0;
            }
        }
        
        //Affichage Accessoire Standard
        else{
            int i = 0;
            int j = 0;
            for (Accessoire acc : accessoires){
                for (Separateur separateur : separateurs){
                    if (separateur.getPosition().toDouble() > acc.getCoordonneeX().toDouble() - 0.5
                        && separateur.getPosition().toDouble() < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(6);
                    }
                }
                for (Accessoire acc2 : accessoires){
                    if (i != j){
                        double acc1X1 = acc.getCoordonneeX().toDouble();
                        double acc1X2 = acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble();
                        double acc1Y1 = acc.getCoordonneeY().toDouble();
                        double acc1Y2 = acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble();
                        
                        double acc2X1 = acc2.getCoordonneeX().toDouble();
                        double acc2X2 = acc2.getCoordonneeX().toDouble() + acc2.getLargeur().toDouble();
                        double acc2Y1 = acc2.getCoordonneeY().toDouble();
                        double acc2Y2 = acc2.getCoordonneeY().toDouble() + acc2.getHauteur().toDouble();
                        
                        if ( (acc2X1 >= acc1X1 && acc2X1 <= acc1X2 && acc2Y1 >= acc1Y1 && acc2Y1 <= acc1Y2) ||
                             (acc2X1 >= acc1X1 && acc2X1 <= acc1X2 && acc2Y2 >= acc1Y1 && acc2Y2 <= acc1Y2) ||
                             (acc2X2 >= acc1X1 && acc2X2 <= acc1X2 && acc2Y1 >= acc1Y1 && acc2Y1 <= acc1Y2) ||
                             (acc2X2 >= acc1X1 && acc2X2 <= acc1X2 && acc2Y2 >= acc1Y1 && acc2Y2 <= acc1Y2)
                            ){
                            g.setColor(Color.ORANGE);
                            controller.setErrorType(7);
                        }
                    }
                    j++;
                }
                if (acc.getSelectionStatus() == true){
                    g.setColor(Color.GREEN);
                }
                
                
                if (orientation == Orientation.NORD || orientation == Orientation.SUD){
                    if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                        acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(8);
                    }
                }
                else {
                    if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                        acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(8);
                    }
                }
                
                if (acc instanceof AccessoirePorte == false){
                    if (acc.getCoordonneeY().toDouble() < 0.5 ||
                        acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble() + 0.5 > controller.getHauteurMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(9);
                    }
                }
                if (acc instanceof AccessoireFenetre){
                    accessoire.fill3DRect((int)(20 + acc.getCoordonneeX().toDouble()*zoomValue), 
                                          (int)(20 + acc.getCoordonneeY().toDouble()*zoomValue), 
                                          (int)(acc.getLargeur().toDouble()*zoomValue), 
                                          (int)(acc.getHauteur().toDouble()*zoomValue),
                                          true);
                }
                if (acc instanceof AccessoirePorte){
                    double coordY = controller.getHauteurMurs().toDouble() - acc.getHauteur().toDouble();
                    FractionImperiale coordonneY;
                    coordonneY = FractionImperiale.doubleToFraction(coordY);
                    acc.setCoordonneY(coordonneY);
                    
                    if (acc.getCoordonneeY().toDouble() < 0.5 ||
                        acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble() > controller.getHauteurMurs().toDouble()){
                        g.setColor(Color.ORANGE);
                        controller.setErrorType(11);
                    }
                    
                    accessoire.fill3DRect((int)(20 + acc.getCoordonneeX().toDouble()*zoomValue), 
                                          (int)(20 + acc.getCoordonneeY().toDouble()*zoomValue), 
                                          (int)(acc.getLargeur().toDouble()*zoomValue), 
                                          (int)(acc.getHauteur().toDouble()*zoomValue),
                                          true);
                }
                if (acc instanceof AccessoirePriseElectrique){
                    if (sens == Sens.INTERIEUR){
                        accessoire.fill3DRect((int)(20 + acc.getCoordonneeX().toDouble()*zoomValue), 
                                              (int)(20 + acc.getCoordonneeY().toDouble()*zoomValue), 
                                              (int)(acc.getLargeur().toDouble()*zoomValue), 
                                              (int)(acc.getHauteur().toDouble()*zoomValue),
                                              true);
                    }
                }
                g.setColor(Color.cyan);
                i++;
                j = 0;
            }
        }
    }
    
    private void afficherEpaisseurMurs(Graphics g){
        double longueurSalle;
        Graphics2D traitEpaisseur = (Graphics2D) g;
        if (orientation == Orientation.NORD || orientation == Orientation.SUD){
            longueurSalle = controller.getSalle().getLongueurSalle().toDouble();
        }
        else{
            longueurSalle = controller.getSalle().getLargeurSalle().toDouble();
        }
        
        Stroke dotted = new BasicStroke(2.0f,                       //Width
                                        BasicStroke.CAP_SQUARE,     //End Cap
                                        BasicStroke.JOIN_MITER,     //Join Style
                                        10.0f,                      //Miter Limit
                                        new float[] {2.0f, 8.0f}, //Dash Pattern
                                        0.0f);                      //Dash Phase
        
        traitEpaisseur.setStroke(dotted);
        traitEpaisseur.setColor(Color.BLACK);
        
        traitEpaisseur.drawLine(20 + (int)(longueurSalle*zoomValue) - (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                                20,
                                20 + (int)(longueurSalle*zoomValue) - (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                                20 + (int)(controller.getHauteurMurs().toDouble()*zoomValue));
        
        traitEpaisseur.drawLine(20 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                                20,
                                20 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                                20 + (int)(controller.getHauteurMurs().toDouble()*zoomValue));
        
    }
    
    private double aireDesTrous(Panneau panneau) {
        Cote cote = this.controller.getCote(this.orientation);
        double aireDesTrous = 0;
        for (Accessoire accessoire : cote.getAccessoires()) {
            FractionImperiale accCoordX = accessoire.getCoordonneeX();
            System.out.println("accCoordX");
            System.out.println(accCoordX.toDouble());
            System.out.println(panneau.getCoordonneeX().toDouble());
            System.out.println(panneau.getCoordonneeX().additionnerFraction(panneau.getLargeur()).toDouble());
            if (accCoordX.toDouble() > panneau.getCoordonneeX().toDouble() && accCoordX.toDouble() < panneau.getCoordonneeX().additionnerFraction(panneau.getLargeur()).toDouble()) {
                aireDesTrous = aireDesTrous + accessoire.aire();
            }
        }
        return aireDesTrous;
    }
    
    public double aireAvecLesTrous(Panneau panneau) {
        return panneau.aire() - this.aireDesTrous(panneau);
    }
    
    private double poids(Panneau panneau) {
        return this.aireAvecLesTrous(panneau) * POIDS_MATIERE_PREMIERE;
    }
    
    public boolean murIsOverweight(Mur mur) {
        boolean isPanneauInOverweight = this.panneauIsOverweight(mur.getPanneauInterieur());
        boolean isPanneauOutOverweight = this.panneauIsOverweight(mur.getPanneauExterieur());
        if (isPanneauInOverweight || isPanneauOutOverweight) {
            return true;
        } else { 
            return false;
        }
    }
    
    public boolean panneauIsOverweight(Panneau panneau) {
        if (this.poids(panneau) > 250.0) {
            return true;
        } else { 
            return false;
        }
    }
}
    
    
