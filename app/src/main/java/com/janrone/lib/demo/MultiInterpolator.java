package com.janrone.lib.demo;

import android.animation.TimeInterpolator;

public class MultiInterpolator implements TimeInterpolator {

    public float mSeed;
    public int index;

    @Override
    public float getInterpolation(float input) {
        if(index == 0){
            return input * 360;
        }else if(index == 1){
            // cycles 要重复的周期数
            float cycles = 1.0f  ;
            return (float)(Math.sin((2 * cycles * (Math.PI) * (input/2)))) * 180;
        }
        return 1.0f;
    }
    //LinearInterpolator
    //AccelerateInterpolator
    //OvershootInterpolator
    //CycleInterpolator

}
