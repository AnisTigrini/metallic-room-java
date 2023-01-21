package ca.ulaval.glo2004.domaine.DTO;

import ca.ulaval.glo2004.domaine.DTO.accessoires.AccessoireDTO;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import java.util.ArrayList;

public class CoteDTO {
    public Orientation orientation;
    
    public ArrayList<MurDTO> murs;
    
    public ArrayList<SeparateurDTO> separateurs;
    
    public ArrayList<AccessoireDTO> accessoires ;
    
}
