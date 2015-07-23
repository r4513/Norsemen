package net.r4513.norsemen.graphics;

import net.r4513.norsemen.entity.mob.Player;

public class AnimatedSprite extends Sprite {

	private int _frame = 0;
	private Sprite _sprite;
	private int _rate;
	private int _length = -1;
	private int _time = 0;
	
		public AnimatedSprite(SpriteSheet sheet, int width, int height, int length){
			super(sheet, width, height);
			_length = length;
			_rate = 3 * _length;
			_sprite = sheet.getSprites()[0];
			if(length > sheet.getSprites().length) System.err.println("Error: Length of animation is too long");
		}
		
		public void update(){
			_time++;
			if(_time % _rate == 0){
			if(_frame >= _length - 1 ) _frame = 0;
			else _frame++;
			_sprite = _sheet.getSprites()[_frame];
			
			}
		}
		
		public Sprite getSprite(){
			return _sprite;
		}
		
		public void frameRate(int rate){
			_rate = rate;
		}

		public void setFrame(int i) {
			if(i > _sheet.getSprites().length - 1){
				System.err.println("Index out of bounds: " + this);
				return;
			}else{
				_sprite = _sheet.getSprites()[i];
			}
		}
}
