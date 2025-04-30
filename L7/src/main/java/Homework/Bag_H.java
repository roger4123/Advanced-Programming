package Homework;

import Compulsory.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Bag_H {
    private final List<Tile> tiles = new ArrayList<>();
    private final Random random = new Random();

    public Bag_H() {
        for (char c = 'A'; c <= 'Z'; c++) {
            int points = 1 + random.nextInt(10);
            for (int i = 0; i < 10; i++) {
                tiles.add(new Tile(c, points));
            }
        }
        Collections.shuffle(tiles);
    }

    public synchronized List<Tile> drawTiles(int count) {
        List<Tile> drawn = new ArrayList<>();
        for (int i = 0; i < count && !tiles.isEmpty(); i++) {
            drawn.add(tiles.removeFirst());
        }
        return drawn;
    }

    public synchronized boolean isEmpty() {
        return tiles.isEmpty();
    }
}

