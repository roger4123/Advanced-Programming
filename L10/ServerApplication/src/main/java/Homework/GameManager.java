package Homework;

import java.util.*;

public class GameManager {
    private Map<Integer, HexGame> games = new HashMap<>();
    private int gameIdCounter = 1;

    public synchronized String processCommand(String command) {
        String[] parts = command.split(" ");
        if (parts.length == 0)
            return "Invalid command";
        switch (parts[0]) {
            case "create":
                if (parts.length < 2)
                    return "Usage: create <playerName>";
                return createGame(parts[1]);
            case "join":
                if (parts.length < 3)
                    return "Usage: join <gameId> <playerName>";
                try {
                    int gameId = Integer.parseInt(parts[1]);
                    return joinGame(gameId, parts[2]);
                } catch (NumberFormatException e) {
                    return "Invalid game id";
                }
            case "move":
                if (parts.length < 5)
                    return "Usage: move <gameId> <playerName> <x> <y>";
                try {
                    int gameId = Integer.parseInt(parts[1]);
                    int x = Integer.parseInt(parts[3]);
                    int y = Integer.parseInt(parts[4]);
                    return move(gameId, parts[2], x, y);
                } catch (NumberFormatException e) {
                    return "Invalid parameters for move";
                }
            case "status":
                if(parts.length < 2)
                    return "Usage: status <gameId>";
                try {
                    int gameId = Integer.parseInt(parts[1]);
                    HexGame game = games.get(gameId);
                    if(game == null)
                        return "Game not found";
                    return game.getState();
                } catch(NumberFormatException e) {
                    return "Invalid game id";
                }
            default:
                return "Unknown command";
        }
    }

    private String createGame(String playerName) {
        HexGame game = new HexGame(gameIdCounter++, playerName);
        games.put(game.getId(), game);
        return "Game created with ID: " + game.getId() + ". Waiting for opponent...";
    }

    private String joinGame(int gameId, String playerName) {
        HexGame game = games.get(gameId);
        if (game == null)
            return "Game not found";
        return game.addPlayer(playerName);
    }

    private String move(int gameId, String playerName, int x, int y) {
        HexGame game = games.get(gameId);
        if (game == null)
            return "Game not found";
        return game.makeMove(playerName, x, y);
    }
}

