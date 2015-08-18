import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Write a description of class GamePanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GamePanel
{
    // instance variables - replace the example below with your own
    private JFrame frame;
    private ImagePanel counter;
    private ImagePanel direction;
    private ImagePanel part;
    private String[] partNames = {"hand.jpg", "leg.jpg"};
    private String[] directionNames = {"left.jpg", "right.jpg"};
    private int counterNum = 3;
    Timer timer;
    boolean startClicked = false;
    JButton start;
    JButton next;
    JPanel guessBoard;
    
    public static void main(String[] args) {
        GamePanel game = new GamePanel();
        game.setupGUI();
        //gameOn();        
    }
    
    
    private void setupGUI() {
        frame = new JFrame("Left or Right"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        start = new JButton("START");
        start.addActionListener(new StartListener());
        
        frame.getContentPane().add(BorderLayout.CENTER, start);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
    
    
    private void gameOn() {
        frame.getContentPane().removeAll();
        Random random = new Random();
        int d = random.nextInt(directionNames.length);
        int p = random.nextInt(partNames.length);
        //System.out.println(d + " " + p);
        
        direction = new ImagePanel(directionNames[d], 400, 600);
        part = new ImagePanel(partNames[p],400, 600);
        
        guessBoard = new JPanel();
        guessBoard.setLayout(new GridLayout(1,2));
        guessBoard.add(direction);
        guessBoard.add(part);        
        
        next = new JButton("NEXT");
        next.addActionListener(new StartListener());
        
        frame.getContentPane().add(BorderLayout.CENTER, guessBoard);
        frame.getContentPane().add(BorderLayout.SOUTH, next);
        frame.revalidate();
        frame.repaint();
        counterNum = 3;
        startClicked = false;
    }
    
    
    private class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            startClicked = true;
            frame.getContentPane().removeAll();
            counter = new ImagePanel(counterNum + ".jpg", 800, 600);
            frame.getContentPane().add(BorderLayout.CENTER, counter);
            frame.validate();
            timer = new Timer(1000, new TimerListener());
            timer.start();
        }
    }
    
    
    
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (startClicked) {
                frame.getContentPane().removeAll();
                //System.out.println("action");
                counterNum --;
                counter = new ImagePanel(counterNum + ".jpg", 800, 600);
                frame.getContentPane().add(BorderLayout.CENTER, counter);
                frame.validate();
                
                if (counterNum < 1) { 
                    timer.stop();
                    gameOn();
                }
            }
        }
    }
    
    
    private class ImagePanel extends JPanel {
        
        private String file;
        private int width;
        private int height;
        
        ImagePanel (String file, int width, int height) {
            this.file = file;
            this.width = width;
            this.height = height;
        }
        
        public void paintComponent(Graphics g) {
            Image img = new ImageIcon(file).getImage();
            g.drawImage(img, 3, 4, width, height, this);
        }
    }
    
    
}
