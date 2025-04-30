package Homework;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private final List<Player_H> players;
    private int currentTurn = 0;
    private volatile boolean gameOver = false;

    public Game(List<Player_H> players) {
        this.players = players;
    }

    public synchronized boolean isMyTurn(Player_H p) {
        return players.get(currentTurn) == p;
    }

    public synchronized void nextTurn() {
        currentTurn = (currentTurn + 1) % players.size();
    }

    public synchronized void setGameOver() {
        this.gameOver = true;
    }

    public synchronized boolean isGameOver() {
        return gameOver;
    }
}

class Timekeeper extends Thread {
    private final Game game;
    private final long timeLimitMillis;

    public Timekeeper(Game game, long timeLimitSeconds) {
        this.game = game;
        this.timeLimitMillis = timeLimitSeconds * 1000;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        while (!game.isGameOver()) {
            try {
                Thread.sleep(1000);  // Sleep 1 second
                long elapsed = System.currentTimeMillis() - startTime;
                System.out.println("⏱️ Time elapsed: " + (elapsed / 1000) + " seconds");

                if (elapsed >= timeLimitMillis) {
                    System.out.println("⏰ Time limit exceeded! Ending the game.");
                    game.setGameOver();
                    synchronized (game) {
                        game.notifyAll(); // Wake up any waiting players
                    }
                    break;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
