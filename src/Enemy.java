import bagel.Image;

import java.lang.Math;

public class Enemy {
    private double xPosition = (Math.random() * 800) + 100;
    private final double yPosition = (Math.random() * 400) + 100;
    private final Image ENEMY_IMAGE = new Image("res/enemy.png");
    private final static int ENEMY_SPEED = 1;
    private double enemyVelocity = Math.copySign(1,(Math.random() * 2 - 1));
    public final static int COLLISION_RADIUS = 104;
    private boolean active = true;
    private final static int MIN_BOUNDARY = 100;
    private final static int MAX_BOUNDARY = 900;
    private final static int REVERSE_DIRECTION = -1;

    public double getX() {
        return xPosition;
    }
    public double getY() {
        return yPosition;
    }

    public boolean isActive() {
        return active;
    }

    public void update() {
        xPosition += ENEMY_SPEED * enemyVelocity;
        checkBoundaries();
        draw();
    }

    private void checkBoundaries() {
        if(xPosition <= MIN_BOUNDARY || xPosition >= MAX_BOUNDARY) {
            enemyVelocity *= REVERSE_DIRECTION;
        }
    }

    public void deactivate() {
        active = false;
    }

    public void draw() {
        ENEMY_IMAGE.draw(xPosition, yPosition);
    }

    public double distanceFromGuardian() {
        return Math.hypot(Math.abs(xPosition - Guardian.GUARDIAN_X), Math.abs(yPosition - Guardian.GUARDIAN_Y));
    }
}
