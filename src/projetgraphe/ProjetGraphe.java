/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author axelb
 */
public class ProjetGraphe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        System.out.println("======== Test ========");
        Graphe graphe;
//        graphe = createGraphe1();
//        testGraphe(graphe, "Graphe statique 1");
//        graphe = createGraphe2();
//        testGraphe(graphe, "Graphe statique 2");
        graphe = Graphe.loadFromFile("crown10.txt");
        testGraphe(graphe, "Graphe crown10");
        graphe = Graphe.loadFromFile("queen5_5.txt");
        testGraphe(graphe, "Graphe queen 5x5");
    }

    public static void testGraphe(Graphe graphe, String nomTest) {
        boolean extendedResults = false;
        System.out.println("======================================");
        System.out.println("======== Test '"+ nomTest +"' ========");
        System.out.println("-------- Test tri décroissant --------");
        int sens = -1;
        testAlgo(1, sens, graphe, true, extendedResults);
        testAlgo(2, sens, graphe, true, extendedResults);
        testAlgo(3, sens, graphe, true, extendedResults);
        System.out.println("--------------------------------------");
        System.out.println("-------- Test tri croissant --------");
        sens = 1;
        testAlgo(1, sens, graphe, true, extendedResults);
        testAlgo(2, sens, graphe, true, extendedResults);
        testAlgo(3, sens, graphe, true, extendedResults);
        System.out.println("--------------------------------------");
        System.out.println("-------- Test tri aléatoire --------");
        sens = 0;
        testAlgo(1, sens, graphe, true, extendedResults);
        testAlgo(2, sens, graphe, true, extendedResults);
        testAlgo(3, sens, graphe, true, extendedResults);
        System.out.println("--------------------------------------");
    }

    public void test1() {
        int sens = -1;
        Graphe graphe = createGraphe1();
        Greedy(trierSommets(graphe, sens));
        WelshPowell(trierSommets(graphe, sens));
        Dsatur(trierSommets(graphe, sens));
        System.out.println("======= Suite ======");
        graphe = createGraphe2();
        Greedy(trierSommets(graphe, sens));
        WelshPowell(trierSommets(graphe, sens));
        Dsatur(trierSommets(graphe, sens));
        System.out.println("======= Suite ======");
        graphe = Graphe.loadFromFile("crown10.txt");
        Greedy(trierSommets(graphe, sens));
        WelshPowell(trierSommets(graphe, sens));
        Dsatur(trierSommets(graphe, sens));
    }

    public static long testAlgo(int numAlgo, int sensDeTri, Graphe graphe,
                                boolean resetPreviousColoration, boolean printExtendedResults) {
        if(resetPreviousColoration) {
            graphe.resetColoration();
        }

        ArrayList<Noeud> sommets = trierSommets(graphe, sensDeTri);
        ArrayList<Noeud> colories;
        String[] algos = { "Greedy", "WelshPowell", "Dsatur" };

        // Regarder le temps
        // (on oublie le temps de tri des sommets puisque ce n'est pas ce qui est intéressant ici)
        long startTime = System.currentTimeMillis();

        if(numAlgo == 1) {
            colories = Greedy(sommets);
        } else if(numAlgo == 2) {
            colories = WelshPowell(sommets);
        } else {
            colories = Dsatur(sommets);
        }

        // Regarder le temps d'exécution
        long finishTime = System.currentTimeMillis();
        long elapsedTime = finishTime - startTime;
        long nbColors = colories.stream().map(s -> s.getColor()).distinct().count();
        graphe.setColorationNumber(nbColors);

        System.out.println("======= Algo '"+ algos[numAlgo-1] +"' ======");
        if(printExtendedResults) {
            printColoredGraphe(colories);
        }
        System.out.println("Nombre de couleurs : " + nbColors);
        System.out.println("Temps d'exécution : " + elapsedTime + " milliseconde(s)");

        return elapsedTime;
    }

    public static ArrayList<Noeud> trierSommets(Graphe graphe, int tri) {
        ArrayList<Noeud> sommets = graphe.getCopyOfSommets();
        ArrayList<Noeud> sommetsTries = new ArrayList<>();

        // Tri
        if(tri > 0) {
            // Sens ascendant (sur les degrés)
            Collections.sort(sommets);
            sommetsTries = sommets;
        } else if(tri < 0) {
            // Sens descendant (sur les degrés)
            Collections.sort(sommets, Collections.reverseOrder());
            sommetsTries = sommets;
        } else {
            // Tri aléatoire
            int size = sommets.size();
            int i = 0;
            Random rand = new Random();
            while(!sommets.isEmpty()) {
                int randomValue = rand.nextInt(sommets.size());
                sommetsTries.add(sommets.get(randomValue));
                sommets.remove(sommets.get(randomValue));
                if(i == size) {
                    System.out.println("Erreur tri aléatoire !");
                }
            }
        }

        return sommetsTries;
    }

    public static Graphe createGraphe1() {
        Noeud.resetNum();
        ArrayList<Noeud> sommets = new ArrayList<>();
        Noeud n1 = new Noeud();
        Noeud n2 = new Noeud();
        Noeud n3 = new Noeud();
        Noeud n4 = new Noeud();
        Noeud n5 = new Noeud();
        
        n1.addArete(n2);
        n2.addArete(n1);
        n1.addArete(n3);
        n3.addArete(n1);
        
        n2.addArete(n3);
        n3.addArete(n2);
        n2.addArete(n4);
        n4.addArete(n2);
        
        n3.addArete(n4);
        n4.addArete(n3);
        
        n4.addArete(n5);
        n5.addArete(n4);
        
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
        
        n1.addArete(n2, true);
        n1.addArete(n3, true);
        n1.addArete(n4, true);
        
        n2.addArete(n3, true);
        n2.addArete(n4, true);
        n2.addArete(n5, true);
        
        n3.addArete(n6, true);
        
        n4.addArete(n5, true);
        n4.addArete(n7, true);
        
        n5.addArete(n6, true);
        n5.addArete(n7, true);
        
        n6.addArete(n7, true);
        
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

    public static ArrayList<Noeud> WelshPowell(ArrayList<Noeud> listeSommets) {
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

        return colories;
    }

    public static ArrayList<Noeud> Dsatur(ArrayList<Noeud> listeSommets) {
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
        /*
        Idem que WelshPowell
        On met en priorité les sommets avec beaucoup de voisins coloriés
        Aide : http://prolland.free.fr/works/research/dsatphp/dsat.txt
        http://prolland.free.fr/Cours/Cycle2/Maitrise/GraphsTheory/TP/PrgGraphDsat/dsat_simple_c.txt
         */

        return colories;
    }

    public static ArrayList<Noeud> Greedy(ArrayList<Noeud> listeSommets) {
        ArrayList<Noeud> colories = new ArrayList<>();
        
        Noeud x = null;
        
        while(!listeSommets.isEmpty()) {
            x = listeSommets.get(0);
            x.color();
            colories.add(x);
            listeSommets.remove(x);
        }

        return colories;
    }
    
    public static void printColoredGraphe(ArrayList<Noeud> colories) {
        String text = "Graphe : \n";
        for(Noeud n : colories) {
            text += n.toString() + "\n";
        }
        System.out.print(text);
    }
}
