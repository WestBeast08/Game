import bagel.*;

/**
 * Class for normal notes
 * Adapted fully from A1 solution by Stella Li
 */
public class Note {
    private final Image image;
    private final int appearanceFrame;
    private static int speed = 2;
    private int y = 100;
    private boolean active = false;
    private boolean completed = false;
    private static final int DOUBLE_SCORE = 2;
    protected final Lane lane;

    public Note(String dir, int appearanceFrame, Lane lane) {
        image = new Image("res/note" + dir + ".png");
        this.lane = lane;
        this.appearanceFrame = appearanceFrame;
    }

    public int getY() {
        return y;
    }

    public boolean isActive() {
        return active;
    }
    public boolean isCompleted() {return completed;}

    public void deactivate() {
        active = false;
        completed = true;
    }

    public void update() {
        if (active) {
            y += speed;
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }

    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }

    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // evaluate accuracy of the key press
            int score = accuracy.evaluateScore(y, targetHeight, input.wasPressed(relevantKey));
            //gets overriden for special notes, only calculating if distance is right, then

            if (score != Accuracy.NOT_SCORED) {
                //returning 15, speeding up/slowing down, and also setting accuracy message
                deactivate();
                if(accuracy.checkDoubleScore()){
                    return score * DOUBLE_SCORE;
                }
                return score;
            }

        }
        return 0;
    }

    public static void incrementSpeed(int increment) {
        speed += increment;
    }

}
