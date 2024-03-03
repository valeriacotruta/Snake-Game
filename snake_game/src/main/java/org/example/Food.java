package org.example;

import java.awt.*;

public class Food {
    private Point food;

    public Food() {
        this.food = new Point(0, 0);
    }

    public Point getFood() {
        return this.food;
    }

    public void setFood(Point food) {
        this.food = food;
    }

    public void drawFood(Graphics g, int cellSize) {
        g.setColor(Color.RED);
        g.fillRect(this.food.x * cellSize, this.food.y * cellSize, cellSize, cellSize);
    }
}
