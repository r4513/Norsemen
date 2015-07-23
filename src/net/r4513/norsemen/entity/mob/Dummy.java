package net.r4513.norsemen.entity.mob;

import net.r4513.norsemen.graphics.AnimatedSprite;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.graphics.SpriteSheet;
import net.r4513.norsemen.level.tile.Tile;

public class Dummy extends NPC {

	private AnimatedSprite _down = new AnimatedSprite(SpriteSheet.dummy_down,
			16, 16, 3);
	private AnimatedSprite _up = new AnimatedSprite(SpriteSheet.dummy_up, 16,
			16, 3);
	private AnimatedSprite _right = new AnimatedSprite(SpriteSheet.dummy_right,
			16, 16, 3);
	private AnimatedSprite _left = new AnimatedSprite(SpriteSheet.dummy_left,
			16, 16, 3);

	private AnimatedSprite _currentAnimatedSprite = _right;

	private int _time = 0;
	private int ya = 0;
	private int xa = 0;

	public Dummy(int x, int y) {
		_x = x * Tile.WIDTH;
		_y = y * Tile.HEIGHT;
		_sprite = Sprite.dummy;
	}

	@Override
	public void update() {
		_time++;
		if (_time % (_random.nextInt(50) + 30) == 0) {
			xa = _random.nextInt(3) - 1;
			ya = _random.nextInt(3) - 1;
			if (_random.nextInt(5) <= 1) {
				ya = 0;
				xa = 0;
			}
		}

		if (_moving) {
			_currentAnimatedSprite.update();
		} else {
			_currentAnimatedSprite.setFrame(0);
		}

		if (ya < 0) {
			_currentAnimatedSprite = _up;
		} else if (ya > 0) {
			_currentAnimatedSprite = _down;
		}
		if (xa < 0) {
			_currentAnimatedSprite = _left;
		} else if (xa > 0) {
			_currentAnimatedSprite = _right;
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			_moving = true;
		} else {
			_moving = false;
		}

		_sprite = _currentAnimatedSprite.getSprite();
	}
}
