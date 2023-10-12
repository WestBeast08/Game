import bagel.*;

/**
 * Class for hold notes
 * Adapted from A1 solution by Stella Li
 */
public class HoldNote extends Note{

    private static final int HEIGHT_OFFSET = 82;
    private final static int INITIAL_Y = 24;
    private final static String HOLD_IMG_KEY = "holdNote";
    private boolean holdStarted = false;

    public HoldNote(String type, int appearanceFrame) {
        super(HOLD_IMG_KEY, type, appearanceFrame, INITIAL_Y);
    }


    public void startHold() {
        holdStarted = true;
    }


    /**
     * Hold note scored twice, once at the start of the hold and once at the end.
     * If the note is initially missed, then the opportunity to attempt a "hold" is lost.
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));

            if (score == Accuracy.MISS_SCORE) {
                deactivate();
            }
            else if (score != Accuracy.NOT_SCORED) {
                startHold();
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

        return 0;
    }

    private int getBottomHeight() {
        return getY() + HEIGHT_OFFSET;
    }

    private int getTopHeight() {
        return getY() - HEIGHT_OFFSET;
    }

}
