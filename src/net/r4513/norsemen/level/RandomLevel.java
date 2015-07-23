package net.r4513.norsemen.level;

import java.util.Random;


public class RandomLevel extends Level {

	private static final Random _random = new Random();
	
	public RandomLevel(int width, int height) {
		super(width, height);
	}

	protected void generateLevel() {
		for(int y = 0; y < _height; y++){
			for(int x = 0; x < _width; x++){
				int randomNumber = _random.nextInt(4);
				if(randomNumber == 0){
					_tiles[x + y * _width] = randomNumber;
				}else if(randomNumber == 1){
					_tiles[x + y * _width] = randomNumber;
				}else if(randomNumber == 2){
					_tiles[x + y * _width] = randomNumber;
				}else if(randomNumber == 3){
					_tiles[x + y * _width] = randomNumber;
				}
			}
		}
	}

	protected void loadLevel(String path) {
		
	}
}
