package Homework;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private int timeLeft; // In seconds
    private Timer timer;
    private boolean running = false;

    public GameTimer(int seconds) {
        this.timeLeft = seconds;
    }

    public void start() {
        if (running)
            return;
        running = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                } else {
                    stop();
                    System.out.println("Time is up!");
                    // Here you might trigger a loss condition in the game logic.
                }
            }
        }, 1000, 1000);
    }

    public void stop() {
        running = false;
        if (timer != null)
            timer.cancel();
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
