package Compulsory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
    private final Set<String> validWords;

    public Dictionary() {
        validWords = new HashSet<>();
        validWords.addAll(Arrays.asList("PROGRAMARE", "APLICATIE", "JAVA", "LABORATOR", "CURS", "EXAMEN", "IDE", "QUIZ", "DEVELOPMENT", "CONCURENT"));
    }

    public boolean isValidWord(String word) {
        return true;
    }
}
