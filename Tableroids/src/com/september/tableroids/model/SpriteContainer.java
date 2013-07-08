package com.september.tableroids.model;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;

public class SpriteContainer extends Sprite{
	
	private List<Sprite> children;
	
	

	public List<Sprite> getChildren() {
		if(children==null) {
			setChildren(new LinkedList<Sprite>());
		}
		return children;
	}

	public void setChildren(List<Sprite> children) {
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
	public void draw(Canvas canvas) {
		super.draw(canvas);
		for(Sprite s: getChildren()) {
			s.draw(canvas);
		}
	}
	
	@Override
	public boolean collide(Sprite s) {
		return true;
	}
	
	@Override
	public void update(long gameTime) {
		super.update(gameTime);
		for(Sprite s: getChildren()) {
			s.update(gameTime);
		}
		
	}
	
	public void appendChildren(Sprite child) {
		if(!getChildren().contains(child)) {
			getChildren().add(child);
		}
	
		child.setX(getX()+child.getX());
		child.setY(getY()+child.getY());
	}

	@Override
	public void onTouch(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
