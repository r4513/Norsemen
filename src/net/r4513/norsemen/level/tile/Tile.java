package net.r4513.norsemen.level.tile;

import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;

public abstract class Tile {

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;

	// Tiles from colors
	public static final int GRASS_COLOR = 0xFF000000;
	public static final int GRASS_ROAD_COLOR = 0xFFFF0000;
	public static final int GRASS_SAND_COLOR = 0xFF00FF00;
	public static final int GRASS_WATER_COLOR = 0xFF0000FF;
	public static final int GRASS_FLOOR_COLOR = 0xFF550000;
	public static final int GRASS_WALL1_COLOR = 0xFF005500;
	public static final int GRASS_WALL2_COLOR = 0xFF000055;

	// Tiles from tiles_normal.png
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile road_sand = new RoadWSandTile(Sprite.road_sand);
	public static Tile sand = new SandTile(Sprite.sand);
	public static Tile road_dark = new RoadDarkTile(Sprite.road_dark);
	public static Tile wall_red = new WallTile(Sprite.wall_red);
	public static Tile wall_dark = new WallTile(Sprite.wall_dark);
	public static Tile water1 = new WaterTile(Sprite.water1);

	// Void tile
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	// Class member variables
	private int _x, _y;
	private Sprite _sprite;
	protected boolean _isSolid = false;
	protected boolean _isLiquid = false;

	public Tile(Sprite sprite) {
		_sprite = sprite;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x * WIDTH, y * HEIGHT, this);
	}

	public boolean solid() {
		return _isSolid;
	}

	public boolean isLiquid() {
		return _isLiquid;
	}

	public Sprite getSprite() {
		return _sprite;
	}

	public int getX() {
		return _x;
	}

	public void setX(int x) {
		_x = x;
	}

	public int getY() {
		return _y;
	}

	public void setY(int y) {
		_y = y;
	}
}
