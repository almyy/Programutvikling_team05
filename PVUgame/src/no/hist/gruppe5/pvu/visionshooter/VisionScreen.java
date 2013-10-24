package no.hist.gruppe5.pvu.visionshooter;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Quint;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.Random;
import no.hist.gruppe5.pvu.*;
import no.hist.gruppe5.pvu.sound.Sounds;
import no.hist.gruppe5.pvu.umlblocks.ScrollingBackground;
import no.hist.gruppe5.pvu.visionshooter.entity.*;

public class VisionScreen extends GameScreen {

    private static int MAX_POINTS = 400;
    private static long FLASH_TIME = 80;

    // Game elements
    private ShooterShip mVisionShooterShip = new ShooterShip();
    private ArrayList<Bullet> mShipProjectiles = new ArrayList<Bullet>();
    private ArrayList<Element> mElements = new ArrayList<Element>();

    // Game variables
    private long mLastBulletShot = 0;
    private int[] mNoElements = {6, 8, 8};//Number of elements: dokument, facebook, youtube
    private int[] mElementsGot = {0, 0, 0};
    private long mLastElementSpawned = 0;
    public int mPoints = 0;

    // Red flash
    public long mLastFlash;
    private boolean mIsFlashing;

    // Other
    private Random mRandom = new Random();
    private String mPointText = "Poeng: ";
    private TweenManager mTweenManager;

    // GUI
    private ScrollingBackground mBackground;
    private Label mPointTextLabel;
    private Label mPointValueLabel;
    private Sprite mFlashTexture;

    public VisionScreen(PVU game) {
        super(game);
        LabelStyle pointStyle = new LabelStyle(Assets.primaryFont10px, Color.BLACK);

        mPointTextLabel = new Label(mPointText, pointStyle);
        mPointTextLabel.setPosition(1, 1);
        mPointTextLabel.setFontScale(0.8f);

        mPointValueLabel = new Label(String.valueOf(mPoints), pointStyle);
        mPointValueLabel.setPosition(mPointTextLabel.getX() + mPointTextLabel.getPrefWidth(), mPointTextLabel.getY());
        mPointValueLabel.setFontScale(0.8f);

        mBackground = new ScrollingBackground(Assets.visionShooterRegion, 50);

        mTweenManager = new TweenManager();
        Tween.registerAccessor(Element.class, new ElementAccessor());

        // Transparent overlay
        Pixmap pix = new Pixmap(2, 2, Pixmap.Format.RGBA8888);
        pix.setColor(new Color(1f, 0f, 0f, 1f));
        pix.fill();

        mFlashTexture = new Sprite(new Texture(pix));
        mFlashTexture.setColor(new Color(1, 1, 0, 0.5f));
        mIsFlashing = true;
    }

    @Override
    protected void draw(float delta) {
        clearCamera(1, 1, 1, 1);

        batch.begin();
        mBackground.draw(batch);
        drawBullets();
        drawElements();
        drawElementsGui();
        mVisionShooterShip.draw(batch);
        mPointTextLabel.draw(batch, 1f);
        mPointValueLabel.draw(batch, 1f);
        drawFlash();
        batch.end();
    }

    @Override
    protected void update(float delta) {
        mVisionShooterShip.update(delta);
        if (Input.continuousAction()) {
            shootBullet();
        }
        mBackground.update(delta);
        updateBulletPos(delta);
        updateElementPos(delta);
        updateFlash(delta);
        checkBulletHit();
        checkShipHit();

        if ((TimeUtils.millis() - mLastElementSpawned) > 700L && !noElementsLeft()) {
            addElement();
        }

        checkFinish();
        mTweenManager.update(delta);
    }

    private boolean noElementsLeft() {
        return (mNoElements[0] == 0 && mNoElements[1] == 0 && mNoElements[2] == 0);
    }

    @Override
    protected void cleanUp() {
    }

    private boolean finish() {
        for (int i = 0; i < 3; i++) {
            if (mNoElements[i] > 0) {
                return false;
            }
        }
        return true;
    }

    private void shootBullet() {
        if ((TimeUtils.millis() - mLastBulletShot) > 400L) {
            Bullet vB = new Bullet();
            vB.setProjectileY(mVisionShooterShip.getShipY() + (mVisionShooterShip.getShipHeight() / 2));
            vB.setProjectileX(mVisionShooterShip.getShipX());
            mShipProjectiles.add(vB);
            mLastBulletShot = TimeUtils.millis();
        }

    }

    private void updateBulletPos(float delta) {
        for (int i = 0; i < mShipProjectiles.size(); i++) {
            if (mShipProjectiles.get(i).getProjectileX() < 196) {
                mShipProjectiles.get(i).update(delta);
            } else {
                mShipProjectiles.remove(i);
            }
        }
    }

    private void updateElementPos(float delta) {
        for (int i = 0; i < mElements.size(); i++) {
            if (mElements.get(i).getElementX() > - 30f) {
                mElements.get(i).update(delta);
            } else if (mElements.get(i) instanceof Document) {
                mPoints -= 20;
                mElements.remove(i);
                flash();
            } else   {
                mPoints -= 10;
                mElements.remove(i);
                flash();
            }
        }

        if(mPoints < 0) mPoints = 0;
    }

    private void flash() {
        mLastFlash = TimeUtils.millis();
        mIsFlashing = true;
    }

    private void updateFlash(float delta) {
        if((TimeUtils.millis() - mLastFlash) > FLASH_TIME)
            mIsFlashing = false;
    }

    private void drawFlash() {
        if(mIsFlashing) {
            Color c = batch.getColor();
            batch.setColor(1, 0, 0, 0.5f);
            batch.draw(mFlashTexture, 0, 0, PVU.GAME_WIDTH, PVU.GAME_HEIGHT);
            batch.setColor(c);
        }
    }

    private void drawBullets() {
        if (!mShipProjectiles.isEmpty()) {
            for (int i = 0; i < mShipProjectiles.size(); i++) {
                mShipProjectiles.get(i).draw(batch);
            }
        }
    }

    private void drawElements() {
        if (!mElements.isEmpty()) {
            for (int i = 0; i < mElements.size(); i++) {
                mElements.get(i).draw(batch);
            }
        }
    }

    private void drawElementsGui() {
        for(int i = 0; i < mElementsGot[0]; i++) {
            batch.draw(Assets.visionShooterDocumentRegion, 1 + (2 * (i * 4)), PVU.GAME_HEIGHT - 9, 7, 8);
        }

        for(int i = 0; i < mElementsGot[1]; i++) {
            batch.draw(Assets.visionShooterFacebookRegion, (PVU.GAME_WIDTH - 9) - (2 * (i * 4)), PVU.GAME_HEIGHT - 9, 7, 8);
        }

        for(int i = 0; i < mElementsGot[2]; i++) {
            batch.draw(Assets.visionShooterYoutubeRegion, (PVU.GAME_WIDTH - 9) - (2 * (i * 4)), PVU.GAME_HEIGHT - 18, 7, 8);
        }
    }

    private void addElement() {
        mLastElementSpawned = TimeUtils.millis();

        int index = mRandom.nextInt(3);
        int counts = 0;
        while(mNoElements[index] == 0) {
            index = mRandom.nextInt(3);
            if(counts > 10) {
                System.out.println("None left");
                return;
            }
            counts++;
        }

        Element newElement = null;
        System.out.print("Adding..");
        if (index == 1 && (mNoElements[1] > 0)) {
            newElement = new Facebook(0);
            mElements.add(newElement);
            mNoElements[1]--;
            System.out.println(" one");
        } else if (index == 2 && (mNoElements[2] > 0)) {
            newElement = new Youtube(0);
            mElements.add(newElement);
            mNoElements[2]--;
            System.out.println(" two");
        } else if (index == 0 && mNoElements[0] > 0) {
            newElement = new Document(0);
            mElements.add(newElement);
            mNoElements[0]--;
            System.out.println(" three");
        }

        if (newElement != null) {
            newElement.setElementY(mRandom.nextInt(Math.round(PVU.GAME_HEIGHT - newElement.getElementHeight())));
            newElement.setElementX(210f);
            Tween.to(newElement, ElementAccessor.POS_X, 1f)
                    .target(160f)
                    .ease(Quint.IN)
                    .start(mTweenManager);
        }

    }

    private void checkBulletHit() {
        for (int i = 0; i < mElements.size(); i++) {
            for (int j = 0; j < mShipProjectiles.size();) {
                if (mShipProjectiles.get(j).getBulletSprite().getBoundingRectangle().overlaps(mElements.get(i).getElementSprite().getBoundingRectangle())) {
                    if (mElements.get(i) instanceof Document) {
                        // Do not kill document!
                        mPoints -= 20;
                        flash();
                    } else {
                        // Killed bad activity, more points!
                        mPoints += 10;
                    }

                    if(mElements.get(i) instanceof Facebook)
                        mElementsGot[1]++;
                    else if(mElements.get(i) instanceof Youtube)
                        mElementsGot[2]++;


                    mShipProjectiles.remove(j);
                    mElements.remove(i);

                    i--;
                    break;
                } else {
                    j++;
                }

            }
        }
        mPointValueLabel.setText(String.valueOf(mPoints));

    }

    private void checkShipHit() {
        for (int i = 0; i < mElements.size(); i++) {
            if (mElements.get(i) instanceof Document) {
                if (mElements.get(i).getElementSprite().getBoundingRectangle().overlaps(mVisionShooterShip.getShipSprite().getBoundingRectangle())) {
                    mPoints += 40;
                    mElements.remove(i);
                    mElementsGot[0]++;
                }
            } else {
                if (mElements.get(i).getElementSprite().getBoundingRectangle().overlaps(mVisionShooterShip.getShipSprite().getBoundingRectangle())) {
                    mPoints -= 40;
                    mElements.remove(i);
                    flash();
                }
            }
        }
        mPointValueLabel.setText(String.valueOf(mPoints));
    }

    private void checkFinish() {
        if (mElements.isEmpty() && finish()) {
            ScoreHandler.updateScore(ScoreHandler.VISION, (float)mPoints/(float)MAX_POINTS);
            game.setScreen(new VisionEndScreen(game,mPoints, mElementsGot));

        }
    }
}
