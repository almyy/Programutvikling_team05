package no.hist.gruppe5.pvu.reqfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.PVU;
import no.hist.gruppe5.pvu.ScoreHandler;

/**
 *
 * @author Martin
 */
public class ReqFinderScreen extends GameScreen {

    private String mCaseText;
    private Stage mStage;;
    private LabelStyle mLabelStyle;
    private LabelStyle mCorrectLabelStyle;
    private LabelStyle mWrongLabelStyle;
    private LabelStyle mHighlightedLabelStyle;
    private LabelStyle mLivesStyle;
    private ArrayList<Label> mLabels = new ArrayList<>();
    private Label mHighlightedLabel;
    private int mHighlightedIndex;
    private String[] mCorrectWords= {"interaktivt skjema,", "kundeprofil", "profilside", "instant messaging-tjeneste(IM-tjeneste).", "prosjektbestillinger", "fysikksimulator"};
    private Input mInput;
    private int mLives = mCorrectWords.length;
    private Label mLivesLabel;
    private int mCorrectCounter = 0;
    private boolean mNewLine;
            
    public ReqFinderScreen(PVU pvu) {
        super(pvu);

        mInput = new Input(100, 200);

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
        mHighlightedLabelStyle = new LabelStyle(kopiert, Color.BLUE);
        mWrongLabelStyle = new LabelStyle(kopiert, Color.RED);
        mCorrectLabelStyle = new LabelStyle(kopiert, Color.GREEN);
        mLivesStyle = new LabelStyle(kopiert, Color.RED);
        

        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mStage.setViewport(mStage.getWidth(), mStage.getHeight(), true, 0, 0, mStage.getWidth(), mStage.getHeight());
        StringTokenizer st = new StringTokenizer(mCaseText);
        mLabelStyle.font.scale(1.2f);
        /*Label initLabel = new Label("Hei", mHighlightedLabelStyle);
        initLabel.setPosition(10, PVU.SCREEN_HEIGHT - initLabel.getHeight() - 15);
        System.out.println(initLabel.getX() + " " + initLabel.getY());
        mLabels.add(initLabel);
        mStage.addActor(initLabel);
        mHighlightedLabel = initLabel;
        */
        mHighlightedIndex = 0;
        float labelLength = 0;
        while (st.hasMoreTokens()) {
            mLabels.add(new Label(st.nextToken(" "), mLabelStyle));
            if(mLabels.get(mLabels.size()-1).textEquals("interaktivt")) {
                mLabels.get(mLabels.size()-1).setText("interaktivt " + st.nextToken(" "));
            }
            if(mLabels.get(mLabels.size()-1).textEquals("Instant")) {
                mLabels.get(mLabels.size()-1).setText("Instant " + st.nextToken(" "));
            }
            if(mLabels.size() == 1) {
                mLabels.get(mLabels.size()-1).setPosition(20, PVU.SCREEN_HEIGHT - mLabels.get(mLabels.size()-1).getHeight() - 15);
                mHighlightedLabel = mLabels.get(mLabels.size()-1);
                mHighlightedLabel.setStyle(mHighlightedLabelStyle);
            }
            else if (labelLength + mLabels.get(mLabels.size() - 1).getPrefWidth() > PVU.SCREEN_WIDTH - 20) {
                mLabels.get(mLabels.size() - 1).setPosition(20, mLabels.get(mLabels.size() - 2).getY() - mLabels.get(mLabels.size() - 1).getHeight()-5);
                labelLength = 0;
            }
            else if(mNewLine){ 
                mLabels.get(mLabels.size() - 1).setPosition(20, mLabels.get(mLabels.size() - 2).getY() - mLabels.get(mLabels.size() - 1).getHeight()-30);
                labelLength = 0;
                mNewLine = false;
            } else {
                mLabels.get(mLabels.size() - 1).setPosition(mLabels.get(mLabels.size() - 2).getX() + mLabels.get(mLabels.size() - 2).getPrefWidth() + 5, mLabels.get(mLabels.size() - 2).getY());
            }
            labelLength = mLabels.get(mLabels.size() - 1).getX() + mLabels.get(mLabels.size() - 1).getPrefWidth();
            mStage.addActor(mLabels.get(mLabels.size() - 1));
            if(mLabels.get(mLabels.size()-1).textEquals("foregå.")) {
                mNewLine = true;
            }
        }
        
        mLivesLabel = new Label("Du har "+mLives + " trykk igjen", mLivesStyle);
        mLivesLabel.setPosition(PVU.SCREEN_WIDTH-mLivesLabel.getPrefWidth() - 5, 5);
        mStage.addActor(mLivesLabel);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important
        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if (mCorrectCounter == mCorrectWords.length) {
            //won
            reportScore();
            game.setScreen(new ReqFinderEndScreen(game, mCorrectCounter, mCorrectWords.length));
        }
        if (mLives <= 0) {
            reportScore();
            game.setScreen(new ReqFinderEndScreen(game, mCorrectCounter, mCorrectWords.length));
        }
        if (mInput.right() && !isRightmost()) {
            mHighlightedIndex++;
            if(mHighlightedLabel.getStyle().equals(mHighlightedLabelStyle)) {
                mHighlightedLabel.setStyle(mLabelStyle);
            }
            mHighlightedLabel = mLabels.get(mHighlightedIndex); 
            if(mHighlightedLabel.getStyle().equals(mLabelStyle)) {
                mHighlightedLabel.setStyle(mHighlightedLabelStyle);
            }
        }
        else if (mInput.left() && !isLeftmost()) {
            mHighlightedIndex--;
            
            if(mHighlightedLabel.getStyle().equals(mHighlightedLabelStyle)) {
                mHighlightedLabel.setStyle(mLabelStyle);
            }
            mHighlightedLabel = mLabels.get(mHighlightedIndex); 
            if(mHighlightedLabel.getStyle().equals(mLabelStyle)) {
                mHighlightedLabel.setStyle(mHighlightedLabelStyle);
            }
        }
        else if (mInput.down() && !isBotmost()) {
            mHighlightedIndex = findLabelUnder(mHighlightedLabel);
            if(mHighlightedLabel.getStyle().equals(mHighlightedLabelStyle)) {
                mHighlightedLabel.setStyle(mLabelStyle);
            }
            mHighlightedLabel = mLabels.get(mHighlightedIndex); 
            if(mHighlightedLabel.getStyle().equals(mLabelStyle)) {
                mHighlightedLabel.setStyle(mHighlightedLabelStyle);
            }
        }
        else if (mInput.up() && !isUpmost()) {
            mHighlightedIndex = findLabelAbove(mHighlightedLabel);  
            if(mHighlightedLabel.getStyle().equals(mHighlightedLabelStyle)) {
                mHighlightedLabel.setStyle(mLabelStyle);
            }
            mHighlightedLabel = mLabels.get(mHighlightedIndex); 
            if(mHighlightedLabel.getStyle().equals(mLabelStyle)) {
                mHighlightedLabel.setStyle(mHighlightedLabelStyle);
            }
        }
        else if (mLives != 0 && mInput.action() && mHighlightedLabel.getStyle() == mHighlightedLabelStyle) {
            if(isCorrect()) {
                mHighlightedLabel.setStyle(mCorrectLabelStyle);
                mCorrectCounter++;
                mLives--;
                mLivesLabel.setText(""+mLives);
                
            } else {
                mHighlightedLabel.setStyle(mWrongLabelStyle);
                mLives--;
                mLivesLabel.setText("Du har "+mLives + " trykk igjen");
            }
        }
    }

    @Override
    protected void cleanUp() {
    }

    private boolean isLeftmost() {
        if (mHighlightedLabel.getX() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isRightmost() {
        for (int i = 0; i < mLabels.size(); i++) {
            if (mHighlightedLabel == mLabels.get(i)) {
                if (mLabels.get(i + 1).getY() != mHighlightedLabel.getY()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean isUpmost() {
        if (mHighlightedLabel.getY() - mHighlightedLabel.getHeight() >= PVU.SCREEN_HEIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isBotmost() {
        for (int i = 0; i < mLabels.size(); i++) {
            if(mHighlightedLabel == mLabels.get(i)) {
                for(int j = 0; j < mLabels.size(); j++) {
                    if(mLabels.get(j).getY() != mHighlightedLabel.getY()) return false;
                }
            }
        }
        return true;
    }
    private int findLabelUnder(Label current) {
        int nextRowStart = 0;
        int nextRowEnd = 0;
        for(int i = mHighlightedIndex; i < mLabels.size(); i++) {
            if(mLabels.get(i).getY() != mHighlightedLabel.getY()) {
                nextRowStart = i;
                break;
            }
        }
        for(int i = nextRowStart; i < mLabels.size(); i++) {
            if(mLabels.get(i).getY() != mLabels.get(nextRowStart).getY()) {
                nextRowEnd = i-1;
                break;
            }
        }
        float currentXAvg = (current.getX() + (current.getX()+current.getPrefWidth()))/2;
        float shortestDistance = Math.abs(currentXAvg-(mLabels.get(nextRowStart).getX() + (mLabels.get(nextRowStart).getX() + mLabels.get(nextRowStart).getPrefWidth()))/2);
        int shortestIndex = nextRowStart;
        for(int i = nextRowStart+1; i <= nextRowEnd; i++) {
            float distance = Math.abs(currentXAvg-(mLabels.get(i).getX() + (mLabels.get(i).getX() + mLabels.get(i).getPrefWidth()))/2);
            if(distance < shortestDistance) {
                shortestDistance = distance;
                shortestIndex = i;
            }
        }
        return shortestIndex;
        
    }
    private int findLabelAbove(Label current) {
        int nextRowStart = 0;
        int nextRowEnd = 0;
        for(int i = mHighlightedIndex; i > 0; i--) {
            if(mLabels.get(i).getY() != mHighlightedLabel.getY()) {
                nextRowEnd = i;
                break;
            }
        }
        for(int i = nextRowEnd; i > 0; i--) {
            if(mLabels.get(i).getY() != mLabels.get(nextRowEnd).getY()) {
                nextRowStart = i+1;
                break;
            }
        }
        float currentXAvg = (current.getX() + (current.getX()+current.getPrefWidth()))/2;
        float shortestDistance = Math.abs(currentXAvg-(mLabels.get(nextRowStart).getX() + (mLabels.get(nextRowStart).getX() + mLabels.get(nextRowStart).getPrefWidth()))/2);
        int shortestIndex = nextRowStart;
        for(int i = nextRowStart+1; i <= nextRowEnd; i++) {
            float distance = Math.abs(currentXAvg-(mLabels.get(i).getX() + (mLabels.get(i).getX() + mLabels.get(i).getPrefWidth()))/2);
            if(distance < shortestDistance) {
                shortestDistance = distance;
                shortestIndex = i;
            }
        }
        return shortestIndex;
        
    }
    private boolean isCorrect() {
        for(int i = 0; i < mCorrectWords.length; i++) {
            if(mCorrectWords[i].equalsIgnoreCase(mHighlightedLabel.getText().toString())) {
                return true;
            }
        }
        return false;
    }
    private void reportScore() {
        float score = (float) ((float)mCorrectCounter/(float)mCorrectWords.length);
        System.out.println((int)(score*100));
        ScoreHandler.updateScore(ScoreHandler.REQ, score);
    }
}
