import bagel.Input;
import bagel.Keys;
import bagel.Window;

/** A type of normal note that can be triggered to cause a special effect on the notes or game
 * @author Leo Brooks
 */
public class SpecialNote extends NormalNote {
    protected final int noteScore;
    protected final String scoreMessage;

    // Just an arbitrary number to distinguish between a note that gives no score verses the note not being counted
    protected final static int ZERO_SCORE_NOTE_ID = 1234;
    protected final static int ZERO_SCORE = 0;
    private final static int SPECIAL_RADIUS = 50;

    protected SpecialNote(String type, int appearanceFrame, Lane lane, int noteScore, String scoreMessage) {
        super(type, appearanceFrame, lane);
        super.setSpecial();
        this.noteScore = noteScore;
        this.scoreMessage = scoreMessage;
    }

    /** Check inputs for a potential trigger, award score based on distance from target
     * and deactivate the note if close enough, and set a score message to be shown.
     * @param input Game instance of Bagel Input class
     * @param accuracy Level instance of Accuracy class
     * @param targetHeight Target y-position for which the player is aiming to line up the note on press/release
     * @param relevantKey Specific key press required to trigger notes on a certain lane
     * @return int The score to be awarded to the player
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(getY() - targetHeight);

            if (input.wasPressed(relevantKey) && distance <= SPECIAL_RADIUS) {
                accuracy.setAccuracy(scoreMessage);
                deactivate();
                if(noteScore == ZERO_SCORE) {
                    return ZERO_SCORE_NOTE_ID;
                }
                return noteScore * accuracy.checkScoreMultiplier();
            }

            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }

}
