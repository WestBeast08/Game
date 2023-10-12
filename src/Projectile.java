import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;

import java.lang.Math;

/**
 * Class for Projectiles fired from the Guardian which targets Enemies
 */

public class Projectile {
    private double xPosition = Guardian.GUARDIAN_X;
    private double yPosition = Guardian.GUARDIAN_Y;
    private final Image PROJECTILE_IMG = new Image("res/arrow.png");
    private final static int PROJECTILE_SPEED = 6;
    private final double angle;
    private final static DrawOptions ROTATION = new DrawOptions();
    private boolean active = true;
    public final static int COLLISION_RADIUS = 62;
    private final static int WINDOW_START = 0;
    private final static double HALF_PI = Math.PI/2;

    public boolean isActive() {
        return active;
    }

    public double getX() {
        return xPosition;
    }
    public double getY() {
        return yPosition;
    }

    public Projectile(double targetX, double targetY) {

        // Projectile is angled towards the closest enemy at moment of spawn
        double baseAngle = Math.atan((targetY-yPosition)/(targetX-xPosition));
        angle = baseAngle + HALF_PI + (HALF_PI * Math.signum(baseAngle));
    }

    public void update() {
        xPosition += PROJECTILE_SPEED * Math.cos(angle);
        yPosition += PROJECTILE_SPEED * Math.sin(angle);
        if(xPosition < Window.getWidth() && xPosition > WINDOW_START && yPosition < Window.getHeight() && yPosition > WINDOW_START) {
            draw();
        }
        else {
            deactivate();
        }
    }

    public void deactivate() {
        active = false;
    }

    public void draw() {
        PROJECTILE_IMG.draw(xPosition, yPosition, ROTATION.setRotation(angle));
    }

}
