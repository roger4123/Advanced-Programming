package Compulsory;

import Compulsory.WordGame;

import java.util.List;

public class Player implements Runnable {
    private String name;
    private WordGame wordGame;; // Reference to the shared game instance

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setWordGame(WordGame wordGame) {
        this.wordGame = wordGame;
    }

    private boolean submitWord() {
        // Extract 7 tiles from the bag
        List<Tile> extracted = wordGame.getBag().getTiles(7);
        if (extracted.isEmpty()) {
            return false; // No tiles can be extracted
        }

        // Create a word with all the extracted tiles (auto-generated for simplicity)
        StringBuilder wordBuilder = new StringBuilder();
        int wordScore = 0; // Calculate score based on tile points
        for (Tile tile : extracted) {
            wordBuilder.append(tile.getLetter());
            wordScore += tile.getPoints(); // Sum up tile points
        }
        String word = wordBuilder.toString();

        // Submit the word to the board and simulate processing delay
        synchronized (wordGame.getBoard()) { // Ensure thread-safe access to the board
            wordGame.getBoard().submitWord(this, word, wordScore); // Pass the score to the board
        }

        try {
            Thread.sleep(500); // Simulate time taken to process submission
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupt status
        }

        return true;
    }


    @Override
    public void run() {
        while (!wordGame.getBag().isEmpty()) { // Keep playing until the bag is empty
            boolean success = submitWord();
            if (!success) {
                break; // Stop playing if no tiles can be extracted
            }
        }

        System.out.println(name + " has finished playing.");
    }

    @Override
    public String toString() {
        return name;
    }
}
