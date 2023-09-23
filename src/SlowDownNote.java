import bagel.Input;
import bagel.Keys;
import bagel.Window;

public class SlowDownNote extends Note{
    private static final int SLOW_DOWN_NOTE_SCORE = 15;

    public SlowDownNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int distance = Math.abs(getY() - targetHeight);
            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(Accuracy.SLOW_DOWN);
                deactivate();
                return SLOW_DOWN_NOTE_SCORE;
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
