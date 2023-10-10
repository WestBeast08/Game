import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Bomb' note mechanics
 */

public class BombNote extends NormalNote {
    private static final int BOMB_NOTE_SCORE = 0;
    private static final String LANE_CLEAR = "Lane Clear";

    public BombNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane);
        super.setSpecial();
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(getY() - targetHeight);

            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(LANE_CLEAR);
                deactivate();
                lane.ActivateBomb();
                return BOMB_NOTE_SCORE;
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
