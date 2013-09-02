package no.hist.gruppe5.pvu.dialogdrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class PopupBox {

    private static final float PADDING = 4f;

    private String code = "Press E";
    private TextField textfield;
    private SpriteBatch batch;
    private Label codeOutput;
    private Skin textboxskin;
    private TextField.TextFieldStyle textfieldstyle;
    private Label.LabelStyle labelStyle;
    private Texture tex;
    private float FONT_SCALE = 0.5f;

    public PopupBox(SpriteBatch batch) {
        this.batch = batch;
        setVariables();
    }

    private void setVariables() {
        labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        tex = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));
        textfieldstyle = new TextField.TextFieldStyle();
        textboxskin = new Skin();
        textboxskin.add("textfieldback", new TextureRegion(tex, 1, 1, 190, 56));
        textfieldstyle.font = Assets.primaryFont10px;
        textfieldstyle.background = textboxskin.getDrawable("textfieldback");
        textfield = new TextField("", textfieldstyle);
        codeOutput = new Label(code, labelStyle);
        codeOutput.setFontScale(FONT_SCALE);
        codeOutput.setWrap(true);
    }

    public void draw(float delta) {
        textfield.draw(batch, 1f);
        codeOutput.draw(batch, 1f);
    }

    public void setXY(float x, float y) {
        codeOutput.setPosition(x + 4f, y + 17f);
        textfield.setPosition(x, y + 17f);

        if((codeOutput.getX() + Assets.primaryFont10px.getBounds(code).width * FONT_SCALE) > PVU.GAME_WIDTH) {
            codeOutput.setX(codeOutput.getX() - Assets.primaryFont10px.getBounds(code).width * FONT_SCALE);
            textfield.setX(codeOutput.getX() - PADDING);
            positionTextBackground();
        }
    }

    private void positionTextBackground() {
        float height = Assets.primaryFont10px.getBounds(code).height * FONT_SCALE;
        float width = Assets.primaryFont10px.getBounds(code).width * FONT_SCALE;
        textfield.setWidth(width + PADDING * 3f);
        textfield.setHeight(height + PADDING);
        codeOutput.setWidth(Assets.primaryFont10px.getBounds(code).width);
        codeOutput.setHeight(Assets.primaryFont10px.getBounds(code).height + 2f);
    }

    public void setXY(Vector2 vec) {
        setXY(vec.x, vec.y);
    }

    public void setText(String text) {
        code = text;
        codeOutput.setText(text);
        codeOutput.setStyle(labelStyle);

        positionTextBackground();

    }

    public String getText() {
        return code;
    }
}
