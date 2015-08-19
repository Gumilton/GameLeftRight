import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

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
    private JTextArea counter;
    private ArrayList<String> partNames = new ArrayList<String>();
    private ArrayList<String> directionNames = new ArrayList<String>();
    private int counterNum = 3;
    Timer timer;
    boolean startClicked = false;
    JButton start;
    JButton next;
    JPanel guessBoard;
    JTextField userDirect;
    JTextField userPart;
    JCheckBox check = new JCheckBox("方向相反");
    JCheckBox auto = new JCheckBox("自动");
    Font insFont = new Font("小篆", Font.PLAIN, 80);
    Font numberFont = new Font("Arial", Font.BOLD, 600); 
    Font wordFont = new Font("小篆", Font.BOLD, 250);
    Timer autoTimer;
    
    public static void main(String[] args) {
        GamePanel game = new GamePanel();
        game.frame = new JFrame("Left or Right"); 
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game.setupGUI();
        //gameOn();        
    }
    
    
    private void setupGUI() {
        JMenuBar menuBar = new JMenuBar();
        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(new ReStartListener());
        menuBar.add(restart);
        frame.setJMenuBar(menuBar);
        
        start = new JButton("START");
        start.addActionListener(new StartListener());
        start.setFont(insFont);
        
        guessBoard = new JPanel();
        guessBoard.setLayout(new GridBagLayout());
        guessBoard.setBackground(Color.WHITE);
        
        userDirect = new JTextField(20);
        userDirect.setText("左；右");
        userDirect.setFont(insFont);
        userPart = new JTextField(20);
        userPart.setText("手；脚");
        userPart.setFont(insFont);
        
        JLabel userDirectIns = new JLabel("输入方向");
        userDirectIns.setFont(insFont);
        JLabel userPartIns = new JLabel("输入动作");
        userPartIns.setFont(insFont);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        guessBoard.add(userDirectIns, c);
        
        c.gridx = 0;
        c.gridy = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        guessBoard.add(userDirect, c);
        
        
        c.gridx = 0;
        c.gridy = 20;
        c.fill = GridBagConstraints.HORIZONTAL;
        guessBoard.add(userPartIns, c);
        
        
        c.gridx = 0;
        c.gridy = 30;
        c.fill = GridBagConstraints.HORIZONTAL;
        guessBoard.add(userPart, c);
        
        check.setSelected(true);
        check.setFont(insFont);
        c.gridx = 0;
        c.gridy = 40;
        c.fill = GridBagConstraints.HORIZONTAL;
        guessBoard.add(check, c);
        
        auto.setSelected(true);
        auto.setFont(insFont);
        c.gridx = 0;
        c.gridy = 60;
        c.fill = GridBagConstraints.HORIZONTAL;
        guessBoard.add(auto, c);
        
        frame.getContentPane().add(BorderLayout.CENTER, guessBoard);
        frame.getContentPane().add(BorderLayout.SOUTH, start);
        frame.setSize(1300,800);
        frame.setVisible(true);
    }
    
    
    private void gameOn() {
        frame.getContentPane().removeAll();
        frame.getContentPane().setBackground(Color.WHITE);
        Random random = new Random();
        int d = random.nextInt(directionNames.size());
        int p = random.nextInt(partNames.size());
        int op = random.nextInt(2);
        //System.out.println(d + " " + p);
        
        
        JTextArea direction = new JTextArea(directionNames.get(d));
        direction.setFont(wordFont);
        direction.setEditable(false);
        JTextArea part = new JTextArea(partNames.get(p));
        part.setFont(wordFont);
        part.setEditable(false);
        
        guessBoard = new JPanel();
        guessBoard.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        
        if(check.isSelected()){
            String[] oppositeNames = {"真的", "反的"};
            JTextArea opposite = new JTextArea(oppositeNames[op]);
            opposite.setFont(wordFont);
            opposite.setEditable(false);
            guessBoard.add(opposite, c);
        }
        
        guessBoard.add(direction,c);
        guessBoard.add(part, c); 
        
        next = new JButton("NEXT");
        next.addActionListener(new NextListener());
        next.setFont(insFont);
        
        guessBoard.setBackground(Color.WHITE);
        frame.getContentPane().add(BorderLayout.CENTER, guessBoard);
        frame.getContentPane().add(BorderLayout.SOUTH, next);
        frame.revalidate();
        frame.repaint();
        counterNum = 3;
        startClicked = false;
        
        if(auto.isSelected()) {
            autoTimer = new Timer(3000, new AutoNextListener());
            autoTimer.start();
        }
        
    }
    
    public ArrayList<String> readIn(ArrayList<String> al, JTextField tf) {
        String[] input;
        
        try { 
            input = tf.getText().split("；");            
        } catch (Exception ex) {
            input = new String[1];
            input[0] = "wrong";
        }
        
        if(input == null || input.length == 0) {
            input = new String[1];
            input[0] = "wrong";
        }
        
        for(String s:input) {
            al.add(s);
        }
        
        return al;
    }
    
    public void next () {
        startClicked = true;
            frame.getContentPane().removeAll();
            counter = new JTextArea("" + counterNum);
            counter.setFont(numberFont);
            counter.setEditable(false);
            
            guessBoard = new JPanel();
            guessBoard.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.BOTH;
            guessBoard.add(counter, c);
            
            guessBoard.setBackground(Color.WHITE);
            frame.getContentPane().add(BorderLayout.CENTER, guessBoard);
            frame.validate();
            timer = new Timer(500, new TimerListener());
            timer.start();
        
    }
    
    
    private class ReStartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {            
            timer.stop();
            autoTimer.stop();
            frame.getContentPane().removeAll();
            setupGUI();        
        }
    }
    
    
    private class StartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            
            partNames = readIn(partNames,userPart);
            directionNames = readIn(directionNames,userDirect);
            
            next();
        }
    }
    
    
    private class NextListener extends StartListener {
        public void actionPerformed(ActionEvent e) {
            next();
            
        }
    }
    
    
    private class AutoNextListener extends StartListener {
        public void actionPerformed(ActionEvent e) {
            next();
            autoTimer.stop();
        }
    }
    
    
    
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (startClicked) {
                frame.getContentPane().removeAll();
                frame.getContentPane().setBackground(Color.WHITE);
                //System.out.println("action");
                counterNum --;
                counter = new JTextArea("" + counterNum);
                counter.setFont(numberFont);
                counter.setEditable(false);
                
                guessBoard = new JPanel();
                guessBoard.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();
                c.gridwidth = 3;
                c.gridx = 0;
                c.gridy = 0;
                c.fill = GridBagConstraints.BOTH;
                guessBoard.add(counter, c);
                
                guessBoard.setBackground(Color.WHITE);
                frame.getContentPane().add(BorderLayout.CENTER, guessBoard);
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
