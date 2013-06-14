package com.september.tableroids.model.elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.Sprite;

public class Shoot extends Sprite {
	
	public Shoot(Bitmap bitmap, int x, int y, int fps,
			int horizontalFrameCount, int verticalFrameCount, int scaleSize) {
		super(bitmap, x, y, fps, horizontalFrameCount, verticalFrameCount,
				scaleSize);
		// TODO Auto-generated constructor stub
	}

	private static int toY = 0;


	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas); 
	}
	
	@Override
	public void update(long gameTime) { 
		if (gameTime > frameTicker + framePeriod) {
			
			if(getY() > 0) {
				setY(getY()-5);
			}
			
			
		}
		
		super.update(gameTime);
	}
	
	@Override
	public void onCollide() { 
		setDirty(true);
	}

}
