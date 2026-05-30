/**
 * Author: Tomas Salaz File: LayoutPractice.java
 * CS251L Lab 8: Layout Practice
 *
 * Layout Practice make a GUI with: a Display; RGB, Height and Width Sliders;
 * Buttons to select a Shape; and a Button to Display the Stats in a Dialogue Box.
 *
 * NOTE: Java Docs will be used to describe one instance since I used anonymous
 * Classes which are all similar to each other.
 * */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
public class LayoutPractice extends JFrame implements ActionListener{
    //Member Variables and GUI Components
    JPanel display;
    JPanel sliderMenu;
    JPanel shapeMenu;
    JPanel dialogueMenu;
    JPanel colorMenu;
    JSlider heightSlider;
    JSlider widthSlider;
    JSlider rSlider;
    JSlider bSlider;
    JSlider gSlider;
    JLabel count = new JLabel("Times Clicked: 0");
    boolean drawCircle;
    boolean drawSquare;
    boolean drawTriangle;
    boolean clearDisplay;
    boolean colorSet = false;
    boolean heightChange = false;
    boolean widthChange = false;
    int redVal;
    int greenVal;
    int blueVal;
    int changeH;
    int changeW;
    int clicks = 0;
    //Main Method: Runs Program
    public static void main(String[] args) {
        (new LayoutPractice()).setVisible(true);
    }

    /**
     * Paints the Display based on the Current State of the GUI Components (Sliders
     * and Buttons)
     */
    public void paintComponents (Graphics g){
        super.paintComponents(g);
        int x = display.getWidth()/5;
        int y = display.getHeight()/5;

        Graphics2D shape = (Graphics2D) g;
        if (drawCircle){
            Color color = Color.white;
            if (colorSet) {color = new Color(redVal,greenVal,blueVal);}
            shape.setColor(color);
            shape.fillOval(250-changeW/2,250-changeH/2,changeW,changeH);
        }
        if (drawSquare){
            Color color = Color.white;
            if (colorSet) {color = new Color(redVal,greenVal,blueVal);}
            shape.setColor(color);
            shape.fillRect(250-changeW/2,250-changeH/2,changeW,changeH);
        }
        if (drawTriangle){
            int x1 = (int) (100+(changeW/1.25));
            int y1 = (int) (400 - (changeH/1.25));
            final int x2 = (x*5)/2;
            int y2 = (int)(100+(changeH/1.25));
            int x3 = (int)(400-changeW/1.25);
            int y3 = (int) (400 - (changeH/1.25));
            int[] xCoords = {x1,x2,x3};
            int[] yCoords = {y1,y2,y3};
            Color color = Color.white;
            if (colorSet){color = new Color(redVal,greenVal,blueVal);}
            shape.setColor(color);
            shape.fillPolygon(xCoords,yCoords,3);

        }
    }

    //Constructor
    public LayoutPractice() {
        super("Stretching Shapes");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout((LayoutManager)null);
        this.setSize(915, 500);
        this.setResizable(false);
        /**
         * All Slider Components use Abstract Change Listeners to update state of
         * the GUI.
         * */
        this.heightSlider = new JSlider(1,500,250);
        this.heightSlider.setPreferredSize(new Dimension(175, 50));
        this.heightSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(heightSlider.getValueIsAdjusting()){
                    heightChange = true;
                    changeH = heightSlider.getValue();
                } else {
                    paintComponents(getGraphics());
                }
            }
        });
        this.widthSlider = new JSlider(1, 500, 250);
        this.widthSlider.setPreferredSize(new Dimension(175, 50));
        this.widthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(widthSlider.getValueIsAdjusting()){
                    widthChange = true;
                    changeW = widthSlider.getValue();
                } else {
                    paintComponents(getGraphics());
                }
            }
        });
        this.rSlider = new JSlider(1, 0, 255, 255);
        this.rSlider.setPreferredSize(new Dimension(50, 350));
        this.rSlider.setBackground(Color.darkGray);
        this.rSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (rSlider.getValueIsAdjusting()){
                    redVal = rSlider.getValue();
                    colorSet = true;
                } else {
                    paintComponents(getGraphics());
                }
            }
        });
        this.bSlider = new JSlider(1, 0, 255, 255);
        this.bSlider.setPreferredSize(new Dimension(50, 350));
        this.bSlider.setBackground(Color.darkGray);
        this.bSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (bSlider.getValueIsAdjusting()){
                    blueVal = bSlider.getValue();
                    colorSet = true;
                } else {
                    paintComponents(getGraphics());
                }
            }
        });
        this.gSlider = new JSlider(1, 0, 255, 255);
        this.gSlider.setPreferredSize(new Dimension(50, 350));
        this.gSlider.setBackground(Color.darkGray);
        this.gSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (gSlider.getValueIsAdjusting()){
                    greenVal = gSlider.getValue();
                    colorSet = true;
                } else {
                    paintComponents(getGraphics());
                }
            }
        });
        JLabel Hlab = new JLabel();
        JLabel WLab = new JLabel();
        Hlab.setText("Change Height");
        WLab.setText("Change Width");
        JLabel rLab = new JLabel();
        rLab.setText("RED ");
        JLabel bLab = new JLabel();
        bLab.setText("     BLUE     ");
        JLabel gLab = new JLabel();
        gLab.setText("GREEN");
        this.display = new JPanel();
        this.sliderMenu = new JPanel();
        this.shapeMenu = new JPanel();
        this.colorMenu = new JPanel();
        this.display.setBackground(Color.black);
        this.display.setBounds(0, 0, 500, 500);
        this.sliderMenu.setBackground(Color.gray);
        this.sliderMenu.setBounds(500, 0, 200, 200);
        this.sliderMenu.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        this.sliderMenu.add(Hlab);
        this.sliderMenu.add(this.heightSlider);
        this.sliderMenu.add(WLab);
        this.sliderMenu.add(this.widthSlider);
        this.shapeMenu.setBackground(Color.gray);
        this.shapeMenu.setBounds(500, 200, 200, 200);
        this.shapeMenu.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        /**
         * Shape Buttons and Action Listeners, Once Again Updating the state of
         * the Display by manipulating the Components
         * */
        JButton circle = new JButton("Circle");
        circle.setPreferredSize(new Dimension(90, 85));
        circle.setFont(new Font("", Font.BOLD, 11));
        circle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCircle = true;
                drawSquare = false;
                drawTriangle = false;
                clearDisplay = false;
                paintComponents(getGraphics());
            }
        });
        JButton square = new JButton("Square");
        square.setPreferredSize(new Dimension(90, 85));
        square.setFont(new Font(" ", Font.BOLD, 11));
        square.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCircle = false;
                drawSquare = true;
                drawTriangle = false;
                clearDisplay = false;
                paintComponents(getGraphics());
            }
        });
        JButton triangle = new JButton("Triangle");
        triangle.setPreferredSize(new Dimension(90, 85));
        triangle.setFont(new Font(" ", Font.BOLD, 11));
        triangle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCircle = false;
                drawSquare = false;
                drawTriangle = true;
                clearDisplay = false;
                paintComponents(getGraphics());
            }
        });
        JButton clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(90, 85));
        clear.setFont(new Font(" ", Font.BOLD, 11));
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawCircle = false;
                drawSquare = false;
                drawTriangle = false;
                clearDisplay = true;
                paintComponents(getGraphics());
            }
        });
        this.shapeMenu.add(circle);
        this.shapeMenu.add(square);
        this.shapeMenu.add(triangle);
        this.shapeMenu.add(clear);
        this.dialogueMenu = new JPanel();
        this.dialogueMenu.setBackground(Color.black);
        this.dialogueMenu.setBounds(500, 400, 400, 65);
        this.dialogueMenu.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        JButton getDialogue = new JButton("SEE STATS!!");
        getDialogue.setPreferredSize(new Dimension(275,45));
        getDialogue.addActionListener(this);
        count.setPreferredSize(new Dimension( 100,45));
        count.setForeground(Color.white);
        dialogueMenu.add(getDialogue);
        dialogueMenu.add(count);
        this.colorMenu.setBackground(Color.gray);
        this.colorMenu.setBounds(700, 0, 200, 400);
        this.colorMenu.setBorder(BorderFactory.createLineBorder(Color.white, 5));
        this.colorMenu.add(this.rSlider);
        this.colorMenu.add(this.bSlider);
        this.colorMenu.add(this.gSlider);
        this.colorMenu.add(rLab);
        this.colorMenu.add(bLab);
        this.colorMenu.add(gLab);
        this.add(this.sliderMenu);
        this.add(this.shapeMenu);
        this.add(this.dialogueMenu);
        this.add(this.colorMenu);
        this.add(this.display);
    }
    /**
     * Only Non-Anonymous Action Listener, used for counting the Dialogue Box Clicks
     * and presenting the stats in the Dialogue Box.
     */
    public void actionPerformed(ActionEvent e) {
        clicks++;
        String s = "Red Value: " + redVal + "\nGreen Value: " + greenVal
            + "\nBlue Value: " + blueVal + "\n\n" + "Width: " + changeW +
            "\nHeight: " + changeH ;
        JOptionPane.showMessageDialog(null,s,"Current Stats" , JOptionPane.INFORMATION_MESSAGE);
        count.setText("Times Clicked: " + clicks);
    }
}