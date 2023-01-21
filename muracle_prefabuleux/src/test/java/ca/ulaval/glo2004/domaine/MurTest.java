package ca.ulaval.glo2004.domaine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.ulaval.glo2004.domaine.enums.SensPanneau;
import ca.ulaval.glo2004.domaine.PanneauExterieur;
import ca.ulaval.glo2004.domaine.PanneauInterieur;
import ca.ulaval.glo2004.domaine.Panneau;
import java.util.ArrayList;

public class MurTest {
    
    private static Mur mur;
    
    @Before
    public void setup() {
        mur = new Mur();
    }
    
    // Tester le constructeur par défault de mur.
    @Test
    public void constructeurDefaultMur() {       
       assertEquals(0, mur.getPosition());
       assertFalse(mur.getSelectionStatus());
       assertEquals(SensPanneau.INTERIEUR,mur.getPanneauInterieur().getSens());
       assertEquals(SensPanneau.EXTERIEUR,mur.getPanneauExterieur().getSens());
    }
    
    // Valider setPosition.
    @Test
    public void validerSetPosition() {
        try {
            mur.setPosition(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("On ne peut pas entrer une position de mur négative.", e.getMessage());
        }
                
        assertEquals(0, mur.getPosition());

        mur.setPosition(50);
        
        assertEquals(50, mur.getPosition());
    }
    
    // Valider setSelectionStatus.
    @Test
    public void validerSetSelectionStatus() {
        assertFalse(mur.getSelectionStatus());

        mur.setSelectionStatus(true);
        
        assertTrue(mur.getSelectionStatus());
    }
    
    // valider setPanneauInterieure
    @Test
    public void setPanneauInterieure() {
        try {
            mur.setPanneauInterieur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de panneau intérieur.",  e.getMessage());
        }
        
        PanneauInterieur pi = new PanneauInterieur();
        mur.setPanneauInterieur(pi);
        
        assertEquals(pi, mur.getPanneauInterieur());
    }
    
    // valider setPanneauExterieure
    @Test
    public void setPanneauExterieure() {
        try {
            mur.setPanneauExterieur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de panneau extérieur.",  e.getMessage());
        }
        
        PanneauExterieur pe = new PanneauExterieur();
        mur.setPanneauExterieur(pe);
        
        assertEquals(pe, mur.getPanneauExterieur());
    }
    
    // valider getBothPanneaux
    @Test
    public void getBothPanneaux() {
        ArrayList<Panneau> listePanneaux = mur.getBothPanneaux();
        
        assertEquals(2, listePanneaux.size());
        assertEquals(SensPanneau.INTERIEUR, listePanneaux.get(0).sens);
        assertEquals(SensPanneau.EXTERIEUR, listePanneaux.get(1).sens);
    }
    
    // valider getLargeureInterieure
    @Test
    public void validerGetLargeureInterieure() {
        PanneauInterieur pi = new PanneauInterieur();
        pi.setLargeur(new FractionImperiale(15,17,512));
        
        mur.setPanneauInterieur(pi);
        
        assertEquals(15, mur.getLargeurInterieure().getPartieEntiere());
        assertEquals(17, mur.getLargeurInterieure().getNumerateur());
        assertEquals(512, mur.getLargeurInterieure().getDenominateur());
    }    

    // valider getLargeureExterieure
    @Test
    public void validerGetLargeureExterieure() {
        PanneauExterieur pe = new PanneauExterieur();
        pe.setLargeur(new FractionImperiale(10,20,2));
        
        mur.setPanneauExterieur(pe);
        
        assertEquals(10, mur.getLargeurExterieure().getPartieEntiere());
        assertEquals(20, mur.getLargeurExterieure().getNumerateur());
        assertEquals(2, mur.getLargeurExterieure().getDenominateur());
    }
    
    // valider setEpaisseurDesMurs
    @Test
    public void validerSetEpaisseurDesMurs() {
        try {
             mur.setEpaisseurDesMurs(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        mur.setEpaisseurDesMurs(new FractionImperiale(2,24,16));
        
        assertEquals(2, mur.getPanneauInterieur().getEpaisseurDesMurs().getPartieEntiere());
        assertEquals(24, mur.getPanneauInterieur().getEpaisseurDesMurs().getNumerateur());
        assertEquals(16, mur.getPanneauInterieur().getEpaisseurDesMurs().getDenominateur());
        
        assertEquals(2, mur.getPanneauExterieur().getEpaisseurDesMurs().getPartieEntiere());
        assertEquals(24, mur.getPanneauExterieur().getEpaisseurDesMurs().getNumerateur());
        assertEquals(16, mur.getPanneauExterieur().getEpaisseurDesMurs().getDenominateur());
    }
    
    // valider setLongueurDePlis
    @Test
    public void validerSetLongueurDesPlis() {
        try {
            mur.setLongueurDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        mur.setLongueurDesPlis(new FractionImperiale(23,214,8));    
        
        assertEquals(23, mur.getLongueurDesPlis().getPartieEntiere());
        assertEquals(214, mur.getLongueurDesPlis().getNumerateur());
        assertEquals(8, mur.getLongueurDesPlis().getDenominateur());

    }
    
    // valider setMargeSoudure
    @Test
    public void validerSetMargeSoudure() {
        try {
            mur.setMargeDesSoudures(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        mur.setMargeDesSoudures(new FractionImperiale(223,0,8));    
        
        assertEquals(223, mur.getMargeDesSoudures().getPartieEntiere());
        assertEquals(0, mur.getMargeDesSoudures().getNumerateur());
        assertEquals(8, mur.getMargeDesSoudures().getDenominateur());
    }
        
    // valider setMargeDesPlis
    @Test
    public void validerSetMargeDesPlis() {
        try {
            mur.setMargeDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        mur.setMargeDesPlis(new FractionImperiale(21,10,16));    
        
        assertEquals(21, mur.getMargeDesPlis().getPartieEntiere());
        assertEquals(10, mur.getMargeDesPlis().getNumerateur());
        assertEquals(16, mur.getMargeDesPlis().getDenominateur());
    }
    
    // valider setAngleDeSoudure
    @Test
    public void validerSetAngleDeSoudure() {
        assertEquals(45.0, mur.getAngleDesSoudures(), 0);
        
        mur.setAngleDesSoudures(65.89);
        
        assertEquals(65.89, mur.getAngleDesSoudures(), 0);
        
        try {
             mur.setAngleDesSoudures(-0.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
        
        try {
            mur.setAngleDesSoudures(90.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
    }
    
    @Test
    public void validerMurClone() {
        try {
            Mur murClone = (Mur) mur.clone();
            
            assertNotEquals(mur, murClone);
            assertNotEquals(mur.getPanneauInterieur(), murClone.getPanneauInterieur());
            assertNotEquals(mur.getPanneauExterieur(), murClone.getPanneauExterieur());
            assertEquals(mur.getPosition(), murClone.getPosition());
            assertEquals(mur.getSelectionStatus(), murClone.getSelectionStatus());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
