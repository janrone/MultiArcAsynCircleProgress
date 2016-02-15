package com.janrone.lib.demo;

import android.animation.TimeInterpolator;
import android.view.animation.CycleInterpolator;

public class MultiInterpolator implements TimeInterpolator {

    public float mSeed;
    public int index;

    @Override
    public float getInterpolation(float input) {
        if(index == 0){
            return -input * 360;
        }else if(index == 1){
            // cycles 要重复的周期数
            float cycles = 1.0f  ;
            return - ((float)(Math.sin((4 * cycles * (Math.PI) * (input/2)))) * (180/7*2));
        }else if(index == 2){
            //x*x*(-6+12*x)
            //x*x*(-2+3*x)*360
            float mTension = 10.0f;
            //return - input * input * (-4 + 3*input) * 360;
            //return  input * input * ((mTension + 1) * input - 3) * 360 /4/2;
            final float s = 1.70158f;
            return input*input*((s + 1)*input - s) * 360 ;
        }else {// if (index == 3){
            if (input == 0f) {
                return 0f;
            }
            if (input == 1.0f) {
                return 1.0f;
            }
            if ((input*=2) < 1.0f) {
                return (float) (0.5 * Math.pow(2, 10*(input - 1))) * 360 * (index-1);
            }
            return (float) (0.5 * (2 - Math.pow(2, -10*(input - 1)))) * 360 * (index -1) ;
        }
//        else {
//            return 1.0f;
//        }
    }
    //LinearInterpolator
    //AccelerateInterpolator
    //OvershootInterpolator
    //CycleInterpolator
   // AnticipateInterpolator

}
