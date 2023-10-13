/** Class containing information and creation for the first level of ShadowDance
 * @author Leo Brooks
 */
public class Level1 extends Level{
    private final static String LEVEL_INFORMATION = "res/test1.csv";
    private final static String TRACK = "res/giornos_theme.wav";
    private final static int CLEAR_SCORE = 150;

    /** Creates the first level, assigning a clear score, music track, and level information
     */
    public Level1() {
        super(LEVEL_INFORMATION, TRACK, CLEAR_SCORE);
    }
}
