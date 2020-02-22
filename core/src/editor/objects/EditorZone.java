package editor.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

public class EditorZone extends EditorObject{
	
	public EditorZone(float x, float y, float width, float height){
		super(null, EditorObjectType.ZONE, x, y);
		setPosition(x, y);
		setSize(width, height);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		getEditor().debugger().drawRect(getX(), getY(), getWidth(), getHeight(), Color.BLUE, false);
	}
}
