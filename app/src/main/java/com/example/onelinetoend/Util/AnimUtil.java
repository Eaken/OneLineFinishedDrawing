package com.example.onelinetoend.Util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import java.util.concurrent.ConcurrentHashMap;

import androidx.annotation.NonNull;

public class AnimUtil {

    private final static ConcurrentHashMap<View,Animator> animatorMap = new ConcurrentHashMap<>();

    public static void removeAnimator(View target){
        if(target==null) return;
        Animator animator = animatorMap.get(target);
        if(animator!=null){
            animator.cancel();
            animatorMap.remove(target);
        }
    }

    public static void clearAnimator(){
        animatorMap.clear();
    }

    public static Animation doTranslationAnimation(View view, float rightF, float downF, boolean isRepeat, Animation.AnimationListener listener,boolean isStartNow){
        Animation anim = new TranslateAnimation(0,rightF,0,downF);
        anim.setDuration(ValueUtil.animateTime);
        anim.setAnimationListener(listener);
        view.setAnimation(anim);
        if(isRepeat){
            anim.setRepeatMode(Animation.RESTART);
            anim.setRepeatCount(Animation.INFINITE);
        }
        if(isStartNow){
            view.startAnimation(anim);
        }
        return anim;
    }

    public static Animation doScaleAnimation(View view, float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, boolean isRepeat, Animation.AnimationListener listener,boolean isStartNow){
        Animation anim = new ScaleAnimation(fromScaleX, toScaleX, fromScaleY, toScaleY,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(ValueUtil.animateTime);
        anim.setAnimationListener(listener);
        view.setAnimation(anim);
        if(isRepeat){
            anim.setRepeatMode(Animation.RESTART);
            anim.setRepeatCount(Animation.INFINITE);
        }
        if(isStartNow){
            view.startAnimation(anim);
        }
        return anim;
    }

    public static Animation doAlphaAnimation(View view, float from, float to, boolean isRepeat, Animation.AnimationListener listener,boolean isStartNow){
        Animation anim = new AlphaAnimation(from,to);
        anim.setDuration(ValueUtil.animateTime);
        anim.setAnimationListener(listener);
        view.setAnimation(anim);
        if(isRepeat){
            anim.setRepeatMode(Animation.RESTART);
            anim.setRepeatCount(Animation.INFINITE);
        }
        if(isStartNow){
            view.startAnimation(anim);
        }
        return anim;
    }

    public static Animation doRotateAnimation(View view, float from, float to, boolean isRepeat, Animation.AnimationListener listener,boolean isStartNow){
        Animation anim = new RotateAnimation(from,to, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(ValueUtil.animateTime*2);
        anim.setAnimationListener(listener);
        anim.setInterpolator(new LinearInterpolator());
        view.setAnimation(anim);
        if(isRepeat){
            anim.setRepeatMode(Animation.RESTART);
            anim.setRepeatCount(Animation.INFINITE);
        }
        if(isStartNow){
            view.startAnimation(anim);
        }
        return anim;
    }

    public static Animator doTranslation(View view, float fromRightF, float toRightF, float fromDownF, float toDownF, Animator.AnimatorListener listener,boolean isStartNow){
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator right = ObjectAnimator.ofFloat(view,ValueUtil.anim_translationX,fromRightF,toRightF);
        ObjectAnimator up = ObjectAnimator.ofFloat(view,ValueUtil.anim_translationY,fromDownF,toDownF);
        AnimatorSet set = new AnimatorSet();
        set.addListener(listener==null?new AnimatorListenerAdapter(){}:listener);
        set.setDuration(ValueUtil.animateTime).play(right).with(up);
        if(isStartNow)
            set.start();
        animatorMap.put(view,set);
        return set;
    }

    public static Animator doScale(View view, float fromScaleX, float toScaleX, float fromScaleY, float toScaleY, Animator.AnimatorListener listener,boolean isStartNow){
        ObjectAnimator x = ObjectAnimator.ofFloat(view,ValueUtil.anim_scaleX,fromScaleX,toScaleX);
        ObjectAnimator y = ObjectAnimator.ofFloat(view,ValueUtil.anim_scaleY,fromScaleY,toScaleY);
        AnimatorSet set = new AnimatorSet();
        set.addListener(listener==null?new AnimatorListenerAdapter(){}:listener);
        set.setDuration(ValueUtil.animateTime).play(x).with(y);
        if(isStartNow)
            set.start();
        animatorMap.put(view,set);
        return set;
    }

    public static Animator doAlpha(View view, float from, float to, Animator.AnimatorListener listener,boolean isStartNow){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view,ValueUtil.anim_alpha,from,to);
        AnimatorSet set = new AnimatorSet();
        set.addListener(listener==null?new AnimatorListenerAdapter(){}:listener);
        set.play(alpha);
        if(isStartNow)
            set.start();
        animatorMap.put(view,set);
        return set;
    }

    public static TransitionSet getScaleTransition(){
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeClipBounds());
        transitionSet.addTransition(new ChangeImageTransform());
        return transitionSet;
    }

}
