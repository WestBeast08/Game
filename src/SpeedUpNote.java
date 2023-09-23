import bagel.Input;
import bagel.Keys;
import bagel.Window;

public class SpeedUpNote extends Note{
    private static final int SPEED_UP_NOTE_SCORE = 15;
    private static final int SPEED_UP_INCREMENT = 1;

    public SpeedUpNote(String dir, int appearanceFrame, Lane lane) {
        super(dir, appearanceFrame, lane);
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int distance = Math.abs(getY() - targetHeight);
            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(Accuracy.SPEED_UP);
                deactivate();
                Note.incrementSpeed(SPEED_UP_INCREMENT);
                HoldNote.incrementSpeed(SPEED_UP_INCREMENT);
                return SPEED_UP_NOTE_SCORE;
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }
}
