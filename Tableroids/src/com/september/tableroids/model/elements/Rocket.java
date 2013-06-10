package com.september.tableroids.model.elements;

import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.Sprite;

public class Rocket extends Sprite {
	
	public Rocket(MainGamePanel panel, Bitmap bitmap, int x, int y, int width,
			int height, int fps, int horizontalFrameCount,
			int verticalFrameCount, int scaleSize) {
		super(panel, bitmap, x, y, width, height, fps, horizontalFrameCount,
				verticalFrameCount, scaleSize);
		// TODO Auto-generated constructor stub
	}

	public enum Direction{
		LEFT,
		RIGHT
	}

	
	
	private int toX;
	private boolean moving = false;
	private final static int INCREASE_SPEED = 10;
	private static int INCREASE = 10;
	private static boolean lastDirectionRight;
	private static Direction direction;
	private final static String TAG = Rocket.class.getName();
	MotionEvent lastEvent;
	@Override
	public void onTouch(MotionEvent event) {
		

		
		int w = 1, h = 1;

		Bitmap.Config conf = Bitmap.Config.RGB_565; // see other conf types
		Bitmap bmp = Bitmap.createBitmap(w, h, conf); 
		//bmp.setPixel(0, 0, Color.BLACK);
		bmp.setPixel(0, 0, Color.BLACK);
		Sprite temp = new Sprite(panel,bmp, (int)event.getX(), (int)event.getY(), 1, 1, 1, 1, 1, 1);
		if(collide(temp)) {
			android.util.Log.d(TAG, "Collision detected");
			Bitmap shoot = Bitmap.createBitmap(3, 3, conf); 
			shoot.setPixel(1, 0, Color.YELLOW);
			shoot.setPixel(0, 1, Color.YELLOW);
			shoot.setPixel(2, 1, Color.YELLOW);
			shoot.setPixel(2, 2, Color.YELLOW);
			//shoot.setPixel(1, 1, Color.RED);
			
			Shoot left = new Shoot(panel, shoot, getX()+5, getY(), 3, 3, 1, 1, 1, 1);
			Shoot right = new Shoot(panel, shoot, getX()+getSpriteWidth() -5 , getY(), 3, 3, 1, 1, 1, 1);
			panel.getSprites().add(left);
			panel.getSprites().add(right);
			
		}
		else {
			moving = true;
			toX = (int) event.getX();
			Direction newDirection = toX > getX()+(getSpriteWidth()/2) ? Direction.RIGHT : Direction.LEFT;
			if(!(direction == null)) {
				if(direction.equals(newDirection)) {
					INCREASE+=INCREASE_SPEED;
				}
				else {
					INCREASE-=INCREASE_SPEED;
				}
			}
			direction = newDirection;
		}
		
	}
	
	@Override
	public void update(long gameTime) { 
		if (gameTime > frameTicker + framePeriod) {
			
			if(moving) {
				if(toX > getX()+(getSpriteWidth()/2)) {
					setX(getX()+INCREASE > toX ? toX : getX()+INCREASE);
				}
				else if(toX < getX()+(getSpriteWidth()/2)) {
					setX(getX()-INCREASE < toX ? toX : getX()-INCREASE);
				}
				
				if(getX() == toX) {
					moving = false;
					INCREASE = INCREASE_SPEED;
				}
			}
			
		}
		
		super.update(gameTime);
	}

}
