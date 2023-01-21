package ca.ulaval.glo2004.domaine.accessoires;

import ca.ulaval.glo2004.domaine.FractionImperiale;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccessoirePorteTest {

    @Test
    public void testContructeurSansParametre() {
        AccessoirePorte accessoire = new AccessoirePorte();
        assertEquals(true, accessoire.estTraversant());
        
        assertEquals(88, accessoire.getHauteur().getPartieEntiere());
        assertEquals(0, accessoire.getHauteur().getNumerateur());
        assertEquals(1, accessoire.getHauteur().getDenominateur());
        
        assertEquals(38, accessoire.getLargeur().getPartieEntiere());
        assertEquals(0, accessoire.getLargeur().getNumerateur());
        assertEquals(1, accessoire.getLargeur().getDenominateur());
        
        assertEquals(0, accessoire.getMarge().getPartieEntiere());
        assertEquals(0, accessoire.getMarge().getNumerateur());
        assertEquals(1, accessoire.getMarge().getDenominateur());
        
        assertEquals(false, accessoire.getSelectionStatus());
    }
    
    @Test
    public void testSeHauteur(){
        AccessoirePorte accessoire = new AccessoirePorte();
        
        try {
            accessoire.setHauteur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        accessoire.setHauteur(new FractionImperiale(10,5,2));
        
        assertEquals(10, accessoire.getHauteur().getPartieEntiere());
        assertEquals(5, accessoire.getHauteur().getNumerateur());
        assertEquals(2, accessoire.getHauteur().getDenominateur());
    }
    
    @Test
    public void testSetLargeur(){
        AccessoirePorte accessoire = new AccessoirePorte();
        
        try {
            accessoire.setLargeur(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        accessoire.setLargeur(new FractionImperiale(129,4,64));
        
        assertEquals(129, accessoire.getLargeur().getPartieEntiere());
        assertEquals(4, accessoire.getLargeur().getNumerateur());
        assertEquals(64, accessoire.getLargeur().getDenominateur());
    }
    
    @Test
    public void testSetMarge(){
        AccessoirePorte accessoire = new AccessoirePorte();
        
        try {
            accessoire.setMarge(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        accessoire.setMarge(new FractionImperiale(64,14,512));
        
        assertEquals(64, accessoire.getMarge().getPartieEntiere());
        assertEquals(14, accessoire.getMarge().getNumerateur());
        assertEquals(512, accessoire.getMarge().getDenominateur());
    }
    
    @Test
    public void testSetCoordonneX(){
        AccessoirePorte accessoire = new AccessoirePorte();
        
        try {
            accessoire.setCoordonneX(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        accessoire.setCoordonneX(new FractionImperiale(141,8,4));
        
        assertEquals(141, accessoire.getCoordonneeX().getPartieEntiere());
        assertEquals(8, accessoire.getCoordonneeX().getNumerateur());
        assertEquals(4, accessoire.getCoordonneeX().getDenominateur());
    }
    
    @Test
    public void testSetCoordonneY(){
        AccessoirePorte accessoire = new AccessoirePorte();
        
        try {
            accessoire.setCoordonneY(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        accessoire.setCoordonneY(new FractionImperiale(12,98,512));
        
        assertEquals(12, accessoire.getCoordonneeY().getPartieEntiere());
        assertEquals(98, accessoire.getCoordonneeY().getNumerateur());
        assertEquals(512, accessoire.getCoordonneeY().getDenominateur());
    }
    
    @Test
    public void testerSetEstTraversant() {
        AccessoirePorte accessoire = new AccessoirePorte();
        
        accessoire.setTraversant(true);
        assertTrue(accessoire.estTraversant());
        
        accessoire.setTraversant(false);
        assertFalse(accessoire.estTraversant());
    }
    
    @Test
    public void testerSetSelection() {
        AccessoirePorte accessoire = new AccessoirePorte();
        
        accessoire.setSelectionStatus(true);
        assertTrue(accessoire.getSelectionStatus());
        
        accessoire.setSelectionStatus(false);
        assertFalse(accessoire.getSelectionStatus());
    }
    
    @Test
    public void testerCloneAccessoire() {
        try {
            AccessoirePorte accessoire = new AccessoirePorte();
            AccessoirePorte accessoireClone = (AccessoirePorte) accessoire.clone();
            
            assertNotEquals(accessoire, accessoireClone);
            assertNotEquals(accessoire.getCoordonneeX(), accessoireClone.getCoordonneeX());
            assertNotEquals(accessoire.getCoordonneeY(), accessoireClone.getCoordonneeY());
            assertNotEquals(accessoire.getHauteur(), accessoireClone.getHauteur());
            assertNotEquals(accessoire.getLargeur(), accessoireClone.getLargeur());
            assertNotEquals(accessoire.getMarge(), accessoireClone.getMarge());
            assertEquals(accessoire.estTraversant(), accessoireClone.estTraversant());
            assertEquals(accessoire.getSelectionStatus(), accessoire.getSelectionStatus());
            
            assertEquals(accessoire.getCoordonneeX().getPartieEntiere(), accessoireClone.getCoordonneeX().getPartieEntiere());
            assertEquals(accessoire.getCoordonneeX().getNumerateur(), accessoireClone.getCoordonneeX().getNumerateur());
            assertEquals(accessoire.getCoordonneeX().getDenominateur(), accessoireClone.getCoordonneeX().getDenominateur());
            
            assertEquals(accessoire.getCoordonneeY().getPartieEntiere(), accessoireClone.getCoordonneeY().getPartieEntiere());
            assertEquals(accessoire.getCoordonneeY().getNumerateur(), accessoireClone.getCoordonneeY().getNumerateur());
            assertEquals(accessoire.getCoordonneeY().getDenominateur(), accessoireClone.getCoordonneeY().getDenominateur());
            
            assertEquals(accessoire.getHauteur().getPartieEntiere(), accessoireClone.getHauteur().getPartieEntiere());
            assertEquals(accessoire.getHauteur().getNumerateur(), accessoireClone.getHauteur().getNumerateur());
            assertEquals(accessoire.getHauteur().getDenominateur(), accessoireClone.getHauteur().getDenominateur());
            
            assertEquals(accessoire.getLargeur().getPartieEntiere(), accessoireClone.getLargeur().getPartieEntiere());
            assertEquals(accessoire.getLargeur().getNumerateur(), accessoireClone.getLargeur().getNumerateur());
            assertEquals(accessoire.getLargeur().getDenominateur(), accessoireClone.getLargeur().getDenominateur());
            
            assertEquals(accessoire.getMarge().getPartieEntiere(), accessoireClone.getMarge().getPartieEntiere());
            assertEquals(accessoire.getMarge().getNumerateur(), accessoireClone.getMarge().getNumerateur());
            assertEquals(accessoire.getMarge().getDenominateur(), accessoireClone.getMarge().getDenominateur());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
