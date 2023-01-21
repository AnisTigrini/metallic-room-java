package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.PanneauInterieur;
import ca.ulaval.glo2004.domaine.Plis;
import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.SensPanneau;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


public class PanneauInterieurTest {
    
    public PanneauInterieurTest() {
    }
    
    @Test
    public void testConstructeurSansParametre()
    {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(SensPanneau.INTERIEUR, instance.getSens());
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
        assertEquals(96, instance.getHauteur().getPartieEntiere());
        assertEquals(0, instance.getHauteur().getNumerateur());
        assertEquals(1, instance.getHauteur().getDenominateur());
    }
    
    @Test
    public void testConstructeurAvecParametre()
    {
        Plis plisHaut = new Plis(PositionPlis.HAUT, ConfigurationPlis.PLAT, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        Plis plisBas = new Plis(PositionPlis.BAS, ConfigurationPlis.PLAT, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        PanneauInterieur instance = new PanneauInterieur(
            new FractionImperiale(50,0,1),
        new FractionImperiale(96,0,1),plisHaut,plisBas );
        assertEquals(SensPanneau.INTERIEUR, instance.getSens());
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
        assertEquals(96, instance.getHauteur().getPartieEntiere());
        assertEquals(0, instance.getHauteur().getNumerateur());
        assertEquals(1, instance.getHauteur().getDenominateur());
    }
   
    @Test
    public void testGetPlisHaut() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(ConfigurationPlis.COIN, instance.getPlisHaut().getConfiguration());
        assertEquals(PositionPlis.HAUT, instance.getPlisHaut().getPosition());
    }

    @Test
    public void testGetPlisBas() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(ConfigurationPlis.COIN, instance.getPlisBas().getConfiguration());
        assertEquals(PositionPlis.BAS, instance.getPlisBas().getPosition());
    }
    
    @Test
    public void testGetLargeur() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
    }
    
    @Test
    public void testGetHauteur() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(96, instance.getHauteur().getPartieEntiere());
        assertEquals(0, instance.getHauteur().getNumerateur());
        assertEquals(1, instance.getHauteur().getDenominateur());
    }
    
    @Test
    public void testGetTrouAvecPlusGrandeLargeur() {
        PanneauInterieur instance = new PanneauInterieur();
        Trou petit = new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        Trou grand = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        instance.addTrou(petit);
        instance.addTrou(grand);
        assertEquals(grand, instance.getTrouAvecPlusGrandeLargeur());
    }
    
    @Test
    public void testGetTrouAvecPlusGrandeHauteur() {
        PanneauInterieur instance = new PanneauInterieur();
        Trou petit = new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        Trou grand = new Trou(new FractionImperiale(4,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1),new FractionImperiale(0,0,1));
        instance.addTrou(petit);
        instance.addTrou(grand);
        assertEquals(grand, instance.getTrouAvecPlusGrandeHauteur());
    }
    
    @Test
    public void testSetPlisHaut() {
        Plis plis = new Plis(PositionPlis.HAUT, ConfigurationPlis.COIN, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        PanneauInterieur instance = new PanneauInterieur();
        instance.setPlisHaut(plis);
        assertEquals(plis.getPosition(), instance.getPlisHaut().getPosition());
        assertEquals(plis.getConfiguration(), instance.getPlisHaut().getConfiguration());
        assertEquals(plis.getLongueur(), instance.getPlisHaut().getLongueur());
        assertEquals(plis.getEpaisseurMurs(), instance.getPlisHaut().getEpaisseurMurs());
        assertEquals(plis.getMargeSoudure(), instance.getPlisHaut().getMargeSoudure());
        assertEquals(plis.getAngleSoudure(), instance.getPlisHaut().getAngleSoudure(), 0.0);
        assertEquals(plis.getMargePlis(), instance.getPlisHaut().getMargePlis());
    }

    @Test
    public void testSetPlisBas() {
        Plis plis = new Plis(PositionPlis.BAS, ConfigurationPlis.PLAT, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        PanneauInterieur instance = new PanneauInterieur();
        instance.setPlisBas(plis);
        assertEquals(plis.getPosition(), instance.getPlisBas().getPosition());
        assertEquals(plis.getConfiguration(), instance.getPlisBas().getConfiguration());
        assertEquals(plis.getLongueur(), instance.getPlisBas().getLongueur());
        assertEquals(plis.getEpaisseurMurs(), instance.getPlisBas().getEpaisseurMurs());
        assertEquals(plis.getMargeSoudure(), instance.getPlisBas().getMargeSoudure());
        assertEquals(plis.getAngleSoudure(), instance.getPlisBas().getAngleSoudure(), 0.0);
        assertEquals(plis.getMargePlis(), instance.getPlisBas().getMargePlis());
    }
    
    @Test
    public void testSetLargeur() {
        try {
            PanneauInterieur instance = new PanneauInterieur();
            instance.setLargeur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        FractionImperiale largeur = new FractionImperiale(50,0,1);
        PanneauInterieur instance = new PanneauInterieur();
        instance.setLargeur(largeur);
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
    }

    @Test
    public void testSetHauteur() {
        try {
            PanneauInterieur instance = new PanneauInterieur();
            instance.setHauteur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        FractionImperiale hauteur = new FractionImperiale(50,2,4);
        PanneauInterieur instance = new PanneauInterieur();
        instance.setHauteur(hauteur);
        assertEquals(50, instance.getHauteur().getPartieEntiere());
        assertEquals(2, instance.getHauteur().getNumerateur());
        assertEquals(4, instance.getHauteur().getDenominateur());
    }

    @Test
    public void testAddTrou() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(0, instance.trous.size());
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1),
        new FractionImperiale(0,0,1),new FractionImperiale(0,0,1)));
        assertEquals(1, instance.trous.size());
    }
    
    @Test
    public void testDeleteAllTrous() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(0, instance.trous.size());
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1),new FractionImperiale(0,0,1)));
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1),
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        assertEquals(3, instance.trous.size());
        assertEquals(3 ,instance.deleteAllTrous());
        assertEquals(0, instance.trous.size());
    }
    
    @Test
    public void testerdeleteTrou() {
        PanneauInterieur instance = new PanneauInterieur();
        assertEquals(0, instance.trous.size());
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1),
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        instance.addTrou(new Trou(new FractionImperiale(8,0,1), new FractionImperiale(4,0,1),
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1),
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        assertEquals(3, instance.trous.size());
        
        // TODO : À RETRAVAILLER
//        assertEquals(2 ,instance.deleteTrou(new Trou(new FractionImperiale(8,0,1), new FractionImperiale(4,0,1), new Point(1,0))));
//        assertEquals(2, instance.trous.size());
    }
    
    @Test
    public void testerPanneauInterieurClone() {
        try {
            PanneauInterieur panneauInterieur = new PanneauInterieur();
            panneauInterieur.addTrou(new Trou());
            
            PanneauInterieur panneauInterieurClone = panneauInterieur.clone();
            
            assertNotEquals(panneauInterieur, panneauInterieurClone);
            assertNotEquals(panneauInterieur.getHauteur(), panneauInterieurClone.getHauteur());
            assertNotEquals(panneauInterieur.getLargeur(), panneauInterieurClone.getLargeur());
            assertNotEquals(panneauInterieur.getPlisHaut(), panneauInterieurClone.getPlisHaut());
            assertNotEquals(panneauInterieur.getPlisBas(), panneauInterieurClone.getPlisBas());
            assertEquals(panneauInterieur.getSens(), panneauInterieurClone.getSens());
            assertEquals(panneauInterieur.getSelectionStatus(), panneauInterieurClone.getSelectionStatus());
            
            assertEquals(panneauInterieur.getHauteur().getPartieEntiere(), panneauInterieurClone.getHauteur().getPartieEntiere());
            assertEquals(panneauInterieur.getHauteur().getNumerateur(), panneauInterieurClone.getHauteur().getNumerateur());
            assertEquals(panneauInterieur.getHauteur().getDenominateur(), panneauInterieurClone.getHauteur().getDenominateur());
            
            assertEquals(panneauInterieur.getLargeur().getPartieEntiere(), panneauInterieurClone.getLargeur().getPartieEntiere());
            assertEquals(panneauInterieur.getLargeur().getNumerateur(), panneauInterieurClone.getLargeur().getNumerateur());
            assertEquals(panneauInterieur.getLargeur().getDenominateur(), panneauInterieurClone.getLargeur().getDenominateur());
            
            ArrayList<Trou> trous = panneauInterieur.getTrous();
            ArrayList<Trou> trousClone = panneauInterieurClone.getTrous();
            
            assertEquals(trous.size(), trousClone.size());
            
            for (int i = 0; i < trous.size(); i++) {
                assertNotEquals(trous.get(i), trousClone.get(i));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
