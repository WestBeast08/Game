import bagel.*;

/**
 * Class for the lanes which notes fall down
 * Adapted fully from A1 solution by Stella Li
 */
public class Lane {
    private static final int HEIGHT = 384;
    private static final int TARGET_HEIGHT = 657;
    private final String type;
    private final Image image;
    private final NormalNote[] normalNotes = new NormalNote[100];
    private int numNotes = 0;
    private final HoldNote[] holdNotes = new HoldNote[20];
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

        for (int i = currNote; i < numNotes; i++) {
            normalNotes[i].update();
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes[j].update();
        }

        if (currNote < numNotes) {
            int score = normalNotes[currNote].checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (normalNotes[currNote].isCompleted()) {
                currNote++;
                return score;
            }
        }

        if (currHoldNote < numHoldNotes) {
            int score = holdNotes[currHoldNote].checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (holdNotes[currHoldNote].isCompleted()) {
                currHoldNote++;
            }
            return score;
        }

        return Accuracy.NOT_SCORED;
    }

    public void addNote(NormalNote n) {
        normalNotes[numNotes++] = n;
    }

    public void addHoldNote(HoldNote hn) {
        holdNotes[numHoldNotes++] = hn;
    }

    /**
     * Finished when all the notes have been pressed or missed
     */
    public boolean isFinished() {
        for (int i = 0; i < numNotes; i++) {
            if (!normalNotes[i].isCompleted()) {
                return false;
            }
        }

        for (int j = 0; j < numHoldNotes; j++) {
            if (!holdNotes[j].isCompleted()) {
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

        for (int i = currNote; i < numNotes; i++) {
            normalNotes[i].draw(location);
        }

        for (int j = currHoldNote; j < numHoldNotes; j++) {
            holdNotes[j].draw(location);
        }
    }

    private void clearLane() {
        for(int i = currNote; i < numNotes; i++) {
            if(normalNotes[i].isActive()) {
                normalNotes[i].deactivate();
            }
        }
        for(int j = currHoldNote; j < numHoldNotes; j++) {
            if(holdNotes[j].isActive()) {
                holdNotes[j].deactivate();
            }
        }
    }

    public void ActivateBomb(){
        activeBomb = true;
    }

    public void checkCollisions(double enemyX, double enemyY) {
        for(int i = currNote; i < numNotes; i++) {
            if(normalNotes[i].isActive() && !normalNotes[i].isSpecial()) {
                if(Math.hypot(Math.abs(enemyX - location), Math.abs(enemyY - normalNotes[i].getY())) <= Enemy.COLLISION_RADIUS){
                    normalNotes[i].deactivate();
                }
            }
        }
    }

}
