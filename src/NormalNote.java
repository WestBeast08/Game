import bagel.*;

/**
 * Class for normal notes
 * Adapted from A1 solution by Stella Li
 */
public class NormalNote extends Note{
    private final static int INITIAL_Y = 100;
    private final static String NORMAL_IMG_KEY = "note";
    protected final Lane lane;
    private boolean special = false;

    public NormalNote(String type, int appearanceFrame, Lane lane) {
        super(NORMAL_IMG_KEY, type, appearanceFrame, INITIAL_Y);
        this.lane = lane;
    }

    protected void setSpecial() {
        special = true;
    }
    public boolean isSpecial(){
        return special;
    }

    /*
     * Calculates score using Accuracy class methods
     * This gets overridden for the special notes due to the different scoring mechanics
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int score = accuracy.evaluateScore(getY(), targetHeight, input.wasPressed(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score * accuracy.checkScoreMultiplier();
            }

        }
        return 0;
    }
}
