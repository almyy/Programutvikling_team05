package no.hist.gruppe5.pvu.coderacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA. User: karl Date: 8/28/13 Time: 10:49 AM
 */
public class CoderacerScreen extends GameScreen {

    private Label finishedCode;
    private Label codeOutput;
    private Label codeInput;
    private String text = "";
    private InputProcessor inputListener;
    private Code code = new Code();
    private Stage stage;
    private Group group;

    public CoderacerScreen(PVU game) {
        super(game);

        stage = new Stage(PVU.GAME_WIDTH, PVU.GAME_HEIGHT, true, batch);

        LabelStyle outputStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
        codeOutput = new Label(code.getCode(), outputStyle);
        codeOutput.setFontScale(2f);

        LabelStyle finishedStyle = new LabelStyle(Assets.primaryFont10px, Color.RED);
        finishedCode = new Label("", finishedStyle);
        finishedCode.setFontScale(0.4f);


        LabelStyle inputStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
        inputStyle.font.scale(0.1f);
        codeInput = new Label(text, inputStyle);
        codeInput.setWrap(true);
        codeInput.setWidth(PVU.GAME_WIDTH);
        codeInput.setHeight(PVU.GAME_HEIGHT);
        codeInput.setAlignment(Align.bottom);

        group = new Group();
        group.addActor(finishedCode);
        group.addActor(codeOutput);
        group.setPosition((PVU.GAME_WIDTH/2) - group.getWidth()/2, PVU.GAME_HEIGHT/2);
        
        stage.addActor(group);
        stage.addActor(codeInput);


        inputListener = new inputListener();
        Gdx.input.setInputProcessor(inputListener);

    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important
        stage.draw();
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void cleanUp() {
    }

    private class inputListener implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Keys.BACKSPACE && text.length() > 0) {
                text = removeLastChar(text);
                codeInput.setText(text);
                return true;
            }
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            if (character > 31) {
                text += character;
                codeInput.setText(text);

            }
            if (code.equals(text)) {
                text = "";
                codeOutput.setText(code.getCode());
                codeInput.setText(text);
                
                updateOutput(character);
            }
            return true;
        }

        @Override
        public boolean touchDown(int x, int y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int x, int y, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int x, int y, int pointer) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    private void updateOutput(char character) {
        finishedCode.setText(code.getCorrect());
        codeOutput.setText(codeOutput.getText().subSequence(code.getCharCounter(), codeOutput.getText().length()));
    }
}
