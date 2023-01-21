package ca.ulaval.glo2004.domaine;

public class Separateur implements Comparable<Separateur>, java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    protected FractionImperiale position = new FractionImperiale(36,0,1);
    
    private boolean selectionStatus = false;

    // ------------- Constructeurs ------------- //
    public Separateur() {}
    
    public Separateur(FractionImperiale position, boolean selectionStatus){
        this.position = position;
        this.selectionStatus = selectionStatus;
    }
    
    // ------------- Setters ------------- //
    public void setPosition(FractionImperiale position){
        this.position = position;
    }
    
    public void setSelectionStatus(boolean status){
        this.selectionStatus = status;
    }
    
    // ------------- Getters ------------- //
    public FractionImperiale getPosition(){
        return this.position;
    }
    
    public boolean getSelectionStatus(){
        return this.selectionStatus;
    }

    // ------------- Utilitaires ------------- //
    // Comparer les position deux separateurs.
    // -1 ==> Le separateur2 est supérieur à cet instance de séparateur.
    // 0 ==> Les deux separateurs sont egaux.
    // 1 ==> Cet instance de séparateur est supérieure au séparateur2.
    @Override
    public int compareTo(Separateur spearateur2) {
        double distanceEntreSeparateurs = Math.abs(position.toDouble() - spearateur2.getPosition().toDouble());
        if (distanceEntreSeparateurs <= 0.01) {
            return 0;
        } else if (position.toDouble() - spearateur2.getPosition().toDouble() > 0) {
            return 1;
        }
        
        return -1;
    }
    
    @Override
    public Separateur clone() throws CloneNotSupportedException {
        Separateur separateurClone = (Separateur) super.clone();
        
        separateurClone.setPosition((FractionImperiale) this.position.clone());
        
        return separateurClone;
    }
}
