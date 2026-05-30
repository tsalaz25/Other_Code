import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ClickCounter extends JFrame
    implements ActionListener {
    private JTextField text = new JTextField(1);
    private JButton button = new JButton("Click me!");
    private int numClicks = 0;

    public ClickCounter() {
        super("Click Counter Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(this);

        getContentPane().add(button, BorderLayout.CENTER);
        getContentPane().add(text, BorderLayout.PAGE_START);
        pack();
    }

    public void actionPerformed(ActionEvent e) {
        numClicks++;
        text.setText("Button Clicked " + numClicks + " times");
    }

    public static void main(String[] args) {
        new ClickCounter().setVisible(true);
    }

}