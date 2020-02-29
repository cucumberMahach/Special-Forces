package world.map;

import java.io.IOException;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Group;

import engine.Loader;
import engine.Style;

public class MapBuilder {
	private Loader loader;
	private Group group;
	private MapReader reader;
	private int width, height;
	private Tile[] tiles;
	private Cell[] map;
	private String[] objects;
	
	public MapBuilder(Loader loader, Group group){
		this.loader = loader;
		this.group = group;
		reader = new MapReader();
	}
	
	public void build(String txtName, OrthographicCamera camera) throws IOException{
		reader.loadMap(Style.MAPS_DIRECTORY + txtName + ".txt");
		build(camera);
	}
	
	public void buildFromString(String map, OrthographicCamera camera){
		reader.loadMapFromString(map);
		build(camera);
	}
	
	private void build(OrthographicCamera camera){
		map = reader.getMap();
		objects = reader.getObjects();
		width = reader.getWidth();
		height = reader.getHeight();
		convertMap(map);
		tiles = buildMap(map, camera);
		buildTypeMap(map);
	}
	
	public void rebuildTypeMap(){
		buildTypeMap(map);
	}
	
	private void convertMap(Cell[] map){
		int x, y;
		for (y = 0; y < height; y++){
			for (x = 0; x < width; x++){
				map[x + y * width].tile = convertToMap(map[x + y * width].tile);
			}
		}
	}
	
	private void buildTypeMap(Cell[] map){
		int x, y;
		for (y = 0; y < height; y++){
			for (x = 0; x < width; x++){
				map[x + y * width].type = isWallTile(map[x + y * width].tile) ? TileType.WALL : TileType.NONE;
				map[x + y * width].objects.clear();
			}
		}
	}
	
	private Tile[] buildMap(Cell[] map, OrthographicCamera camera){
		Tile[] tiles = new Tile[map.length];
		Tile tile;
		int x, y;
		for (y = 0; y < height; y++){
			for (x = 0; x < width; x++){
				tile = new Tile(loader, map[x + y * width].tile, x, height - y - 1, camera);
				group.addActor(tile);
				tiles[x + y * width] = tile;
			}
		}
		return tiles;
	}
	
	public static char convertToMap(char tile){
		return (char) (tile - 48);
	}
	
	public static char convertToFile(char tile){
		return (char) (tile + 48);
	}
	
	private boolean isWallTile(char tile){
		return tile == 4 || tile == 8 || tile == 10 || tile == 15 || tile == 16 || tile == 17 || tile == 19;
	}
	
	public Tile[] getTiles(){
		return tiles;
	}
	
	public Cell[] getMap(){
		return map;
	}
	
	public String[] getObjects(){
		return objects;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
