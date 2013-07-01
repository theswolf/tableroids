package com.september.tableroids.model.elements;

import android.graphics.Bitmap;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.SpriteFixedFrame;

public class Asteroid extends SpriteFixedFrame{


	
	public Asteroid(MainGamePanel panel, Bitmap bitmap, int x, int y, int fps,
			int horizontalFrameCount, int verticalFrameCount, int scaleSize) {
		super(panel, bitmap, x, y, fps, horizontalFrameCount, verticalFrameCount,
				scaleSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(long gameTime) { 
		if (gameTime > frameTicker + framePeriod) {
				setY(getY()+10);
		}
		
		super.update(gameTime);
	}
	
	@Override
	public void onCollide() {

	}


}
