package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import java.util.ArrayList;
import no.hist.gruppe5.pvu.Assets;
import no.hist.gruppe5.pvu.PVU;

public class Platform {

    private static final float BOX_MARGIN_X = 0.6f;
    private static final float BOX_MARGIN_Y = -0.1f;
    //Body
    private BodyDef groundBodyDef;
    private Body groundBody;
    private PolygonShape groundBox;
    //Sprites and positions
    private final ArrayList<Sprite> mPlatformSprite;
    private final ArrayList<Vector2> mPlatformPositions;
    // Bar
    private final Sprite mPowerBar;
    private final Sprite mRedBar;
    private final ShapeRenderer mShape;
    private float mShapeSize;
    // Labels
    private final Stage mStage;
    private final Group mLabelGroup;
    private final String[] mMinigameNames = {"Class 5", "Class 4", "Class 3", "Class 2", "Class 1"};
    private Label.LabelStyle labelstyle;

    public Platform(World world) {
        mPlatformSprite = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            this.mPlatformSprite.add(new Sprite(Assets.seqBox));
        }
        this.mPlatformPositions = new ArrayList<>(5);
        this.mPowerBar = new Sprite(Assets.seqBox);
        this.mRedBar = new Sprite(Assets.redBar);
        this.mShape = new ShapeRenderer();

        mStage = new Stage(PVU.SCREEN_WIDTH, PVU.SCREEN_HEIGHT, true);
        mLabelGroup = new Group();

        initLabels();
    }

    public void drawInside(SpriteBatch batch) {
        for (int i = 0; i < mPlatformSprite.size(); i++) {
            mPlatformSprite.get(i).draw(batch);
        }
    }

    public void drawOutside(SpriteBatch batch) {
        mStage.draw();
    }

    public void update(float delta) {
        initPlatform();
    }

    public Body getBody() {
        return groundBody;
    }

    public Body createPlatform(Vector2 from, float size, World world, boolean flat) {
        groundBox = new PolygonShape();
        groundBodyDef = new BodyDef();
        groundBox = new PolygonShape();
        this.mPlatformPositions.add(from);
        groundBodyDef.position.set(from);
        groundBody = world.createBody(groundBodyDef);
        if (flat) {
            groundBox.setAsBox(size, 0.05f);
        } else {
            groundBox.setAsBox(0.05f, size);
        }

        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();
        return groundBody;
    }

    private void initLabels() {
        Label placeHolder;
        for (int i = 0; i < mMinigameNames.length; i++) {
            placeHolder = makeLabel(mMinigameNames[i]);
            placeHolder.setPosition(PVU.SCREEN_WIDTH * (0.79f - (i*1.67f / 10f)), PVU.SCREEN_HEIGHT / 9.5f);
            placeHolder.setFillParent(true);
            placeHolder.setFontScale(2f);
            placeHolder.setWrap(true);
            mLabelGroup.addActor(placeHolder);
        }
        mStage.addActor(mLabelGroup);
    }

    protected void initPlatform() {
        // Update sprite position based on the Box2d body
        for (int i = 0; i < mPlatformSprite.size(); i++) {
            Vector2 pos = mPlatformPositions.get(i);
            mPlatformSprite.get(i).setPosition((pos.x * JumperScreen.BOX_TO_WORLD) - BOX_MARGIN_X - mPlatformSprite.get(i).getWidth() / 2,
                    (pos.y * JumperScreen.BOX_TO_WORLD) - BOX_MARGIN_Y - mPlatformSprite.get(i).getHeight() / 2);
            mPlatformSprite.get(i).setScale(0.185f, 0.15f);
        }
    }

    public Sprite createPowerBar() {
        mPowerBar.setPosition(-50f, 3f);
        mPowerBar.setScale(0.5f, 0.3f);
        mPowerBar.rotate(90f);
        return mPowerBar;
    }

    public ShapeRenderer drawRedBar() {
        mShape.setColor(Color.RED);
        mShape.begin(ShapeRenderer.ShapeType.Filled);
        mShape.rect(46, 222, 54, mShapeSize * 280);
        mShape.end();
        return mShape;
    }

    public void setRedBar(float y) {
        mShapeSize = y;
    }

    private Label makeLabel(String text) {
        labelstyle = new Label.LabelStyle(Assets.primaryFont10px, Color.BLACK);
        Label l = new Label(text, labelstyle);
        return l;
    }
}
