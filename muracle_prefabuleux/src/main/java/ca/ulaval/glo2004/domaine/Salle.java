package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.enums.Orientation;
import ca.ulaval.glo2004.domaine.enums.Sens;
import java.util.ArrayList;

public class Salle implements java.io.Serializable, Cloneable {
    
    // ------------- Attributs ------------- //
    protected Cote coteNord = new Cote(Orientation.NORD);
    
    protected Cote coteSud = new Cote(Orientation.SUD);
    
    protected Cote coteOuest = new Cote(Orientation.OUEST);
    
    protected Cote coteEst = new Cote(Orientation.EST);
    
    protected FractionImperiale longueurSalle = new FractionImperiale(140,0,1);
    
    protected FractionImperiale largeurSalle = new FractionImperiale(140,0,1);
    
    protected FractionImperiale epaisseurDesMurs = new FractionImperiale(4,0,1);
    
    protected FractionImperiale longueurDesPlis = new FractionImperiale(95,0,1);
    
    protected FractionImperiale margeDesSoudures = new FractionImperiale(2,0,1);
    
    protected double angleDesSoudures = 45.0;
    
    protected FractionImperiale margeDesPlis = new FractionImperiale(0,1,4);
    
    protected FractionImperiale hauteurDesMurs = new FractionImperiale(96,0,1);

    protected FractionImperiale hauteurDesRetoursAir = new FractionImperiale(4,0,1);
    
    protected FractionImperiale hauteurSolDesRetoursAir = new FractionImperiale(12,0,1);
    
    protected FractionImperiale margeDesFenetres = new FractionImperiale(0, 1,8);
    
    // ------------- Constructeurs ------------- //
    public Salle(){}
    
    public Salle(Cote coteNord, Cote coteSud, Cote coteOuest, Cote coteEst, FractionImperiale longueurSalle, FractionImperiale largeurSalle){
        this.coteNord = coteNord;
        this.coteSud = coteSud;
        this.coteOuest = coteOuest;
        this.coteEst = coteEst;
        this.longueurSalle = longueurSalle;
        this.largeurSalle = largeurSalle;
    }
    
    public void restore() {
        this.coteNord = new Cote(Orientation.NORD);
        this.coteSud = new Cote(Orientation.SUD);
        this.coteOuest = new Cote(Orientation.OUEST);
        this.coteEst = new Cote(Orientation.EST);
    }

    // ------------- Getters ------------- //
    public Cote getCoteNord(){
        return this.coteNord;
    }
    
    public Cote getCoteSud(){
        return this.coteSud;
    }
    
    public Cote getCoteOuest(){
        return this.coteOuest;
    }
    
    public Cote getCoteEst(){
        return this.coteEst;
    }
    
    public ArrayList<Cote> getAllCotes(){
        ArrayList<Cote> allCotes = new ArrayList<Cote>();
        allCotes.add(this.coteNord);
        allCotes.add(this.coteSud);
        allCotes.add(this.coteEst);
        allCotes.add(this.coteOuest);
        return allCotes;
    }
    
    public FractionImperiale getLargeurSalle(){
        return this.largeurSalle;
    }
    
    public FractionImperiale getLongueurSalle(){
        return this.longueurSalle;
    }
    
    public FractionImperiale getHauteurMurs() {
        return this.coteNord.getHauteurMurs();
    }
    
    public ArrayList<Double> getDimensionsMurs(Orientation orientation){
        ArrayList<Double> dimensionsMurs = new ArrayList<Double>();
        double currentPosition = 0.0d;
        switch (orientation) {
            case NORD:
                for (Separateur separateur : coteNord.separateurs){
                    dimensionsMurs.add(separateur.getPosition().toDouble() - currentPosition);
                    currentPosition = separateur.getPosition().toDouble();
                }
                if (currentPosition < longueurSalle.toDouble()){
                    dimensionsMurs.add(longueurSalle.toDouble() - currentPosition);
                }
                this.setDimensionsMurs(dimensionsMurs, orientation);
                break;
            case SUD:
                for (Separateur separateur : coteSud.separateurs){
                    dimensionsMurs.add(separateur.getPosition().toDouble() - currentPosition);
                    currentPosition = separateur.getPosition().toDouble();
                }   
                if (currentPosition < longueurSalle.toDouble()){
                    dimensionsMurs.add(longueurSalle.toDouble() - currentPosition);
                }
                this.setDimensionsMurs(dimensionsMurs, orientation);
                break;
            case OUEST:
                for (Separateur separateur : coteOuest.separateurs){
                    dimensionsMurs.add(separateur.getPosition().toDouble() - currentPosition);
                    currentPosition = separateur.getPosition().toDouble();
                }   
                if (currentPosition < largeurSalle.toDouble()){
                    dimensionsMurs.add(largeurSalle.toDouble() - currentPosition);
                }
                this.setDimensionsMurs(dimensionsMurs, orientation);
                break;
            case EST:
                for (Separateur separateur : coteEst.separateurs){
                    dimensionsMurs.add(separateur.getPosition().toDouble() - currentPosition);
                    currentPosition = separateur.getPosition().toDouble();
                }  
                if (currentPosition < largeurSalle.toDouble()){
                    dimensionsMurs.add(largeurSalle.toDouble() - currentPosition);
                }
                this.setDimensionsMurs(dimensionsMurs, orientation);
                break;
            default:
                break;
        }
        return dimensionsMurs;
    }
    
     //------------- Setters ------------- //
    public void setDimensionsMurs(ArrayList<Double> dimensionsMurs, Orientation orientation) throws IllegalArgumentException {
        if (!(orientation instanceof Orientation)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une orientation valide.");
        } else if (!(dimensionsMurs instanceof ArrayList)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas un ArrayList valide.");            
        }
        switch (orientation) {
            case NORD:
                this.coteNord.setDimensionsMurs(dimensionsMurs, this.epaisseurDesMurs);
                break;
            case SUD:
                this.coteSud.setDimensionsMurs(dimensionsMurs, this.epaisseurDesMurs);
                break;
            case OUEST:
                this.coteOuest.setDimensionsMurs(dimensionsMurs, this.epaisseurDesMurs);
                break;
            case EST:
                this.coteEst.setDimensionsMurs(dimensionsMurs, this.epaisseurDesMurs);
                break;
            default:
                break;
        }
    }

    public void setCoteNord(Cote coteNord) throws IllegalArgumentException {
        if (!(coteNord instanceof Cote)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de côté.");
        } else if (coteNord.getOrientation() != Orientation.NORD) {
            throw new IllegalArgumentException("Le côté passé en paramètre n'a pas la bonne orientation.");            
        }
        
        this.coteNord = coteNord;
    }
    
    public void setCoteSud(Cote coteSud) throws IllegalArgumentException {
        if (!(coteSud instanceof Cote)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de côté.");
        } else if (coteSud.getOrientation() != Orientation.SUD) {
            throw new IllegalArgumentException("Le côté passé en paramètre n'a pas la bonne orientation.");            
        }
        
        this.coteSud = coteSud;    
    }
    
    public void setCoteOuest(Cote coteOuest) throws IllegalArgumentException {
        if (!(coteOuest instanceof Cote)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de côté.");
        } else if (coteOuest.getOrientation() != Orientation.OUEST) {
            throw new IllegalArgumentException("Le côté passé en paramètre n'a pas la bonne orientation.");            
        }
        
        this.coteOuest = coteOuest;     
    }
    
    public void setCoteEst(Cote coteEst) throws IllegalArgumentException {
        if (!(coteEst instanceof Cote)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de côté.");
        } else if (coteEst.getOrientation() != Orientation.EST) {
            throw new IllegalArgumentException("Le côté passé en paramètre n'a pas la bonne orientation.");            
        }
        
        this.coteEst = coteEst;     
    }
    
    public void setLargeurSalle(FractionImperiale fraction) throws IllegalArgumentException {
        if (!(fraction instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.largeurSalle = fraction;
    }
    
    public void setLongueurSalle(FractionImperiale fraction){
        if (!(fraction instanceof FractionImperiale)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de FractionImperiale.");
        }
        this.longueurSalle = fraction;
    }
    
    public int addSeparateur(double pointx, double pointy) {
        //pointx est compatible avec coteNord et coteSud
        if ((pointx > epaisseurDesMurs.toDouble()) && (pointx < longueurSalle.toDouble() - getEpaisseurDesMurs().toDouble())){
            //pointy est compatible avec coteNord
            if ((pointy > -1) && (pointy < getEpaisseurDesMurs().toDouble() +1)){
                return coteNord.addSeparateur(pointx);
            }
            //pointy est compatible avec coteSud
            else if ((pointy > largeurSalle.toDouble() - getEpaisseurDesMurs().toDouble() -1) && (pointy < largeurSalle.toDouble() +1)){
                return coteSud.addSeparateur(pointx);
            }
        }
        //pointy est compatible avec coteNord et coteSud
        if ((pointy > epaisseurDesMurs.toDouble()) && (pointy < largeurSalle.toDouble() - getEpaisseurDesMurs().toDouble())){
            //pointx est compatible avec mur ouest
            if ((pointx > -1) && (pointx < getEpaisseurDesMurs().toDouble() +1)){
                return coteOuest.addSeparateur(pointy);
            }
            //pointx est compatible avec mur est
            else if ((pointx > longueurSalle.toDouble() - getEpaisseurDesMurs().toDouble() -1) && (pointx < longueurSalle.toDouble() +1)){
                return coteEst.addSeparateur(pointy);
            }
        }
        return 0;
    }
    
    public int addFenetre(Sens sens, Orientation orientation, double pointx, double pointy) {
        switch (orientation){
            case NORD:
                return coteNord.addFenetre(this.longueurSalle.toDouble(), sens, pointx, pointy);
            case SUD:
                return coteSud.addFenetre(this.longueurSalle.toDouble(), sens, pointx, pointy);
            case OUEST:
                return coteOuest.addFenetre(this.largeurSalle.toDouble(), sens, pointx, pointy);
            case EST:
                return coteEst.addFenetre(this.largeurSalle.toDouble(), sens, pointx, pointy);
            default:
                return 0;
        }
    }
    
    public int addPorte(Sens sens, Orientation orientation, double pointx, double pointy) {
        switch (orientation){
            case NORD:
                return coteNord.addPorte(this.longueurSalle.toDouble(), sens, pointx, pointy, this.hauteurDesMurs);
            case SUD:
                return coteSud.addPorte(this.longueurSalle.toDouble(), sens, pointx, pointy, this.hauteurDesMurs);
            case OUEST:
                return coteOuest.addPorte(this.largeurSalle.toDouble(), sens, pointx, pointy, this.hauteurDesMurs);
            case EST:
                return coteEst.addPorte(this.largeurSalle.toDouble(), sens, pointx, pointy, this.hauteurDesMurs);
            default:
                return 0;
        }
    }
    
    public int addPriseElectrique(Orientation orientation, double pointx, double pointy) {
        switch (orientation){
            case NORD:
                return coteNord.addPriseElectrique(this.longueurSalle.toDouble(), pointx, pointy);
            case SUD:
                return coteSud.addPriseElectrique(this.longueurSalle.toDouble(), pointx, pointy);
            case OUEST:
                return coteOuest.addPriseElectrique(this.largeurSalle.toDouble(), pointx, pointy);
            case EST:
                return coteEst.addPriseElectrique(this.largeurSalle.toDouble(), pointx, pointy);
            default:
                return 0;
        }
    }
    
    public int addRetourAir(Orientation orientation, double pointx, double pointy) {
        switch (orientation){
            case NORD: 
                return coteNord.addRetourAir(this.longueurSalle.toDouble(), pointx, pointy);
            case SUD:
                return coteSud.addRetourAir(this.longueurSalle.toDouble(), pointx, pointy);
            case OUEST:
                return coteOuest.addRetourAir(this.largeurSalle.toDouble(), pointx, pointy);
            case EST:
                return coteEst.addRetourAir(this.largeurSalle.toDouble(), pointx, pointy);
            default:
                return 0;
        }
    }
    
    // ------------- Delete ------------- //
    public void deleteSelectedSeparateur(){
        coteNord.deleteSelectedSeparateur();
        coteSud.deleteSelectedSeparateur();
        coteOuest.deleteSelectedSeparateur();
        coteEst.deleteSelectedSeparateur();
    }
    
    public void deleteSelectedAccessoire(){
        coteNord.deleteSelectedAccessoire();
        coteSud.deleteSelectedAccessoire();
        coteOuest.deleteSelectedAccessoire();
        coteEst.deleteSelectedAccessoire();
    }
    
    public void deleteSelectedFenetre(){
        coteNord.deleteSelectedFenetre();
        coteSud.deleteSelectedFenetre();
        coteOuest.deleteSelectedFenetre();
        coteEst.deleteSelectedFenetre();
    }
    
    public void deleteSelectedPorte(){
        coteNord.deleteSelectedPorte();
        coteSud.deleteSelectedPorte();
        coteOuest.deleteSelectedPorte();
        coteEst.deleteSelectedPorte();
    }

    
    public void deleteSelectedPriseElectrique(){
        coteNord.deleteSelectedPriseElectrique();
        coteSud.deleteSelectedPriseElectrique();
        coteOuest.deleteSelectedPriseElectrique();
        coteEst.deleteSelectedPriseElectrique();
    }
    
    public void deleteSelectedRetourAir(){
        coteNord.deleteSelectedRetourAir();
        coteSud.deleteSelectedRetourAir();
        coteOuest.deleteSelectedRetourAir();
        coteEst.deleteSelectedRetourAir();
    }
    
    // ------------- Select ------------- //
    public int selectVuePlan(double pointx, double pointy){
        coteNord.unSelect();
        coteSud.unSelect();
        coteOuest.unSelect();
        coteEst.unSelect();
        
        //pointy est compatible avec coteNord
        if ((pointy > -1) && (pointy < getEpaisseurDesMurs().toDouble() +1)){
            if (coteNord.selectVuePlan(pointx, 1) != 0){
                return coteNord.selectVuePlan(pointx, 1);
            }
        }
        //pointy est compatible avec coteSud
        if ((pointy > largeurSalle.toDouble() - getEpaisseurDesMurs().toDouble() -1) && (pointy < largeurSalle.toDouble() +1)){
            if (coteSud.selectVuePlan(pointx, 1) != 0){
                return coteSud.selectVuePlan(pointx, 1);
            }
        }
        //pointx est compatible avec mur ouest
        if ((pointx > -1) && (pointx < getEpaisseurDesMurs().toDouble() +1)){
            if (coteOuest.selectVuePlan(pointy, 2) != 0){
                return coteOuest.selectVuePlan(pointy, 2);
            }
        }
        //pointx est compatible avec mur est
        if ((pointx > longueurSalle.toDouble() - getEpaisseurDesMurs().toDouble() -1) && (pointx < longueurSalle.toDouble() +1)){
            if (coteEst.selectVuePlan(pointy, 2) != 0){
                return coteEst.selectVuePlan(pointy, 2);
            }
        }
        return 0;
    }
    
    public FractionImperiale getSelectedSeparateurPosition(){
        if (coteNord.getSelectedSeparateurPosition() != null){
            return coteNord.getSelectedSeparateurPosition();
        }
        else if (coteSud.getSelectedSeparateurPosition() != null){
            return coteSud.getSelectedSeparateurPosition();
        }
        else if (coteOuest.getSelectedSeparateurPosition() != null){
            return coteOuest.getSelectedSeparateurPosition();
        }
        else if (coteEst.getSelectedSeparateurPosition() != null){
            return coteEst.getSelectedSeparateurPosition();
        }
        else {
            return null;
        }
    }
    
    public FractionImperiale getSelectedAccessoireLargeur(){
        if (coteNord.getSelectedAccessoireLargeur() != null){
            return coteNord.getSelectedAccessoireLargeur();
        }
        else if (coteSud.getSelectedAccessoireLargeur() != null){
            return coteSud.getSelectedAccessoireLargeur();
        }
        else if (coteOuest.getSelectedAccessoireLargeur() != null){
            return coteOuest.getSelectedAccessoireLargeur();
        }
        else if (coteEst.getSelectedAccessoireLargeur() != null){
            return coteEst.getSelectedAccessoireLargeur();
        }
        else {
            return null;
        }
    }
    
    public FractionImperiale getSelectedAccessoireHauteur(){
        if (coteNord.getSelectedAccessoireHauteur() != null){
            return coteNord.getSelectedAccessoireHauteur();
        }
        else if (coteSud.getSelectedAccessoireHauteur() != null){
            return coteSud.getSelectedAccessoireHauteur();
        }
        else if (coteOuest.getSelectedAccessoireHauteur() != null){
            return coteOuest.getSelectedAccessoireHauteur();
        }
        else if (coteEst.getSelectedAccessoireHauteur() != null){
            return coteEst.getSelectedAccessoireHauteur();
        }
        else {
            return null;
        }
    }
    
    public FractionImperiale getSelectedAccessoireCoordonneeX(){
        if (coteNord.getSelectedAccessoireCoordonneeX() != null){
            return coteNord.getSelectedAccessoireCoordonneeX();
        }
        else if (coteSud.getSelectedAccessoireCoordonneeX() != null){
            return coteSud.getSelectedAccessoireCoordonneeX();
        }
        else if (coteOuest.getSelectedAccessoireCoordonneeX() != null){
            return coteOuest.getSelectedAccessoireCoordonneeX();
        }
        else if (coteEst.getSelectedAccessoireCoordonneeX() != null){
            return coteEst.getSelectedAccessoireCoordonneeX();
        }
        else {
            return null;
        }
    }
    
    public FractionImperiale getSelectedAccessoireCoordonneeY(){
        if (coteNord.getSelectedAccessoireCoordonneeY() != null){
            return coteNord.getSelectedAccessoireCoordonneeY();
        }
        else if (coteSud.getSelectedAccessoireCoordonneeY() != null){
            return coteSud.getSelectedAccessoireCoordonneeY();
        }
        else if (coteOuest.getSelectedAccessoireCoordonneeY() != null){
            return coteOuest.getSelectedAccessoireCoordonneeY();
        }
        else if (coteEst.getSelectedAccessoireCoordonneeY() != null){
            return coteEst.getSelectedAccessoireCoordonneeY();
        }
        else {
            return null;
        }
    }
	
    public FractionImperiale getSelectedMurLargeurExterieure(){
        if (coteNord.getSelectedMurPosition()!= -1){
            ArrayList<Double> dimensionsMurs = getDimensionsMurs(Orientation.NORD);
            Double dimensionMurSelected = dimensionsMurs.get(coteNord.getSelectedMurPosition());
            FractionImperiale.doubleToFraction(dimensionMurSelected);
            return FractionImperiale.doubleToFraction(dimensionMurSelected);
        }
        else if (coteSud.getSelectedMurPosition()!= -1){
            ArrayList<Double> dimensionsMurs = getDimensionsMurs(Orientation.SUD);
            Double dimensionMurSelected = dimensionsMurs.get(coteSud.getSelectedMurPosition());
            FractionImperiale.doubleToFraction(dimensionMurSelected);
            return FractionImperiale.doubleToFraction(dimensionMurSelected);
        }
        else if (coteOuest.getSelectedMurPosition()!= -1){
            ArrayList<Double> dimensionsMurs = getDimensionsMurs(Orientation.OUEST);
            Double dimensionMurSelected = dimensionsMurs.get(coteOuest.getSelectedMurPosition());
            FractionImperiale.doubleToFraction(dimensionMurSelected);
            return FractionImperiale.doubleToFraction(dimensionMurSelected);
        }
        else if (coteEst.getSelectedMurPosition()!= -1){
            ArrayList<Double> dimensionsMurs = getDimensionsMurs(Orientation.EST);
            Double dimensionMurSelected = dimensionsMurs.get(coteEst.getSelectedMurPosition());
            FractionImperiale.doubleToFraction(dimensionMurSelected);
            return FractionImperiale.doubleToFraction(dimensionMurSelected);
        }
        else {
            return null;
        }
    }
    
    public FractionImperiale getSelectedRetourAirLargeur(){
        if (coteNord.getSelectedRetourAirLargeur() != null){
            return coteNord.getSelectedRetourAirLargeur();
        }
        else if (coteSud.getSelectedRetourAirLargeur() != null){
            return coteSud.getSelectedRetourAirLargeur();
        }
        else if (coteOuest.getSelectedRetourAirLargeur() != null){
            return coteOuest.getSelectedRetourAirLargeur();
        }
        else if (coteEst.getSelectedRetourAirLargeur() != null){
            return coteEst.getSelectedRetourAirLargeur();
        }
        else {
            return null;
        }
    }
    
    
    public void updateSelectedSeparateur(FractionImperiale newValue){
        if (coteNord.getSelectedSeparateurPosition() != null){
            coteNord.updateSelectedSeparateur(newValue);
        }
        else if (coteSud.getSelectedSeparateurPosition() != null){
            coteSud.updateSelectedSeparateur(newValue);
        }
        else if (coteOuest.getSelectedSeparateurPosition() != null){
            coteOuest.updateSelectedSeparateur(newValue);
        }
        else if (coteEst.getSelectedSeparateurPosition() != null){
            coteEst.updateSelectedSeparateur(newValue);
        }
    }
    
    public void updateSelectedAccessoire(FractionImperiale newCoordX,
                                         FractionImperiale newCoordY,
                                         FractionImperiale newLargeur,
                                         FractionImperiale newHauteur){
        if (coteNord.getSelectedAccessoireLargeur() != null){
            coteNord.updateSelectedAccessoire(newCoordX, newCoordY, newLargeur, newHauteur);
        }
        else if (coteSud.getSelectedAccessoireLargeur() != null){
            coteSud.updateSelectedAccessoire(newCoordX, newCoordY, newLargeur, newHauteur);
        }
        else if (coteOuest.getSelectedAccessoireLargeur() != null){
            coteOuest.updateSelectedAccessoire(newCoordX, newCoordY, newLargeur, newHauteur);
        }
        else if (coteEst.getSelectedAccessoireLargeur() != null){
            coteEst.updateSelectedAccessoire(newCoordX, newCoordY, newLargeur, newHauteur);
        }
    }
    
    public void updateSelectedRetourAirLargeur(FractionImperiale newValue){
        if (coteNord.getSelectedRetourAirLargeur() != null){
            coteNord.updateSelectedRetourAirLargeur(newValue);
        }
        else if (coteSud.getSelectedRetourAirLargeur() != null){
            coteSud.updateSelectedRetourAirLargeur(newValue);
        }
        else if (coteOuest.getSelectedRetourAirLargeur() != null){
            coteOuest.updateSelectedRetourAirLargeur(newValue);
        }
        else if (coteEst.getSelectedRetourAirLargeur() != null){
            coteEst.updateSelectedRetourAirLargeur(newValue);
        }
    }

    
    public int selectVueElevation(double pointx, double pointy, Orientation orientation, Sens sens){
        coteNord.unSelect();
        coteSud.unSelect();
        coteOuest.unSelect();
        coteEst.unSelect();
        switch (orientation) {
            case NORD:
                return coteNord.selectVueElevation(pointx, pointy, longueurSalle.toDouble(), sens);
            case SUD:
                return coteSud.selectVueElevation(pointx, pointy, longueurSalle.toDouble(), sens);
            case OUEST:
                return coteOuest.selectVueElevation(pointx, pointy, largeurSalle.toDouble(), sens);
            case EST:
                return coteEst.selectVueElevation(pointx, pointy, largeurSalle.toDouble(), sens);
            default:
                return 0;
        }
    }
    
    // ------------- Utilitaires ------------- //
    public boolean murIsSelected(Orientation orientation, int index){
        switch (orientation){
            case NORD:
                return coteNord.murIsSelected(index);
            case SUD:
                return coteSud.murIsSelected(index);
            case OUEST:
                return coteOuest.murIsSelected(index);
            case EST:
                return coteEst.murIsSelected(index);
            default:
                return false;
        }
    }
    
    //--------------- Getters et Setters en Cascade --------------//
    
    public void setEpaisseurDesMurs(FractionImperiale epaisseur) {
        this.epaisseurDesMurs = epaisseur;
        for (Cote cote : this.getAllCotes()){
            cote.setEpaisseurDesMurs(epaisseur);
        }
    }
    
    public void setLongueurDesPlis(FractionImperiale longueur) {
        this.longueurDesPlis = longueur;
        for (Cote cote : this.getAllCotes()){
            cote.setLongueurDesPlis(longueur);
        }
    }
    
    public void setMargeDesSoudures(FractionImperiale marge) {
        this.margeDesSoudures = marge;
        for (Cote cote : this.getAllCotes()){
            cote.setMargeDesSoudures(marge);
        }
    }
    
    public void setAngleDesSoudures(double angle) {
        this.angleDesSoudures = angle;
        for (Cote cote : this.getAllCotes()){
            cote.setAngleDesSoudures(angle);
        }
    }
    
    public void setMargeDesPlis(FractionImperiale marge) {
        this.margeDesPlis = marge;
        for (Cote cote : this.getAllCotes()){
            cote.setMargeDesPlis(marge);
        }
    }
    
    public void setHauteurDesMurs(FractionImperiale hauteur) {
        this.hauteurDesMurs = hauteur;
        ArrayList<Cote> cotes = this.getAllCotes();
        for (Cote cote : cotes) {
            cote.setHauteurDesMurs(hauteur);
        }
    }
    
    public void setHauteurDesRetoursAir(FractionImperiale hauteur) {
        this.hauteurDesRetoursAir = hauteur;
        for (Cote cote : this.getAllCotes()) {
            if (cote.hasRetoursAir()) {
                cote.setHauteurDesRetoursAir(hauteur);
            }
        }
    }
    
    public void setHauteurSolDesRetoursAir(FractionImperiale hauteur) {
        this.hauteurSolDesRetoursAir = hauteur;
        for (Cote cote : this.getAllCotes()) {
            cote.setHauteurSolDesRetoursAir(hauteur);
        }
    }
    
    public void setMargeDesFenetres(FractionImperiale margeFenetre){
        this.margeDesFenetres = margeFenetre;
        for (Cote cote : this.getAllCotes()) {
            if (cote.hasFenetre()) {
                cote.setMargeDesFenetres(margeFenetre);
            }
        }
    }
    
    public FractionImperiale getEpaisseurDesMurs() {
        return this.epaisseurDesMurs;
    }
    
    public FractionImperiale getLongueurDesPlis() {
        return coteNord.getLongueurDesPlis();
    }
    
    public FractionImperiale getMargeDesSoudures() {
        return coteNord.getMargeDesSoudures();
    }
    
    public double getAngleDesSoudures() {
        return coteNord.getAngleDesSoudures();
    }
    
    public FractionImperiale getMargeDesPlis() {
        return coteNord.getMargeDesPlis();
    }
    
    public FractionImperiale getHauteurDesRetoursAir() {
        return this.hauteurDesRetoursAir;
    }
    
    public FractionImperiale getMargeDesFenetres() {
        return this.margeDesFenetres;
    }
    
    public FractionImperiale getHauteurSolDesRetoursAir() {
        return this.hauteurSolDesRetoursAir;
    }
    
    @Override
    public Salle clone() throws CloneNotSupportedException {
        Salle salleClone = (Salle) super.clone();
        
        salleClone.setCoteEst(this.coteEst.clone());
        salleClone.setCoteNord(this.coteNord.clone());
        salleClone.setCoteOuest(this.coteOuest.clone());
        salleClone.setCoteSud(this.coteSud.clone());
        
        salleClone.setLongueurSalle((FractionImperiale) this.longueurSalle.clone());
        salleClone.setLargeurSalle((FractionImperiale) this.largeurSalle.clone());
        salleClone.setEpaisseurDesMurs((FractionImperiale) this.epaisseurDesMurs.clone());
        salleClone.setLongueurDesPlis((FractionImperiale) this.longueurDesPlis.clone());
        salleClone.setMargeDesSoudures((FractionImperiale) this.margeDesSoudures.clone());
        salleClone.setMargeDesPlis((FractionImperiale) this.margeDesPlis.clone());
        salleClone.setHauteurDesMurs((FractionImperiale) this.hauteurDesMurs.clone());
        salleClone.setHauteurDesRetoursAir((FractionImperiale) this.hauteurDesRetoursAir.clone());
        salleClone.setMargeDesFenetres((FractionImperiale) this.margeDesFenetres.clone());
        salleClone.setHauteurSolDesRetoursAir((FractionImperiale) this.hauteurSolDesRetoursAir.clone());
        return salleClone;
    }
    
    public int unSelectAll(){
        coteNord.unSelect();
        coteSud.unSelect();
        coteOuest.unSelect();
        coteEst.unSelect();
        return 0;
    }
}
