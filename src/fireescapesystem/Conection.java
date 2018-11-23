/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireescapesystem;

import static fireescapesystem.Node.xSize;
import static fireescapesystem.Node.ySize;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author martin
 */
public class Conection {
    private Node destination;
    private double length;
    private boolean chosen;

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
    
    
    public Conection(Node destination, double length) {
        this.destination = destination;
        this.length = length;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
    
    
    public void Render(Graphics g, Node base) {
        //xPosition += baseRenderPointX;
        //yPosition += baseRenderPointY;
        Color outline;
        Color infill;
        
        if(chosen){
        outline = Color.ORANGE;
        infill = Color.red;
        }else{
        outline = Color.green;
        infill = Color.darkGray;
        
        }
        if(base.getxPosition()<destination.getxPosition()){
        g.setColor(outline);
        g.fillRect(base.getxPosition() + Node.xSize, base.getyPosition() + Node.ySize/4 , destination.getxPosition() + 3*Node.xSize/4, base.getyPosition() + 3*Node.ySize/4);
        g.setColor(infill);
        g.fillRect(base.getxPosition() + Node.xSize, 2 + base.getyPosition() + Node.ySize/4 , destination.getxPosition() + 3*Node.xSize/4, -2 + base.getyPosition() + 3*Node.ySize/4);
        }    
        
        
        //xPosition -= baseRenderPointX;
        //yPosition -= baseRenderPointY;

    }
    
}
