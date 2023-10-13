import bagel.*;

/*
 Adapted from A1 solution by Stella Li

 Pre-note: I renamed res/note2x.PNG to res/noteDoubleScore.PNG to allow for a consistent general solution
 * for reading in different types of notes from the csv.
*/

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * @author Leo Brooks
 *
 * ShadowDance is a game where notes fall from the top of the screen and the goal
 * is to press specific keys at the right time to score points.
 */
public class ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");

    /** The string for the file name of the font used in all instances of text.
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
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static final String RETURN_MESSAGE = "Press space to return to level selection";
    private Track menu = new Track("res/Wii Music.wav");
    private Track win = new Track("res/Win.wav");
    private Track lose = new Track("res/Lose.wav");
    private boolean started = false;
    private boolean paused = false;
    private Level currentLevel;

    /** Creates a game instance using the AbstractGame from Bagel.
     */
    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }


    /** The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /** Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        /* At the beginning of the game create title screen and music
         * with options to start any of the 3 levels
         */
        if (!started) {
            menu.run();
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTIONS, TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);

            if (input.wasPressed(Keys.NUM_1)) {
                currentLevel = new Level1();
                startLevel();
            }
            else if (input.wasPressed(Keys.NUM_2)) {
                currentLevel = new Level2();
                startLevel();
            }
            else if (input.wasPressed(Keys.NUM_3)) {
                currentLevel = new Level3();
                startLevel();
            }
        }

        /* Once level is finished, create end screen with accompanying text and music depending on a win/loss
         * with an option to go back to level selection
         */
        else if (currentLevel.checkFinished()) {
            currentLevel.endLevel();

            if (currentLevel.getScore() >= currentLevel.clearScore) {
                TITLE_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH/2.0 - TITLE_FONT.getWidth(CLEAR_MESSAGE)/2,
                        END_MESSAGE_Y);
                win.run();
            }
            else {
                TITLE_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH/2.0 - TITLE_FONT.getWidth(TRY_AGAIN_MESSAGE)/2,
                        END_MESSAGE_Y);
                lose.run();
            }

            INSTRUCTION_FONT.drawString(RETURN_MESSAGE,
                        WINDOW_WIDTH/2.0 - INSTRUCTION_FONT.getWidth(RETURN_MESSAGE)/2, END_INSTRUCTION_Y);

            if(input.wasPressed(Keys.SPACE)) {
                restartGame();
            }
        }

        // Level gameplay with option to pause game
        else {
            SCORE_FONT.drawString("Score " + currentLevel.getScore(), SCORE_LOCATION, SCORE_LOCATION);

            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                    currentLevel.startTrack();
                }
                currentLevel.paused();
            }

            else {
                currentLevel.update(input);
                if (input.wasPressed(Keys.TAB)) {
                    paused = true;
                    currentLevel.pauseTrack();
                }
            }
        }
    }

    // Reset all relevant attributes to their default state to allow for repetitive gameplay
    private void restartGame() {
        win.pause();
        lose.pause();
        started = false;
        menu = new Track(menu.file);
        win = new Track(win.file);
        lose = new Track(lose.file);
        currentLevel.restart();
        NormalNote.resetSpeed();
        HoldNote.resetSpeed();
    }

    private void startLevel() {
        currentLevel.startLevel();
        started = true;
        menu.pause();
    }
}
