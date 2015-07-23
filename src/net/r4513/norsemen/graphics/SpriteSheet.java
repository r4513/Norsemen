package net.r4513.norsemen.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.r4513.norsemen.entity.mob.Player;

public class SpriteSheet {

	// Level sheets
	public static SpriteSheet normal_level = new SpriteSheet(
			"/textures/sheets/tiles.png", 256);

	// Building sprites
	public static SpriteSheet buildings = new SpriteSheet(
			"/textures/sheets/buildings.png", 256);

	// Projectiles
	// TODO
	// Player sheets
	public static SpriteSheet player = new SpriteSheet(
			"/textures/sprites/player.png", 48, 64);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 3, 1,
			Player.SIZE);
	public static SpriteSheet player_up = new SpriteSheet(player, 0, 1, 3, 1,
			Player.SIZE);
	public static SpriteSheet player_right = new SpriteSheet(player, 0, 2, 3,
			1, Player.SIZE);
	public static SpriteSheet player_left = new SpriteSheet(player, 0, 3, 3, 1,
			Player.SIZE);

	// Animated sheets here

	// NPC sheets
	public static SpriteSheet dummy = new SpriteSheet(
			"/textures/sprites/player.png", 48, 64);
	public static SpriteSheet dummy_down = new SpriteSheet(player, 0, 0, 3, 1,
			16);
	public static SpriteSheet dummy_up = new SpriteSheet(player, 0, 1, 3, 1, 16);
	public static SpriteSheet dummy_right = new SpriteSheet(player, 0, 2, 3, 1,
			16);
	public static SpriteSheet dummy_left = new SpriteSheet(player, 0, 3, 3, 1,
			16);

	private String _path;
	final int SIZE;
	private int[] _pixels;
	public final int WIDTH;
	public final int HEIGHT;
	private int _width, _height;
	private Sprite[] _sprites;

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height,
			int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		_pixels = new int[w * h];
		WIDTH = w;
		HEIGHT = h;
		_width = w;
		_height = h;
		if (w == h) {
			SIZE = w;
		} else {
			SIZE = -1;
		}

		int yp, xp;
		int[] pixels = sheet.getPixels();

		for (int y0 = 0; y0 < h; y0++) {
			yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				xp = xx + x0;
				_pixels[x0 + y0 * w] = pixels[xp + yp * sheet.WIDTH];
			}
		}

		int frame = 0;
		_sprites = new Sprite[width * height];

		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] pixelsSprite = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						pixelsSprite[x0 + y0 * spriteSize] = _pixels[(x0 + xa
								* spriteSize)
								+ (y0 + ya * spriteSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(pixelsSprite, spriteSize, spriteSize);
				_sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int width, int height) {
		if (width == height) {
			SIZE = width;
		} else {
			SIZE = -1;
		}
		WIDTH = width;
		HEIGHT = height;
		_path = path;
		load();
	}

	public SpriteSheet(String path, int size) {
		SIZE = size;
		WIDTH = SIZE;
		HEIGHT = SIZE;
		_path = path;
		load();
	}

	public int getSheetWidth() {
		return _width;
	}

	public int getSheetHeight() {
		return _height;
	}

	public Sprite[] getSprites() {
		return _sprites;
	}

	public int getSize() {
		return SIZE;
	}

	private void load() {
		try {
			System.out.print("Trying to load SpriteSheet: " + _path + ": ");
			BufferedImage image = ImageIO.read(SpriteSheet.class
					.getResource(_path));
			System.out.println(" Success!");
			_width = image.getWidth();
			_height = image.getHeight();
			_pixels = new int[_width * _height];
			image.getRGB(0, 0, _width, _height, _pixels, 0, _width);
		} catch (IOException e) {
		} catch (Exception e) {
			System.err.println("Failed...");
			e.printStackTrace();
		}
	}

	public int[] getPixels() {
		return _pixels;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
}
