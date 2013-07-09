package com.september.tableroids.model.elements;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.statics.GameBuilder;
import com.september.tableroids.statics.Scorer;
import com.september.tableroids.utils.Updater;

public class SquareResponse extends Sprite{
	
	private int color = 0;
	private static int[] colors = new int[]{Color.DKGRAY,Color.BLUE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.RED,Color.YELLOW};
	private int value;
	
	private int yRange;
	private int yValue;
	
	

	public SquareResponse(Bitmap bitmap, int x, int y, int fps, int[] frameCount) {
		super(bitmap, x, y, fps, frameCount);
		yValue = getY();
		setY(evaluateY(yValue, getyRange()));
		setTouchable(true);
	}

	@Override
	public boolean isTouchable() {
		return true;
	}
	
	public int getValue() {
		return value;
	}



	public void setValue(int value) {
		this.value = value;
	}

	

	public int getyRange() {
		return yRange;
	}



	public void setyRange(int yRange) {
		this.yRange = yRange;
	}



	public int getyValue() {
		return yValue;
	}

	public int evaluateY(int originalPos, int range) {
		return originalPos + (range > 0 ? Scorer.getR().nextInt(range) : 0);
		
	}


	public void setyValue(int yValue) {
		this.yValue = yValue;
	}



	@Override
	protected void doUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		Scorer.putResponse(getValue());
	}
	
	@Override
	public void draw(Canvas canvas) { 
		super.draw(canvas);
		drawtext(canvas);
	}
	
	public void changeColor() {
		color = Scorer.getR().nextInt(colors.length);
	}
	
private void drawtext(Canvas canvas) {
		
		
		Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(Color.WHITE);
		circlePaint.setStyle(Style.STROKE);
		circlePaint.setStrokeWidth(5L);
		Updater.getInstance().getCanvas().drawCircle(getX()+(getSpriteWidth()/2), getY()+(getSpriteHeight()/2), getSpriteHeight()/2, circlePaint);
		
		circlePaint.setColor(colors[color]);
		circlePaint.setStyle(Style.FILL);
		Updater.getInstance().getCanvas().drawCircle(getX()+(getSpriteWidth()/2), getY()+(getSpriteHeight()/2), getSpriteHeight()/2, circlePaint);
		
		
		String value = getValue()+"";
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
        paint.setTextSize(getSpriteHeight()-(getSpriteHeight()/4));
        paint.setTypeface(GameBuilder.getTypeFace());
        paint.setShadowLayer(1f, 0f, 1f, Color.GRAY);
        int tW = (int) paint.measureText(""+getValue());
        int spacerX = ((getSpriteWidth()-tW)/2);
        int spacerY = (int) ((getSpriteHeight()/2) + (paint.getTextSize()/3));
		//Updater.getInstance().getCanvas().drawText(""+getValue(), getX()+spacer, getY()+getSpriteHeight(), paint);
        Updater.getInstance().getCanvas().drawText(""+getValue(), getX()+spacerX, getY()+spacerY, paint);
	}

}
