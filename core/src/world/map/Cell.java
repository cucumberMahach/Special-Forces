package world.map;

import com.badlogic.gdx.utils.Array;

import world.objects.Item;
import world.objects.MapObject;

public class Cell{
	
	public TileType type;
	public char tile;
	public int x, y, F, G, H;
	public Array<MapObject> objects;
	public Array<Item> items;
	public boolean start, finish, road, closed, open;
	public Cell parent;
	
	public Cell(int x, int y, char tile){
		set(x, y, tile);
		objects = new Array<MapObject>();
		items = new Array<Item>();
	}
	
	public Cell(int x, int y, TileType type){
		this.x = x;
		this.y = y;
		this.type = type;
		objects = new Array<MapObject>();
		items = new Array<Item>();
	}
	
	public void set(int x, int y, char tile){
		this.x = x;
		this.y = y;
		this.tile = tile;
	}
	
	public char getChar(){
		if (start)
			return 'a';
		if (finish)
			return 'b';
		if (type == TileType.WALL)
			return '#';
		if (road)
			return '*';
		return '.';
	}
	
	public void calc(Cell finish){
		H = mandist(finish);
		G = price(finish);
		F = H + G;
	}
	
	public int price(Cell cell){
		if (x == cell.x || y == cell.y)
			return 10;
		else
			return 14;
	}
	
	public int mandist(Cell cell){
		int x = this.x - cell.x;
		int y = this.y - cell.y;
		return 10*(Math.abs(x) + Math.abs(y));
	}
	
	public void clear(){
		start = false;
		finish = false;
		road = false;
		parent = null;
		closed = false;
		open = false;
	}
}
