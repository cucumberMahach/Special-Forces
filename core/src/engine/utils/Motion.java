package engine.utils;

import com.badlogic.gdx.utils.Array;

import world.objects.Item;
import world.objects.MapObject;

public class Motion {
	public boolean x, y;
	public MapObject[] barriers;
	public Array<Item> items;
	
	public Motion(boolean x, boolean y){
		barriers = new MapObject[2];
		items = new Array<Item>();
		set(x, y);
	}
	
	public Motion(){
		this(false, false);
	}
	
	public void set(boolean x, boolean y){
		this.x = x;
		this.y = y;
	}
	
	public boolean isAnyBarrierIs(String className){
		return (barriers[0] == null ? false : barriers[0].getClass().getSimpleName().equals(className)) || (barriers[1] == null ? false : barriers[1].getClass().getSimpleName().equals(className));
	}
	
	public boolean isWall(){
		return isCollided() && barriers[0] == null && barriers[1] == null;
	}
	
	public boolean isFullCollided(){
		return !x && !y;
	}
	
	public boolean isCollided(){
		return !x || !y;
	}
	
	public void clear(){
		barriers[0] = null;
		barriers[1] = null;
		items.clear();
	}
}
