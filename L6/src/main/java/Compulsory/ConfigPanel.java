package Compulsory;

import Homework.GameFrame;

import javax.swing.*;
import java.awt.*;

public class ConfigPanel extends JPanel {
    private final JLabel dotsLabel;
    private final JTextField dotsField;
    private final JButton newGameButton;
    private final GameFrame game;

    public ConfigPanel(GameFrame game) {
        this.game = game;

        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Config"));

        dotsLabel = new JLabel("Dots");
        dotsField = new JTextField("10", 5);
        newGameButton = new JButton("New Game");

        newGameButton.addActionListener(e -> {
            try {
                int nrDots = Integer.parseInt(dotsField.getText());
                if (nrDots > 1) {
                    game.startNewGame(nrDots);
                } else {
                    JOptionPane.showMessageDialog(game,"Please enter a positive number greater than 1.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(game, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(dotsLabel);
        add(dotsField);
        add(newGameButton);
    }
}

