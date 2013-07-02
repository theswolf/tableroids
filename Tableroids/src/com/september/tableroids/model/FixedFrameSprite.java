package com.september.tableroids.model;

import android.graphics.Bitmap;

public abstract class FixedFrameSprite extends Sprite{

	public FixedFrameSprite(Bitmap bitmap, int x, int y, int fps,
			int[] frameCount, int[] frameRef) {
		super(bitmap, x, y, fps, frameCount);
		frameNr = frameRef;
	}

	@Override
	protected void cutOut() { 
		this.sourceRect.left = frameNr[0] * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
		
		this.sourceRect.top = frameNr[1] * spriteHeight;
		this.sourceRect.bottom = this.sourceRect.top + spriteHeight;
	}
}
