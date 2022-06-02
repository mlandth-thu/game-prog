import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class SoundManager {

    public static SoundManager instance;

    public SoundManager() {
        instance = this;
    }

    /*** Play specific Sound: ***/

    public void playShootSound(){
        playSound("SmallShot.wav");
    }

    public void playEnemyHitSound(){
        playSound("EnemyHit.wav");
    }

    public void playGameOverSound(){
        playSound("GameOver-Dead.wav");
    }

    public void playWaveFinishedSound(){
        playSound("WaveFinished.wav");
    }


    /*** General methode to play Sound: ***/

    private void playSound(String soundName) {
        float volume = 0.2f; //Volume in %/100 (0.5 == 50%)
        try {
            File f = new File("src/resources/sounds/" + soundName);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            /*** Volume Adjustment: ***/
            if (volume < 0f || volume > 1f)
                throw new IllegalArgumentException("Volume not valid: " + volume);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(20f * (float) Math.log10(volume));

            clip.start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
