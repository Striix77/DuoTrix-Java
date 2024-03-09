package main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {

    //manages the background sounds and the sound effects
    private final AudioInputStream menuSong;
    private final Clip menuSongClip;
    private final AudioInputStream gameSong;
    private final Clip gameSongClip;
    private FloatControl soundController;
    private AudioInputStream buttonBeep;
    private Clip buttonClip;
    private AudioInputStream jumpSound;
    private Clip jumpClip;
    private AudioInputStream transformSound;
    private Clip transformClip;
    private AudioInputStream hurtSound;
    private Clip hurtClip;
    private AudioInputStream gloveSound;
    private Clip gloveClip;
    private AudioInputStream bombSound;
    private Clip bombClip;
    private AudioInputStream meleeSound;
    private Clip meleeClip;

    public SoundPlayer() {
        //loads the songs
        try {
            menuSong = AudioSystem.getAudioInputStream(new File("sound/tritrix.wav"));
            gameSong = AudioSystem.getAudioInputStream(new File("sound/dodge.wav"));

        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        try {
            menuSongClip = AudioSystem.getClip();
            menuSongClip.open(menuSong);
            gameSongClip = AudioSystem.getClip();
            gameSongClip.open(gameSong);
            soundController = (FloatControl) menuSongClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            soundController = (FloatControl) gameSongClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
        } catch (LineUnavailableException | IOException e) {
            throw new RuntimeException(e);
        }

        try {
            jumpSound = AudioSystem.getAudioInputStream(new File("sound/jump.wav"));
            jumpClip = AudioSystem.getClip();
            jumpClip.open(jumpSound);
            soundController = (FloatControl) jumpClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            hurtSound = AudioSystem.getAudioInputStream(new File("sound/hurt.wav"));
            hurtClip = AudioSystem.getClip();
            hurtClip.open(hurtSound);
            soundController = (FloatControl) hurtClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            transformSound = AudioSystem.getAudioInputStream(new File("sound/transform.wav"));
            transformClip = AudioSystem.getClip();
            transformClip.open(transformSound);
            soundController = (FloatControl) transformClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            gloveSound = AudioSystem.getAudioInputStream(new File("sound/glove.wav"));
            gloveClip = AudioSystem.getClip();
            gloveClip.open(gloveSound);
            soundController = (FloatControl) gloveClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            meleeSound = AudioSystem.getAudioInputStream(new File("sound/melee.wav"));
            meleeClip = AudioSystem.getClip();
            meleeClip.open(meleeSound);
            soundController = (FloatControl) meleeClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            gloveSound = AudioSystem.getAudioInputStream(new File("sound/bomb.wav"));
            gloveClip = AudioSystem.getClip();
            gloveClip.open(gloveSound);
            soundController = (FloatControl) gloveClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
            buttonBeep = AudioSystem.getAudioInputStream(new File("sound/buttonBeep.wav"));
            buttonClip = AudioSystem.getClip();
            buttonClip.open(buttonBeep);
            soundController = (FloatControl) buttonClip.getControl(FloatControl.Type.MASTER_GAIN);
            soundController.setValue(-10f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    //starters and players
    public void startMenuMusic() {
        menuSongClip.start();
        menuSongClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void startGameMusic() {
        gameSongClip.start();
        gameSongClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playButtonBeep() {
        buttonClip.setFramePosition(0);
        buttonClip.start();
    }

    public void playJumpSound() {
        jumpClip.setFramePosition(0);
        jumpClip.start();
    }

    public void playHurtSound() {
        hurtClip.setFramePosition(0);
        hurtClip.start();
    }

    public void playTransformSound() {
        transformClip.setFramePosition(0);
        transformClip.start();
    }

    public void playGloveSound() {
        gloveClip.setFramePosition(0);
        gloveClip.start();
    }

    public void playMeleeSound() {
        meleeClip.setFramePosition(0);
        meleeClip.start();
    }

    public void playBombSound() {
        gloveClip.setFramePosition(0);
        gloveClip.start();
    }

    //music controllers
    public void stopMenuMusic() {
        menuSongClip.stop();
    }

    public void reduceGameSongVolume() {
        soundController = (FloatControl) gameSongClip.getControl(FloatControl.Type.MASTER_GAIN);
        soundController.setValue(-20f);
    }

    public void increaseGameSongVolume() {
        soundController = (FloatControl) gameSongClip.getControl(FloatControl.Type.MASTER_GAIN);
        soundController.setValue(-10f);

    }
}
