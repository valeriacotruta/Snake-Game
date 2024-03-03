# Snake-Game

This is a basic Snake game in Java using Java's Swing library. 

https://github.com/valeriacotruta/Snake-Game/assets/100199921/d5b7e7dc-1747-4010-8c7d-22856bbd4778

## Code explanation:
There are three classes for every essential component, namely _GameBoard, Snake_ and _Food_.
Within **Snake** class, the snake is represented as a list of points and every point is a part of its body. There is also set the speed of the snake defining the speed of the game in milliseconds.
```
private static final int SNAKE_SPEED = 200; // milliseconds
private LinkedList<Point> snake;
```
In **Food** class, food is illustrated as a point. 

In the **GameBoard** class, it is developed all the logistics of the game. A window was created using _JFrame class_ and some dimensions have been set. 
```
private static final int MATRIX_SIZE = 20;
private static final int CELL_SIZE = 20;
private static JLabel scoreText;
private final Snake snake;
private final Food food;
private int actualScore;
private String direction;
```
There is a method for generating a random location for the food, preventing the collision with the snake. 
```
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
```
**switchDirection() method** was implemented to detect the movements of the snake, increasing the score and its growth if it ate the food or to stop the game, if the snake hit itself. 
```
 private void switchDirection() {
        Point currentHead = this.snake.getSnakeBody().getFirst();
        Point newHead = switch (this.direction) {
           ...
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
```
**keyPressed() function** simulates the interaction user-game by detecting if there is any pressed key _(VK_UP, VK_DOWN, VK_LEFT, VK_RIGHT)_. Moreover, it helps to prevent the snake going backwards(if it goes down, you can make the snake move only to the left or to the right).
```
@Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP -> {
                if (!this.direction.equals("DOWN")) {
                    this.direction = "UP";
                }
            }
           ...
        }
    }
```
To display the snake and the food, **function paint(Graphics g**) has been overridden.
```
public void paint(Graphics g) {
    super.paint(g);
    this.snake.drawSnake(g, CELL_SIZE);
    this.food.drawFood(g, CELL_SIZE);
}
```


