import bagel.Input;
import bagel.Keys;
import bagel.Window;

public class SpecialNote extends NormalNote {

    protected final int noteScore;
    protected final String scoreMessage;

    //Just an arbitrary number to distinguish between a note that gives no score verses the note not being counted
    protected final static int ZERO_SCORE_NOTE = 123;
    protected final static int ZERO_SCORE = 0;

    protected SpecialNote(String dir, int appearanceFrame, Lane lane, int noteScore, String scoreMessage) {
        super(dir, appearanceFrame, lane);
        super.setSpecial();
        this.noteScore = noteScore;
        this.scoreMessage = scoreMessage;
    }

    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(getY() - targetHeight);

            if (input.wasPressed(relevantKey) && distance <= Accuracy.SPECIAL_RADIUS) {
                accuracy.setAccuracy(scoreMessage);
                deactivate();
                if(noteScore == 0) {
                    return ZERO_SCORE_NOTE;
                }
                return noteScore * accuracy.checkScoreMultiplier();
            }
            else if (getY() >= (Window.getHeight())) {
                deactivate();
            }
        }
        return Accuracy.NOT_SCORED;
    }

}
