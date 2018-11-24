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

    
    
    

}
