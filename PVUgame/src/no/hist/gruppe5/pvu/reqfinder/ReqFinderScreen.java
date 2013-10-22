package no.hist.gruppe5.pvu.reqfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author Martin
 */
public class ReqFinderScreen extends GameScreen {

    private String mCaseText;
    private Group mTextGroup;
    private Stage mStage;
    private Label mLabel;
    private LabelStyle mLabelStyle;

    public ReqFinderScreen(PVU pvu) {
        super(pvu);
        try {
            mCaseText = Assets.readFile("data/case.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReqFinderScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReqFinderScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
        mStage.setViewport(mStage.getWidth(), mStage.getHeight(), true, 0, 0, mStage.getWidth(), mStage.getHeight());

        
        BitmapFont kopiert = new BitmapFont(
                Gdx.files.internal("data/LucidaBitmap10px.fnt"),
                Gdx.files.internal("data/LucidaBitmap10px_0.png"), false);
        mLabelStyle = new LabelStyle(kopiert, Color.BLACK);
        mLabel = new Label("jalla", mLabelStyle);
        mLabel.setFillParent(true);
        mLabel.setWrap(true);
        mLabel.setWidth(PVU.SCREEN_WIDTH);
        mLabelStyle.font.setScale(2f);
        mLabel.setAlignment(Align.top | Align.left);
        mLabel.setText(mCaseText);

        mStage.addActor(mLabel);
        System.out.println(mStage.getHeight() + " " + mStage.getWidth());
        System.out.println(mLabel.getHeight() + " " + mStage.getWidth());
        System.out.println(mLabelStyle.font + " " + Assets.primaryFont10px);

    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important
        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void cleanUp() {
    }
}
