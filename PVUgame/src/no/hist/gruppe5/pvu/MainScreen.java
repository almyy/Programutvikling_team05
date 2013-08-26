package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/26/13
 * Time: 10:56 PM
 */
public class MainScreen implements Screen {

    private PVU mGame;

    private boolean left = true;

    private OrthographicCamera mCamera;
    private SpriteBatch mBatch;
    private Texture mTestTexture;
    private Sprite mTestSprite;

    public MainScreen(PVU game) {
        this.mGame = game;

        float w = PVU.GAME_WIDTH;
        float h = PVU.GAME_HEIGHT;

        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 1, h/w);
        mBatch = new SpriteBatch();

        mTestTexture = new Texture(Gdx.files.internal("data/logo.png"));
        mTestTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion region = new TextureRegion(mTestTexture, 0, 0, 512, 512);

        mTestSprite = new Sprite(region);
        mTestSprite.setSize(0.5f, 0.1f);
        mTestSprite.setOrigin(mTestSprite.getWidth() / 2, mTestSprite.getHeight() / 2);
        mTestSprite.setPosition(mTestSprite.getWidth()/2, mCamera.viewportHeight/1.9f);
    }

    private void draw(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.begin();
        mTestSprite.draw(mBatch);
        mBatch.end();
    }

    private void update(float delta) {
        if (mTestSprite.getX() < 0f && left) {
            left = false;
        } else if (mTestSprite.getX() + mTestSprite.getWidth() > 1f && !left) {
            left = true;
        }
        float dx = (left) ? -0.1f : 0.1f;
        mTestSprite.setPosition(mTestSprite.getX() + dx * delta, mTestSprite.getY());
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw(delta);
    }

    @Override
    public void resize(int width, int height) {
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

    @Override
    public void dispose() {
        mBatch.dispose();
        mTestTexture.dispose();
    }
}
