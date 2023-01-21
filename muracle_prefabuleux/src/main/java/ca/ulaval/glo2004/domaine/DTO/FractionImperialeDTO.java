package ca.ulaval.glo2004.domaine.DTO;

// 1. Les ints sont passés par référence.
public class FractionImperialeDTO {
    public int partieEntiere;
    public int numerateur;
    public int denominateur;
    
    public FractionImperialeDTO(int partieEntiere, int numerateur, int denominateur) {
        this.partieEntiere = partieEntiere;
        this.numerateur = numerateur;
        this.denominateur = denominateur;
    }
    
    public double toDouble(){
        double result = 0.0d;
        result += partieEntiere;
        result += ((double)numerateur/denominateur);
        return result;
    }
}
