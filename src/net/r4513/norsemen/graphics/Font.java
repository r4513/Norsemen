package net.r4513.norsemen.graphics;

public class Font {

	private static SpriteSheet _font = new SpriteSheet("/fonts/alpha_runes.png", 16);
	private static Sprite[] _chars = Sprite.split(_font);
	
	private static String _charIndex = "ABCDEFGHIJKLM" + //
										"NOPQRSTUVWXYZ" + //
										"£{}1234567890" + //
										",.@%/'½=#$&()" + //
										":;^<>_|*?+-\"";
	private static int SIZE = 16;
	
	public Font(){
		
	}
	
	public void render(String str, Screen screen){
		str = str.toUpperCase();
		int x = 50;
		int y = 50;
		int index;
		char current;
		for(int i = 0; i < str.length(); i++){
			current = str.charAt(i);
			index = _charIndex.indexOf(current);
			if(index < 0) continue;
			//if(index >= 3) index--;
			screen.renderFont(x + i * Font.SIZE , y, _chars[index], false);
		}
	}
}
