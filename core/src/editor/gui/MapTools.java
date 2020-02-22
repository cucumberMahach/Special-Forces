package editor.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import editor.ToolType;
import engine.Loader;
import engine.Style;
import stages.Editor;
import view.Group;

public class MapTools extends Group{
	private Editor editor;
	private int index;
	
	public MapTools(Editor editor, Loader loader){
		this.editor = editor;
		TextureRegion[] mapTiles = loader.getMapTiles();
		
		addListener(new SelectEvent(this));
		
		int i, x = 0, y = 0;
		TileIcon icon;
		for (i = 0; i < mapTiles.length; i++){
			icon = new TileIcon(mapTiles[i], x, y, i);
			icon.setSize(Style.EDITOR_ICON_WIDTH, Style.EDITOR_ICON_HEIGHT);
			y += Style.EDITOR_ICON_HEIGHT;
			if (y >= Style.EDITOR_ICON_HEIGHT * Style.EDITOR_TILES_ICONS_LINE){
				y = 0;
				x += Style.EDITOR_ICON_WIDTH;
			}
			addActor(icon);
		}
	}
	
	public void select(TileIcon icon) {
		try {
			index = icon.getIndex();
			editor.selectTool(ToolType.PAINT);
		}catch (Exception ex){ System.out.println(ex.getMessage());}
	}
	
	public int getIndex(){
		return index;
	}
}

class SelectEvent extends ClickListener{
	
	private MapTools tool;
	
	public SelectEvent(MapTools tool){
		this.tool = tool;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		tool.select((TileIcon) tool.hit(x, y, true));
	}
}