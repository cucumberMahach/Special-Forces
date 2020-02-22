package view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class Button extends Actor{
	
	private TextureRegion texture;
	private boolean activeInvisible = false;
	
	public Button(TextureRegion texture, float x, float y){
		this.texture = texture;
		setSize(texture.getRegionWidth(), texture.getRegionHeight());
		setOrigin(Align.center);
		setPosition(x, y);
	}

	public void setActiveInvisible(boolean value){
		this.activeInvisible = value;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (!activeInvisible)
			batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
