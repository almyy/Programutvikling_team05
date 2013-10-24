package no.hist.gruppe5.pvu.visionshooter.entity;

import aurelienribon.tweenengine.TweenAccessor;
import no.hist.gruppe5.pvu.visionshooter.entity.Element;

/**
 * Created with IntelliJ IDEA.
 * User: karl
 * Date: 9/4/13
 * Time: 12:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementAccessor implements TweenAccessor<Element> {

    public final static int POS_X = 0;

    @Override
    public int getValues(Element target, int tweenType, float[] returnValues) {
        switch(tweenType) {
            case POS_X:
                returnValues[0] = target.getElementX();
                return 1;
        }

        return -1;
    }

    @Override
    public void setValues(Element target, int tweenType, float[] newValues) {
        switch(tweenType) {
            case POS_X:
                target.setElementX(newValues[0]);
                break;
        }
    }
}
