package Homework;

import java.util.HashMap;
import java.util.Map;

public class Board_H {
    private final Map<String, Integer> submittedWords = new HashMap<>();

    public synchronized void submitWord(String word, int score, Player_H player) {
        submittedWords.put(word, score);
        System.out.println(player.getName() + " submitted \"" + word + "\" for " + score + " points.");
    }

    public Map<String, Integer> getSubmittedWords() {
        return submittedWords;
    }
}

