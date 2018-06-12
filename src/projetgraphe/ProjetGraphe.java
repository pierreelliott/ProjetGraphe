/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetgraphe;

import java.util.ArrayList;
import java.util.Collections;

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
//        graphe = Graphe.loadFromFile("crown10.txt");
//        testGraphe(graphe, "Graphe crown10");
//        graphe = Graphe.loadFromFile("queen5_5.txt");
//        testGraphe(graphe, "Graphe queen 5x5");
//        graphe = Graphe.loadFromFile("queen15_15.txt");
//        testGraphe(graphe, "Graphe queen 15x15");

        String[] files = { "crown10.txt", "queen5_5.txt", "queen7_7.txt",
                "queen9_9.txt", "queen11_11.txt", "queen13_13.txt", "queen15_15.txt"};
        int nbEssais = 500;

        benchmark(files, 1, nbEssais);
        benchmark(files, 2, nbEssais);
    }

    public static void benchmark(String[] files, int numAlgo, int nbEssais) {
        long[][] bench;
        Graphe graphe;
        System.out.println("===== Benchmark de " + getNomAlgo(numAlgo) + " ===");

        System.out.println("------- Décroissant ------");
        for(String file: files) {
            graphe = Graphe.loadFromFile(file);
            bench = benchmarkAlgo(graphe, numAlgo, nbEssais, -1);
            printBenchmark(bench);
        }

        System.out.println("-------- Croissant -------");
        for(String file: files) {
            graphe = Graphe.loadFromFile(file);
            bench = benchmarkAlgo(graphe, numAlgo, nbEssais, 1);
            printBenchmark(bench);
        }

        System.out.println("-------- Aléatoire -------");
        for(String file: files) {
            graphe = Graphe.loadFromFile(file);
            bench = benchmarkAlgo(graphe, numAlgo, nbEssais, 0);
            printBenchmark(bench);
        }
    }

    public static long[][] benchmarkAlgo(Graphe graphe, int numAlgo, int nbEssais, int sens) {
        long[][] bench = new long[nbEssais][2];

        for(int i = 0; i < nbEssais; i++) {
            bench[i] = testAlgo(numAlgo, sens, graphe, true, false);
        }

        return bench;
    }

    public static void printBenchmark(long[][] tab) {
        long[][] b = { { Long.MAX_VALUE, 0, 0 }, { Long.MAX_VALUE, 0, 0 } };
        // b[0] -> le temps (en millisecondes)
        // b[1] -> la coloration
        for(long[] vals : tab) {
//            System.out.println(vals[0] + "\t" + vals[1]);

            // Pour le temps
            if(vals[0] < b[0][0]) b[0][0] = vals[0]; // Cherche Min
            b[0][1] += vals[0]; // Additionne
            if(vals[0] > b[0][2]) b[0][2] = vals[0]; // Cherche Max

            // Pour la coloration
            if(vals[1] < b[1][0]) b[1][0] = vals[1]; // Cherche Min
            b[1][1] += vals[1]; // Additionne
            if(vals[1] > b[1][2]) b[1][2] = vals[1]; // Cherche Max
        }

        System.out.print(/*"Temps exec :\t" + */b[0][0] + "\t" + b[0][1]/(float)tab.length + "\t" + b[0][2]);
        System.out.print("\t");
        System.out.println(/*"Coloration :\t" + */b[1][0] + "\t" + b[1][1]/(float)tab.length + "\t" + b[1][2]);
    }

    public static String getNomAlgo(int num) {
        switch (num) {
            case 1: return "Greedy";
            case 2: return "WelshPowell";
            default: return "Dsatur";
        }
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
//        extendedResults = true;
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

    public static long[] testAlgo(int numAlgo, int sensDeTri, Graphe graphe,
                                boolean resetPreviousColoration, boolean printExtendedResults) {
        if(resetPreviousColoration) {
            graphe.resetColoration();
        }

        // Mise à faux pour les benchmarks
        // Cela permet d'afficher le nom de l'algo, son temps d'exécution et le nombre de couleurs utilisées
        // À ne pas confondre avec "printExtendedResults" qui affiche le graphe entier avec les couleurs
        boolean printSimpleResult = false;

        ArrayList<Noeud> sommets = trierSommets(graphe, sensDeTri);
        ArrayList<Noeud> colories;
        String[] algos = { "Greedy", "WelshPowell", "Dsatur" };

        // Regarder le temps
        // (on oublie le temps de tri des sommets puisque ce n'est pas ce qui est intéressant ici)
        long startTime = System.currentTimeMillis();

        switch (numAlgo) {
            case 1:
                colories = Greedy(sommets);
                break;
            case 2:
                colories = WelshPowell(sommets);
                break;
            default:
                colories = Dsatur(sommets);
                break;
        }

        // Regarder le temps d'exécution
        long finishTime = System.currentTimeMillis();
        long elapsedTime = finishTime - startTime;
        long nbColors = colories.stream().map(s -> s.getColor()).distinct().count();
        graphe.setColorationNumber(nbColors);

        if(printSimpleResult) {
            System.out.println("======= Algo '" + algos[numAlgo-1] + "' ======");
            System.out.println("Nombre de couleurs : " + nbColors);
            System.out.println("Temps d'exécution : " + elapsedTime + " milliseconde(s)");
        }

        if(printExtendedResults) {
            printColoredGraphe(colories);
        }


        return new long[]{elapsedTime, nbColors};
    }

    public static ArrayList<Noeud> trierSommets(Graphe graphe, int tri) {
        ArrayList<Noeud> sommets = graphe.getCopyOfSommets();
        ArrayList<Noeud> sommetsTries;

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
            Collections.shuffle(sommets);
            sommetsTries = sommets;
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
                int index = listeSommets.indexOf(y);
                while(index < listeSommets.size()) {
                    if(!y.hasAdjacentWithColor(k)) {
                        y.setColor(k);
                        colories.add(y);
                        listeSommets.remove(y);
                    } else {
                        index++;
                    }
                    
                    if(index < listeSommets.size()) {
                        y = listeSommets.get(index);
                    }
                }
                k++;
            }
        }

        return colories;
    }
    
    public static ArrayList<Noeud> Dsatur(ArrayList<Noeud> listeSommets) {
        ArrayList<Noeud> colories = new ArrayList<>();
        
        ArrayList<Integer> dsatValues = new ArrayList<>();
        for(Noeud n : listeSommets) {
            dsatValues.add(n.dsatValue());
        }
        
        Noeud x = null;
        
        while(!listeSommets.isEmpty()) {
            if(0 < listeSommets.size()) {
                int maxSatur = -1;
                Noeud tmp = null;
                for(int j = 0; j < listeSommets.size(); j++) {
                    if(listeSommets.get(j).dsatValue() > maxSatur) {
                        maxSatur = listeSommets.get(j).dsatValue();
                        tmp = listeSommets.get(j);
                    }
                }
                x = tmp;
            }
            
            x.color();
            colories.add(x);
            listeSommets.remove(x);
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
