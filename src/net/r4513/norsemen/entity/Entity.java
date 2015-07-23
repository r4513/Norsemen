package net.r4513.norsemen.entity;

import java.util.Random;

import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.level.Level;
import net.r4513.norsemen.level.tile.Tile;

public abstract class Entity {

	protected double _x, _y;
	protected boolean _removed = false;
	protected Level _level;
	protected final Random _random = new Random();
	
	public abstract void update();
	public abstract void render(Screen screen);
	
	public void init(Level level){
		_level = level;
	}
	
	public double getX(){
		return _x;
	}
	
	public double getY(){
		return _y;
	}
	
	public void remove(){
		_removed = true;
	}
	
	public boolean isRemoved(){
		return _removed;
	}
}
