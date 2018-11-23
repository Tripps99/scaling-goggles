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
    private Node base;
    private double length;
    private boolean chosen;
    private int verticalXPosition;
    private int verticalYPosition;
    private int verticalXSize;
    private int verticalYSize;
    private int horizontalXPosition;
    private int horizontalYPosition;
    private int horizontalXSize;
    private int horizontalYSize;
    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
    
    
    public Conection(Node base,Node destination, double length) {
        this.destination = destination;
        this.length = length;
        this.base = base;
       try{
        if(base.getxPosition()<destination.getxPosition()){
        this.horizontalXPosition = base.getxPosition() + Node.xSize;
        this.horizontalYPosition = base.getyPosition() + Node.ySize/4;
        this.horizontalXSize = -1*base.getxPosition() + destination.getxPosition() - Node.xSize/4 ;
        this.horizontalYSize  = 2*Node.ySize/4;
        }else{
        this.horizontalXPosition = destination.getxPosition() + Node.xSize/4;
        this.horizontalYPosition = base.getyPosition() + Node.ySize/4;
        this.horizontalXSize = base.getxPosition() - destination.getxPosition() - Node.xSize/4 ;
        this.horizontalYSize  = 2*Node.ySize/4;
        }
        
        if(base.getyPosition()<destination.getyPosition()){
        this.verticalXPosition = destination.getxPosition() + Node.xSize/4;
        this.verticalYPosition = base.getyPosition() + Node.ySize/4 ;
        this.verticalXSize = 2* Node.xSize/4 ;
        this.verticalYSize = 1*destination.getyPosition() - base.getyPosition();
        
           }
        else{
            
        this.verticalXPosition = destination.getxPosition() + Node.xSize/4;
        this.verticalYPosition = destination.getyPosition() + Node.ySize ;
        this.verticalXSize = 2* Node.xSize/4 ;
        this.verticalYSize = -1*destination.getyPosition() + base.getyPosition() - Node.ySize /2 ;
        }
       } catch(NullPointerException e){
           System.err.println(e.getMessage());
       }
        
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
        
        g.setColor(outline);
        g.fillRect(horizontalXPosition, horizontalYPosition, horizontalXSize, horizontalYSize);
        g.fillRect(verticalXPosition, verticalYPosition, verticalXSize, verticalYSize);     
        
        g.setColor(infill);
        g.fillRect(horizontalXPosition+2, horizontalYPosition+2, horizontalXSize-4, horizontalYSize-4);
        g.fillRect(verticalXPosition+2, verticalYPosition+2, verticalXSize-4, verticalYSize-4);     
        }    
        
        
        //xPosition -= baseRenderPointX;
        //yPosition -= baseRenderPointY;

    }
    

