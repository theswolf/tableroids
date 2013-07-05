/**
 * 
 */
package com.september.tableroids.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.MotionEvent;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.utils.Updater;

/**
 * @author impaler
 *
 */
public abstract class Sprite {

	private static final String TAG = Sprite.class.getSimpleName();

	private Bitmap bitmap;		// the animation sequence
	//protected MainGamePanel panel;
	protected Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	protected int[] frameNr;		// number of frames in animation
	protected int currentFrame;	// the current frame
	protected long frameTicker;	// the time of the last frame update
	protected int framePeriod;	// milliseconds between each frame (1000/fps)

	protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	protected int spriteHeight;	// the height of the sprite

	private int x;				// the X coordinate of the object (top left of the image)
	private int y;				// the Y coordinate of the object (top left of the image)
	private List<Integer> collision;
	private boolean ruledByGarbage = true;
	private boolean dirty = false;
	private int id;
	private Sprite collider;
	
	private boolean resized = false;
	
	private int scaleWidth;

	public Sprite(Bitmap bitmap, int x, int y, int fps, int[] frameCount) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount[0];
		spriteHeight = bitmap.getHeight() / frameCount[1];
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
	

		id = (new Random()).nextInt();
	}

	public int getId() {
		return id;
	}
	
	

	public int getScaleWidth() {
		return scaleWidth;
	}

	public void setScaleWidth(int scaleWidth) {
		this.resized = true;
		this.scaleWidth = scaleWidth;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	

	public Sprite getCollider() {
		return collider;
	}

	public void setCollider(Sprite collider) {
		this.collider = collider;
	}

	public synchronized List<Integer> getCollision() {
		if(collision == null) {
			setCollision(new LinkedList<Integer>());
		}
		return collision;
	}



	public boolean isDirty() {
		return dirty;
	}


	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}


	public void setCollision(List<Integer> collision) {
		this.collision = collision;
	}

	public void addCollision(Integer spriteId) {
		if(!getCollision().contains(spriteId)) {
			getCollision().add(spriteId);
			if(spriteId != getId()) {
				Sprite ref = Updater.getInstance().getById(spriteId);
				if(ref != null) {
					Updater.getInstance().getById(spriteId).addCollision(getId());
				}
			}
		}
			
	}



	public boolean isRuledByGarbage() {
		return ruledByGarbage;
	}


	public void setRuledByGarbage(boolean ruledByGarbage) {
		this.ruledByGarbage = ruledByGarbage;
	}


	public Rect getSourceRect() {
		return sourceRect;
	}
	public void setSourceRect(Rect sourceRect) {
		this.sourceRect = sourceRect;
	}
	public int[] getFrameNr() {
		return frameNr;
	}
	public void setFrameNr(int[] frameNr) {
		this.frameNr = frameNr;
	}
	public int getCurrentFrame() {
		return currentFrame;
	}
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	public int getFramePeriod() {
		return framePeriod;
	}
	public void setFramePeriod(int framePeriod) {
		this.framePeriod = framePeriod;
	}
	public int getSpriteWidth() {
		return spriteWidth;
	}
	public void setSpriteWidth(int spriteWidth) {
		this.spriteWidth = spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public void setSpriteHeight(int spriteHeight) {
		this.spriteHeight = spriteHeight;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	protected abstract void doUpdate();
	
	private int[] findInMatrix(int currentFrame) {
		int counterX = 0;
		int counterY = 0;
		for(int x = 0; x<currentFrame; x++) {
			if(x == currentFrame) {
				return new int[]{counterX,counterY};
			}
			else {
				if(x % frameNr[0] == 0 ) {
					counterX++;
				}
				if(x % frameNr[1] == 0 ) {
					counterY++;
				}
			}
		}
		return new int[]{counterX,counterY};
	}

	public void update(long gameTime) {

		
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;
			if (currentFrame >= frameNr[0]*frameNr[1]) {
				currentFrame = 0;
			}
			
			doUpdate();
			cutOut();
		}

		
		
		
	}

	protected void cutOut() {
		
		// define the rectangle to cut out sprite
//		this.sourceRect.left = currentFrame * spriteWidth;
//		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		
		this.sourceRect.left = findInMatrix(currentFrame)[0] * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		
		this.sourceRect.top = findInMatrix(currentFrame)[1] * spriteHeight;
		this.sourceRect.bottom = this.sourceRect.top + spriteHeight;
	}
	
	public int getResizedHeight() {
		return resized ? (getScaleWidth()*spriteHeight)/spriteWidth : spriteHeight;
	}

	public void draw(Canvas canvas) {
		
		getCollision();

		int width = resized ? getScaleWidth() : spriteWidth;
		int height = resized ? (getScaleWidth()*spriteHeight)/spriteWidth : spriteHeight;
		
		Rect destRect = new Rect(getX(), getY(), getX() + width, getY() + height);
		
		Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
		
		canvas.drawBitmap(bitmap, sourceRect, destRect, paint);
		//		canvas.drawBitmap(bitmap, 20, 150, null);
		//		Paint paint = new Paint();
		//		paint.setARGB(50, 0, 255, 0);
		//		canvas.drawRect(20 + (  (currentFrame%frameNr[0]) * destRect.width()), 150, 20 + (  (currentFrame%frameNr[0]) * destRect.width()) + destRect.width(), 150 + destRect.height(),  paint);

	}


	public abstract void onCollide();

	public abstract void onTouch(MotionEvent event);


	public boolean collide(Sprite s) {
		if (getX()<0 && s.getX()<0 && getY()<0 && s.getY()<0) return false;

		Rect myRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
		Rect colliderRect = new Rect(s.getX(), s.getY(), s.getX() + s.getSpriteWidth(), s.getY() + s.getSpriteHeight());

		if(myRect.intersect(colliderRect)) {
			for(int i = getX(); i < getX() + spriteWidth ; i++) {
				for(int j = getY(); j < getY() + spriteHeight ; j++) {
					int x1 = i - getX();
					int y1 = j-getY();
					if(x1 >= 0 && y1>= 0 && x1<bitmap.getWidth() && y1 < bitmap.getHeight()) {
						if(bitmap.getPixel(x1, y1) != Color.TRANSPARENT) {
							Bitmap colliderBitmap = s.getBitmap();
							int x2= i-s.getX();
							int y2 = j-s.getY();

							if(x2 >= 0 && y2>=0 && x2<colliderBitmap.getWidth() && y2 < colliderBitmap.getHeight()) {
								if(colliderBitmap.getPixel(x2,y2) != Color.TRANSPARENT) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

}
