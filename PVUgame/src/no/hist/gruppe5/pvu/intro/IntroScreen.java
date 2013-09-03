package no.hist.gruppe5.pvu.intro;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.GameScreen;
import no.hist.gruppe5.pvu.PVU;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 9/3/13
 * Time: 1:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class IntroScreen extends GameScreen {

    private TweenManager mManager;
    private Sprite mLogo;
    private Sprite mLogoGroup;
    private Sprite mPressSpace;
    private Sprite mBackground;

    private boolean mHasPressed = false;

    public IntroScreen(PVU game) {
        super(game);

        mManager = new TweenManager();

        mLogo = new Sprite(Assets.introMainLogo);
        mLogoGroup = new Sprite(Assets.introTeamLogo);
        mPressSpace = new Sprite(Assets.introPressSpace);
        mBackground = new Sprite(Assets.msBackground);
        mLogo.setPosition(-100f, (PVU.GAME_HEIGHT / 2f) - (mLogo.getHeight() / 2f));
        mLogoGroup.setPosition(-100f, (PVU.GAME_HEIGHT / 2f) - (mLogoGroup.getHeight() / 2f));
        mPressSpace.setPosition(-100, 10f);

        Timeline.createSequence()
                .push(Tween.set(mBackground, SpriteAccessor.OPACITY)
                        .target(0f))
                .push(Tween.to(mLogo, SpriteAccessor.POS_XY, 1f)
                    .target((PVU.GAME_WIDTH / 2) - (mLogo.getWidth() / 2f), mLogo.getY())
                    .ease(Quint.OUT))
                .pushPause(1f)
                .push(Tween.to(mLogo, SpriteAccessor.POS_XY, 1f)
                        .target(200f, mLogo.getY())
                        .ease(Expo.IN))
                .push(Tween.to(mLogoGroup, SpriteAccessor.POS_XY, 1f)
                    .target((PVU.GAME_WIDTH / 2) - (mLogoGroup.getWidth() / 2f), mLogoGroup.getY())
                    .ease(Quint.OUT))
                .pushPause(1f)
                .push(Tween.to(mLogoGroup, SpriteAccessor.POS_XY, 1f)
                        .target(200f, mLogoGroup.getY())
                        .ease(Expo.IN))
                .beginParallel()
                .push(Tween.set(mLogo, SpriteAccessor.POS_XY)
                        .target(200f, 45f))
                .push(Tween.to(mPressSpace, SpriteAccessor.POS_XY, 1f)
                        .target((PVU.GAME_WIDTH / 2) - (mPressSpace.getWidth() / 2), 10f)
                        .ease(Elastic.OUT))
                    .push(Tween.to(mLogo, SpriteAccessor.POS_XY, 1f)
                        .target((PVU.GAME_WIDTH / 2f) - (mLogo.getWidth() / 2f), 45f)
                        .ease(Elastic.OUT))
                    .push(Tween.to(mBackground, SpriteAccessor.OPACITY, 2f)
                        .target(1f)
                        .ease(Quint.INOUT))
                .end()
                .start(mManager);

    }
    private void recieveInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && !mHasPressed) {
            System.out.println("Whap");
            mHasPressed = true;
            mManager.killAll();
            Tween.to(mBackground, SpriteAccessor.OPACITY, 0.5f)
                    .target(1f)
                    .ease(Quint.INOUT)
                    .start(mManager);

            // Todo temporary
            game.setScreen(PVU.MAIN_SCREEN);
        }
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1f, 1f, 1f, 1f);
        batch.begin();
        mBackground.draw(batch);
        if(!mHasPressed) {
            mLogo.draw(batch);
            mLogoGroup.draw(batch);
            mPressSpace.draw(batch);
        } else {
            //TODO input name
        }
        batch.end();
    }

    @Override
    protected void update(float delta) {
        mManager.update(delta);
        recieveInput();
    }

    @Override
    protected void cleanUp() {
    }
}
