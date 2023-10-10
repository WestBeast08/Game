import bagel.Input;
import bagel.Keys;
import bagel.Window;

/**
 * Note subclass which encapsulates the specific 'Slow Down' note mechanics
 */

public class SlowDownNote extends NormalNote {
    private static final int SLOW_DOWN_NOTE_SCORE = 15;
    private static final int SLOW_DOWN_INCREMENT = -1;
    public static final String SLOW_DOWN = "Slow Down";

    public SlowDownNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane);
        super.setSpecial();
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(getY() - targetHeight);

            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(SLOW_DOWN);
                deactivate();
                NormalNote.incrementSpeed(SLOW_DOWN_INCREMENT);
                HoldNote.incrementSpeed(SLOW_DOWN_INCREMENT);
                return SLOW_DOWN_NOTE_SCORE;
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
