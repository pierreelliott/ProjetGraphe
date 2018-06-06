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
        int sens = -1;
        Graphe graphe = createGraphe1();
        Greedy(graphe, sens);
        WelshPowell(graphe, sens);
        Dsatur(graphe, sens);
        System.out.println("======= Suite ======");
        graphe = createGraphe2();
        Greedy(graphe, sens);
        WelshPowell(graphe, sens);
        Dsatur(graphe, sens);
        System.out.println("======= Suite ======");
        graphe = Graphe.loadFromFile("crown10.txt");
        Greedy(graphe, sens);
        WelshPowell(graphe, sens);
        Dsatur(graphe, sens);
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

    public static void WelshPowell(Graphe graphe, int tri) {
        ArrayList<Noeud> listeSommets = graphe.getCopyOfSommets();
        if(tri > 0) {
            // Sens ascendant (sur les degrés)
            Collections.sort(listeSommets);
        } else if(tri < 0) {
            // Sens descendant (sur les degrés)
            Collections.sort(listeSommets, Collections.reverseOrder());
        } else {
            // aléatoire
        }
        ArrayList<Noeud> colories = new ArrayList<>();
        
        Noeud x = null;
        Noeud y = null;
        int k = 1;
        
        while(!listeSommets.isEmpty()) {
            x = listeSommets.get(0);
            x.setColor(k);
            colories.add(x);
            listeSommets.remove(x);
            if(!listeSommets.isEmpty()) {
                y = listeSommets.get(0);
                for(int i = 0; i < listeSommets.size(); i++) {
                    int index = listeSommets.indexOf(y);
                    if(!y.hasAdjacentWithColor(k)) {
                        y.setColor(k);
                        colories.add(y);
                        listeSommets.remove(y);
                        if(index < listeSommets.size()) {
                            y = listeSommets.get(index);
                        }
                    } else {
                        if(index+1 < listeSommets.size()) {
                            y = listeSommets.get(index + 1);
                        }
                    }
                }
                k++;
            }
            
        }
        
        printColoredGraphe(colories);
        System.out.println("nombre de couleurs : " + colories.stream().map(s -> s.getColor()).distinct().count());
    }

    public static void Dsatur(Graphe graphe, int tri) {
        ArrayList<Noeud> listeSommets = graphe.getCopyOfSommets();
        if(tri > 0) {
            // Sens ascendant (sur les degrés)
            Collections.sort(listeSommets);
        } else if(tri < 0) {
            // Sens descendant (sur les degrés)
            Collections.sort(listeSommets, Collections.reverseOrder());
        } else {
            // aléatoire
        }
        ArrayList<Noeud> colories = new ArrayList<>();
        
        Noeud x = null;
        Noeud y = null;
        int k = 1;
        
        while(!listeSommets.isEmpty()) {
            x = listeSommets.get(0);
            x.setColor(k);
            colories.add(x);
            listeSommets.remove(x);
            if(!listeSommets.isEmpty()) {
                y = listeSommets.get(0);
                for(int i = 0; i < listeSommets.size(); i++) {
                    int index = listeSommets.indexOf(y);
                    if(!y.hasAdjacentWithColor(k)) {
                        y.setColor(k);
                        colories.add(y);
                        listeSommets.remove(y);
                    }
                    
                    if(index+1 < listeSommets.size()) {
                        int maxSatur = -1;
                        Noeud tmp = null;
                        for(int j = i; j < listeSommets.size(); j++) {
                            if(listeSommets.get(j).dsatValue() > maxSatur) {
                                maxSatur = listeSommets.get(j).dsatValue();
                                tmp = listeSommets.get(j);
                            }
                        }
                        y = tmp;
                    }
                }
                k++;
            }
            
        }
        
        printColoredGraphe(colories);
        System.out.println("nombre de couleurs : " + colories.stream().map(s -> s.getColor()).distinct().count());
        /*
        Idem que WelshPowell
        On met en priorité les sommets avec beaucoup de voisins coloriés
        Aide : http://prolland.free.fr/works/research/dsatphp/dsat.txt
        http://prolland.free.fr/Cours/Cycle2/Maitrise/GraphsTheory/TP/PrgGraphDsat/dsat_simple_c.txt
         */
    }

    public static void Greedy(Graphe graphe, int tri) {
        ArrayList<Noeud> listeSommets = graphe.getCopyOfSommets();
        if(tri > 0) {
            // Sens ascendant (sur les degrés)
            Collections.sort(listeSommets);
        } else if(tri < 0) {
            // Sens descendant (sur les degrés)
            Collections.sort(listeSommets, Collections.reverseOrder());
        } else {
            // aléatoire
        }
        ArrayList<Noeud> colories = new ArrayList<>();
        
        Noeud x = null;
        
        while(!listeSommets.isEmpty()) {
            x = listeSommets.get(0);
            x.color();
            colories.add(x);
            listeSommets.remove(x);
        }
        
        printColoredGraphe(colories);
        System.out.println("nombre de couleurs : " + colories.stream().map(s -> s.getColor()).distinct().count());
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
