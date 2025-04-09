package Compulsory;

import Homework.GameFrame;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JButton loadButton;
    private JButton saveButton;
    private JButton exitButton;
    private JButton exportButton;
    private GameFrame game;

    public ControlPanel(GameFrame game) {
        this.game = game;

        setLayout(new FlowLayout());
        setBorder(BorderFactory.createTitledBorder("Control Panel"));

        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        exitButton = new JButton("Exit");
        exportButton = new JButton("Export");

        loadButton.addActionListener(e -> {game.loadGame();});
        saveButton.addActionListener(e -> {game.saveGame();});
        exitButton.addActionListener(e -> System.exit(0));
        exportButton.addActionListener(e -> {game.exportAsPNG();});

        add(loadButton);
        add(saveButton);
        add(exportButton);
        add(exitButton);
    }
}
