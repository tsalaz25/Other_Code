/**
 * @author Andree Jacobson (andree@cs.unm.edu)
 * @version 1.1 (May 10, 2008)
 * @version 1.2 (Aug 28, 2008)
 * @version 1.3    (Jan 30, 2009)
 *
 * @author Brooke Chenoweth
 * @version 1.4 (Aug 25, 2013)
 * Removed package and expanded some comments.
 * @version 1.5 (Jan 20, 2015)
 * Change to lab2, change pixel to point
 * @version 1.6 (Sept 3, 2018)
 * Adapt for 152 lab
 * @version 1.7 (Sept 7, 2022)
 * Stop using Thread.sleep and replace with blocking queue and timer
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;

/**
 * The main Display class for the picture drawing assignment in 152.
 *
 * Displays points at user specified locations, with oldest points
 * fading away.
 */
public class Display {

    /** Default length of the trace */
    private static final int DEFAULT_TRACE_LENGTH = 25;

    /** Default point size */
    private static final int DEFAULT_POINT_SIZE = 5;

    /** Maximum fps for the speed slider */
    private static final int MAX_FPS = 200;

    /**
     * Special class for drawing dots on a display
     */
    private class DisplayPanel extends JPanel {

        /**
         * Default constructor sets up default behavior
         */
        public DisplayPanel() {
            setPreferredSize(new Dimension(400, 400));
            setBackground(Color.BLACK);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Iterator<Color> colorListIterator = colors.subList(
                    colors.size() - points.size(), colors.size()).iterator();
            int offset = -pointSize / 2;

            synchronized (points) {
                Iterator<Point> pointIterator = points.iterator();
                while (pointIterator.hasNext()) {
                    if (colorListIterator.hasNext()) {
                        g.setColor(colorListIterator.next());
                    }
                    Point p = pointIterator.next();
                    g.fillRect(p.x + offset, p.y + offset, pointSize, pointSize);
                }
            }
        }

    }

    /**
     * List of points
     */
    private List<Point> points = null;

    /**
     * List of colors
     */
    private List<Color> colors = null;

    /**
     * Current trace index
     */
    private int maxTraceLength = DEFAULT_TRACE_LENGTH;

    /**
     * Frames per second to use for the update frequency
     */
    private int fps = MAX_FPS / 4;

    /** Timer to draw one point per tick */
    private Timer timer = new Timer(1000 / fps, event -> doDrawNextPoint());

    /** Points to process as timer ticks */
    private BlockingQueue<Point> pointsToProcess = new ArrayBlockingQueue<>(DEFAULT_TRACE_LENGTH);

    /**
     * Starting color of the first point
     */
    private Color pointColor = Color.RED;

    /**
     * Size of each point to draw
     */
    private int pointSize = DEFAULT_POINT_SIZE;

    /**
     * Panel to draw points on
     */
    private DisplayPanel drawPanel;

    /**
     * Construct display with default trace length and point size.
     */
    public Display() {
        this(DEFAULT_TRACE_LENGTH, DEFAULT_POINT_SIZE);
    }

    /**
     * Main constructor for the display
     * @param traceLength The number of trace points to use
     * @param pointSize The size of each box point
     */
    public Display(int traceLength, int pointSize) {

        setTraceLength(traceLength);

        JFrame mainFrame = new JFrame();
        mainFrame.setSize(450, 450);
        mainFrame.setTitle("CS152 Point Tracer");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel control = new JPanel();
        control.setPreferredSize(new Dimension(400, 50));

        control.add(new JLabel("Speed:"));

        // Create the speed slider to control update frequency
        JSlider speedSlider = new JSlider();
        speedSlider.setPreferredSize(new Dimension(75, 20));
        speedSlider.setCursor(new Cursor(Cursor.HAND_CURSOR));
        speedSlider.setMaximum(MAX_FPS);
        speedSlider.setMinimum(1);
        speedSlider.setPaintTicks(false);
        speedSlider.setValue(fps);
        speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider s = (JSlider) e.getSource();
                fps = s.getValue();
                timer.setDelay(1000 / fps);
            }
        });
        control.add(speedSlider);

        control.add(new JLabel("Length:"));
        JTextField lengthField = new JTextField();
        lengthField.setPreferredSize(new Dimension(50, 20));

        lengthField.setText("" + maxTraceLength);
        lengthField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField jtf = (JTextField) e.getSource();
                int length = maxTraceLength;
                try {
                    length = Integer.parseInt(jtf.getText());
                    setTraceLength(length);
                } catch (NumberFormatException ex) {
                    jtf.setText("" + length);
                }
            }
        });
        control.add(lengthField);

        // Create a button that allows user to pick a new color to draw with
        final JButton colorButton = new JButton("Color");
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Color chosenColor =
                        JColorChooser.showDialog(colorButton, "Pick a color", pointColor);
                if (chosenColor != null) {
                    pointColor = chosenColor;
                }
                generateColorSpectrum();
            }
        });
        control.add(colorButton);

        // Create the quit button so we can exit
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        control.add(quitButton);

        // Add the control panel to the main frame
        mainFrame.add(control, BorderLayout.SOUTH);

        // Create the JPanel that we will draw the points on
        drawPanel = new DisplayPanel();
        mainFrame.add(drawPanel, BorderLayout.CENTER);

        if (pointSize < 1) {
            this.pointSize = 1;
        } else {
            this.pointSize = pointSize;
        }

        // Make sure the components are laid out properly and show the frame
        // at the center of the screen
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    /**
     * Draw the most recent point at position
     * @param x X position for new point.
     * @param y Y position for new point.
     */
    public void drawPointAt(int x, int y) {

        Point point = new Point(x, y);

        try {
            pointsToProcess.put(point);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        if(!timer.isRunning()) {
            timer.start();
        }
    }


    private void doDrawNextPoint() {

        try{
            Point point = pointsToProcess.poll(timer.getDelay() / 2, TimeUnit.MILLISECONDS);
            if(point != null) {
                drawPoint(point);
            } else {
                timer.stop();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void drawPoint(Point point) {

        while (points.size() >= maxTraceLength) {
            points.remove(0);
        }
        points.add(point);

        drawPanel.repaint();
    }

    /**
     * Return the height of the black part of the display
     * @return Panel height.
     */
    public int getHeight() {
        return drawPanel.getHeight();
    }

    /**
     * Return the width of the black part of the display
     * @return Panel width.
     */
    public int getWidth() {
        return drawPanel.getWidth();
    }

    /**
     * Return String representation of this Display object
     * @return String for the display.
     */
    public String toString() {
        return "point display ( " + getWidth() + " by " + getHeight() + ")";
    }

    /**
     * After updates of the color or size of spectrum, this method is called to
     * create the new set of colors to use when drawing
     */
    private void generateColorSpectrum() {

        // Generate new color spectrum
        colors = Collections.synchronizedList(new LinkedList<Color>());
        float alphaDiff = 1.0f / maxTraceLength;
        float red = pointColor.getRed() / 255f;
        float green = pointColor.getGreen() / 255f;
        float blue = pointColor.getBlue() / 255f;
        for (int i = maxTraceLength - 1; i >= 0; i--) {
            colors.add(new Color(red, green, blue, 1.0f - alphaDiff * i));
        }
    }

    /**
     * Update the length of the point trace
     * @param length length of the trace, > 1
     */
    private void setTraceLength(int length) {

        // Set the new trace length
        maxTraceLength = length <= 1 ? 1 : length;

        generateColorSpectrum();
        if (points == null) {
            points = Collections.synchronizedList(new LinkedList<Point>());
        } else if (maxTraceLength < points.size()) {
            points = Collections.synchronizedList(points.subList(points
                    .size()
                    - maxTraceLength, points.size()));
        }
    }
}
