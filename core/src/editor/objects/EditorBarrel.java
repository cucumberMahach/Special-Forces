package editor.objects;

import engine.Loader;
import world.objects.Barrel;

public class EditorBarrel extends EditorObject{
	private int type;
	
	public EditorBarrel(Loader loader, float x, float y, int type){
		super(Barrel.getTexture(loader, type), EditorObjectType.BARREL, x, y);
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
}
