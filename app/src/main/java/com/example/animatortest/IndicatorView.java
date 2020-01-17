package com.example.animatortest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * name:wangxuyang
 * time:2020-01-17 17:32
 * email:chips.wang@oneplus.com
 * description:
 */
public class IndicatorView extends View {

    public Paint mPaint = new Paint();
    public int width;
    public int height;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void startAnimation() {


        AnimatorSet animatorSet = new AnimatorSet();

        final View parent = (View) IndicatorView.this.getParent();
        width = getWidth();
        height = getHeight();
        int dx = width * 3 / 2;
        ValueAnimator ani = ValueAnimator.ofFloat(0, 1);
        ani.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                int dH = (int) (0.15 * height * animation.getAnimatedFraction());
                if (dH <= 0.15 * height) {
                    layoutParams.height = height - dH;
                }

                setLayoutParams(layoutParams);
            }
        });
        ani.setInterpolator(new EaseCubicInterpolator(0,0,5,5));
        ani.setDuration(400);


        ObjectAnimator translationX = ObjectAnimator.ofFloat(this, "translationX", 0, dx);
        translationX.setDuration(700);
        translationX.setInterpolator(new   EaseCubicInterpolator(0,0,5,5));


        ValueAnimator ani2 = ValueAnimator.ofFloat(0, 1);
        ani2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                int dw = (int) (0.5 * width * animation.getAnimatedFraction());
                layoutParams.width = width +dw;
                setLayoutParams(layoutParams);
            }
        });
        ani2.setInterpolator(new DecelerateInterpolator());
        ani2.setDuration(700);

        animatorSet.play(ani2);
        animatorSet.play(ani);
        animatorSet.play(translationX);



        ValueAnimator ani3 = ValueAnimator.ofFloat(0, 1);
        ani3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
                int dH = (int) (0.15 * height * (1-animation.getAnimatedFraction()));
                layoutParams.height = (height)-dH;
                int dw = (int) (0.5 * width * animation.getAnimatedFraction());

                layoutParams.width=width/2*3-dw;
                setLayoutParams(layoutParams);
            }
        });
        ani3.setDuration(400);
        ani3.setInterpolator(new EaseCubicInterpolator(0,0,5,5));


        animatorSet.play(ani2).with(ani).with(translationX).before(ani3);
        animatorSet.start();
    }

}
