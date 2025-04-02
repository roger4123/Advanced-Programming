package Compulsory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private ArrayList<Point> dots;

    public DrawingPanel() {
        dots = new ArrayList<>();
    }

    public void generateDots(int number) {
        dots.clear();
        Random rand = new Random();
        for (int i = 0; i < number; i++) {
            dots.add(new Point(rand.nextInt(getWidth()), rand.nextInt(getHeight())));
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (Point dot : dots) {
            g.fillOval(dot.x - 5, dot.y - 5, 10, 10);
        }
    }
}

