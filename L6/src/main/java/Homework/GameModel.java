package Homework;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

record Dot(int x, int y) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Line implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Dot start;
    private final Dot end;
    private final boolean isPlayer1;
    private final double length;

    public Line(Dot start, Dot end, boolean player1) {
        this.start = start;
        this.end = end;
        this.isPlayer1 = player1;
        this.length = computeLength();
    }

    public double computeLength() {
        return Math.sqrt(Math.pow(end.x() - start.x(), 2) + Math.pow(end.y() - start.y(), 2));
    }

    public Dot getStart() {
        return start;
    }

    public Dot getEnd() {
        return end;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }

    public double getLength() {
        return length;
    }
}

class Edge implements Comparable<Edge> {
    private final Dot dot1;
    private final Dot dot2;
    private final double weight;

    public Edge(Dot dot1, Dot dot2, double weight) {
        this.dot1 = dot1;
        this.dot2 = dot2;
        this.weight = weight;
    }

    public Dot getDot1() {
        return dot1;
    }

    public Dot getDot2() {
        return dot2;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Edge o) {
        return Double.compare(weight, o.weight);
    }
}

class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Dot> dots;
    private final List<Line> lines;
    private final boolean isPlayer1Turn;
    private final Dot selectedDot;
    private final double player1Score;
    private final double player2Score;
    private final boolean gameEnded;


    GameState(List<Dot> dots, List<Line> lines, boolean isPlayer1Turn, Dot selectedDot, double player1Score, double player2Score, boolean gameEnded) {
        this.dots = new ArrayList<>(dots);
        this.lines = new ArrayList<>(lines);
        this.isPlayer1Turn = isPlayer1Turn;
        this.selectedDot = selectedDot;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.gameEnded = gameEnded;
    }

    public List<Dot> getDots() {
        return dots;
    }

    public List<Line> getLines() {
        return lines;
    }

    public boolean isPlayer1Turn() {
        return isPlayer1Turn;
    }

    public Dot getSelectedDot() {
        return selectedDot;
    }

    public double getPlayer1Score() {
        return player1Score;
    }

    public double getPlayer2Score() {
        return player2Score;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }
}

