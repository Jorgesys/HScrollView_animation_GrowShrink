package com.jorgesys.zscrollview;

import java.util.Random;

import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	private static final String TAG = "MainActivity", URL_ART = "https://www.youtube.com/watch?v=VW7afIUk-ik", CREATED_BY = "Created by Jorgesys@gmail.com";  
	HorizontalScrollView hsv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hsv = (HorizontalScrollView)findViewById(R.id.pageSV);

		ImageView img1 = (ImageView)findViewById(R.id.image1);
		setAnimationGrowShrink(img1);
		ImageView img2 = (ImageView)findViewById(R.id.image2);
		setAnimationGrowShrink(img2);
		ImageView img3 = (ImageView)findViewById(R.id.image3);
		setAnimationGrowShrink(img3);
		ImageView img4 = (ImageView)findViewById(R.id.image4);
		setAnimationGrowShrink(img4);
		ImageView img5 = (ImageView)findViewById(R.id.image5);
		setAnimationGrowShrink(img5);
		ImageView img6 = (ImageView)findViewById(R.id.image6);
		setAnimationGrowShrink(img6);
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(URL_ART)));			
			}
		};

		//set listeners to open url.
		img1.setOnClickListener(listener);
		img2.setOnClickListener(listener);
		img3.setOnClickListener(listener);
		img4.setOnClickListener(listener);
		img5.setOnClickListener(listener);
		img6.setOnClickListener(listener);
	}


	public static boolean isInVisible(ScrollView scrollView, View view, Rect region, boolean relative){
		int top = scrollView.getScrollY() + region.top;
		int bottom = scrollView.getScrollY() + region.bottom;

		if(!relative){
			// If given region is not relative to scrollView 
			// i.e 0,0 does not point to first child left and top
			top -= scrollView.getTop();
			bottom -= scrollView.getTop();
		}

		Rect rect = new Rect(region);
		rect.top = top;
		rect.bottom = bottom;
		Rect childRegion = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());

		return Rect.intersects(childRegion, region);
	}

	private float getRandom(){
		int min = 0;
		int max = 100;
		Random r = new Random();
		float numRandom = r.nextInt(max - min + 1) + min;
		numRandom = (float) (numRandom * 0.01); 
		return numRandom;
	}

	private void setAnimationGrowShrink(final ImageView imgV){
		final Animation animationEnlarge;
		final Animation animationShrink;
		float pivotX = getRandom();
		float pivotY = getRandom();
		Log.e(TAG,"for " + imgV.getTag() + " : X=" + pivotX + " Y="+ pivotY);
		float startScale = 1.0f;
		float endScale = 1.2f;
		animationEnlarge =  new ScaleAnimation(startScale, endScale, startScale, endScale, 
				Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY); 
		animationEnlarge.setStartOffset(2000);
		animationEnlarge.setDuration(3000);		
		animationShrink =  new ScaleAnimation(endScale, startScale, endScale, startScale, 
				Animation.RELATIVE_TO_SELF, pivotX, Animation.RELATIVE_TO_SELF, pivotY);
		animationShrink.setStartOffset(2000);
		animationShrink.setDuration(3000);
		imgV.startAnimation(animationEnlarge);

		animationEnlarge.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationRepeat(Animation animation) {}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgV.startAnimation(animationShrink);
			}
		});

		animationShrink.setAnimationListener(new AnimationListener() {			
			@Override
			public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationRepeat(Animation animation) {}

			@Override
			public void onAnimationEnd(Animation animation) {
				imgV.startAnimation(animationEnlarge);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.about) {
			Toast.makeText(getApplicationContext(), CREATED_BY, Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
