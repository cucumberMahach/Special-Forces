package view.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import view.Group;
import view.Image;
import view.Label;

public class Button extends Group implements Pressable{
	private TextureRegion texture;
	private boolean hover, down;
	private Label label;
	private Image icon;
	
	public Button(Loader loader, ButtonType type, String caption, ButtonIcon icon){
		texture = loader.getGui(type.getName());
		setSize(texture.getRegionWidth(), texture.getRegionHeight());
		setOrigin(Align.center);
		
		if (type.isFonted()){
			label = new Label(loader, caption, type.getFont(), type.getFontAlign(), 0, 0, 0, 0);
			label.setSize(getWidth(), getHeight());
			addActor(label);
		}
		
		if (icon != null){
			this.icon = new Image(loader.getIcon(icon.getName()));
			
			if (type.isNextToIcon()){
				this.icon.setPosition(15, getOriginY() - this.icon.getOriginY());
				label.setX(this.icon.getRight() + 15);
			}else{
				this.icon.setPosition(getWidth()/2 - this.icon.getOriginX(), getOriginY() - this.icon.getOriginY());
			}
			
			addActor(this.icon);
			if (type.isSmall(type) && this.icon.getWidth() > getWidth() || this.icon.getHeight() > getHeight())
				this.icon.setScale(0.6f);
			if (type == ButtonType.MANAGER){
				this.icon.moveBy(0, 22);
				label.moveBy(0, -36);
			}
		}
		
		addListener(new PressController(this));
	}
	
	public Button(Loader loader, ButtonType type, TextureRegion iconTex){
		this(loader, type, "", null);
		icon = new Image(null, 1.5f);
		setIcon(iconTex);
		addActor(icon);
	}
	
	public void setIconScale(float scaleXY){
		icon.setNaturalScale(scaleXY);
		icon.setPosition(getOriginX(), getOriginY(), Align.center);
	}
	
	public void setIcon(TextureRegion iconTex){
		icon.setTexture(iconTex);
		icon.setPosition(getOriginX(), getOriginY(), Align.center);
	}
	
	public void setCaption(String caption){
		label.setCaption(caption);
	}
	
	public void setFocus(boolean value){
		hover = value;
	}
	
	public void setDown(boolean value){
		if (!value)
			SpecialForces.getInstance().sounds().click();
		down = value;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		batch.setColor(getColor());
		if (hover)
			batch.setColor(getColor().r/1.2f, getColor().g/1.2f, getColor().b/1.2f, 1f);
		if (down)
			batch.setColor(getColor().r/1.8f, getColor().g/1.8f, getColor().b/1.8f, 1f);
		batch.draw(texture, getX(), getY(), getWidth(), getHeight());
		batch.setColor(1, 1, 1, 1);
		super.draw(batch, parentAlpha);
	}
	
}
