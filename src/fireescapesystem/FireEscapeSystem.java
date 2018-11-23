/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireescapesystem;

import java.awt.Color;
import javax.swing.JFrame;
/**
 *
 * @author martin
 */
public class FireEscapeSystem extends JFrame{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FireEscapeSystem core = new FireEscapeSystem();
        core.init();
    }
    
    public void init(){
    Render coreR = new Render();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setVisible(true);
    this.add(coreR);
    this.pack();
    this.setTitle("FireEscapeSystem");
    this.setResizable(false);
    coreR.init();
    
        
        
    }
    
}
