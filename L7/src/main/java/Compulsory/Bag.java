package Compulsory;

import java.util.*;

public class Bag {
    private final Map<Character, Queue<Tile>> tiles;
    private final Random random;

    public Bag() {
        tiles = new HashMap<Character, Queue<Tile>>();
        random = new Random();

        for(char letter = 'A'; letter <= 'Z'; letter++) {
            Queue<Tile> tileQueue = new LinkedList<Tile>();
            for(int i = 0; i < 10; i++) {
                tileQueue.add(new Tile(letter, random.nextInt(10) + 1));
            }
            tiles.put(letter, tileQueue);
        }
    }

    public synchronized List<Tile> getTiles(int count) {
        List<Tile> extractedTiles = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            char letter = (char)(random.nextInt(26) + 'A');
            Queue<Tile> tileQueue = tiles.get(letter);
            if(tileQueue != null && !tileQueue.isEmpty()) {
                extractedTiles.add(tileQueue.poll());
            }
        }
        return extractedTiles;
    }

    public boolean isEmpty() {
        return tiles.values().stream().allMatch(Queue::isEmpty);
    }
}
