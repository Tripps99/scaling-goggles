/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireescapesystem;

import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class Pathfinder {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Conection> conections = new ArrayList<Conection>();

    public Pathfinder(ArrayList<Node> nodes, ArrayList<Conection> conections) {
        this.nodes.addAll(nodes);
        this.conections.addAll(conections);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Conection> getConections() {
        return conections;
    }

    public void setConections(ArrayList<Conection> conections) {
        this.conections = conections;
    }

    public void seek(Node a) {
        System.out.println("Actual: " + a.getID());
        ArrayList<Conection> valid = new ArrayList<Conection>();
        for (Conection c : conections) {
            if (c.getBase() == a || a == c.getDestination()) {
                valid.add(c);

            }

        }
        
        for(Conection c : valid){
        Node tmp;
            if(c.getBase() == a){
        //c.getDestination().setSUM(a.getSUM() + c.getLength());
        tmp = c.getDestination();
        }else{
        //c.getDestination().setSUM(a.getSUM() + c.getLength());
        tmp = c.getBase();
            }
         if(((tmp.isIsExit() == false) && (tmp.getSUM()> a.getSUM() + c.getLength())) || (tmp.isIsExit() == false) && tmp.getSUM()==-1){
             {tmp.setSUM(a.getSUM() + c.getLength());
         if(a==c.getBase()){
         c.setWayB2D(false);
         seek(tmp);
         }
         
             }
         
         }
            
        
        }
    }
    
    public void findAndMark() {
        ArrayList<Node> exits = new ArrayList<Node>();
        for (Node n : nodes) {
            if (n.isIsExit()) {
                exits.add(n);
            }
        }

        for (Node e : exits) {
            e.setSUM(0);
            System.out.println("Exit found :" + e.getID());
            seek(e);
        }

    }

}
