package com.september.tableroids;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BaseCanvasActivity extends Activity {
	MySurfaceView mySurfaceView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mySurfaceView = new MySurfaceView(this);
		setContentView(mySurfaceView);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mySurfaceView.onResumeMySurfaceView();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mySurfaceView.onPauseMySurfaceView();
	}
	
	
	
	@Override
	public void onBackPressed() {
		
	}

	class MySurfaceView extends SurfaceView implements Runnable{

		Thread thread = null;
		SurfaceHolder surfaceHolder;
		volatile boolean running = false;
		private final String TAG = MySurfaceView.class.getSimpleName();

		private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		Random random;

		public MySurfaceView(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			surfaceHolder = getHolder();
			random = new Random();
		}
		
		

		public void onResumeMySurfaceView(){
			running = true;
			thread = new Thread(this);
			thread.start();
		}

		public void onPauseMySurfaceView(){
			boolean retry = true;
			running = false;
			while(retry){
				try {
					thread.join();
					retry = false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public void run() {
			Canvas canvas;
			while(running){
				canvas = null;
				try
				{
					canvas = surfaceHolder.lockCanvas();
					//... actual drawing on canvas

					paint.setStyle(Paint.Style.STROKE);
					paint.setStrokeWidth(3);

					int w = canvas.getWidth();
					int h = canvas.getHeight();
					int x = random.nextInt(w-1); 
					int y = random.nextInt(h-1);
					int r = random.nextInt(255);
					int g = random.nextInt(255);
					int b = random.nextInt(255);
					paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);
					canvas.drawPoint(x, y, paint);
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						android.util.Log.e(TAG, e.getMessage());
					}

					//surfaceHolder.unlockCanvasAndPost(canvas);
				} finally {
					// in case of an exception the surface is not left in 
					// an inconsistent state
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}	// end finally
				
				
			}
		}

	}
}
