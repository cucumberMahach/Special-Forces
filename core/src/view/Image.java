package view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

public class Image extends Actor{
	
	private TextureRegion texture;
	private boolean centered;
	private float cenX, cenY, scaleXY = 1;
	
	public Image(TextureRegion texture, float x, float y, boolean centered){
		this.centered = centered;
		if (centered){
			cenX = x;
			cenY = y;
		}
		setTexture(texture);
		if (!centered)
			setPosition(x, y);
	}

	public Image(TextureRegion texture){
		this(texture, 0, 0);
	}
	
	public Image(TextureRegion texture, float x, float y){
		this(texture, x, y, false);
	}
	
	public Image(TextureRegion texture, float naturalScaleXY){
		this(texture, 0, 0);
		setNaturalScale(naturalScaleXY);
	}
	
	public void setNaturalScale(float scaleXY){
		setSize(getWidth() * scaleXY, getHeight() * scaleXY);
		setOrigin(Align.center);
		this.scaleXY = scaleXY;
	}
	
	public void setTexture(TextureRegion texture){
		this.texture = texture;
		if (texture == null)
			return;
		setSize(texture.getRegionWidth() * scaleXY, texture.getRegionHeight() * scaleXY);
		setOrigin(Align.center);
		if (centered)
			setPosition(cenX - getOriginX(), cenY - getOriginY());
	}
	
	public TextureRegion getTexture(){
		return texture;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (texture == null)
			return;
		batch.setColor(getColor().r, getColor().g , getColor().b, parentAlpha * getColor().a);
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.setColor(1, 1, 1, 1);
	}
	
	public float getCenterX(){
		return getX() + getOriginX();
	}
	
	public float getCenterY(){
		return getY() + getOriginY();
	}
}
