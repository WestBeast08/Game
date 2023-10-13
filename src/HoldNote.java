import bagel.*;

/* Adapted from A1 solution by Stella Li
 */

/** A type of note that is pressed and held for a period of time
 * @author Leo Brooks
 */
public class HoldNote extends Note{

    private static final int HEIGHT_OFFSET = 82;
    private final static int INITIAL_Y = 24;
    private final static String HOLD_IMG_KEY = "holdNote";
    private boolean holdStarted = false;

    /** Creates a note that is pressed and held instead of immediately released
     * @param type Indicates what type of image to use
     * @param appearanceFrame Indicates the frame for the note to spawn
     */
    public HoldNote(String type, int appearanceFrame) {
        super(type, HOLD_IMG_KEY, appearanceFrame, INITIAL_Y);
    }

    /** Hold note scored twice, once at the start of the hold and once at the end.
     * Check inputs for a potential trigger, award score based on distance from target
     * and set a score message to be shown. This is done for both a press and release
     * where the note is deactivated after both occur.
     * @param input Game instance of Bagel Input class
     * @param accuracy Level instance of Accuracy class
     * @param targetHeight Target y-position for which the player is aiming to line up the note on press/release
     * @param relevantKey Specific key press required to trigger notes on a certain lane
     * @return The score to be awarded to the player
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));

            /* If the initial press triggers a miss, no attempt at the hold is given
             * and note is deactivated immediately
             */
            if (score == Accuracy.MISS_SCORE) {
                deactivate();
            }
            else if (score != Accuracy.NOT_SCORED) {
                holdStarted = true;
            }
            return score * accuracy.checkScoreMultiplier();
        }

        else if (isActive() && holdStarted) {

            int score = accuracy.evaluateScore(getTopHeight(), targetHeight, input.wasReleased(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score * accuracy.checkScoreMultiplier();
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE * accuracy.checkScoreMultiplier();
            }
        }
        return Accuracy.NOT_SCORED;
    }

    private int getBottomHeight() {
        return getY() + HEIGHT_OFFSET;
    }

    private int getTopHeight() {
        return getY() - HEIGHT_OFFSET;
    }

}
