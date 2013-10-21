package no.hist.gruppe5.pvu.reqfinder;

import com.badlogic.gdx.graphics.Color;
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
        
        mLabelStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);
        
        
        
        
        
        mLabel = new Label("jalla", mLabelStyle);
        mLabel.setFontScale(2f);
        mLabel.setFillParent(true);
        mLabel.setWrap(true);
        mLabel.setAlignment(Align.top | Align.left);
        
        
        mTextGroup = new Group();
        mTextGroup.setWidth(PVU.SCREEN_WIDTH);
        mTextGroup.setHeight(PVU.SCREEN_HEIGHT);
        mTextGroup.setPosition(0, 0);
        mTextGroup.addActor(mLabel);
        mStage.addActor(mTextGroup);
        
    }
    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important
        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        mLabel.setText(mCaseText);
    }

    @Override
    protected void cleanUp() {
    }
}
