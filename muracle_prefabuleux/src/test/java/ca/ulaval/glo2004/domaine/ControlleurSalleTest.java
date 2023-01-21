package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.DTO.FractionImperialeDTO;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
        
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;

public class ControlleurSalleTest {
    private static ControlleurSalle controlleurSalle;
    
    @Before
    public void setup() {
        controlleurSalle = new ControlleurSalle();
    }
    
    // 1. Constructeur par default.
    @Test
    public void validerConstructeurParDefault() {
        assertNull(controlleurSalle.getConfiguration());
        assertNotNull(controlleurSalle.getSalle());
        
        assertEquals(140, controlleurSalle.getLongueurSalle().partieEntiere);
        assertEquals(0, controlleurSalle.getLongueurSalle().numerateur);
        assertEquals(1, controlleurSalle.getLongueurSalle().denominateur);
        
        assertEquals(140, controlleurSalle.getLargeurSalle().partieEntiere);
        assertEquals(0, controlleurSalle.getLargeurSalle().numerateur);
        assertEquals(1, controlleurSalle.getLargeurSalle().denominateur);
        
        assertEquals(4,controlleurSalle.getEpaisseurDesMurs().partieEntiere);
        assertEquals(0,controlleurSalle.getEpaisseurDesMurs().numerateur);
        assertEquals(1,controlleurSalle.getEpaisseurDesMurs().denominateur);
        
        assertEquals(95, controlleurSalle.getLongueurDesPlis().partieEntiere);
        assertEquals(0, controlleurSalle.getLongueurDesPlis().numerateur);
        assertEquals(1, controlleurSalle.getLongueurDesPlis().denominateur);
        
        assertEquals(96,controlleurSalle.getHauteurMurs().partieEntiere);
        assertEquals(0,controlleurSalle.getHauteurMurs().numerateur);
        assertEquals(1,controlleurSalle.getHauteurMurs().denominateur);
        
        assertEquals(45.0, controlleurSalle.getAngleDesSoudures(), 0);
        
        assertEquals(0, controlleurSalle.getMargeDesPlis().partieEntiere);
        assertEquals(1, controlleurSalle.getMargeDesPlis().numerateur);
        assertEquals(4, controlleurSalle.getMargeDesPlis().denominateur);
        
        assertEquals(2, controlleurSalle.getMargeDesSoudures().partieEntiere);
        assertEquals(0, controlleurSalle.getMargeDesSoudures().numerateur);
        assertEquals(1, controlleurSalle.getMargeDesSoudures().denominateur);
        
        Cote coteNord = controlleurSalle.getCote(Orientation.NORD);
        Cote coteNord2 = controlleurSalle.getCote(Orientation.UNKNOWN);
        Cote coteSud = controlleurSalle.getCote(Orientation.SUD);
        Cote coteEst = controlleurSalle.getCote(Orientation.EST);
        Cote coteOuest = controlleurSalle.getCote(Orientation.OUEST);

        assertEquals(Orientation.NORD, coteNord.getOrientation());
        assertEquals(Orientation.NORD, coteNord2.getOrientation());
        assertEquals(Orientation.SUD, coteSud.getOrientation());
        assertEquals(Orientation.EST, coteEst.getOrientation());
        assertEquals(Orientation.OUEST, coteOuest.getOrientation());
    }
    
    @Test
    public void testerAjouterConfiguration() {
        controlleurSalle.ajouterConfiguration();
        
        assertNotNull(controlleurSalle.getConfiguration());
    }
    
    @Test
    public void testerSetLongueurSalle() {
        try {

            controlleurSalle.setLargeurSalle(null);
        } catch (Exception e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        controlleurSalle.setLongueurSalle(new FractionImperialeDTO(10,34,8));
        
        assertEquals(10, controlleurSalle.getLongueurSalle().partieEntiere);
        assertEquals(34, controlleurSalle.getLongueurSalle().numerateur);
        assertEquals(8, controlleurSalle.getLongueurSalle().denominateur);
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setLongueurSalle(new FractionImperialeDTO(10,34,8));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerSetLargeureSalle() {
        try {
            controlleurSalle.setLargeurSalle(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        controlleurSalle.setLargeurSalle(new FractionImperialeDTO(981,29,64));
                
        assertEquals(981, controlleurSalle.getLargeurSalle().partieEntiere);
        assertEquals(29, controlleurSalle.getLargeurSalle().numerateur);
        assertEquals(64, controlleurSalle.getLargeurSalle().denominateur);
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setLargeurSalle(new FractionImperialeDTO(981,29,64));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerSetEpaisseurMurs() {
        try {
            controlleurSalle.setEpaisseurDesMurs(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        controlleurSalle.setEpaisseurDesMurs(new FractionImperialeDTO(0,1,512));
        
        assertEquals(0, controlleurSalle.getEpaisseurDesMurs().partieEntiere);
        assertEquals(1, controlleurSalle.getEpaisseurDesMurs().numerateur);
        assertEquals(512, controlleurSalle.getEpaisseurDesMurs().denominateur);
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setEpaisseurDesMurs(new FractionImperialeDTO(0,1,512));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerSetLonguerPlis() {
        try {
             controlleurSalle.setLongueurDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
        
        controlleurSalle.setLongueurDesPlis(new FractionImperialeDTO(2,1,1));
        
        assertEquals(2, controlleurSalle.getLongueurDesPlis().partieEntiere);
        assertEquals(1, controlleurSalle.getLongueurDesPlis().numerateur);
        assertEquals(1, controlleurSalle.getLongueurDesPlis().denominateur);
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setLongueurDesPlis(new FractionImperialeDTO(2,1,1));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerSetHauteurMurs() {
        try {
             controlleurSalle.setLongueurDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        controlleurSalle.setHauteurMurs(new FractionImperialeDTO(10,26,2));
        
        assertEquals(10, controlleurSalle.getHauteurMurs().partieEntiere);
        assertEquals(26, controlleurSalle.getHauteurMurs().numerateur);
        assertEquals(2, controlleurSalle.getHauteurMurs().denominateur);
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setHauteurMurs(new FractionImperialeDTO(10,26,2));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerSetAngleSoudure() {
        controlleurSalle.setAngleDesSoudures(12.8);
        assertEquals(12.8, controlleurSalle.getAngleDesSoudures(), 0);
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setAngleDesSoudures(12.8);
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        try {
             controlleurSalle.setAngleDesSoudures(-0.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
        
        try {
            controlleurSalle.setAngleDesSoudures(90.01);
        } catch (IllegalArgumentException e) {
            assertEquals("L'angle doit être entre 0 et 360 degrés.", e.getMessage());
        }
    }
    
    @Test
    public void testerSetMargePlis() {
        try {
             controlleurSalle.setMargeDesPlis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }        
        
        controlleurSalle.setMargeDesPlis(new FractionImperialeDTO(15,3,8));
        
        ArrayList<Mur> coteNordMurs = controlleurSalle.getCote(Orientation.NORD).getMurs();
        ArrayList<Mur> coteSudMurs = controlleurSalle.getCote(Orientation.SUD).getMurs();
        ArrayList<Mur> coteEstMurs = controlleurSalle.getCote(Orientation.EST).getMurs();
        ArrayList<Mur> coteOuestMurs = controlleurSalle.getCote(Orientation.OUEST).getMurs();
        
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
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setMargeDesPlis(new FractionImperialeDTO(15,3,8));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerSetMargeSoudure() {
        try {
             controlleurSalle.setMargeDesSoudures(null);
        } catch (IllegalArgumentException e) {
            assertEquals("L'argument passé en paramètre n'est pas une instance de FractionImperiale.", e.getMessage());
        }
                
        controlleurSalle.setMargeDesSoudures(new FractionImperialeDTO(4,2567,64));
        
        ArrayList<Mur> coteNordMurs = controlleurSalle.getCote(Orientation.NORD).getMurs();
        ArrayList<Mur> coteSudMurs = controlleurSalle.getCote(Orientation.SUD).getMurs();
        ArrayList<Mur> coteEstMurs = controlleurSalle.getCote(Orientation.EST).getMurs();
        ArrayList<Mur> coteOuestMurs = controlleurSalle.getCote(Orientation.OUEST).getMurs();
        
        for (int i = 0; i< coteNordMurs.size(); i++) {
            Mur mur = coteNordMurs.get(i);
            assertEquals(4, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(2567, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(64, mur.getMargeDesSoudures().getDenominateur());        
        }
        
        for (int i = 0; i< coteSudMurs.size(); i++) {
            Mur mur = coteSudMurs.get(i);
            assertEquals(4, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(2567, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(64, mur.getMargeDesSoudures().getDenominateur());        
        }
                
        for (int i = 0; i< coteEstMurs.size(); i++) {
            Mur mur = coteEstMurs.get(i);
            assertEquals(4, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(2567, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(64, mur.getMargeDesSoudures().getDenominateur());        
        }
        
        for (int i = 0; i< coteOuestMurs.size(); i++) {
            Mur mur = coteOuestMurs.get(i);
            assertEquals(4, mur.getMargeDesSoudures().getPartieEntiere());
            assertEquals(2567, mur.getMargeDesSoudures().getNumerateur());
            assertEquals(64, mur.getMargeDesSoudures().getDenominateur());        
        }
        
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
        
        // Modifier avec la meme fraction imperiale ne rajoute pas de copies de la salle.
        controlleurSalle.setMargeDesSoudures(new FractionImperialeDTO(4,2567,64));
        assertEquals(2, controlleurSalle.listeSalle.size());
        assertEquals(1, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerRestaurerConfig() {
        controlleurSalle.restoreDefaultConfig();
        assertNull(controlleurSalle.getConfiguration());
    }
    
    @Test
    public void testerMurIsSelected() {
        // Tester que aucun mur est selectionne
        assertFalse(controlleurSalle.murIsSelected(Orientation.UNKNOWN, 11120));
        
        assertFalse(controlleurSalle.murIsSelected(Orientation.NORD, 0));
        assertFalse(controlleurSalle.murIsSelected(Orientation.NORD, 1));
        
        assertFalse(controlleurSalle.murIsSelected(Orientation.SUD, 0));
        assertFalse(controlleurSalle.murIsSelected(Orientation.SUD, 1));
        
        assertFalse(controlleurSalle.murIsSelected(Orientation.EST, 0));
        assertFalse(controlleurSalle.murIsSelected(Orientation.EST, 1));
        
        assertFalse(controlleurSalle.murIsSelected(Orientation.OUEST, 0));
        assertFalse(controlleurSalle.murIsSelected(Orientation.OUEST, 1));
        
        // Tester que les index outs of bounds retourne faux
        assertFalse(controlleurSalle.murIsSelected(Orientation.OUEST, 154));
        
        // Tester que les valeurs negatives retournent faux
        assertFalse(controlleurSalle.murIsSelected(Orientation.OUEST, -1));
    }
    
    @Test
    public void testerUndoEtRedo() {
        // 1. Faire un total de 3 modifications.
        controlleurSalle.setLargeurSalle(new FractionImperialeDTO(90,20,2));
        controlleurSalle.setLongueurSalle(new FractionImperialeDTO(94,8,8));
        controlleurSalle.setHauteurMurs(new FractionImperialeDTO(18,1,1));
        
        assertEquals(4,controlleurSalle.listeSalle.size());
        assertEquals(3, controlleurSalle.positionSalle);
        
        // 2. Verifier que la salle est a jour selon la derniere modification.
        assertEquals(90, controlleurSalle.getLargeurSalle().partieEntiere);
        assertEquals(20, controlleurSalle.getLargeurSalle().numerateur);
        assertEquals(2, controlleurSalle.getLargeurSalle().denominateur);
        
        assertEquals(94, controlleurSalle.getLongueurSalle().partieEntiere);
        assertEquals(8, controlleurSalle.getLongueurSalle().numerateur);
        assertEquals(8, controlleurSalle.getLongueurSalle().denominateur);
        
        assertEquals(18, controlleurSalle.getHauteurMurs().partieEntiere);
        assertEquals(1, controlleurSalle.getHauteurMurs().numerateur);
        assertEquals(1, controlleurSalle.getHauteurMurs().denominateur);
        
        // 3.1 Premier UNDO devrait remette la hauteur des murs au default.
        controlleurSalle.undo();
        assertEquals(2, controlleurSalle.positionSalle);
        
        assertEquals(96,controlleurSalle.getHauteurMurs().partieEntiere);
        assertEquals(0,controlleurSalle.getHauteurMurs().numerateur);
        assertEquals(1,controlleurSalle.getHauteurMurs().denominateur);
        
        // 3.2 Deuxieme UNDO devrait remettre la longueur de la salle a default.
        controlleurSalle.undo();
        assertEquals(1, controlleurSalle.positionSalle);
        
        assertEquals(140,controlleurSalle.getLongueurSalle().partieEntiere);
        assertEquals(0,controlleurSalle.getLongueurSalle().numerateur);
        assertEquals(1,controlleurSalle.getLongueurSalle().denominateur);
        
        // 3.3 Trpoisieme UNDO devrait remettre la largeure de la salle a default.
        controlleurSalle.undo();
        assertEquals(0, controlleurSalle.positionSalle);
        
        assertEquals(140,controlleurSalle.getLargeurSalle().partieEntiere);
        assertEquals(0,controlleurSalle.getLargeurSalle().numerateur);
        assertEquals(1,controlleurSalle.getLargeurSalle().denominateur);
        
        // 3.4 Les undo supplementaires devrait laisser la position a 0.
        controlleurSalle.undo();
        controlleurSalle.undo();
        assertEquals(0, controlleurSalle.positionSalle);
        
        //4.1 Premier REDO devrait re-changer la largeure de la salle.
        controlleurSalle.redo();
        assertEquals(1, controlleurSalle.positionSalle);
        
        assertEquals(90, controlleurSalle.getLargeurSalle().partieEntiere);
        assertEquals(20, controlleurSalle.getLargeurSalle().numerateur);
        assertEquals(2, controlleurSalle.getLargeurSalle().denominateur);
        
         //4.2 Deuxieme REDO devrait re-changer la longueur de la salle.
        controlleurSalle.redo();
        assertEquals(2, controlleurSalle.positionSalle);
        
        assertEquals(94, controlleurSalle.getLongueurSalle().partieEntiere);
        assertEquals(8, controlleurSalle.getLongueurSalle().numerateur);
        assertEquals(8, controlleurSalle.getLongueurSalle().denominateur);
        
        //4.3 Troisieme REDO devrait re-changer la hauteur de la salle.
        controlleurSalle.redo();
        assertEquals(3, controlleurSalle.positionSalle);
        
        assertEquals(18, controlleurSalle.getHauteurMurs().partieEntiere);
        assertEquals(1, controlleurSalle.getHauteurMurs().numerateur);
        assertEquals(1, controlleurSalle.getHauteurMurs().denominateur);
        
        // 4.4 Les autres redo devrait laisser la position a la fin de la liste
        controlleurSalle.redo();
        controlleurSalle.redo();
        assertEquals(3, controlleurSalle.positionSalle);
    }
    
    @Test
    public void testerAddSeparateur() {
        // 1. Ajouter un separateur dans le vide devrait pas creer une copie de la salle.
        controlleurSalle.addSeparateur(4000, 4000);
        controlleurSalle.addSeparateur(200, 2000);

        assertEquals(0, controlleurSalle.positionSalle);
        assertEquals(1, controlleurSalle.listeSalle.size());
        
        // 2. Ajouter un separateur dans le cote nord un coin de la salle doit creer une copie de la salle.
        controlleurSalle.addSeparateur(13, 0.67);
        
        assertEquals(1, controlleurSalle.positionSalle);
        assertEquals(2, controlleurSalle.listeSalle.size());
    }
    
    @Test
    public void testSetHauteurGrille() {
        // 1. Valeure par default.
        assertEquals(20, controlleurSalle.getHauteurGrille().partieEntiere);
        assertEquals(0, controlleurSalle.getHauteurGrille().numerateur);
        assertEquals(1, controlleurSalle.getHauteurGrille().denominateur);
        
        // 2. null ne va pas modifier les valeurs par default.
        controlleurSalle.setHauteurGrille(null);
        
        assertEquals(20, controlleurSalle.getHauteurGrille().partieEntiere);
        assertEquals(0, controlleurSalle.getHauteurGrille().numerateur);
        assertEquals(1, controlleurSalle.getHauteurGrille().denominateur);
        
        // 3. Fraction illegale ne modifie pas la largeure de la grille
        controlleurSalle.setHauteurGrille(new FractionImperialeDTO(-10, 0, 1));
        
        assertEquals(20, controlleurSalle.getHauteurGrille().partieEntiere);
        assertEquals(0, controlleurSalle.getHauteurGrille().numerateur);
        assertEquals(1, controlleurSalle.getHauteurGrille().denominateur);
        
        // 4. Bonne fractionimperiale va modifier la valeure de la variable.
        controlleurSalle.setHauteurGrille(new FractionImperialeDTO(10, 5, 1));
        
        assertEquals(10, controlleurSalle.getHauteurGrille().partieEntiere);
        assertEquals(5, controlleurSalle.getHauteurGrille().numerateur);
        assertEquals(1, controlleurSalle.getHauteurGrille().denominateur);
    }
    
        @Test
    public void testSetLargeureGrille() {
        // 1. Valeure par default.
        assertEquals(20, controlleurSalle.getLargeurGrille().partieEntiere);
        assertEquals(0, controlleurSalle.getLargeurGrille().numerateur);
        assertEquals(1, controlleurSalle.getLargeurGrille().denominateur);
        
        // 2. null ne va pas modifier les valeurs par default.
        controlleurSalle.setLargeureGrille(null);
        
        assertEquals(20, controlleurSalle.getLargeurGrille().partieEntiere);
        assertEquals(0, controlleurSalle.getLargeurGrille().numerateur);
        assertEquals(1, controlleurSalle.getLargeurGrille().denominateur);
        
        // 3. Fraction illegale ne modifie pas la largeure de la grille
        controlleurSalle.setLargeureGrille(new FractionImperialeDTO(-10, 0, 1));
        
        assertEquals(20, controlleurSalle.getLargeurGrille().partieEntiere);
        assertEquals(0, controlleurSalle.getLargeurGrille().numerateur);
        assertEquals(1, controlleurSalle.getLargeurGrille().denominateur);
        
        // 4. Bonne fractionimperiale va modifier la valeure de la variable.
        controlleurSalle.setLargeureGrille(new FractionImperialeDTO(10, 5, 1));
        
        assertEquals(10, controlleurSalle.getLargeurGrille().partieEntiere);
        assertEquals(5, controlleurSalle.getLargeurGrille().numerateur);
        assertEquals(1, controlleurSalle.getLargeurGrille().denominateur);
    }
 }
