package ca.ulaval.glo2004.domaine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConfigurationTest {
    private static Configuration configuration;
    
    @Before
    public void setup() {
        configuration = new Configuration();
    }
    
    // 1. La base (juste pour commencer).
    @Test
    public void parametresDeBaseDeConfiguration() {
        String cheminDeSauvegarde = System.getProperty("user.home") + '/';
        
        assertEquals("Projet", configuration.getProjectName());
        assertFalse(configuration.isSaved());
        assertEquals(cheminDeSauvegarde, configuration.getStorePath());
        assertTrue(configuration.isValidConfiguration());
        assertEquals(cheminDeSauvegarde + "Projet.mrcl", configuration.getCheminTotalDeSauvegarde());
    }
    
    // 2. Nom de projet valide devrait mettre a jour le parametre associé.
    @Test
     public void setProjectNameAuDessusValide() {
        configuration.setProjectName("Muracle");
         
        assertEquals("Muracle", configuration.getProjectName());
        assertTrue(configuration.isValidConfiguration());
        assertEquals(configuration.getStorePath() + "Muracle.mrcl", configuration.getCheminTotalDeSauvegarde());
     }
    
    // 3. Nom de projet au dessus de 255 caracteres lance exception.
    @Test
    public void setProjectNameAuDessus255() {
        String nomSuperLong = "";
        for (int i = 0; i<255; i++) {
            nomSuperLong += "a";
        }

        configuration.setProjectName(nomSuperLong);
        
        assertEquals(nomSuperLong, configuration.getProjectName());
        assertFalse(configuration.isValidConfiguration());
        assertEquals(configuration.getStorePath() + nomSuperLong + ".mrcl", configuration.getCheminTotalDeSauvegarde());
    }
    
    // 4. Nom de projet est vide.
    @Test
    public void setProjectNameAuDessusVide() {
        configuration.setProjectName("");
        
        assertEquals("", configuration.getProjectName());
        assertFalse(configuration.isValidConfiguration());
        assertEquals(configuration.getStorePath() + ".mrcl", configuration.getCheminTotalDeSauvegarde());
    }
    
    // 5. Nom de projet avec caracteres illegaux lance exception.
    @Test
    public void setProjectNameCaractereIllegaux() {
        configuration.setProjectName("test@");

        assertEquals("test@", configuration.getProjectName());
        assertFalse(configuration.isValidConfiguration());
        assertEquals(configuration.getStorePath() + "test@" + ".mrcl", configuration.getCheminTotalDeSauvegarde());
    }
    
    // 6. Chemin de sauvegarde valide.
    @Test
    public void setStorePathValide() {
        String pathValideDansToutOS = System.getProperty("user.home");
        configuration.setStorePath(pathValideDansToutOS);
        
        assertEquals(pathValideDansToutOS + '/', configuration.getStorePath());
        // Nom de projet toujours Muracle par default (valide).
        assertTrue(configuration.isValidConfiguration());
    }
    
    // 6.1 Donner un chemin de sauvegarde qui finit deja avec /.
    @Test
    public void setStorePathValideDeux() {
        String pathValideDansToutOS = System.getProperty("user.home") + '/';
        configuration.setStorePath(pathValideDansToutOS);
        
        assertEquals(pathValideDansToutOS, configuration.getStorePath());
        // Nom de projet toujours Muracle par default (valide).
        assertTrue(configuration.isValidConfiguration());
        assertEquals(configuration.getStorePath() + "Projet" + ".mrcl", configuration.getCheminTotalDeSauvegarde());
    }
    
    // 7. Chemin de sauvegarde qui n'existe pas.
    @Test
    public void setStorePathInvalide() {
        configuration.setStorePath("ksjdhas/asdasd/asd");
        
        assertEquals("ksjdhas/asdasd/asd/", configuration.getStorePath());
        assertFalse(configuration.isValidConfiguration());
        assertEquals("ksjdhas/asdasd/asd/Projet.mrcl", configuration.getCheminTotalDeSauvegarde());
    }
    
    // 8. Cas Trivial.
    @Test
    public void setIsSaved() {
        configuration.setIsSaved(true);
        
        assertTrue(configuration.isSaved());
    }
}
