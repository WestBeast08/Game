import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Speed Up' note mechanics
 */

public class SpeedUpNote extends SpecialNote {
    private static final int SPEED_UP_NOTE_SCORE = 15;
    private static final int SPEED_UP_INCREMENT = 1;
    private static final String SPEED_UP_MESSAGE = "Speed Up";

    public SpeedUpNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane, SPEED_UP_NOTE_SCORE, SPEED_UP_MESSAGE);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = super.checkScore(input, accuracy, targetHeight, relevantKey);
        if(score != Accuracy.NOT_SCORED) {
            Note.incrementSpeed(SPEED_UP_INCREMENT);
        }
        if(score == ZERO_SCORE_NOTE) {
            return ZERO_SCORE;
        }
        return score;
    }
}
