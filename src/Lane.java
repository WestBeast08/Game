import bagel.*;
import java.util.ArrayList;

/**
 * Class for the lanes which notes fall down
 * Adapted from A1 solution by Stella Li
 */
public class Lane {
    private static final int HEIGHT = 384;
    private static final int TARGET_HEIGHT = 657;
    private final String type;
    private final Image image;
    private final ArrayList<NormalNote> normalNotes = new ArrayList<>();
    private int numNotes = 0;
    private final ArrayList<HoldNote> holdNotes = new ArrayList<>();
    private int numHoldNotes = 0;
    private Keys relevantKey;
    private final int location;
    private int currNote = 0;
    private int currHoldNote = 0;
    private boolean activeBomb = false;

    public Lane(String dir, int location) {
        this.type = dir;
        this.location = location;
        image = new Image("res/lane" + dir + ".png");
        switch (dir) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }

    public String getType() {
        return type;
    }

    /**
     * updates all the notes in the lane
     */
    public int update(Input input, Accuracy accuracy) {
        if(activeBomb){
            clearLane();
            activeBomb = false;
        }

        draw();

        for(NormalNote i: normalNotes) {
            i.update();
        }

        for(HoldNote j: holdNotes) {
            j.update();
        }

        if (currNote < numNotes) {
            int score = normalNotes.get(currNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (normalNotes.get(currNote).isCompleted()) {
                currNote++;
                return score;
            }
        }

        if (currHoldNote < numHoldNotes) {
            int score = holdNotes.get(currHoldNote).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (holdNotes.get(currHoldNote).isCompleted()) {
                currHoldNote++;
            }
            return score;
        }

        return Accuracy.NOT_SCORED;
    }

    public void addNote(NormalNote n) {
        normalNotes.add(n);
        numNotes++;
    }

    public void addHoldNote(HoldNote hn) {
        holdNotes.add(hn);
        numHoldNotes++;
    }

    /**
     * Finished when all the notes have been pressed or missed
     */
    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!normalNotes.get(i).isCompleted()) {
                return false;
            }
        }

        for (int j = 0; j < numHoldNotes; j++) {
            if (!holdNotes.get(j).isCompleted()) {
                return false;
            }
        }

        return true;
    }

    /**
     * draws the lane and the notes
     */
    public void draw() {
        image.draw(location, HEIGHT);

        for(NormalNote i: normalNotes) {
            if(i.isActive()) {
                i.draw(location);
            }
        }

        for(HoldNote j: holdNotes) {
            if(j.isActive()) {
                j.draw(location);
            }
        }
    }

    private void clearLane() {
        for(int i = currNote; i < numNotes; i++) {
            if(normalNotes.get(i).isActive()) {
                normalNotes.get(i).deactivate();
            }
        }
        for(int j = currHoldNote; j < numHoldNotes; j++) {
            if(holdNotes.get(j).isActive()) {
                holdNotes.get(j).deactivate();
            }
        }
    }

    public void ActivateBomb(){
        activeBomb = true;
    }

    // For every note in the lane we have to check if it is in the collision range of an enemy, removing it if it is.
    public void checkCollisions(double enemyX, double enemyY) {
        for(int i = currNote; i < numNotes; i++) {
            if(normalNotes.get(i).isActive() && !normalNotes.get(i).isSpecial()) {
                if(Math.hypot(Math.abs(enemyX - location), Math.abs(enemyY - normalNotes.get(i).getY())) <= Enemy.COLLISION_RADIUS){
                    normalNotes.get(i).deactivate();
                }
            }
        }
    }

}
