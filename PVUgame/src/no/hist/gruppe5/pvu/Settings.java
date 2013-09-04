package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import no.hist.gruppe5.pvu.sound.Sounds;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 9/4/13
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class Settings {

    public static boolean GLOBAL_SOUND = true;
    public static String PLAYER_NAME = "PLAYER";

    public static void setPlayerName(String newName) {
        PLAYER_NAME = newName;
    }

    public static void toggleSound() {
        GLOBAL_SOUND = (GLOBAL_SOUND) ? false : true;
        updateSound();
        Gdx.app.log("pvu.Settings" , "Sound set to: " + GLOBAL_SOUND);
    }

    public static void setSound(boolean bool) {
        GLOBAL_SOUND = bool;
        updateSound();
        Gdx.app.log("pvu.Settings" , "Sound set to: " + GLOBAL_SOUND);
    }

    private static void updateSound() {
        if(GLOBAL_SOUND) {
            Sounds.playMusic();
        } else {
            Sounds.stopMusic();
        }

    }
}
