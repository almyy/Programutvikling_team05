package no.hist.gruppe5.pvu.dialogdrawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class PopupBox {

    private static final float PADDING = 4f;
    private static final float GAME_TO_SCREEN = 5f;
    private static final float SCREEN_TO_GAME = 1/5f;

    private Stage mStage;

    private String mText = "";
    private TextField mTextBackground;
    private SpriteBatch mBatch;
    private Label mTextLabel;
    private Label.LabelStyle mLabelStyle;
    private Texture mTexture;
    private float FONT_SCALE = 2f;

    public PopupBox(SpriteBatch batch) {
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);

        this.mBatch = batch;
        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mTexture = new Texture(Gdx.files.internal("data/DialogTextureWithoutFrame.png"));

        setVariables();
    }

    private void setVariables() {
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        Skin textBoxSkin = new Skin();
        textBoxSkin.add("text_field_back", new TextureRegion(mTexture, 1, 1, 190, 56));
        textFieldStyle.font = Assets.primaryFont10px;
        textFieldStyle.background = textBoxSkin.getDrawable("text_field_back");

        mTextBackground = new TextField("", textFieldStyle);
        mTextLabel = new Label(mText, mLabelStyle);
        mTextLabel.setFontScale(FONT_SCALE);
        mTextLabel.setWrap(true);

        mStage.addActor(mTextBackground);
        mStage.addActor(mTextLabel);
    }

    public void draw(float delta) {
        mStage.draw();
    }

    public void setXY(float x, float y) {
        mTextLabel.setPosition((x + 4f) * GAME_TO_SCREEN, (y + 17f) * GAME_TO_SCREEN);
        mTextBackground.setPosition((x * GAME_TO_SCREEN) + 14.5f, (y + 14.5f) * GAME_TO_SCREEN);

        if(x > PVU.GAME_WIDTH / 2) {
            mTextLabel.setX(mTextLabel.getX() - mTextBackground.getWidth());
            mTextBackground.setX(mTextBackground.getX() - mTextBackground.getWidth());
        }

    }

    private void positionTextBackground() {
        float height = mLabelStyle.font.getBounds(mText).height * FONT_SCALE * 2;
        float width = mLabelStyle.font.getBounds(mText).width * FONT_SCALE * 1.05f;
        mTextBackground.setWidth(width + PADDING);
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
