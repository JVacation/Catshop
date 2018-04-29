package sound;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Class that handles playing sounds within CatShop
 * @author James Holliday
 * @version 1.0
 */
public class SoundPlayer
{
    /**
     * Allows the system to play sounds.
     * Sound to play is dynamic as long as the sound exists in the snd/ folder as a .WAV and you pass the id as the file name (excluding extension)
     * Example: soundPlayer.playSound("fileName");
     * @param id   Identifier for sound to be played.
     */
    public void playSound(String id)
    {
        System.out.println("[DEBUG] Playing sound: " + id);
        File f = new File("sound/" + id + ".wav");
        if(f.isFile()) 
        {
            // Play sound
            try
            {
                AudioInputStream sndFile = AudioSystem.getAudioInputStream(f);
                Clip clip = AudioSystem.getClip();
                clip.open(sndFile);
                clip.start();
            }
            catch(Exception e)
            {
                System.out.println("[DEBUG] Sound " + id + " could not be played. [" + e.getMessage() + "]");
            }
        }
        else
        {
            System.out.println("[DEBUG] Sound " + id + " does not exist in sound/");
        }
    }
}
