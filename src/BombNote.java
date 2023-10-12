import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Bomb' note mechanics
 */

public class BombNote extends SpecialNote {
    private static final int BOMB_NOTE_SCORE = 0;
    private static final String BOMB_NOTE_MESSAGE = "Lane Clear";

    public BombNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane, BOMB_NOTE_SCORE, BOMB_NOTE_MESSAGE);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        int score = super.checkScore(input, accuracy, targetHeight, relevantKey);
        if(score != Accuracy.NOT_SCORED) {
            lane.ActivateBomb();
        }
        if(score == ZERO_SCORE_NOTE) {
            return ZERO_SCORE;
        }
        return score;
    }
}
