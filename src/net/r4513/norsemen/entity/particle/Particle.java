package net.r4513.norsemen.entity.particle;

import java.util.ArrayList;
import java.util.List;

import net.r4513.norsemen.entity.Entity;
import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.level.tile.Tile;

public class Particle extends Entity {

	protected static List<Particle> _particles = new ArrayList<Particle>();
	protected Sprite _sprite;

	protected int _lifeTime;
	protected int _time = 0;
	protected double _xa, _ya, _za;
	protected double _xx, _yy, _zz;
	protected double _zRate = -0.1;

	public Particle(int x, int y, int lifeTime) {
		_x = x;
		_y = y;
		_sprite = Sprite.particle_normal;
		_lifeTime = lifeTime + (_random.nextInt(20) - 10);
		_xx = _x;
		_yy = _y;
		_xa = _random.nextGaussian();
		_ya = _random.nextGaussian();
		_zz = 2.0;
		_za = 0;
	}

	public Sprite getSprite() {
		return _sprite;
	}

	@Override
	public void update() {
		_time++;
		if (_time >= _lifeTime) {
			remove();
			// _za -= _zRate;
		}

		// Tweek bounce animation here
		if (_zz <= 0) {
			_zz = 0;
			_za *= -0.55;
			_xa *= -0.4;
			_ya *= -0.4;
		}

		move(_xx + _xa, (_yy + _ya) + (_zz + _za));
	}

	protected void move(double x, double y) {
		if (collision(x, y)) {
			_xa *= -0.5;
			_ya *= -0.5;
			_za *= -0.5;
		}
		_xx += _xa;
		_yy += _ya;
		_zz += _za;
	}

	public boolean collision(double x, double y) {
		boolean collition = false;
		double xt, yt;
		for (int c = 0; c < 4; c++) {
			xt = (x - c % 2 * Tile.WIDTH) / Tile.WIDTH;
			yt = (y - c / 2 * Tile.HEIGHT) / Tile.HEIGHT;
			int xi = (int) Math.ceil(xt);
			int yi = (int) Math.ceil(yt);

			if (c % 2 == 0) {
				xi = (int) Math.floor(xt);
			}

			if (c / 2 == 0) {
				yi = (int) Math.floor(yt);
			}

			if (_level.getTile(xi, yi).solid()) {
				collition = true;
			}
		}
		return collition;
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite((int) Math.round(_xx), (int) Math.round(_yy)
				- (int) Math.round(_zz), _sprite, true);
	}

}
