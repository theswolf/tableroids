package com.september.tableroids.model.elements;

import android.graphics.Bitmap;

import com.september.tableroids.MainGamePanel;
import com.september.tableroids.model.Sprite;

public class Boom extends Sprite{


	
	public Boom(Bitmap bitmap, int x, int y, int fps,
			int horizontalFrameCount, int verticalFrameCount, int scaleSize) {
		super( bitmap, x, y, fps, horizontalFrameCount, verticalFrameCount,
				scaleSize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(long gameTime) {
		super.update(gameTime);
		if(currentFrame == 0) {
			//panel.getSpritesToRemove().add(this);
			setDirty(true);
		}
	}
}
