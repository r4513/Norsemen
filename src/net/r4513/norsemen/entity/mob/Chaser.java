package net.r4513.norsemen.entity.mob;

import java.util.List;

import net.r4513.norsemen.graphics.AnimatedSprite;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.graphics.SpriteSheet;
import net.r4513.norsemen.level.tile.Tile;

public class Chaser extends NPC {

	private AnimatedSprite _down = new AnimatedSprite(SpriteSheet.dummy_down,
			16, 16, 3);
	private AnimatedSprite _up = new AnimatedSprite(SpriteSheet.dummy_up, 16,
			16, 3);
	private AnimatedSprite _right = new AnimatedSprite(SpriteSheet.dummy_right,
			16, 16, 3);
	private AnimatedSprite _left = new AnimatedSprite(SpriteSheet.dummy_left,
			16, 16, 3);

	private AnimatedSprite _currentAnimatedSprite = _right;

	private int xa = 1;
	private int ya = 0;

	public Chaser(int x, int y) {
		_x = x * Tile.WIDTH;
		_y = y * Tile.HEIGHT;
		_sprite = Sprite.dummy;
	}

	private void move() {
		xa = 0;
		ya = 0;

		// Implement working for multiplayer
		Player player = _level.getClientPlayer();
		List<Player> players = _level.getPlayers(this, 100);

		if (players.size() > 0) {
			double px = player.getX();
			double py = player.getY();
			if (_x < px) {
				xa++;
			}
			if (_x > px) {
				xa--;
			}
			if (_y < py) {
				ya++;
			}
			if (_y > py) {
				ya--;
			}
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			_moving = true;
		} else {
			_moving = false;
		}
	}

	@Override
	public void update() {
		move();

		if (_moving) {
			_currentAnimatedSprite.update();
		} else {
			_currentAnimatedSprite.setFrame(0);
		}

		updateAnimatedSprite();

		_sprite = _currentAnimatedSprite.getSprite();
	}

	private void updateAnimatedSprite() {
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
	}
}
