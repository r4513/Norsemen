package net.r4513.norsemen.graphics;

import net.r4513.norsemen.entity.mob.Player;
import net.r4513.norsemen.level.building.Building;
import net.r4513.norsemen.level.tile.Tile;

public class Sprite {

	// Tile sprites here
	public static Sprite grass = new Sprite(Tile.WIDTH, Tile.HEIGHT, 0, 0,
			SpriteSheet.normal_level);
	public static Sprite road_sand = new Sprite(Tile.WIDTH, Tile.HEIGHT, 1, 0,
			SpriteSheet.normal_level);
	public static Sprite sand = new Sprite(Tile.WIDTH, Tile.HEIGHT, 2, 0,
			SpriteSheet.normal_level);
	public static Sprite road_dark = new Sprite(Tile.WIDTH, Tile.HEIGHT, 0, 1,
			SpriteSheet.normal_level);
	public static Sprite wall_red = new Sprite(Tile.WIDTH, Tile.HEIGHT, 1, 1,
			SpriteSheet.normal_level);
	public static Sprite wall_dark = new Sprite(Tile.WIDTH, Tile.HEIGHT, 2, 1,
			SpriteSheet.normal_level);
	public static Sprite water1 = new Sprite(Tile.WIDTH, Tile.HEIGHT, 0, 2,
			SpriteSheet.normal_level);
	public static Sprite water2 = new Sprite(Tile.WIDTH, Tile.HEIGHT, 1, 2,
			SpriteSheet.normal_level);
	public static Sprite water3 = new Sprite(Tile.WIDTH, Tile.HEIGHT, 2, 2,
			SpriteSheet.normal_level);

	// Building sprites here
	public static Sprite tippi = new Sprite(Building.WIDTH, Building.HEIGHT, 0,
			0, SpriteSheet.buildings);

	// Animated Sprites here

	// Void sprite here
	public static Sprite voidSprite = new Sprite(Tile.WIDTH, Tile.HEIGHT,
			0xFF00FF);

	// Dummy sprite
	public static Sprite dummy = new Sprite(Player.SIZE, Player.SIZE, 0, 0,
			SpriteSheet.dummy_down);

	// Particles
	public static Sprite particle_normal = new Sprite(1, 1, 0xFFFFFF);
	public static Sprite particle_fire = new Sprite(3, 3, 0xD8FF00);

	// Projectile sprite here
	public static Sprite projectile_fireball1 = new Sprite(3, 3, 0xD8FF00);

	// Player sprites here
	public static Sprite player_down1 = new Sprite(Player.SIZE, Player.SIZE, 0,
			0, SpriteSheet.player);
	/*
	 * public static Sprite player_down2 = new Sprite(Player.SIZE, 1, 0,
	 * SpriteSheet.player); public static Sprite player_down3 = new
	 * Sprite(Player.SIZE, 2, 0, SpriteSheet.player); public static Sprite
	 * player_up1 = new Sprite(Player.SIZE, 0, 1, SpriteSheet.player); public
	 * static Sprite player_up2 = new Sprite(Player.SIZE, 1, 1,
	 * SpriteSheet.player); public static Sprite player_up3 = new
	 * Sprite(Player.SIZE, 2, 1, SpriteSheet.player); public static Sprite
	 * player_side1 = new Sprite(Player.SIZE, 0, 2, SpriteSheet.player); public
	 * static Sprite player_side2 = new Sprite(Player.SIZE, 1, 2,
	 * SpriteSheet.player); public static Sprite player_side3 = new
	 * Sprite(Player.SIZE, 2, 2, SpriteSheet.player);
	 */
	private int _x, _y;
	private int[] _pixels;
	protected SpriteSheet _sheet;
	private int _width;
	private int _height;

	// Used for animated sprites
	protected Sprite(SpriteSheet sheet, int width, int height) {
		_sheet = sheet;
		_width = width;
		_height = height;
	}

	public Sprite(int width, int height, int color) {
		_width = width;
		_height = height;
		_pixels = new int[_width * _height];
		setColor(color);
	}

	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		_width = width;
		_height = height;
		_pixels = new int[width * height];
		_x = x * width;
		_y = y * height;
		_sheet = sheet;
		load();
	}

	public Sprite(int[] pixels, int width, int height) {
		_width = width;
		_height = height;
		_pixels = new int[pixels.length];
		;
		for (int i = 0; i < pixels.length; i++) {
			_pixels[i] = pixels[i];
		}
	}

	private void setColor(int color) {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = color;
		}
	}

	public void load() {
		int[] pixels = _sheet.getPixels();
		for (int y = 0; y < _height; y++) {
			for (int x = 0; x < _width; x++) {
				_pixels[x + y * getWidth()] = pixels[(x + _x)
				                                     + ((y + _y) * _sheet.getWidth())];
			}
		}
	}

	public int[] getPixels() {
		return _pixels;
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getSheetWidth() * sheet.getSheetHeight())
				/ (sheet.getWidth() * sheet.getHeight());
		Sprite[] sprites = new Sprite[amount];
		int[] pixels = new int[sheet.getWidth() * sheet.getHeight()];
		int current = 0;

		for (int yp = 0; yp < sheet.getSheetHeight() / sheet.getHeight(); yp++) {
			for (int xp = 0; xp < sheet.getSheetWidth() / sheet.getWidth(); xp++) {
				for (int y = 0; y < sheet.getHeight(); y++) {
					for (int x = 0; x < sheet.getWidth(); x++) {
						int xOffset = x + xp * sheet.getWidth();
						int yOffset = y + yp * sheet.getHeight();
						pixels[x + y * sheet.getWidth()] = sheet.getPixels()[xOffset
						                                                     + yOffset * sheet.getSheetWidth()];
					}
				}
				sprites[current] = new Sprite(pixels, sheet.getWidth(),
						sheet.getHeight());
				current++;
			}
		}

		return sprites;
	}
}
