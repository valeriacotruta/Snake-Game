package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameBoard extends JFrame implements ActionListener, KeyListener {
    private static final int MATRIX_SIZE = 20;
    private static final int CELL_SIZE = 20;
    private static JLabel scoreText;
    private final Snake snake;
    private final Food food;
    private int actualScore;
    private String direction;

    public GameBoard() {
        int edge_size = MATRIX_SIZE * CELL_SIZE;
        setSize(edge_size, edge_size);
        setTitle("Little Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);

        this.actualScore = 0;
        scoreText = new JLabel("Score: " + this.actualScore);
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreText, BorderLayout.NORTH);

        this.snake = new Snake();
        int point_coordinate = MATRIX_SIZE / 2;
        this.snake.addSnakeBody(point_coordinate);
        this.direction = "RIGHT";

        Timer timer = new Timer(this.snake.getSnakeSpeed(), this);
        timer.start();

        this.food = new Food();
        setFoodLocation();
    }

    private void validateCoordinates(int xCoordinate, int yCoordinate) {
        if (xCoordinate <= MATRIX_SIZE && yCoordinate <= MATRIX_SIZE) {
            this.food.setFood(new Point(xCoordinate, yCoordinate));
        } else {
            this.food.setFood(new Point(0, 0));
        }
    }

    private void setFoodLocation() {
        int xCoordinate = (int) (Math.random() * MATRIX_SIZE);
        int yCoordinate = (int) (Math.random() * MATRIX_SIZE);
        validateCoordinates(xCoordinate, yCoordinate);
        while (this.snake.getSnakeBody().contains(this.food.getFood())) {
            xCoordinate = (int) (Math.random() * MATRIX_SIZE);
            yCoordinate = (int) (Math.random() * MATRIX_SIZE);
            validateCoordinates(xCoordinate, yCoordinate);
        }
    }

    private void switchDirection() {
        Point currentHead = this.snake.getSnakeBody().getFirst();
        Point newHead = switch (this.direction) {
            case "UP" -> new Point(currentHead.x, (currentHead.y - 1 + MATRIX_SIZE) % MATRIX_SIZE);
            case "DOWN" -> new Point(currentHead.x, (currentHead.y + 1) % MATRIX_SIZE);
            case "LEFT" -> new Point((currentHead.x - 1 + MATRIX_SIZE) % MATRIX_SIZE, currentHead.y);
            case "RIGHT" -> new Point((currentHead.x + 1) % MATRIX_SIZE, currentHead.y);
            default -> currentHead;
        };

        if (newHead.equals(this.food.getFood())) {
            this.snake.getSnakeBody().addFirst(this.food.getFood());
            setFoodLocation();
        } else {
            this.snake.getSnakeBody().addFirst(newHead);
            this.snake.getSnakeBody().removeLast();
        }

        if (this.snake.getSnakeBody().size() > 1 && this.snake.getSnakeBody().subList(1, this.snake.getSnakeBody().size()).contains(newHead)) {
            stopGame();
        }
    }

    private void stopGame() {
        JOptionPane.showMessageDialog(this, "Game Over!", "", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switchDirection();
        repaint();
        this.actualScore = this.snake.getSnakeBody().size() - 1;
        scoreText.setText("Score: " + this.actualScore);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP -> {
                if (!this.direction.equals("DOWN")) {
                    this.direction = "UP";
                }
            }
            case KeyEvent.VK_DOWN -> {
                if (!this.direction.equals("UP")) {
                    this.direction = "DOWN";
                }
            }
            case KeyEvent.VK_LEFT -> {
                if (!this.direction.equals("RIGHT")) {
                    this.direction = "LEFT";
                }
            }
            case KeyEvent.VK_RIGHT -> {
                if (!this.direction.equals("LEFT")) {
                    this.direction = "RIGHT";
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.snake.drawSnake(g, CELL_SIZE);
        this.food.drawFood(g, CELL_SIZE);
    }
}
