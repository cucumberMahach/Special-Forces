package editor.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import editor.ToolType;
import editor.objects.EditorObjectType;
import engine.Loader;
import engine.Style;
import stages.Editor;
import view.Group;

public class ObjectTools extends Group{
	private Editor editor;
	private EditorObjectType selectedType;
	private int index;
	
	public ObjectTools(Editor editor, Loader loader){
		this.editor = editor;
		addListener(new SelectObjectEvent(this));
		Map<EditorObjectType, ArrayList<TextureRegion>> map = new HashMap<EditorObjectType, ArrayList<TextureRegion>>();
		
		ArrayList<TextureRegion> values;
		
		values = new ArrayList<TextureRegion>();
		values.add(loader.getObject("bed"));
		map.put(EditorObjectType.DECORATION, values);
		
		values = new ArrayList<TextureRegion>();
		values.add(loader.getObject("box"));
		map.put(EditorObjectType.BOX, values);
		
		values = new ArrayList<TextureRegion>();
		values.add(loader.getObject("barrelStand"));
		values.add(loader.getObject("barrelFall"));
		values.add(loader.getObject("boxTnt"));
		map.put(EditorObjectType.BARREL, values);
		
		values = new ArrayList<TextureRegion>();
		values.add(loader.getCharacter("skin1")[2]);
		map.put(EditorObjectType.BOT, values);
		
		values = new ArrayList<TextureRegion>();
		for (int i = 0; i < loader.getWeaponsCount(); i++)
			values.add(loader.getWeaponIcon(i));
		for (int i = 0; i < loader.getItemsCount(); i++)
			values.add(loader.getItemIcon(i));
		map.put(EditorObjectType.ITEM, values);
		
		values = new ArrayList<TextureRegion>();
		values.add(loader.getCharacter("player")[2]);
		map.put(EditorObjectType.PLAYER, values);
		
		values = new ArrayList<TextureRegion>();
		values.add(loader.getIcon("zone"));
		map.put(EditorObjectType.ZONE, values);
		
		ObjectIcon icon;
		int x = 0, y = 0, i = 0;
		EditorObjectType types[] = EditorObjectType.values();
		for (EditorObjectType type : types){
			values = map.get(type);
			for (TextureRegion texture : values) {
				icon = new ObjectIcon(texture, x, y, type, i++);
				icon.setSize(Style.EDITOR_ICON_WIDTH, Style.EDITOR_ICON_HEIGHT);
				addActor(icon);
				x += Style.EDITOR_ICON_WIDTH;
				if (x > Style.EDITOR_ICON_WIDTH * Style.EDITOR_OBJECT_ICONS_LINE){
					x = 0;
					y += Style.EDITOR_ICON_HEIGHT;
				}
			}
		}
		
		
	}
	
	public void select(ObjectIcon icon) {
		try {
		selectedType = icon.getObjectType();
		index = icon.getIndex();
		editor.selectTool(ToolType.ADD_OBJECT);
		}catch(NullPointerException ex){} //fix null
	}
	
	public EditorObjectType getObjectType(){
		return selectedType;
	}
	
	public int getIndex(){
		return index;
	}
}

class SelectObjectEvent extends ClickListener{
	
	private ObjectTools tool;
	
	public SelectObjectEvent(ObjectTools tool){
		this.tool = tool;
	}
	
	@Override
	public void clicked(InputEvent event, float x, float y) {
		tool.select((ObjectIcon) tool.hit(x, y, true));
	}
}