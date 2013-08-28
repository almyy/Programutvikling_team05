package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 9:48 AM
 */
public abstract class GameScreen implements Screen {

    protected PVU game;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;

    public GameScreen(PVU game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch = new SpriteBatch();
    }

    protected abstract void draw(float delta);
    protected abstract void update(float delta);
    protected abstract void cleanUp();

    protected void clearCamera(float r, float g, float b, float a) {
        camera.update();
        camera.apply(Gdx.gl10);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(r, g, b, a);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render(float delta) {
        float deltaUpdate =  (delta > 0.1f) ? 0.1f : delta;
        update(deltaUpdate);
        draw(delta);
    }

    @Override
    public void dispose() {
        cleanUp();
        batch.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
