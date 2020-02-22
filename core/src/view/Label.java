package view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import engine.Loader;

public class Label extends Actor{
	private BitmapFont font;
	private String caption;
	private float fontOffY;
	private int haling;
	private boolean wrap;
	
	public Label(Loader loader, String caption, Font fontType, int haling, float x, float y, float width, float height){
		this.haling = haling;
		font = loader.getFont(fontType);
		setPosition(x, y);
		setCaption(caption);
		setSize(width, height == 0 ? font.getCapHeight() : height);
		setTouchable(Touchable.disabled);
	}
	
	public void setWrap(boolean wrap){
		this.wrap = wrap;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		font.setColor(getColor());
		font.draw(batch, caption, getX(), getY() + fontOffY, getWidth(), haling, wrap);
	}
	
	public void setCaption(String value){
		if (value == null)
			value = "";
		caption = value;
	}

	@Override
	protected void sizeChanged() {
		fontOffY = getHeight() / 2 + font.getCapHeight() / 2;
	}
}
