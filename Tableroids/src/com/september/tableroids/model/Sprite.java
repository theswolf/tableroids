/**
 * 
 */
package com.september.tableroids.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.elements.Boom;

/**
 * @author impaler
 *
 */
public class Sprite {

	private static final String TAG = Sprite.class.getSimpleName();

	private Bitmap bitmap;		// the animation sequence
	protected MainGamePanel panel;
	protected Rect sourceRect;	// the rectangle to be drawn from the animation bitmap
	protected int[] frameNr;		// number of frames in animation
	protected int currentFrame;	// the current frame
	protected long frameTicker;	// the time of the last frame update
	protected int framePeriod;	// milliseconds between each frame (1000/fps)

	protected int spriteWidth;	// the width of the sprite to calculate the cut out rectangle
	protected int spriteHeight;	// the height of the sprite

	private int x;				// the X coordinate of the object (top left of the image)
	private int y;				// the Y coordinate of the object (top left of the image)
	private List<Sprite> collision;
	private boolean ruledByGarbage = true;

	public Sprite(MainGamePanel panel,Bitmap bitmap, int x, int y, int width, int height, int fps, int horizontalFrameCount,int verticalFrameCount, int scaleSize) {
		this.panel = panel;
		this.bitmap = bitmap;//scaleImage(bitmap, scaleSize);
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNr = new int[2];
		frameNr[0] = horizontalFrameCount;
		frameNr[1] = verticalFrameCount;
		spriteWidth = this.bitmap.getWidth() / horizontalFrameCount;
		spriteHeight = this.bitmap.getHeight() / verticalFrameCount;
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
	}


	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public List<Sprite> getCollision() {
		if(collision == null) {
			setCollision(Collections.synchronizedList(new LinkedList<Sprite>()));
		}
		return collision;
	}


	public void setCollision(List<Sprite> collision) {
		this.collision = collision;
	}

	public void addCollision(Sprite sprite) {
		getCollision().add(sprite);
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
	public void setFrameNr(int pos, int frameNr) {
		this.frameNr[pos] = frameNr;
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

	protected int[] findInMatrix(int position) {
		int[] ret = new int[2];
		int counter = 0;
		for(int y = 0; y< frameNr[1]; y++) {
			for(int x = 0; x< frameNr[0]; x++) {
				counter++;
				if(counter == position) {
					ret[0] = x;
					ret[1] = y;
				}
			}
		}
		return ret;
	}

	// the update method for Elaine
	public void update(long gameTime) {



		int[] evaluated = findInMatrix(currentFrame);

		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// increment the frame
			currentFrame++;

			if (currentFrame >= frameNr[0]*frameNr[1]) {
				currentFrame = 0;
			}
		}

		// define the rectangle to cut out sprite
		this.sourceRect.left = (evaluated[0]) * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;

		this.sourceRect.top = (evaluated[1]) * spriteHeight;
		this.sourceRect.bottom = this.sourceRect.top +spriteHeight;
	}



	//	private Bitmap scaleImage(Bitmap bitmap, int ratio) 
	//	{
	//		Bitmap newBitmap;
	//
	//		newBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/ratio, bitmap.getHeight()/ratio, false);
	//		return newBitmap;
	//	}

	// the draw method which draws the corresponding frame
	public void draw(Canvas canvas) {
		// where to draw the sprite
		getCollision();

		if(panel.getSprites().contains(this)) {
			synchronized (collision) {
				for(Sprite collider: getCollision()) {
					if(collide(collider)) {
						onCollide(collider);
					}
				}

			}
			


			Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
			canvas.drawBitmap(bitmap, sourceRect, destRect, null);
			//		canvas.drawBitmap(bitmap, 20, 150, null);
			//		Paint paint = new Paint();
			//		paint.setARGB(50, 0, 255, 0);
			//		canvas.drawRect(20 + (  (currentFrame%frameNr[0]) * destRect.width()), 150, 20 + (  (currentFrame%frameNr[0]) * destRect.width()) + destRect.width(), 150 + destRect.height(),  paint);

		}

	}


	protected void onCollide(Sprite collider) {

	}

	public void onTouch(MotionEvent event) {

	}

	public void boom(Sprite collider) {

		panel.getSpritesToRemove().add(this);
		if(collider != null ) {
			panel.getSpritesToRemove().add(collider);
		}
		

		Boom boom = new Boom(panel,panel.getBitmap("explosion.png"),
				getX(),getY(),
				320,320,
				40,5,5,1);

		panel.getSpritesToAdd().add(boom);

	}

	protected boolean collide(Sprite s) {
		if (getX()<0 && s.getX()<0 && getY()<0 && s.getY()<0) return false;

		Rect myRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
		Rect colliderRect = new Rect(s.getX(), s.getY(), s.getX() + s.getSpriteWidth(), s.getY() + s.getSpriteHeight());

		if(myRect.intersect(colliderRect)) {
			for(int i = getX(); i < getX() + spriteWidth ; i++) {
				for(int j = getY(); j < getY() + spriteHeight ; j++) {
					if( (i - getX()) >= 0 && (j - getY()) >= 0 && bitmap.getPixel(i - getX(), j - getY() ) != Color.TRANSPARENT) {
						Bitmap colliderBitmap = s.getBitmap();
						if((i-s.getX()) >= 0 && (j-s.getY()) >= 0 && (i-s.getX()) < colliderBitmap.getWidth() && (j-s.getY()) < colliderBitmap.getHeight()) {
							if(colliderBitmap.getPixel(i-s.getX(), j-s.getY()) != Color.TRANSPARENT) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

}
