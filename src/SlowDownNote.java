import bagel.Input;
import bagel.Keys;

/** A type of special note that once triggered, all other notes are slowed down.
 * @author Leo Brooks
 */
public class SlowDownNote extends SpecialNote {
    private static final int SLOW_DOWN_NOTE_SCORE = 15;
    private static final int SLOW_DOWN_INCREMENT = -1;
    public static final String SLOW_DOWN_MESSAGE = "Slow Down";

    /** Creates a special note with the specific score and message given by the slow-down note.
     * @param type Indicates what type of image to use
     * @param appearanceFrame Indicates the frame for the note to spawn
     * @param lane Indicates the lane that the note will spawn in
     */
    public SlowDownNote(String type, int appearanceFrame, Lane lane) {
        super(type, appearanceFrame, lane, SLOW_DOWN_NOTE_SCORE, SLOW_DOWN_MESSAGE);
    }

    /** Check inputs for a potential trigger, award score based on distance from target
     * and deactivate the note if close enough, and set a score message to be shown.
     * Also decreases the speed of all notes indefinitely if triggered.
     * @param input Game instance of Bagel Input class
     * @param accuracy Level instance of Accuracy class
     * @param targetHeight Target y-position for which the player is aiming to line up the note on press/release
     * @param relevantKey Specific key press required to trigger notes on a certain lane
     * @return int The score to be awarded to the player
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = super.checkScore(input, accuracy, targetHeight, relevantKey);
        if(score != Accuracy.NOT_SCORED) {
            Note.incrementSpeed(SLOW_DOWN_INCREMENT);
        }
        if(score == ZERO_SCORE_NOTE_ID) {
            return ZERO_SCORE;
        }
        return score;
    }
}
