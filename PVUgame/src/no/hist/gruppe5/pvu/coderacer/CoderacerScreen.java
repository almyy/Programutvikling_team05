package no.hist.gruppe5.pvu.coderacer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import no.hist.gruppe5.pvu.Assets;
import static no.hist.gruppe5.pvu.Assets.primaryFont;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 8/28/13
 * Time: 10:49 AM
 */
public class CoderacerScreen extends GameScreen {

    private TextField codeText;
    private String code = "test";
    private Label codeOutput;
    
    public CoderacerScreen(PVU game) {
        super(game);
        
        LabelStyle labelStyle = new LabelStyle(Assets.primaryFont, Color.BLACK);
        codeOutput = new Label(code, labelStyle);
        codeOutput.setSize(0.1f, 0.1f);
        codeOutput.setFontScale(0.1f, 0.1f);
        System.out.println(camera.viewportWidth + " " + camera.viewportHeight);
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1); // Important

        batch.begin();
        codeOutput.draw(batch, 1f);
        batch.end();
    }

    @Override
    protected void update(float delta) {
    }

    @Override
    protected void cleanUp() {
    }
}
