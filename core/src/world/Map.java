package world;

import java.io.IOException;
import java.util.Stack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;

import engine.Loader;
import engine.Style;
import engine.utils.Maths;
import stages.World;
import view.Group;
import world.gameplay.ItemType;
import world.gameplay.WeaponType;
import world.map.Cell;
import world.map.MapBuilder;
import world.map.PathFinder;
import world.map.Tile;
import world.map.TileType;
import world.objects.Item;
import world.objects.MapObject;

public class Map extends Group{
	private World world;
	private MapBuilder builder;
	
	private Tile[] tiles;
	private Cell[] map;
	
	private int width, height;
	private float buildTime;
	private boolean debug;
	
	private PathFinder pathFinder;
	
	public Map(World world, Loader loader, boolean debug){
		this.world = world;
		this.debug = debug;
		builder = new MapBuilder(loader, this);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		buildTime += delta;
		if (buildTime > Style.MAP_REFRESH_FREQUENCY){
			buildTime = 0;
			refreshObjects();
		}
		if (debug)
			debugMap();
	}
	
	public void refreshItems(){
		cleanItems();
		buildItems();
	}
	
	private void cleanItems(){
		for (int i = 0; i < map.length; i++)
			map[i].items.clear();
	}
	
	private void buildItems(){
		Array<Item> items = world.objects().getItems();
		for (int k = 0; k < items.size; k++){
			final Item item = items.get(k);
			final int mapX = Maths.toMapX(item.getX());
			final int mapY = Maths.toMapY(item.getY() + item.getCollideHeight());
			int toX = Maths.toMapX(item.getX() + item.getCollideWidth())+1;
			int toY = Maths.toMapY(item.getY())+1;
			toX += mapX == toX ? 1 : 0;
			toY += mapY == toY ? 1 : 0;
			for (int i = mapY; i < toY; i++){
				for (int j = mapX; j < toX; j++){
					final Cell cell = get(j, i);
					cell.items.add(item);
				}
			}
		}
	}
	
	public void refreshObjects(){
		cleanObjects();
		buildObjects();
	}
	
	private void debugMap(){
		int x, y;
		for (y = 0; y < height; y++){
			for (x = 0; x < width; x++){
				final Cell c = map[x + y * width];
				if (c.type != TileType.NONE)
					world.debugger().drawRect(Maths.fromMapX(x), Maths.fromMapY(y), 16, 16, c.type == TileType.OBJECT ? Color.ORANGE : Color.GRAY, true);
			}
		}
	}
	
	public void load(String txtName){
		try {
			builder.build(txtName, world.getOrtCam());
		} catch (IOException e) {
			e.printStackTrace();
		}
		load();
	}
	
	public void loadFromString(String map){
		builder.buildFromString(map, world.getOrtCam());
		load();
	}
	
	private void load(){
		width = builder.getWidth();
		height = builder.getHeight();
		map = builder.getMap();
		tiles = builder.getTiles();
		pathFinder = new PathFinder(map, builder.getWidth(), builder.getHeight());
	}
	
	public void build(){
		buildObjects(world.spawner(), builder.getObjects());
	}
	
	public void	freeMap(){
		if (tiles == null)
			return;
		int i;
		for (i = 0; i < tiles.length; i++)
			tiles[i].remove();
	}
	
	private void buildObjects(Spawner s, String[] objectsMap){
		String[] str;
		int i;
		for (i = 0; i < objectsMap.length; i++){
			str = objectsMap[i].split(",");
			switch (str[0]) {
			case "decoration":
				s.spawnDecoration(Float.valueOf(str[1]), Float.valueOf(str[2]), str[3], Integer.valueOf(str[4]));
				break;
			case "box":
				s.spawnBox(Float.valueOf(str[1]), Float.valueOf(str[2]));
				break;
			case "barrel":
				s.spawnBarrel(Float.valueOf(str[1]), Float.valueOf(str[2]), Integer.valueOf(str[3]));
				break;
			case "bot":
				s.spawnEnemy(Float.valueOf(str[1]), Float.valueOf(str[2]), Float.valueOf(str[3]), Float.valueOf(str[4]), Integer.valueOf(str[5]), WeaponType.valueOf(str[6]), Integer.valueOf(str[7]));
				break;
			case "item":
				s.spawnItem(Float.valueOf(str[1]), Float.valueOf(str[2]), ItemType.valueOf(str[3]), Integer.valueOf(str[4]));
				break;
			case "player":
				s.spawnPlayer(Float.valueOf(str[1]), Float.valueOf(str[2]));
				break;
			case "nextlevel":
				world.setCompleteZone(Float.valueOf(str[1]), Float.valueOf(str[2]), Float.valueOf(str[3]), Float.valueOf(str[4]));
				break;
			default:
				break;
			}
		}
		s.spawn();
	}
	
	private void buildObjects(){
		Array<MapObject> objects = world.objects().getObjects();
		for (int k = 0; k < objects.size; k++){
			final MapObject obj = objects.get(k);
			final int mapX = Maths.toMapX(obj.getX());
			final int mapY = Maths.toMapY(obj.getY() + obj.getCollideHeight());
			int toX = Maths.toMapX(obj.getX() + obj.getCollideWidth())+1;
			int toY = Maths.toMapY(obj.getY())+1;
			toX += mapX == toX ? 1 : 0;
			toY += mapY == toY ? 1 : 0;
			for (int i = mapY; i < toY; i++){
				for (int j = mapX; j < toX; j++){
					final Cell cell = get(j, i);
					cell.objects.add(obj);
					if (cell.type == TileType.NONE)
						cell.type = TileType.OBJECT;
				}
			}
		}
	}
	
	public void registerObject(MapObject obj){
		final int mapX = Maths.toMapX(obj.getX());
		final int mapY = Maths.toMapY(obj.getY() + obj.getCollideHeight());
		int toX = Maths.toMapX(obj.getX() + obj.getCollideWidth())+1;
		int toY = Maths.toMapY(obj.getY())+1;
		toX += mapX == toX ? 1 : 0;
		toY += mapY == toY ? 1 : 0;
		for (int i = mapY; i < toY; i++){
			for (int j = mapX; j < toX; j++){
				final Cell cell = get(j, i);
				cell.objects.add(obj);
				if (cell.type == TileType.NONE)
					cell.type = TileType.OBJECT;
			}
		}
	}
	
	private void cleanObjects(){
		builder.rebuildTypeMap();
	}
	
	public void findPath(int x1, int y1, int x2, int y2, Stack<Cell> steps){
		pathFinder.find(x1, y1, x2, y2, steps);
	}
	
	public TileType getTileType(int x, int y){
		return x >= 0 && x < width && y >= 0 && y < height ? map[x + y * width].type : TileType.WALL;
	}
	
	public Cell get(int x, int y){
		try{
			return map[x + y * width];
		}catch(ArrayIndexOutOfBoundsException e){
			return new Cell(x, y, TileType.WALL);
		}
	}
	
	public Tile[] getTiles(){
		return tiles;
	}
	
	public int getMapWidth(){
		return width;
	}
	
	public int getMapHeight(){
		return height;
	}
}
