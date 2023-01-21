package ca.ulaval.glo2004.domaine;

import java.awt.Point;
import org.junit.Test;
import ca.ulaval.glo2004.domaine.FractionImperiale;
import static org.junit.Assert.*;

public class TrouTest {
    
    public TrouTest () {}
    
    
    @Test
    public void testConstructeurSansParametre()
    {
        Trou instance = new Trou();
        
        assertEquals(0, instance.getHauteur().getPartieEntiere());
        assertEquals(0, instance.getHauteur().getNumerateur());
        assertEquals(1, instance.getHauteur().getDenominateur());
        
        assertEquals(0, instance.getLargeur().getPartieEntiere());
        assertEquals(0, instance.getLargeur().getNumerateur());
        assertEquals(1, instance.getLargeur().getDenominateur());
        
        assertEquals(0, instance.getCoordooneeX().getPartieEntiere());
        assertEquals(0, instance.getCoordooneeX().getNumerateur());
        assertEquals(1, instance.getCoordooneeX().getDenominateur());     
        
        assertEquals(0, instance.getCoordooneeY().getPartieEntiere());
        assertEquals(0, instance.getCoordooneeY().getNumerateur());
        assertEquals(1, instance.getCoordooneeY().getDenominateur());
    }
    
    @Test
    public void testConstructeurAvecParametre()
    {
        Trou instance = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        assertEquals(8, instance.getHauteur().getPartieEntiere());
        assertEquals(8, instance.getLargeur().getPartieEntiere());
        assertEquals(64, instance.aire(), 0);
    }
    
    @Test
    public void testSetHauteur() {
        try {
            Trou instance = new Trou();
            instance.setHauteur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        Trou instance = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        FractionImperiale hauteur = new FractionImperiale(4,4,2);
        instance.setHauteur(hauteur);
        assertEquals(4, instance.getHauteur().getPartieEntiere());
        assertEquals(4, instance.getHauteur().getNumerateur());
        assertEquals(2, instance.getHauteur().getDenominateur());
        assertEquals(48, instance.aire(), 0);
    }
    
    @Test
    public void testSetLargeur() {
        try {
            Trou instance = new Trou();
            instance.setLargeur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        Trou instance = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        FractionImperiale largeur = new FractionImperiale(4,4,2);
        instance.setLargeur(largeur);
        assertEquals(4, instance.getLargeur().getPartieEntiere());
        assertEquals(4, instance.getLargeur().getNumerateur());
        assertEquals(2, instance.getLargeur().getDenominateur());
        assertEquals(48, instance.aire(), 0);
    }
    
    @Test
    public void testerSetCoordonneX() {
        try {
            Trou instance = new Trou();
            instance.setCoordonneeX(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        Trou instance = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        FractionImperiale coordonneX = new FractionImperiale(4,4,2);
        instance.setCoordonneeX(coordonneX);
        assertEquals(4, instance.getCoordooneeX().getPartieEntiere());
        assertEquals(4, instance.getCoordooneeX().getNumerateur());
        assertEquals(2, instance.getCoordooneeX().getDenominateur());
    }
    
    @Test
    public void testerSetCoordonneY() {
        try {
            Trou instance = new Trou();
            instance.setCoordonneeY(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        Trou instance = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(8,0,1), 
        new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));
        FractionImperiale coordonneY = new FractionImperiale(4,4,2);
        instance.setCoordonneeY(coordonneY);
        assertEquals(4, instance.getCoordooneeY().getPartieEntiere());
        assertEquals(4, instance.getCoordooneeY().getNumerateur());
        assertEquals(2, instance.getCoordooneeY().getDenominateur());
    }
    
    @Test
    public void testerTrouClone() {
        try {
            Trou trou = new Trou(new FractionImperiale(8,0,1), new FractionImperiale(8,0,1), 
            new FractionImperiale(0,0,1), new FractionImperiale(0,0,1));            
            Trou trouClone = (Trou) trou.clone();
            
            assertNotEquals(trou, trouClone);
            
            assertNotEquals(trou.getCoordooneeX(), trouClone.getCoordooneeX());
            assertNotEquals(trou.getCoordooneeY(), trouClone.getCoordooneeY());
            assertNotEquals(trou.getHauteur(), trouClone.getHauteur());
            assertNotEquals(trou.getLargeur(), trouClone.getLargeur());

            assertEquals(trou.getCoordooneeX().getPartieEntiere(), trouClone.getCoordooneeX().getPartieEntiere());
            assertEquals(trou.getCoordooneeX().getNumerateur(), trouClone.getCoordooneeX().getNumerateur());
            assertEquals(trou.getCoordooneeX().getDenominateur(), trouClone.getCoordooneeX().getDenominateur());
            
            assertEquals(trou.getCoordooneeY().getPartieEntiere(), trouClone.getCoordooneeY().getPartieEntiere());
            assertEquals(trou.getCoordooneeY().getNumerateur(), trouClone.getCoordooneeY().getNumerateur());
            assertEquals(trou.getCoordooneeY().getDenominateur(), trouClone.getCoordooneeY().getDenominateur());

            assertEquals(trou.getHauteur().getPartieEntiere(), trouClone.getHauteur().getPartieEntiere());
            assertEquals(trou.getHauteur().getNumerateur(), trouClone.getHauteur().getNumerateur());
            assertEquals(trou.getHauteur().getDenominateur(), trouClone.getHauteur().getDenominateur());
            
            assertEquals(trou.getLargeur().getPartieEntiere(), trouClone.getLargeur().getPartieEntiere());
            assertEquals(trou.getLargeur().getNumerateur(), trouClone.getLargeur().getNumerateur());
            assertEquals(trou.getLargeur().getDenominateur(), trouClone.getLargeur().getDenominateur());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
