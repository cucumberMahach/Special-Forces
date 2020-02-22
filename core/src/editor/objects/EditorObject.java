package editor.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import stages.Editor;
import view.Group;

public class EditorObject extends Group{
	private TextureRegion texture;
	private float sizeCof;
	
	private EditorObjectType type;
	private int index;
	private boolean selected;
	
	public EditorObject(TextureRegion texture, EditorObjectType type, float x, float y, float sizeCof){
		this.texture = texture;
		this.type = type;
		this.sizeCof = sizeCof;
		if (texture != null)
			setSize(texture.getRegionWidth() * sizeCof, texture.getRegionHeight() * sizeCof);
		setPosition(x, y);
		setOrigin(Align.center);
	}
	
	public EditorObject(TextureRegion texture, EditorObjectType type, float x, float y) {
		this(texture, type, x, y, 1);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (selected)
			getEditor().debugger().drawRect(getX(), getY(), getWidth(), getHeight(), Color.YELLOW, false);
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		super.draw(batch, parentAlpha);
	}
	
	public void setTexture(TextureRegion texture){
		this.texture = texture;
		setSize(texture.getRegionWidth() * sizeCof, texture.getRegionHeight() * sizeCof);
		setOrigin(Align.center);
	}
	
	public EditorObjectType getObjectType(){
		return type;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public boolean isSelected(){
		return selected;
	}
	
	public void select(){
		selected = true;
	}
	
	public void unselect(){
		selected = false;
	}
	
	public int getIndex(){
		return index;
	}
	
	public Editor getEditor(){
		return ((Editor) getStage());
	}
}
