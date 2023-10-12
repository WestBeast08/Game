import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Double Score' note mechanics
 */

public class DoubleScoreNote extends SpecialNote {
    private static final int DOUBLE_NOTE_SCORE = 0;
    private static final String DOUBLE_SCORE_MESSAGE = "Double Score";


    public DoubleScoreNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane, DOUBLE_NOTE_SCORE, DOUBLE_SCORE_MESSAGE);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = super.checkScore(input, accuracy, targetHeight, relevantKey);
        if(score != Accuracy.NOT_SCORED) {
            accuracy.activateDoubleScore();
        }
        if(score == ZERO_SCORE_NOTE) {
            return ZERO_SCORE;
        }
        return score;
    }
}
