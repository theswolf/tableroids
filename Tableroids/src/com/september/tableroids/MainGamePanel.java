/**
 * 
 */
package com.september.tableroids;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.model.elements.Asteroid;
import com.september.tableroids.model.elements.Rocket;
import com.september.tableroids.model.elements.Shoot;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements
SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	private static final int BASE_WIDTH = 480;

	private MainThread thread;
	//private Sprite elaine;
	public List<Sprite> spriteInScene;
	private static List<Sprite> spriteToAdd;
	//private static List<Sprite> spriteToRemove;
	private final static int MAX_ENEMIES = 3;

	private int gameWidth,gameHeight, screenHeight, screenWidth , orientation;
	// the fps to be displayed
	private String avgFps;

	public final static int BACKGROUND = 0;
	public final static int ROCKET = 1;
	public final static int ASTEROID = 2;
	public final static int SHOOT = 3;
	public final static int EXPLOSION = 4;
	private SparseArray<Bitmap> bitmapResources;

	public void setAvgFps(String avgFps) {
		this.avgFps = avgFps;
	}

	public SparseArray<Bitmap> getBitmapResources() {
		if(bitmapResources == null) {
			bitmapResources = new SparseArray<Bitmap>();
		}
		return bitmapResources;
	}
	
	public static int computeSquareSize (int screenWidth, int screenHeight, int gameWidth, int gameHeight)
	{
//		int byWidth;
//		int byHeight;
//		byWidth = screenWidth / gameWidth;
//		byHeight = ( screenHeight - (screenHeight / 4)) / gameHeight;
//		if (byWidth < byHeight)
//		{
//			return byWidth;
//		}
//		return byHeight;
		int widthRatio = screenWidth/BASE_WIDTH;
		
		return widthRatio > 0 ? widthRatio : 1;
	}
	
	public Sprite getById(int id) {
		synchronized (spriteInScene) {
			for(Sprite s: getSprites()) {
				if(s.getId() == id) {
					return s;
				}
			}
			return null;
		}
		
	}

	public void loadResources() {
		
		int partSize;
		int entitySize;
		
		if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270)
		{
			partSize = computeSquareSize(screenHeight, screenWidth, gameWidth, gameHeight);
		}
		else
		{
			partSize = computeSquareSize(screenWidth, screenHeight, gameWidth, gameHeight);
		}
		//entitySize = partSize - 5;
		
		
	
		

		AssetManager manager = getContext().getAssets();

		Bitmap backBitmap = loadImage(manager, "farback.gif", gameWidth, gameHeight, true,partSize);
		setBitmapResources(BACKGROUND, backBitmap);

		Bitmap rocket = loadImage(manager, "ship_116x64.png", 0, 0, true,partSize);
		setBitmapResources(ROCKET, rocket);

		Bitmap asteroids = loadImage(manager, "asteroids.png", 0, 0, true,partSize);
		setBitmapResources(ASTEROID,asteroids);

		Bitmap.Config conf = Bitmap.Config.RGB_565;
		Bitmap shoot = Bitmap.createBitmap(3, 3, conf); 
		shoot.setPixel(1, 0, Color.YELLOW);
		shoot.setPixel(0, 1, Color.YELLOW);
		shoot.setPixel(2, 1, Color.YELLOW);
		shoot.setPixel(1, 2, Color.YELLOW);

		setBitmapResources(SHOOT, shoot);
		
		Bitmap explosion = loadImage(manager, "explosion.png", 0, 0, true, partSize);
		setBitmapResources(EXPLOSION,explosion);

	}

	private Bitmap loadImage(AssetManager manager, String fileName, int width, int height, boolean filter, int resize) {
		try {
			
			if(width > 0 && height > 0) {
				return Bitmap.createScaledBitmap(BitmapFactory.decodeStream(manager.open(fileName)), width, height, filter);
			}
			
			else {
				Bitmap original = BitmapFactory.decodeStream(manager.open(fileName));
				int w = original.getWidth()*resize;
				int h = original.getHeight()*resize;
				
				return Bitmap.createScaledBitmap(original, w, h, filter);
			}
			
			
			//return scaleImage(Bitmap.createScaledBitmap(BitmapFactory.decodeStream(manager.open(fileName)), width, height, filter),resize);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public void setBitmapResources(int id,Bitmap resource) {
		getBitmapResources().append(id, resource);
	}

	public  List<Sprite> getSprites() {
		if(spriteInScene == null) {
			spriteInScene = Collections.synchronizedList(new LinkedList<Sprite>());
			//spriteInScene = new SparseArray<Sprite>();
		}
		synchronized (spriteInScene) {
			return spriteInScene;
		}
		
	}

//	public List<Sprite> getSpritesToAdd() {
//		if(spriteToAdd == null) {
//			spriteToAdd = new LinkedList<Sprite>();
//		}
//		return spriteToAdd;
//	}

	public void resetLists() {
		spriteToAdd = null;
		//spriteToRemove = null;
	}

//	public List<Sprite> getSpritesToRemove() {
//		if(spriteToRemove == null) {
//			spriteToRemove = new LinkedList<Sprite>();
//		}
//		return spriteToRemove;
//	}


	public Bitmap getBitmap(String name) {
		AssetManager manager = getContext().getAssets();
		try {
			return BitmapFactory.decodeStream (manager.open(name));
		} catch (IOException e) {
			return null;
		}
	}
	

	public MainGamePanel(Context context) {
		super(context);
		
		getHolder().addCallback(this);



		AssetManager manager = getContext().getAssets();

		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point p = new Point();
		
		
		screenHeight = display.getHeight();
		screenWidth = display.getWidth();
		
		gameWidth = screenWidth;
		gameHeight = screenHeight;
		orientation = display.getRotation();

		loadResources();

		Sprite backGround = new Sprite(this,
				getBitmapResources().get(BACKGROUND),
				0,0,
				1,1,1,1
				);

		Bitmap rocketBM = getBitmapResources().get(ROCKET);
		
		Rocket rocket = new Rocket(this,
				rocketBM
				, gameWidth/2, gameHeight-rocketBM.getHeight()-5	// initial position
				, 10, 4,1,1);



//		getSprites().append(backGround.getId(), backGround);
//		getSprites().append(rocket.getId(),rocket);
		getSprites().add(backGround);
		getSprites().add(rocket);
		thread = new MainThread(getHolder(), this);

		setFocusable(true);
	}

//    public MainGamePanel(Context context, AttributeSet attrs) {
//        this(context, attrs , 0);
//        //init();
//    }
//
//    public MainGamePanel(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init();
//    }




	public void addAsteroids(Sprite collider) throws IOException {

		int astrocount = 0;
		
		if(collider != null) {
			for(int x = 0; x<getSprites().size(); x++) {
				Sprite s = getSprites().get(x);
				if(s instanceof Asteroid) {
					astrocount ++;
				}
				
			}
			
			for(int x = astrocount; x < MAX_ENEMIES ; x++) {
				Random random = new Random();
				//for(int x = astrocount; x< MAX_ENEMIES; x++) {
				Asteroid astro1 = new Asteroid(this,
						getBitmapResources().get(ASTEROID)
						,random.nextInt(gameWidth),-50-random.nextInt(10)
						//,random.nextInt(gameWidth),0
						,5,4,4,1
						);

				astro1.setFixedFrame(random.nextInt(15));
				astro1.setRuledByGarbage(false);
				
				getSprites().add(astro1);
				
				collider.addCollision(astro1.getId());
				
				for(int y = 0; y < getSprites().size(); y++) {
					Sprite s = getSprites().get(y);
					if(s instanceof Shoot) {
						s.addCollision(astro1.getId());
					}
				}
				
				
				
			}

		}
		
			
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
	public boolean onTouchEvent(final MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
				for(int x = 0; x < getSprites().size(); x++) {
					Sprite s = getSprites().get(x);
					s.onTouch(event);
				}
		}
		return true;
	}

	public void render(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		garbage(canvas);
		
		synchronized (spriteInScene) {
			for(int x = 0; x < getSprites().size(); x++) {
				Sprite s = getSprites().get(x);
					s.draw(canvas);
				}
		}
		
		
//		for (Iterator<Sprite> iterator = getSprites().iterator(); iterator.hasNext();) {
//				iterator.next().draw(canvas);
//			
//		}
		
		displayFps(canvas, avgFps);
	}

	/**
	 * This is the game update method. It iterates through all the objects
	 * and calls their update method if they have one or calls specific
	 * engine's update method.
	 */

	private void garbage(Canvas canvas) {
		
		for(int x = 0; x < getSprites().size(); x++) {
			Sprite sprite = getSprites().get(x);
			if((new Rect(0, 0, canvas.getWidth(), canvas.getHeight())).intersect(new Rect(sprite.getX(),sprite.getY(),sprite.getX()+sprite.getSpriteWidth(),sprite.getY()+sprite.getSpriteHeight()))) {
				sprite.setRuledByGarbage(true);
			}
			else {
				if(sprite.isRuledByGarbage()) {
					//getSpritesToRemove().add(sprite);
					sprite.setDirty(true);
				}
			}
			
		}
	}

	private Rocket getRocket() {
		
		
		
		for(int x = 0; x < getSprites().size(); x++) {
			Sprite s = getSprites().get(x);
			if(s instanceof Rocket) {
				return (Rocket) s;
			}
		}
		return null;
	}

	public void update() {


		//getSprites().removeAll(getSpritesToRemove());
		
		
		
		//getSprites().addAll(getSpritesToAdd());
		
		List<Integer> toRemove = new ArrayList<Integer>();
	
		
		
		
		for (Iterator<Sprite> iterator = getSprites().iterator(); iterator.hasNext();) {
			Sprite sprite = iterator.next();
			if(sprite.isDirty()) {
				iterator.remove();
			}
			
		}

//		for (Sprite sprite: getSprites()) {
//			sprite.getCollision().removeAll(getSpritesToRemove());
//		}


		resetLists();
		
		try {
			addAsteroids(getRocket());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			android.util.Log.d(TAG,e.getMessage());
		}

		
		long time = System.currentTimeMillis();
		
		for(int x = 0; x < getSprites().size(); x++) {
			Sprite s = getSprites().get(x);
			s.update(time);
		}


	}

	private void displayFps(Canvas canvas, String fps) {
		if (canvas != null && fps != null) {
			Paint paint = new Paint();
			paint.setARGB(255, 255, 255, 255);
			canvas.drawText(fps, this.getWidth() - 50, 20, paint);
		}
	}
	
	private Bitmap scaleImage(Bitmap bitmap, int newWidth) 
	{
		Bitmap newBitmap;
		
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		float scaleWidth = ((float) newWidth) / width;
		float ratio = ((float) bitmap.getWidth()) / newWidth;
		int newHeight = (int) (height / ratio);
		float scaleHeight = ((float) newHeight) / height;

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);

		newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		return newBitmap;
	}

}
