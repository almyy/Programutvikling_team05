package no.hist.gruppe5.pvu.mainroom;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.TimeUtils;
import no.hist.gruppe5.pvu.PVU;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 9/2/13
 * Time: 10:34 AM
 */
public class RayCastManager {

    public static final int BOOK = 0;
    public static final int PC = 1;
    public static final int CART = 2;
    public static final int TABLE = 3;

    private long MAX_CALLBACK_TIMEOUT = 300L;
    private long mLastCallBack = 0L;

    public RayCast[] rayCasts;
    public boolean[] infrontRay;
    private boolean mWithinRay = false;

    public RayCastManager() {
        rayCasts = new RayCast[4];
        infrontRay = new boolean[4];

        rayCasts[BOOK] = new RayCast(
                createCallBack(BOOK),
                new Vector2(73f, PVU.GAME_HEIGHT - 31f),
                new Vector2(89f, PVU.GAME_HEIGHT - 31f)
        );

        rayCasts[PC] = new RayCast(
                createCallBack(PC),
                new Vector2(75f, PVU.GAME_HEIGHT - 86f),
                new Vector2(75f, PVU.GAME_HEIGHT - 100f)
        );

        rayCasts[CART] = new RayCast(
                createCallBack(CART),
                new Vector2(13f, PVU.GAME_HEIGHT - 25f),
                new Vector2(35f, PVU.GAME_HEIGHT - 25f)
        );

        rayCasts[TABLE] = new RayCast(
                createCallBack(TABLE),
                new Vector2(178f, PVU.GAME_HEIGHT - 48f),
                new Vector2(178f, PVU.GAME_HEIGHT - 86f)
        );

    }

    public RayCastCallback createCallBack(final int which) {
        RayCastCallback temp = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                infrontRay[which] = true;
                mLastCallBack = TimeUtils.millis();
                mWithinRay = true;
                return 0;
            }
        };

        return temp;

    }

    public void update(float deltaTime) {
        if((TimeUtils.millis() - mLastCallBack) > MAX_CALLBACK_TIMEOUT && mWithinRay) {
            for(int i = 0; i < infrontRay.length; i++) {
                infrontRay[i] = false;
            }
            mWithinRay = false;
            System.out.println("Timeout");
        }
    }

    public int getInfront() {
        for(int i = 0; i < infrontRay.length; i++) {
            if(infrontRay[i]) return i;
        }
        return -1;
    }

    public RayCast[] getRayCasts() {
        return rayCasts;
    }

    class RayCast {
        public RayCastCallback callBack;
        public Vector2 from;
        public Vector2 to;

        public RayCast(RayCastCallback cb, Vector2 from, Vector2 to) {
            this.callBack = cb;
            this.from = new Vector2(from.x, from.y);
            this.to = new Vector2(to.x, to.y);
        }

        public RayCast(RayCastCallback cb, float fromx, float fromy, float tox, float toy) {
            this.callBack = cb;
            this.from = new Vector2(fromx, fromy);
            this.to = new Vector2(tox, toy);
        }
    }
}
