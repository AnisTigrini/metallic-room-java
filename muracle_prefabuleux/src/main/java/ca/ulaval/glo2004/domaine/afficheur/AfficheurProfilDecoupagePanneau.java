package ca.ulaval.glo2004.domaine.afficheur;

import ca.ulaval.glo2004.domaine.ControlleurSalle;
import ca.ulaval.glo2004.domaine.Mur;
import ca.ulaval.glo2004.domaine.Separateur;
import ca.ulaval.glo2004.domaine.accessoires.Accessoire;
import ca.ulaval.glo2004.domaine.accessoires.AccessoirePorte;
import ca.ulaval.glo2004.domaine.accessoires.AccessoireFenetre;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import ca.ulaval.glo2004.domaine.enums.Sens;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class AfficheurProfilDecoupagePanneau extends Afficheur {
    protected Orientation orientation;
    protected Sens sens;
    protected int indexPanneau;
    
    private String currentSvg = "<!DOCTYPE html>\n" +
                                "<html>\n" +
                                "<body>\n" +
                                "\n";

    public AfficheurProfilDecoupagePanneau(ControlleurSalle controller, double zoomValue, Orientation orientation, Sens sens, int indexPanneau){
        super(controller, zoomValue);
        this.orientation = orientation;
        this.sens = sens;
        this.indexPanneau = indexPanneau;
    } 
   
    public void draw(Graphics g) {
        if (this.sens == sens.INTERIEUR) {
            drawPanneauInterieur(g);
        } else {
            drawPanneauExterieur(g);
        }
    }
        
    public void drawPanneauExterieur(Graphics g) {
        // 1. Chercher les attributs generaux.
        int hauteurMur = (int) (controller.getHauteurMurs().toDouble() * zoomValue);
        int epaisseurMur = (int) (controller.getEpaisseurDesMurs().toDouble() * zoomValue);
        double angleSoudure = Math.toRadians(controller.getAngleDesSoudures());
        int jeuEpaisseurPanneau= (int)(1 * zoomValue);
        
        // 1.1 chercher la largeure du mur
        Mur murAafficher = controller.getMurParIndexEtOrientation(indexPanneau, orientation);
        int margePlisSoudure = (int)(murAafficher.getPanneauInterieur()
                .getPlisBas().getMargePlis().toDouble() * zoomValue);
        int epaisseurPlisSoudure = (int)(6*zoomValue);
        int largeureExterieure = (int) (murAafficher.getLargeurExterieure().toDouble() * zoomValue);
        int nombreMurs = controller.getCote(this.orientation).getMurs().size();
        
        // 2. Construire la liste des points
        // 2.1 Point d'origine
        int origineX = 70;
        int origineY = 70;
        
        // 2.2 Autres points TODO : IMPLANTER LE JEU DE LA MARGE DE PLIS DE SOUDURE
        int x1 = origineX + epaisseurPlisSoudure;
        int x2 = 0;
        int x3 = 0;
        int x4 = 0;
        int x5 = 0;
        int y1 = 0;
        int y2 = 0;
        int y3 = 0;
        int y4 = 0;
        int y5 = 0;
        
        // 4. Aller chercher les separateurs et Accessoire du cote en question
        double murPosition = 0.0d;
        double murLimite = 0.0d;
        ArrayList<Accessoire> accessoires = controller.getSalle().getCoteNord().getAccessoires();
        ArrayList<Separateur> separateurs = controller.getSalle().getCoteNord().getSeparateurs();
        switch (orientation) {
            case NORD:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteNord().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteNord().getAccessoires();
                separateurs = controller.getSalle().getCoteNord().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteNord().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLongueurSalle().toDouble();
                }
                break;
            case SUD:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteSud().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteSud().getAccessoires();
                separateurs = controller.getSalle().getCoteSud().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteSud().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLongueurSalle().toDouble();
                }
                break;
            case OUEST:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteOuest().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteOuest().getAccessoires();
                separateurs = controller.getSalle().getCoteOuest().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteOuest().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLargeurSalle().toDouble();
                }
                break;
            case EST:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteEst().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteEst().getAccessoires();
                separateurs = controller.getSalle().getCoteEst().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteEst().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLargeurSalle().toDouble();
                }
                break;
            default:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteNord().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteNord().getAccessoires();
                separateurs = controller.getSalle().getCoteNord().getSeparateurs();
                break;
        }
        
        if(indexPanneau == 0 && indexPanneau == nombreMurs - 1)//Panneau Exterieur coin gauche et droit
        {
            double hypoth = Math.sqrt(epaisseurMur*epaisseurMur + epaisseurMur*epaisseurMur);
            int hypothenuse = (int)hypoth;
            x1 = origineX + epaisseurPlisSoudure;
            x2 = x1 + hypothenuse + 2*margePlisSoudure;
            x3 = x2 + largeureExterieure;
            x4 = x3 + hypothenuse + 2*margePlisSoudure;
            x5 = x4 + epaisseurPlisSoudure;
            y1 = origineY + jeuEpaisseurPanneau;
            y2 = y1 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            y3 = 2*origineY + hauteurMur - y2;
            y4 = 2*origineY + hauteurMur - y1;
            y5 = origineY + hauteurMur;
            
        }
        else if( (indexPanneau == 0))// && (orientation == Orientation.SUD || orientation == Orientation.OUEST)) ||
            //(indexPanneau == nombreMurs - 1 && (orientation == Orientation.NORD || orientation == Orientation.EST)) )//Panneau Exterieur coin gauche
        {
            double hypoth = Math.sqrt(epaisseurMur*epaisseurMur + epaisseurMur*epaisseurMur);
            int hypothenuse = (int)hypoth;
            x1 = origineX + epaisseurPlisSoudure;
            x2 = x1 + hypothenuse + 2*margePlisSoudure;
            x3 = x2 + largeureExterieure;
            x4 = x3 + epaisseurMur + 2*margePlisSoudure;
            x5 = x4 + epaisseurPlisSoudure;
            y1 = origineY + jeuEpaisseurPanneau;
            y2 = y1 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            y3 = 2*origineY + hauteurMur - y2;
            y4 = 2*origineY + hauteurMur - y1;
            y5 = origineY + hauteurMur;
        }
        else if(indexPanneau == nombreMurs - 1) {
            double hypoth = Math.sqrt(epaisseurMur*epaisseurMur + epaisseurMur*epaisseurMur);
            int hypothenuse = (int)hypoth;
            x1 = origineX + epaisseurPlisSoudure;
            x2 = x1 + epaisseurMur + 2*margePlisSoudure;
            x3 = x2 + largeureExterieure;
            x4 = x3 + hypothenuse + 2*margePlisSoudure;
            x5 = x4 + epaisseurPlisSoudure;
            y1 = origineY + jeuEpaisseurPanneau;
            y2 = y1 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            y3 = 2*origineY + hauteurMur - y2;
            y4 = 2*origineY + hauteurMur - y1;
            y5 = origineY + hauteurMur;
        }
        else if(indexPanneau > 0 &&  indexPanneau < nombreMurs - 1)//Panneau Exterieur pas de coin
        {
            x1 = origineX + epaisseurPlisSoudure;
            x2 = x1 + epaisseurMur + 2*margePlisSoudure;
            x3 = x2 + largeureExterieure;
            x4 = x3 + epaisseurMur + 2*margePlisSoudure;
            x5 = x4 + epaisseurPlisSoudure;
            y1 = origineY + jeuEpaisseurPanneau;
            y2 = y1 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            y3 = 2*origineY + hauteurMur - y2;
            y4 = 2*origineY + hauteurMur - y1;
            y5 = origineY + hauteurMur;
        }
        
        this.currentSvg = this.currentSvg.concat("<svg height=\"600\" width=\"800\">\n" +
                               "  <polygon points=\"");
        
        int pointx1 = origineX;
        int pointy1 = y2;
        this.currentSvg = this.currentSvg.concat(origineX + "," + y2 + " ");

        int pointx2 = origineX;
        int pointy2 = y3;
        this.currentSvg = this.currentSvg.concat(origineX + "," + y3 + " ");

        int pointx3 = x1;
        int pointy3 = y4;
        this.currentSvg = this.currentSvg.concat(x1 + "," + y4 + " ");

        int pointx4 = x2;
        int pointy4 = y4;
        this.currentSvg = this.currentSvg.concat(x2 + "," + y4 + " ");

        int pointx5 = x2;
        int pointy5 = y5;
        this.currentSvg = this.currentSvg.concat(x2 + "," + y5 + " ");

        int pointx6 = x3;
        int pointy6 = y5;
        this.currentSvg = this.currentSvg.concat(x3 + "," + y5 + " ");

        int pointx7 = x3;
        int pointy7 = y4;
        this.currentSvg = this.currentSvg.concat(x3 + "," + y4 + " ");

        int pointx8 = x4;
        int pointy8 = y4;
        this.currentSvg = this.currentSvg.concat(x4 + "," + y4 + " ");

        int pointx9 = x5;
        int pointy9 = y3;
        this.currentSvg = this.currentSvg.concat(x5 + "," + y3 + " ");

        int pointx10 = x5;
        int pointy10 = y2;
        this.currentSvg = this.currentSvg.concat(x5 + "," + y2 + " ");

        int pointx11 = x4;
        int pointy11 = y1;
        this.currentSvg = this.currentSvg.concat(x4 + "," + y1 + " ");

        int pointx12 = x3;
        int pointy12 = y1;
        this.currentSvg = this.currentSvg.concat(x3 + "," + y1 + " ");

        int pointx13 = x3;
        int pointy13 = origineY;
        this.currentSvg = this.currentSvg.concat(x3 + "," + origineY + " ");

        int pointx14 = x2;
        int pointy14 = origineY;
        this.currentSvg = this.currentSvg.concat(x2 + "," + origineY + " ");

        int pointx15 = x2;
        int pointy15 = y1;
        this.currentSvg = this.currentSvg.concat(x2 + "," + y1 + " ");

        int pointx16 = x1;
        int pointy16 = y1;
        this.currentSvg = this.currentSvg.concat(x1 + "," + y1);
        
        this.currentSvg= this.currentSvg.concat("\" class=\"" + "$panneau-exterieure-"+ orientation + "-" + indexPanneau + '$' + "\"");
        this.currentSvg = this.currentSvg.concat("\" style=\"fill:white;stroke:black;stroke-width:1\" />\n" +
                               "  Sorry, your browser does not support inline SVG.\n");

        int[] xPoints = new int[]{pointx1, pointx2, pointx3, pointx4, pointx5, pointx6, pointx7, pointx8, pointx9, pointx10, pointx11, pointx12, pointx13, pointx14, pointx15, pointx16};
        int[] yPoints = new int[]{pointy1, pointy2, pointy3, pointy4, pointy5, pointy6, pointy7, pointy8, pointy9, pointy10, pointy11, pointy12, pointy13, pointy14, pointy15, pointy16};
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.drawPolygon(xPoints, yPoints, 16);
        
        for (Accessoire acc : accessoires) {
            if (acc instanceof AccessoirePorte || acc instanceof AccessoireFenetre) {
                if (acc.getCoordonneeX().toDouble() > murPosition && acc.getCoordonneeX().toDouble() < murLimite) {

                    double accX1 = origineX + (controller.getEpaisseurDesMurs().toDouble() + 6 + (acc.getCoordonneeX().toDouble() - murPosition)) * zoomValue;

                    double accX2 = (acc.getLargeur().toDouble() + acc.getMarge().toDouble()) * zoomValue;
                    double accY1 = origineY + (acc.getCoordonneeY().toDouble()) * zoomValue;
                    double accY2 = (acc.getHauteur().toDouble() + acc.getMarge().toDouble()) * zoomValue;

                    Graphics2D accessoireCurrent = (Graphics2D) g;
                    accessoireCurrent.setColor(Color.blue);
                    accessoireCurrent.drawRect((int)accX1, (int)accY1, (int)accX2, (int)accY2);


                    this.currentSvg = this.currentSvg.concat("  <rect x=\"" + accX1 + "\" y=\"" + accY1 + "\" width=\"" + accX2 + "\" height=\"" + accY2 + "\"\n"
                        + "style=\"fill:white;stroke:black;stroke-width:1\" />\n");
                }
            }
        }
        
        String currentSvgFoot = "</svg>" +
                                "\n" +
                                "</body>\n" +
                                "</html>";
    
    
        this.currentSvg = currentSvg.concat(currentSvgFoot);
        
        controller.setCurrentSvg(currentSvg);
    }
    
    public void drawPanneauInterieur(Graphics g) {
        // 1. Chercher les attributs generaux.
        int hauteurMur = (int) (controller.getHauteurMurs().toDouble() * zoomValue);
        int epaisseurMur = (int) (controller.getEpaisseurDesMurs().toDouble() * zoomValue);
        double angleSoudure = Math.toRadians(controller.getAngleDesSoudures());
        int jeuEpaisseurPanneau= (int)(1 * zoomValue);
        
        // 1.1 chercher la largeure du mur
        Mur murAafficher = controller.getMurParIndexEtOrientation(indexPanneau, orientation);
        int margePlisSoudure = (int)(murAafficher.getPanneauInterieur()
                .getPlisBas().getMargePlis().toDouble() * zoomValue);
        int epaisseurPlisSoudure = (int)(6*zoomValue);
        int largeureInterieure = (int) (murAafficher.getLargeurExterieure().toDouble() * zoomValue); //OUI LargeurExterieure TOUCHEZ PAS
        int nombreMurs = controller.getCote(this.orientation).getMurs().size();
        
        // 2. Construire la liste des points
        // 2.1 Point d'origine
        int origineX = 70;
        int origineY = 70;
        

        // 4. Aller chercher les separateurs et Accessoire du cote en question
        double murPosition = 0.0d;
        double murLimite = 0.0d;
        ArrayList<Accessoire> accessoires = controller.getSalle().getCoteNord().getAccessoires();
        ArrayList<Separateur> separateurs = controller.getSalle().getCoteNord().getSeparateurs();
        switch (orientation) {
            case NORD:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteNord().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteNord().getAccessoires();
                separateurs = controller.getSalle().getCoteNord().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteNord().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLongueurSalle().toDouble();
                }
                break;
            case SUD:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteSud().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteSud().getAccessoires();
                separateurs = controller.getSalle().getCoteSud().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteSud().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLongueurSalle().toDouble();
                }
                break;
            case OUEST:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteOuest().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteOuest().getAccessoires();
                separateurs = controller.getSalle().getCoteOuest().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteOuest().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLargeurSalle().toDouble();
                }
                break;
            case EST:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteEst().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteEst().getAccessoires();
                separateurs = controller.getSalle().getCoteEst().getSeparateurs();
                // determiner ma largeur de mur courrant
                if (indexPanneau < separateurs.size()) {
                    murLimite = controller.getSalle().getCoteEst().getSeparateurs().get(indexPanneau).getPosition().toDouble();
                } else {
                    murLimite = controller.getLargeurSalle().toDouble();
                }
                break;
            default:
                if (indexPanneau != 0){
                    murPosition = controller.getSalle().getCoteNord().getSeparateurs().get(indexPanneau-1).getPosition().toDouble();
                }
                accessoires = controller.getSalle().getCoteNord().getAccessoires();
                separateurs = controller.getSalle().getCoteNord().getSeparateurs();
                break;
        }
        

        int x1 = origineX + epaisseurPlisSoudure;
        int x2 = 0;
        int x3 = 0;
        int x4 = 0;
        int x5 = 0;
        int x6 = 0;
        int x7 = 0;
        int y1 = 0;
        int y2 = 0;
        int y3 = 0;
        int y4 = 0;
        int y5 = 0;
        
        if(indexPanneau == 0 && indexPanneau == nombreMurs - 1)//Panneau Interieur coin gauche et droit
        {
            largeureInterieure -= epaisseurMur;
            largeureInterieure -= epaisseurMur;
            x1 = origineX + jeuEpaisseurPanneau;
            x2 = x1 - epaisseurMur;
            x3 = x2 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x4 = origineX + largeureInterieure + epaisseurMur - (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure) - jeuEpaisseurPanneau;
            x5 = x4 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);//TOCAHNGE
            x6 = origineX + largeureInterieure - jeuEpaisseurPanneau;
            x7 = x6 + jeuEpaisseurPanneau;
            y1 = origineY + epaisseurPlisSoudure + margePlisSoudure;
            y2 = y1 + epaisseurMur + margePlisSoudure;
            y3 = y2 + hauteurMur + margePlisSoudure;
            y4 = y3 + epaisseurMur + margePlisSoudure;
            y5 = y4 + epaisseurPlisSoudure;
            
        }
        else if( (indexPanneau == 0))// && (orientation == Orientation.NORD || orientation == Orientation.EST)) ||
            //(indexPanneau == nombreMurs - 1 && (orientation == Orientation.SUD || orientation == Orientation.OUEST)) )//Panneau Interieur coin gauche
        {
            //largeureInterieure -= epaisseurMur;
            x1 = origineX + jeuEpaisseurPanneau;
            x2 = x1 - epaisseurMur;
            x3 = x2 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x4 = origineX + largeureInterieure - 2*(int)(Math.tan(angleSoudure)*epaisseurPlisSoudure) - 2*jeuEpaisseurPanneau;
            x5 = x4 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x6 = x5;
            x7 = x6 + jeuEpaisseurPanneau;
            y1 = origineY + epaisseurPlisSoudure;
            y2 = y1 + epaisseurMur;
            y3 = y2 + hauteurMur;
            y4 = y3 + epaisseurMur;
            y5 = y4 + epaisseurPlisSoudure;
        }
        else if( //(indexPanneau == 0 && (orientation == Orientation.SUD || orientation == Orientation.OUEST)) ||
            (indexPanneau == nombreMurs - 1))// && (orientation == Orientation.NORD || orientation == Orientation.EST)) )//Panneau Interieur coin droit
        {
            largeureInterieure -= epaisseurMur;
            x1 = origineX + jeuEpaisseurPanneau;
            x2 = x1;
            x3 = x2 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x4 = 2*origineX + largeureInterieure - x3 + epaisseurMur;
            x5 = x4 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x6 = origineX + largeureInterieure - jeuEpaisseurPanneau;
            x7 = x6 + jeuEpaisseurPanneau;
            y1 = origineY + epaisseurPlisSoudure;
            y2 = y1 + epaisseurMur;
            y3 = y2 + hauteurMur;
            y4 = y3 + epaisseurMur;
            y5 = y4 + epaisseurPlisSoudure;
        }
        else if(indexPanneau > 0 &&  indexPanneau < nombreMurs - 1)//Panneau Interieur pas de coin
        {
            x1 = origineX + jeuEpaisseurPanneau;
            x2 = x1;
            x3 = x2 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x4 = 2*origineX + largeureInterieure - x3;
            x5 = x4 + (int)(Math.tan(angleSoudure)*epaisseurPlisSoudure);
            x6 = x5;
            x7 = x6 + jeuEpaisseurPanneau;
            y1 = origineY + epaisseurPlisSoudure;
            y2 = y1 + epaisseurMur;
            y3 = y2 + hauteurMur;
            y4 = y3 + epaisseurMur;
            y5 = y4 + epaisseurPlisSoudure;
        }
        
        this.currentSvg = this.currentSvg.concat("<svg height=\"600\" width=\"800\">\n" +
                               "  <polygon points=\"");
        
        int pointx1 = origineX;
        int pointy1 = y2;
        this.currentSvg = this.currentSvg.concat(origineX + "," + y2 + " ");

        int pointx2 = x1;
        int pointy2 = y2;
        this.currentSvg = this.currentSvg.concat(x1 + "," + y2 + " ");

        int pointx3 = x2;
        int pointy3 = y1;
        this.currentSvg = this.currentSvg.concat(x2 + "," + y1 + " ");

        int pointx4 = x3;
        int pointy4 = origineY;
        this.currentSvg = this.currentSvg.concat(x3 + "," + origineY + " ");

        int pointx5 = x4;
        int pointy5 = origineY;
        this.currentSvg = this.currentSvg.concat(x4 + "," + origineY + " ");

        int pointx6 = x5;
        int pointy6 = y1;
        this.currentSvg = this.currentSvg.concat(x5 + "," + y1 + " ");

        int pointx7 = x6;
        int pointy7 = y2;
        this.currentSvg = this.currentSvg.concat(x6 + "," + y2 + " ");

        int pointx8 = x7;
        int pointy8 = y2;
        this.currentSvg = this.currentSvg.concat(x7 + "," + y2 + " ");

        int pointx9 = x7;
        int pointy9 = y3;
        this.currentSvg = this.currentSvg.concat(x7 + "," + y3 + " ");

        int pointx10 = x6;
        int pointy10 = y3;
        this.currentSvg = this.currentSvg.concat(x6 + "," + y3 + " ");

        int pointx11 = x5;
        int pointy11 = y4;
        this.currentSvg = this.currentSvg.concat(x5 + "," + y4 + " ");

        int pointx12 = x4;
        int pointy12 = y5;
        this.currentSvg = this.currentSvg.concat(x4 + "," + y5 + " ");

        int pointx13 = x3;
        int pointy13 = y5;
        this.currentSvg = this.currentSvg.concat(x3 + "," + y5 + " ");

        int pointx14 = x2;
        int pointy14 = y4;
        this.currentSvg = this.currentSvg.concat(x2 + "," + y4 + " ");

        int pointx15 = x1;
        int pointy15 = y3;
        this.currentSvg = this.currentSvg.concat(x1 + "," + y3 + " ");

        int pointx16 = origineX;
        int pointy16 = y3;
        this.currentSvg = this.currentSvg.concat(origineX + "," + y3);
        
        this.currentSvg= this.currentSvg.concat("\" class=\"" + "$panneau-interieure-"+ orientation + "-" + indexPanneau + '$' + "\"");
        this.currentSvg = this.currentSvg.concat("\" style=\"fill:white;stroke:black;stroke-width:1\" />\n" +
                               "  Sorry, your browser does not support inline SVG.\n");

        int[] xPoints = new int[]{pointx1, pointx2, pointx3, pointx4, pointx5, pointx6, pointx7, pointx8, pointx9, pointx10, pointx11, pointx12, pointx13, pointx14, pointx15, pointx16};
        int[] yPoints = new int[]{pointy1, pointy2, pointy3, pointy4, pointy5, pointy6, pointy7, pointy8, pointy9, pointy10, pointy11, pointy12, pointy13, pointy14, pointy15, pointy16};
        
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.drawPolygon(xPoints, yPoints, 16);
        
        if (murAafficher.hasRetourAir()){
            
            double retourAirX1 = origineX + (murAafficher.getRetourAir().getCoordonneeX().toDouble() - murPosition)*zoomValue;
            
            if (indexPanneau == 0){// && (orientation == Orientation.NORD || orientation == Orientation.EST)){
                retourAirX1 -= controller.getEpaisseurDesMurs().toDouble()*zoomValue;
            }
//            else if (indexPanneau == nombreMurs - 1){// && (orientation == Orientation.SUD || orientation == Orientation.OUEST)){
//                retourAirX1 -= controller.getEpaisseurDesMurs().toDouble()*zoomValue;
//            }
            
            double retourAirX2 = murAafficher.getRetourAir().getLargeur().toDouble()*zoomValue;
            double retourAirY1 = origineY + (6 + controller.getEpaisseurDesMurs().toDouble() + murAafficher.getRetourAir().getCoordonneeY().toDouble())*zoomValue;
            double retourAirY2 = controller.getHauteurDesRetoursAir().toDouble()*zoomValue;
            
            double retourAirPliY1 = origineY + (6 + 0.25*controller.getEpaisseurDesMurs().toDouble())*zoomValue;
            double retourAirPliY2 = (0.5*controller.getEpaisseurDesMurs().toDouble())*zoomValue;
            
            Graphics2D retourAir = (Graphics2D) g;
            retourAir.setColor(Color.blue);
            retourAir.drawRect((int)retourAirX1, (int)retourAirY1, (int)retourAirX2, (int)retourAirY2);
            retourAir.drawRect((int)retourAirX1, (int)retourAirPliY1, (int)retourAirX2, (int)retourAirPliY2);
            
            this.currentSvg = this.currentSvg.concat("  <rect x=\"" + retourAirX1 + "\" y=\"" + retourAirY1 + "\" width=\"" + retourAirX2 + "\" height=\"" + retourAirY2 + "\"\n"
                                       + "style=\"fill:white;stroke:black;stroke-width:1\" />\n");
            
            this.currentSvg = this.currentSvg.concat("  <rect x=\"" + retourAirX1 + "\" y=\"" + retourAirPliY1 + "\" width=\"" + retourAirX2 + "\" height=\"" + retourAirPliY2 + "\"\n"
                                       + "style=\"fill:white;stroke:black;stroke-width:1\" />\n");
        }
        

        for (Accessoire acc : accessoires) { 
            if (acc.getCoordonneeX().toDouble() > murPosition && acc.getCoordonneeX().toDouble() < murLimite) {
                
                double accX1 = origineX + (acc.getCoordonneeX().toDouble() - murPosition) * zoomValue;

                if (indexPanneau == 0){// && (orientation == Orientation.NORD || orientation == Orientation.EST)){
                    accX1 -= controller.getEpaisseurDesMurs().toDouble() * zoomValue;
                }
//                else if (indexPanneau == nombreMurs - 1){// && (orientation == Orientation.SUD || orientation == Orientation.OUEST)){
//                    accX1 -= controller.getEpaisseurDesMurs().toDouble() * zoomValue;
//                }

                double accX2 = (acc.getLargeur().toDouble() + acc.getMarge().toDouble()) * zoomValue;
                double accY1 = origineY + (6 + controller.getEpaisseurDesMurs().toDouble() + acc.getCoordonneeY().toDouble()) * zoomValue;
                double accY2 = (acc.getHauteur().toDouble() + acc.getMarge().toDouble()) * zoomValue;

                // ICI VERIFIER SI CEST UNE PORTE, SI OUI, COUPER LE PLIS SOUS LA PORTE
                if (acc instanceof AccessoirePorte) {
                    accY2 = (acc.getHauteur().toDouble() + acc.getMarge().toDouble() + 6 + controller.getEpaisseurDesMurs().toDouble()) * zoomValue;
                }
                
                Graphics2D accessoireCurrent = (Graphics2D) g;
                accessoireCurrent.setColor(Color.blue);
                accessoireCurrent.drawRect((int)accX1, (int)accY1, (int)accX2, (int)accY2);


                this.currentSvg = this.currentSvg.concat("  <rect x=\"" + accX1 + "\" y=\"" + accY1 + "\" width=\"" + accX2 + "\" height=\"" + accY2 + "\"\n"
                    + "style=\"fill:white;stroke:black;stroke-width:1\" />\n");
            }
        }
        
        String currentSvgFoot = "</svg>" +
                                "\n" +
                                "</body>\n" +
                                "</html>";
    
    
        this.currentSvg = currentSvg.concat(currentSvgFoot);
        
        controller.setCurrentSvg(currentSvg);
    }
    
}
