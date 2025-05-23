package Compulsory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board {
    private final ConcurrentHashMap<Player, Integer> playerScores = new ConcurrentHashMap<>(); // Player scores
    private final List<String> words = new CopyOnWriteArrayList<>(); // Submitted words

    public synchronized void submitWord(Player player, String word, int score) {
        words.add(word); // Store the submitted word
        playerScores.put(player, playerScores.getOrDefault(player, 0) + score); // Update the player's score
        System.out.println(player.getName() + " submitted: " + word + " (Score: " + score + ")");
    }

    public Map<Player, Integer> getPlayerScores(Player player) {
        return playerScores;
    }

    public List<String> getWords() {    
        return words;
    }

    public void displayScores() {
        System.out.println("\nFinal Scores:");
        playerScores.forEach((player, score) ->
                System.out.println(player.getName() + ": " + score + " points"));

        // Determine and display the winner
        playerScores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).ifPresent(winner -> System.out.println("\nWinner: " + winner.getName() + " with " +
                        playerScores.get(winner) + " points!"));

    }

    @Override
    public String toString() {
        return "Words: " + words + "\nScores: " + playerScores;
    }
}
