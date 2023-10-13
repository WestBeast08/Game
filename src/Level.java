import bagel.Input;

import java.io.BufferedReader;
import java.io.FileReader;

// Adapted from ShadowDance.java in A1 solution by Stella Li

/** Class containing information and creation for all levels of ShadowDance
 * @author Leo Brooks
 */
public class Level {

    private static final int MAX_LANES = 5;
    protected Lane[] lanes = new Lane[MAX_LANES];
    protected int numLanes = 0;
    private Track levelTrack;
    private final String levelCsv;
    public final int clearScore;
    private static int currFrame = 0;
    private int score = 0;
    private final Accuracy accuracy = new Accuracy();
    private final static int FAILURE = -1;

    protected Level(String csvFile, String levelTrack, int clearScore) {
        this.levelTrack = new Track(levelTrack);
        this.levelCsv = csvFile;
        this.clearScore = clearScore;
    }

    private void readCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader(levelCsv))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");

                if (splitText[0].equals("Lane")) {
                    // reading lanes
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    Lane lane = new Lane(laneType, pos);
                    lanes[numLanes++] = lane;
                } else {
                    // reading notes
                    String dir = splitText[0];
                    Lane lane = null;
                    for (int i = 0; i < numLanes; i++) {
                        if (lanes[i].getType().equals(dir)) {
                            lane = lanes[i];
                        }
                    }

                    if (lane != null) {
                        String type = splitText[1];
                        NormalNote normalNote;
                        switch (type) {
                            case "Normal":
                                normalNote = new NormalNote(dir, Integer.parseInt(splitText[2]), lane);
                                lane.addNote(normalNote);
                                break;
                            case "Hold":
                                HoldNote holdNote = new HoldNote(dir, Integer.parseInt(splitText[2]));
                                lane.addHoldNote(holdNote);
                                break;
                            //case for all 4 special notes (inherited from normal notes)
                            case "DoubleScore":
                                normalNote = new DoubleScoreNote(type, Integer.parseInt(splitText[2]), lane);
                                lane.addNote(normalNote);
                                break;
                            case "Bomb":
                                normalNote = new BombNote(type, Integer.parseInt(splitText[2]), lane);
                                lane.addNote(normalNote);
                                break;
                            case "SpeedUp":
                                normalNote = new SpeedUpNote(type, Integer.parseInt(splitText[2]), lane);
                                lane.addNote(normalNote);
                                break;
                            case "SlowDown":
                                normalNote = new SlowDownNote(type, Integer.parseInt(splitText[2]), lane);
                                lane.addNote(normalNote);
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + type);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(FAILURE);
        }
    }

    /** Starts level in ShadowDance
     */
    public void startLevel() {
        readCsv();
        levelTrack.start();
    }

    /** Performs a state update for the level
     * @param input Game input from an instance of the Bagel Input class in ShadowDance
     */
    public void update(Input input) {
        currFrame++;

        for (int i = 0; i < numLanes; i++) {
            score += lanes[i].update(input, accuracy);
        }

        accuracy.update();
    }

    /** Checks if the game is finished by checking if all the notes have been deactivated.
     * @return boolean True if the game is finished, false otherwise.
     */
    public boolean checkFinished() {
        for (int i = 0; i < numLanes; i++) {
            if (!lanes[i].isFinished()) {
                return false;
            }
        }
        return true;
    }

    /** Game state for the end of a level
     */
    public void endLevel() {
        accuracy.setAccuracy("");
        levelTrack.pause();
    }

    /** Gets the current score
     * @return int The current score
     */
    public int getScore() {
        return score;
    }

    /** Level state for when the game is paused
     */
    public void paused() {
        for (int i = 0; i < numLanes; i++) {
            lanes[i].draw();
        }
    }

    /** Starts the level track
     */
    public void startTrack() {
        levelTrack.run();
    }

    /** Pauses the level track
     */
    public void pauseTrack() {
        levelTrack.pause();
    }

    /** Gets the current frame of the level
     * @return int The current frame of the level
     */
    public static int getCurrFrame() {
        return currFrame;
    }

    /** Resets the current frame to 0
     */
    public void restart() {
        currFrame = 0;
    }

}
