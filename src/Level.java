import bagel.Input;
import bagel.Keys;

import java.io.BufferedReader;
import java.io.FileReader;

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
    private boolean finished;

    protected Level(String csvFile, String levelTrack, int clearScore) {
        this.levelTrack = new Track(levelTrack);
        this.levelCsv = csvFile;
        this.clearScore = clearScore;
    }

    public void readCsv() {
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
            System.exit(-1);
        }
    }

    public void startLevel() {
        readCsv();
        //started = true;
        //menu.pause();
        levelTrack.start();
    }

    public void update(Input input) {
        currFrame++;
        /*if(isLevel3) {
            level3.update(input, lanes, numLanes);
        }*/

        for (int i = 0; i < numLanes; i++) {
            score += lanes[i].update(input, accuracy);
        }

        accuracy.update();
        //finished = checkFinished();
        /*if (input.wasPressed(Keys.TAB)) {
            paused = true;
            levelTrack.pause();
        }*/
    }

    public boolean checkFinished() {
        for (int i = 0; i < numLanes; i++) {
            if (!lanes[i].isFinished()) {
                return false;
            }
        }
        return true;
    }

    public void endLevel() {
        accuracy.setAccuracy("");
        levelTrack.pause();
    }

    public int getScore() {
        return score;
    }

    public void paused() {
        for (int i = 0; i < numLanes; i++) {
            lanes[i].draw();
        }
    }

    public void startTrack() {
        levelTrack.run();
    }
    public void pauseTrack() {
        levelTrack.pause();
    }

    public static int getCurrFrame() {
        return currFrame;
    }

    public void restart() {
        currFrame = 0;
    }

}
