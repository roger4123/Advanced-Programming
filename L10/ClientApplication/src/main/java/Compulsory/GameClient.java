package Compulsory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GameClient extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 1234;

    // Main command socket (for create, join, moves)
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    // Status socket (for polling the board and timers)
    private Socket statusSocket;
    private BufferedReader statusIn;
    private PrintWriter statusOut;

    private JTextArea messageArea;
    private JButton[][] boardButtons;
    private final int boardSize = 11;

    // Game state variables
    private int gameId = -1;
    private String playerName;

    // A label to show timer info
    private JLabel timerLabel;

    public GameClient(String playerName) {
        super("Hex Game Client: " + playerName);
        this.playerName = playerName;
        setupNetworking();
        setupGUI();
    }

    private void setupNetworking() {
        try {
            // For moves and commands:
            socket = new Socket(SERVER_ADDRESS, PORT);
            in  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Separate connection for status updates:
            statusSocket = new Socket(SERVER_ADDRESS, PORT);
            statusIn = new BufferedReader(new InputStreamReader(statusSocket.getInputStream()));
            statusOut = new PrintWriter(statusSocket.getOutputStream(), true);
        } catch(IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Cannot connect to server.");
            System.exit(1);
        }
    }

    private void setupGUI() {
        setLayout(new BorderLayout());

        // Control panel: Create and Join game controls.
        JPanel controlPanel = new JPanel();
        JButton createGameButton = new JButton("Create Game");
        JButton joinGameButton = new JButton("Join Game");
        JTextField joinGameField = new JTextField(5);

        timerLabel = new JLabel("Timer: ");
        controlPanel.add(timerLabel);

        createGameButton.addActionListener(e -> {
            // Send create command: "create <playerName>"
            out.println("create " + playerName);
            try {
                String response = in.readLine();
                appendMessage("Server: " + response);
                if(response.startsWith("Game created")) {
                    gameId = extractGameId(response);
                    appendMessage("Your Game ID is " + gameId + ". Share it with an opponent!");
                    startStatusPolling();
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        joinGameButton.addActionListener(e -> {
            String gameIdStr = joinGameField.getText().trim();
            if (!gameIdStr.isEmpty()) {
                out.println("join " + gameIdStr + " " + playerName);
                try {
                    String response = in.readLine();
                    appendMessage("Server: " + response);
                    if(response.contains("joined")) {
                        gameId = Integer.parseInt(gameIdStr);
                        startStatusPolling();
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        controlPanel.add(createGameButton);
        controlPanel.add(new JLabel("Game ID:"));
        controlPanel.add(joinGameField);
        controlPanel.add(joinGameButton);
        add(controlPanel, BorderLayout.NORTH);

        // Board Panel: An 11x11 grid of buttons.
        JPanel boardPanel = new JPanel(new GridLayout(boardSize, boardSize));
        boardButtons = new JButton[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton btn = new JButton();
                btn.setMargin(new Insets(0,0,0,0));
                final int x = i;
                final int y = j;
                btn.addActionListener(e -> {
                    if(gameId != -1) {
                        // Send move command: "move <gameId> <playerName> <x> <y>"
                        out.println("move " + gameId + " " + playerName + " " + x + " " + y);
                        try {
                            String response = in.readLine();
                            appendMessage("Server: " + response);
                            // We no longer update just this button locally.
                            // The periodic status poll will update the whole board.
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        appendMessage("No game in progress. Please create or join a game first.");
                    }
                });
                boardButtons[i][j] = btn;
                boardPanel.add(btn);
            }
        }
        add(new JScrollPane(boardPanel), BorderLayout.CENTER);

        // Message area
        messageArea = new JTextArea(5, 40);
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setVisible(true);
    }

    // Utility method to extract gameId from server response.
    private int extractGameId(String response) {
        // Expected format: "Game created with ID: <number>. Waiting for opponent..."
        try {
            int start = response.indexOf("ID:") + 3;
            int end = response.indexOf(".", start);
            String idStr = response.substring(start, end).trim();
            return Integer.parseInt(idStr);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    // This method starts a polling thread to request status updates
    private void startStatusPolling() {
        new Thread(() -> {
            while (true) {
                if (gameId != -1) {  // Only poll if you have an active gameId
                    // Send the status command to the server
                    statusOut.println("status " + gameId);
                    try {
                        // Read the response from the server
                        String response = statusIn.readLine();
                        // Call parseStatus to update the board & timer on the GUI.
                        parseStatus(response);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000); // Poll every second
                } catch (InterruptedException ie) {
                    // Handle interruption if needed
                }
            }
        }).start();
    }

    // Parse the status string from the server and update the board and timer.
    private void parseStatus(final String response) {
        SwingUtilities.invokeLater(() -> {
            // Expected format: "STATE <boardState> TIMER <redTime> <blueTime>"
            if(response == null || !response.startsWith("STATE"))
                return;

            String[] parts = response.split("TIMER");
            if(parts.length < 2)
                return;

            String boardPart = parts[0].trim(); // "STATE <boardState>"
            if(boardPart.startsWith("STATE"))
                boardPart = boardPart.substring(5).trim();

            // boardPart now should contain rows separated by ";" and cells by ","
            String[] rows = boardPart.split(";");
            if(rows.length < boardSize)
                return;

            for (int i = 0; i < boardSize; i++) {
                String[] cells = rows[i].split(",");
                for(int j = 0; j < boardSize; j++) {
                    int val;
                    try {
                        val = Integer.parseInt(cells[j]);
                    } catch(NumberFormatException nfe) {
                        val = 0;
                    }
                    JButton btn = boardButtons[i][j];
                    if(val == 0) {
                        btn.setText("");
                        btn.setForeground(Color.BLACK);
                        btn.setBackground(null);
                    } else if(val == 1) {  // Red player
                        btn.setText("R");
                        btn.setForeground(Color.RED);
                    } else if(val == 2) {  // Blue player
                        btn.setText("B");
                        btn.setForeground(Color.BLUE);
                    }
                }
            }

            // Timer part: expected to be "<timeForRed> <timeForBlue>"
            String timerPart = parts[1].trim();
            String[] timerValues = timerPart.split(" ");
            if(timerValues.length >= 2) {
                timerLabel.setText("Red: " + timerValues[0] + " sec, Blue: " + timerValues[1] + " sec");
            }
        });
    }


    private void appendMessage(String msg) {
        SwingUtilities.invokeLater(() -> messageArea.append(msg + "\n"));
    }



    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("Enter your player name:");
        if(name == null || name.trim().isEmpty())
            name = "Anonymous";
        String finalName = name;
        SwingUtilities.invokeLater(() -> new GameClient(finalName));
    }
}
