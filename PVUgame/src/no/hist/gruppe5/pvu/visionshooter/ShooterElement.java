package no.hist.gruppe5.pvu.visionshooter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import no.hist.gruppe5.pvu.Assets;
/**
 *
 * @author Frode
 */
public abstract class ShooterElement {
    private Sprite elementSprite;
    private float elementWidth = 10f;
    private float elementHeight = 10f;
    private float elementX = 180f;
    private float elementY;
    private float speed;

    public ShooterElement(Sprite elementSprite, float elementY, float newSpeed) {
        this.elementSprite = elementSprite;
        this.elementY = elementY;
        this.speed = newSpeed;
        elementSprite.setSize(elementHeight, elementWidth);
        elementSprite.setPosition(elementX,elementY);
    }

    public void draw(SpriteBatch batch) {
        elementSprite.draw(batch);
    }

    public Sprite getElementSprite() {
        return elementSprite;
    }

    public float getElementHeight() {
        return elementHeight;
    }

    public float getElementWidth() {
        return elementWidth;
    }

    public float getSpeed() {
        return speed;
    }

    public float getElementX() {
        return elementX;
    }

    public float getElementY() {
        return elementY;
    }

    public void update(float delta){
        elementX -= speed * delta;
        elementSprite.setPosition(elementX, elementY);
    }

    public void setElementY(float elementY) {
        this.elementY = elementY;
    }

    public void setElementX(float elementX) {
        this.elementX = elementX;
    }
  
}