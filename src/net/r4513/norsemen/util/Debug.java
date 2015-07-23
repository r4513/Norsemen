package net.r4513.norsemen.util;

import net.r4513.norsemen.graphics.Screen;

public class Debug {

	private Debug(){
		
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, int color, boolean fixed){
		screen.drawRect(x,y, width, height, color, fixed);
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed){
		screen.drawRect(x,y, width, height, 0xFFFF0000, fixed);
	}
}
