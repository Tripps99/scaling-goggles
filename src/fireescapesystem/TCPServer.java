/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fireescapesystem;

/**
 *
 * @author martin
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;




class TCPServer implements Runnable{
 
    
  private boolean smtnew = false;  
  private String clientSentence;
  private String capitalizedSentence;
  private ServerSocket welcomeSocket;

    public TCPServer(String clientSentence) {
        this.clientSentence = clientSentence;
        
      try {
          this.welcomeSocket = new ServerSocket(6789);
      } catch (IOException ex) {
          Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
      }
        System.out.println("Online");
    }
  
  
   
   public String pool(){
   if(smtnew == true){
   smtnew = false;
   return clientSentence;
   }
   return null;
   
   
   }
 

    @Override
    public void run() {
        System.out.println("Running"); 
       while(true){
        try{ Socket connectionSocket = welcomeSocket.accept();
   BufferedReader inFromClient =
    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
   DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
   smtnew = true;
   clientSentence = inFromClient.readLine();
   System.out.println("Received: " + clientSentence);
   capitalizedSentence = clientSentence.toUpperCase() + 'n';
   outToClient.writeBytes(capitalizedSentence);
    } catch(IOException e){System.err.println(e.getMessage());}
       
    }
    }
 }


