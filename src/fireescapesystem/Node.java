/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireescapesystem;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class Node {
    private int xPosition;
    private int yPosition;
    private int ID;
    private boolean chosen;
    public static final int xSize = 32;
    public static final int ySize = 32;

    private ArrayList<Conection> connections = new ArrayList<Conection>();

    public ArrayList<Conection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Conection> connections) {
        this.connections = connections;
    }
    
    
    
    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Node(int xPosition, int yPosition, int ID) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.ID = ID;
        chosen = false;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
    
    
    
    public void Render(Graphics g) {
        //xPosition += baseRenderPointX;
        //yPosition += baseRenderPointY;
        Color outline;
        Color infill;
        
        for(Conection c : connections){
        c.Render(g, this);
        }
        
        if(chosen){
        outline = Color.ORANGE;
        infill = Color.red;
        }else{
        outline = Color.green;
        infill = Color.darkGray;
        
        }
            
        g.setColor(outline);
        g.fillRect(xPosition, yPosition, xSize, ySize);
        g.setColor(Color.GRAY);
        g.drawRect(xPosition + 2, yPosition + 2, xSize - 5, ySize - 5);
        g.setColor(infill);
        g.fillRect(xPosition + 3, yPosition + 3, xSize - 6, ySize - 6);
        g.setColor(outline);
        g.drawString("" + ID, xPosition +(int)xSize/3, yPosition +  (int) ySize - ySize/3);
        
        //xPosition -= baseRenderPointX;
        //yPosition -= baseRenderPointY;

    }
    
    
}
