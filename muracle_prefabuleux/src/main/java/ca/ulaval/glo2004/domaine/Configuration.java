package ca.ulaval.glo2004.domaine;

import java.io.BufferedWriter;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// C'est la classe de configuration qui va contenir les informations sur le projetainsi que son état.
public class Configuration {
    // Chemin de sauvegarde devrait etre le "home" de l'utilisateur.
    private static final String CHEMIN_DE_SAUVEGARDE_DEFAULT = System.getProperty("user.home");
    private static final String NOM_DU_PROJET = "Projet";
    private static final Pattern projectNameValidationRegexp = Pattern.compile("^[a-zA-Z0-9](?:[a-zA-Z0-9 ._-]*[a-zA-Z0-9])?$");
    private static final String MURACLE_EXTENSION = ".mrcl";
        
    private boolean isSaved;
    private boolean isValidConfiguration;
    private String projectName;
    private String storePath;
    
    public Configuration() {
        this.isSaved = false;
        this.isValidConfiguration = true;
        this.projectName = NOM_DU_PROJET;
        this.storePath = CHEMIN_DE_SAUVEGARDE_DEFAULT + '/';
    }
    
    public boolean isSaved() {
        return isSaved;
    }
    
    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }
    
    public boolean isValidConfiguration() {
        return isValidConfiguration;
    }
    
    private void validateConfiguration() {
        // 1. Valider que le nom du projet est conforme aux exigences et que le chemin de sauvegard existe bel et bien.
        if (projectName.length() >= 255 || projectName.isEmpty() || !isProjectNameValid(projectName) || !isPathExists(this.storePath)) {
            this.isValidConfiguration = false;
        } else {
            this.isValidConfiguration = true;   
        }        
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
        validateConfiguration();
    }
    
    public String getStorePath() {
        return storePath;
    }
    
    public void setStorePath(String storePath) throws IllegalArgumentException {
        this.storePath = storePath;
        
        // 1. Si le dernier caratere n'est pas /, le rajouter.
        if (System.getProperty("os.name").startsWith("Windows")) {
            if (this.storePath.charAt(this.storePath.length() -1) != '\\') {
                this.storePath += '\\';
            }
        } else {
            if (this.storePath.charAt(this.storePath.length() -1) != '/') {
                this.storePath += '/';
            }
        }
        
        validateConfiguration();
    }
    
    public boolean isPathExists(String path) {
        return new File(path).exists();
    }
    
    public boolean isProjectNameValid(String projectName) {
        return projectNameValidationRegexp.matcher(projectName).find();
    }
    
    public String getCheminTotalDeSauvegarde() {
        return storePath + projectName + MURACLE_EXTENSION;
    }
        
    public void saveSalle(Salle salle) throws IllegalArgumentException, FileNotFoundException, IOException {
        // 1. Vérifier si la salle est une instance de salle.
        if (!(salle instanceof Salle)) {
            throw new IllegalArgumentException("L'argument passé en paramètre n'est pas une instance de la salle.");
        }

        // 2. Verifier que la configuration est valide
        if (!this.isValidConfiguration) {
            throw new IllegalArgumentException("Le chemin de sauvegarde est invalide.");
        }
        
        // 3. Sauvegarder le fichier.
        try {
            String cheminTotalDeSauvegarde = this.getCheminTotalDeSauvegarde();
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(cheminTotalDeSauvegarde));
            os.writeObject(salle);
            os.close();
            setIsSaved(true);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
    
    public Salle load(String cheminDeSauvegarde) throws IllegalArgumentException, FileNotFoundException, IOException, ClassNotFoundException {        
        try {
            // 1. Loader le fichier.
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(cheminDeSauvegarde));
            Salle salle = (Salle) is.readObject();
            is.close();
            
            // 2. Setter le nouveau nom de projet et le chemin de sauvegarde.
            String nomDuProjetLoader;
            String cheminDeSauvegardeDuProjetLoader;
            
            if (System.getProperty("os.name").startsWith("Windows")) {
                nomDuProjetLoader = cheminDeSauvegarde.substring(cheminDeSauvegarde.lastIndexOf("\\") + 1, cheminDeSauvegarde.lastIndexOf(".mrcl"));
                cheminDeSauvegardeDuProjetLoader = cheminDeSauvegarde.substring(0, cheminDeSauvegarde.lastIndexOf("\\"));
            } else {
                nomDuProjetLoader = cheminDeSauvegarde.substring(cheminDeSauvegarde.lastIndexOf("/") + 1, cheminDeSauvegarde.lastIndexOf(".mrcl"));
                cheminDeSauvegardeDuProjetLoader = cheminDeSauvegarde.substring(0, cheminDeSauvegarde.lastIndexOf("/"));
            }
            
            setProjectName(nomDuProjetLoader);
            setStorePath(cheminDeSauvegardeDuProjetLoader);
            setIsSaved(true);
            
            return salle;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
    
    public void exporterSVG(String svgChaineCaractere) throws IllegalArgumentException, FileNotFoundException, IOException, ClassNotFoundException {
        try {
            // 1. Ecrire dans le fichier et l'exporter.
            int indexDuPremierSigneDollar = svgChaineCaractere.indexOf("$");
            int indexDuDeuxiemenSigneDollar = svgChaineCaractere.indexOf("$", indexDuPremierSigneDollar + 1);

            String nomPanneau = svgChaineCaractere.substring(indexDuPremierSigneDollar + 1, indexDuDeuxiemenSigneDollar);
            File fichierExporte = new File(this.storePath + this.projectName + nomPanneau + ".html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fichierExporte));
            writer.write(svgChaineCaractere);
            writer.close();
            fichierExporte.createNewFile();
                                
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
