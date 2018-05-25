/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphe;

import java.util.ArrayList;

/**
 *
 * @author axelb
 */
public class Graphe {

    public Graphe() {
        this.sommets = new ArrayList<>();
    }

    public static Graphe load(String fileContent) {
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
                    .addArrete(sommets.get(Integer.parseInt(arete[1])), true);
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
        return sommets;
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
}
