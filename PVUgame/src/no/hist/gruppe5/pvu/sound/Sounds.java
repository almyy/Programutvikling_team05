package no.hist.gruppe5.pvu.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import no.hist.gruppe5.pvu.Settings;

public class Sounds {

    private static Music music = Gdx.audio.newMusic(Gdx.files.internal("data/chiptune-loop-2.mp3"));
    private Sound sound;
    
    private Runnable thread = new Runnable() {

        @Override
        public void run() {
            sound.play(0.1f);
        }
    };
    
    private Runnable thread2 = new Runnable() {

        @Override
        public void run() {
            sound.play(0.4f);
        }
    };

    public static void playMusic() {
        if (Settings.GLOBAL_SOUND && !music.isPlaying()) {
            music.setVolume(0.6f);
            music.setLooping(true);
            music.play();
        }
    }

    public static void stopMusic() {
        if (!Settings.GLOBAL_SOUND) {
            music.pause();
        }
    }

    public void playSound(int i) {
        if (Settings.GLOBAL_SOUND) {
            if (i == 0) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/big_shot.mp3"));
                thread.run();
            }
            if (i == 1) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/explode.mp3"));
                thread.run();
            }
            if (i == 2) {
                music.play();
                music.isLooping();
            }
            if (i == 3) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/book_close.mp3"));
                thread2.run();
            }
            if (i == 4) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/book_page_turn.mp3"));
                thread2.run();
            }
        }
    }
}
