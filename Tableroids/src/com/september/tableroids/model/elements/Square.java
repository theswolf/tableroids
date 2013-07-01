package com.september.tableroids.model.elements;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.utils.Updater;


public class Square extends Sprite{

	Random r;
	
	public Square(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
		super(bitmap,  x,  y,  width,  height,  fps,  frameCount);
		r = new Random();
	}
	
	private void drawtext(Canvas canvas) {
		int value = r.nextInt(8)+1;
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.BLACK);
        paint.setTextSize(getSpriteHeight());
        paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);
        int tW = (int) paint.measureText(""+value);
        int spacer = ((getSpriteWidth()-tW)/2);
		Updater.getInstance().getCanvas().drawText(""+value, getX()+spacer, getY()+getSpriteHeight()-(spacer/2), paint);
	}

	@Override
	protected void doUpdate() {
		drawtext(Updater.getInstance().getCanvas());
		
	}
	
	@Override
	public void draw(Canvas canvas) { 
		super.draw(canvas);
		drawtext(canvas);
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param args
	 */

}
