package com.september.tableroids.model.elements;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

import com.september.tableroids.builder.GameBuilder;
import com.september.tableroids.model.Sprite;
import com.september.tableroids.utils.Updater;


public class Square extends Sprite{

	Random r;
	Integer value;
	
	
	
	
	public Random getR() {
		if(r == null) {
			setR(new Random());
		}
		return r;
	}

	public void setR(Random r) {
		this.r = r;
	}

	public Integer getValue() {
		if(value == null) {
			setValue(getR().nextInt(9)+1);
		}
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Square(Bitmap bitmap, int x, int y, int fps, int[] frameCount) {
		super(bitmap,  x,  y, fps,  frameCount);
		r = new Random();
	}
	
	private void drawtext(Canvas canvas) {
		
		Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(Color.DKGRAY);
		circlePaint.setStyle(Style.STROKE);
		circlePaint.setStrokeWidth(5L);
		Updater.getInstance().getCanvas().drawCircle(getX()+(getSpriteWidth()/2), getY()+(getSpriteHeight()/2), getSpriteHeight()/2, circlePaint);
		
		circlePaint.setColor(Color.WHITE);
		circlePaint.setStyle(Style.FILL);
		Updater.getInstance().getCanvas().drawCircle(getX()+(getSpriteWidth()/2), getY()+(getSpriteHeight()/2), getSpriteHeight()/2, circlePaint);
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.DKGRAY);
        paint.setTextSize(getSpriteHeight());
        paint.setTypeface(GameBuilder.getTypeFace());
        paint.setShadowLayer(1f, 0f, 1f, Color.GRAY);
        int tW = (int) paint.measureText(""+getValue());
        int spacer = ((getSpriteWidth()-tW)/2);
		Updater.getInstance().getCanvas().drawText(""+getValue(), getX()+spacer, getY()+getSpriteHeight()-(spacer/2), paint);
	}

	@Override
	protected void doUpdate() {
		//drawtext(Updater.getInstance().getCanvas());
		setValue(null);
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
