package Compulsory;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel() {
        setLayout(new FlowLayout());

        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");
        JButton exitButton = new JButton("Exit");

        add(loadButton);
        add(saveButton);
        add(exitButton);

        exitButton.addActionListener(e -> System.exit(0));
    }
}
