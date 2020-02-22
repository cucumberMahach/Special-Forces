package editor.gui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import view.Image;

public class TileIcon extends Image{
	
	private int index;

	public TileIcon(TextureRegion texture, float x, float y, int index) {
		super(texture, x, y);
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}

}
