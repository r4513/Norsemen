package net.r4513.norsemen.level.building;

import net.r4513.norsemen.graphics.Screen;
import net.r4513.norsemen.graphics.Sprite;
import net.r4513.norsemen.level.tile.Tile;

public class TippiBuilding extends Building {

	private Sprite _sprite;

	public TippiBuilding(int x, int y, Sprite tippi) {
		_sprite = tippi;
		_x = x;
		_y = y;
	}

	@Override
	public void render(Screen screen) {
		screen.renderSprite((int) Math.round(getX()) * Tile.WIDTH,
				(int) Math.round(getY()) * Tile.HEIGHT, _sprite, true);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
}
