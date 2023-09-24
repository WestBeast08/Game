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

    public Projectile(double targetX, double targetY) {
        angle = Math.atan((targetY-yPosition)/(targetX-xPosition)) + Math.PI;
    }

    public void update() {
        xPosition += PROJECTILE_SPEED * Math.cos(angle);
        yPosition += PROJECTILE_SPEED * Math.sin(angle);
        draw();
    }

    private boolean hitCheck(Enemy enemy) {
        return false;
    }

    private void deactivate() {
        //
    }

    public void draw() {
        PROJECTILE_IMG.draw(xPosition, yPosition, ROTATION.setRotation(angle));
    }

}
