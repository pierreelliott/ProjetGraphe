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
    
    public Graphe()
    {
        this.sommets = new ArrayList<>();
    }
    
    public Graphe(ArrayList<Noeud> sommets) {
        this.sommets = sommets;
    }
    
    public ArrayList<Noeud> getSommets()
    {
        return sommets;
    }
    
    public ArrayList<Noeud> getCopyOfSommets()
    {
        // TODO
        // A faire après
        return sommets;
    }

    @Override
    public String toString() {
        String text = "Graphe : \n";
        for(Noeud n : sommets) {
            text += n.toString() + "\n";
        }
        return text;
    }
    
    private ArrayList<Noeud> sommets;
}
