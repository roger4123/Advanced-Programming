package Homework;

import Compulsory.ConfigPanel;
import Compulsory.ControlPanel;


import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.*;

public class GameFrame extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int DOT_RADIUS = 10;
    private static final Color PLAYER1_COLOR = Color.BLUE;
    private static final Color PLAYER2_COLOR = Color.RED;
    private static final Color DOT_COLOR = Color.BLACK;
    private static final Color SELECT_DOT_COLOR = Color.GREEN;

    private List<Dot> dots = new ArrayList<Dot>();
    private List<Line> lines = new ArrayList<Line>();
    private boolean isPlayer1Turn = true;
    private Dot selectedDot = null;
    private int nrDots = 10;
    private double player1Score = 0;
    private double player2Score = 0;
    private boolean gameEnded = false;

    private ConfigPanel configPanel;
    private DrawingPanel drawingPanel;
    private ControlPanel controlPanel;


    public GameFrame() {
        super("Dot Connection Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        initUI();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        setLayout(new BorderLayout());

        configPanel = new ConfigPanel(this);
        drawingPanel = new DrawingPanel(this);
        controlPanel = new ControlPanel(this);

        add(configPanel, BorderLayout.NORTH);
        add(drawingPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        startNewGame(nrDots);
    }

    public void startNewGame(int nrDots) {
        this.nrDots = nrDots;
        dots.clear();
        lines.clear();
        isPlayer1Turn = true;
        player1Score = 0;
        player2Score = 0;
        gameEnded = false;

        Random rand = new Random();
        for (int i = 0; i < nrDots; i++) {
            int x = DOT_RADIUS + rand.nextInt(WIDTH - 2 * DOT_RADIUS);
            int y = DOT_RADIUS + rand.nextInt(HEIGHT - 2 * DOT_RADIUS - 200);
            dots.add(new Dot(x, y));
        }

        drawingPanel.repaint();
        updateStatusMessage();
    }

    public void updateStatusMessage() {
        String message = isPlayer1Turn ? "Player 1's turn (Blue)" : "Player 2's turn (Red)";
        if (selectedDot != null) {
            message += " - Dot selected. Select another dot to connect.";
        }
        if (gameEnded) {
            message = "Game Over! Player 1: " + String.format("%.2f", player1Score) + ", Player 2: " + String.format("%.2f", player2Score);
        }
        drawingPanel.setStatusMessage(message);
    }

    public void handleDotSelect(Dot dot) {
        if(gameEnded) {
            return;
        }

        if(selectedDot == null) {
            selectedDot = dot;
        } else if (!selectedDot.equals(dot)) {
            boolean lineExists = false;
            for (Line line : lines) {
                if ((line.getStart().equals(selectedDot) && line.getEnd().equals(dot)) || (line.getStart().equals(dot) && line.getEnd().equals(selectedDot))) {
                    lineExists = true;
                    break;
                }
            }
            if(!lineExists) {
                Line newLine = new Line(selectedDot, dot, isPlayer1Turn);
                lines.add(newLine);

                double lineLength = newLine.getLength();
                if(isPlayer1Turn) {
                    player1Score += lineLength;
                } else {
                    player2Score += lineLength;
                }

                isPlayer1Turn = !isPlayer1Turn;

                if(allDotsConnected()) {
                    gameEnded = true;
                    displayResults();
                }
            }

            selectedDot = null;
        }
        drawingPanel.repaint();
        updateStatusMessage();
    }

    private boolean allDotsConnected() {
        if(dots.isEmpty() || lines.isEmpty()) {
            return false;
        }

        Set<Dot> visited = new HashSet<Dot>();
        dfs(dots.getFirst(), visited);

        return visited.size() == dots.size();
    }

    private void dfs(Dot dot, Set<Dot> visited) {
        visited.add(dot);

        for (Line line : lines) {
            if (line.getStart().equals(dot) && !visited.contains(line.getEnd())) {
                dfs(line.getEnd(), visited);
            } else if (line.getEnd().equals(dot) && !visited.contains(line.getStart())) {
                dfs(line.getStart(), visited);
            }
        }
    }

    private void displayResults() {
        double optimalScore = calculateMinimumSpanningTreeScore();
        String winner = player1Score <= player2Score ? "Player 1 (Blue)" : "Player 2 (Red)";
        String message = "Game Over!\n" + "Player 1 score: " + String.format("%.2f", player1Score) + "\n" + "Player 2 score: " + String.format("%.2f", player2Score) + "\n" + "Optimal score: " + String.format("%.2f", optimalScore) + "\n" + "Winner: " + winner;
        JOptionPane.showMessageDialog(this, message, "Game Results", JOptionPane.PLAIN_MESSAGE);
    }

    private double calculateMinimumSpanningTreeScore() {
        // Kruskal
        double totalWeight = 0;
        int addedEdges = 0;

        if (dots.size() <= 1) {
            return 0;
        }

        List<Edge> edges = new ArrayList<>();
        for(int i = 0; i < dots.size(); i++) {
            for(int j = i + 1; j < dots.size(); j++) {
                Dot d1 = dots.get(i);
                Dot d2 = dots.get(j);
                double distance = Math.sqrt(Math.pow(d2.getX() - d1.getX(), 2) + Math.pow(d2.getY() - d1.getY(), 2));
                edges.add(new Edge(d1, d2, distance));
            }
        }

        Collections.sort(edges);

        Map<Dot, Dot> parent = new HashMap<>();
        for(Dot dot : dots) {
            parent.put(dot, dot);
        }

        // in order of increasing weight
        for(Edge edge : edges) {
            Dot root1 = find(parent, edge.getDot1());
            Dot root2 = find(parent, edge.getDot2());

            // pay attention to not have cycles
            if(!root1.equals(root2)) {
                totalWeight += edge.getWeight();
                parent.put(root1, root2);
                addedEdges++;

                // MST has n-1 edges
                if(addedEdges == dots.size() - 1) {
                    break;
                }
            }
        }
        return totalWeight;
    }

    private Dot find(Map<Dot, Dot> parent, Dot dot) {
        if(!parent.get(dot).equals(dot)) {
            parent.put(dot, find(parent, parent.get(dot)));
        }
        return parent.get(dot);
    }

    public void saveGame() {
        GameFileManager.saveGame(this, dots, lines, isPlayer1Turn, selectedDot, player1Score, player2Score, gameEnded);
    }

    public void loadGame() {
        GameState gameState = GameFileManager.loadGame(this);
        if (gameState != null) {
            this.dots = gameState.getDots();
            this.lines = gameState.getLines();
            this.isPlayer1Turn = gameState.isPlayer1Turn();
            this.selectedDot = gameState.getSelectedDot();
            this.player1Score = gameState.getPlayer1Score();
            this.player2Score = gameState.getPlayer2Score();
            this.gameEnded = gameState.isGameEnded();

            drawingPanel.repaint();
            updateStatusMessage();
        }
    }

    public void exportAsPNG() {
        GameFileManager.exportAsPNG(this, drawingPanel);
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public Dot getSelectedDot() {
        return selectedDot;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public List<Line> getLines() {
        return lines;
    }

    public static int getDotRadius() {
        return DOT_RADIUS;
    }

    public static Color getPlayer1Color() {
        return PLAYER1_COLOR;
    }

    public static Color getPlayer2Color() {
        return PLAYER2_COLOR;
    }

    public static Color getDotColor() {
        return DOT_COLOR;
    }

    public static Color getSelectedDotColor() {
        return SELECT_DOT_COLOR;
    }

    public boolean isPointInDot(int x, int y, Dot dot) {
        double distance = Math.sqrt(Math.pow(x - dot.getX(), 2) + Math.pow(y - dot.getY(), 2));
        return distance <= DOT_RADIUS;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }

}

