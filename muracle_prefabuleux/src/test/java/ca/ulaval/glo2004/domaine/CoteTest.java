package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.Orientation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;

public class CoteTest {
    private static Cote cote;
    
    @Before
    public void setup() {
        cote = new Cote();
    }
    
    // 1. Tester le constructeur par défault de cote.
    @Test
    public void constructeurDefaultCote() {
        Cote cote = new Cote();
                
        assertEquals(Orientation.UNKNOWN, cote.getOrientation());
        assertEquals(2, cote.getMurs().size());
        assertEquals(1,cote.getSeparateurs().size());
    }
    
    // 2. Valider le contructeur avec orientation comme attribut.
    @Test
    public void validerContstructeurAvecOrientation() {
       Cote cote2 = new Cote(Orientation.EST);
       Cote cote3 = new Cote(Orientation.NORD);
       Cote cote4 = new Cote(Orientation.OUEST);
       Cote cote5 = new Cote(Orientation.SUD);
       
       assertEquals(Orientation.EST, cote2.getOrientation());
       assertEquals(2, cote2.getMurs().size());
       assertEquals(1,cote2.getSeparateurs().size());       
       
       assertEquals(Orientation.NORD, cote3.getOrientation());
       assertEquals(2, cote3.getMurs().size());
       assertEquals(1,cote3.getSeparateurs().size());
       
       assertEquals(Orientation.OUEST, cote4.getOrientation());
       assertEquals(2, cote4.getMurs().size());
       assertEquals(1,cote4.getSeparateurs().size());  

       assertEquals(Orientation.SUD, cote5.getOrientation());
       assertEquals(2, cote5.getMurs().size());
       assertEquals(1,cote5.getSeparateurs().size());  
    }
    
    // 3. Valier le setter d'orientation
    @Test
    public void validerSetOrientation() {
        assertEquals(Orientation.UNKNOWN, cote.getOrientation());
        
        cote.setOrientation(Orientation.NORD);
        assertEquals(Orientation.NORD, cote.getOrientation());
        
        cote.setOrientation(Orientation.SUD);        
        assertEquals(Orientation.SUD, cote.getOrientation());
        
        cote.setOrientation(Orientation.EST);        
        assertEquals(Orientation.EST, cote.getOrientation());
        
        cote.setOrientation(Orientation.OUEST);        
        assertEquals(Orientation.OUEST, cote.getOrientation());
    }
    
    @Test
    public void validerCoteClone() {
        try {
            Cote coteClone = (Cote) cote.clone();
        
            assertNotEquals(cote, coteClone);
            assertEquals(cote.getMurs().size(), coteClone.getMurs().size());
            assertEquals(cote.getSeparateurs().size(), coteClone.getSeparateurs().size());
            assertEquals(cote.getAccessoires().size(), coteClone.getAccessoires().size());
            
            for (int i = 0; i < cote.getMurs().size(); i++) {
                assertNotEquals(cote.getMurs().get(i), coteClone.getMurs().get(i));
            }
            
            for (int i = 0; i < cote.getSeparateurs().size(); i++) {
                assertNotEquals(cote.getSeparateurs().get(i), coteClone.getSeparateurs().get(i));
            }
            
            for (int i = 0; i < cote.getAccessoires().size(); i++) {
                assertNotEquals(cote.getAccessoires().get(i), coteClone.getAccessoires().get(i));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
