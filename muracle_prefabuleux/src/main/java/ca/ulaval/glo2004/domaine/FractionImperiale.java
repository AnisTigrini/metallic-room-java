package ca.ulaval.glo2004.domaine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FractionImperiale implements java.io.Serializable, Cloneable, Comparable<FractionImperiale> {
    private int partieEntiere;
    private int numerateur;
    private int denominateur;
    
    public FractionImperiale(){
        this.partieEntiere = 0;
        this.numerateur = 0;
        this.denominateur = 1;
    }
    
    public FractionImperiale(int partieEntiere, int numerateur, int denominateur){
        this.setPartieEntiere(partieEntiere);
        this.setNumerateur(numerateur);
        this.setDenominateur(denominateur);
    }
    
    public void setFraction(int partieEntiere, int numerateur, int denominateur){
        this.setPartieEntiere(partieEntiere);
        this.setNumerateur(numerateur);
        this.setDenominateur(denominateur);
    }
    
    public int getPartieEntiere(){
        return this.partieEntiere;
    }
    
    public void setPartieEntiere(int entier) throws IllegalArgumentException {
        if (entier < 0) {
            throw new IllegalArgumentException("On ne peut pas entrer un nombre négatif.");
        }
        
        this.partieEntiere = entier;
    }
    
    public int getNumerateur(){
        return this.numerateur;
    }
    
    public void setNumerateur(int entier) throws IllegalArgumentException {
        if (entier < 0) {
            throw new IllegalArgumentException("On ne peut pas entrer un nombre négatif.");
        }
        
        this.numerateur = entier;
    }
    
    public int getDenominateur(){
        return this.denominateur;
    }
    
    public void setDenominateur(int entier)  throws IllegalArgumentException {
        List<Integer> denominateurImperial = Arrays.asList(1,2,4,8,16,32,64,128,256,512,1000);
        
        if (!denominateurImperial.contains(entier)){
            throw new IllegalArgumentException("Ce denominateur n'est pas celui d'une fraction imperiale.");
        }
        this.denominateur = entier;
    }
    
    public ArrayList<FractionImperiale> mettreAuDenominateurCommun(FractionImperiale fraction1, FractionImperiale fraction2){
        while (fraction1.denominateur != fraction2.denominateur){
            if (fraction1.denominateur > fraction2.denominateur){
                fraction2.setDenominateur(fraction2.denominateur*2);
                fraction2.setNumerateur(fraction2.numerateur*2);
            }
            else if (fraction1.denominateur < fraction2.denominateur){
                fraction1.setDenominateur(fraction1.denominateur*2);
                fraction1.setNumerateur(fraction1.numerateur*2);
            }
        }
        ArrayList<FractionImperiale> fractions = new ArrayList<>();
        fractions.add(fraction1);
        fractions.add(fraction2);
        return fractions;
    }
    
    public FractionImperiale simplifierFraction(){
        while (numerateur >= denominateur){
            setNumerateur(numerateur - denominateur);
            setPartieEntiere(partieEntiere + 1);
        }
        if (numerateur == 0){
            setDenominateur(1);
        }
        else{
            while ( ((denominateur % numerateur) == 0) && (numerateur != 1) ){
                setNumerateur(numerateur/2);
                setDenominateur(denominateur/2);
            }
        }
        return this;
    }
    
    public FractionImperiale additionnerFraction(FractionImperiale fraction){
        FractionImperiale instanceCopie = new FractionImperiale(this.getPartieEntiere(),this.getNumerateur(), this.getDenominateur());
        ArrayList<FractionImperiale> fractions = mettreAuDenominateurCommun(instanceCopie, fraction);
        FractionImperiale sommeFraction = new FractionImperiale();
        sommeFraction.setPartieEntiere(fractions.get(0).partieEntiere + fractions.get(1).partieEntiere);
        sommeFraction.setNumerateur(fractions.get(0).numerateur + fractions.get(1).numerateur);
        sommeFraction.setDenominateur(fractions.get(0).denominateur);
        sommeFraction.simplifierFraction();
        return sommeFraction;
    }
    
    public FractionImperiale soustraireFraction(FractionImperiale fraction){
        FractionImperiale instanceCopie = new FractionImperiale(this.getPartieEntiere(),this.getNumerateur(), this.getDenominateur());
        ArrayList<FractionImperiale> fractions = mettreAuDenominateurCommun(instanceCopie, fraction);
        FractionImperiale differenceFraction = new FractionImperiale();
        differenceFraction.setPartieEntiere(fractions.get(0).partieEntiere - fractions.get(1).partieEntiere > 0 ? fractions.get(0).partieEntiere - fractions.get(1).partieEntiere : 0);
        differenceFraction.setNumerateur(fractions.get(0).numerateur - fractions.get(1).numerateur > 0 ? fractions.get(0).numerateur - fractions.get(1).numerateur : 0);
        differenceFraction.setDenominateur(fractions.get(0).denominateur);
        differenceFraction.simplifierFraction();
        if (differenceFraction.getPartieEntiere() < 0 || differenceFraction.getNumerateur() < 0) {
            differenceFraction.setPartieEntiere(0);
            differenceFraction.setNumerateur(0);
        }
        return differenceFraction;
    }
    
    public void multiplierFractionPar(FractionImperiale fraction){
        setNumerateur(numerateur + partieEntiere*denominateur);
        setPartieEntiere(0);
        fraction.setNumerateur(fraction.numerateur + fraction.partieEntiere*fraction.denominateur);
        setNumerateur(numerateur*fraction.numerateur);
        setDenominateur(denominateur*fraction.denominateur);
        simplifierFraction();
    }
    
    public FractionImperiale multiplierFraction(FractionImperiale fraction2){
        FractionImperiale produitFraction = new FractionImperiale();
        FractionImperiale fractionSansEntier1 = new FractionImperiale();
        FractionImperiale fractionSansEntier2 = new FractionImperiale();
        
        fractionSansEntier1.setNumerateur(numerateur + partieEntiere*denominateur);
        fractionSansEntier1.setDenominateur(getDenominateur());
        
        fractionSansEntier2.setNumerateur(fraction2.getNumerateur() + fraction2.getPartieEntiere()*fraction2.getDenominateur());
        fractionSansEntier2.setDenominateur(fraction2.getDenominateur());
        
        produitFraction.setNumerateur(fractionSansEntier1.getNumerateur() * fractionSansEntier2.getNumerateur());
        produitFraction.setDenominateur(fractionSansEntier1.getDenominateur() * fractionSansEntier2.getDenominateur());
        produitFraction.simplifierFraction();
        return produitFraction;
    }
    
    public double toDouble(){
        double result = 0.0d;
        result += partieEntiere;
        result += ((double)numerateur/denominateur);
        return result;
    }
    
    public static FractionImperiale doubleToFraction(double dimension){
        FractionImperiale fraction = new FractionImperiale(0,0,1);
        if (dimension >= 0){
            fraction.setPartieEntiere((int)dimension);
            dimension = dimension - (int)dimension;
        }
        if (dimension >= 0.5){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,2));
            dimension = dimension - 0.5;
        }
        if (dimension >= 0.25){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,4));
            dimension = dimension - 0.25;
        }
        if (dimension >= 0.125){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,8));
            dimension = dimension - 0.125;
        }
        if (dimension >= 0.0625){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,16));
            dimension = dimension - 0.0625;
        }
        if (dimension >= 0.03125){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,32));
            dimension = dimension - 0.03125;
        }
        if (dimension >= 0.015625){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,64));
            dimension = dimension - 0.015625;
        }
        if (dimension >= 0.0078125){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,128));
            dimension = dimension - 0.0078125;
        }
        if (dimension >= 0.00390625){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,256));
            dimension = dimension - 0.00390625;
        }
        if (dimension >= 0.001953125){
            fraction = fraction.additionnerFraction(new FractionImperiale(0,1,512));
        }
        return fraction;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    // return 0 : Tous les attributs sont les memes.
    // return -1 : Un ou plusieurs attributs de différents.
    @Override
    public int compareTo(FractionImperiale f) {
        if (f.partieEntiere == this.partieEntiere  && f.numerateur == this.numerateur && f.denominateur == this.denominateur) {
            return 0;
        }
        return -1;
    } 
}



