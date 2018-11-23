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
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author martin
 */
public class Render extends Canvas implements Runnable {

    
    
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

    public Render() {
        this.setSize(new Dimension(800, 500));

        mouse = new MouseInput1();

        addMouseListener(mouse);

        addMouseMotionListener(mouse);

        this.addMouseListener(mouse);

        this.addMouseMotionListener(mouse);

    }

    public void init() {
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

        double unprocessedFPS = 0;
        double nsPerFPS = Math.pow(10, 9) / EstaminatedFPS;

        while (this.isRunning) {

            // KeyBinder();
            long nowTimeCylce = System.nanoTime();

            nsPerTick = Math.pow(10, 9) / EstaminatedTicks;
            nsPerFPS = Math.pow(10, 9) / EstaminatedFPS;
            unprocessedTicks += (nowTimeCylce - lastTimeCycle) / nsPerTick;
            unprocessedFPS += (nowTimeCylce - lastTimeCycle) / nsPerFPS;
            lastTimeCycle = nowTimeCylce;

            while (unprocessedTicks >= 1) {
                ticks++;
                
                    this.update();
                
                mouse.poll();
                unprocessedTicks--;

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

    

    void update() {
        // if button pressed for first time,
        // start drawing lines
        
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
                    lastOne = chosenOne;
                    for(Node n: nodes){
                    if(n.getxPosition()<(int)mouse.getPosition().getX() && (int)mouse.getPosition().getX()<n.getxPosition() + Node.xSize )
                    {if(n.getyPosition()<(int)mouse.getPosition().getY() && (int)mouse.getPosition().getY()<n.getyPosition() + Node.ySize ){
                        if(chosenOne!=null)chosenOne.setChosen(false);
                        if(n==lastOne) chosenOne = null;
                        else{
                            lastOne = chosenOne;
                            chosenOne = n;}
                        
                        choice = true;
                    }}    
                    }
                    if(chosenOne!=null)chosenOne.setChosen(true);
                    if(choice){
                    lastOne.getConnections();
                    
                    }
                    if(!choice)
                    {nodes.add(new Node((int)mouse.getPosition().getX()-Node.xSize/2,(int) mouse.getPosition().getY()-Node.ySize/2, nodes.size()+1));
                    System.out.println("fireescapesystem.Render.update() :" + nodes.size());}
                }
         //   isWin = testWin();
            }
        }

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
        
        
        
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.getWidth(), 100);
        g.setColor(Color.orange);
        g.drawString("FPS: " + runtimeFPS, 20, 20);
        g.drawString("FPS realtime calculation: " + FPS, 20, 40);
        
        g.setColor(Color.GRAY);
        g.fillRect(10, 110, this.getWidth() - 21, this.getHeight() - 121);
        
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

}

