package ca.ulaval.glo2004.domaine;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FractionImperialTest {
    private static FractionImperiale fractionImperiale;
    
    @Before
    public void setup() {
        fractionImperiale = new FractionImperiale();
    }
    
    // 1. La base (juste pour commencer).
    @Test
    public void parametresDeBaseDeConfiguration() {
        assertEquals(0, fractionImperiale.getPartieEntiere());
        assertEquals(0, fractionImperiale.getNumerateur());
        assertEquals(1, fractionImperiale.getDenominateur());
    }
    
    // 2. Cas normaux
    @Test 
    public void testerSetPartieEntiere() {
        fractionImperiale.setPartieEntiere(10);
        assertEquals(10, fractionImperiale.getPartieEntiere());
    }
    
    @Test 
    public void testerSetNumerateur() {
        fractionImperiale.setNumerateur(10);
        assertEquals(10, fractionImperiale.getNumerateur());
    }
    
    @Test 
    public void testerSetDenominateur() {
        List<Integer> denominateurImperial = Arrays.asList(1,2,4,8,16,32,64,128,256,512,1000);
        
        for (int i=0;i<denominateurImperial.size();i++) {
            int denominateur = denominateurImperial.get(i);
            fractionImperiale.setDenominateur(denominateur);
            assertEquals(denominateur, fractionImperiale.getDenominateur());
        }
    }
    
    
    // 3. Cas anormaux
    // 3.1 Rentrer des valeurs negatives
    @Test 
    public void testerSetPartieEntiereNegative() {
        try {
            fractionImperiale.setPartieEntiere(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("On ne peut pas entrer un nombre négatif.", e.getMessage());
        } 
    }
    
    @Test 
    public void testerSetNumerateurNegatif() {
        try {
            fractionImperiale.setNumerateur(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("On ne peut pas entrer un nombre négatif.", e.getMessage());
        } 
    }
    
    @Test 
    public void testerSetDenominateurNul() {
        try {
            fractionImperiale.setDenominateur(0);
        } catch (IllegalArgumentException e) {
            assertEquals("Ce denominateur n'est pas celui d'une fraction imperiale.", e.getMessage());
        } 
    }
    
    @Test 
    public void testerSetDenominateurNegatif() {
        try {
            fractionImperiale.setDenominateur(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("Ce denominateur n'est pas celui d'une fraction imperiale.", e.getMessage());
        }
    }
    
    @Test
    public void testerSetDenominateurPasDansListe() {
        try {
            fractionImperiale.setDenominateur(3);
        } catch (IllegalArgumentException e) {
            assertEquals("Ce denominateur n'est pas celui d'une fraction imperiale.", e.getMessage());
        }
    }
    
    @Test
    public void testmultiplierFractionSansNumerateur()
    {
        FractionImperiale fractionGauche = new FractionImperiale(10,0,1);
        FractionImperiale fractionDroite = new FractionImperiale(10,0,1);
        
        FractionImperiale resultat = fractionGauche.multiplierFraction(fractionDroite);
        assertEquals(100, resultat.getPartieEntiere());
        assertEquals(0, resultat.getNumerateur());
        assertEquals(1, resultat.getDenominateur());
    }
    
    @Test
    public void testmultiplierFractionAvecNumerateur()
    {
        // (82 / 8) * (7 / 4) => 574 / 32 => 17 et 30 / 32 
        FractionImperiale fractionGauche = new FractionImperiale(10,2,8);
        FractionImperiale fractionDroite = new FractionImperiale(1,3,4);
        
        FractionImperiale resultat = fractionGauche.multiplierFraction(fractionDroite);

        assertEquals(17, resultat.getPartieEntiere());
        assertEquals(30, resultat.getNumerateur());
        assertEquals(32, resultat.getDenominateur());
    }
    
    @Test
    public void testAdditionFractionSansNumerateur()
    {
        FractionImperiale fractionGauche = new FractionImperiale(10,0,1);
        FractionImperiale fractionDroite = new FractionImperiale(1,0,1);
        FractionImperiale resultat = fractionGauche.additionnerFraction(fractionDroite);
        assertEquals(11, resultat.getPartieEntiere());
        assertEquals(0, resultat.getNumerateur());
        assertEquals(1, resultat.getDenominateur());
    }
    
    @Test
    public void testAdditionFractionAvecNumerateur()
    {
        FractionImperiale fractionGauche = new FractionImperiale(36,0,1);
        FractionImperiale fractionDroite = new FractionImperiale(0,1,8);
        FractionImperiale resultat = fractionGauche.additionnerFraction(fractionDroite);
        assertEquals(36, resultat.getPartieEntiere());
        assertEquals(1, resultat.getNumerateur());
        assertEquals(8, resultat.getDenominateur());
    }
    
    @Test
    public void testToDouble()
    {
       FractionImperiale fraction = new FractionImperiale(36,1,8);
       assertEquals(36.125, fraction.toDouble(), 0.0);
    }
    
    // Tester le constructeur avec parametres.
    @Test
    public void testerConstructeurParametres() {
        // fraction imperiale normale pas d'exceptions
        FractionImperiale fi = new  FractionImperiale(36,1,8);
        
        // fraction imperiale avec un des parametres negatifs
        try {
            fi = new FractionImperiale(-1,1,8);   
        } catch (IllegalArgumentException e) {
            assertEquals("On ne peut pas entrer un nombre négatif.",e.getMessage());
        }
        
        try {
            fi = new FractionImperiale(1,-1,8);   
        } catch (IllegalArgumentException e) {
            assertEquals("On ne peut pas entrer un nombre négatif.",e.getMessage());
        }
        
        try {
            fi = new FractionImperiale(1,1,-8);   
        } catch (IllegalArgumentException e) {
            assertEquals("Ce denominateur n'est pas celui d'une fraction imperiale.",e.getMessage());
        }
    }
    
    @Test
    public void testerClone() {
        FractionImperiale fraction = new FractionImperiale(36,1,8);
        
        try {
            FractionImperiale fraction2 = (FractionImperiale) fraction.clone();
            
            assertNotEquals(fraction, fraction2);
            assertEquals(fraction.getPartieEntiere(), fraction2.getPartieEntiere());
            assertEquals(fraction.getNumerateur(), fraction2.getNumerateur());
            assertEquals(fraction.getDenominateur(), fraction2.getDenominateur());

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
