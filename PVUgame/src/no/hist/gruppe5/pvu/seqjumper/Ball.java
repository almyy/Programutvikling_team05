package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.Assets;

/**
 * Created with IntelliJ IDEA. User: karl Date: 10/15/13 Time: 9:41 AM To change
 * this template use File | Settings | File Templates.
 */
public class Ball {

    private static final float BALL_MARGIN_X = 0.1f;
    private static final float BALL_MARGIN_Y = 1f;
    private Body mBody;
    private Sprite mSprite;

    public Ball(World world) {
        this.mBody = createBody(world);
        this.mSprite = new Sprite(Assets.seqBall);
    }

    public void draw(SpriteBatch batch) {
        mSprite.draw(batch);
    }

    public void update(float delta) {
        updateBall();
    }

    public Body getBody() {
        return mBody;
    }

    private Body createBody(World world) {
        //Dynamic Body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0.5f, 2f);
        mBody = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(0.05f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        mBody.createFixture(fixtureDef);
        mBody.setFixedRotation(true);
        return mBody;
    }

    protected void updateBall() {
        // Update sprite position based on the Box2d body
        Vector2 pos = mBody.getTransform().getPosition();
        mSprite.setPosition((pos.x * JumperScreen.BOX_TO_WORLD) + BALL_MARGIN_X - mSprite.getWidth() / 2,
                (pos.y * JumperScreen.BOX_TO_WORLD) + BALL_MARGIN_Y - mSprite.getHeight() / 2);
        mSprite.setScale(0.13f, 0.143f);
    }
}
