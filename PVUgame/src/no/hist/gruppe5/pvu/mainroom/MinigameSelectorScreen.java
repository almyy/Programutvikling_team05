package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.coderacer.Code;
import no.hist.gruppe5.pvu.coderacer.CoderacerScreen;

public class MinigameSelectorScreen extends GameScreen {

    private String text;
    private Label codeOutput;
    private InputProcessor inputListener;
    private Stage stage;
    private Skin textboxskin;
    private Texture tex;
    private TextField.TextFieldStyle textfieldstyle;
    private TextField textfield;
    private Label.LabelStyle outputStyle;
    private String text2;
    private Label codeOutput2;
    private InputProcessor inputListener2;
    private TextField textfield2;
    private Label.LabelStyle outputStyle2;
    private String text3;
    private Label codeOutput3;
    private InputProcessor inputListener3;
    private TextField textfield3;
    private Label.LabelStyle outputStyle3;
    private String text4;
    private Label codeOutput4;
    private InputProcessor inputListener4;
    private TextField textfield4;
    private Label.LabelStyle outputStyle4;
    private String text5;
    private Label codeOutput5;
    private InputProcessor inputListener5;
    private TextField textfield5;
    private Label.LabelStyle outputStyle5;

    public MinigameSelectorScreen(PVU game) {
        super(game);

        stage = new Stage(PVU.GAME_WIDTH, PVU.GAME_HEIGHT, true, batch);

        tex = new Texture(Gdx.files.internal("data/DialogTexture.png"));
        textfieldstyle = new TextField.TextFieldStyle();
        textboxskin = new Skin();
        textboxskin.add("textfieldback", new TextureRegion(tex, 1, 1, 190, 56));

        textfieldstyle.font = Assets.primaryFont10px;
        textfieldstyle.background = textboxskin.getDrawable("textfieldback");
        textfield = new TextField("", textfieldstyle);
        textfield.setHeight(15);
        textfield.setWidth(PVU.GAME_WIDTH / 3);

        outputStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        codeOutput = new Label(text, outputStyle);
        codeOutput.setFontScale(0.4f);

        textfield.setPosition(PVU.GAME_WIDTH / 3, 90);
        codeOutput.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.8f));

        stage.addActor(textfield);
        stage.addActor(codeOutput);

        codeOutput2 = new Label(text2, outputStyle);
        codeOutput2.setFontScale(0.4f);

        textfield2 = new TextField("", textfieldstyle);
        textfield2.setHeight(15);
        textfield2.setWidth(PVU.GAME_WIDTH / 3);

        textfield2.setPosition(PVU.GAME_WIDTH / 3, 70);
        codeOutput2.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.7f));

        stage.addActor(textfield2);
        stage.addActor(codeOutput2);

        codeOutput3 = new Label(text3, outputStyle);
        codeOutput3.setFontScale(0.4f);

        textfield3 = new TextField("", textfieldstyle);
        textfield3.setHeight(15);
        textfield3.setWidth(PVU.GAME_WIDTH / 3);

        textfield3.setPosition(PVU.GAME_WIDTH / 3, 70);
        codeOutput3.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.7f));

        textfield3.setPosition(PVU.GAME_WIDTH / 3, 50);
        codeOutput3.setPosition(PVU.GAME_WIDTH / 2
                - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.6f));

        stage.addActor(textfield3);
        stage.addActor(codeOutput3);

        codeOutput4 = new Label(text4, outputStyle);
        codeOutput4.setFontScale(0.4f);

        textfield4 = new TextField("", textfieldstyle);
        textfield4.setHeight(15);
        textfield4.setWidth(PVU.GAME_WIDTH / 3);

        textfield4.setPosition(PVU.GAME_WIDTH / 3, 70);
        codeOutput4.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.7f));
        
        textfield4.setPosition(PVU.GAME_WIDTH / 3, 30);
        codeOutput4.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.5f));
        
        stage.addActor(textfield4);
        stage.addActor(codeOutput4);

        codeOutput5 = new Label(text5, outputStyle);
        codeOutput5.setFontScale(0.4f);

        textfield5 = new TextField("", textfieldstyle);
        textfield5.setHeight(15);
        textfield5.setWidth(PVU.GAME_WIDTH / 3);

        textfield5.setPosition(PVU.GAME_WIDTH / 3, 70);
        codeOutput5.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.7f));

        textfield5.setPosition(PVU.GAME_WIDTH / 3, 10);
        codeOutput5.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.4f));

        stage.addActor(textfield5);
        stage.addActor(codeOutput5);

        //  inputListener = new CoderacerScreen.inputListener();
        Gdx.input.setInputProcessor(inputListener);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        stage.draw();
        batch.begin();
        textfield.draw(batch, delta);
        batch.end();
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void cleanUp() {
    }

    /*
     * private void updateOutput() { codeOutput.setText(code.getLeft());
     * finishedCode.setText(code.getCorrect());
     * codeOutput.setPosition(PVU.GAME_WIDTH / 2 - codeOutput.getPrefWidth() /
     * 2, (PVU.GAME_HEIGHT * 0.8f)); finishedCode.setPosition(PVU.GAME_WIDTH / 2
     * - finishedCode.getPrefWidth() / 2, (PVU.GAME_HEIGHT * 0.2f)); }
     */
}
