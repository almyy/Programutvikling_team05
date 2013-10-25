package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import no.hist.gruppe5.pvu.*;
import no.hist.gruppe5.pvu.intro.IntroScreen;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 25/10/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class EndScreen extends GameScreen {

    private Stage mStage;
    private Input mInput;
    private Label mGoBackLabel;

    public EndScreen(PVU game) {
        super(game);

        mInput = new Input(200, 3000L);
        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);

        Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        Label.LabelStyle gradeStyle = new Label.LabelStyle(Assets.primaryFont10px, Color.RED);

        Label endLabel = new Label("Gratulerer! Du har fullført Tores Utopia!", labelStyle);
        endLabel.setFontScale(3.5f);
        endLabel.setPosition(80f, PVU.SCREEN_HEIGHT-40f);

        Label gradeDescLabel = new Label("Du fikk karakter:", labelStyle);
        gradeDescLabel.setFontScale(3.5f);
        gradeDescLabel.setPosition(30, 170);

        Label gradeLabel = new Label("" + ScoreHandler.getGrade(), gradeStyle);
        gradeLabel.setFontScale(10f);
        gradeLabel.setPosition(180, 100);

        mGoBackLabel = new Label("Trykk på SPACE for å avslutte, ha det bra!", labelStyle);
        mGoBackLabel.setFontScale(2f);
        mGoBackLabel.setPosition(20, 240);
        mGoBackLabel.setLayoutEnabled(false);

        mStage.addActor(endLabel);
        mStage.addActor(gradeDescLabel);
        mStage.addActor(gradeLabel);
        mStage.addActor(mGoBackLabel);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);
        batch.begin();
        batch.draw(Assets.endScreenBackground, 0, 0);
        batch.end();

        mStage.draw();
    }

    @Override
    protected void update(float delta) {
        if(mInput.action()) {
            System.exit(0);
        }

        if(mInput.isActionReady()) {
            mGoBackLabel.setLayoutEnabled(true);
        }

    }

    @Override
    protected void cleanUp() {
    }
}
