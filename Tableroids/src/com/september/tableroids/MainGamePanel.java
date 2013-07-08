/**
 * 
 */
package com.september.tableroids;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.statics.GameBuilder;
import com.september.tableroids.utils.GraphicsUtils;
import com.september.tableroids.utils.Updater;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
		SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private MainThread thread;
	private Context context;

	// the fps to be displayed
	private String avgFps;
	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}
	
	 @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        super.onSizeChanged(w, h, oldw, oldh);
	        GraphicsUtils.setScreenSize(w,h);
	        int translation = oldh - h;
	        GameBuilder.getBackgroundSprite().setY(GameBuilder.getBackgroundSprite().getY() - translation);
	    }

	    @Override
	    protected void onDraw(Canvas canvas) {
	       super.onDraw(canvas);
	       GraphicsUtils.setScreenSize(canvas.getWidth(),canvas.getHeight());
	   }
	    
	public MainGamePanel(Context context, AttributeSet as) {
		super(context,as);
		init(context);
	}

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
//		getHolder().addCallback(this);
//
//		// create Elaine and load bitmap
////		elaine = new ElaineAnimated(
////				BitmapFactory.decodeResource(getResources(), R.drawable.walk_elaine) 
////				, 10, 50	// initial position
////				, 30, 47	// width and height of sprite
////				, 5, 5);	// FPS and number of frames in the animation
//		
//		GameBuilder.build((Activity)context);
//		
//		// create the game loop thread
//		thread = new MainThread(getHolder(), this);
//		
//		// make the GamePanel focusable so it can handle events
//		setFocusable(true);
		init(context);
	}
	
	
	private void init(Context context) {
		getHolder().addCallback(this);

		// create Elaine and load bitmap
//		elaine = new ElaineAnimated(
//				BitmapFactory.decodeResource(getResources(), R.drawable.walk_elaine) 
//				, 10, 50	// initial position
//				, 30, 47	// width and height of sprite
//				, 5, 5);	// FPS and number of frames in the animation
//		((Activity)context).runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				LinearLayout ll = (LinearLayout) ((Activity)context).findViewById(R.id.gamellayout);
//				while (!GameBuilder.isReady() || ll == null) {
//					try {
//						Thread.sleep(200);
//						if (ll == null) {
//							ll = (LinearLayout) ((Activity)context).findViewById(R.id.gamellayout);
//						}
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						android.util.Log.e(TAG,e.getMessage());
//					}
//				}
//				
//				TextView tv = new TextView(context);
//				tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20));
//				tv.setText("FAKE ADMOB");
//				tv.setBackgroundColor(Color.MAGENTA);
//				tv.setTextColor(Color.WHITE);
//				
//				ll.addView(tv);
//			}
//			
//		});
		
		
		GameBuilder.build((Activity)context);
		
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
		Log.d(TAG, "Thread was shut down cleanly");
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
				if(s.isTouchable() && s.collide(collider)) {
					s.onTouch(event);
				}
				
			}
		}
		return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.parseColor("#B8DBFF"));
		//elaine.draw(canvas);
		for(Sprite s:Updater.getInstance().getSprites()) {
			s.draw(canvas);
		}
		// display fps
		displayFps(canvas, avgFps);
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */
	public void update() {
		//elaine.update(System.currentTimeMillis());
		Updater.getInstance().clear();
		Long gameTime = System.currentTimeMillis();
		for(Sprite s:Updater.getInstance().getSprites()) {
			s.update(gameTime);
		}
	}
	


	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}

	public void showLoading(Canvas canvas) {
		Sprite backGround = GameBuilder.getBackgroundSprite();
		backGround.setY(GameBuilder.getOut().heightPixels-backGround.getResizedHeight());
		
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		
//		Bitmap backBitmap = Bitmap.createBitmap(GameBuilder.getOut().widthPixels, GameBuilder.getOut().heightPixels, conf);
//		int skyColor = Color.parseColor("#B8DBFF");//b8dbff
////		
//		for(int x = 0; x< GameBuilder.getOut().widthPixels; x++) {
//			for (int y = 0; y<GameBuilder.getOut().heightPixels; y++) {
//				backBitmap.setPixel(x, y, skyColor);
//			}
//		}
//		Sprite backGroundSky = new Sprite(backBitmap, 0, 0, 1, new int[]{1,1}) {
//			@Override
//			public void onTouch(MotionEvent event) {
//			}
//			@Override
//			public void onCollide() {
//			}
//			@Override
//			protected void doUpdate() {
//			}
//		};
//		
//		backGroundSky.draw(canvas);
		canvas.drawColor(Color.parseColor("#B8DBFF"));
		
		Paint strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    strokePaint.setColor(Color.WHITE);
	    strokePaint.setTextAlign(Paint.Align.CENTER);
	    strokePaint.setTextSize(GameBuilder.getOut().heightPixels/10);
	    strokePaint.setTypeface(GameBuilder.getTypeFace());
	    strokePaint.setStyle(Paint.Style.STROKE);
	    strokePaint.setStrokeWidth(2);

	    Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    textPaint.setColor(Color.RED);
	    textPaint.setTextAlign(Paint.Align.CENTER);
	    textPaint.setTextSize(GameBuilder.getOut().heightPixels/10);
	    textPaint.setTypeface(GameBuilder.getTypeFace());

	    canvas.drawText("Loading...",  GameBuilder.getOut().widthPixels/2, GameBuilder.getOut().heightPixels/3, strokePaint);
	    canvas.drawText("Loading...",  GameBuilder.getOut().widthPixels/2, GameBuilder.getOut().heightPixels/3, textPaint);
		
		
		GameBuilder.getBackgroundSprite().draw(canvas);
		GameBuilder.getLoaderSprite().draw(canvas);
		
//		
//		Bitmap maskBitmap = Bitmap.createBitmap(GameBuilder.getOut().widthPixels, GameBuilder.getOut().heightPixels, conf);
//		for(int x = 0; x< GameBuilder.getOut().widthPixels; x++) {
//			for (int y = 0; y<GameBuilder.getOut().heightPixels; y++) {
//				maskBitmap.setPixel(x, y, Color.DKGRAY);
//			}
//		}
//		Paint transparentpainthack = new Paint();
//		transparentpainthack.setAlpha(50);
//		canvas.drawBitmap(maskBitmap, 0, 0, transparentpainthack);
	}

}
