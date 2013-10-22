package no.hist.gruppe5.pvu.seqjumper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/15/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class Ball {

    private Body mBody;
    private Sprite mSprite;

    public Ball(World world) {

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
    }

    public Ball(Body body, Sprite sprite) {
        this.mBody = body;
        this.mSprite = sprite;
    }

    public void draw(SpriteBatch batch) {

    }

    public void update(float delta) {
    }

    public Body getBody() {
        return mBody;
    }
}

