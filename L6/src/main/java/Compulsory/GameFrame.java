package Compulsory;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() {
        super("Dot Connection Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Add configuration, canvas, and control panels
        add(new ConfigPanel(), BorderLayout.NORTH);
        add(new DrawingPanel(), BorderLayout.CENTER);
        add(new ControlPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }
}

