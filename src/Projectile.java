import bagel.DrawOptions;
import bagel.Image;
import java.lang.Math;

public class Projectile {
    private double xPosition = Guardian.GUARDIAN_X;
    private double yPosition = Guardian.GUARDIAN_Y;
    private final Image PROJECTILE_IMG = new Image("res/arrow.png");
    private final static int PROJECTILE_SPEED = 6;
    private final double angle;
    private final static DrawOptions ROTATION = new DrawOptions();
    private boolean completed = false;
    public final static int COLLISION_RADIUS = 62;

    public boolean isCompleted() {
        return completed;
    }

    public double getX() {
        return xPosition;
    }
    public double getY() {
        return yPosition;
    }

    public Projectile(double targetX, double targetY) {
        angle = Math.atan((targetY-yPosition)/(targetX-xPosition)) + Math.PI;
    }

    public void update() {
        xPosition += PROJECTILE_SPEED * Math.cos(angle);
        yPosition += PROJECTILE_SPEED * Math.sin(angle);
        draw();
    }

    public void deactivate() {
        completed = true;
    }

    public void draw() {
        PROJECTILE_IMG.draw(xPosition, yPosition, ROTATION.setRotation(angle));
    }

}
