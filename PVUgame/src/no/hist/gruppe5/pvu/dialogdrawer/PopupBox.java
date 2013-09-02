package no.hist.gruppe5.pvu.dialogdrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class PopupBox {

    private String code = "Press E";
    private TextField textfield;
    private SpriteBatch batch;
    private Label codeOutput;
    private Skin textboxskin;
    private TextField.TextFieldStyle textfieldstyle;
    private Label.LabelStyle labelStyle;
    private Texture tex;

    public PopupBox(SpriteBatch batch) {
        this.batch = batch;
        setVariables();
    }

    public void draw(float delta) {
        textfield.draw(batch, 1f);
        codeOutput.draw(batch, 1f);
    }

    public void setVariables() {
        labelStyle = new Label.LabelStyle(Assets.primaryFont16px, Color.BLACK);
        tex = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));
        textfieldstyle = new TextField.TextFieldStyle();
        textboxskin = new Skin();
        textboxskin.add("textfieldback", new TextureRegion(tex, 1, 1, 190, 56));
        labelStyle.font.setScale(0.3f);
        textfieldstyle.font = Assets.primaryFont16px;
        textfieldstyle.background = textboxskin.getDrawable("textfieldback");
        textfield = new TextField("", textfieldstyle);
        codeOutput = new Label(code, labelStyle);
        codeOutput.setWrap(true);
    }

    public void setXY(int x, int y) {
        codeOutput.setPosition(x + 4, y);
        textfield.setPosition(x, y);
    }

    public void setHeightWidth(String text) {
        code = text;
        codeOutput = new Label(code, labelStyle);
        textfield.setWidth(Assets.primaryFont16px.getBounds(code).width + 12);
        textfield.setHeight(Assets.primaryFont16px.getBounds(code).height + 4);
        codeOutput.setWidth(Assets.primaryFont16px.getBounds(code).width + 4);
        codeOutput.setHeight(Assets.primaryFont16px.getBounds(code).height + 4);

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
