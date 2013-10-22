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
import java.util.ArrayList;
import java.util.StringTokenizer;
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
    private LabelStyle mCorrectLabelStyle;
    private LabelStyle mWrongLabelStyle;
    private ArrayList<Label> mLabels = new ArrayList<>();

    public ReqFinderScreen(PVU pvu) {
        super(pvu);
        try {
            mCaseText = Assets.readFile("data/case.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReqFinderScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReqFinderScreen.class.getName()).log(Level.SEVERE, null, ex);
        }

        BitmapFont kopiert = new BitmapFont(
                Gdx.files.internal("data/LucidaBitmap10px.fnt"),
                Gdx.files.internal("data/LucidaBitmap10px_0.png"), false);
        mLabelStyle = new LabelStyle(kopiert, Color.BLACK);

        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true, batch);
        mStage.setViewport(mStage.getWidth(), mStage.getHeight(), true, 0, 0, mStage.getWidth(), mStage.getHeight());
        StringTokenizer st = new StringTokenizer(mCaseText);
        mLabelStyle.font.scale(1.5f);
        Label initLabel = new Label("Hei", mLabelStyle);
        initLabel.setPosition(0, PVU.SCREEN_HEIGHT - initLabel.getHeight());
        System.out.println(initLabel.getX() + " " + initLabel.getY());
        mLabels.add(initLabel);
        mStage.addActor(initLabel);
        float labelLength = 0;
        while (st.hasMoreTokens()) {
            mLabels.add(new Label(st.nextToken(" "), mLabelStyle));
            System.out.println(labelLength);
            if (labelLength+mLabels.get(mLabels.size()-1).getWidth() > PVU.SCREEN_WIDTH-5) {
                mLabels.get(mLabels.size() - 1).setPosition(0, mLabels.get(mLabels.size() - 2).getY() - mLabels.get(mLabels.size()-1).getHeight());
                labelLength = 0;
            } else {
                mLabels.get(mLabels.size() - 1).setPosition(mLabels.get(mLabels.size() - 2).getX() + mLabels.get(mLabels.size() - 2).getWidth() + 5, mLabels.get(mLabels.size() - 2).getY());
            }
            labelLength = mLabels.get(mLabels.size()-1).getX()+mLabels.get(mLabels.size()-1).getWidth();
            mStage.addActor(mLabels.get(mLabels.size() - 1));
        }



        /*
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
         */



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
