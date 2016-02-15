package com.janrone.lib.demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
/**
 * Created by Janrone on 2016/1/26.
 */
public class MultiArcAsynCircleProgress extends View {

    private static final int RED = 0xFFE5282C;

    private static final int PURPLE = 0xFF602E8C;
    private static final int LILAC = 0xFFC22286; //浅紫色
    private static final int PINK = 0xFFEB225E; //pink粉
    private static final int ORANGE = 0xFFEC5A34;
    private static final int YELLOW = 0xFFF4B42E;
    private static final int NAVY = 0xFF80C440; //青色
    private static final int GREEN = 0xFF00A296;
    private static final int BLUE = 0xFF1674BC;

    private static final int COLORS[] = new int[]{PURPLE, LILAC, PINK, ORANGE, YELLOW, NAVY, GREEN, BLUE};

    private Paint mPaint;
    private int mViewSize;
    private Point mCenter = new Point();

    private static final int SEED = 180 / 7;
    private static final int BASE = 180 / 7;

    private static final int CIRCLENUM = 14;

    private static final int GAPNUM = 30;

    private Point mLeftTop = new Point();
    private Point mRightBottom = new Point();

    private Context mContext;

    private MultiInterpolator mMultiInterpolator;

    private long mDuration = 3600;

    private boolean mStartAnim;
    private long mPlayTime;
    private long mStartTime;

    public MultiArcAsynCircleProgress(Context context) {
        super(context);
        mContext = context;
        init(null, 0);
    }

    public MultiArcAsynCircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs, 0);
    }

    public MultiArcAsynCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth((float) 1.0 * 18);

        startAnim();
        mMultiInterpolator = new MultiInterpolator();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultSize = getResources().getDimensionPixelSize(R.dimen.default_circle_view_size);
        int width = getDefaultSize(defaultSize, widthMeasureSpec);
        int height = getDefaultSize(defaultSize, heightMeasureSpec);
        mViewSize = Math.min(width, height);
        setMeasuredDimension(mViewSize, mViewSize);
        mCenter.set(mViewSize / 2, mViewSize / 2);
        mLeftTop.set(getLeft(), getTop());
        mRightBottom.set(getRight(), getBottom());

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        float factor = getFactor();
        Log.d("TAG", "factor" + factor);

        for (int i = CIRCLENUM; i > (CIRCLENUM - COLORS.length); i--) {
            mPaint.setColor(COLORS[i - (CIRCLENUM - COLORS.length) - 1]);
            RectF oval = new RectF(0 + (getMeasuredWidth() / GAPNUM * (i - 1)),
                    0 + (getMeasuredHeight() / GAPNUM * (i - 1)),
                    getMeasuredWidth() - (getMeasuredWidth() / GAPNUM * (i - 1)),
                    getMeasuredHeight() - (getMeasuredHeight() / GAPNUM * (i - 1)));
            //画弧线
            canvas.drawArc(oval, (90 + SEED * ((CIRCLENUM - i) + (CIRCLENUM - i + 1))
                    + getItemFactor(CIRCLENUM - i, factor)), 180, false, mPaint);
        }

        canvas.restore();

        if (true) {
            postInvalidate();
        }
    }

    private float getFactor() {
        if (mStartAnim) {
            mPlayTime = AnimationUtils.currentAnimationTimeMillis() - mStartTime;
        }
        float factor = mPlayTime / (float) mDuration;
        return factor % 1f;
    }

    public void startAnim() {
        mPlayTime = mPlayTime % mDuration;
        mStartTime = AnimationUtils.currentAnimationTimeMillis() - mPlayTime;
        mStartAnim = true;
        postInvalidate();
    }

    private float getItemFactor(int i, float factor) {
//        float itemFactor = (factor - 0.66f / POINT_NUM * index) * 3;
//        if (itemFactor < 0f) {
//            itemFactor = 0f;
//        } else if (itemFactor > 1f) {
//            itemFactor = 1f;
//        }
//        if(factor < 0f){
//            factor = 0f;
//        }else if(factor > 1f){
//            factor = 1f;
//        }
        mMultiInterpolator.index = i;
        return mMultiInterpolator.getInterpolation(factor);

    }
}
