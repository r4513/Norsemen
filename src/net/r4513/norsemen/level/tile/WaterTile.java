package net.r4513.norsemen.level.tile;

import net.r4513.norsemen.graphics.Sprite;

public class WaterTile extends Tile {
	
	public WaterTile(Sprite sprite) {
		super(sprite);
		_isLiquid = true;
	}
}
