import javax.sound.sampled.*;

/**
 * Class for managing the music being played in the background
 * Class is provided in the assignment spec
 */

public class Track extends Thread {
    private AudioInputStream stream;
    private Clip clip;
    public String file;

    public Track(String file) {
        try {
            stream = AudioSystem.getAudioInputStream(new java.io.File(file));
            clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class,stream.getFormat()));
            clip.open(stream);
            this.file = file;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void pause() {
        try {
            clip.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void run(){
        try {
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}