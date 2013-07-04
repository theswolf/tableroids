package com.september.tableroids.model.elements;

import java.util.Random;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.september.tableroids.model.Sprite;
import com.september.tableroids.statics.Scorer;

public class Cloud extends Sprite {
	
	public enum Direction {
		LEFT,
		RIGHT
	}
	
	private int speed;
	private int speedRange;
	private int[] sourcedim;
	private int yRange;
	private int yValue;
	//private Random r;
	private Direction direction;
	
	

	public Cloud(Bitmap bitmap, int x, int y, int fps, int[] frameCount) {
		super(bitmap, x, y, fps, frameCount);
		yValue = getY();
		setY(evaluateY(yValue, getyRange()));
	}
	
	public int evaluateY(int originalPos, int range) {
		return originalPos + (range > 0 ? getR().nextInt(range) : 0);
		
	}

	@Override
	protected void doUpdate() {
		if(getDirection().equals(Direction.RIGHT)) {
			doRightUpdate();
		}
		else {
			doLeftUpdate();
		}
		
	}
	
	private void doRightUpdate() {
		if(getX() > getSourcedim()[0]) //Out of display
		{
			int evaluatedNewSpeed = getR().nextInt(getSpeedRange());
			setSpeed(evaluatedNewSpeed > 0 ? evaluatedNewSpeed : getSpeed());
			setX(0-getSpriteWidth());
			setY(evaluateY(yValue, getyRange()));
		}
		
		else {
			setX(getX()+speed);
		}
	}
	
	private void doLeftUpdate() {
		if(getX()+getSpriteWidth() <0 ) //Out of display
		{
			int evaluatedNewSpeed = getR().nextInt(getSpeedRange());
			setSpeed(evaluatedNewSpeed > 0 ? evaluatedNewSpeed : getSpeed());
			setX(getSourcedim()[0]);
			setY(evaluateY(yValue, getyRange()));
		}
		
		else {
			setX(getX()-speed);
		}
	}


	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int[] getSourcedim() {
		return sourcedim;
	}

	public void setSourcedim(int[] sourcedim) {
		this.sourcedim = sourcedim;
	}

	public int getyRange() {
		return yRange;
	}

	public void setyRange(int yRange) {
		this.yRange = yRange;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getSpeedRange() {
		return speedRange;
	}

	public void setSpeedRange(int speedRange) {
		this.speedRange = speedRange;
	}
	
	private Random getR() {
//		if(r== null) {
//			r = new Random();
//		}
		return Scorer.getR();
	}
	

}
