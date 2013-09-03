package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.coderacer.Code;
import no.hist.gruppe5.pvu.coderacer.CoderacerScreen;
import no.hist.gruppe5.pvu.visionshooter.VisionScreen;

public class MinigameSelectorScreen extends GameScreen {

    private String text = "Minigame 1";
    private Label codeOutput;
    private InputProcessor inputListener;
    private Stage stage;
    private Skin textboxskin;
    private Texture tex;
    private TextButtonStyle textbuttonstyle;
    private TextField textfield;
    private Label.LabelStyle outputStyle;
    private String text2 = "Minigame 2";
    private Label codeOutput2;
    private InputProcessor inputListener2;
    private TextField textfield2;
    private Label.LabelStyle outputStyle2;
    private String text3 = "Minigame 3";
    private Label codeOutput3;
    private InputProcessor inputListener3;
    private TextField textfield3;
    private Label.LabelStyle outputStyle3;
    private String text4 = "Minigame 4";
    private Label codeOutput4;
    private InputProcessor inputListener4;
    private TextField textfield4;
    private Label.LabelStyle outputStyle4;
    private String text5 = "Minigame 5";
    private Label codeOutput5;
    private InputProcessor inputListener5;
    private TextField textfield5;
    private Label.LabelStyle outputStyle5;
    private TextButton button;
    private Skin buttonskin;

    public MinigameSelectorScreen(final PVU game) {
        super(game);

        stage = new Stage(PVU.GAME_WIDTH, PVU.GAME_HEIGHT, true, batch);

        Button button1 = makeButton(text);
        button1.setPosition(68, 88);
        stage.addActor(button1);

        button1.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                
                    game.setScreen(new VisionScreen(game));
            }
        });

        Button button2 = makeButton(text2);
        button2.setPosition(68, 75);
        stage.addActor(button2);

        button2.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new CoderacerScreen(game));
            }
        });

        Button button3 = makeButton(text3);
        button3.setPosition(68, 62);
        stage.addActor(button3);

        button3.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("LOL3");
            }
        });

        Button button4 = makeButton(text4);
        button4.setPosition(68, 49);
        stage.addActor(button4);

        button4.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("LOL4");
            }
        });

        Button button5 = makeButton(text5);
        button5.setPosition(68, 36);
        stage.addActor(button5);

        button5.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("LOL5");
            }
        });
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        stage.getSpriteBatch().draw(Assets.msPcBackground, 0, 0);
        batch.end();
        stage.draw();
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void cleanUp() {
    }

    private TextButton makeButton(String text) {
        Gdx.input.setInputProcessor(stage);
        tex = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));
        buttonskin = new Skin();
        textbuttonstyle = new TextButton.TextButtonStyle();
        textbuttonstyle.font = Assets.primaryFont10px;
        buttonskin.add("textfieldback", new TextureRegion(tex, 10, 10));
        Drawable d = buttonskin.getDrawable("textfieldback");
        textbuttonstyle.up = d;
        textbuttonstyle.down = d;
        button = new TextButton(text, textbuttonstyle);
        return button;
    }
}
