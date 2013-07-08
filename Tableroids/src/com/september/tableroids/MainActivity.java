package com.september.tableroids;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.utils.Updater;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
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
    
    @Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			Bitmap bmp = Bitmap.createBitmap(1,1,Bitmap.Config.RGB_565);
			bmp.setPixel(0, 0, Color.BLACK);
			
			Sprite collider = new Sprite(bmp, (int) event.getX(), (int) event.getY(), 1, new int[]{1,1}) {
				
				@Override
				public void onTouch(MotionEvent event) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onCollide() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				protected void doUpdate() {
					// TODO Auto-generated method stub
					
				}
			};
			
			for(Sprite s:Updater.getInstance().getSprites()) {
				if(s.collide(collider)) {
					s.onTouch(event);
				}
				
			}
		}
		return true;
	}
}