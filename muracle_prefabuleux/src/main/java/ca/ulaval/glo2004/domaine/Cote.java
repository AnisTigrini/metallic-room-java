package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.accessoires.Accessoire;
import ca.ulaval.glo2004.domaine.accessoires.AccessoireFenetre;
import ca.ulaval.glo2004.domaine.accessoires.AccessoirePorte;
import ca.ulaval.glo2004.domaine.accessoires.AccessoirePriseElectrique;
import ca.ulaval.glo2004.domaine.accessoires.AccessoireRetourAir;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import ca.ulaval.glo2004.domaine.enums.Sens;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Cote implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    protected Orientation orientation = Orientation.UNKNOWN;
    
    protected ArrayList<Mur> murs = new ArrayList<Mur>(Arrays.asList(new Mur(0), new Mur(1)));
    
    protected ArrayList<Separateur> separateurs = new ArrayList<Separateur>(Arrays.asList(new Separateur()));
    
    protected ArrayList<Accessoire> accessoires = new ArrayList<Accessoire>();
    
    // ------------- Constructeurs ------------- //
    public Cote(){
    }
    
    public Cote(Orientation orientation) {
        this.orientation = orientation;
    }
    
    public Cote(Orientation orientation, ArrayList<Separateur> separateurs, ArrayList<Accessoire> accessoires){
        this.orientation = orientation;
        this.separateurs = separateurs;
        this.accessoires = accessoires;
        this.genererMurs();
    }
    
    // ------------- Getters ------------- //
    public Orientation getOrientation(){
        return this.orientation;
    }
    
    public ArrayList<Mur> getMurs(){
        return this.murs;
    }
    
    public ArrayList<Separateur> getSeparateurs(){
        return this.separateurs;
    }
    
    public ArrayList<Accessoire> getAccessoires(){
        return this.accessoires;
    }

    
    // ------------- Setters ------------- //
    public void setOrientation(Orientation orientation){
        this.orientation = orientation;
    }
    
    public void setMurs(ArrayList<Mur> murs){
        this.murs = murs;
    }
    
    public void setSeparateurs(ArrayList<Separateur> separateurs){
        this.separateurs = separateurs;
    }
    
    public void setAccessoires(ArrayList<Accessoire> accessoires){
        this.accessoires = accessoires;
    }

    public int addSeparateur(double coordonnee){
        int added = 0;
        //if (separateurs.isEmpty()){
            Separateur newSeparateur = new Separateur(FractionImperiale.doubleToFraction(coordonnee), false);
            separateurs.add(newSeparateur);
            added++;
        //}
        sortSeparateur();
        genererMurs();
        return added;
    }
    
    
    public int addFenetre(double longueurCote, Sens sens, double pointx, double pointy) {
        int initial = this.countAccessoires();
        
        if (((orientation == Orientation.NORD || orientation == Orientation.EST) && sens == Sens.EXTERIEUR) ||
           ((orientation == Orientation.SUD || orientation == Orientation.OUEST) && sens == Sens.INTERIEUR)){
            int largeurFenetre = 24;
            pointx = longueurCote - pointx - largeurFenetre;
            this.accessoires.add(new AccessoireFenetre(pointx, pointy, new FractionImperiale(largeurFenetre,0,1)));
        }
        else {
            this.accessoires.add(new AccessoireFenetre(pointx, pointy));
        }
        
        int newCount = this.countAccessoires();
        int added = newCount - initial;
        return added;
    }
    
    public int addPorte(double longueurCote, Sens sens, double pointx, double pointy, FractionImperiale hauteurDesMurs) {
        int initial = this.countAccessoires();
        
        if (((orientation == Orientation.NORD || orientation == Orientation.EST) && sens == Sens.EXTERIEUR) ||
           ((orientation == Orientation.SUD || orientation == Orientation.OUEST) && sens == Sens.INTERIEUR)){
            int largeurPorte = 24;
            pointx = longueurCote - pointx - largeurPorte;
            this.accessoires.add(new AccessoirePorte(pointx, pointy, hauteurDesMurs));
        }
        else{
            this.accessoires.add(new AccessoirePorte(pointx, pointy, hauteurDesMurs));
        }
        
        int newCount = this.countAccessoires();
        int added = newCount - initial;
        return added;
    }
    
    public int addPriseElectrique(double longueurCote, double pointx, double pointy) {
        int initial = this.countAccessoires();
        
        if (orientation == Orientation.SUD || orientation == Orientation.OUEST){
            int largeurPrise = 2;
            pointx = longueurCote - pointx - largeurPrise;
            this.accessoires.add(new AccessoirePriseElectrique(pointx, pointy, new FractionImperiale(largeurPrise,0,1)));
        }
        else{
            this.accessoires.add(new AccessoirePriseElectrique(pointx, pointy));
        }
        
        int newCount = this.countAccessoires();
        int added = newCount - initial;
        return added;
    }
    
    public int addRetourAir(double longueurCote, double pointx, double pointy) {
        int initial = this.countAccessoires();
        if (orientation == Orientation.SUD || orientation == Orientation.OUEST){
            double positionActuelle = longueurCote;
            int indexMur = 0;
            for (Separateur separateur:separateurs){
                if (pointx < positionActuelle && pointx > (longueurCote - separateur.getPosition().toDouble())){
                    if (murs.get(indexMur).retourAir == null){
                        this.murs.get(indexMur).setRetourAir(new AccessoireRetourAir(indexMur));
                    }
                }
                positionActuelle = longueurCote - separateur.getPosition().toDouble();
                indexMur += 1;
            }
            if (pointx < positionActuelle && pointx > 0){
                if (murs.get(indexMur).retourAir == null){
                                this.murs.get(indexMur).setRetourAir(new AccessoireRetourAir(indexMur));
                }
            }
        }
        else{
            double positionActuelle = 0.0d;
            int indexMur = 0;
            System.out.println(separateurs);
            for (Separateur separateur : separateurs){
                if (pointx > positionActuelle && pointx < separateur.getPosition().toDouble()){
                    System.out.println(indexMur);
                    if (murs.get(indexMur).retourAir == null){
                        this.murs.get(indexMur).setRetourAir(new AccessoireRetourAir(indexMur));
                    }
                }
                positionActuelle = separateur.getPosition().toDouble();
                indexMur += 1;
            }
            if (pointx > positionActuelle && pointx < longueurCote){
                if (murs.get(indexMur).retourAir == null){
                    this.murs.get(indexMur).setRetourAir(new AccessoireRetourAir(indexMur));
                }
            }
        }
        
        
        
        int newCount = this.countAccessoires();
        int added = newCount - initial;
        System.out.println(accessoires);
        return added;
    }
    
    
    // ------------- Delete ------------- //
    public int deleteSelectedSeparateur(){
        int initial = this.countSeparateurs();
        Iterator<Separateur> itr = this.separateurs.iterator();
        while (itr.hasNext()){
            Separateur separateur = itr.next();
            if (separateur.getSelectionStatus()){
                itr.remove();
            }
        }
        int newCount = this.countSeparateurs();
        int removed = initial - newCount != 0 ? 1 : 0;
        genererMurs();
        return removed;
    }
    
    public int deleteSelectedAccessoire(){
        int initial = this.countAccessoires();
        Iterator<Accessoire> itr = this.accessoires.iterator();
        while (itr.hasNext()){
            Accessoire accessoire = itr.next();
            if (accessoire.getSelectionStatus()){
                itr.remove();
            }
        }
        for (Mur mur : murs){
            if (mur.retourAir != null){
                if(mur.retourAir.getSelectionStatus() == true){
                    mur.setRetourAir(null);
                }
            }
        }
        int newCount = this.countAccessoires();
        int removed = initial - newCount != 0 ? 1 : 0;
        return removed;
    }
    
    public int deleteSelectedFenetre(){
        int initial = this.countAccessoires();
        Iterator<Accessoire> itr = this.accessoires.iterator();
        while (itr.hasNext()){
            Accessoire accessoire = itr.next();
            if (accessoire instanceof AccessoireFenetre) {
                if (accessoire.getSelectionStatus()){
                    itr.remove();
                }
            }
        }
        int newCount = this.countAccessoires();
        int removed = initial - newCount != 0 ? 1 : 0;
        return removed;
    }
    
    public int deleteSelectedPorte(){
        int initial = this.countAccessoires();
        Iterator<Accessoire> itr = this.accessoires.iterator();
        while (itr.hasNext()){
            Accessoire accessoire = itr.next();
            if (accessoire instanceof AccessoirePorte) {
                if (accessoire.getSelectionStatus()){
                    itr.remove();
                }
            }
        }
        int newCount = this.countAccessoires();
        int removed = initial - newCount != 0 ? 1 : 0;
        return removed;
    }
    
    public int deleteSelectedPriseElectrique(){
        int initial = this.countAccessoires();
        Iterator<Accessoire> itr = this.accessoires.iterator();
        while (itr.hasNext()){
            Accessoire accessoire = itr.next();
            if (accessoire instanceof AccessoirePriseElectrique) {
                if (accessoire.getSelectionStatus()){
                    itr.remove();
                }
            }
        }
        int newCount = this.countAccessoires();
        int removed = initial - newCount != 0 ? 1 : 0;
        return removed;
    }
    
    public int deleteSelectedRetourAir(){
        // ICI ON DOIT EGALEMENT SUPPRIMER LE TROU DANS LE PLIS
        int initial = this.countAccessoires();
        Iterator<Accessoire> itr = this.accessoires.iterator();
        while (itr.hasNext()){
            Accessoire accessoire = itr.next();
            if (accessoire instanceof AccessoireRetourAir) {
                if (accessoire.getSelectionStatus()){
                    itr.remove();
                }
            }
        }
        int newCount = this.countAccessoires();
        int removed = initial - newCount != 0 ? 1 : 0;
        return removed;
    }
    
    public FractionImperiale getSelectedSeparateurPosition(){
        for (Separateur separateur : separateurs){
            if (separateur.getSelectionStatus()){
                return separateur.getPosition();
            }
        }
        return null;
    }
    
    public FractionImperiale getSelectedAccessoireLargeur(){
        for (Accessoire accessoire : accessoires){
            if (accessoire.getSelectionStatus()){
                return accessoire.getLargeur();
            }
        }
        return null;
    }
    
    public FractionImperiale getSelectedAccessoireHauteur(){
        for (Accessoire accessoire : accessoires){
            if (accessoire.getSelectionStatus()){
                return accessoire.getHauteur();
            }
        }
        return null;
    }
    
    public FractionImperiale getSelectedAccessoireCoordonneeX(){
        for (Accessoire accessoire : accessoires){
            if (accessoire.getSelectionStatus()){
                return accessoire.getCoordonneeX();
            }
        }
        return null;
    }
    
    public FractionImperiale getSelectedAccessoireCoordonneeY(){
        for (Accessoire accessoire : accessoires){
            if (accessoire.getSelectionStatus()){
                return accessoire.getCoordonneeY();
            }
        }
        return null;
    }
    
    public FractionImperiale getSelectedRetourAirLargeur(){
        for(Mur mur: murs){
            if (mur.retourAir != null){
                if (mur.retourAir.getSelectionStatus() == true){
                    System.out.println(mur.retourAir.getLargeur().getPartieEntiere());
                    return mur.retourAir.getLargeur();
                }
            }
        }
        return null;
    }
    
    public int getSelectedMurPosition(){
        for(Mur mur: murs){
            if(mur.getSelectionStatus()){
                return mur.getPosition();
            }
        }
        return -1;
    }

    // ------------- Select ------------- //
    public int selectVuePlan(double point, int typeSelected){
        for (Separateur separateur : separateurs){
            if (point - separateur.getPosition().toDouble() > -1 && point - separateur.getPosition().toDouble() < 1){
                separateur.setSelectionStatus(true);
                return typeSelected;
            }
        }
        return 0;
    }
    
    public int selectVueElevation(double pointx, double pointy, double dimension, Sens sens){
        for (Accessoire acc : accessoires){
            if (((orientation == Orientation.NORD || orientation == Orientation.EST) && sens == Sens.EXTERIEUR) ||
                ((orientation == Orientation.SUD || orientation == Orientation.OUEST) && sens == Sens.INTERIEUR)){
                if (pointx > (dimension - acc.getCoordonneeX().toDouble() - acc.getLargeur().toDouble()) &&
                    pointx < (dimension - acc.getCoordonneeX().toDouble()) &&
                    pointy > acc.getCoordonneeY().toDouble() &&
                    pointy < acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble()){
                    acc.setSelectionStatus(true);
                    return 4;
                }
            }
            else{
                if (pointx > acc.getCoordonneeX().toDouble() &&
                    pointx < acc.getCoordonneeX().toDouble() + acc.getLargeur().toDouble() &&
                    pointy > acc.getCoordonneeY().toDouble() &&
                    pointy < acc.getCoordonneeY().toDouble() + acc.getHauteur().toDouble()){
                    acc.setSelectionStatus(true);
                    return 4;
                }
            }
                
        }
        if (pointy > 0.0 && pointy < getHauteurMurs().toDouble()){
            if (((orientation == Orientation.NORD || orientation == Orientation.EST) && sens == Sens.EXTERIEUR) ||
                ((orientation == Orientation.SUD || orientation == Orientation.OUEST) && sens == Sens.INTERIEUR)){
                double positionActuelle = dimension;
                    int indexMur = 0;
                    for (Separateur separateur:separateurs){
                        if (pointx < positionActuelle && pointx > (dimension - separateur.getPosition().toDouble())){
                            
                            if (murs.get(indexMur).retourAir != null){
                                
                                if (pointx < dimension - murs.get(indexMur).retourAir.getCoordonneeX().toDouble() && 
                                    pointx > dimension - murs.get(indexMur).retourAir.getCoordonneeX().toDouble() - murs.get(indexMur).retourAir.getLargeur().toDouble() &&
                                    pointy > murs.get(indexMur).retourAir.getCoordonneeY().toDouble() &&
                                    pointy < murs.get(indexMur).retourAir.getCoordonneeY().toDouble() + murs.get(indexMur).retourAir.getHauteur().toDouble()) {
                                    murs.get(indexMur).retourAir.setSelectionStatus(true);
                                    return 5;
                                }
                            }
                            murs.get(indexMur).setSelectionStatus(true);
                            return 3;
                        }
                        
                        positionActuelle = dimension - separateur.getPosition().toDouble();
                        indexMur += 1;
                    }
                    if (pointx < positionActuelle && pointx > 0){
                        if (murs.get(indexMur).retourAir != null){
                                if (pointx < dimension - murs.get(indexMur).retourAir.getCoordonneeX().toDouble() && 
                                    pointx > dimension - murs.get(indexMur).retourAir.getCoordonneeX().toDouble() - murs.get(indexMur).retourAir.getLargeur().toDouble() &&
                                    pointy > murs.get(indexMur).retourAir.getCoordonneeY().toDouble() &&
                                    pointy < murs.get(indexMur).retourAir.getCoordonneeY().toDouble() + murs.get(indexMur).retourAir.getHauteur().toDouble()) {
                                    murs.get(indexMur).retourAir.setSelectionStatus(true);
                                    return 5;
                                }
                            }
                            murs.get(indexMur).setSelectionStatus(true);
                            return 3;
                    }
            }
            else{
                double positionActuelle = 0.0d;
                    int indexMur = 0;
                    for (Separateur separateur : separateurs){
                        if (pointx > positionActuelle && pointx < separateur.getPosition().toDouble()){
                            if (murs.get(indexMur).retourAir != null){
                                if (pointx > murs.get(indexMur).retourAir.getCoordonneeX().toDouble() && 
                                    pointx < murs.get(indexMur).retourAir.getCoordonneeX().toDouble() + murs.get(indexMur).retourAir.getLargeur().toDouble() &&
                                    pointy > murs.get(indexMur).retourAir.getCoordonneeY().toDouble() &&
                                    pointy < murs.get(indexMur).retourAir.getCoordonneeY().toDouble() + murs.get(indexMur).retourAir.getHauteur().toDouble()) {
                                    murs.get(indexMur).retourAir.setSelectionStatus(true);
                                    return 5;
                                }
                            }
                            murs.get(indexMur).setSelectionStatus(true);
                            return 3;
                        }
                        positionActuelle = separateur.getPosition().toDouble();
                        indexMur += 1;
                    }
                    if (pointx > positionActuelle && pointx < dimension){
                        if (murs.get(indexMur).retourAir != null){
                            if (pointx > murs.get(indexMur).retourAir.getCoordonneeX().toDouble() && 
                                pointx < murs.get(indexMur).retourAir.getCoordonneeX().toDouble() + murs.get(indexMur).retourAir.getLargeur().toDouble() &&
                                pointy > murs.get(indexMur).retourAir.getCoordonneeY().toDouble() &&
                                pointy < murs.get(indexMur).retourAir.getCoordonneeY().toDouble() + murs.get(indexMur).retourAir.getHauteur().toDouble()) {
                                murs.get(indexMur).retourAir.setSelectionStatus(true);
                                return 5;
                            }
                        }
                        murs.get(indexMur).setSelectionStatus(true);
                        return 3;
                    }
            }
        }
        return 0;
    }
    
    public void updateSelectedSeparateur(FractionImperiale newValue){
        for (Separateur separateur : separateurs){
            if (separateur.getSelectionStatus() == true){
                separateur.setPosition(newValue);
            }
        }
        sortSeparateur();
    }
    
    public void updateSelectedAccessoire(FractionImperiale newCoordX,
                                         FractionImperiale newCoordY,
                                         FractionImperiale newLargeur,
                                         FractionImperiale newHauteur){
        for (Accessoire accessoire : accessoires){
            if (accessoire.getSelectionStatus() == true){
                accessoire.setCoordonneX(newCoordX);
                accessoire.setCoordonneY(newCoordY);
                accessoire.setLargeur(newLargeur);
                accessoire.setHauteur(newHauteur);
            }
        }
    }
    
    public void updateSelectedRetourAirLargeur(FractionImperiale newValue){
        for (Mur mur : murs){
            if (mur.hasRetourAir()){
                if (mur.retourAir.getSelectionStatus() == true){
                    mur.retourAir.setLargeur(newValue);
                }
            }
        }
          
    }

    
    public void unSelect(){
        for (Separateur separateur : separateurs){
            separateur.setSelectionStatus(false);
        }
        for (Mur mur : murs){
            mur.setSelectionStatus(false);
            if (mur.retourAir != null){
                mur.retourAir.setSelectionStatus(false);
            }
        }
        for (Accessoire accessoire : accessoires){
            accessoire.setSelectionStatus(false);
        }
    }
    
    // ------------- Utilitaires ------------- //
    public void genererMurs() {
        ArrayList<Mur> newMurs = new ArrayList<Mur>();
        int indexMur = 0;
        newMurs.add(new Mur(indexMur));
        for (Separateur separateur : separateurs) {
            newMurs.add(new Mur(++indexMur));
        }
        setMurs(newMurs);
        this.setCoordinatesToMurs();
    }
    
    
    public void validerCote() throws IllegalStateException {
        // 1. La liste de murs ne devrait jamais être vide
        if (murs.isEmpty()) {
            throw new IllegalStateException("Il n'y a pas de mur dans le côté.");
        } 

        // 2. S'assurer que le nombre d'éléments dans la liste de mur est cohérent par rapport aux séparateurs.
        if (murs.size() - 1 == separateurs.size()) {
            throw new IllegalStateException("Le nombre de séparateurs et le nombre de murs ne fait pas de sens.");
        }
        
        // 3. S'assurer que les séparateurs sont bien ordonnés.
        for (int i = 0; i < separateurs.size(); i++) {
            if (i > 0) {
                Separateur separateurCourant = separateurs.get(i);
                Separateur separateurPrecedent = separateurs.get(i - 1);
                
                if (separateurCourant.compareTo(separateurPrecedent) != 1) {
                    throw new IllegalStateException("Le séparateur courant ne se trouve pas à une position supérieure du précédent.");
                }
            }
        }
        
        // TODO : On doit aussi s'assurer que la liste des murs est bien ordonné en conséquence.
        for (int i = 0; i < murs.size(); i++) {
            if (i > 0) {
                Mur murCourant = murs.get(i);
                Mur murPrecedent = murs.get(i-1);
            }
        }
        
        // TODO : On doit s'assurer que la position des separateurs est cohérente par rapport aux positions des murs.
    }
    
    public void sortSeparateur(){
        Collections.sort(separateurs, new Comparator<Separateur>(){
            @Override
            public int compare(Separateur s1, Separateur s2){
                return Integer.compare(s1.getPosition().getPartieEntiere(), s2.getPosition().getPartieEntiere());
            }
        });
    }

    public boolean murIsSelected(int index){
        ArrayList<Mur> listeDeMurs = getMurs();
        if (index >= 0 && index < listeDeMurs.size()) {
            return getMurs().get(index).getSelectionStatus();
        }
        return false;
    }
    
    public boolean hasSeparateurs() {
        return this.countSeparateurs() > 0;
    }
    
    public int countSeparateurs() {
        return this.separateurs.size();
    }
    
    public boolean hasAccessoires() {
        return this.countAccessoires() > 0;
    }
    
    public int countAccessoires() {
        return this.accessoires.size();
    }
    
    public int countRetoursAir() {
        int count = 0;
        for (Accessoire accessoire : this.accessoires) {
            if (accessoire instanceof AccessoireRetourAir) {
                count++;
            }
        }
        return count;
    }
    
    public boolean hasRetoursAir() {
        return this.countRetoursAir() > 0;
    }
    public boolean hasFenetre(){
        int count = 0;
        for (Accessoire accessoire : this.accessoires) {
            if (accessoire instanceof AccessoireFenetre) {
                count++;
            }
        }
        
        return count > 0;
    }
    //--------------- Getters et Setters en Cascade --------------//
    public FractionImperiale getLargeurExterieure(){
        FractionImperiale largeur = new FractionImperiale();
        for (Mur mur : this.murs) {
            largeur = largeur.additionnerFraction(mur.getLargeurExterieure());
        }
        return largeur;
    }
    
    public FractionImperiale getLargeurInterieure(){
        FractionImperiale largeur = new FractionImperiale();
        for (Mur mur : this.murs) {
            largeur = largeur.additionnerFraction(mur.getLargeurInterieure());
        }
        return largeur;
    }
    
    public FractionImperiale getHauteurMurs() {
        return this.murs.get(0).panneauExterieur.getHauteur();
    }
    
    public FractionImperiale getEpaisseurDesMurs() {
        return this.murs.get(0).getEpaisseurDesMurs();
    }
    
    public FractionImperiale getLongueurDesPlis() {
        return this.murs.get(0).getLongueurDesPlis();
    }
    
    public FractionImperiale getMargeDesSoudures() {
        return this.murs.get(0).getMargeDesSoudures();
    }
    
    public double getAngleDesSoudures() {
        return this.murs.get(0).getAngleDesSoudures();
    }
    
    public FractionImperiale getMargeDesPlis() {
        return this.murs.get(0).getMargeDesPlis();
    }
    
    public FractionImperiale getHauteurSolDesRetoursAir() {
        return this.murs.get(0).getHauteurSolRetourAir();
    }
    
    public FractionImperiale getHauteurDesRetoursAir() throws IllegalStateException {
        if (this.hasRetoursAir()) {
            for (Accessoire accessoire : this.accessoires) {
                if (accessoire instanceof AccessoireRetourAir) {
                    return accessoire.getHauteur();
                }
            }
        }
        throw new IllegalStateException("Le Côté ne possède pas de fenêtre.");
    }
    
    public FractionImperiale getMargeDesFenetres() throws IllegalStateException {
        if (this.hasFenetre()) {
            for (Accessoire accessoire : this.accessoires) {
                if (accessoire instanceof AccessoireFenetre) {
                    return accessoire.getMarge();
                }
            }
        }
        throw new IllegalStateException("Le Côté ne possède pas de fenêtre.");
    }
    
    public void setEpaisseurDesMurs(FractionImperiale epaisseurMur) {
        for (Mur mur : this.murs){
            mur.setEpaisseurDesMurs(epaisseurMur);
        }
    }
    
    public void setLongueurDesPlis(FractionImperiale longueur) {
        for (Mur mur : this.murs){
            mur.setLongueurDesPlis(longueur);
        }
    }
    
    public void setMargeDesSoudures(FractionImperiale marge) {
        for (Mur mur : this.murs){
            mur.setMargeDesSoudures(marge);
        }
    }
    
    public void setAngleDesSoudures(double angle) {
        for (Mur mur : this.murs){
            mur.setAngleDesSoudures(angle);
        }
    }
    
    public void setMargeDesPlis(FractionImperiale marge) {
        for (Mur mur : this.murs){
            mur.setMargeDesPlis(marge);
        }
    }
    
    public void setHauteurDesRetoursAir(FractionImperiale hauteur) {
        for (Accessoire accessoire : this.accessoires) {
            if (accessoire instanceof AccessoireRetourAir) {
                accessoire.setHauteur(hauteur);
            }
        }
    }
    
    public void setMargeDesFenetres(FractionImperiale margeFenetre){
        for (Accessoire accessoire : this.accessoires) {
            if (accessoire instanceof AccessoireFenetre) {
                accessoire.setMarge(margeFenetre);
            }
        }
    }
    
    public void setHauteurSolDesRetoursAir(FractionImperiale hauteur){
        for (Mur mur : this.murs) {
            mur.setHauteurSolRetourAir(hauteur);
        }
    }
    
    @Override
    public Cote clone() throws CloneNotSupportedException {
        Cote coteClone = (Cote) super.clone();
        
        ArrayList<Mur> mursClone = new ArrayList<Mur>();
        ArrayList<Accessoire> accessoireClone = new ArrayList<Accessoire>();
        ArrayList<Separateur> separateursClone = new ArrayList<Separateur>();
        
        for (int i = 0 ; i < murs.size(); i++) {
            mursClone.add(murs.get(i).clone());
        }
        
        for (int i = 0 ; i < separateurs.size(); i++) {
            separateursClone.add(separateurs.get(i).clone());
        }
        
        for (int i = 0 ; i < accessoires.size(); i++) {
            Accessoire a = accessoires.get(i);

            if (a instanceof AccessoireFenetre) {
                accessoireClone.add((AccessoireFenetre) accessoires.get(i).clone());
            } else if (a instanceof AccessoirePorte) {
                accessoireClone.add((AccessoirePorte) accessoires.get(i).clone());
            } else if (a instanceof AccessoirePriseElectrique) {
                accessoireClone.add((AccessoirePriseElectrique) accessoires.get(i).clone());
            } else if (a instanceof AccessoireRetourAir) {
                accessoireClone.add((AccessoireRetourAir) accessoires.get(i).clone());
            }
        }
        
        coteClone.murs = mursClone;
        coteClone.accessoires = accessoireClone;
        coteClone.separateurs = separateursClone;
                
        return coteClone;
    }
    
    public void setDimensionsMurs(ArrayList<Double> dimensionsMurs, FractionImperiale epaisseurDesMurs) throws IllegalArgumentException {
        if (!(dimensionsMurs instanceof ArrayList)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas un ArrayList valide.");            
        }
        Mur currentMur;
        int currentIndex = 0;
        for (double dimensionMur : dimensionsMurs) {
            FractionImperiale dimensionMurImperiale = FractionImperiale.doubleToFraction(dimensionMur);
            currentMur = this.getMurs().get(currentIndex);
            currentMur.setLargeurExterieure(dimensionMurImperiale);
            if (currentIndex == 0 || currentIndex == (dimensionsMurs.size() - 1)) {
                currentMur.setLargeurInterieure(dimensionMurImperiale.soustraireFraction(epaisseurDesMurs));
            } else {
                currentMur.setLargeurInterieure(dimensionMurImperiale);
            }
            currentIndex += 1;
        }
    }
    
    public void setHauteurDesMurs(FractionImperiale hauteur) {
        for (Mur mur : this.getMurs()) {
            mur.setHauteurDesPanneaux(hauteur);
        }
    }
    
    public void setCoordinatesToMurs() {
        int index = 1;
        this.murs.get(0).setCoordonneeX(new FractionImperiale(0,0,1));
        this.murs.get(0).getPanneauExterieur().setCoordonneeX(new FractionImperiale(0,0,1));
        this.murs.get(0).getPanneauInterieur().setCoordonneeX(new FractionImperiale(0,0,1));
        for (Separateur separateur : this.separateurs) {
            this.murs.get(index).setCoordonneeX(separateur.getPosition());
            this.murs.get(index).getPanneauExterieur().setCoordonneeX(separateur.getPosition());
            this.murs.get(index).getPanneauInterieur().setCoordonneeX(separateur.getPosition());
            index++;
        }
    }
    
    public ArrayList<Panneau> getPanneauxInterieurs() {
        ArrayList<Panneau> panneauInterieure = new ArrayList<Panneau>();
        
        for (int i = 0; i < this.murs.size(); i++) {
            panneauInterieure.add(murs.get(i).panneauInterieur);
        }
        
        return panneauInterieure;
    }
    
    public ArrayList<Panneau> getPanneauxExterieurs() {
        ArrayList<Panneau> panneauInterieure = new ArrayList<Panneau>();
        
        for (int i = 0; i < this.murs.size(); i++) {
            panneauInterieure.add(murs.get(i).panneauExterieur);
        }
        
        return panneauInterieure;
    }
}