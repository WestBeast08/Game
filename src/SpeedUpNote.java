import bagel.Input;
import bagel.Keys;
import bagel.Window;

public class SpeedUpNote extends Note{
    private static final int SPEED_UP_NOTE_SCORE = 15;

    public SpeedUpNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int distance = Math.abs(getY() - targetHeight);
            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(Accuracy.SPEED_UP);
                deactivate();
                return SPEED_UP_NOTE_SCORE;
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
