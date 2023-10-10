import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Double Score' note mechanics
 */

public class DoubleScoreNote extends NormalNote {
    private static final int DOUBLE_NOTE_SCORE = 0;
    private static final String DOUBLE_SCORE = "Double Score";

    public DoubleScoreNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane);
        super.setSpecial();
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(getY() - targetHeight);

            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(DOUBLE_SCORE);
                deactivate();
                accuracy.activateDoubleScore();
                return DOUBLE_NOTE_SCORE;
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
