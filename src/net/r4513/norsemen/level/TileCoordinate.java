package net.r4513.norsemen.level;

public class TileCoordinate {

	private int _x, _y;
	private final int TILE_SIZE = 16;
	
	public TileCoordinate(int x, int y){
		_x = x * TILE_SIZE;
		_y = y * TILE_SIZE;
	}
	
	public int getX(){
		return _x;
	}
	
	public int getY(){
		return _y;
	}
	
	public int[] getXY(){
		int[] result = new int[2];
		result[0] = _x;
		result[1] = _y;
		return result;
	}
}
