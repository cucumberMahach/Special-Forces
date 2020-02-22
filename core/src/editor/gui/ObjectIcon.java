package editor.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import editor.objects.EditorObjectType;
import view.Image;

public class ObjectIcon extends Image{
	
	private EditorObjectType type;
	private int index;

	public ObjectIcon(TextureRegion texture, float x, float y, EditorObjectType type, int index) {
		super(texture, x, y);
		this.type = type;
		this.index = index;
	}
	
	public EditorObjectType getObjectType(){
		return type;
	}
	
	public int getIndex(){
		return index;
	}

}
