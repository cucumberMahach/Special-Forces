package editor;

import java.io.IOException;

import com.badlogic.gdx.scenes.scene2d.Group;

import engine.Loader;
import stages.Editor;
import world.gameplay.ItemType;
import world.gameplay.WeaponType;
import world.map.Cell;
import world.map.MapBuilder;
import world.map.Tile;

public class Map extends Group{
	private Loader loader;
	private Editor editor;
	private MapBuilder builder;
	
	private Tile[] tiles;
	private Cell[] map;
	
	private int width, height;
	
	public Map(Editor editor, Loader loader){
		this.editor = editor;
		this.loader = loader;
		builder = new MapBuilder(loader, this);
	}
	
	public void load(String txtName) throws IOException{
		builder.build(txtName, editor.getOrtCam());
		load();
	}
	
	public void loadFromString(String map){
		builder.buildFromString(map, editor.getOrtCam());
		load();
	}
	
	private void load(){
		width = builder.getWidth();
		height = builder.getHeight();
		map = builder.getMap();
		tiles = builder.getTiles();
	}
	
	public void setTile(int x, int y, int index){
		Cell cell = get(x, y);
		if (cell == null)
			return;
		cell.tile = (char) index;
		tiles[x + y * width].setTexture(loader.getMapTile(index));
	}
	
	public void generate(StringBuilder sb){
		int x, y;
		for (y = 0; y < height; y++){
			for (x = 0; x < width; x++)
				sb.append(MapBuilder.convertToFile(map[x + y * width].tile));
			sb.append("\n");
		}
	}
	
	public void build(){
		buildObjects(editor.objects(), builder.getObjects());
	}
	
	private void buildObjects(Objects objects, String[] objectsMap){
		String[] str;
		int i;
		for (i = 0; i < objectsMap.length; i++){
			str = objectsMap[i].split(",");
			switch (str[0]) {
			case "decoration":
				objects.addDecoration(Float.valueOf(str[1]), Float.valueOf(str[2]), str[3], Integer.valueOf(str[4]));
				break;
			case "box":
				objects.addBox(Float.valueOf(str[1]), Float.valueOf(str[2]));
				break;
			case "barrel":
				objects.addBarrel(Float.valueOf(str[1]), Float.valueOf(str[2]), Integer.valueOf(str[3]));
				break;
			case "bot":
				objects.addBot(Float.valueOf(str[1]), Float.valueOf(str[2]), Float.valueOf(str[3]), Float.valueOf(str[4]), Integer.valueOf(str[5]), WeaponType.valueOf(str[6]), Integer.valueOf(str[7]));
				break;
			case "item":
				objects.addItem(Float.valueOf(str[1]), Float.valueOf(str[2]), ItemType.valueOf(str[3]), Integer.valueOf(str[4]));
				break;
			case "player":
				objects.addPlayer(Float.valueOf(str[1]), Float.valueOf(str[2]));
				break;
			case "nextlevel":
				objects.addZone(Float.valueOf(str[1]), Float.valueOf(str[2]), Float.valueOf(str[3]), Float.valueOf(str[4]));
				break;
			default:
				break;
			}
		}
	}
	
	public void	freeMap(){
		if (tiles == null)
			return;
		int i;
		for (i = 0; i < tiles.length; i++)
			tiles[i].remove();
	}
	
	public Cell get(int x, int y){
		return x >= 0 && x < width && y >= 0 && y < height ? map[x + y * width] : null;
	}
	
	public int getMapWidth(){
		return width;
	}
	
	public int getMapHeight(){
		return height;
	}
}
