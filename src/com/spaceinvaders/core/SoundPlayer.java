package com.spaceinvaders.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    public static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Fichier audio introuvable : " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = audioStream.getFormat();

            // Vérifier si le format est compatible
            if (!isSupportedAudioFormat(format)) {
                System.err.println("Format audio non supporté : " + filePath);
                return;
            }

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Erreur lors de la lecture du fichier audio : " + filePath);
            e.printStackTrace();
        }
    }

    public static void playSoundAsync(String filePath) {
        new Thread(() -> playSound(filePath)).start();
    }

    private static boolean isSupportedAudioFormat(AudioFormat format) {
        return format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED &&
                format.getSampleRate() == 44100.0 &&
                format.getSampleSizeInBits() == 16 &&
                format.getChannels() == 1;
    }
}
