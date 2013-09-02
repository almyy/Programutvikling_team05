package no.hist.gruppe5.pvu.dialogdrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class DialogDrawer {

    private String code = "Hei efje fjehfj wjkef jkew jhwe jkf jke whf jkew hf k j w e h f k h w ejk fhwej kfhkjweh fjkhwekj fjkwe fkjwhef jkhewfjkfhe wjkfehjfew lekfe fkjfekjwklfkl  weflkwef lwklef fnejf ej fkje kjef kjehf kjef kjehf jkehf jehf jehf je fhefj hef efklj ";
    private TextField textfield;
    private SpriteBatch batch;
    private Label codeOutput;
    private Skin textboxskin;
    private TextFieldStyle textfieldstyle;
    private LabelStyle labelStyle;
    private Texture tex;

    public DialogDrawer(SpriteBatch batch) {
        this.batch = batch;
        setVariables();
    }

    public void draw(float delta) {
        textfield.draw(batch, 1f);
        codeOutput.draw(batch, 1f);
    }

    public void setVariables() {
        labelStyle = new LabelStyle(Assets.primaryFont, Color.BLACK);
        tex = new Texture(Gdx.files.internal("data/DialogTexture.png"));
        textfieldstyle = new TextFieldStyle();
        textboxskin = new Skin();
        textboxskin.add("textfieldback", new TextureRegion(tex, 1, 1, 190, 56));
        labelStyle.font.setScale(0.3f);
        textfieldstyle.font = Assets.primaryFont;
        textfieldstyle.background = textboxskin.getDrawable("textfieldback");
        textfield = new TextField("", textfieldstyle);
        textfield.setHeight(PVU.GAME_HEIGHT / 3);
        textfield.setWidth(PVU.GAME_WIDTH);
        codeOutput = new Label(code, labelStyle);
        codeOutput.setPosition(6, -37);
        codeOutput.setWrap(true);
        codeOutput.setHeight((PVU.GAME_HEIGHT));
        codeOutput.setWidth(PVU.GAME_WIDTH - 10);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}