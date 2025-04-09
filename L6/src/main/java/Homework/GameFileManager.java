package Homework;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class GameFileManager {
    public static void saveGame(JFrame frame, List<Dot> dots, List<Line> lines, boolean isPlayer1Turn, Dot selectedDot,
                                double player1Score, double player2Score, boolean gameEnded) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Game");
        chooser.setFileFilter(new FileNameExtensionFilter("Game save files (*.sav)", "sav"));

        int userSelection = chooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            if(!fileToSave.getName().endsWith(".sav")) {        // lowercase?
                fileToSave = new File(fileToSave.getAbsolutePath() + ".sav");
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                GameState gameState = new GameState(dots, lines, isPlayer1Turn, selectedDot, player1Score, player2Score, gameEnded);
                oos.writeObject(gameState);
                JOptionPane.showMessageDialog(frame, "Game saved successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving game: " + JOptionPane.ERROR_MESSAGE);
                System.out.println("Error at saving game: " + e.getMessage());
            }
        }
    }

    public static GameState loadGame(JFrame frame) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Load Game");
        chooser.setFileFilter(new FileNameExtensionFilter("Game save files (*.sav)", "sav"));

        int userSelection = chooser.showOpenDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = chooser.getSelectedFile();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {
                GameState gameState = (GameState) ois.readObject();
                JOptionPane.showMessageDialog(frame, "Game loaded successfully!");
                return gameState;
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(frame, "Error loading game: " + JOptionPane.ERROR_MESSAGE);
                System.out.println("Error at loading game: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    public static void exportAsPNG(JFrame frame, DrawingPanel drawingPanel) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Export as PNG");
        chooser.setFileFilter(new FileNameExtensionFilter("PNG files (*.png)", "png"));

        int userSelection = chooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = chooser.getSelectedFile();
            if (!fileToSave.getName().endsWith(".png")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
            }

            try {
                BufferedImage image = new BufferedImage(drawingPanel.getWidth(), drawingPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = image.createGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

                drawingPanel.paintGame(graphics);
                graphics.dispose();

                ImageIO.write(image, "png", fileToSave);
                JOptionPane.showMessageDialog(frame, "Exported PNG successfully!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error exporting PNG: " + JOptionPane.ERROR_MESSAGE);
                System.out.println("Error at exporting PNG: " + e.getMessage());
            }
        }
    }

}
