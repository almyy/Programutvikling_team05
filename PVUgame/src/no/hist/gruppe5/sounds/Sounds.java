package no.hist.gruppe5.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import no.hist.gruppe5.pvu.Settings;

public class Sounds {

    private Sound sound;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("data/background_music.mp3"));
    private Runnable test = new Runnable() {

        @Override
        public void run() {
            sound.play(0.1f);
        }
    };

    public void playSound(int i) {
        if (Settings.GLOBAL_SOUND) {
            if (i == 0) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/big_shot.mp3"));
                test.run();
            }
            if (i == 1) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/explode.mp3"));
                test.run();
            }
            if (i == 2) {
                music.play();
                music.isLooping();
            }
            if (i == 3) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/book_close.mp3"));
                test.run();
            }
            if (i == 4) {
                sound = Gdx.audio.newSound(Gdx.files.internal("data/book_page_turn.mp3"));
                test.run();
            }
        }
    }
}
