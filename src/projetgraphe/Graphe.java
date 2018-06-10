/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author axelb
 */
public class Graphe implements Cloneable {

    public Graphe() {
        this.sommets = new ArrayList<>();
    }

    public static Graphe loadFromFile(String filename) {
        File file = new File(filename);
        BufferedInputStream fileStream;
        String fileContent = "";
        try {
            fileStream = new BufferedInputStream(new FileInputStream(file));
            byte fileData[] = new byte[(int) file.length()];
            fileStream.read(fileData);
            fileContent = new String(fileData);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Graphe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Graphe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return loadFromString(fileContent);
    }
    
    public static Graphe loadFromString(String fileContent) {
        // Créer sommets
        String[] tab = fileContent.split("--- Liste des sommets\n")[1]
                .split("--- Liste des aretes\n");
        // tab[0] -> sommets
        // tab[1] -> aretes
        
        String[] sommetsString = tab[0].split("\n");
        ArrayList<Noeud> sommets = new ArrayList<>();
        for(String s : sommetsString) {
            if(!s.equals(""))
                sommets.add(new Noeud());
        }
        // Ajouter sommets
        String[] aretesString = tab[1].split("\n");
        
        for(String s : aretesString) {
            if(s.equals(""))
                continue;
            
            String arete[] = s.split(" ");
            sommets.get(Integer.parseInt(arete[0]))
                    .addArete(sommets.get(Integer.parseInt(arete[1])), true);
        }
        
        return new Graphe(sommets);
    }

    public Graphe(ArrayList<Noeud> sommets) {
        this.sommets = sommets;
    }

    public ArrayList<Noeud> getSommets() {
        return sommets;
    }

    public ArrayList<Noeud> getCopyOfSommets() {
        // TODO
        // A faire après
        return new ArrayList<>(sommets);
    }

    public long getColorationNumber() { return nbCouleurs; }
    public void setColorationNumber(long val) { nbCouleurs = val; }

    public void resetColoration() {
        for (Noeud n : sommets) {
            n.reset();
        }
        nbCouleurs = 0;
    }

    @Override
    public String toString() {
        String text = "Graphe : \n";
        for (Noeud n : sommets) {
            text += n.toString() + "\n";
        }
        return text;
    }

    private ArrayList<Noeud> sommets;
    private long nbCouleurs = 0;
}
