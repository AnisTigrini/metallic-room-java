package ca.ulaval.glo2004.domaine.afficheur;

import ca.ulaval.glo2004.domaine.ControlleurSalle;
import ca.ulaval.glo2004.domaine.Cote;
import ca.ulaval.glo2004.domaine.DTO.FractionImperialeDTO;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.Separateur;
import ca.ulaval.glo2004.domaine.accessoires.Accessoire;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;

public class AfficheurPlanSalle extends Afficheur implements java.io.Serializable{
   
    public AfficheurPlanSalle(ControlleurSalle controller, double zoomValue){
        super(controller, zoomValue);
    }
    
    public void draw(Graphics g){
        controller.setErrorType(0);
        drawMurExterieur(g);
        drawMurInterieur(g);
        drawCoins(g);
        drawSeparateurs(g);
        drawAccessoires(g);
    }
    
    public void drawMurExterieur(Graphics g){
        Graphics2D murExterieur = (Graphics2D) g;
        murExterieur.setColor(Color.GRAY);
        murExterieur.fill3DRect(20, 20, (int)(controller.getLongueurSalle().toDouble()*zoomValue), (int)(controller.getLargeurSalle().toDouble()*zoomValue), false);
    }
    
    public void drawMurInterieur(Graphics g) {
        FractionImperialeDTO epaisseurMur = controller.getEpaisseurDesMurs();
        Graphics2D murInterieur = (Graphics2D) g;
        murInterieur.setColor(Color.blue);
        int rectx1 = 20 + (int)(epaisseurMur.toDouble()*zoomValue);
        int recty1 = 20 + (int)(epaisseurMur.toDouble()*zoomValue);
        int rectx2 = (int)((controller.getLongueurSalle().toDouble() - 2*epaisseurMur.toDouble())*zoomValue);
        int recty2 = (int)((controller.getLargeurSalle().toDouble() - 2*epaisseurMur.toDouble())*zoomValue);
        murInterieur.setColor(Color.BLACK);
        murInterieur.fill3DRect(rectx1, recty1, rectx2, recty2, false);
    }
    public void drawSeparateurs(Graphics g){
        FractionImperialeDTO epaisseurMur = controller.getEpaisseurDesMurs();
        Cote coteNord = controller.getCote(Orientation.NORD);
        Cote coteSud = controller.getCote(Orientation.SUD);
        Cote coteOuest = controller.getCote(Orientation.OUEST);
        Cote coteEst = controller.getCote(Orientation.EST);
        ArrayList<Separateur> separateursNord = coteNord.getSeparateurs();
        ArrayList<Separateur> separateursSud = coteSud.getSeparateurs();
        ArrayList<Separateur> separateursOuest = coteOuest.getSeparateurs();
        ArrayList<Separateur> separateursEst = coteEst.getSeparateurs();
        
        Stroke stroke = new BasicStroke((float)(zoomValue*2),                       //Width
                                        BasicStroke.CAP_SQUARE,     //End Cap
                                        BasicStroke.JOIN_MITER,     //Join Style
                                        10.0f,                      //Miter Limit
                                        new float[] {1.0f, 0.0f},   //Dash Pattern
                                        0.0f);                      //Dash Phase
        
        Graphics2D separateur = (Graphics2D) g;
        
        Stroke original = separateur.getStroke();
        
        separateur.setColor(Color.white);
        separateur.setStroke(stroke);
        
        for (Separateur s : separateursNord){
            if (s.getPosition().toDouble() < epaisseurMur.toDouble() + 1 ||
                s.getPosition().toDouble() > controller.getLongueurSalle().toDouble() - epaisseurMur.toDouble() - 1){
                separateur.setColor(Color.orange);
                controller.setErrorType(1);
            }
            if (s.getSelectionStatus()){
                separateur.setColor(Color.green);
            }
            int x1 = 20 + (int)(s.getPosition().toDouble()*zoomValue);
            int y1 = 15;
            int y2 = 25 + (int)(epaisseurMur.toDouble()*zoomValue);
            separateur.drawLine(x1,y1,x1,y2);
            separateur.setColor(Color.white);
        }
        for (Separateur s : separateursSud){
            if (s.getPosition().toDouble() < epaisseurMur.toDouble() + 1 ||
                s.getPosition().toDouble() > controller.getLongueurSalle().toDouble() - epaisseurMur.toDouble() - 1){
                separateur.setColor(Color.orange);
                controller.setErrorType(2);
            }
            if (s.getSelectionStatus()){
                separateur.setColor(Color.green);
            }
            int x1 = 20 + (int)(s.getPosition().toDouble()*zoomValue);
            int y1 = 25 + (int)(controller.getLargeurSalle().toDouble()*zoomValue);
            int y2 = y1 - (int)(epaisseurMur.toDouble()*zoomValue) - 10;
            separateur.drawLine(x1,y1,x1,y2);
            separateur.setColor(Color.white);
        }
        for (Separateur s : separateursOuest){
            if (s.getPosition().toDouble() < epaisseurMur.toDouble() + 1 ||
                s.getPosition().toDouble() > controller.getLargeurSalle().toDouble() - epaisseurMur.toDouble() - 1){
                separateur.setColor(Color.orange);
                controller.setErrorType(3);
            }
            if (s.getSelectionStatus()){
                separateur.setColor(Color.green);
            }
            int x1 = 15;
            int x2 = 25 + (int)(epaisseurMur.toDouble()*zoomValue);
            int y1 = 20 + (int)(s.getPosition().toDouble()*zoomValue);
            separateur.drawLine(x1,y1,x2,y1);
            separateur.setColor(Color.white);
        }
        for (Separateur s : separateursEst){
            if (s.getPosition().toDouble() < epaisseurMur.toDouble() + 1 ||
                s.getPosition().toDouble() > controller.getLargeurSalle().toDouble() - epaisseurMur.toDouble() - 1){
                separateur.setColor(Color.orange);
                controller.setErrorType(4);
            }
            if (s.getSelectionStatus()){
                separateur.setColor(Color.green);
            }
            int x1 = 25 + (int)(controller.getLongueurSalle().toDouble()*zoomValue);
            int x2 = x1 - (int)(epaisseurMur.toDouble()*zoomValue) - 10;
            int y1 = 20 + (int)(s.getPosition().toDouble()*zoomValue);
            separateur.drawLine(x1,y1,x2,y1);
            separateur.setColor(Color.white);
        }
        
        separateur.setStroke(original);
    }
    
    private void drawAccessoires(Graphics g){
        ArrayList<Accessoire> accessoiresNord = controller.getSalle().getCoteNord().getAccessoires();
        ArrayList<Accessoire> accessoiresSud = controller.getSalle().getCoteSud().getAccessoires();
        ArrayList<Accessoire> accessoiresOuest = controller.getSalle().getCoteOuest().getAccessoires();
        ArrayList<Accessoire> accessoiresEst = controller.getSalle().getCoteEst().getAccessoires();
        ArrayList<Separateur> separateursNord = controller.getSalle().getCoteNord().getSeparateurs();
        ArrayList<Separateur> separateursSud = controller.getSalle().getCoteSud().getSeparateurs();
        ArrayList<Separateur> separateursOuest = controller.getSalle().getCoteOuest().getSeparateurs();
        ArrayList<Separateur> separateursEst = controller.getSalle().getCoteEst().getSeparateurs();
        ArrayList<Mur> mursNord = controller.getSalle().getCoteNord().getMurs();
        ArrayList<Mur> mursSud = controller.getSalle().getCoteSud().getMurs();
        ArrayList<Mur> mursOuest = controller.getSalle().getCoteOuest().getMurs();
        ArrayList<Mur> mursEst = controller.getSalle().getCoteEst().getMurs();
        
        Stroke dotted = new BasicStroke(2.0f,                       //Width
                                        BasicStroke.CAP_SQUARE,     //End Cap
                                        BasicStroke.JOIN_MITER,     //Join Style
                                        10.0f,                      //Miter Limit
                                        new float[] {2.0f, 8.0f}, //Dash Pattern
                                        0.0f);                      //Dash Phase
        
        Graphics2D dottedLine = (Graphics2D) g;
        
        Stroke original = dottedLine.getStroke();
        
        dottedLine.setColor(Color.cyan);
        dottedLine.setStroke(dotted);
        
        for (Accessoire acc :accessoiresNord){
            dottedLine.setColor(Color.cyan);
            for (Separateur sep : separateursNord){
                if (sep.getPosition().toDouble() > acc.getCoordonneeX().toDouble() - 0.5
                   && sep.getPosition().toDouble() < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5){
                   dottedLine.setColor(Color.ORANGE);
                   controller.setErrorType(6);
                }
            }
            if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                dottedLine.setColor(Color.ORANGE);
                controller.setErrorType(8);
            }
            dottedLine.drawLine(20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue),
                       10, 
                       20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue),
                       30 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue));
            dottedLine.drawLine(20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue),
                       10, 
                       20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue),
                       30 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue));
        }
        
        for (Accessoire acc :accessoiresSud){
            dottedLine.setColor(Color.cyan);
            for (Separateur sep : separateursSud){
                if (sep.getPosition().toDouble() > acc.getCoordonneeX().toDouble() - 0.5
                   && sep.getPosition().toDouble() < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5){
                   dottedLine.setColor(Color.ORANGE);
                   controller.setErrorType(6);
                }
            }
            if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                dottedLine.setColor(Color.ORANGE);
                controller.setErrorType(8);
            }
            dottedLine.drawLine(20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue),
                       10 + (int)((controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble())*zoomValue), 
                       20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue),
                       30 + (int)((controller.getLargeurSalle().toDouble())*zoomValue));
            dottedLine.drawLine(20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue),
                       10 + (int)((controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble())*zoomValue), 
                       20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue),
                       30 + (int)((controller.getLargeurSalle().toDouble())*zoomValue));
        }
        
        for (Accessoire acc :accessoiresOuest){
            dottedLine.setColor(Color.cyan);
            for (Separateur sep : separateursOuest){
                if (sep.getPosition().toDouble() > acc.getCoordonneeX().toDouble() - 0.5
                   && sep.getPosition().toDouble() < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5){
                   dottedLine.setColor(Color.ORANGE);
                   controller.setErrorType(6);
                }
            }
            if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                dottedLine.setColor(Color.ORANGE);
                controller.setErrorType(8);
            }
            dottedLine.drawLine(10,
                       20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue),
                       30 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                       20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue));
            dottedLine.drawLine(10,
                       20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue),
                       30 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                       20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue));
        }
        
        for (Accessoire acc :accessoiresEst){
            dottedLine.setColor(Color.cyan);
            for (Separateur sep : separateursEst){
                if (sep.getPosition().toDouble() > acc.getCoordonneeX().toDouble() - 0.5
                   && sep.getPosition().toDouble() < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5){
                   dottedLine.setColor(Color.ORANGE);
                   controller.setErrorType(6);
                }
            }
            if (acc.getCoordonneeX().toDouble() -0.5 <= controller.getEpaisseurDesMurs().toDouble() || // COORD X ACCESSOIRE EN DEHORS DE LA SALLE
                acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() + 0.5 >= controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()){
                dottedLine.setColor(Color.ORANGE);
                controller.setErrorType(8);
            }
            dottedLine.drawLine(10 + (int)((controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble())*zoomValue), 
                       20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue),
                       30 + (int)((controller.getLongueurSalle().toDouble())*zoomValue),
                       20 + (int)(acc.getCoordonneeX().toDouble()*zoomValue));
            dottedLine.drawLine(10 + (int)((controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble())*zoomValue), 
                       20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue),
                       30 + (int)((controller.getLongueurSalle().toDouble())*zoomValue),
                       20 + (int)((acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble())*zoomValue));
        }
        
        dottedLine.setStroke(original);
        
        double currentPosition = 0.0d;
        int currentIndex = 0;
        ArrayList<Double> largeurs = controller.getDimensionsMurs(Orientation.NORD);
        double largeurMur;
        
        for (Mur mur: mursNord){
            try {
                largeurMur = largeurs.get(currentIndex);
                if (mur.hasRetourAir()){
                    Graphics2D retour = (Graphics2D) g;
                    retour.setColor(Color.cyan);
                    if (mur.getRetourAir().getLargeur().toDouble() + 0.5 > largeurMur){
                        retour.setColor(Color.orange);
                        controller.setErrorType(10);
                    }

                    int rectx1 = 20 + (int)((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2))*zoomValue);
                    int recty1 = 20 + (int)(controller.getEpaisseurDesMurs().toDouble()/4*zoomValue);
                    int rectx2 = (int)(mur.getRetourAir().getLargeur().toDouble()*zoomValue);
                    int recty2 = (int)(controller.getEpaisseurDesMurs().toDouble()*0.5*zoomValue);

                    for (Accessoire acc: accessoiresNord){
                        if (((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) >= acc.getCoordonneeX().toDouble() &&
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            retour.setColor(Color.orange);
                            controller.setErrorType(12);
                        }    
                    }   

                    retour.drawRect(rectx1, recty1, rectx2, recty2);
                }
                currentPosition = currentPosition + largeurMur;
                currentIndex++;
            }catch (Exception e) {}
        }
        
        currentPosition = 0.0d;
        currentIndex = 0;
        largeurs = controller.getDimensionsMurs(Orientation.SUD);
        
        for (Mur mur: mursSud){
            try {
                largeurMur = largeurs.get(currentIndex);
                if (mur.hasRetourAir()){
                    Graphics2D retour = (Graphics2D) g;
                    retour.setColor(Color.cyan);
                    if (mur.getRetourAir().getLargeur().toDouble() + 0.5 > largeurMur){
                        retour.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    
                    int rectx1 = 20 + (int)((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2))*zoomValue);
                    int recty1 = 20 + (int)((controller.getLargeurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()*0.75)*zoomValue);
                    int rectx2 = (int)(mur.getRetourAir().getLargeur().toDouble()*zoomValue);
                    int recty2 = (int)(controller.getEpaisseurDesMurs().toDouble()*0.5*zoomValue);
                    
                    for (Accessoire acc: accessoiresSud){
                        if (((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) >= acc.getCoordonneeX().toDouble() &&
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            retour.setColor(Color.orange);
                            controller.setErrorType(12);
                        }    
                    }   
                    
                    retour.drawRect(rectx1, recty1, rectx2, recty2);
                }
                currentPosition = currentPosition + largeurMur;
                currentIndex++;
            }catch (Exception e) {}
        }
        
        currentPosition = 0.0d;
        currentIndex = 0;
        largeurs = controller.getDimensionsMurs(Orientation.OUEST);
        
        for (Mur mur: mursOuest){
            try {
                largeurMur = largeurs.get(currentIndex);
                if (mur.hasRetourAir()){
                    Graphics2D retour = (Graphics2D) g;
                    retour.setColor(Color.cyan);
                    if (mur.getRetourAir().getLargeur().toDouble() + 0.5 > largeurMur){
                        retour.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    
                    int recty1 = 20 + (int)((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2))*zoomValue);
                    int rectx1 = 20 + (int)(controller.getEpaisseurDesMurs().toDouble()/4*zoomValue);
                    int recty2 = (int)(mur.getRetourAir().getLargeur().toDouble()*zoomValue);
                    int rectx2 = (int)(controller.getEpaisseurDesMurs().toDouble()*0.5*zoomValue);
                    
                    for (Accessoire acc: accessoiresOuest){
                        if (((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) >= acc.getCoordonneeX().toDouble() &&
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            retour.setColor(Color.orange);
                            controller.setErrorType(12);
                        }    
                    }   
                    
                    retour.drawRect(rectx1, recty1, rectx2, recty2);
                }
                currentPosition = currentPosition + largeurMur;
                currentIndex++;
            }catch (Exception e) {}
        }
        
        currentPosition = 0.0d;
        currentIndex = 0;
        largeurs = controller.getDimensionsMurs(Orientation.EST);
        
        for (Mur mur: mursEst){
            try {
                largeurMur = largeurs.get(currentIndex);
                if (mur.hasRetourAir()){
                    Graphics2D retour = (Graphics2D) g;
                    retour.setColor(Color.cyan);
                    if (mur.getRetourAir().getLargeur().toDouble() + 0.5 > largeurMur){
                        retour.setColor(Color.orange);
                        controller.setErrorType(10);
                    }
                    
                    int recty1 = 20 + (int)((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2))*zoomValue);
                    int rectx1 = 20 + (int)((controller.getLongueurSalle().toDouble() - controller.getEpaisseurDesMurs().toDouble()*0.75)*zoomValue);
                    int recty2 = (int)(mur.getRetourAir().getLargeur().toDouble()*zoomValue);
                    int rectx2 = (int)(controller.getEpaisseurDesMurs().toDouble()*0.5*zoomValue);
                    
                    for (Accessoire acc: accessoiresEst){
                        if (((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) >= acc.getCoordonneeX().toDouble() &&
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble()) ||
                            ((currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) >= acc.getCoordonneeX().toDouble() && 
                             (currentPosition+(largeurMur/2 - mur.getRetourAir().getLargeur().toDouble()/2)) + (mur.getRetourAir().getLargeur().toDouble()) <= acc.getCoordonneeX().toDouble()+acc.getLargeur().toDouble())){
                            retour.setColor(Color.orange);
                            controller.setErrorType(12);
                        }    
                    }   
                    
                    retour.drawRect(rectx1, recty1, rectx2, recty2);
                }
                currentPosition = currentPosition + largeurMur;
                currentIndex++;
            }catch (Exception e) {}
        }
    }
    
    private void drawCoins(Graphics g){
        
        Stroke stroke = new BasicStroke((float)(zoomValue),                       //Width
                                        BasicStroke.CAP_SQUARE,     //End Cap
                                        BasicStroke.JOIN_MITER,     //Join Style
                                        10.0f,                      //Miter Limit
                                        new float[] {1.0f, 0.0f},   //Dash Pattern
                                        0.0f);                      //Dash Phase
        Graphics2D coins = (Graphics2D) g;
        coins.setColor(Color.BLACK);
        coins.setStroke(stroke);
        
        coins.drawLine(20,
                       20,
                       20 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                       20 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue));
        
        coins.drawLine(20 + (int)(controller.getLongueurSalle().toDouble()*zoomValue),
                       20,
                       20 + (int)((controller.getLongueurSalle().toDouble() - (int)controller.getEpaisseurDesMurs().toDouble())*zoomValue),
                       20 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue));
        
        coins.drawLine(20,
                       20 + (int)(controller.getLargeurSalle().toDouble()*zoomValue),
                       20 + (int)(controller.getEpaisseurDesMurs().toDouble()*zoomValue),
                       20 + (int)((controller.getLargeurSalle().toDouble() - (int)controller.getEpaisseurDesMurs().toDouble())*zoomValue));
        
        coins.drawLine(20 + (int)(controller.getLongueurSalle().toDouble()*zoomValue),
                       20 + (int)(controller.getLargeurSalle().toDouble()*zoomValue),
                       20 + (int)((controller.getLongueurSalle().toDouble() - (int)controller.getEpaisseurDesMurs().toDouble())*zoomValue),
                       20 + (int)((controller.getLargeurSalle().toDouble() - (int)controller.getEpaisseurDesMurs().toDouble())*zoomValue));
        
    }
}
