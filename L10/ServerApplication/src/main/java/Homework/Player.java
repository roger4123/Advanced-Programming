package Homework;

public class Player {
    private String name;
    private int marker;
    private GameTimer timer; // Using our custom timer

    public Player(String name, int marker) {
        this.name = name;
        this.marker = marker;
        // For example, each player gets 5 minutes (300 seconds)
        this.timer = new GameTimer(300);
    }

    public String getName() {
        return name;
    }

    public int getMarker() {
        return marker;
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public int getTimeLeft() {
        return timer.getTimeLeft();
    }
}
