import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;

public class Level3 {

    private final static int ENEMY_SPAWN_FRAME = 600;
    private final ArrayList<Enemy> currentEnemies = new ArrayList<>();
    private final Guardian guardian = new Guardian();
    private Enemy currentTarget = new Enemy();

    public void update(Input input, Lane[] lanes, int numLanes) {
        if((ShadowDance.getCurrFrame() % ENEMY_SPAWN_FRAME == 0) && (ShadowDance.getCurrFrame() != 0)) {
            Enemy newEnemy = new Enemy();
            currentEnemies.add(newEnemy);
        }
        guardian.draw();
        if(input.wasPressed(Keys.LEFT_SHIFT)) {
            for(Enemy i: currentEnemies) {
                if(i.isActive()) {
                    guardian.fireProjectile(currentTarget.getX(), currentTarget.getY());
                    break;
                }

            }
        }
        double min_distance = Math.hypot(Window.getWidth(), Window.getHeight());
        boolean activeEnemies = false;
        for(Enemy i: currentEnemies) {
            if(i.isActive()){
                activeEnemies = true;
                i.update();
                if(i.distanceFromGuardian() <= min_distance) {
                    min_distance = i.distanceFromGuardian();
                    currentTarget = i;
                }
                if(guardian.checkCollisions(i.getX(), i.getY())) {
                    i.deactivate();
                }
                for(int j = 0; j < numLanes; j++) {
                    lanes[j].checkCollisions(i.getX(), i.getY());
                }
            }
        }
        if(!activeEnemies) {
            guardian.removeProjectiles();
        }
        guardian.update();

    }

}
