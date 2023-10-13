import bagel.*;

// Adapted from A1 solution by Stella Li

/**
 * Class for dealing with accuracy of pressing the notes
 * Adapted fully from A1 solution by Stella Li
 */
public class Accuracy {
    private static final int PERFECT_SCORE = 10;
    private static final int GOOD_SCORE = 5;
    private static final int BAD_SCORE = -1;

    /**
     * MISS_SCORE is the negative score deducted for attempting to hit non-special normal
     * and hold notes between the BAD_RADIUS and MISS_RADIUS or when the note goes off-screen.
     * MISS_SCORE is public to work with the unique case of missing the presses and releases of hold notes.
     */
    public static final int MISS_SCORE = -5;

    /**
     * NOT_SCORE represents the idea that non-special notes and special notes are not registered by a press
     * when the note is outside the MISS_RADIUS or SPECIAL_RADIUS respectively
     */
    public static final int NOT_SCORED = 0;
    private static final String PERFECT = "PERFECT";
    private static final String GOOD = "GOOD";
    private static final String BAD = "BAD";

    /** MISS is the message string shown on screen when the MISS_SCORE is deducted from the player.
     */
    public static final String MISS = "MISS";
    private static final int PERFECT_RADIUS = 15;
    private static final int GOOD_RADIUS = 50;
    private static final int BAD_RADIUS = 100;
    private static final int MISS_RADIUS = 200;
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    private String currAccuracy = null;
    private int frameCount = 0;
    private int doubleCount = 0;
    private static final int DOUBLE_FRAMES = 480;
    private int scoreMultiplier = 1;

    /** Sets the message to be displayed that accompanies either the score changes or special note activations
     */
    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }

    /** Doubles the current score multiplier and resets the period of that multiplier
     */
    public void activateDoubleScore() {
        /* Since spec does not specify how stacked double scores work, I decided to make
         * the multiplier stack but reset the timer back to zero to allow for a building combo type experience.
         */
        scoreMultiplier *= 2;
        doubleCount = 0;
    }

    /** Checks the current score multiplier
     * @return int The current score multiplier
     */
    public int checkScoreMultiplier(){
        return scoreMultiplier;
    }

    /** Evaluates the score by calculating the distance from note to target and assigning a score to it
     * @param height Current y-position of the note being evaluated
     * @param targetHeight Target y-position for which the player is aiming to line up the note on press
     * @param triggered Specific key was pressed/released
     * @return int The score calculated by the distance from note to target
     */
    public int evaluateScore(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);

        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE;
            }

        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE;
        }

        return NOT_SCORED;

    }

    /** Performs a state update, rendering scoring messages and keeping track of multipliers
     */
    public void update() {
        frameCount++;
        doubleCount++;
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    Window.getWidth()/2.0 - ACCURACY_FONT.getWidth(currAccuracy)/2,
                    Window.getHeight()/2.0);
        }
        if (doubleCount > DOUBLE_FRAMES) {
            scoreMultiplier = 1;
        }
    }
}
