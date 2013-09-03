package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created with IntelliJ IDEA. User: karl Date: 8/28/13 Time: 9:48 AM
 */
public abstract class GameScreen implements Screen {

    protected PVU game;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    private Button soundButton;
    private Button noSoundButton;
    private Stage stage;
    private long timeSinceLastAction;

    public GameScreen(PVU game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch = new SpriteBatch();

        timeSinceLastAction = 0;
        stage = new Stage(PVU.GAME_WIDTH * 2.7f, PVU.GAME_HEIGHT * 2.7f, true);
        initSoundButton();
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
        float deltaUpdate = (delta > 0.1f) ? 0.1f : delta;
        update(deltaUpdate);
        draw(delta);

        if (TimeUtils.millis() - timeSinceLastAction > 400l) {
            if (soundButton.isPressed()) {
                soundButton.remove();
                stage.addActor(noSoundButton);
                timeSinceLastAction = TimeUtils.millis();
            }else if(noSoundButton.isPressed()){
                noSoundButton.remove();
                stage.addActor(soundButton);
                timeSinceLastAction = TimeUtils.millis();
            }
        }
        stage.draw();
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

    public void initSoundButton() {
        TextureAtlas atlas = new TextureAtlas("data/menuButtons/menubuttons.pack");
        Skin skin = new Skin(atlas);
        noSoundButton = new Button(skin.getDrawable("nosound.up"));
        soundButton = new Button(skin.getDrawable("sound.up"));
        soundButton.setPosition(PVU.GAME_WIDTH * 2.7f - 25, PVU.GAME_HEIGHT * 2.7f - 25);
        noSoundButton.setPosition(PVU.GAME_WIDTH * 2.7f - 25, PVU.GAME_HEIGHT * 2.7f - 25);
        stage.addActor(soundButton);
        Gdx.input.setInputProcessor(stage);
    }
}
