package no.hist.gruppe5.pvu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import no.hist.gruppe5.pvu.book.BookScreen;
import no.hist.gruppe5.pvu.intro.IntroScreen;
import no.hist.gruppe5.pvu.mainroom.BurndownScreen;
import no.hist.gruppe5.pvu.mainroom.MainScreen;
import no.hist.gruppe5.pvu.mainroom.MinigameSelectorScreen;
import no.hist.gruppe5.pvu.mainroom.ScoreScreen;
import no.hist.gruppe5.pvu.visionshooter.VisionEndScreen;

/**
 * Created with IntelliJ IDEA. User: karl Date: 8/28/13 Time: 9:48 AM
 */
public abstract class GameScreen implements Screen {

    protected PVU game;
    protected SpriteBatch batch;
    protected OrthographicCamera camera;
    private Stage stage;
    private Skin skinPauseButton;
    private TextureAtlas atlas;
    private boolean running;
    private LabelStyle labelStyle;
    private Label pauseLabel;

    private TextButton[] pauseButtons;
    private int selectedButton;
    private TextButtonStyle selectedButtonStyle;
    private TextButtonStyle buttonStyle;

    private Texture mPauseOverlay;

    private Input input;

    public GameScreen(PVU game) {
        this.game = game;
        input = new Input();
        labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
        batch = new SpriteBatch();

        running = true;
        stage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        atlas = new TextureAtlas("data/menuButtons/menubuttons.pack");
        initPauseLayout();
        Gdx.input.setInputProcessor(stage);
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
        if (running) {
            float deltaUpdate = (delta > 0.1f) ? 0.1f : delta;
            update(deltaUpdate);
        }
        draw(delta);
        if (!running) {
            drawPauseMenu();
            checkPauseMenu();
        }
        checkEscapeButton();
        stage.draw();
    }

    private void checkEscapeButton() {
        if (input.back()) {
            if (this instanceof BookScreen
                    || this instanceof MinigameSelectorScreen
                    || this instanceof BurndownScreen
                    || this instanceof ScoreScreen
                    || this instanceof VisionEndScreen) {
                game.setScreen(PVU.MAIN_SCREEN);
            } else if (this instanceof IntroScreen) {
                return;
            } else {
                pauseButtons[selectedButton].setStyle(buttonStyle);
                selectedButton = 0;
                pauseButtons[selectedButton].setStyle(selectedButtonStyle);
                running = false;
            }
        }
    }

    private void checkPauseMenu() {
        if (input.back()) {
            running = true;
            stage.clear();
        } else if (input.up()) {
            pauseButtons[selectedButton].setStyle(buttonStyle);
            selectedButton = (selectedButton == 0) ? 0 : selectedButton - 1;
            pauseButtons[selectedButton].setStyle(selectedButtonStyle);
        } else if (input.down()) {
            pauseButtons[selectedButton].setStyle(buttonStyle);
            selectedButton = (selectedButton == pauseButtons.length - 1) ? pauseButtons.length - 1 : selectedButton + 1;
            pauseButtons[selectedButton].setStyle(selectedButtonStyle);
        } else if (input.action()) {
            checkSelectedMenuItem();
        }
    }

    private void checkSelectedMenuItem() {
        if (selectedButton == Button.RESUME.pos) {
            stage.clear();
            running = true;
        } else if (selectedButton == Button.SOUND.pos) {
            if (Settings.GLOBAL_SOUND) {
                pauseButtons[selectedButton].setText("Lyd av");
                Settings.setSound(false);
            } else {
                pauseButtons[selectedButton].setText("Lyd på");
                Settings.setSound(true);
            }
        } else if (selectedButton == Button.BACK.pos) {
            if (this instanceof MainScreen) {
                stage.clear();
                running = true;
            } else {
                game.setScreen(PVU.MAIN_SCREEN);
            }
        } else if (selectedButton == Button.EXIT.pos) {
            System.exit(0);
        }
    }

    private void drawPauseMenu() {
        batch.begin();

        // Create transparent overlay
        batch.draw(mPauseOverlay, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);

        batch.draw(Assets.introMainLogo, 55, 70);

        batch.end();
        for (TextButton button : pauseButtons) {
            stage.addActor(button);
        }
        stage.addActor(pauseLabel);
    }

    private void initPauseLayout() {
        buttonStyle = new TextButtonStyle();
        selectedButtonStyle = new TextButtonStyle();
        skinPauseButton = new Skin(atlas);
        buttonStyle.up = skinPauseButton.getDrawable("menubutton.up");
        buttonStyle.down = skinPauseButton.getDrawable("menubutton.down");
        buttonStyle.font = Assets.primaryFont10px;
        buttonStyle.fontColor = Color.BLACK;
        selectedButtonStyle.up = skinPauseButton.getDrawable("menubutton.down");
        selectedButtonStyle.down = skinPauseButton.getDrawable("menubutton.up");
        selectedButtonStyle.font = Assets.primaryFont10px;
        pauseLabel = new Label("PAUSE", labelStyle);
        pauseLabel.setPosition(PVU.SCREEN_WIDTH / 2 - 55, PVU.SCREEN_HEIGHT / 2);
        pauseLabel.setFontScale(3.9f);

        // Transparent overlay
        Pixmap pix = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(1f, 1f, 1f, 0.7f));
        pix.fill();

        mPauseOverlay = new Texture(pix);

        String[] pauseButtonText = {"Fortsett", "Til rommet", "Lyd på", "Exit"};
        pauseButtons = new TextButton[pauseButtonText.length];

        for (int i = 0; i < pauseButtonText.length; i++) {
            pauseButtons[i] = new TextButton(pauseButtonText[i], buttonStyle);
            pauseButtons[i].setWidth(190);
            pauseButtons[i].setHeight(40);
            pauseButtons[i].getLabel().setFontScale(2.5f);
            pauseButtons[i].setPosition(PVU.SCREEN_WIDTH / 2 - pauseButtons[i].getWidth() / 2, stage.getHeight() / 3 - i * pauseButtons[i].getHeight());
        }
    }

    @Override
    public void dispose() {
        cleanUp();
        batch.dispose();
    }

    private void resumeGame() {
        running = true;
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

    public boolean isGamePaused() {
        return !running;
    }

    private enum Button {

        RESUME(0), BACK(1), SOUND(2), EXIT(3);

        private int pos;

        private Button(int c) {
            pos = c;
        }

        public int pos() {
            return pos;
        }
    }
}
