import bagel.Image;
import bagel.util.Point;
import java.lang.Math;

public class Enemy {
    private double xPosition = (Math.random() * 800) + 100;
    private final double yPosition = (Math.random() * 400) + 100;
    private final Image ENEMY_IMAGE = new Image("res/enemy.png");
    private final static int ENEMY_SPEED = 1;
    private double enemyVelocity = Math.copySign(1,(Math.random() * 2 - 1));
    private final static int COLLISION_RADIUS = 104;
    private boolean completed = false;

    public double getX() {
        return xPosition;
    }
    public double getY() {
        return yPosition;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void update() {
        xPosition += ENEMY_SPEED * enemyVelocity;
        checkBoundaries();
        draw();
    }

    public double calculateDistance(Note note) {
        return 0;
    }

    public Point getPosition() {
        return new Point();
    }

    private void checkBoundaries() {
        if(xPosition <= 100 || xPosition >= 900) {
            enemyVelocity *= -1;
        }
    }

    private void checkCollisions() {
        //
    }

    public void deactivate() {
        completed = true;
    }

    public void draw() {
        ENEMY_IMAGE.draw(xPosition, yPosition);
    }
}
