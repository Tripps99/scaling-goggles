/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireescapesystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author martin
 */
public class Node implements Serializable{
    private int xPosition;
    private int yPosition;
    private int ID;
    private double SUM = -1;
    private boolean chosen;
    private boolean isExit = false;
    public static final int xSize = 32;
    public static final int ySize = 32;

    public boolean isIsExit() {
        return isExit;
    }

    public void setIsExit(boolean isExit) {
        this.isExit = isExit;
    }

    public double getSUM() {
        return SUM;
    }

    public void setSUM(double SUM) {
        this.SUM = SUM;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.xPosition;
        hash = 67 * hash + this.yPosition;
        hash = 67 * hash + this.ID;
     
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
        final Node other = (Node) obj;
        if (this.xPosition != other.xPosition) {
            return false;
        }
        if (this.yPosition != other.yPosition) {
            return false;
        }
        if (this.ID != other.ID) {
            return false;
        }
        
        return true;
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
        
        
        
        
        if(chosen){
        outline = Color.ORANGE;
        infill = Color.red;
        }else{
        outline = Color.green;
        infill = Color.darkGray;
        
        }
        
        if(isExit){
        outline = Color.WHITE;
        }
            
        g.setColor(outline);
        g.fillRect(xPosition, yPosition, xSize, ySize);
        g.setColor(Color.GRAY);
        g.drawRect(xPosition + 2, yPosition + 2, xSize - 5, ySize - 5);
        g.setColor(infill);
        g.fillRect(xPosition + 3, yPosition + 3, xSize - 6, ySize - 6);
        g.setColor(outline);
        if(isExit){
        Font f = g.getFont();
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
        g.drawString("E ", xPosition +(int)xSize/5, yPosition +  (int) ySize - 2*ySize/3);
        g.drawString(" " + ID, xPosition +(int)xSize/5, yPosition +  (int) ySize - ySize/3);
        g.setFont(f);
        }else
        g.drawString("" + ID, xPosition +(int)xSize/3, yPosition +  (int) ySize - ySize/3);
        
        //xPosition -= baseRenderPointX;
        //yPosition -= baseRenderPointY;

    }
    
    
}
