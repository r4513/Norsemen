package net.r4513.norsemen.graphics;

import net.r4513.norsemen.entity.mob.NPC;
import net.r4513.norsemen.entity.projectile.Projectile;
import net.r4513.norsemen.level.tile.Tile;

public class Screen {

	private int _xOffset, _yOffset;
	private int _width, _height;
	private int[] _pixels;

	/**
	 * Constructor for the Screen class
	 *
	 * @param width
	 *            of the screen
	 * @param height
	 *            of the screen
	 */
	public Screen(int width, int height) {
		_width = width;
		_height = height;
		_pixels = new int[_width * _height];
	}

	/**
	 * Renders the sprites
	 *
	 * @param xp
	 *            X position of the Sprite
	 * @param yp
	 *            Y position of the Sprite
	 * @param sprite
	 *            The Sprite
	 * @param fixed
	 *            If the Sprite is fixed to the Screen
	 */
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= _xOffset;
			yp -= _yOffset;
		}

		int xa, ya;
		int[] pixels = sprite.getPixels();
		int color;

		for (int y = 0; y < sprite.getHeight(); y++) {
			ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				xa = x + xp;
				if (xa < 0 || xa >= _width || ya < 0 || ya >= _height) {
					continue;
				}
				color = pixels[x + y * sprite.getWidth()];
				if (color != 0xffff00ff) {
					_pixels[xa + ya * _width] = color;
				}
			}
		}
	}

	public void renderFont(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= _xOffset;
			yp -= _yOffset;
		}

		int xa, ya;
		int[] pixels = sprite.getPixels();
		int color;

		for (int y = 0; y < sprite.getHeight(); y++) {
			ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				xa = x + xp;
				if (xa < 0 || xa >= _width || ya < 0 || ya >= _height) {
					continue;
				}
				color = pixels[x + y * sprite.getWidth()];
				if (color != 0xffff00ff && color != 0xff7f007f) {
					_pixels[xa + ya * _width] = color;
				}
			}
		}
	}

	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= _xOffset;
			yp -= _yOffset;
		}

		int xa, ya;
		int[] pixels = sheet.getPixels();

		for (int y = 0; y < sheet.getHeight(); y++) {
			ya = y + yp;
			for (int x = 0; x < sheet.getWidth(); x++) {
				xa = x + xp;
				if (xa < 0 || xa >= _width || ya < 0 || ya >= _height) {
					continue;
				}
				_pixels[xa + ya * _width] = pixels[x + y * sheet.getWidth()];
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= _xOffset;
		yp -= _yOffset;
		for (int y = 0; y < tile.getSprite().getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.getSprite().getWidth(); x++) {
				int xa = x + xp;
				if (xa < -tile.getSprite().getWidth() || xa >= _width || ya < 0
						|| ya >= _height) {
					break;
				}
				if (xa < 0) {
					xa = 0;
				}
				_pixels[xa + ya * _width] = tile.getSprite().getPixels()[x + y
				                                                         * tile.getSprite().getWidth()];
			}
		}
	}

	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= _xOffset;
		yp -= _yOffset;
		for (int y = 0; y < p.getSprite().getHeight(); y++) {
			int yAbs = y + yp;
			for (int x = 0; x < p.getSprite().getWidth(); x++) {
				int xAbs = x + xp;
				if (xAbs < -p.getSprite().getWidth() || xAbs >= _width
						|| yAbs < 0 || yAbs >= _height) {
					break;
				}
				if (xAbs < 0) {
					xAbs = 0;
				}
				int color = p.getSprite().getPixels()[x + y
				                                      * p.getSprite().getWidth()];
				if (color != 0xffff00ff) {
					_pixels[xAbs + yAbs * _width] = color;
				}
			}
		}
	}

	public void renderNPC(int xp, int yp, Sprite sprite, int flip) {
		xp -= _xOffset;
		yp -= _yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yAbs = y + yp;
			int ys = y;
			if (flip == 1) {
				ys = (sprite.getHeight() - 1) - y;
			}
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xAbs = x + xp;
				int xs = x;
				if (flip == 2) {
					xs = (sprite.getWidth() - 1) - x;
				}
				if (xAbs < -sprite.getWidth() || xAbs >= _width || yAbs < 0
						|| yAbs >= _height) {
					break;
				}
				if (xAbs < 0) {
					xAbs = 0;
				}
				int color = sprite.getPixels()[xs + ys * sprite.getWidth()];
				if (color != 0xFFFF00FF) {
					_pixels[xAbs + yAbs * _width] = color;
				}
			}
		}
	}

	public void renderNPC(int xp, int yp, NPC npc) {
		xp -= _xOffset;
		yp -= _yOffset;
		for (int y = 0; y < npc.getSize(); y++) {
			int yAbs = y + yp;
			int ys = y;
			for (int x = 0; x < npc.getSize(); x++) {
				int xAbs = x + xp;
				int xs = x;
				if (xAbs < -npc.getSize() || xAbs >= _width || yAbs < 0
						|| yAbs >= _height) {
					break;
				}
				if (xAbs < 0) {
					xAbs = 0;
				}
				int color = npc.getSprite().getPixels()[xs + ys
				                                        * npc.getSprite().getWidth()];

				if (color != 0xFFFF00FF) {
					_pixels[xAbs + yAbs * _width] = color;
				}
			}
		}
	}

	public void drawRect(int xp, int yp, int width, int height, int color,
			boolean fixed) {
		if (fixed) {
			xp -= _xOffset;
			yp -= _yOffset;
		}

		for (int x = xp; x <= width; x++) {
			if (yp >= _height || x < 0 || x >= _width) {
				continue;
			}
			if (yp > 0) {
				_pixels[x + yp * _width] = color;
			}
			if (yp + height >= _height) {
				continue;
			}
			if (yp + height > 0) {
				_pixels[x + (yp + height) * _width] = color;
			}
		}

		for (int y = yp; y < height; y++) {
			if (xp >= _width || y < 0 || y >= _height) {
				continue;
			}
			if (xp > 0) {
				_pixels[xp + y * _width] = color;
			}
			if (xp + width >= _width) {
				continue;
			}
			if (xp + width > 0) {
				_pixels[(xp + width) + y * _width] = color;
			}
		}
	}

	public void setOffsets(int xOffset, int yOffset) {
		_xOffset = xOffset;
		_yOffset = yOffset;
	}

	public void clear() {
		for (int i = 0; i < _pixels.length; i++) {
			_pixels[i] = 0;
		}
	}

	/**
	 * Returns the pixel at location i
	 *
	 * @param i
	 *            the location of the pixel to return
	 * @return the pixel at the location i
	 */
	public int getPixelAt(int i) {
		return _pixels[i];
	}

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}
}
