package Homework;

public class HexGame {
    private int id;
    private Board board;
    private Player[] players = new Player[2];
    private int currentPlayerIndex = 0;
    private boolean gameOver = false;

    public HexGame(int id, String playerName) {
        this.id = id;
        board = new Board();
        players[0] = new Player(playerName, 1); // marker 1 for first player
    }

    public int getId() {
        return id;
    }

    public synchronized String addPlayer(String playerName) {
        if (players[1] != null)
            return "Game is full.";
        players[1] = new Player(playerName, 2); // marker 2 for second player
        // Start timers for both players (assuming blitz rules)
        players[0].startTimer();
        players[1].startTimer();
        return "Player " + playerName + " joined. Game started. " + players[currentPlayerIndex].getName() + " begins.";
    }

    public synchronized String makeMove(String playerName, int x, int y) {
        if (gameOver)
            return "Game is already over.";
        Player currentPlayer = players[currentPlayerIndex];
        if (!currentPlayer.getName().equals(playerName))
            return "It's not your turn.";
        boolean movePlaced = board.placeMove(x, y, currentPlayer.getMarker());
        if (!movePlaced)
            return "Invalid move, cell occupied or out of bounds.";

        String response = "Move accepted at (" + x + "," + y + "). ";
        if (board.checkWin(currentPlayer.getMarker())) {
            gameOver = true;
            // Stop both timers.
            players[0].stopTimer();
            players[1].stopTimer();
            response += "Player " + currentPlayer.getName() + " wins!";
            return response;
        }
        currentPlayerIndex = 1 - currentPlayerIndex;
        response += "Next turn: " + players[currentPlayerIndex].getName();
        return response;
    }

    // In HexGame.java
    public String getState() {
        // Get board state as a string (each row separated by ";" and cell values by ",")
        String boardState = board.getState();
        // Get timer info from both players (in seconds)
        String timerInfo = players[0].getTimeLeft() + " " + players[1].getTimeLeft();
        // Protocol message:
        // "STATE <boardState> TIMER <redTime> <blueTime>"
        return "STATE " + boardState + " TIMER " + timerInfo;
    }

}
