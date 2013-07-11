package com.september.tableroids;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.september.tableroids.statics.GameBuilder;
import com.september.tableroids.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class SplashActivity extends Activity {

	private final static String TAG = SplashActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	        // making it full screen
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_splash);
		
		try {
	        new Handler().postDelayed(new Runnable() {
	 
	            public void run() {
	                //GameBuilder.build(SplashActivity.this);
	            	Intent intent = new Intent(SplashActivity.this,MainActivity.class);
	    			startActivity(intent);
	 
	            }
	             
	             
	        }, 2000);
		
	
		}
		catch(Exception e) {
			android.util.Log.e(TAG,e.getMessage());
		}

		
	}

	
}
