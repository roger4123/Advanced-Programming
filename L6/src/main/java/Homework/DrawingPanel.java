package Homework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class DrawingPanel extends JPanel {
    private String statusMessage = "Player 1's turn (Blue)!";
    private Dot dragStartDot = null;
    private Point dragEndPoint = null;
    private GameFrame game;

    public DrawingPanel(GameFrame game) {
        this.game = game;

        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(game.isGameEnded()) {
                    return;
                }

                // check if a dot was clicked
                for(Dot dot : game.getDots()) {
                    if(game.isPointInDot(e.getX(), e.getY(), dot)) {
                        dragStartDot = dot;
                        break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if(game.isGameEnded() || dragStartDot == null) {
                    return;
                }
                dragEndPoint = e.getPoint();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(game.isGameEnded() || dragStartDot == null) {
                    return;
                }

                // check if the drag ended on another dot
                for(Dot dot : game.getDots()) {
                    if(game.isPointInDot(e.getX(), e.getY(), dot) && !dot.equals(dragStartDot)) {
                        game.handleDotSelect(dragStartDot);
                        game.handleDotSelect(dot);
                        break;
                    }
                }

                dragStartDot = null;
                dragEndPoint = null;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(game.isGameEnded()) {
                    return;
                }

                for(Dot dot : game.getDots()) {
                    if(game.isPointInDot(e.getX(), e.getY(), dot)) {
                        game.handleDotSelect(dot);
                        break;
                    }
                }
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintGame(graphics);
        graphics.setColor(Color.BLACK);
        graphics.drawString(statusMessage, 10, 20);

        if(dragStartDot != null && dragEndPoint != null) {
            graphics.setColor(game.isPlayer1Turn() ? GameFrame.getPlayer1Color() : GameFrame.getPlayer2Color());
            graphics.setStroke(new BasicStroke(2));
            graphics.drawLine(dragStartDot.getX(), dragStartDot.getY(), dragEndPoint.x, dragEndPoint.y);
        }
    }

    public void paintGame(Graphics2D graphics) {
        graphics.setStroke(new BasicStroke(2));
        for(Line line : game.getLines()) {
            graphics.setColor(line.isPlayer1() ? GameFrame.getPlayer1Color() : GameFrame.getPlayer2Color());
            graphics.drawLine(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
        }

        for(Dot dot : game.getDots()) {
            if(dot.equals(game.getSelectedDot()) || dot.equals(dragStartDot)) {
                graphics.setColor(GameFrame.getSelectedDotColor());
            } else {
                graphics.setColor(GameFrame.getDotColor());
            }
            graphics.drawOval(dot.getX() - GameFrame.getDotRadius(), dot.getY() - GameFrame.getDotRadius(), GameFrame.getDotRadius() * 2, GameFrame.getDotRadius() * 2);
        }
    }
}

