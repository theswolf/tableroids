package com.september.tableroids.model.elements;

import java.util.Random;

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


public class Square extends Sprite{

//	private Random r;
//	private Integer value;
	private Fattore fattore;
	private static int[] colors = new int[]{Color.DKGRAY,Color.BLUE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.RED,Color.YELLOW};
	private static int color=0;
	
	public enum Fattore{
		MOLTIPLICANDO,
		MOLTIPLICATORE
	}
	
	
	public Random getR() {
//		if(r == null) {
//			setR(new Random());
//		}
//		return r;
		return Scorer.getR();
	}

//	public void setR(Random r) {
//		this.r = r;
//	}

	public void changeColor() {
		color = getR().nextInt(colors.length);
	}
	
	public Fattore getFattore() {
		return fattore;
	}

	public void setFattore(Fattore fattore) {
		this.fattore = fattore;
	}

	public Integer getValue() {
//		if(value == null) {
//			
//			int 
//			
//			setValue(getR().nextInt(9)+1);
//		}
//		return value;
		int _value = fattore.equals(Fattore.MOLTIPLICANDO) ? Scorer.getMoltiplicando() : Scorer.getMoltiplicatore();
		return _value;
	}

//	public void setValue(Integer value) {
//		this.value = value;
//	}

	public Square(Bitmap bitmap, int x, int y, int fps, int[] frameCount) {
		super(bitmap,  x,  y, fps,  frameCount);
		//r = new Random();
	}
	
	private void drawtext(Canvas canvas) {
		
		
		Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(colors[color]);
		circlePaint.setStyle(Style.STROKE);
		circlePaint.setStrokeWidth(5L);
		Updater.getInstance().getCanvas().drawCircle(getX()+(getSpriteWidth()/2), getY()+(getSpriteHeight()/2), getSpriteHeight()/2, circlePaint);
		
		circlePaint.setColor(Color.WHITE);
		circlePaint.setStyle(Style.FILL);
		Updater.getInstance().getCanvas().drawCircle(getX()+(getSpriteWidth()/2), getY()+(getSpriteHeight()/2), getSpriteHeight()/2, circlePaint);
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(colors[color]);
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
		//setValue(null);
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
