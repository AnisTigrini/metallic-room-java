package ca.ulaval.glo2004.domaine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;

public class SeparateurTest {
    
    private static Separateur separateur;
    
    @Before
    public void setup() {
        separateur = new Separateur();
    }
    
    // 1. Tester le constructeur par default du séparateur.
    @Test
    public void constructeurDefaultSeparateur() {
         FractionImperiale fractionImperiale = separateur.getPosition();
         
         assertFalse(separateur.getSelectionStatus());
         assertEquals(36, fractionImperiale.getPartieEntiere());
         assertEquals(0, fractionImperiale.getNumerateur());
         assertEquals(1, fractionImperiale.getDenominateur());
    }
    
    // 2. Valider setSelectionStatus (trivial)
    @Test
    public void validerSetSelectionStatus() {
        assertFalse(separateur.getSelectionStatus());
        
        separateur.setSelectionStatus(true);
        
        assertTrue(separateur.getSelectionStatus());
    }
    
    // 3. Valider setPosition (trivial)
    @Test
    public void validerSetPosition() {
        assertEquals(36, separateur.getPosition().getPartieEntiere());
        assertEquals(0, separateur.getPosition().getNumerateur());
        assertEquals(1, separateur.getPosition().getDenominateur());
         
        FractionImperiale nouvelleFractionImperiale = new FractionImperiale(10, 10,2);
        separateur.setPosition(nouvelleFractionImperiale);
        
        assertEquals(10, separateur.getPosition().getPartieEntiere());
        assertEquals(10, separateur.getPosition().getNumerateur());
        assertEquals(2, separateur.getPosition().getDenominateur());
    }
    
    // 4. Valider le constructeur de séparateur
    @Test
    public void validerConstructeurSeparateur() {
        FractionImperiale position = new FractionImperiale(8, 8,1);
        Separateur nouveauSeparateur = new Separateur(position, true);
        
        assertTrue(nouveauSeparateur.getSelectionStatus());
        assertEquals(8, nouveauSeparateur.getPosition().getPartieEntiere());
        assertEquals(8, nouveauSeparateur.getPosition().getNumerateur());
        assertEquals(1, nouveauSeparateur.getPosition().getDenominateur());
    }
    
    // 5. Valier la fonction compareTo.
    @Test
    public void validerCompareTo() {
        // Cas ou la position du separateur2 est inferieure.
        Separateur separateur2 = new Separateur(new FractionImperiale(8, 8,1), false);
        assertEquals(1, separateur.compareTo(separateur2));
        
        separateur2.setPosition(new FractionImperiale(35, 1,2));
        assertEquals(1, separateur.compareTo(separateur2));
        
        separateur2.setPosition(new FractionImperiale(35, 3,4));
        assertEquals(1, separateur.compareTo(separateur2));  
        
        separateur2.setPosition(new FractionImperiale(35, 63,64));
        assertEquals(1, separateur.compareTo(separateur2));  
        
        
        // Cas ou la position du separateur2 est superieure
        separateur2.setPosition(new FractionImperiale(36, 1,2));
        assertEquals(-1, separateur.compareTo(separateur2));
        
        separateur2.setPosition(new FractionImperiale(36, 3,4));
        assertEquals(-1, separateur.compareTo(separateur2));  
        
        separateur2.setPosition(new FractionImperiale(36, 63,64));
        assertEquals(-1, separateur.compareTo(separateur2));  
        
        
        // Cas ou la position des deux separateur est la meme sur le cote.
        separateur2.setPosition(new FractionImperiale(35, 999,1000));
        assertEquals(0, separateur.compareTo(separateur2));  
        
        separateur2.setPosition(new FractionImperiale(0, 72,2));
        assertEquals(0, separateur.compareTo(separateur2));
        
        separateur2.setPosition(new FractionImperiale(1, 70,2));
        assertEquals(0, separateur.compareTo(separateur2));
        
        separateur2.setPosition(new FractionImperiale(2, 136,4));
        assertEquals(0, separateur.compareTo(separateur2));
    }
    
    @Test
    public void testClone() {
        try {
            Separateur separateurClone = (Separateur) separateur.clone();
            
            assertNotEquals(separateur, separateurClone);
            assertEquals(separateur.getSelectionStatus(), separateurClone.getSelectionStatus());
            assertNotEquals(separateur.getPosition(), separateurClone.getPosition());
            assertEquals(separateur.getPosition().getPartieEntiere(), separateurClone.getPosition().getPartieEntiere());
            assertEquals(separateur.getPosition().getNumerateur(), separateurClone.getPosition().getNumerateur());
            assertEquals(separateur.getPosition().getDenominateur(), separateurClone.getPosition().getDenominateur());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
