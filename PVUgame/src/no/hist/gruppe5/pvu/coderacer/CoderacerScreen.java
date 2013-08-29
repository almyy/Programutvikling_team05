package no.hist.gruppe5.pvu.coderacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import no.hist.gruppe5.pvu.Assets;
import static no.hist.gruppe5.pvu.Assets.*;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 10:49 AM
 */
public class CoderacerScreen extends GameScreen {

    private String code = "Hei, jeg heter Martin. Skriv dette så fort som mulig er du snill.";
    private Label codeOutput;
    private Stage stage;
    private Table table;
    public CoderacerScreen(PVU game) {
        super(game);
        
        LabelStyle labelStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
        codeOutput = new Label(code, labelStyle);
        codeOutput.setFontScale(0.4f);
        codeOutput.setPosition((PVU.GAME_WIDTH/2)-codeOutput.getPrefWidth()/2, PVU.GAME_HEIGHT*0.9f);
        
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important

        batch.begin();
        codeOutput.draw(batch, 1f);
        batch.end();
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void cleanUp() {
    }

}
