import bagel.DrawOptions;
import bagel.Image;
import bagel.Window;

import java.lang.Math;

/**
 * Class for Projectiles fired from the Guardian which targets Enemies
 * @author Leo Brooks
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

    /** Checks if the projectile is active
     * @return boolean True if the projectile is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /** Gets the x position of the projectile
     * @return double The x position
     */
    public double getX() {
        return xPosition;
    }

    /** Gets the y position of the projectile
     * @return double The y position
     */
    public double getY() {
        return yPosition;
    }

    /** Creates a projectile at the location of where the guardian is and aims towards the target enemy
     * @param targetX
     * @param targetY
     */
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
