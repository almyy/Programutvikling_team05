package no.hist.gruppe5.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/big_shot.mp3"));
    private Sound sound2 = Gdx.audio.newSound(Gdx.files.internal("data/background_music.mp3"));
    
    public void playSound(int i){
        if(i == 0){
            sound.play(1.0f);
        }
        if(i == 1){
            sound2.play(1.0f);
            sound2.loop();
        }
    }
}
