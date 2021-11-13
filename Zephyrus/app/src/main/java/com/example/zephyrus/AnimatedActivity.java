package com.example.zephyrus;

import android.app.Activity;
import android.app.ActivityOptions;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.app.AppCompatActivity;

public class AnimatedActivity extends AppCompatActivity {

    protected void setActivityTransitionAnimationDirection(int gravitySlideInFrom, int gravitySlideOutFrom)
    {
        int animationDuration = getResources().getInteger(R.integer.changeActivityAnimationDurationMilliseconds);
        Interpolator interpolator = new AccelerateDecelerateInterpolator();

        if(gravitySlideInFrom != Gravity.NO_GRAVITY) {
            Slide slideIn = new Slide();
            slideIn.setSlideEdge(gravitySlideInFrom);
            slideIn.setDuration(animationDuration);
            slideIn.setInterpolator(interpolator);
            getWindow().setEnterTransition(slideIn);
        }

        if(gravitySlideOutFrom != Gravity.NO_GRAVITY) {
            Slide slideOut = new Slide();
            slideOut.setSlideEdge(gravitySlideOutFrom);
            slideOut.setDuration(animationDuration);
            slideOut.setInterpolator(interpolator);
            getWindow().setExitTransition(slideOut);
        }
    }

    protected ActivityOptions getAnimationOptions(Activity activity)
    {
        return ActivityOptions.makeSceneTransitionAnimation(activity);
    }

}
