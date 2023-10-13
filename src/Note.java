import bagel.Image;
import bagel.Input;
import bagel.Keys;

/* Adapted from Note.java in A1 solution by Stella Li
 */

/** The Note class represents an entity in the ShadowDance game.
 * Notes are displayed on the game screen and correspond to lanes in
 * which they fall from the top of the screen to the bottom.
 * Players attempt to hit notes by lining them up with a certain target which accumulates score.
 * This class encapsulates the properties and behaviors of a game note, including its position,
 * timing, and appearance. Subclasses extend this class to create different types of notes
 * with specialized behaviors.
 *
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

    protected Note(String type, String imgKey, int appearanceFrame, int displacement) {
        image = new Image("res/" + imgKey + type + ".png");
        this.appearanceFrame = appearanceFrame;
        this.y = displacement;
    }

    /** Gets the current y-coordinate of the note.
     * @return int The current y-coordinate of the note.
     */
    public int getY() {
        return y;
    }

    /** Checks if the note is currently active
     * @return boolean True if the note is active, false otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /** Checks if the note has been deactivated
     * @return boolean True if the note has been deactivated, false otherwise.
     */
    public boolean isCompleted() {return completed;}

    /** Deactivates the note to stop further updates to it
     */
    public void deactivate() {
        active = false;
        completed = true;
    }

    /** Performs a state update for the note, eventually activating it and moving it down the screen.
     */
    public void update() {
        if (active) {
            y += speed;
        }

        if (Level.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    /** Draws the note's image at the relevant coordinates
     * @param x The x coordinate given by the lane containing the note
     */
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }

    protected abstract int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey);

    /** Increases/decreases the speed of all notes by an increment
     * @param increment The increment added to the current speed
     */
    public static void incrementSpeed(int increment) {

        // If the speed is not positive then the game becomes unplayable
        if(speed + increment <= MINIMUM_SPEED) {
            return;
        }
        speed += increment;
    }

    /** Resets the speed of all notes back to the base speed
     */
    public static void resetSpeed() {
        speed = BASE_SPEED;
    }
}
