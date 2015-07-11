package com.devpaul.materiallibrary.utils;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Paul T. on 3/12/2015.
 */
public class ValueGeneratorAnim extends Animation {

    private InterpolatedTimeCallback interpolatedTimeCallback;

    /**
     * This animation allows for the creating of a animation with time based control, i.e. frame
     * by frame. Given the duration of the animation this animation returns an interpolated time
     * between the values of 0 and 1
     * @param interpolatedTimeCallback the callback to use for this animation.
     */
    public ValueGeneratorAnim(InterpolatedTimeCallback interpolatedTimeCallback) {
        this.interpolatedTimeCallback = interpolatedTimeCallback;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        this.interpolatedTimeCallback.onTimeUpdate(interpolatedTime);
    }

    /**
     * Interpolated time interface for ValueGenerationAnim animation. Returns an updated interpolated
     * time.
     */
    public interface InterpolatedTimeCallback {
        public void onTimeUpdate(float interpolatedTime);
    }
}
