package Homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary_H {
    private final Set<String> validWords;

    public Dictionary_H(String filePath) {
        validWords = new HashSet<>();
        loadDictionary(filePath);
    }

    private void loadDictionary(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                validWords.add(line.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error loading dictionary: " + e.getMessage());
        }
    }

    public boolean isValidWord(String word) {
        return validWords.contains(word);
    }

    public Set<String> getWords() {
        return validWords;
    }

}
