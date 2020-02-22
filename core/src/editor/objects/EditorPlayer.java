package editor.objects;

import engine.Loader;
import engine.Style;

public class EditorPlayer extends EditorObject{
	
	public EditorPlayer(Loader loader, float x, float y){
		super(loader.getCharacter("player")[2], EditorObjectType.PLAYER, x, y, Style.CHARACTER_SIZECOF);
	}
}
