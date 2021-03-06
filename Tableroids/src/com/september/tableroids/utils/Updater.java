package com.september.tableroids.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.SparseArray;

import com.september.tableroids.model.Sprite;

public class Updater {

	private static  Updater INSTANCE;
	private List<Sprite> sprites;
	private SparseArray<Bitmap> resources;
	private Canvas canvas;
	
	private Updater() {
	}
	
	public static Updater getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Updater();
		}
		return INSTANCE;
	}
	
//	public Sprite[] getSpritesAsArray() {
//		Sprite[] spriteArray = new Sprite[getSprites().size()];
//		for(int x = 0; x < getSprites().size() ; x++) {
//			spriteArray[x] = getSprites().valueAt(x);
//		}
//		return spriteArray;
//	}
	
	public Sprite getById(int id) {
		for(Sprite s: getSprites()) {
			if(s.getId() == id) {
				return s;
			}
		}
		return null;
	}
	
	public List<Sprite> getSprites() {
		if(sprites == null) {
			setSprites(new CopyOnWriteArrayList<Sprite>());
		}
		return sprites;
	}

	public void setSprites(List<Sprite> sprites) {
		this.sprites = sprites;
	}

	public void addSprite(Sprite s) {
		getSprites().add(s);
	}
	
	public void clear() {
		//Integer[] removal = new Integer[getSprites().size()];
		//int limit = getSprites().size();
		List<Sprite> maintain = new CopyOnWriteArrayList<Sprite>();
		for(Sprite s: getSprites()) {
			if (!s.isDirty()) {
				maintain.add(s);
			}
		}
		
		setSprites(maintain);
		//getSprites().removeAll(remove);
//		for (Iterator<Sprite> iterator = getSprites().iterator(); iterator.hasNext();) {
//			if(iterator.next().isDirty()) {
//				iterator.remove();
//			}	
//		}
	}

	public SparseArray<Bitmap> getResources() {
		if(resources == null) {
			resources = new SparseArray<Bitmap>();
		}
		return resources;
	}



	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
	
	
}
