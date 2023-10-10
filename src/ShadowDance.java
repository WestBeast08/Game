import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @author Leo Brooks
 *
 * ShadowDance class works with all elements of the game to bring everything together
 * Adapted from A1 solution by Stella Li
 */
public class ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final static String LEVEL_1_CSV = "res/level1.csv";
    private final static String LEVEL_2_CSV = "res/level2.csv";
    private final static String LEVEL_3_CSV = "res/level3.csv";

    /**
     *  FONT_FILE contains the string for the file name of the font
     *  used in all instances of text
     */
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;
    private final static int SCORE_LOCATION = 35;
    private final static int END_MESSAGE_Y = 300;
    private final static int END_INSTRUCTION_Y = 500;
    private final Font TITLE_FONT = new Font(FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private static final String INSTRUCTIONS = "Select levels with\nnumber keys\n\n    1       2       3";
    private static final int LEVEL_1_CLEAR = 150;
    private static final int LEVEL_2_CLEAR = 400;
    private static final int LEVEL_3_CLEAR = 350;
    private int clearScore;
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static final String RETURN_MESSAGE = "Press space to return to level selection";
    private final Accuracy accuracy = new Accuracy();
    private static final int MAX_LANES = 5;
    private Lane[] lanes = new Lane[MAX_LANES];
    private int numLanes = 0;
    private int score = 0;
    private static int currFrame = 0;
    private Track track1 = new Track("res/giornos_theme.wav");
    private Track track2 = new Track("res/Green_Beast.wav");
    private Track track3 = new Track("res/HxH_Legend.wav");
    private Track menu = new Track("res/Wii Music.wav");
    private Track win = new Track("res/Win.wav");
    private Track lose = new Track("res/Lose.wav");
    private Track levelTrack;
    private boolean started = false;
    private boolean finished = false;
    private boolean paused = false;
    private final static int RESTART = 0;
    private final Guardian guardian = new Guardian();
    private boolean level3 = false;
    private ArrayList<Enemy> currentEnemies = new ArrayList<>();
    private Enemy currentTarget = new Enemy();
    private final static int ENEMY_SPAWN_FRAME = 600;

    /**
     * Creates a game instance using the AbstractGame from Bagel
     */
    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }



    private void readCsv(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
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

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (!started) {
            // starting screen
            menu.run();
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS,
                    TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);

            if (input.wasPressed(Keys.NUM_1)) {
                readCsv(LEVEL_1_CSV);
                started = true;
                menu.pause();
                levelTrack = track1;
                levelTrack.start();
                clearScore = LEVEL_1_CLEAR;
            }
            else if (input.wasPressed(Keys.NUM_2)) {
                readCsv(LEVEL_2_CSV);
                started = true;
                menu.pause();
                levelTrack = track2;
                levelTrack.start();
                clearScore = LEVEL_2_CLEAR;
            }
            else if (input.wasPressed(Keys.NUM_3)) {
                readCsv(LEVEL_3_CSV);
                started = true;
                menu.pause();
                levelTrack = track3;
                levelTrack.start();
                level3 = true;
                clearScore = LEVEL_3_CLEAR;
            }
        } else if (finished) {
            // end screen
            levelTrack.pause();
            if (score >= clearScore) {
                TITLE_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(CLEAR_MESSAGE)/2,
                        END_MESSAGE_Y);
                win.run();
            } else {
                TITLE_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(TRY_AGAIN_MESSAGE)/2,
                        END_MESSAGE_Y);
                lose.run();
            }
            INSTRUCTION_FONT.drawString(RETURN_MESSAGE,
                        WINDOW_WIDTH/2 - INSTRUCTION_FONT.getWidth(RETURN_MESSAGE)/2,
                        END_INSTRUCTION_Y);
            if(input.wasPressed(Keys.SPACE)) {
                restartGame();
            }
        } else {
            // gameplay

            SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);

            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                    levelTrack.run();
                }

                for (int i = 0; i < numLanes; i++) {
                    lanes[i].draw();
                }
                if(level3){
                    guardian.paused();
                    for(Enemy i: currentEnemies) {
                        if(i.isActive()){
                            i.draw();
                        }
                    }
                }



            } else {
                currFrame++;
                if(level3) {
                    if(currFrame % ENEMY_SPAWN_FRAME == 0 && currFrame != 0) {
                        Enemy newEnemy = new Enemy();
                        currentEnemies.add(newEnemy);
                    }
                    guardian.draw();
                    if(input.wasPressed(Keys.LEFT_SHIFT)) {
                        if(currFrame < ENEMY_SPAWN_FRAME) {
                            guardian.fireProjectile(0, 0);
                        }
                        else {
                            guardian.fireProjectile(currentTarget.getX(), currentTarget.getY());
                        }
                    }
                    double min_distance = Math.hypot(WINDOW_WIDTH, WINDOW_HEIGHT);
                    for(Enemy i: currentEnemies) {
                        if(i.isActive()){
                            i.update();
                            if(i.distanceFromGuardian() <= min_distance) {
                                min_distance = i.distanceFromGuardian();
                                currentTarget = i;
                            }
                            if(guardian.checkCollisions(i.getX(), i.getY())) {
                                i.deactivate();
                            }
                            for(int j = 0; j < numLanes; j++) {
                                lanes[j].checkCollisions(i.getX(), i.getY());
                            }
                        }
                    }
                    guardian.update();

                }

                for (int i = 0; i < numLanes; i++) {
                    score += lanes[i].update(input, accuracy);
                }

                accuracy.update();
                finished = checkFinished();
                if (input.wasPressed(Keys.TAB)) {
                    paused = true;
                    levelTrack.pause();
                }
            }
        }

    }

    /**
     * Returns the current frame of the level being played.
     * Used for message timing and entity spawns.
     */
    public static int getCurrFrame() {
        return currFrame;
    }

    private boolean checkFinished() {
        for (int i = 0; i < numLanes; i++) {
            if (!lanes[i].isFinished()) {
                return false;
            }
        }
        return true;
    }

    private void restartGame() {
        win.pause();
        lose.pause();
        started = false;
        finished = false;
        score = RESTART;
        currFrame = RESTART;
        numLanes = RESTART;
        menu = new Track(menu.file);
        win = new Track(win.file);
        lose = new Track(lose.file);
        track1 = new Track(track1.file);
        track2 = new Track(track2.file);
        track3 = new Track(track3.file);
        lanes = new Lane[MAX_LANES];
        accuracy.setAccuracy("");
        level3 = false;
        currentEnemies.clear();
        NormalNote.resetSpeed();
        HoldNote.resetSpeed();
    }
}
