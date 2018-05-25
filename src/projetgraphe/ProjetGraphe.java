/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author axelb
 */
public class ProjetGraphe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Greedy(createGraphe1());
        WelshPowell(createGraphe1());
        System.out.println("======= Suite ======");
        Greedy(createGraphe2());
        WelshPowell(createGraphe2());
    }

    public static Graphe createGraphe1() {
        Noeud.resetNum();
        ArrayList<Noeud> sommets = new ArrayList<>();
        Noeud n1 = new Noeud();
        Noeud n2 = new Noeud();
        Noeud n3 = new Noeud();
        Noeud n4 = new Noeud();
        Noeud n5 = new Noeud();
        
        n1.addArrete(n2);
        n2.addArrete(n1);
        n1.addArrete(n3);
        n3.addArrete(n1);
        
        n2.addArrete(n3);
        n3.addArrete(n2);
        n2.addArrete(n4);
        n4.addArrete(n2);
        
        n3.addArrete(n4);
        n4.addArrete(n3);
        
        n4.addArrete(n5);
        n5.addArrete(n4);
        
        sommets.add(n1);
        sommets.add(n2);
        sommets.add(n3);
        sommets.add(n4);
        sommets.add(n5);
        
        Graphe graphe = new Graphe(sommets);
        
        return graphe;
    }
    
    public static Graphe createGraphe2() {
        Noeud.resetNum();
        ArrayList<Noeud> sommets = new ArrayList<>();
        Noeud n1 = new Noeud();
        Noeud n2 = new Noeud();
        Noeud n3 = new Noeud();
        Noeud n4 = new Noeud();
        Noeud n5 = new Noeud();
        Noeud n6 = new Noeud();
        Noeud n7 = new Noeud();
        
        n1.addArrete(n2, true);
        n1.addArrete(n3, true);
        n1.addArrete(n4, true);
        
        n2.addArrete(n3, true);
        n2.addArrete(n4, true);
        n2.addArrete(n5, true);
        
        n3.addArrete(n6, true);
        
        n4.addArrete(n5, true);
        n4.addArrete(n7, true);
        
        n5.addArrete(n6, true);
        n5.addArrete(n7, true);
        
        n6.addArrete(n7, true);
        
        sommets.add(n1);
        sommets.add(n2);
        sommets.add(n3);
        sommets.add(n4);
        sommets.add(n5);
        sommets.add(n6);
        sommets.add(n7);
        
        Graphe graphe = new Graphe(sommets);
        
        return graphe;
    }

    public static void WelshPowell(Graphe graphe) {
        ArrayList<Noeud> listeSommets = graphe.getSommets();
        Collections.sort(listeSommets, Collections.reverseOrder());
        // Classe les sommets par ordre décroissant de degrés
        ArrayList<Noeud> colories = new ArrayList<>();
        
        Noeud x = null;
        Noeud y = null;
        int k = 1;
        
        while(!listeSommets.isEmpty()) {
            x = listeSommets.get(0);
            x.setColor(k);
            colories.add(x);
            listeSommets.remove(x);
            y = listeSommets.get(0);
            for(int i = 1; i < listeSommets.size(); i++) {
                if(!y.hasAdjacentWithColor(k)) {
                    y.setColor(k);
                    colories.add(y);
                    listeSommets.remove(y);
                }
                y = listeSommets.get(0);
            }
            k++;
        }
        
        printColoredGraphe(colories);
        
        /*
        WelshPowell(G = (X, U), c);
        {
            Ranger les sommets par ordre de degrés décroissant dans la liste ordonnée L;
            k = 1;
            tant que (L non vide) faire
            {
                x = 1er sommet de L;
                c(x) = k;
                Enlever x de L;
                y = 1er sommet de L;
                tant que (fin de liste L non atteinte) faire
                {
                    si(y non adjacent à un sommet de couleur k) alors
                    {
                        c(y) = k;
                        Enlever y de L;
                    }
                    y = sommet suivant dans L;
                }
                k = k + 1;
            }
        }
         */
    }

    public static void Dsatur() {
        /*
        Idem que WelshPowell
        On met en priorité les sommets avec beaucoup de voisins coloriés
         */
    }

    public static void Greedy(Graphe graphe) {
        ArrayList<Noeud> listeSommets = graphe.getSommets();
        Collections.sort(listeSommets, Collections.reverseOrder());
        // Classe les sommets par ordre décroissant de degrés
        ArrayList<Noeud> colories = new ArrayList<>();
        
        Noeud x = null;
        
        while(!listeSommets.isEmpty()) {
            x = listeSommets.get(0);
            x.color();
            colories.add(x);
            listeSommets.remove(x);
        }
        
        printColoredGraphe(colories);
    }
    
    public static List<Noeud> sort() {
        // Utiliser la méthode Collections.sort(List<>)
        // Attention, elle trie directement la liste,
        // elle n'en renvoie pas une nouvelle
        return new ArrayList<Noeud>();
    }
    
    public static void printColoredGraphe(ArrayList<Noeud> colories) {
        String text = "Graphe : \n";
        for(Noeud n : colories) {
            text += n.toString() + "\n";
        }
        System.out.print(text);
    }
}
