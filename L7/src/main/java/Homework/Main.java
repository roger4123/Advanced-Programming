package Homework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Bag_H bag = new Bag_H();
        Board_H board = new Board_H();
        Dictionary_H dict = new Dictionary_H("src/main/java/Homework/dictionary.txt");
        Object turnLock = new Object();

        List<Player_H> players = new ArrayList<>();
        Game game = new Game(players);
        String[] playerNames = {"Alice", "Bob", "Carl"};

        for (int i = 0; i < 3; i++) {
            players.add(new Player_H(playerNames[i], bag, board, dict, turnLock, game));
        }

        Timekeeper timekeeper = new Timekeeper(game, 30); // 30 seconds
        timekeeper.setDaemon(true);
        timekeeper.start();

        for (Player_H p : players) p.start();

        try {
            timekeeper.join(); // wait for timer to finish
            for (Player_H p : players) p.join(); // then wait for players to finish
        } catch (InterruptedException ignored) {}

        System.out.println("\n🎉 Final scores:");
        players.forEach(p -> System.out.println(p.getName() + " scored " + p.getScore()));

        int maxScore = 0;
        for (Player_H player : players) {
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
            }
        }

        for (Player_H p : players) {
            if(p.getScore() == maxScore) {
                System.out.println("\n🎉 Winner is " + p.getName());
            }
        }

    }
}


