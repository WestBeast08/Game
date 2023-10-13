import bagel.*;

/* Adapted from Note.java in A1 solution by Stella Li
 */

/** A type of note with no special properties
 * @author Leo Brooks
 */
public class NormalNote extends Note{
    private final static int INITIAL_Y = 100;
    private final static String NORMAL_IMG_KEY = "note";
    protected final Lane lane;
    private boolean special = false;

    /** Creates a normal note with no special properties
     * @param type Indicates what type of image to use
     * @param appearanceFrame Indicates the frame for the note to spawn
     * @param lane Indicates the lane that the note will spawn in
     */
    public NormalNote(String type, int appearanceFrame, Lane lane) {
        super(type, NORMAL_IMG_KEY, appearanceFrame, INITIAL_Y);
        this.lane = lane;
    }

    protected void setSpecial() {
        special = true;
    }

    /** Check if a normal note is a type of special note
     * @return boolean True if the note is special, false otherwise.
     */
    public boolean isSpecial(){
        return special;
    }

    /** Check inputs for a potential trigger, award score based on distance from target
     * and deactivate the note if close enough, and set a score message to be shown.
     * @param input Game instance of Bagel Input class
     * @param accuracy Level instance of Accuracy class
     * @param targetHeight Target y-position for which the player is aiming to line up the note on press
     * @param relevantKey Specific key press required to trigger notes on a certain lane
     * @return The score to be awarded to the player
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int score = accuracy.evaluateScore(getY(), targetHeight, input.wasPressed(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score * accuracy.checkScoreMultiplier();
            }

        }
        return Accuracy.NOT_SCORED;
    }
}
