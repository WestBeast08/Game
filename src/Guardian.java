import bagel.Image;

import java.util.ArrayList;

public class Guardian {
    public static final int GUARDIAN_X = 800;
    public static final int GUARDIAN_Y = 600;
    private final Image GUARDIAN_IMAGE = new Image("res/guardian.png");
    private ArrayList<Projectile> currentProjectiles = new ArrayList<Projectile>();

    public void fireProjectile(int targetX, int targetY) {
        Projectile newProjectile = new Projectile(targetX, targetY);



        currentProjectiles.add(newProjectile);
    }

    public void update() {
        for(Projectile i: currentProjectiles) {
            i.update();
        }
    }

    public void paused() {
        draw();
        for(Projectile i: currentProjectiles) {
            i.draw();
        }
    }

    public void draw() {
        GUARDIAN_IMAGE.draw(GUARDIAN_X, GUARDIAN_Y);
    }

}
