package Compulsory;

import java.util.ArrayList;
import java.util.List;

public class WordGame {
    private final Bag bag = new Bag();
    private final Board board = new Board();
    private final Dictionary dictionary = new Dictionary();
    private final List<Player> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(player);
        player.setWordGame(this); // Assign the Game instance to the Player
    }

    public void play() {
        List<Thread> threads = new ArrayList<>(); // Store threads for players
        for (Player player : players) {
            Thread playerThread = new Thread(player);
            threads.add(playerThread);
            playerThread.start();
        }

        // Wait for all threads to finish execution
        for (Thread thread : threads) {
            try {
                thread.join(); // Ensure all threads complete before proceeding
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
                Thread.currentThread().interrupt(); // Restore interrupt status
            }
        }

        // Display final scores
        System.out.println("Game finished!");
        System.out.println("Final scores:");
        for (Player player : players) {
            System.out.println(board.getPlayerScores(player).toString());
            break;
        }

    }


    public Bag getBag() {
        return bag;
    }

    public Board getBoard() {
        return board;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public static void main(String[] args) {
        WordGame game = new WordGame();

        // Add players and set the game instance
        game.addPlayer(new Player("Player 1"));
        game.addPlayer(new Player("Player 2"));
        game.addPlayer(new Player("Player 3"));

        // Start the game
        game.play();
    }
}
