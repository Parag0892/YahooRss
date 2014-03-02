package com.example.yahoorss;

import java.util.concurrent.TimeUnit;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.Button;

public class ExpandAnimation extends Animation implements Animation.AnimationListener {
	private View view;
	private static int ANIMATION_DURATION;
	private int LastWidth;
	private int FromWidth;
	private int ToWidth;
	private static int STEP_SIZE=30;
	private View x; 
	
	
	public ExpandAnimation(View v, int FromWidth, int ToWidth, int Duation , View vq ) {
		 TimeUnit tu = TimeUnit.NANOSECONDS; 
		this.view = v;
		this.x = vq ; 
	
		ANIMATION_DURATION = 1;
		this.FromWidth = FromWidth;
		this.ToWidth = ToWidth;
		setDuration(ANIMATION_DURATION);
		setRepeatCount(5);
		setFillAfter(false);
		setInterpolator(new AccelerateInterpolator());
		setAnimationListener(this);
	}

	public void onAnimationEnd(Animation anim) {
		
		// TODO Auto-generated method stub
		x.setVisibility(View.VISIBLE) ; 
		
	}

	public void onAnimationRepeat(Animation anim) {
		// TODO Auto-generated method stub
		LayoutParams lyp =  view.getLayoutParams();
		lyp.width = LastWidth +=ToWidth/5;
		view.setLayoutParams(lyp);
	}

	public void onAnimationStart(Animation anim) {
		// TODO Auto-generated method stub
		LayoutParams lyp =  view.getLayoutParams();
		lyp.width = 0;
		view.setLayoutParams(lyp);
		LastWidth = 0;
	}

}
