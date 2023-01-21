package ca.ulaval.glo2004.domaine;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import ca.ulaval.glo2004.domaine.enums.ConfigurationPlis;
import ca.ulaval.glo2004.domaine.enums.PositionPlis;

public class PlisTest {
    private static Plis plis;
    
    @Before
    public void setup() {
        plis = new Plis();
    }
    
    // 1. Tester le constructeur par default
    // TODO : REVISER METHODE AIRE
    @Test
    public void testerConstructeurDefaultPlis() {
        assertEquals(PositionPlis.UNKNOWN, plis.getPosition());
        assertEquals(ConfigurationPlis.UNKNOWN, plis.getConfiguration());
        assertEquals(45.0, plis.getAngleSoudure(), 0);
        assertEquals(null, plis.getTrouVentilation());
        
        assertEquals(95, plis.getLongueur().getPartieEntiere());
        assertEquals(0, plis.getLongueur().getNumerateur());
        assertEquals(1, plis.getLongueur().getDenominateur());
        
        assertEquals(4, plis.getEpaisseurMurs().getPartieEntiere());
        assertEquals(0, plis.getEpaisseurMurs().getNumerateur());
        assertEquals(1, plis.getEpaisseurMurs().getDenominateur());
        
        assertEquals(2, plis.getMargeSoudure().getPartieEntiere());
        assertEquals(0, plis.getMargeSoudure().getNumerateur());
        assertEquals(1, plis.getMargeSoudure().getDenominateur());
        
        assertEquals(0, plis.getMargePlis().getPartieEntiere());
        assertEquals(1, plis.getMargePlis().getNumerateur());
        assertEquals(4, plis.getMargePlis().getDenominateur()); 
    }
    
    // 2. Tester les setters
    // setPositionPlis
    @Test
    public void testerSetPositionPlis() {
        plis.setPosition(PositionPlis.BAS);
        assertEquals(PositionPlis.BAS, plis.getPosition());
        
        plis.setPosition(PositionPlis.DROITE);
        assertEquals(PositionPlis.DROITE, plis.getPosition());

        plis.setPosition(PositionPlis.GAUCHE);
        assertEquals(PositionPlis.GAUCHE, plis.getPosition());

        plis.setPosition(PositionPlis.HAUT);
        assertEquals(PositionPlis.HAUT, plis.getPosition());

        plis.setPosition(PositionPlis.UNKNOWN);
        assertEquals(PositionPlis.UNKNOWN, plis.getPosition());
    }
    
    // setConfigurationPlis
    @Test
    public void testerSetConfigurationPlis() {
        plis.setConfiguration(ConfigurationPlis.COIN);
        assertEquals(ConfigurationPlis.COIN, plis.getConfiguration());
        
        plis.setConfiguration(ConfigurationPlis.PLAT);
        assertEquals(ConfigurationPlis.PLAT, plis.getConfiguration());
        
        plis.setConfiguration(ConfigurationPlis.UNKNOWN);
        assertEquals(ConfigurationPlis.UNKNOWN, plis.getConfiguration());        
    }
    
    // angleDeSoudure
    @Test
    public void testerSetAngleSoudure() {
        plis.setAngleSoudure(12.8);
        assertEquals(12.8, plis.getAngleSoudure(), 0);
        
        try {
             plis.setAngleSoudure(-0.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
        
        try {
            plis.setAngleSoudure(90.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
    }

    // setLonguerPlis
    @Test
    public void testerSetLongueurPlis() {
        plis.setLongueur(new FractionImperiale(10,40,8));
        
        assertEquals(10, plis.getLongueur().getPartieEntiere());
        assertEquals(40, plis.getLongueur().getNumerateur());
        assertEquals(8, plis.getLongueur().getDenominateur());
    }
    
    // setEpaisseurMurs
    @Test
    public void testerSetEpaissurMurs() {
        try {
            plis.setEpaisseurMurs(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        plis.setEpaisseurMurs(new FractionImperiale(37,20,64));
        
        assertEquals(37, plis.getEpaisseurMurs().getPartieEntiere());
        assertEquals(20, plis.getEpaisseurMurs().getNumerateur());
        assertEquals(64, plis.getEpaisseurMurs().getDenominateur());
    }
    
    // setMargeSoudure
    @Test
    public void testerSetMargeSoudure() {
        plis.setMargeSoudure(new FractionImperiale(12,17,32));
        
        assertEquals(12, plis.getMargeSoudure().getPartieEntiere());
        assertEquals(17, plis.getMargeSoudure().getNumerateur());
        assertEquals(32, plis.getMargeSoudure().getDenominateur());
    }
   
    // setMargePlis
    @Test
    public void testerSetMargePlis() {
        plis.setMargePlis(new FractionImperiale(4,24,2));
        
        assertEquals(4, plis.getMargePlis().getPartieEntiere());
        assertEquals(24, plis.getMargePlis().getNumerateur());
        assertEquals(2, plis.getMargePlis().getDenominateur());
    }
    
    @Test
    public void testerSetTrouVentilation() {
        Trou trouVentilation = new Trou();
        
        plis.setTrouVentilation(trouVentilation);
        
        assertEquals(trouVentilation, plis.getTrouVentilation());
    }
    
    @Test
    public void testerPlisClone() {
        try {
            Plis plisClone = (Plis) plis.clone();
            
            assertNotEquals(plis, plisClone);
            
            assertNotEquals(plis.getEpaisseurMurs(), plisClone.getEpaisseurMurs());
            assertNotEquals(plis.getLongueur(), plisClone.getLongueur());
            assertNotEquals(plis.getMargePlis(), plisClone.getMargePlis());
            assertNotEquals(plis.getMargeSoudure(), plisClone.getMargeSoudure());
            
            assertEquals(plis.getAngleSoudure(), plisClone.getAngleSoudure(), 0);
            assertEquals(plis.getTrouVentilation(), plisClone.getTrouVentilation());
            
            assertEquals(plis.getEpaisseurMurs().getPartieEntiere(), plisClone.getEpaisseurMurs().getPartieEntiere());
            assertEquals(plis.getEpaisseurMurs().getNumerateur(), plisClone.getEpaisseurMurs().getNumerateur());
            assertEquals(plis.getEpaisseurMurs().getDenominateur(), plisClone.getEpaisseurMurs().getDenominateur());
            
            assertEquals(plis.getLongueur().getPartieEntiere(), plisClone.getLongueur().getPartieEntiere());
            assertEquals(plis.getLongueur().getNumerateur(), plisClone.getLongueur().getNumerateur());
            assertEquals(plis.getLongueur().getDenominateur(), plisClone.getLongueur().getDenominateur());
            
            assertEquals(plis.getMargePlis().getPartieEntiere(), plisClone.getMargePlis().getPartieEntiere());
            assertEquals(plis.getMargePlis().getNumerateur(), plisClone.getMargePlis().getNumerateur());
            assertEquals(plis.getMargePlis().getDenominateur(), plisClone.getMargePlis().getDenominateur());
            
            assertEquals(plis.getMargeSoudure().getPartieEntiere(), plisClone.getMargeSoudure().getPartieEntiere());
            assertEquals(plis.getMargeSoudure().getNumerateur(), plisClone.getMargeSoudure().getNumerateur());
            assertEquals(plis.getMargeSoudure().getDenominateur(), plisClone.getMargeSoudure().getDenominateur());
            
            // Tester les enums
            plis.setConfiguration(ConfigurationPlis.COIN);
            assertNotEquals(plis.getConfiguration(), plisClone.getConfiguration());
            
            plis.setPosition(PositionPlis.DROITE);
            assertNotEquals(plis.getPosition(), plisClone.getPosition());
            
            // Tester le troue de ventilation
            plis.setTrouVentilation(new Trou());
            assertNotEquals(plis.getTrouVentilation(), plisClone.getTrouVentilation());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
