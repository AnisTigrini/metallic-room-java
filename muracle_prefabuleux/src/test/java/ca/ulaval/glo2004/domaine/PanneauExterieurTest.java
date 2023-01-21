package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.PanneauExterieur;
import ca.ulaval.glo2004.domaine.Plis;
import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.SensPanneau;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


public class PanneauExterieurTest {
    
    public PanneauExterieurTest() {}
    
    @Test
    public void testConstructeurSansParametre()
    {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(SensPanneau.EXTERIEUR, instance.getSens());
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
        Plis plisDroite = new Plis(PositionPlis.DROITE, ConfigurationPlis.PLAT, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        Plis plisGauche = new Plis(PositionPlis.GAUCHE, ConfigurationPlis.PLAT, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        PanneauExterieur instance = new PanneauExterieur(
            new FractionImperiale(50,0,1),

        new FractionImperiale(96,0,1),plisGauche,plisDroite );
        //new FractionImperiale(96,0,1),plisDroite,plisGauche );

        assertEquals(SensPanneau.EXTERIEUR, instance.getSens());
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
        assertEquals(96, instance.getHauteur().getPartieEntiere());
        assertEquals(0, instance.getHauteur().getNumerateur());
        assertEquals(1, instance.getHauteur().getDenominateur());
    }
   
    @Test
    public void testGetPlisDroite() {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(ConfigurationPlis.COIN, instance.getPlisDroite().getConfiguration());
        assertEquals(PositionPlis.DROITE, instance.getPlisDroite().getPosition());
    }

    @Test
    public void testGetPlisGauche() {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(ConfigurationPlis.COIN, instance.getPlisDroite().getConfiguration());
        assertEquals(PositionPlis.GAUCHE, instance.getPlisGauche().getPosition());
    }
    
    @Test
    public void testGetLargeur() {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
    }
    
    @Test
    public void testGetHauteur() {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(96, instance.getHauteur().getPartieEntiere());
        assertEquals(0, instance.getHauteur().getNumerateur());
        assertEquals(1, instance.getHauteur().getDenominateur());
    }
    
    @Test
    public void testGetTrouAvecPlusGrandeLargeur() {
        PanneauExterieur instance = new PanneauExterieur();
        Trou petit = new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1),new FractionImperiale(0,0,1));
        Trou grand = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        instance.addTrou(petit);
        instance.addTrou(grand);
        assertEquals(grand, instance.getTrouAvecPlusGrandeLargeur());
    }
    
    @Test
    public void testGetTrouAvecPlusGrandeHauteur() {
        PanneauExterieur instance = new PanneauExterieur();
        Trou petit = new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        Trou grand = new Trou(new FractionImperiale(4,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        instance.addTrou(petit);
        instance.addTrou(grand);
        assertEquals(grand, instance.getTrouAvecPlusGrandeHauteur());
    }
    
    @Test
    public void testSetPlisDroite() {
        Plis plis = new Plis(PositionPlis.DROITE, ConfigurationPlis.COIN, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        PanneauExterieur instance = new PanneauExterieur();
        instance.setPlisDroite(plis);
        assertEquals(plis.getPosition(), instance.getPlisDroite().getPosition());
        assertEquals(plis.getConfiguration(), instance.getPlisDroite().getConfiguration());
        assertEquals(plis.getLongueur(), instance.getPlisDroite().getLongueur());
        assertEquals(plis.getEpaisseurMurs(), instance.getPlisDroite().getEpaisseurMurs());
        assertEquals(plis.getMargeSoudure(), instance.getPlisDroite().getMargeSoudure());
        assertEquals(plis.getAngleSoudure(), instance.getPlisDroite().getAngleSoudure(), 0.0);
        assertEquals(plis.getMargePlis(), instance.getPlisDroite().getMargePlis());
    }

    @Test
    public void testSetPlisGauche() {
        Plis plis = new Plis(PositionPlis.GAUCHE, ConfigurationPlis.PLAT, 
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                new FractionImperiale(50,0,1),
                45,new FractionImperiale(50,0,1));
        PanneauExterieur instance = new PanneauExterieur();
        instance.setPlisGauche(plis);
        assertEquals(plis.getPosition(), instance.getPlisGauche().getPosition());
        assertEquals(plis.getConfiguration(), instance.getPlisGauche().getConfiguration());
        assertEquals(plis.getLongueur(), instance.getPlisGauche().getLongueur());
        assertEquals(plis.getEpaisseurMurs(), instance.getPlisGauche().getEpaisseurMurs());
        assertEquals(plis.getMargeSoudure(), instance.getPlisGauche().getMargeSoudure());
        assertEquals(plis.getAngleSoudure(), instance.getPlisGauche().getAngleSoudure(), 0.0);
        assertEquals(plis.getMargePlis(), instance.getPlisGauche().getMargePlis());
    }
    
    @Test
    public void testSetLargeur() {
        try {
            PanneauExterieur instance = new PanneauExterieur();
            instance.setLargeur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        FractionImperiale largeur = new FractionImperiale(50,0,1);
        PanneauExterieur instance = new PanneauExterieur();
        instance.setLargeur(largeur);
        assertEquals(50, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
    }

    @Test
    public void testSetHauteur() {
        try {
            PanneauExterieur instance = new PanneauExterieur();
            instance.setHauteur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        FractionImperiale hauteur = new FractionImperiale(50,2,4);
        PanneauExterieur instance = new PanneauExterieur();
        instance.setHauteur(hauteur);
        assertEquals(50, instance.getHauteur().getPartieEntiere());
        assertEquals(2, instance.getHauteur().getNumerateur());
        assertEquals(4, instance.getHauteur().getDenominateur());
    }

    @Test
    public void testAddTrou() {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(0, instance.trous.size());
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        assertEquals(1, instance.trous.size());
    }
    
    @Test
    public void testDeleteAllTrous() {
        PanneauExterieur instance = new PanneauExterieur();
        assertEquals(0, instance.trous.size());
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        instance.addTrou(new Trou(new FractionImperiale(4,0,1), new FractionImperiale(4,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1)));
        assertEquals(3, instance.trous.size());
        assertEquals(3 ,instance.deleteAllTrous());
        assertEquals(0, instance.trous.size());
    }
    
    @Test
    public void deleteTrou() {
        PanneauExterieur instance = new PanneauExterieur();
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
            PanneauExterieur panneauExterieure = new PanneauExterieur();
            panneauExterieure.addTrou(new Trou());
            
            PanneauExterieur panneauExterieureClone = panneauExterieure.clone();
            
            assertNotEquals(panneauExterieure, panneauExterieureClone);
            assertNotEquals(panneauExterieure.getHauteur(), panneauExterieureClone.getHauteur());
            assertNotEquals(panneauExterieure.getLargeur(), panneauExterieureClone.getLargeur());
            assertNotEquals(panneauExterieure.getPlisDroite(), panneauExterieureClone.getPlisDroite());
            assertNotEquals(panneauExterieure.getPlisGauche(), panneauExterieureClone.getPlisGauche());
            assertEquals(panneauExterieure.getSens(), panneauExterieureClone.getSens());
            assertEquals(panneauExterieure.getSelectionStatus(), panneauExterieureClone.getSelectionStatus());
            
            assertEquals(panneauExterieure.getHauteur().getPartieEntiere(), panneauExterieureClone.getHauteur().getPartieEntiere());
            assertEquals(panneauExterieure.getHauteur().getNumerateur(), panneauExterieureClone.getHauteur().getNumerateur());
            assertEquals(panneauExterieure.getHauteur().getDenominateur(), panneauExterieureClone.getHauteur().getDenominateur());
            
            assertEquals(panneauExterieure.getLargeur().getPartieEntiere(), panneauExterieureClone.getLargeur().getPartieEntiere());
            assertEquals(panneauExterieure.getLargeur().getNumerateur(), panneauExterieureClone.getLargeur().getNumerateur());
            assertEquals(panneauExterieure.getLargeur().getDenominateur(), panneauExterieureClone.getLargeur().getDenominateur());
            
            ArrayList<Trou> trous = panneauExterieure.getTrous();
            ArrayList<Trou> trousClone = panneauExterieureClone.getTrous();
            
            assertEquals(trous.size(), trousClone.size());
            
            for (int i = 0; i < trous.size(); i++) {
                assertNotEquals(trous.get(i), trousClone.get(i));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
