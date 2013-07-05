package com.september.tableroids.statics;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.september.tableroids.R;
import com.september.tableroids.consts.Constants;
import com.september.tableroids.model.Sprite;
import com.september.tableroids.model.elements.Cloud;
import com.september.tableroids.model.elements.Cloud.Direction;
import com.september.tableroids.model.elements.Smile;
import com.september.tableroids.model.elements.Square;
import com.september.tableroids.model.elements.Square.Fattore;
import com.september.tableroids.utils.GraphicsUtils;
import com.september.tableroids.utils.Updater;

public class GameBuilder {

	/**
	 * @param args
	 */
	private static boolean ready = false;
	private static Typeface typeFace;
	public final static String tag = GameBuilder.class.getSimpleName();
	private static Bitmap background;
	private static Sprite backgroundSprite;
	private static DisplayMetrics out;
	
	
	public static boolean isReady() {
		return ready;
	}



	public static void setReady(boolean ready) {
		GameBuilder.ready = ready;
	}
	
	
	

	public static DisplayMetrics getOut() {
		if(out == null) {
			out = new DisplayMetrics();
		}
		return out;
	}



	public static void setOut(DisplayMetrics out) {
		GameBuilder.out = out;
	}



	public static Sprite getBackgroundSprite() {
		return backgroundSprite;
	}



	public static void setBackgroundSprite(Sprite backgroundSprite) {
		GameBuilder.backgroundSprite = backgroundSprite;
	}



	public static Bitmap getBackground() {
		return background;
	}



	public static void setBackground(Bitmap background) {
		GameBuilder.background = background;
	}



	protected static void threadedBuild(final Activity activity) {
		
		
		
		Updater up = Updater.getInstance();
		int[] dim = GraphicsUtils.getScreenSize();
		Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
		Bitmap backBitmap = Bitmap.createBitmap(dim[0], dim[1], conf);
		int skyColor = Color.parseColor("#B8DBFF");//b8dbff
//		
		for(int x = 0; x< dim[0]; x++) {
			for (int y = 0; y< dim[1]; y++) {
				backBitmap.setPixel(x, y, skyColor);
			}
		}
		
	
		
//		int x = dim[0];
//		int y = dim[1];
		
		Sprite backGround = new Sprite(backBitmap, 0, 0, 1, new int[]{1,1}) {
			
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
		
		Updater.getInstance().getSprites().add(backGround);
		
		GraphicsUtils.setBitmapResources(Constants.MOUNTAINS, getBackground());
		Sprite mountain = getBackgroundSprite();
		mountain.setY(dim[1]-mountain.getResizedHeight());
		
		Updater.getInstance().getSprites().add(mountain);
		
		Bitmap bmp = Bitmap.createBitmap(GraphicsUtils.ONEPERCENTWIDTH*20, GraphicsUtils.ONEPERCENTWIDTH*20, conf);
//		for(int x = 0; x < bmp.getWidth(); x++) {
//			for(int y = 0; y < bmp.getHeight(); y++){
//				int color = Color.parseColor("#ADD8E6");//Color.TRANSPARENT;
////				if(x == 0 || y == 0 || x == bmp.getWidth() -1 || y == bmp.getHeight() -1) {
////					color = Color.TRANSPARENT;
////				}
//				bmp.setPixel(x, y, color);
//			}
//		}
		
		GraphicsUtils.setBitmapResources(Constants.SQUARED_BITMAP, bmp);
		Square square = new Square(bmp, (GraphicsUtils.ONEPERCENTWIDTH*30), (GraphicsUtils.ONEPERCENTWIDTH*40), Constants.FPS, new int[]{1,1});
		square.setFattore(Fattore.MOLTIPLICANDO);
		Square square2 = new Square(bmp, (GraphicsUtils.ONEPERCENTWIDTH*70), (GraphicsUtils.ONEPERCENTWIDTH*50), Constants.FPS, new int[]{1,1});
		square2.setFattore(Fattore.MOLTIPLICATORE);
		
		Bitmap smileySprites = GraphicsUtils.getBitmapResources(Constants.SMILE_BITMAP,"gfx/smiley_sprites.png", activity);
		Bitmap sadSprites = GraphicsUtils.getBitmapResources(Constants.SAD_BITMAP,"gfx/smiley_sad_sprites.png", activity);
		
		Bitmap cloud = GraphicsUtils.getBitmapResources(Constants.CLOUD,"gfx/cloud.png", activity);
		
		Cloud cloud1 = new Cloud(cloud, -20, (GraphicsUtils.ONEPERCENTWIDTH*40), 20, new int[]{1,1});
		buildCloud(cloud1, dim[0]/5, Direction.RIGHT, 5, 10, dim, 30);
	
		Cloud cloud2 = new Cloud(cloud, dim[0], (GraphicsUtils.ONEPERCENTWIDTH*42), 20, new int[]{1,1});
		buildCloud(cloud2, dim[0]/7, Direction.LEFT, 7, 10, dim, 30);
		
		Cloud cloud3 = new Cloud(cloud, -20, (GraphicsUtils.ONEPERCENTWIDTH*38), 20, new int[]{1,1});
		buildCloud(cloud3, dim[0]/10, Direction.RIGHT, 7, 10, dim, 30);
		
		Updater.getInstance().getSprites().add(cloud1);
		Updater.getInstance().getSprites().add(cloud2);
		Updater.getInstance().getSprites().add(cloud3);
		
		for(int x = 0; x< 20; x++) {
			
			int size = dim[0]/20;
			Random r = new Random();
			 int rx = r.nextInt(5);
			 int ry = r.nextInt(3);
			
			Smile smile = new Smile(smileySprites,x*size,0,1,new int[]{5,3},new int[]{rx,ry});
			smile.setScaleWidth(size);
			Updater.getInstance().getSprites().add(smile);
		}
		
		for(int x = 0; x< 20; x++) {
			
			int size = dim[0]/20;
			Random r = new Random();
			 int rx = r.nextInt(5);
			 int ry = r.nextInt(3);
			
			Smile smile = new Smile(sadSprites,x*size,size,1,new int[]{5,3},new int[]{rx,ry});
			smile.setScaleWidth(size);
			Updater.getInstance().getSprites().add(smile);
		}
		
//		int size = dim[0]/10;
//		Smile smile = new Smile(smileySprites,0,0,1,new int[]{5,3},new int[]{0,0});
//		smile.setScaleWidth(size);
//		Updater.getInstance().getSprites().add(smile);
//		
//		Smile smile2 = new Smile(smileySprites,size,0,1,new int[]{5,3},new int[]{1,0});
//		smile2.setScaleWidth(size);
//		Updater.getInstance().getSprites().add(smile2);
		
		Updater.getInstance().getSprites().add(square);
		Updater.getInstance().getSprites().add(square2);
		
		

//		TextView tv = (TextView) activity.findViewById(R.id.textAdView);
//		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20));
//		tv.setText("FAKE ADMOB");
//		tv.setBackgroundColor(Color.MAGENTA);
//		tv.setTextColor(Color.WHITE);
		
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
//				while (!GameBuilder.isReady() || ll == null) {
//					try {
//						Thread.sleep(200);
//						if (ll == null) {
//							ll = (LinearLayout) activity.findViewById(R.id.gamellayout);
//						}
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						android.util.Log.e(tag,e.getMessage());
//					}
//				}
				
				TextView tv = (TextView) activity.findViewById(R.id.textAdView);
				//tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20));
				tv.setHeight(150);
				tv.setText("FAKE ADMOB");
				tv.setBackgroundColor(Color.MAGENTA);
				tv.setTextColor(Color.WHITE);
				//activity.findViewById(R.id.gamellayout).postInvalidate();
			}
			
		});
		
		
	}
	
	private static void buildCloud(Cloud cloudSprite,int scaleWidth, Direction direction, int speed, int speedRange, int[] sourceDim, int yRange) {
		cloudSprite.setScaleWidth(scaleWidth);
		cloudSprite.setDirection(direction);
		cloudSprite.setSpeed(speed);
		cloudSprite.setSpeedRange(speedRange);
		cloudSprite.setSourcedim(sourceDim);
		cloudSprite.setyRange(yRange);
	}
	
	

	public static Typeface getTypeFace() {
		return typeFace;
	}



	public static void setTypeFace(Typeface typeFace) {
		GameBuilder.typeFace = typeFace;
	}



	public static void build(final Activity activity) {
		
		setTypeFace(Typeface.createFromAsset(activity.getAssets(),"fonts/WalterTurncoat.ttf"));
		AssetManager manager = activity.getAssets();
		try {
			setBackground(BitmapFactory.decodeStream (manager.open("gfx/mountain.png")));
			
			activity.getWindowManager().getDefaultDisplay().getMetrics(getOut());
			int w = out.widthPixels;
			
			Sprite backGround = new Sprite(getBackground(), 0, 0, 1, new int[]{1,1}) {
				@Override
				public void onTouch(MotionEvent event) {
				}
				@Override
				public void onCollide() {
				}
				@Override
				protected void doUpdate() {
				}
			};
			
			backGround.setScaleWidth(w);
			setBackgroundSprite(backGround);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			android.util.Log.e(GameBuilder.tag, e1.getMessage());
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!GameBuilder.isReady()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						android.util.Log.e(GameBuilder.tag, e.getMessage());
					}
				}
				GameBuilder.setReady(false);
				GameBuilder.threadedBuild(activity);
				GameBuilder.setReady(true);
			}
		}).start();
		
		
		
	}


}
