/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.io.FileNotFoundException;
import java.io.IOException;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author Frode
 */
public class IntroGUI {

    private Stage mStage;
    private Label.LabelStyle mLabelStyle;
    private Skin mSkin;
    private String mTitle = "SequenceJumper";
    private Label mLtitle;
    private String mText;
    private Label mLtext;
    private String mContinue = "Trykk SPACE for å starte!";
    private Label mLcontinue;

    


    public IntroGUI(float width, float height, boolean keepAspectRation) {
        mStage = new Stage(width, height, keepAspectRation);
        initStage();

    }
    public void draw(){
        mStage.draw();
    }
     private void initStage(){
        mLtitle = makeHeadLabel(mTitle);
        mLtitle.setPosition(PVU.SCREEN_WIDTH / 4, PVU.SCREEN_HEIGHT * 0.9f);
        mLtitle.setFontScale(6f);
        mLtitle.setWrap(true);
        mStage.addActor(mLtitle);
        
        mLcontinue = makeHeadLabel(mContinue);
        mLcontinue.setPosition(PVU.SCREEN_WIDTH / 3, PVU.SCREEN_HEIGHT / 3f);
        mLcontinue.setWrap(true);
        mLcontinue.setWidth(PVU.GAME_WIDTH *2);
        mLcontinue.setFontScale(2f);
        mStage.addActor(mLcontinue);
         try {
            mText = Assets.readFile("data/seqJump_intro/Intro.txt");
        } catch (FileNotFoundException e) {
        } catch (IOException e) {

        }
        mLtext = makeLabel(mText);
        mLtext.setPosition(PVU.SCREEN_WIDTH / 6, PVU.SCREEN_HEIGHT / 1.5f);
        mLtext.setWrap(true);
        mLtext.setWidth(PVU.GAME_WIDTH *2);
        mLtext.setFontScale(2f);
        mStage.addActor(mLtext);
    }
    
    private Label makeHeadLabel(String text) {
        mSkin = new Skin();
        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mLabelStyle.font = Assets.primaryFont10px;
        mLabelStyle.fontColor = Color.RED;
        Label l = new Label(text, mLabelStyle);
        return l;
    }

    private Label makeLabel(String text) {
        mSkin = new Skin();
        mLabelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        mLabelStyle.font = Assets.primaryFont10px;
        Label l = new Label(text, mLabelStyle);
        return l;
    }
   
}
