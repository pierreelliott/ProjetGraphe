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
    
    public Noeud()
    {
        this.adjacents = new ArrayList<>();
        this.num = numero++;
        this.color = 0;
    }
    
    public void addArrete(Noeud noeud)
    {
        adjacents.add(noeud);
    }
    
    public void addArrete(Noeud noeud, boolean reflex) {
        adjacents.add(noeud);
        if(reflex) {
            noeud.addArrete(this);
        }
    }
    
    public ArrayList<Noeud> getAdjacents()
    {
        return adjacents;
    }
    
    public boolean isAdjacent(Noeud n) {
        return adjacents.contains(n);
    }
    
    public boolean hasAdjacentWithColor(int col) {
        for(Noeud n : adjacents) {
            if(n.color == col) {
                return true;
            }
        }
        return false;
    }
    
    public int getColor() { return color; }
    public void setColor(int val) { color = val; }
    public void color() {
        ArrayList<Integer> couleursInterdites = new ArrayList<>();
        int minCol = 1;
        for(Noeud n : adjacents) {
            couleursInterdites.add(n.color);
        }
        
        for(int i = 0; i <= couleursInterdites.size(); i++) {
            boolean hasSame = false;
            for(int j = 0; j < couleursInterdites.size(); j++) {
                if(couleursInterdites.get(j).equals(minCol)) {
                    hasSame = true;
                }
            }
            if(hasSame) {
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
    
    public String getName() { return "Noeud"+num; }
    
    private ArrayList<Noeud> adjacents;
    private int color;
    private int num;
    
    private static int numero = 1;

    @Override
    public String toString() {
        String text = getName() + " (couleur : " + color + ") : ";
        for(Noeud n : adjacents) {
            text += "("+getName()+","+n.getName()+"), ";
        }
        return text;
    }

    @Override
    public int compareTo(Object o) {
        if(o == null) {
            return 1;
        }
        
        if(this.getDegre() < ((Noeud)o).getDegre()) {
            return -1;
        } else if(this.getDegre() > ((Noeud)o).getDegre()) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public static void resetNum() { numero = 1; }
}
