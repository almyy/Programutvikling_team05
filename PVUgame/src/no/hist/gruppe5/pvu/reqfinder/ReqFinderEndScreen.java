package no.hist.gruppe5.pvu.reqfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.PVU;

public class ReqFinderEndScreen extends GameScreen {
    
    private Label mLabel;
    private LabelStyle mLabelStyle;
    private Stage mStage;
    private BitmapFont mFont;
    private String mText;
    private Input mInput;
    public ReqFinderEndScreen(PVU pvu, int result, int max) {
        super(pvu);
        mInput = new Input();
        mText = "Spillet er over!\n\nDu fikk " + result + " av " + max + "\n\n\nTrykk space for å gå tilbake";
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mFont = new BitmapFont(
                Gdx.files.internal("data/LucidaBitmap10px.fnt"),
                Gdx.files.internal("data/LucidaBitmap10px_0.png"), false);
        mLabelStyle = new LabelStyle(mFont, Color.BLACK);
        mLabelStyle.font.scale(3f);
        mLabel = new Label(mText, mLabelStyle);
        mLabel.setAlignment(Align.center);
        mLabel.setWrap(true);
        mLabel.setFillParent(true);
        mStage.addActor(mLabel);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if(mInput.action()) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void cleanUp() {
    }
    
}
