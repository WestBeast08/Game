import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Slow Down' note mechanics
 */

public class SlowDownNote extends SpecialNote {
    private static final int SLOW_DOWN_NOTE_SCORE = 15;
    private static final int SLOW_DOWN_INCREMENT = -1;
    public static final String SLOW_DOWN_MESSAGE = "Slow Down";

    public SlowDownNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane, SLOW_DOWN_NOTE_SCORE, SLOW_DOWN_MESSAGE);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = super.checkScore(input, accuracy, targetHeight, relevantKey);
        if(score != Accuracy.NOT_SCORED) {
            Note.incrementSpeed(SLOW_DOWN_INCREMENT);
        }
        if(score == ZERO_SCORE_NOTE) {
            return ZERO_SCORE;
        }
        return score;
    }
}
