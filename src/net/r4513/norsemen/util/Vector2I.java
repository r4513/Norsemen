package net.r4513.norsemen.util;

public class Vector2I {

	private int _x;
	private int _y;

	public Vector2I(int x, int y){
		set(x,y);
	}
	
	public Vector2I(){
		set(0,0);
	}
	
	public Vector2I(Vector2I vec){
		set(vec.getX(), vec.getY());
	}
	
	public double getDistance(Vector2I vec2){
		double x = getX() - vec2.getX();
		double y = getY() - vec2.getY();
		
		return Math.sqrt(x * x + y * y);
	}
	
	public void set(int x, int y){
		_x = x;
		_y = y;
	}
	
	public Vector2I setX(int x){
		_x = x;
		return this;
	}
	
	public Vector2I setY(int y){
		_y = y;
		return this;
	}

	public int getX(){
		return _x;
	}

	public int getY(){
		return _y;
	}
	
	public void add(Vector2I vec){
		_x += vec.getX();
		_y += vec.getY();
	}
	
	public void sub(Vector2I vec){
		_x -= vec.getX();
		_y -= vec.getY();
	}
	
	public Vector2I verticalFlip(){
		return new Vector2I(_x, -_y);
	}

	public Vector2I horizontalFlip(){
		return new Vector2I(-_x, _y);
	}

	public double getLength(){
		return Math.sqrt((_x * _x) + (_y * _y));
	}

	public Vector2I scale(int f){
		return new Vector2I(_x*f, _y*f);
	}

	public void setDirection(int x, int y){
		_x = x;
		_y = y;
	}

	public Vector2I getDirection(){
		return new Vector2I(_x, _y);
	}

	public String toString(){
		return "(" + _x + "," + _y + ")";
	}

	public boolean equals(Object vec){
		if(!(vec instanceof Vector2I)) return false;
		if(_x == ((Vector2I) vec).getX() && _y == ((Vector2I) vec).getY()) return true;
		return false;
	}
}
