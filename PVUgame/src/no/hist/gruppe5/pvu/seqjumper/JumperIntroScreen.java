/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.Input;
import no.hist.gruppe5.pvu.PVU;

/**
 *
 * @author Frode
 */
public class JumperIntroScreen extends GameScreen {
    
    private IntroGUI mGui;
    private Input mInput;

    public JumperIntroScreen(PVU game) {
        super(game);
        mGui = new IntroGUI(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mInput = new Input();
       
    }
    

    @Override
    protected void draw(float delta) {
       clearCamera(1, 1, 1, 1);

        batch.begin();
       ;
        batch.end();
        mGui.draw();
    }

    @Override
    protected void update(float delta) {
         if (mInput.action()) {
            game.setScreen(new JumperScreen(game));
        }

    }

    @Override
    protected void cleanUp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
