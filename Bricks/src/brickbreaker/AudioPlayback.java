package brickbreaker;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayback {

    public static void playMusic() {
        AudioInputStream audioStream;
        try {
            audioStream = AudioSystem.getAudioInputStream(new File("assets/bg-music.wav"));
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.open(audioStream);
            audioClip.start();
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("File not supported");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line is unavailable");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Unable to open file");
            ex.printStackTrace();
        }
    }

    public static void playSoundEffect() {
        AudioInputStream soundEffectStream;
        Clip soundEffect;
        try {
            soundEffectStream = AudioSystem.getAudioInputStream(new File("assets/break.wav"));
            AudioFormat format = soundEffectStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            soundEffect = (Clip) AudioSystem.getLine(info);
            soundEffect.open(soundEffectStream);
            soundEffect.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            Logger.getLogger(AudioPlayback.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
