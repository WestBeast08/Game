import bagel.Image;
import bagel.Input;
import bagel.Keys;

/* Adapted from Note.java in A1 solution by Stella Li
 */

/** 
 * @author Leo Brooks
 */
public abstract class Note {
    private final Image image;
    private final int appearanceFrame;
    private final static int BASE_SPEED = 2;
    private final static int MINIMUM_SPEED = 0;
    private static int speed = BASE_SPEED;
    private int y;
    private boolean active = false;
    private boolean completed = false;


    public Note(String imgKey, String type, int appearanceFrame, int displacement) {
        image = new Image("res/" + imgKey + type + ".png");
        this.appearanceFrame = appearanceFrame;
        this.y = displacement;
    }

    public int getY() {
        return y;
    }
    public boolean isActive() {
        return active;
    }

    public boolean isCompleted() {return completed;}

    public void deactivate() {
        active = false;
        completed = true;
    }

    public void update() {
        if (active) {
            y += speed;
        }

        if (Level.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }

    public abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey);

    public static void incrementSpeed(int increment) {

        // If the speed is not positive then the game becomes unplayable
        if(speed + increment <= MINIMUM_SPEED) {
            return;
        }
        speed += increment;
    }

    public static void resetSpeed() {
        speed = BASE_SPEED;
    }
}
