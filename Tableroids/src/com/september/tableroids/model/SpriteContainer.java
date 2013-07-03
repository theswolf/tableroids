package com.september.tableroids.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.SparseArray;
import android.view.MotionEvent;

public class SpriteContainer extends Sprite{
	
	private SparseArray<Sprite> children;
	
	

	public SparseArray<Sprite> getChildren() {
		if(children==null) {
			setChildren(new SparseArray<Sprite>());
		}
		return children;
	}

	public void setChildren(SparseArray<Sprite> children) {
		this.children = children;
	}

	public SpriteContainer(Bitmap bitmap, int x, int y, int fps,
			int[] frameCount) {
		super(bitmap, x, y, fps, frameCount);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Canvas canvas) {
		for(int z = 0; z < getChildren().size(); z++) {
			Sprite s = getChildren().valueAt(z);
			s.setX(s.getX()+getX());
			s.setY(s.getY()+getY());
			s.draw(canvas);
		}
	}
	
	@Override
	public void update(long gameTime) {
		for(int z = 0; z < getChildren().size(); z++) {
			Sprite s = getChildren().valueAt(z);
			s.update(gameTime);
		}
		
	}
	
	public void appendChildren(Sprite child) {
		getChildren().put(child.getId(), child);
	}

}
