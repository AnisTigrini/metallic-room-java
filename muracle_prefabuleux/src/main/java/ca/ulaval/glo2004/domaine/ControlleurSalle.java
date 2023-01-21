package ca.ulaval.glo2004.domaine;

import ca.ulaval.glo2004.domaine.DTO.FractionImperialeDTO;
import ca.ulaval.glo2004.domaine.enums.Orientation;
import ca.ulaval.glo2004.domaine.enums.Sens;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ControlleurSalle {
    
    // ------------- Attributs ------------- //
    protected List<Salle> listeSalle = new ArrayList<Salle>(Arrays.asList(new Salle()));
  
    protected int positionSalle = 0;
        
    // Le créer seulement si l'utilisateur passe le premier Frame.
    protected Configuration configuration;
    
    protected FractionImperiale hauteurGrille = new FractionImperiale(20, 0, 1);
    protected FractionImperiale largeureGrille = new FractionImperiale(20, 0, 1);
    
    // Propriétés de la grille
    protected boolean afficherGrille = false;

    private int errorType = 0;
    /* LISTE DES CODES D'ERREURS
    1 - SEPARATEUR NORD EN DEHORS DE LA SALLE
    2 - SEPARATEUR SUD EN DEHORS DE LA SALLE
    3 - SEPARATEUR OUEST EN DEHORS DE LA SALLE
    4 - SEPARATEUR EST EN DEHORS DE LA SALLE
    5 - SEPARATEUR EN ERREUR VUE ELEVATION - VOIR VUE EN PLAN
    6 - ACCESSOIRE COUPE PAR UN SEPARATEUR
    7 - DEUX ACCESSOIRES SONT EN CONFLIT
    8 - COORD X ACCESSOIRE EN DEHORS DES MURS
    9 - COORD Y ACCESSOIRE EN DEHORS DES MURS
    10 - RETOUR DAIR TROP LARGE POUR LE MUR
    11 - PORTE TROP HAUTE POUR LE MUR
    12 - CONDUIT DE VENTILATION EN CONFLIT AVEC UN ACCESSOIRE
    100 - COTE CONFORME - VALIDER AVEC LA VUE EN PLAN
    */
    
    private String currentSvg = "";

    // ------------- Constructeurs ------------- //
    public ControlleurSalle() {}

    public void restore() {
        this.restoreDefaultSalle();
        this.restoreDefaultConfig();
    }
    
    public void restoreDefaultSalle() {        
        this.listeSalle = new ArrayList<Salle>(Arrays.asList(new Salle()));
        this.positionSalle = 0;
    }
    
    public void undo() {
        if (this.positionSalle > 0) {
            this.positionSalle--;
        } else {
            this.positionSalle = 0;
        }
    }
    
    public void redo() {
        if (this.positionSalle < this.listeSalle.size() -1 ) {
            this.positionSalle++;
        } else {
            this.positionSalle = this.listeSalle.size() -1;
        }
    }
    
    public void restoreDefaultConfig() {
        this.configuration = null;
    }
    
    private void majUndoRedoEtConfig(Salle salleCouranteClone) {
        // 1. MAJ undo/redo
        this.listeSalle = this.listeSalle.subList(0, this.positionSalle + 1);
            
        this.listeSalle.add(salleCouranteClone);
        positionSalle++;
        
        // 2. MAJ configuration
        if (this.configuration != null) {
            this.configuration.setIsSaved(false);
        }
    }
    
    public void creerNouvelleCopieSalleCourante() {
        try {
            Salle salleCourante = getSalle().clone();
            majUndoRedoEtConfig(salleCourante);
            System.out.println("Copies de Salle : " + this.listeSalle.size());
        } catch (Exception e) {}

    }
    
    // ------------- Getters ------------- //
    public Salle getSalle() {
        return this.listeSalle.get(this.positionSalle);
    }
    
    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public Cote getCote(Orientation orientation){
        switch (orientation) {
            case NORD:
                return this.getSalle().getCoteNord();
            case SUD:
                return this.getSalle().getCoteSud();
            case OUEST:
                return this.getSalle().getCoteOuest();
            case EST:
                return this.getSalle().getCoteEst();
            default:
                return this.getSalle().getCoteNord();
        }
    }
    
    public ArrayList<Double> getDimensionsMurs(Orientation orientation){
        return this.getSalle().getDimensionsMurs(orientation);
    }
    
    public boolean exporterSVG() {    
        if (this.currentSvg.isEmpty()) {
            return false;
        }
        
        try {
          configuration.exporterSVG(this.currentSvg);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // ------------- Add ------------- //
    public void ajouterConfiguration() {
        this.configuration = new Configuration();
    }
    
    public void addSeparateur(double pointx, double pointy) {
        try {
            System.out.println("AddSeparateurs appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            int resultat = salleCouranteClone.addSeparateur(pointx, pointy);
            
            // 2. MAJ undo/red si un separateur a réellement étéuu ajouter.
            if (resultat != 0) {
                majUndoRedoEtConfig(salleCouranteClone);
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {}
    }
    
    public void addFenetre(Sens sens, Orientation orientation, double pointx, double pointy) {
        try {
            System.out.println("AddFenetre appele");

            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.addFenetre(sens, orientation, pointx, pointy);
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void addPorte(Sens sens, Orientation orientation, double pointx, double pointy) {
        try {
            System.out.println("AddPorte appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.addPorte(sens, orientation, pointx, pointy);
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void addPriseElectrique(Orientation orientation, double pointx, double pointy) {
        try {
            System.out.println("AddPriseElectrique appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.addPriseElectrique(orientation, pointx, pointy);
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void addRetourAir(Orientation orientation, double pointx, double pointy) {
        try {
            System.out.println("AddRetourAir appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.addRetourAir(orientation, pointx, pointy);
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    // ------------- Delete ------------- //
    public void deleteSelectedSeparateur(){
        try {
            System.out.println("deleteSeparateur appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.deleteSelectedSeparateur();
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void deleteSelectedAccessoire(){
        try {
            System.out.println("deleteSelectedAccessoire appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.deleteSelectedAccessoire();
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void deleteSelectedPorte() {
        try {
            System.out.println("deleteSelectedPorte appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.deleteSelectedPorte();
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void deleteSelectedFenetre() {
        try {
            System.out.println("deleteSelectedFenetre appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.deleteSelectedFenetre();
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void deleteSelectedPriseElectrique() {
        try {
            System.out.println("deleteSelectedPriseElectrique appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.deleteSelectedPriseElectrique();
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }
    
    public void deleteSelectedRetourAir() {
        // ICI ON DOIT SUPPRIMER AUSSI LE RETOUR D'AIR DU MUR
        try {
            System.out.println("deleteSelectedRetourAir appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            salleCouranteClone.deleteSelectedRetourAir();
            
            // 2. MAJ undo/red
            majUndoRedoEtConfig(salleCouranteClone);
            
            System.out.println("Copies de Salle : " +this.listeSalle.size());
        } catch (Exception e) {}
    }

    // ------------- Select ------------- //
    // TODO A REGARDER
    public int selectVuePlan(double pointx, double pointy) {
        return this.listeSalle.get(this.positionSalle).selectVuePlan(pointx, pointy);       
    }
    
    // TODO A REGARDER
    public int selectVueElevation(double pointx, double pointy, Orientation orientation, Sens sens){
        return this.listeSalle.get(this.positionSalle).selectVueElevation(pointx, pointy, orientation, sens);
    }
    
    public FractionImperialeDTO getSelectedSeparateurPosition() {
        FractionImperiale scSeparateurPosition = this.listeSalle.get(this.positionSalle).getSelectedSeparateurPosition();

        if (scSeparateurPosition != null) {
            return new FractionImperialeDTO(scSeparateurPosition.getPartieEntiere(), scSeparateurPosition.getNumerateur(), scSeparateurPosition.getDenominateur());                
        }
        
        return null;
    }
    
    public FractionImperialeDTO getSelectedAccessoireLargeur() {
        FractionImperiale scAccLargeur = this.listeSalle.get(this.positionSalle).getSelectedAccessoireLargeur();
                
        return new FractionImperialeDTO(scAccLargeur.getPartieEntiere(), scAccLargeur.getNumerateur(), scAccLargeur.getDenominateur());                
    }
    
    public FractionImperialeDTO getSelectedAccessoireHauteur() {
        FractionImperiale scAccHauteurMur = this.listeSalle.get(this.positionSalle).getSelectedAccessoireHauteur();
        
        return new FractionImperialeDTO(scAccHauteurMur.getPartieEntiere(), scAccHauteurMur.getNumerateur(), scAccHauteurMur.getDenominateur());                
    }
    
    public FractionImperialeDTO getSelectedAccessoireCoordonneeX() {
        FractionImperiale scAccCoordonneX = this.listeSalle.get(this.positionSalle).getSelectedAccessoireCoordonneeX();
        
        return new FractionImperialeDTO(scAccCoordonneX.getPartieEntiere(), scAccCoordonneX.getNumerateur(), scAccCoordonneX.getDenominateur());                
    }
    
    public FractionImperialeDTO getSelectedAccessoireCoordonneeY() {
        FractionImperiale scAccCoordonneY = this.listeSalle.get(this.positionSalle).getSelectedAccessoireCoordonneeY();
        
        return new FractionImperialeDTO(scAccCoordonneY.getPartieEntiere(), scAccCoordonneY.getNumerateur(), scAccCoordonneY.getDenominateur());        
    }
    public FractionImperialeDTO getSelectedMurLargeurExterieure(){
        FractionImperiale largeurMur = this.listeSalle.get(this.positionSalle).getSelectedMurLargeurExterieure();
        return new FractionImperialeDTO(largeurMur.getPartieEntiere(), largeurMur.getNumerateur(), largeurMur.getDenominateur());        
    }
    
    public FractionImperialeDTO getSelectedRetourAirLargeur() {
        FractionImperiale largeurRetourAir = this.listeSalle.get(this.positionSalle).getSelectedRetourAirLargeur();
        return new FractionImperialeDTO(largeurRetourAir.getPartieEntiere(), largeurRetourAir.getNumerateur(), largeurRetourAir.getDenominateur());        
    }
    
    public void updateSelectedSeparateur(FractionImperialeDTO fdto){
        try {
            System.out.println("UpdateSelectedSeparateur appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale newValue = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCourante = this.listeSalle.get(this.positionSalle);
            
            salleCourante.updateSelectedSeparateur(newValue);
            
        } catch (Exception e) {}
    }
    
    public void updateSelectedAccessoire(FractionImperialeDTO newCoordXDTO,
                                         FractionImperialeDTO newCoordYDTO,
                                         FractionImperialeDTO newLargeuDTO,
                                         FractionImperialeDTO newHauteurDTO){
        try {
            System.out.println("UpdateSelectedAccessoire appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale newCoordX = new FractionImperiale(newCoordXDTO.partieEntiere, newCoordXDTO.numerateur, newCoordXDTO.denominateur) ;
            FractionImperiale newCoordY = new FractionImperiale(newCoordYDTO.partieEntiere, newCoordYDTO.numerateur, newCoordYDTO.denominateur) ;
            FractionImperiale newLargeur = new FractionImperiale(newLargeuDTO.partieEntiere, newLargeuDTO.numerateur, newLargeuDTO.denominateur) ;
            FractionImperiale newHauteur = new FractionImperiale(newHauteurDTO.partieEntiere, newHauteurDTO.numerateur, newHauteurDTO.denominateur) ;
                        
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            
            // 1.1 Get le selected accessoire
            FractionImperiale selcAccessCoordX= salleCouranteClone.getSelectedAccessoireCoordonneeX();
            FractionImperiale selcAccessCoordY= salleCouranteClone.getSelectedAccessoireCoordonneeY();
            FractionImperiale selcAccessLargeure = salleCouranteClone.getSelectedAccessoireLargeur();
            FractionImperiale selcAccessHauteur= salleCouranteClone.getSelectedAccessoireHauteur();
            
            // 2. Créer une copie de salle seulement lorsque une de ces valeurs change.
            if (newCoordX.compareTo(selcAccessCoordX) != 0 || newCoordY.compareTo(selcAccessCoordY) != 0 ||
                    newLargeur.compareTo(selcAccessLargeure) != 0 || newHauteur.compareTo(selcAccessHauteur) != 0) {
                
                salleCouranteClone.updateSelectedAccessoire(newCoordX, newCoordY, newLargeur, newHauteur);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);

                System.out.println("Copies de Salle : " +this.listeSalle.size());   
            }
        } catch (Exception e) {}
    }
    
    public void updateSelectedRetourAirLargeur(FractionImperialeDTO fdto){
        try {
            System.out.println("UpdateSelectedRetourAirLargeur appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale newValue = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale selectedRetourAirLargeur = salleCouranteClone.getSelectedRetourAirLargeur();
            
            if (selectedRetourAirLargeur != null) {
                if (selectedRetourAirLargeur.compareTo(newValue) != 0) {
                         
                    salleCouranteClone.updateSelectedRetourAirLargeur(newValue);
                    // 2. MAJ undo/red
                    majUndoRedoEtConfig(salleCouranteClone);
                    System.out.println("Copies de Salle : " +this.listeSalle.size());
                }
            }
        } catch (Exception e) {}
    }
    
    // ------------- Utilitaires ------------- //
    // Fonction qui va utiliser les setters de la salle. Peut throw des exceptions.
    public void construireSalle(FractionImperialeDTO longueurExterieureDTO, FractionImperialeDTO largeureExterieureDTO, 
            FractionImperialeDTO hauteurDesMursDTO, FractionImperialeDTO epaisseurDesMursDTO, FractionImperialeDTO margeDesPlisDTO,
            FractionImperialeDTO margeDeSoudureDTO, FractionImperialeDTO hauteurDesRetoursDairDTO, FractionImperialeDTO hauteurSolDesRetoursAirDTO,
            double angleDesPlisDeSoudure, FractionImperialeDTO margeDesFenetresDTO) {
        
        Salle salle = new Salle();
        
        salle.setLongueurSalle(new FractionImperiale(longueurExterieureDTO.partieEntiere, longueurExterieureDTO.numerateur, longueurExterieureDTO.denominateur));
        salle.setLargeurSalle(new FractionImperiale(largeureExterieureDTO.partieEntiere, largeureExterieureDTO.numerateur, largeureExterieureDTO.denominateur));
        salle.setHauteurDesMurs(new FractionImperiale(hauteurDesMursDTO.partieEntiere, hauteurDesMursDTO.numerateur, hauteurDesMursDTO.denominateur));
        salle.setEpaisseurDesMurs(new FractionImperiale(epaisseurDesMursDTO.partieEntiere, epaisseurDesMursDTO.numerateur, epaisseurDesMursDTO.denominateur));
        salle.setMargeDesPlis(new FractionImperiale(margeDesPlisDTO.partieEntiere, margeDesPlisDTO.numerateur, margeDesPlisDTO.denominateur));
        salle.setMargeDesSoudures(new FractionImperiale(margeDeSoudureDTO.partieEntiere, margeDeSoudureDTO.numerateur, margeDeSoudureDTO.denominateur));
        salle.setHauteurDesRetoursAir(new FractionImperiale(hauteurDesRetoursDairDTO.partieEntiere, hauteurDesRetoursDairDTO.numerateur, hauteurDesRetoursDairDTO.denominateur));
        salle.setAngleDesSoudures(angleDesPlisDeSoudure);
        salle.setMargeDesFenetres(new FractionImperiale(margeDesFenetresDTO.partieEntiere, margeDesFenetresDTO.numerateur, margeDesFenetresDTO.denominateur));
        salle.setHauteurSolDesRetoursAir(new FractionImperiale(hauteurSolDesRetoursAirDTO.partieEntiere, hauteurSolDesRetoursAirDTO.numerateur, hauteurSolDesRetoursAirDTO.denominateur));
        
        this.listeSalle = new ArrayList<Salle>();
        this.listeSalle.add(salle);
        this.positionSalle = 0;
        
        System.out.println("ConstruireSalle appele");
        System.out.println("Copies de Salle : " +this.listeSalle.size());
    }
    
    public void fermerLogiciel() throws IllegalStateException {
        // 1. Si l'objet configuration n'existe pas on peut fermer sans problemes.
        if (this.configuration == null) {
            System.exit(0);
        } else {
            if (this.configuration.isSaved()) {
                System.exit(0);
            } else {
                throw new IllegalStateException("Vous tentez de quitter sans avoir sauvegradé!");
            }
        }
    }
    
    public void loaderProjet(String cheminDeSauvegarde) throws IllegalArgumentException, FileNotFoundException, IOException, ClassNotFoundException {
        this.ajouterConfiguration();
        Salle salleLoader = this.configuration.load(cheminDeSauvegarde);
        
        this.listeSalle = new ArrayList<Salle>(Arrays.asList(salleLoader));
    }
    
    public void sauvegarderProjet() throws IllegalArgumentException, FileNotFoundException, IOException {
        Salle derniereSalle = this.listeSalle.get(this.listeSalle.size() -1);
        
        this.configuration.saveSalle(derniereSalle);
    }

    public boolean murIsSelected(Orientation orientation, int index){
        Salle salleCourante = this.listeSalle.get(this.positionSalle);
        
        return salleCourante.murIsSelected(orientation,index);
    }
    
    //--------------- Getters et Setters en Cascade --------------//
    public void setLargeurSalle(FractionImperialeDTO fdto){
        try {
            System.out.println("setLargeurSalle appele" + fdto.partieEntiere + " " + fdto.numerateur + " " + fdto.denominateur);
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scLargeureSalle = salleCouranteClone.getLargeurSalle();
            
            if (fraction.getPartieEntiere() != scLargeureSalle.getPartieEntiere() || fraction.getNumerateur() != scLargeureSalle.getNumerateur()
                || fraction.getDenominateur() != scLargeureSalle.getDenominateur()) {
                
                salleCouranteClone.setLargeurSalle(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
            
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            } 
        } catch (Exception e) {}
    }
    
    public void setLongueurSalle(FractionImperialeDTO fdto){
        try {
            System.out.println("setLongueurSalle appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scLongueurSalle= salleCouranteClone.getLongueurSalle();
                        
            if (fraction.getPartieEntiere() != scLongueurSalle.getPartieEntiere() || fraction.getNumerateur() != scLongueurSalle.getNumerateur()
                || fraction.getDenominateur() != scLongueurSalle.getDenominateur()) {
                
                salleCouranteClone.setLongueurSalle(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
            
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }            
        } catch (Exception e) {}
    }
    
    public void setEpaisseurDesMurs(FractionImperialeDTO fdto) {
        try {
            System.out.println("setEpaisseurDesMurs appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scEpaisseurMurs = salleCouranteClone.getEpaisseurDesMurs();
            
            if (fraction.getPartieEntiere() != scEpaisseurMurs.getPartieEntiere() || fraction.getNumerateur() != scEpaisseurMurs.getNumerateur()
                || fraction.getDenominateur() != scEpaisseurMurs.getDenominateur()) {
                
                salleCouranteClone.setEpaisseurDesMurs(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
            
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {}                
    }
    
    public void setLongueurDesPlis(FractionImperialeDTO fdto) {
        try {
            System.out.println("setLongueurDesPlis appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scLongueurPlis = salleCouranteClone.getLongueurDesPlis();
            
            if (fraction.getPartieEntiere() != scLongueurPlis.getPartieEntiere() || fraction.getNumerateur() != scLongueurPlis.getNumerateur()
                || fraction.getDenominateur() != scLongueurPlis.getDenominateur()) {
                
                salleCouranteClone.setLongueurDesPlis(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {}  
    }
    
    public void setMargeDesSoudures(FractionImperialeDTO fdto) {
        try {
            System.out.println("setMargeDesSoudures appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scMargeSoudures = salleCouranteClone.getMargeDesSoudures();
            
            if (fraction.getPartieEntiere() != scMargeSoudures.getPartieEntiere() || fraction.getNumerateur() != scMargeSoudures.getNumerateur()
                || fraction.getDenominateur() != scMargeSoudures.getDenominateur()) {
                
                salleCouranteClone.setMargeDesSoudures(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            } 
        } catch (Exception e) {} 
    }
    
    public void setAngleDesSoudures(double angle) {
        try {
            System.out.println("setAngleDesSoudures appele");
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            
            if (angle != salleCouranteClone.getAngleDesSoudures()) {
                salleCouranteClone.setAngleDesSoudures(angle);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
            
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }      
        } catch (Exception e) {} 
    }
    
    public void setMargeDesPlis(FractionImperialeDTO fdto) {
        try {
            System.out.println("setMargeDesPlis appele" + fdto.partieEntiere + " " + fdto.numerateur + " " + fdto.denominateur);
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scMargePlis = salleCouranteClone.getMargeDesPlis();
            
            if (fraction.getPartieEntiere() != scMargePlis.getPartieEntiere() || fraction.getNumerateur() != scMargePlis.getNumerateur()
                || fraction.getDenominateur() != scMargePlis.getDenominateur()) {
            
                salleCouranteClone.setMargeDesPlis(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {}                
    }
    
    public void setHauteurSolDesRetoursAirDTO(FractionImperialeDTO fdto) {
        try {
            System.out.println("setHauteurSolDesRetoursAirDTO appele" + fdto.partieEntiere + " " + fdto.numerateur + " " + fdto.denominateur);
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scHauteurSolDesRetoursAir = salleCouranteClone.getHauteurSolDesRetoursAir();
            
            if (fraction.getPartieEntiere() != scHauteurSolDesRetoursAir.getPartieEntiere() || fraction.getNumerateur() != scHauteurSolDesRetoursAir.getNumerateur()
                || fraction.getDenominateur() != scHauteurSolDesRetoursAir.getDenominateur()) {
            
                salleCouranteClone.setHauteurSolDesRetoursAir(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {}
    }

    public void setHauteurMurs(FractionImperialeDTO fdto) {
        try {
            System.out.println("setHauteurMurs appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scHauteur = salleCouranteClone.getHauteurMurs();
            
            if (fraction.getPartieEntiere() != scHauteur.getPartieEntiere() || fraction.getNumerateur() != scHauteur.getNumerateur()
                    || fraction.getDenominateur() != scHauteur.getDenominateur()) {
                    
                salleCouranteClone.setHauteurDesMurs(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {} 
    }
    
    public void setHauteurDesRetoursAir(FractionImperialeDTO fdto) {
        try {
            System.out.println("setHauteurDesRetoursAir appele");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scHauteurRetoursAir = salleCouranteClone.getHauteurDesRetoursAir();
            
            if (fraction.getPartieEntiere() != scHauteurRetoursAir.getPartieEntiere() || fraction.getNumerateur() != scHauteurRetoursAir.getNumerateur()
                || fraction.getDenominateur() != scHauteurRetoursAir.getDenominateur()) {
                
                salleCouranteClone.setHauteurDesRetoursAir(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {}                
    }
    
    public void setMargeDesFenetres(FractionImperialeDTO fdto){
        try {
            System.out.println("setMargeDesFenetres appelé");
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            
            // 1. Cloner la salle sur laquelle on est.
            Salle salleCouranteClone = this.listeSalle.get(this.positionSalle).clone();
            FractionImperiale scmargeDesFenetres = salleCouranteClone.getMargeDesFenetres();
            
            if (fraction.getPartieEntiere() != scmargeDesFenetres.getPartieEntiere() || fraction.getNumerateur() != scmargeDesFenetres.getNumerateur()
                || fraction.getDenominateur() != scmargeDesFenetres.getDenominateur()) {
                
                salleCouranteClone.setMargeDesFenetres(fraction);
            
                // 2. MAJ undo/red
                majUndoRedoEtConfig(salleCouranteClone);
                
                System.out.println("Copies de Salle : " +this.listeSalle.size());
            }
        } catch (Exception e) {} 
    
    }
    
    public void setHauteurGrille(FractionImperialeDTO fdto) {
        try {
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            this.hauteurGrille = fraction;
        } catch (Exception e) {}
    }
    
    public void setLargeureGrille(FractionImperialeDTO fdto) {
        try {
            // 0. Transformer DTO en FractionImperiale
            FractionImperiale fraction = new FractionImperiale(fdto.partieEntiere, fdto.numerateur, fdto.denominateur);
            this.largeureGrille = fraction;
        } catch (Exception e) {}
    }
    
    public FractionImperialeDTO getLargeurSalle() {
        FractionImperiale f = this.getSalle().getLargeurSalle();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getLongueurSalle() {
        FractionImperiale f = this.getSalle().getLongueurSalle();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getEpaisseurDesMurs() {
        FractionImperiale f = this.getSalle().getEpaisseurDesMurs();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getLongueurDesPlis() {
        FractionImperiale f = this.getSalle().getLongueurDesPlis();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getMargeDesSoudures() {
        FractionImperiale f = this.getSalle().getMargeDesSoudures();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public double getAngleDesSoudures() {
        // Int passage par valeurs donc OK.
        return this.getSalle().getAngleDesSoudures();
    }
    
    public FractionImperialeDTO getMargeDesPlis() {
        FractionImperiale f = this.getSalle().getMargeDesPlis();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getHauteurMurs() {
        FractionImperiale f = this.getSalle().getHauteurMurs();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getHauteurDesRetoursAir() {
        FractionImperiale f = this.getSalle().getHauteurDesRetoursAir();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getMargeDesFenetres() {
        FractionImperiale f = this.getSalle().getMargeDesFenetres();
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getHauteurGrille() {
        FractionImperiale f = this.hauteurGrille;
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public FractionImperialeDTO getLargeurGrille() {
        FractionImperiale f = this.largeureGrille;
        return new FractionImperialeDTO(f.getPartieEntiere(), f.getNumerateur(), f.getDenominateur());
    }
    
    public void setErrorType(int i){
        this.errorType = i;
    }
    
    public int getErrorType(){
        return this.errorType;
    }
    
    public boolean getAfficherGrille() {
        return this.afficherGrille;
    }
    
    public void reverseAfficherGrille() {
        if (this.afficherGrille) {
            this.afficherGrille = false;
        } else {
            this.afficherGrille = true;
        }
    }
    
    public Mur getMurParIndexEtOrientation(int index, Orientation orientationDemande) {
        // TODO : A modifier pour avoir une map avec les accessoires.
        Salle salleCourante = getSalle();
        Cote cote;
        
        if (orientationDemande == Orientation.EST) {
            cote = salleCourante.coteEst;
        } else if (orientationDemande == Orientation.NORD) {
            cote = salleCourante.coteNord;
        } else if (orientationDemande == Orientation.SUD) {
            cote = salleCourante.coteSud;
        } else {
            cote = salleCourante.coteOuest;
        }
        
        return cote.murs.get(index);
        
    }
    
    public int unSelectAll(){
        Salle salleCourante = getSalle();
        return salleCourante.unSelectAll();
    }
    
    public String getCurrentSvg(){
        return this.currentSvg;
    }
    
    public void setCurrentSvg(String svg){
        this.currentSvg = svg;
    }
}
