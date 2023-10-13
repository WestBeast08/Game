import bagel.Input;
import bagel.Keys;

/** A type of special note that once triggered, all scores are doubled for a period of time.
 * @author Leo Brooks
 */

public class DoubleScoreNote extends SpecialNote {
    private static final int DOUBLE_NOTE_SCORE = 0;
    private static final String DOUBLE_SCORE_MESSAGE = "Double Score";

    /** Creates a special note with the specific score and message given by the bomb note.
     * @param type Indicates what type of image to use
     * @param appearanceFrame Indicates the frame for the note to spawn
     * @param lane Indicates the lane that the note will spawn in
     */
    public DoubleScoreNote(String type, int appearanceFrame, Lane lane) {
        super(type, appearanceFrame, lane, DOUBLE_NOTE_SCORE, DOUBLE_SCORE_MESSAGE);
    }

    /** Check inputs for a potential trigger, award score based on distance from target
     * and deactivate the note if close enough, and set a score message to be shown.
     * Also doubles are scores for a period of time if triggered.
     * @param input Game instance of Bagel Input class
     * @param accuracy Level instance of Accuracy class
     * @param targetHeight Target y-position for which the player is aiming to line up the note on press
     * @param relevantKey Specific key press required to trigger notes on a certain lane
     * @return int The score to be awarded to the player
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = super.checkScore(input, accuracy, targetHeight, relevantKey);
        if(score != Accuracy.NOT_SCORED) {
            accuracy.activateDoubleScore();
        }
        if(score == ZERO_SCORE_NOTE_ID) {
            return ZERO_SCORE;
        }
        return score;
    }
}
