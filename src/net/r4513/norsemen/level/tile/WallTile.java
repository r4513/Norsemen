package net.r4513.norsemen.level.tile;

import net.r4513.norsemen.graphics.Sprite;

public class WallTile extends Tile {

	public WallTile(Sprite sprite) {
		super(sprite);
		_isSolid = true;
	}

}
