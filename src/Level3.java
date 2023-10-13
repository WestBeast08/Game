import bagel.Input;
import bagel.Keys;
import bagel.Window;

import java.util.ArrayList;

/** Class containing information and creation for the third level of ShadowDance
 * @author Leo Brooks
 */
public class Level3 extends Level{

    private final static int ENEMY_SPAWN_FRAME = 600;
    private final ArrayList<Enemy> currentEnemies = new ArrayList<>();
    private final Guardian guardian = new Guardian();
    private Enemy currentTarget = new Enemy();
    private final static String LEVEL_INFORMATION = "res/test3.csv";
    private final static String TRACK = "res/HxH_Legend.wav";
    private final static int CLEAR_SCORE = 350;

    /** Creates the third level, assigning a clear score, music track, and level information
     */
    public Level3() {
        super(LEVEL_INFORMATION, TRACK, CLEAR_SCORE);
    }

    /** Performs a state update for both the level and the level 3 creatures
     * @param input Game input from an instance of the Bagel Input class in ShadowDance
     */
    @Override
    public void update(Input input) {
        super.update(input);
        creatureUpdate(input, super.lanes, super.numLanes);
    }

    /* Update all movements of enemies and guardian projectiles, checking for both projectile-enemy and
     * enemy-note collisions and deactivating accordingly.
     */
    private void creatureUpdate(Input input, Lane[] lanes, int numLanes) {
        if((Level.getCurrFrame() % ENEMY_SPAWN_FRAME == 0) && (Level.getCurrFrame() != 0)) {
            Enemy newEnemy = new Enemy();
            currentEnemies.add(newEnemy);
        }

        guardian.draw();

        if(input.wasPressed(Keys.LEFT_SHIFT)) {
            // Projectiles can only be fired if there is an active enemy to help reduce lag
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

        /* All current on-screen projectiles are removed once there are no active enemies
         * to help reduce lag as per ED discussion
         */
        if(!activeEnemies) {
            guardian.removeProjectiles();
        }
        guardian.update();

    }

    /** Level state for paused game
     */
    @Override
    public void paused() {
        super.paused();
        guardian.paused();
        for(Enemy i: currentEnemies) {
            i.paused();
        }
    }

    /** Level restart, clearing all enemies and hence projectiles
     */
    @Override
    public void restart() {
        super.restart();
        currentEnemies.clear();
    }

}
