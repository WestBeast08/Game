import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;

public class Level3 extends Level{

    private final static int ENEMY_SPAWN_FRAME = 600;
    private final ArrayList<Enemy> currentEnemies = new ArrayList<>();
    private final Guardian guardian = new Guardian();
    private Enemy currentTarget = new Enemy();
    private final static String LEVEL_INFORMATION = "res/test3.csv";
    private final static String TRACK = "res/HxH_Legend.wav";
    private final static int CLEAR_SCORE = 350;

    public Level3() {
        super(LEVEL_INFORMATION, TRACK, CLEAR_SCORE);
    }

    @Override
    public void update(Input input) {
        super.update(input);
        entityUpdate(input, super.lanes, super.numLanes);
    }

    private void entityUpdate(Input input, Lane[] lanes, int numLanes) {
        if((super.getCurrFrame() % ENEMY_SPAWN_FRAME == 0) && (super.getCurrFrame() != 0)) {
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

    @Override
    public void paused() {
        super.paused();
        guardian.paused();
        for(Enemy i: currentEnemies) {
            i.paused();
        }
    }

    @Override
    public void restart() {
        super.restart();
        currentEnemies.clear();
    }

}
