package org.example;

import java.awt.*;
import java.util.LinkedList;

public class Snake {
    private static final int SNAKE_SPEED = 200; // milliseconds
    private LinkedList<Point> snake;

    public Snake() {
        this.snake = new LinkedList<>();
    }

    public void addSnakeBody(int coordinates) {
        this.snake.add(new Point(coordinates, coordinates));
    }

    public int getSnakeSpeed() {
        return SNAKE_SPEED;
    }

    public LinkedList<Point> getSnakeBody() {
        return this.snake;
    }

    public void drawSnake(Graphics g, int cellSize) {
        for (Point point : this.snake) {
            g.setColor(Color.GREEN);
            g.fillRect(point.x * cellSize, point.y * cellSize, cellSize, cellSize);
        }
    }
}
