import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Speed Up' note mechanics
 */

public class SpeedUpNote extends NormalNote {
    private static final int SPEED_UP_NOTE_SCORE = 15;
    private static final int SPEED_UP_INCREMENT = 1;
    private static final String SPEED_UP = "Speed Up";

    public SpeedUpNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane);
        super.setSpecial();
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(getY() - targetHeight);

            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(SPEED_UP);
                deactivate();
                Note.incrementSpeed(SPEED_UP_INCREMENT);
                return SPEED_UP_NOTE_SCORE * accuracy.checkScoreMultiplier();
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
