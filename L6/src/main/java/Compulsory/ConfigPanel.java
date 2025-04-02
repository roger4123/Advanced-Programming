package Compulsory;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    public ConfigPanel() {
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Number of dots:");
        JTextField inputField = new JTextField(5);
        JButton createGameButton = new JButton("Create Game");


        add(label);
        add(inputField);
        add(createGameButton);

        createGameButton.addActionListener(e -> {
            int numberOfDots;
            try {
                numberOfDots = Integer.parseInt(inputField.getText());
                // Call DrawingPanel to generate dots
                DrawingPanel drawingPanel = (DrawingPanel) getParent().getComponent(1); // Get the center panel
                drawingPanel.generateDots(numberOfDots);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

