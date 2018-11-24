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
import java.awt.Point;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author martin
 */
public class Render extends Canvas implements Runnable {

    
    File a =new File("nodes.data");
    File b =new File("conections.data");
    private String ClientSentence;
    private TCPServer tcp = new TCPServer(ClientSentence); 
    private Thread t1 = new Thread(tcp);
    boolean isRunning;
    public static int EstaminatedTicks = 60;
    public static int EstaminatedFPS = 10;
    public static int GridSIZE = 13;
    public static Point nullPOINT = new Point(GridSIZE + 1, GridSIZE + 1);
    protected int runtimeFPS = 0;
    protected int FPS = 0;
    protected long totalticks;
    protected int turn = 1;
    protected Point lastTestfor = nullPOINT;
    public static int X = 1;
    public static int O = 2;
    public static int TESTFOR = 3;
    MouseInput1 mouse;
    protected int[][] Grid = new int[GridSIZE][GridSIZE];
    private Point[] winner = new Point[2];
    KeyboardInput keyboard;
    boolean isWin = false;
    Node chosenOne = null;
    Node lastOne = null;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Conection> conections = new ArrayList<Conection>();
    Conection CchousenOne = null;
    Conection ClastOne = null;
    Pathfinder ptf;
    boolean Alarm = false;
    boolean AlarmClock = false;
    
    public void readData(File a,File b) throws IOException {
        try {
            ObjectInputStream oisA = new ObjectInputStream(new FileInputStream(a));
            nodes = (ArrayList<Node>) oisA.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            ObjectInputStream oisB = new ObjectInputStream(new FileInputStream(b));
            conections = (ArrayList<Conection>) oisB.readObject();
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
        for(Conection c : conections){
        System.out.println(c.getDestination().getID());
        }
        
        repair();
    }
    
        public void writeData(File a,File b) {
        try {
            ObjectOutputStream oosA = new ObjectOutputStream(new FileOutputStream(a));
            oosA.writeObject(nodes);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        try {
            ObjectOutputStream oosB = new ObjectOutputStream(new FileOutputStream(b));
            oosB.writeObject(conections);
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    public void repair(){
    for(Node n : nodes){
        
    for(Conection c: conections){
    if(c.getBase().equals(n) ){
    c.setBase(n);
    
    }
    
    if(c.getDestination().equals(n)){
    c.setDestination(n);
    }
    
    }
    
    }
    }

    public void setData(){
    conections.get(2).setCable(1);
    conections.get(7).setCable(2);
    conections.get(3).setCable(3);
   conections.get(11).setCable(4);
   conections.get(6).setCable(5);

   //conections.get(4).setFire(true);
    
    
    }    
        
    public Render() {
        this.setSize(new Dimension(800, 500));

        keyboard = new KeyboardInput();
        addKeyListener(keyboard);
        this.addKeyListener(keyboard);

        
        mouse = new MouseInput1();

        addMouseListener(mouse);

        addMouseMotionListener(mouse);

        this.addMouseListener(mouse);

        this.addMouseMotionListener(mouse);

       
        
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
  
    public void init() {
        t1.start();
        for (int[] row : Grid) {
            Arrays.fill(row, 0);
        }
        Arrays.fill(winner, nullPOINT);

        this.isRunning = true;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        long lastTimeCycle = System.nanoTime();
        long lastTimeOutput = System.currentTimeMillis();
        double unprocessedTicks = 0;
        double nsPerTick = Math.pow(10, 9) / EstaminatedTicks;

        int ticks = 0;
        double unpAlarm = 0;
        double nsPerAlrm = Math.pow(10, 9) / 4;
        double unprocessedFPS = 0;
        double nsPerFPS = Math.pow(10, 9) / EstaminatedFPS;

        while (this.isRunning) {

            // KeyBinder();
            long nowTimeCylce = System.nanoTime();
            nsPerAlrm = Math.pow(10, 9) / 4;
            nsPerTick = Math.pow(10, 9) / EstaminatedTicks;
            nsPerFPS = Math.pow(10, 9) / EstaminatedFPS;
            unprocessedTicks += (nowTimeCylce - lastTimeCycle) / nsPerTick;
            unprocessedFPS += (nowTimeCylce - lastTimeCycle) / nsPerFPS;
            unpAlarm += (nowTimeCylce - lastTimeCycle) / nsPerAlrm;
            lastTimeCycle = nowTimeCylce;

            while (unprocessedTicks >= 1) {
                ticks++;
                
                   try{ this.update();}
                   catch(IOException e){ System.out.println("catashrophe: " + e.getMessage());}
                
                mouse.poll();
                unprocessedTicks--;

            }
            
            while (unpAlarm >= 1) {
                
                
                if(Alarm)
                AlarmClock = !AlarmClock;
                   unpAlarm--;

            }
            while (unprocessedFPS >= 1) {

                unprocessedFPS--;

                FPS++;

                this.render();
                if (System.currentTimeMillis() - lastTimeOutput > 1000) {
                    lastTimeOutput += 1000;
                    System.out.println("Ticks: " + ticks + " , FPS: " + FPS + " , Toatal Ticks: " + totalticks);
                    runtimeFPS = FPS;
                    FPS = 0;
                    ticks = 0;

                }

            }

        }
    }

    

    void update() throws IOException {
        // if button pressed for first time,
        // start drawing lines
        ClientSentence = tcp.pool();
        
        if(ClientSentence != null){
            System.out.println(ClientSentence);
            int cable = Integer.parseInt(ClientSentence);
            for(Conection c : conections){
            if(c.getCable()==cable){
            c.setFire(true);
            
            }
            }
            repair();
                for(Node n: nodes){
                n.setSUM(-1);
                }
                
                
                
                
                System.out.println("P");    
                    findAndMark();
                for(Node n:nodes){
                    System.out.println(n.getID() + "  " + n.getSUM());
                }

        }
        
        Alarm = false;
        for(Conection  c : conections){
        if(c.isFire()){Alarm = true;}
        }
        
        
        keyboard.poll();
            if(chosenOne!=null && keyboard.keyDown(KeyEvent.VK_SPACE) ){
                        System.out.println("fireescapesystem.Render.update() mezera");
                        deleteNode(chosenOne);
                    }
            if(CchousenOne!=null && keyboard.keyDown(KeyEvent.VK_SPACE) ){
                        System.out.println("fireescapesystem.Render.update() mezera");
                    conections.remove(CchousenOne);
                    }
            if(CchousenOne!=null && keyboard.keyDownOnce(KeyEvent.VK_W) ){
                        System.out.println("fireescapesystem.Render.update() mezera");
                    CchousenOne.setWayB2D(!CchousenOne.isWayB2D());
                    }
            
            if(chosenOne!=null && keyboard.keyDownOnce(KeyEvent.VK_E) ){
                        System.out.println("fireescapesystem.Render.update() E");
                        chosenOne.setIsExit(!chosenOne.isIsExit());
                    }
            if(CchousenOne!=null && keyboard.keyDownOnce(KeyEvent.VK_E) ){
                        System.out.println("fireescapesystem.Render.update() E");
                        CchousenOne.setFire(!CchousenOne.isFire());
                    }
            
            if(keyboard.keyDownOnce(KeyEvent.VK_P) ){
                repair();
                for(Node n: nodes){
                n.setSUM(-1);
                }
                
                
                
                
                System.out.println("P");    
                    findAndMark();
                for(Node n:nodes){
                    System.out.println(n.getID() + "  " + n.getSUM());
                }
                    }
            
            if(keyboard.keyDownOnce(KeyEvent.VK_S) ){
                System.out.println("S");    
                writeData(a, b);
                
            }
            if(keyboard.keyDownOnce(KeyEvent.VK_O) ){
                System.out.println("O");    
                try{readData(a, b);}
                catch(IOException e){System.err.println(e.getMessage());}
                
            }
            
            if(keyboard.keyDownOnce(KeyEvent.VK_K) ){
                System.out.println("K");    
                setData();
                
            }

            
        if (mouse.buttonDownOnce(1)) {
            
            Point tmp = null;
            if (10 < mouse.getPosition().getX() && mouse.getPosition().getX() < this.getWidth() - 21) {
                if (110 < mouse.getPosition().getY() && mouse.getPosition().getY() < this.getHeight() - 21) {
                    System.out.println("Mouse Position- X: " + mouse.getPosition().getX() + "  Y: " + mouse.getPosition().getY());
                    /*
                    tmp = returnPoint(mouse.getPosition());
                    if (tmp == nullPOINT) {
                    } else {
                        if (tmp.equals(lastTestfor) && Grid[(int) lastTestfor.getX()][(int) lastTestfor.getY()] == TESTFOR) {
                         //   Grid[(int) tmp.getX()][(int) tmp.getY()] = switchTurn();
                            System.err.println("tmp - X: " + (int) tmp.getX() + "  Y: " + (int) tmp.getY());
                            System.err.println("GRID: " + Grid[(int) tmp.getX()][(int) tmp.getY()]);
                            tmp = nullPOINT;
                        }
                        if (tmp != nullPOINT && tmp != lastTestfor) {
                            Grid[(int) tmp.getX()][(int) tmp.getY()] = TESTFOR;
                            if (lastTestfor.getX() != nullPOINT.getX() && Grid[(int) lastTestfor.getX()][(int) lastTestfor.getY()] == TESTFOR) {
                                Grid[(int) lastTestfor.getX()][(int) lastTestfor.getY()] = 0;
                            }
                            lastTestfor = tmp;
                            System.out.pri ntln("lastTestfor - X: " + lastTestfor.getX() + "  Y: " + lastTestfor.getY());
                            System.out.println("tmp - X: " + tmp.getX() + "  Y: " + tmp.getY());
                        }

                    }*/
                    boolean choice = false;
                    boolean Cchoice = false;
                    boolean DONT = false;
                    lastOne = chosenOne;
                    for(Node n: nodes){
                    if(n.getxPosition()<(int)mouse.getPosition().getX() && (int)mouse.getPosition().getX()<n.getxPosition() + Node.xSize )
                    {if(n.getyPosition()<(int)mouse.getPosition().getY() && (int)mouse.getPosition().getY()<n.getyPosition() + Node.ySize ){
                        if(chosenOne == n){DONT = true;}
                        if(chosenOne!=null)chosenOne.setChosen(false);
                        if(n==lastOne) chosenOne = null;
                        else{
                            lastOne = chosenOne;
                            chosenOne = n;}
                       try{
                        CchousenOne.setChosen(false);
                        CchousenOne = null;}
                        catch(NullPointerException e){System.err.println(e.getMessage());}
                        choice = true;
                    }}    
                    }
                    if(chosenOne!=null)chosenOne.setChosen(true);
                    if(!DONT && choice && lastOne!=null && chosenOne != lastOne){
                    conections.add(new Conection(lastOne,chosenOne, 2.0));
                    System.out.println("New conection :" + conections.size());
                    }
                    System.out.println(choice);
                   
                    if(!choice){
                    
                    for(Conection c : conections){
                     
                    if((c.getHorizontalXPosition()<(int)mouse.getPosition().getX() && (int)mouse.getPosition().getX()<c.getHorizontalXPosition() + c.getHorizontalXSize() ) && (c.getHorizontalYPosition()<(int)mouse.getPosition().getY() && (int)mouse.getPosition().getY()<c.getHorizontalYPosition() + c.getHorizontalYSize() ) ||( (c.getVerticalXPosition() <(int)mouse.getPosition().getX() && (int)mouse.getPosition().getX()<c.getVerticalXPosition() + c.getVerticalXSize()  ) && (c.getVerticalYPosition() <(int)mouse.getPosition().getY() && (int)mouse.getPosition().getY()<c.getVerticalYPosition() + c.getVerticalYSize()  )))
                    //if( || )
                    {
                        try{
                        CchousenOne.setChosen(false);
                        CchousenOne = null;}
                        catch(NullPointerException e){System.err.println(e.getMessage());}
                        
                        Cchoice = true;
                        try{
                        chosenOne.setChosen(false);
                        chosenOne = null;}
                        catch(NullPointerException e){System.err.println(e.getMessage());}
                    CchousenOne = c;
                    c.setChosen(true);
                    
                        
                        System.out.println("fireescapesystem.Render.update() conc");
                    }

                    
                    }
                    
                    }
                    
                    if((!choice) && chosenOne == null && CchousenOne == null)
                    {nodes.add(new Node((int)mouse.getPosition().getX()-Node.xSize/2,(int) mouse.getPosition().getY()-Node.ySize/2, nodes.size()+1));
                    System.out.println("fireescapesystem.Render.update() :" + nodes.size());}
                    
                    if((!choice) && chosenOne!=null  ){
                        
                        try{
                        chosenOne.setChosen(false);
                        chosenOne = null;}
                        catch(NullPointerException e){System.err.println(e.getMessage());}
                        
                    choice = false;
                    }
                    
                    if(!Cchoice && CchousenOne != null){
                    try{
                        CchousenOne.setChosen(false);
                        CchousenOne = null;}
                        catch(NullPointerException e){System.err.println(e.getMessage());}
                        
                    }
                                        
                       
                        
                    
                    
                }
         //   isWin = testWin();
            }
        }

    }
    
    void deleteNode(Node n){
    ArrayList<Conection> tmp = new ArrayList<Conection>();
    //tmp.addAll(conections);
    for(Conection c : conections){
    if(c.getBase() == n || c.getDestination() == n){
    tmp.add(c);
    System.out.println(c.getBase().getID() +  "-" + c.getDestination().getID() );
    }
    }
    conections.removeAll(tmp);
    nodes.remove(n);
    chosenOne = null;
    }

    Point returnPoint(Point base
    ) {
        int gridSize = this.getWidth() - 22;
        int sqrSize = (int) (gridSize * 0.8) / GridSIZE;
        int sqrSpace = (int) (gridSize * 0.1) / GridSIZE;
        int x = 13;

        for (int xP = 0; xP < GridSIZE; xP++) {
            int y = 113;
            x += sqrSpace;

            for (int yP = 0; yP < GridSIZE; yP++) {
                y += sqrSpace;
                if ((x < base.getX() && base.getX() < x + sqrSize) && (y < base.getY() && base.getY() < y + sqrSize)) {
                    return new Point(xP, yP);
                }
                y += sqrSize + sqrSpace;
            }
            x += sqrSize + sqrSpace;
        }

        return nullPOINT;
    }

    void render() {
        BufferStrategy buffer = this.getBufferStrategy();
        if (buffer == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = buffer.getDrawGraphics();
        
        if(AlarmClock)g.setColor(Color.white);
        else{g.setColor(Color.red);}
        
        if(!Alarm){g.setColor(Color.white);}
        
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.gray);
        g.fillRect(0, 0, this.getWidth(), 100);
        g.setColor(Color.orange);
        g.drawString("FPS: " + runtimeFPS, 20, 20);
        g.drawString("FPS realtime calculation: " + FPS, 20, 40);
        
        g.setColor(Color.GRAY);
        g.fillRect(10, 110, this.getWidth() - 21, this.getHeight() - 121);
        
        for(Conection c : conections){
        c.Render(g);
        }
        
        for (Node n : this.nodes) {
            n.Render(g);
        }
        
        
        
        /*g.setColor(Color.WHITE);
        int gridSize = this.getWidth() - 22;
        int sqrSize = (int) (gridSize * 0.8) / GridSIZE;
        int sqrSpace = (int) (gridSize * 0.1) / GridSIZE;
        int x = 13;
        for (int xP = 0; xP < GridSIZE; xP++) {
            int y = 113;
            x += sqrSpace;

            for (int yP = 0; yP < GridSIZE; yP++) {
                y += sqrSpace;
                if (Grid[xP][yP] == TESTFOR) {
                    g.setColor(Color.yellow);
                }
                if (Grid[xP][yP] == 0) {
                    g.setColor(Color.white);
                }
                if (Grid[xP][yP] == X) {
                    g.setColor(Color.red);
                }
                if (Grid[xP][yP] == O) {
                    g.setColor(Color.blue);
                }

                g.fillRect(x, y, sqrSize, sqrSize);
                g.setColor(Color.DARK_GRAY);
                //g.drawString("" + Grid[xP][yP], x, y);
                y += sqrSize + sqrSpace;
            }
            x += sqrSize + sqrSpace;
        }

        if (isWin) {
            g.setColor(Color.CYAN);

            Point frst = nullPOINT;
            Point scnd = nullPOINT;
            x = 13;
            for (int xP = 0; xP < GridSIZE; xP++) {
                int y = 113;
                x += sqrSpace;

                for (int yP = 0; yP < GridSIZE; yP++) {
                    y += sqrSpace;
                    if (winner[0].equals(new Point(xP, yP))) {
                        x += 1;
                        y += 1;
                        frst = new Point(x, y);
                    }
                    y += sqrSize + sqrSpace;
                }
                x += sqrSize + sqrSpace;
            }
            x = 13;
            for (int xP = 0; xP < GridSIZE; xP++) {
                int y = 113;
                x += sqrSpace;

                for (int yP = 0; yP < GridSIZE; yP++) {
                    y += sqrSpace;
                    if (winner[1].equals(new Point(xP, yP))) {
                        y += sqrSize + sqrSpace;
                        x += sqrSize + sqrSpace;
                        y -= 3;
                        x -= 3;
                        scnd = new Point(x, y);
                    }
                    y += sqrSize + sqrSpace;
                }
                x += sqrSize + sqrSpace;
            }

            g.drawLine((int) (frst.getX() + sqrSize / 2), (int) (frst.getY() + sqrSize / 2), (int) (scnd.getX() - sqrSize / 2), (int) (scnd.getY() - sqrSize / 2));
        }*/
//g.fillPolygon(x,y,4);
        g.dispose();
        buffer.show();

    
    
    }
    
    
    
    
    
       public void seek(Node a) {

        
        for (Conection c : conections) {
            if (c.isFire() == false &&(c.getBase().equals(a) || c.getDestination().equals(a))) {
                //boolean isBase = !c.getBase().equals(a);
                for(Node n : nodes){
                
                    if(n.equals(c.getBase())){
                    
                    
                        if(((n.isIsExit() == false) && (n.getSUM()> a.getSUM() + c.getLength())) || (n.isIsExit() == false) && n.getSUM()==-1){
         n.setSUM(a.getSUM() + c.getLength());
         if(c.getBase().equals(a)){
         c.setWayB2D(false);
         }else{c.setWayB2D(true);}
         seek(n);
         }
                    
                    }
                    
                    
                    if(n.equals(c.getDestination())){
                    
                             if(((n.isIsExit() == false) && (n.getSUM()> a.getSUM() + c.getLength())) || (n.isIsExit() == false) && n.getSUM()==-1){
         n.setSUM(a.getSUM() + c.getLength());
         if(c.getBase().equals(a)){
         c.setWayB2D(false);
         }else{c.setWayB2D(true);}
         seek(n);
         }
                    }
                    
                }
                
                
                

            }

        }
        
         
            
        
        }

    
    
    public void findAndMark() {
        
        for (Node n : nodes) {
            if (n.isIsExit()) {
                seek(n);
                repair();
            }
        }

        
    }

}
 
/*
public void seek(Node a) {
        System.out.println("Actual: " + a.getID());
        ArrayList<Conection> valid = new ArrayList<Conection>();
        for (Conection c : conections) {
            if (c.getBase().equals(a) || a.equals(c.getDestination())) {
                valid.add(c);
                
            }

        }
        
       
        
        for(Conection c : conections){
        
            
            if(valid.contains(c)){
            int tmp = 0;
            
        //c.getDestination().setSUM(a.getSUM() + c.getLength());
        
        if(a.equals(c.getBase())){
        for(Node n: nodes){
        if(n.equals(c.getDestination())){tmp = nodes.lastIndexOf(n);}
        }
        }
        
         if(a.equals(c.getDestination())){
        for(Node n: nodes){
        if(n.equals(c.getBase())){tmp = nodes.lastIndexOf(n);}
        }
        }
         
                System.out.println(tmp + "");
        
                
         if(nodes.get(tmp).isIsExit() == false && nodes.get(tmp).getSUM()> a.getSUM() + c.getLength() || nodes.get(tmp).isIsExit() == false && nodes.get(tmp).getSUM()==-1)
             {nodes.get(WIDTH) .setSUM(a.getSUM() + c.getLength());
             
             
             
             if(a.equals(c.getBase())){
         c.setWayB2D(false);
         
         }
         for(Conection d : conections){
         if(d.getBase().equals(tmp)){
             System.out.println("FCdC" +nodes.get(tmp).getID());
             d.setBase(nodes.get(tmp));
         }
         if(d.getDestination().equals(tmp)){
         System.out.println("FDdD" + nodes.get(tmp).getID());
             d.setDestination(nodes.get(tmp));
         
             
         }
         seek(nodes.get(tmp));
         
             
            
         }
            
        }
        }}}
    
    
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
*/
