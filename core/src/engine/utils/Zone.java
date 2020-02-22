package engine.utils;

import world.Physics;
import world.objects.MapObject;

public class Zone {
	
	public float x, y, width, height;
	
	public Zone(float x, float y, float width, float height){
		set(x, y, width, height);
	}
	
	public Zone(){ }

	public void hide(){
		set(-100,-100,1,1);
	}
	
	public void set(float x, float y, float width, float height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public boolean contains(MapObject obj){
		return Physics.isRectOverlaps(x, y, width, height, obj.getX(), obj.getY(), obj.getWidth(), obj.getHeight());
	}
}
