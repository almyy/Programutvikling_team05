package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class PVU extends Game {

    public static int GAME_WIDTH = 960;
    public static int GAME_HEIGHT = 580;

	@Override
	public void create() {
        setScreen(new MainScreen(this));
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
        super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
	public static void main(String[] args) {
            LwjglApplicationConfiguration a = new LwjglApplicationConfiguration();
            a.width = PVU.GAME_WIDTH;
            a.height = PVU.GAME_HEIGHT;
            a.fullscreen = false;
            
            new LwjglApplication(new PVU(), a);
	}
}
