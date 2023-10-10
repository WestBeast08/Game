import bagel.Image;

import java.util.ArrayList;

public class Guardian {
    public static final int GUARDIAN_X = 800;
    public static final int GUARDIAN_Y = 600;
    private final Image GUARDIAN_IMAGE = new Image("res/guardian.png");
    private final ArrayList<Projectile> currentProjectiles = new ArrayList<>();

    public void fireProjectile(double targetX, double targetY) {
        Projectile newProjectile = new Projectile(targetX, targetY);
        currentProjectiles.add(newProjectile);
    }

    public void update() {
        for(Projectile i: currentProjectiles) {
            if(i.isActive()) {
                i.update();
            }
        }
    }

    public void paused() {
        draw();
        for(Projectile i: currentProjectiles) {
            if(i.isActive()) {
                i.draw();
            }
        }
    }

    public void draw() {
        GUARDIAN_IMAGE.draw(GUARDIAN_X, GUARDIAN_Y);
    }

    public boolean checkCollisions(double enemyX, double enemyY) {
        for(Projectile i: currentProjectiles) {
            if(i.isActive()) {
                if(Math.hypot(Math.abs(enemyX - i.getX()), Math.abs(enemyY - i.getY())) <= Projectile.COLLISION_RADIUS) {
                    i.deactivate();
                    return true;
                }
            }
        }
        return false;
    }

}
