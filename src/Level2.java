/** Class containing information and creation for the second level of ShadowDance
 * @author Leo Brooks
 */
public class Level2 extends Level{

    private final static String LEVEL_INFORMATION = "res/test2.csv";
    private final static String TRACK = "res/Green_Beast.wav";
    private final static int CLEAR_SCORE = 400;

    /** Creates the second level, assigning a clear score, music track, and level information
     */
    public Level2() {
        super(LEVEL_INFORMATION, TRACK, CLEAR_SCORE);
    }

}
