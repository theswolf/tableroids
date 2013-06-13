package com.september.tableroids.model.elements;

import java.io.IOException;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.MotionEvent;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.Sprite;

public class Rocket extends Sprite {
	


	public Rocket(MainGamePanel panel, Bitmap bitmap, int x, int y, int fps,
			int horizontalFrameCount, int verticalFrameCount, int scaleSize) {
		super(panel, bitmap, x, y, fps, horizontalFrameCount, verticalFrameCount,
				scaleSize);
		// TODO Auto-generated constructor stub
	}

	public enum Direction{
		LEFT,
		RIGHT
	}

	
	
	private int toX;
	private boolean moving = false;
	private final static int INCREASE_SPEED = 5;
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
		Sprite temp = new Sprite(panel,bmp, (int)event.getX(), (int)event.getY(), 1, 1, 1, 1);
		if(collide(temp) || ( (int) event.getY() ) > getY()+getSpriteHeight()/3) {
			android.util.Log.d(TAG, "Collision detected");
			
			
			Shoot left = new Shoot(panel, panel.getBitmapResources().get(panel.SHOOT), getX()+5, getY(), 10, 1, 1, 1);
			Shoot right = new Shoot(panel, panel.getBitmapResources().get(panel.SHOOT), getX()+getSpriteWidth() -5 , getY(), 10, 1, 1, 1);
//			panel.getSpritesToAdd().add(left);
//			panel.getSpritesToAdd().add(right);
			
			panel.getSprites().add(left);
			panel.getSprites().add(right);
			
			for(Integer collisionId:getCollision()) {
				Sprite collide = panel.getById(collisionId);
				if(collide != null && !(collide instanceof Shoot)) {
					left.addCollision(collide.getId());
					right.addCollision(collide.getId());
				}
				
			}
			
			
			this.addCollision(left.getId());
			this.addCollision(right.getId());
			
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
					INCREASE = Math.max(INCREASE, INCREASE-INCREASE_SPEED);
				}
			}
			direction = newDirection;
		}
		
		bmp = null;
		
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
	
	@Override
	public void onCollide() {
		if(getCollider() != null) {
			getCollider().boom();
		}
		boom();
	}

}
