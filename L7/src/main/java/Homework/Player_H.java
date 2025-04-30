package Homework;

import Compulsory.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player_H extends Thread {
    private final Bag_H bag;
    private final Board_H board;
    private final Dictionary_H dictionary;
    private final List<Tile> hand = new ArrayList<>();
    private int score = 0;
    private final Object turnLock;
    private final Game game;

    public Player_H(String name, Bag_H bag, Board_H board, Dictionary_H dict, Object turnLock, Game game) {
        super(name);
        this.bag = bag;
        this.board = board;
        this.dictionary = dict;
        this.turnLock = turnLock;
        this.game = game;
    }

    @Override
    public void run() {
        while (!game.isGameOver() && !bag.isEmpty()) {
            synchronized (turnLock) {
                while (!game.isMyTurn(this) && !game.isGameOver()) {
                    System.out.println(getName() + " waiting for turn...");
                    try {
                        turnLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                if (game.isGameOver()) break;

                // simulating thinking time
                try {
                    int thinkingTime = (int) (Math.random() * 3000 + 2000); // random delay between 2-5 sec
                    System.out.println(getName() + " is thinking... 🤔");
                    Thread.sleep(thinkingTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                // turn logic
                if (hand.isEmpty()) {
                    hand.addAll(bag.drawTiles(12));
                }

                String word = tryCreateValidWord();
                if (word != null) {
                    int wordScore = computeScore(word);
                    score += wordScore;
                    board.submitWord(word, wordScore, this);
                    hand.clear();
                    hand.addAll(bag.drawTiles(word.length()));
                } else {
                    hand.clear();
                    hand.addAll(bag.drawTiles(12));
                    System.out.println(getName() + " couldn't form a word and discarded tiles.");
                }

                game.nextTurn();
                turnLock.notifyAll();
            }
        }
    }




    private String tryCreateValidWord() {
        List<Character> letters = hand.stream().map(Tile::getLetter).toList();
        String bestWord = null;
        int bestScore = 0;

        for (String word : dictionary.getWords()) {
            if (canFormWord(word, new ArrayList<>(letters)) && word.length() <= hand.size()) {
                int score = computeScore(word);
                if (score > bestScore) {
                    bestWord = word;
                    bestScore = score;
                }
            }
        }
        return bestWord;
    }

    private boolean canFormWord(String word, List<Character> letters) {
        for (char c : word.toCharArray()) {
            if (!letters.remove((Character) c)) return false;
        }
        return true;
    }

    private int computeScore(String word) {
        int score = 0;
        for (char c : word.toCharArray()) {
            for (Tile t : hand) {
                if (t.getLetter() == c) {
                    score += t.getPoints();
                    break;
                }
            }
        }
        return score;
    }

    public int getScore() {
        return score;
    }
}

