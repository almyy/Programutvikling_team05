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

    private String mText = "";
    private TextField mTextBackground;
    private SpriteBatch mBatch;
    private Label mTextLabel;
    private Label.LabelStyle mLabelStyle;
    private Texture mTexture;
    private float FONT_SCALE = 0.5f;

    public PopupBox(SpriteBatch batch) {
        this.mBatch = batch;
        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mTexture = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));

        setVariables();
    }

    private void setVariables() {
        TextField.TextFieldStyle textfieldStyle = new TextField.TextFieldStyle();
        Skin textBoxSkin = new Skin();
        textBoxSkin.add("text_field_back", new TextureRegion(mTexture, 1, 1, 190, 56));
        textfieldStyle.font = Assets.primaryFont10px;
        textfieldStyle.background = textBoxSkin.getDrawable("text_field_back");

        mTextBackground = new TextField("", textfieldStyle);
        mTextLabel = new Label(mText, mLabelStyle);
        mTextLabel.setFontScale(FONT_SCALE);
        mTextLabel.setWrap(true);
    }

    public void draw(float delta) {
        mTextBackground.draw(mBatch, 1f);
        mTextLabel.setFontScale(FONT_SCALE);
        mTextLabel.draw(mBatch, 1f);
    }

    public void setXY(float x, float y) {
        mTextLabel.setPosition(x + 4f, y + 17f);
        mTextBackground.setPosition(x, y + 17f);

        if((mTextLabel.getX() + Assets.primaryFont10px.getBounds(mText).width * FONT_SCALE) > PVU.GAME_WIDTH) {
            mTextLabel.setX(mTextLabel.getX() - Assets.primaryFont10px.getBounds(mText).width * FONT_SCALE);
            mTextBackground.setX(mTextLabel.getX() - PADDING);
            positionTextBackground();
        }
    }

    private void positionTextBackground() {
        float height = Assets.primaryFont10px.getBounds(mText).height * FONT_SCALE;
        float width = Assets.primaryFont10px.getBounds(mText).width * FONT_SCALE;
        mTextBackground.setWidth(width + PADDING * 3f);
        mTextBackground.setHeight(height + PADDING);
        mTextLabel.setWidth(Assets.primaryFont10px.getBounds(mText).width);
        mTextLabel.setHeight(Assets.primaryFont10px.getBounds(mText).height + PADDING);
    }

    public void setXY(Vector2 vec) {
        setXY(vec.x, vec.y);
    }

    public void setText(String text) {
        mText = text;
        mTextLabel.setText(text);
        mTextLabel.setStyle(mLabelStyle);

        positionTextBackground();

    }

    public String getText() {
        return mText;
    }
}
