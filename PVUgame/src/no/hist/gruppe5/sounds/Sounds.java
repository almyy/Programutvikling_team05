package no.hist.gruppe5.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/big_shot.mp3"));
    
    public void playSound(){
        sound.play(1.0f);
    }
}
