package no.hist.gruppe5.pvu.umlblocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import no.hist.gruppe5.pvu.mainroom.BodyEditorLoader;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 10/17/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class Room {

    private Body mBody;
    private Sprite mSprite;

    public Room(World world) {
        BodyEditorLoader loader = new BodyEditorLoader(Gdx.files.internal("data/pvugame.json"));

        //Room Body
        Body mBody;

        BodyDef bd = new BodyDef();
        bd.position.set(0, 0);
        bd.type = BodyDef.BodyType.StaticBody;

        FixtureDef fd = new FixtureDef();
        fd.density = 1;
        fd.friction = 1f;
        fd.restitution = 0f;

        mBody = world.createBody(bd);
        loader.attachFixture(mBody, "uml_room", fd, 3f);

        // Creating the floor and walls
        //createWall(new Vector2(0, 0), 3f, world, true); // Floor
        //createWall(new Vector2(0, 0), 2f, world, false); // left wall
        //createWall(new Vector2(3f, 0), 2f, world, false); // right wall


    }

    public void createWall(Vector2 from, float size, World world, boolean flat) {
        BodyDef groundBodyDef =new BodyDef();
        groundBodyDef.position.set(from);
        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundBox = new PolygonShape();
        if(flat)
            groundBox.setAsBox(size, 0.05f);
        else
            groundBox.setAsBox(0.05f, size);

        groundBody.createFixture(groundBox, 0.0f);
        groundBox.dispose();

    }
}
