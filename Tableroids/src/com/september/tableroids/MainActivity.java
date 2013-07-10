package com.september.tableroids;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.ads.AdView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	private AdView adView;
	
	
    public AdView getAdView() {
		return adView;
	}


	public void setAdView(AdView adView) {
		this.adView = adView;
	}
	
	@Override
	  public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }



	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // set our MainGamePanel as the View
        //setContentView(new MainGamePanel(this));
        setContentView(R.layout.gamelayout);

      
    }
 
}