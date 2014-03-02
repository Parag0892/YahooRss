package com.example.yahoorss;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

public class CollapseAnimation extends Animation implements Animation.AnimationListener {

	private View view;
	private static int ANIMATION_DURATION;
	private int LastWidth;
	private int FromWidth;
	private int ToWidth;
	private static int STEP_SIZE=30;
	private View x; 
	private View x1; 
	 
	
	
	public CollapseAnimation(View v, int FromWidth, int ToWidth, int Duration, View vq  , View tx) {
		
		
		this.view = v;
	   this.x =vq ; 
	   this.x1 = tx ; 
		LayoutParams lyp =  view.getLayoutParams();
		ANIMATION_DURATION = 1;
		this.FromWidth = lyp.width;
		this.ToWidth = lyp.width;
		setDuration(ANIMATION_DURATION);
		setRepeatCount(5);
		setFillAfter(false);
		setInterpolator(new AccelerateInterpolator());
		setAnimationListener(this);
	}

	public void onAnimationEnd(Animation anim) {
		// TODO Auto-generated method stub
		x.setVisibility(View.VISIBLE) ;
		x1.setVisibility(View.VISIBLE) ;
		LayoutParams lyp =  view.getLayoutParams();
		lyp.width =  0 ;
		view.setLayoutParams(lyp);
		
		 
	}

	public void onAnimationRepeat(Animation anim) {
		// TODO Auto-generated method stub
		LayoutParams lyp =  view.getLayoutParams();
		lyp.width = lyp.width - ToWidth/5;
		view.setLayoutParams(lyp);
	}

	public void onAnimationStart(Animation anim) {
		// TODO Auto-generated method stub
		LayoutParams lyp =  view.getLayoutParams();
		LastWidth = lyp.width;
	}

}
