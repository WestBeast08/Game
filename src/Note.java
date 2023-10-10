import bagel.Image;
import bagel.Input;
import bagel.Keys;

/**
 * Superclass encapsulating all common properties among various note type entities.
 * Some code is adapted from A1 solution by Stella Li.
 */
public abstract class Note {
    private final Image image;
    private final int appearanceFrame;
    private final static int BASE_SPEED = 2;
    private static int speed = BASE_SPEED;
    private int y;
    private boolean active = false;
    private boolean completed = false;
    public static final int DOUBLE_SCORE = 2;


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

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
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
        speed += increment;
    }

    public static void resetSpeed() {
        speed = BASE_SPEED;
    }
}
