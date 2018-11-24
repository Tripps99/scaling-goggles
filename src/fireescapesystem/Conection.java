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
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author martin
 */
public class Conection implements Serializable{
    private Node destination;
    private Node base;
    private double length;
    private boolean chosen;
    private boolean fire = false;
    private boolean wayB2D = true;
    
    private int verticalXPosition;
    private int verticalYPosition;
    private int verticalXSize;
    private int verticalYSize;
    private int horizontalXPosition;
    private int horizontalYPosition;
    private int horizontalXSize;
    private int horizontalYSize;

    public boolean isWayB2D() {
        return wayB2D;
    }

    public void setWayB2D(boolean wayB2D) {
        this.wayB2D = wayB2D;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.destination);
        hash = 83 * hash + Objects.hashCode(this.base);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.length) ^ (Double.doubleToLongBits(this.length) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conection other = (Conection) obj;
        if (Double.doubleToLongBits(this.length) != Double.doubleToLongBits(other.length)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        if (!Objects.equals(this.base, other.base)) {
            return false;
        }
        return true;
    }

    
    
    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }
 
    
    
    public Node getBase() {
        return base;
    }

    public void setBase(Node base) {
        this.base = base;
    }

    public int getVerticalXPosition() {
        return verticalXPosition;
    }

    public void setVerticalXPosition(int verticalXPosition) {
        this.verticalXPosition = verticalXPosition;
    }

    public int getVerticalYPosition() {
        return verticalYPosition;
    }

    public void setVerticalYPosition(int verticalYPosition) {
        this.verticalYPosition = verticalYPosition;
    }

    public int getVerticalXSize() {
        return verticalXSize;
    }

    public void setVerticalXSize(int verticalXSize) {
        this.verticalXSize = verticalXSize;
    }

    public int getVerticalYSize() {
        return verticalYSize;
    }

    public void setVerticalYSize(int verticalYSize) {
        this.verticalYSize = verticalYSize;
    }

    public int getHorizontalXPosition() {
        return horizontalXPosition;
    }

    public void setHorizontalXPosition(int horizontalXPosition) {
        this.horizontalXPosition = horizontalXPosition;
    }

    public int getHorizontalYPosition() {
        return horizontalYPosition;
    }

    public void setHorizontalYPosition(int horizontalYPosition) {
        this.horizontalYPosition = horizontalYPosition;
    }

    public int getHorizontalXSize() {
        return horizontalXSize;
    }

    public void setHorizontalXSize(int horizontalXSize) {
        this.horizontalXSize = horizontalXSize;
    }

    public int getHorizontalYSize() {
        return horizontalYSize;
    }

    public void setHorizontalYSize(int horizontalYSize) {
        this.horizontalYSize = horizontalYSize;
    }
    
    
    
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
        this.verticalYSize = 1*destination.getyPosition() - base.getyPosition() - Node.ySize/4 ;
        
           }
        else{
            
        this.verticalXPosition = destination.getxPosition() + Node.xSize/4;
        this.verticalYPosition = destination.getyPosition() + Node.ySize ;
        this.verticalXSize = 2* Node.xSize/4 ;
        this.verticalYSize = -1*destination.getyPosition() + base.getyPosition() - Node.ySize/2  ;
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
    
    
    public void Render(Graphics g) {
        //xPosition += baseRenderPointX;
        //yPosition += baseRenderPointY;
        Color outline;
        Color infill;
        
        
        if(fire){
       outline = Color.RED;
        infill = Color.BLACK;
        
        g.setColor(outline);
        g.fillRect(horizontalXPosition, horizontalYPosition, horizontalXSize, horizontalYSize);
        g.fillRect(verticalXPosition, verticalYPosition, verticalXSize, verticalYSize);     
        
        g.setColor(infill);
        g.fillRect(horizontalXPosition+2, horizontalYPosition+2, horizontalXSize-4, horizontalYSize-4);
        g.fillRect(verticalXPosition+2, verticalYPosition+2, verticalXSize-4, verticalYSize-4);     
        int hpos = horizontalXPosition;
        while(hpos + horizontalYSize < horizontalXPosition + horizontalXSize){
        
            g.setColor(outline);
        g.drawLine(hpos, horizontalYPosition, hpos + horizontalYSize, horizontalYPosition+horizontalYSize);
        g.drawLine(hpos, horizontalYPosition+horizontalYSize  , hpos + horizontalYSize, horizontalYPosition);
        hpos +=horizontalYSize;
        }
        int vpos = verticalYPosition;
        while(vpos + verticalXSize < verticalYPosition + verticalYSize){
        g.setColor(outline);
        g.drawLine(verticalXPosition, vpos, verticalXPosition + verticalXSize,vpos + verticalXSize);
        g.drawLine(verticalXPosition+verticalXSize, vpos  ,  verticalXPosition,vpos + verticalXSize);
        vpos +=verticalXSize;
        }
        
        }
        else{
        
        
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
        
        boolean positive;
        positive =  base.getxPosition()<destination.getxPosition();
        if(!wayB2D){
        positive = !positive ;
        }
        
        int swap;
        int hspace = horizontalXSize -1;
        if(positive){swap = 0;}
        else{swap = Node.xSize;  }
        g.setColor(outline );
        while(hspace>Node.xSize/2){
        int xPoints[] = new int[3];
        int yPoints[] = new int[3];
        xPoints[0] = horizontalXPosition + hspace - Node.xSize/2;
        xPoints[1] = horizontalXPosition + hspace - Node.xSize/2;
        xPoints[2] = horizontalXPosition + hspace - swap;
        yPoints[0] = horizontalYPosition;
        yPoints[1] = horizontalYPosition + horizontalYSize -1;
        yPoints[2] = horizontalYPosition + horizontalYSize/2;
        //g.setColor(Color.BLUE);
        if(!(swap != 0 && hspace + swap < Node.xSize + 5))
        g.drawPolygon(xPoints, yPoints, 3);
        hspace -= Node.xSize/2 + 5;
        }
        
        positive =  base.getyPosition()<destination.getyPosition();
        if(!wayB2D){
        positive = !positive ;
        }
        
        int vspace = verticalYSize-1;
        if(positive){swap = 0;}
        else{swap = Node.ySize; }
        g.setColor(outline );
        while(vspace>Node.ySize/2){
        int xPoints[] = new int[3];
        int yPoints[] = new int[3];
        yPoints[0] = verticalYPosition + vspace - Node.ySize/2;
        yPoints[1] = verticalYPosition + vspace - Node.ySize/2;
        yPoints[2] = verticalYPosition + vspace - swap;
        xPoints[0] = verticalXPosition;
        xPoints[1] = verticalXPosition + verticalXSize -1 ;
        xPoints[2] = verticalXPosition + verticalXSize/2;
        //g.setColor(Color.BLUE);
        
        if(!(swap != 0 && vspace < Node.ySize +5 ))
        g.drawPolygon(xPoints, yPoints, 3);
        vspace -= Node.ySize/2 + 5;
        } 
        //xPosition -= baseRenderPointX;
        //yPosition -= baseRenderPointY;
                }
    }}
    

