package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.Orientation;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SalleTest { 
    private static Salle salle;
    
    @Before
    public void setup() {
        salle = new Salle();
    }
    
    // 1. Tester le constructeur par défault de salle.
    @Test
    public void constructeurDefaultSalle() {
        Cote coteNord = salle.getCoteNord();
        Cote coteSud = salle.getCoteSud();
        Cote coteEst= salle.getCoteEst();
        Cote coteOuest = salle.getCoteOuest();
        
        assertEquals(Orientation.NORD, coteNord.getOrientation());
        assertEquals(Orientation.SUD, coteSud.getOrientation());
        assertEquals(Orientation.EST, coteEst.getOrientation());
        assertEquals(Orientation.OUEST, coteOuest.getOrientation());
        
        assertEquals(140, salle.getLongueurSalle().getPartieEntiere());
        assertEquals(0, salle.getLongueurSalle().getNumerateur());
        assertEquals(1, salle.getLongueurSalle().getDenominateur());
        
        assertEquals(140, salle.getLargeurSalle().getPartieEntiere());
        assertEquals(0, salle.getLargeurSalle().getNumerateur());
        assertEquals(1, salle.getLargeurSalle().getDenominateur());
        
        assertEquals(96,salle.getHauteurMurs().getPartieEntiere());
        assertEquals(0, salle.getHauteurMurs().getNumerateur());
        assertEquals(1, salle.getHauteurMurs().getDenominateur());
        
        assertEquals(4, salle.getEpaisseurDesMurs().getPartieEntiere());
        assertEquals(0, salle.getEpaisseurDesMurs().getNumerateur());
        assertEquals(1, salle.getEpaisseurDesMurs().getDenominateur());
        
        assertEquals(0, salle.getMargeDesPlis().getPartieEntiere());
        assertEquals(1, salle.getMargeDesPlis().getNumerateur());
        assertEquals(4, salle.getMargeDesPlis().getDenominateur());
        
        assertEquals(45.0d, salle.getAngleDesSoudures(), 0);
    }
    
    // 2. Tester les setter de tous les côtés.
    // 2.1 Cote Nord
    @Test
    public void testerSetCoteNord() {
        try {
            salle.setCoteNord(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de côté.", e.getMessage());
        }
       
        try {
            salle.setCoteNord(new Cote(Orientation.EST));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteNord(new Cote(Orientation.OUEST));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteNord(new Cote(Orientation.SUD));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        salle.setCoteNord(new Cote(Orientation.NORD));
        assertEquals(Orientation.NORD, salle.getCoteNord().getOrientation());
    }
    
    // 2.2 Cote Sud
    @Test
    public void testerSetCoteSud() {
        try {
            salle.setCoteSud(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de côté.", e.getMessage());
        }
       
        try {
            salle.setCoteSud(new Cote(Orientation.EST));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteSud(new Cote(Orientation.OUEST));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteSud(new Cote(Orientation.NORD));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        salle.setCoteSud(new Cote(Orientation.SUD));
        assertEquals(Orientation.SUD, salle.getCoteSud().getOrientation());
    }
    
    // 2.3 Cote Est
    @Test
    public void testerSetCoteEst() {
        try {
            salle.setCoteEst(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de côté.", e.getMessage());
        }
       
        try {
            salle.setCoteEst(new Cote(Orientation.SUD));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteEst(new Cote(Orientation.OUEST));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteEst(new Cote(Orientation.NORD));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        salle.setCoteEst(new Cote(Orientation.EST));
        assertEquals(Orientation.EST, salle.getCoteEst().getOrientation());
    }
    
    // 2.4 Cote Ouest
    @Test
    public void testerSetCoteOuest() {
        try {
            salle.setCoteOuest(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de côté.", e.getMessage());
        }
       
        try {
            salle.setCoteOuest(new Cote(Orientation.SUD));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteOuest(new Cote(Orientation.EST));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        try {
            salle.setCoteOuest(new Cote(Orientation.NORD));
        } catch (IllegalArgumentException e) {
            assertEquals("Le côté passé en paramètre n'a pas la bonne orientation.", e.getMessage());
        }
        
        salle.setCoteOuest(new Cote(Orientation.OUEST));
        assertEquals(Orientation.OUEST, salle.getCoteOuest().getOrientation());
    }
    
    // 3. Tester getAllCotes
    @Test
    public void testerGetAllCotes() {
         ArrayList<Cote> allCotes = salle.getAllCotes();
         
         assertEquals(4, allCotes.size());
         assertEquals(Orientation.NORD, allCotes.get(0).getOrientation());
         assertEquals(Orientation.SUD, allCotes.get(1).getOrientation());
         assertEquals(Orientation.EST, allCotes.get(2).getOrientation());
         assertEquals(Orientation.OUEST, allCotes.get(3).getOrientation());
    }
    
    // TODO : CODE À MODIFIER BEAUCOUP DE TRAVAIL.
    // 4. Tester les setters
    // 4.1 largeurSalle.
    @Test
    public void testerSetLargeure() {
        try {
            salle.setLargeurSalle(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        salle.setLargeurSalle(new FractionImperiale(10,10,2));

        assertEquals(10, salle.getLargeurSalle().getPartieEntiere());
        assertEquals(10, salle.getLargeurSalle().getNumerateur());
        assertEquals(2, salle.getLargeurSalle().getDenominateur());
    }
    
    // 4.2 longueurSalle.
    @Test
    public void testerSetLongueure() {
        try {
            salle.setLongueurSalle(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        salle.setLongueurSalle(new FractionImperiale(10,10,2));

        assertEquals(10, salle.getLongueurSalle().getPartieEntiere());
        assertEquals(10, salle.getLongueurSalle().getNumerateur());
        assertEquals(2, salle.getLongueurSalle().getDenominateur());
    }
    
    
    @Test
    public void testerSetAngleDeSoudure() {
        assertEquals(45.0, salle.getAngleDesSoudures(), 0);
        
        salle.setAngleDesSoudures(65.89);
        
        assertEquals(65.89, salle.getAngleDesSoudures(), 0);
        
        try {
             salle.setAngleDesSoudures(-0.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
        
        try {
            salle.setAngleDesSoudures(90.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
    }
    
    // 4.4 setEpaisseurDesMurs
    @Test
    public void testerSetEpaisseurDesMurs() {
        try {
             salle.setEpaisseurDesMurs(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        salle.setEpaisseurDesMurs(new FractionImperiale(10,24,4));
        
        ArrayList<Mur> coteNordMurs = salle.getCoteNord().getMurs();
        ArrayList<Mur> coteSudMurs = salle.getCoteSud().getMurs();
        ArrayList<Mur> coteEstMurs = salle.getCoteEst().getMurs();
        ArrayList<Mur> coteOuestMurs = salle.getCoteOuest().getMurs();
        
        // Verifier sur chaque mur que l'epaisseur des murs
        for (int i = 0; i< coteNordMurs.size(); i++) {
            Mur mur = coteNordMurs.get(i);
            assertEquals(10, mur.getEpaisseurDesMurs().getPartieEntiere());
            assertEquals(24, mur.getEpaisseurDesMurs().getNumerateur());
            assertEquals(4, mur.getEpaisseurDesMurs().getDenominateur());        
        }
        
        for (int i = 0; i< coteSudMurs.size(); i++) {
            Mur mur = coteSudMurs.get(i);
            assertEquals(10, mur.getEpaisseurDesMurs().getPartieEntiere());
            assertEquals(24, mur.getEpaisseurDesMurs().getNumerateur());
            assertEquals(4, mur.getEpaisseurDesMurs().getDenominateur());        
        }
                
        for (int i = 0; i< coteEstMurs.size(); i++) {
            Mur mur = coteEstMurs.get(i);
            assertEquals(10, mur.getEpaisseurDesMurs().getPartieEntiere());
            assertEquals(24, mur.getEpaisseurDesMurs().getNumerateur());
            assertEquals(4, mur.getEpaisseurDesMurs().getDenominateur());        
        }
        
        for (int i = 0; i< coteOuestMurs.size(); i++) {
            Mur mur = coteOuestMurs.get(i);
            assertEquals(10, mur.getEpaisseurDesMurs().getPartieEntiere());
            assertEquals(24, mur.getEpaisseurDesMurs().getNumerateur());
            assertEquals(4, mur.getEpaisseurDesMurs().getDenominateur());        
        }
    }
    
    // 4.5 setMargeDesPlis
    @Test
    public void testerSetMargeDesPlis() {
        try {
             salle.setMargeDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        salle.setMargeDesPlis(new FractionImperiale(15,3,8));
        
        ArrayList<Mur> coteNordMurs = salle.getCoteNord().getMurs();
        ArrayList<Mur> coteSudMurs = salle.getCoteSud().getMurs();
        ArrayList<Mur> coteEstMurs = salle.getCoteEst().getMurs();
        ArrayList<Mur> coteOuestMurs = salle.getCoteOuest().getMurs();
        
        for (int i = 0; i< coteNordMurs.size(); i++) {
            Mur mur = coteNordMurs.get(i);
            assertEquals(15, mur.getMargeDesPlis().getPartieEntiere());
            assertEquals(3, mur.getMargeDesPlis().getNumerateur());
            assertEquals(8, mur.getMargeDesPlis().getDenominateur());        
        }
        
        for (int i = 0; i< coteSudMurs.size(); i++) {
            Mur mur = coteSudMurs.get(i);
            assertEquals(15, mur.getMargeDesPlis().getPartieEntiere());
            assertEquals(3, mur.getMargeDesPlis().getNumerateur());
            assertEquals(8, mur.getMargeDesPlis().getDenominateur());        
        }
                
        for (int i = 0; i< coteEstMurs.size(); i++) {
            Mur mur = coteEstMurs.get(i);
            assertEquals(15, mur.getMargeDesPlis().getPartieEntiere());
            assertEquals(3, mur.getMargeDesPlis().getNumerateur());
            assertEquals(8, mur.getMargeDesPlis().getDenominateur());        
        }
        
        for (int i = 0; i< coteOuestMurs.size(); i++) {
            Mur mur = coteOuestMurs.get(i);
            assertEquals(15, mur.getMargeDesPlis().getPartieEntiere());
            assertEquals(3, mur.getMargeDesPlis().getNumerateur());
            assertEquals(8, mur.getMargeDesPlis().getDenominateur());        
        }
    }
    
    // 4.6 setHauteurDesMurs
    @Test
    public void testerSetHauteurDesMurs() {
        try {
            salle.setHauteurDesMurs(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        // Cas Normal
        FractionImperiale hauteurDesMurs = new FractionImperiale(10,4,8);
        salle.setHauteurDesMurs(hauteurDesMurs);
        
        assertEquals(10, salle.getHauteurMurs().getPartieEntiere());
        assertEquals(4, salle.getHauteurMurs().getNumerateur());
        assertEquals(8, salle.getHauteurMurs().getDenominateur());

    }
    
    // 4.7 setLongueurDesPlis
    @Test
    public void testerSetLongueurDesPlis() {
        try {
            salle.setLongueurDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        salle.setLongueurDesPlis(new FractionImperiale(10,24,8));
        
        assertEquals(10, salle.getLongueurDesPlis().getPartieEntiere());
        assertEquals(24, salle.getLongueurDesPlis().getNumerateur());
        assertEquals(8, salle.getLongueurDesPlis().getDenominateur());
    }
    
    // 4.8 setMargeDeSoudure
    @Test
    public void testerSetMargeDeSoudure() {
        try {
            salle.setMargeDesSoudures(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        salle.setMargeDesSoudures(new FractionImperiale(120,1,2));
        
        ArrayList<Mur> coteNordMurs = salle.getCoteNord().getMurs();
        ArrayList<Mur> coteSudMurs = salle.getCoteSud().getMurs();
        ArrayList<Mur> coteEstMurs = salle.getCoteEst().getMurs();
        ArrayList<Mur> coteOuestMurs = salle.getCoteOuest().getMurs();
        
        for (int i = 0; i< coteNordMurs.size(); i++) {
            Mur mur = coteNordMurs.get(i);
            assertEquals(120, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(1, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(2, mur.getMargeDesSoudures().getDenominateur());        
        }
        
        for (int i = 0; i< coteSudMurs.size(); i++) {
            Mur mur = coteSudMurs.get(i);
            assertEquals(120, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(1, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(2, mur.getMargeDesSoudures().getDenominateur());        
        }
                
        for (int i = 0; i< coteEstMurs.size(); i++) {
            Mur mur = coteEstMurs.get(i);
            assertEquals(120, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(1, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(2, mur.getMargeDesSoudures().getDenominateur());        
        }
        
        for (int i = 0; i< coteOuestMurs.size(); i++) {
            Mur mur = coteOuestMurs.get(i);
            assertEquals(120, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(1, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(2, mur.getMargeDesSoudures().getDenominateur());        
        }
    }
    
    @Test
    public void testerSalleClone() {
        try {
            Salle salleClone = salle.clone();
            
            assertNotEquals(salle, salleClone);
            
            assertNotEquals(salle.getCoteEst(), salleClone.getCoteEst());
            assertNotEquals(salle.getCoteNord(), salleClone.getCoteNord());
            assertNotEquals(salle.getCoteOuest(), salleClone.getCoteOuest());
            assertNotEquals(salle.getCoteSud(), salleClone.getCoteSud());
            
            assertEquals(salle.getAngleDesSoudures(), salleClone.getAngleDesSoudures(), 0);
            
            assertNotEquals(salle.getLongueurSalle(), salleClone.getLongueurSalle());
            assertNotEquals(salle.getLargeurSalle(), salleClone.getLargeurSalle());
            assertNotEquals(salle.getEpaisseurDesMurs(), salleClone.getEpaisseurDesMurs());
            assertNotEquals(salle.getLongueurDesPlis(), salleClone.getLongueurDesPlis());
            assertNotEquals(salle.getMargeDesSoudures(), salleClone.getMargeDesSoudures());
            assertNotEquals(salle.getMargeDesPlis(), salleClone.getMargeDesPlis());
            assertNotEquals(salle.getHauteurMurs(), salleClone.getHauteurMurs());
            
            assertEquals(salle.getLongueurSalle().getPartieEntiere(), salleClone.getLongueurSalle().getPartieEntiere());
            assertEquals(salle.getLongueurSalle().getNumerateur(), salleClone.getLongueurSalle().getNumerateur());
            assertEquals(salle.getLongueurSalle().getDenominateur(), salleClone.getLongueurSalle().getDenominateur());
            
            assertEquals(salle.getLargeurSalle().getPartieEntiere(), salleClone.getLargeurSalle().getPartieEntiere());
            assertEquals(salle.getLargeurSalle().getNumerateur(), salleClone.getLargeurSalle().getNumerateur());
            assertEquals(salle.getLargeurSalle().getDenominateur(), salleClone.getLargeurSalle().getDenominateur());
            
            assertEquals(salle.getEpaisseurDesMurs().getPartieEntiere(), salleClone.getEpaisseurDesMurs().getPartieEntiere());
            assertEquals(salle.getEpaisseurDesMurs().getNumerateur(), salleClone.getEpaisseurDesMurs().getNumerateur());
            assertEquals(salle.getEpaisseurDesMurs().getDenominateur(), salleClone.getEpaisseurDesMurs().getDenominateur());

            assertEquals(salle.getLongueurDesPlis().getPartieEntiere(), salleClone.getLongueurDesPlis().getPartieEntiere());
            assertEquals(salle.getLongueurDesPlis().getNumerateur(), salleClone.getLongueurDesPlis().getNumerateur());
            assertEquals(salle.getLongueurDesPlis().getDenominateur(), salleClone.getLongueurDesPlis().getDenominateur());
            
            assertEquals(salle.getMargeDesSoudures().getPartieEntiere(), salleClone.getMargeDesSoudures().getPartieEntiere());
            assertEquals(salle.getMargeDesSoudures().getNumerateur(), salleClone.getMargeDesSoudures().getNumerateur());
            assertEquals(salle.getMargeDesSoudures().getDenominateur(), salleClone.getMargeDesSoudures().getDenominateur());
            
            assertEquals(salle.getMargeDesPlis().getPartieEntiere(), salleClone.getMargeDesPlis().getPartieEntiere());
            assertEquals(salle.getMargeDesPlis().getNumerateur(), salleClone.getMargeDesPlis().getNumerateur());
            assertEquals(salle.getMargeDesPlis().getDenominateur(), salleClone.getMargeDesPlis().getDenominateur());
            
            assertEquals(salle.getHauteurMurs().getPartieEntiere(), salleClone.getHauteurMurs().getPartieEntiere());
            assertEquals(salle.getHauteurMurs().getNumerateur(), salleClone.getHauteurMurs().getNumerateur());
            assertEquals(salle.getHauteurMurs().getDenominateur(), salleClone.getHauteurMurs().getDenominateur());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
