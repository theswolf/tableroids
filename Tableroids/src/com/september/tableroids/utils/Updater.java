package com.september.tableroids.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.SparseArray;

import com.september.tableroids.model.Sprite;

public class Updater {

	private static  Updater INSTANCE;
	private List<Sprite> addList;
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
	
	public void addSprite(Sprite s) {
		getSprites().add(s);
	}

	public List<Sprite> getSprites() {
		if(addList == null) {
			//addList = Collections.synchronizedList(new LinkedList<Sprite>());
			addList = new LinkedList<Sprite>();
		}
		return addList;
	}
	
	public void clear() {
		for (Iterator<Sprite> iterator = getSprites().iterator(); iterator.hasNext();) {
			Sprite s = iterator.next();
			if(s.isDirty()) {
				iterator.remove();
			}
			
		}
		//addList = null;
	}

	public SparseArray<Bitmap> getResources() {
		if(resources == null) {
			resources = new SparseArray<Bitmap>();
		}
		return resources;
	}

//	public void setResources(SparseArray<Bitmap> resources) {
//		this.resources = resources;
//	}
	
	public Sprite getById(int id) {
			for(Sprite s: getSprites()) {
				if(s.getId() == id) {
					return s;
				}
			}
		return null;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}
	
//	public Sprite getById(int id,List<Sprite> input) {
//		for(Sprite s: input) {
//			if(s.getId() == id) {
//				return s;
//			}
//		}
//	return null;
//}
	
	
	
}
