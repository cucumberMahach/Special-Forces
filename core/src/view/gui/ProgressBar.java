package view.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;

import engine.Loader;

public class ProgressBar extends Group{
	private TextureRegion barTex, backTex;
	private float progress, offx, offy, barWidth;
	private int origWidth;
	
	public ProgressBar(Loader loader, ProgressBarStyle style){
		barTex = loader.getGui(style.getName());
		backTex = loader.getGui(style.getName() + "_back");
		barTex = new TextureRegion(barTex);
		origWidth = barTex.getRegionWidth();
		setOffset(style.getOffx(), style.getOffy());
		setSize(backTex.getRegionWidth(), backTex.getRegionHeight());
		setOrigin(Align.center);
		setProgress(1f);
	}
	
	private void setOffset(float x, float y){
		offx = x;
		offy = y;
	}
	
	public void setProgress(float value){
		progress = value;
		barWidth = origWidth * value;
		barTex.setRegion(barTex, 0, 0, (int) barWidth, barTex.getRegionHeight());
	}
	
	public float getProgress(){
		return progress;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(backTex, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.draw(barTex, getX() + offx, getY() + offy,  barTex.getRegionWidth(), barTex.getRegionHeight());
		super.draw(batch, parentAlpha);
	}
}
