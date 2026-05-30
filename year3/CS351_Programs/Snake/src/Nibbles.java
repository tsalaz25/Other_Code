/**
 * Author: Tomas Salaz  File: Nibbles.java
 * CS251L   Lab 10: Snake Game Part 2: GameManager
 *
 * Nibble implements the GUI using Java's Swing Package. The Nibbles class
 * extends JFrame so a Nibbles object is the GUI. Both the Menu and the Display
 * use JPanels, there is also a JButton and a JLabel on the Menu. The JButton
 * uses an Action listener and the Display uses ActionMaps to get keyboard
 * inputs
 * */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class Nibbles extends JFrame {
    //Member Variables
    private static GameManager GAME;
    private int width;
    private int height;
    private Boolean gameMoving = false;
    private JPanel Display;
    private JPanel Menu;
    private JButton PlayPause = new JButton();
    private JLabel ScoreLab = new JLabel();
    private final int CELL_SIZE = 25;
    private Timer gameTimer;
    private Direction D;
    private JDialog OVER;
    /**
     * Contractor for making a Nibbles Frame Uses Border layout so we can add
     * components the layout I wanted to
     * */
    public Nibbles(BorderLayout borderLayout) {
        width = GAME.getWidth();
        height = GAME.getHeight();
        setRandomDir();
        super.setTitle("SNAKE GAME");
        super.setResizable(true);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setFocusable(true);
        super.requestFocusInWindow();
        initialSetup();
        menuSetup();
        super.add(Menu, BorderLayout.CENTER);
        super.pack();
        super.setVisible(true);
        //Abstract method for implementing Tomer and Stepping on the GUI
        gameTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameMoving) {
                    GAME.step(D);
                    Display.repaint();
                    ScoreLab.setText("SCORE: " + GAME.getScore());

                    if (GAME.gameOver){
                        gameMoving = !gameMoving;
                        gameTimer.stop();
                        showDialogue();
                    }
                }
            }
        });
        gameTimer.start();
    }
    /**
     * Main method is the Entry point for the whole Program. Functions by making
     * a FileGetter object. It then sets the GAME member variable to get all the
     * information from the Game Manager
     * */
    public static void main(String[] args) throws Exception {
        try {
            if (args.length == 0) {
                GAME = new GameManager(new FileGetter());
            } else {
                GAME = new GameManager(new FileGetter(args[0]));
            }
        } catch (Exception E) {
            E.printStackTrace();
        }

        Nibbles game = new Nibbles(new BorderLayout());
        game.AddEvents(game.Display);
    }
    /**
     * InitialSetup is a helper method that set the Dimensions and Format the
     * windows in the GUI. Also has an abstract class that is used for repainting
     * and is called in the timer abstract method
     * */
    public void initialSetup() {
        Menu = new JPanel(new BorderLayout());
        Menu.setBackground(Color.GRAY);
        Menu.setPreferredSize(new Dimension(width * CELL_SIZE,
        (height * CELL_SIZE) + 50));
        Display = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                repaintDisplay(g);
            }
        };
        Display.setBackground(Color.BLACK);
        Display.setPreferredSize(new Dimension(width * CELL_SIZE,
        height * CELL_SIZE));
        Display.setFocusable(true);
        Display.requestFocusInWindow();
        Menu.add(Display, BorderLayout.SOUTH);
        Menu.setVisible(true);
        Display.setVisible(true);
    }
    /**
     * MenuSetup implements the Action Listener for the button and formats the
     * three components that it contains.
     * */
    public void menuSetup() {
        PlayPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameMoving = !gameMoving;
            }
        });
        ScoreLab.setText("SCORE: " + GAME.Score);
        PlayPause.setText("Play/Pause");
        PlayPause.setPreferredSize(new Dimension(((width * CELL_SIZE) / 5)
        * 4, 45));
        ScoreLab.setPreferredSize(new Dimension((width * CELL_SIZE) / 5,
        45));
        Menu.add(PlayPause, BorderLayout.WEST);
        Menu.add(ScoreLab, BorderLayout.EAST);
    }
    /**
     * AddEvents is a Helper method to implements the KeyListeners for the
     * Display The KeyListener is implemented using and Action Map. Also
     * setFocusable to the Display
     * */
    public void AddEvents(JPanel Display) {
        InputMap input = Display.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap action = Display.getActionMap();
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP,0),
        "UP");
        action.put("UP", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (D != Direction.DOWN){
                    D = Direction.UP;
                    if (!gameMoving) {
                        gameMoving = true;
                        gameTimer.start();
                    }
                }
            }
        });
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0),
        "DOWN");
        action.put("DOWN", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (D != Direction.UP){
                    D = Direction.DOWN;
                    if (!gameMoving) {
                        gameMoving = true;
                        gameTimer.start();
                    }
                }
            }
        });
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0),
        "LEFT");
        action.put("LEFT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (D != Direction.RIGHT){
                    D = Direction.LEFT;
                    if (!gameMoving) {
                        gameMoving = true;
                        gameTimer.start();
                    }
                }
            }
        });
        input.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0),
        "RIGHT");
        action.put("RIGHT", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (D != Direction.LEFT){
                    D = Direction.RIGHT;
                    if (!gameMoving) {
                        gameMoving = true;
                        gameTimer.start();
                    }
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }
    /**
     * Repaints the Display panel when called by Traversing over the Points in
     * the Board and using a Switch
     * */
    private void repaintDisplay(Graphics G) {
        for (Point P : GAME.getBOARD()) {
            int x = P.getX() * CELL_SIZE;
            int y = P.getY() * CELL_SIZE;
            switch (P.type) {
                case SNAKE -> {
                    if (P.getSymbol() == '0') {
                        G.setColor(Color.GREEN);
                        G.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    } else {
                        G.setColor(Color.YELLOW);
                        G.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
                case WALL ->{ G.setColor(Color.WHITE);
                    G.fillRect(x, y, CELL_SIZE, CELL_SIZE);}
                case FOOD ->{ G.setColor(Color.RED);
                    G.fillRect(x, y, CELL_SIZE, CELL_SIZE);}
                default ->{}
            }
        }
    }
    //Sets Random Director
    private void setRandomDir(){
        int random = new Random().nextInt(4);
        switch (random){
            case 0 -> {D  =Direction.UP;}
            case 1 -> {D = Direction.DOWN;}
            case 2 -> {D = Direction.LEFT;}
            default -> {D = Direction.RIGHT;}
        }
    }
    //Dialogue helper method
    private void showDialogue(){
        JOptionPane.showMessageDialog(this,
        "GAME OVER! FINAL SCORE: " + GAME.Score);
    }
}