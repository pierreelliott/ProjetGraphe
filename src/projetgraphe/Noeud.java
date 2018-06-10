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
public class Noeud implements Comparable {

    public Noeud() {
        this.adjacents = new ArrayList<>();
        this.num = numero++;
        this.color = 0;
    }

    public Noeud(ArrayList<Noeud> list, int nb, int col) {
        this.adjacents = list;
        this.num = nb;
        this.color = col;
    }

    /**
     * Ajoute un arc en précisant avec quel autre noeud le noeud actuel est relié
     *
     * Cette méthode est surchargée pour pouvoir travailler indépendamment avec des arcs ou des arêtes
     * @param noeud Noeud auquel celui-ci est relié
     */
    public void addArete(Noeud noeud) {
        if (!adjacents.contains(noeud)) {
            adjacents.add(noeud);
        }
    }

    /**
     * Ajoute une arête en précisant avec quel autre noeud le noeud actuel est relié
     * @param noeud Noeud auquel celui-ci est relié
     * @param reflex <em>True</em> si c'est une arête, <em>False</em> si c'est un arc
     */
    public void addArete(Noeud noeud, boolean reflex) {
        addArete(noeud);
        if (reflex) {
            noeud.addArete(this);
        }
    }

    public ArrayList<Noeud> getAdjacents() {
        return adjacents;
    }

    public int dsatValue() {
        return getForbiddenColors().size();
    }

    public boolean isAdjacent(Noeud n) {
        return adjacents.contains(n);
    }

    public boolean hasAdjacentWithColor(int col) {
        for (Noeud n : adjacents) {
            if (n.color == col) {
                return true;
            }
        }
        return false;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int val) {
        color = val;
    }

    public ArrayList<Integer> getForbiddenColors() {
        ArrayList<Integer> couleursInterdites = new ArrayList<>();
        for (Noeud n : adjacents) {
            if (!couleursInterdites.contains(n.color)) {
                couleursInterdites.add(n.color);
            }
        }
        return couleursInterdites;
    }

    public void color() {
        ArrayList<Integer> couleursInterdites = getForbiddenColors();
        int minCol = 1;
        for (int i = 0; i <= couleursInterdites.size(); i++) {
            boolean hasSame = false;
            for (int j = 0; j < couleursInterdites.size(); j++) {
                if (couleursInterdites.get(j).equals(minCol)) {
                    hasSame = true;
                }
            }
            if (hasSame) {
                minCol++;
            } else {
                break;
            }
        }

        this.color = minCol;
    }

    public int getDegre() {
        return adjacents.size();
    }

    public String getName() {
        return "Noeud" + num;
    }

    public void reset() {
        this.color = 0;
    }

    /**
     * Mouais... Je pense que ça va induire plus d'erreurs qu'autre chose
     * @return
     */
    public Noeud clone() {
        Noeud n = new Noeud(adjacents, num, color);
        return n;
    }

    @Override
    public String toString() {
        String text = getName() + " (couleur : " + color + ") : ";
        for (Noeud n : adjacents) {
            text += "(" + getName() + "," + n.getName() + "), ";
        }
        return text;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }

        if (this.getDegre() < ((Noeud) o).getDegre()) {
            return -1;
        } else if (this.getDegre() > ((Noeud) o).getDegre()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void resetNum() {
        numero = 1;
    }

    private ArrayList<Noeud> adjacents;
    private int color;
    private int num;

    private static int numero = 1;
}
