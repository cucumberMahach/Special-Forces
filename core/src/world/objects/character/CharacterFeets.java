package world.objects.character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import view.Actor;

public class CharacterFeets extends Actor{
	
	private Animation<TextureRegion> animation;
	private float time;
	private boolean moving;
	private float speedPercent;
	
	public CharacterFeets(Loader loader){
		TextureRegion[] frames = loader.getAnimation("feets");
		animation = new Animation<TextureRegion>(0.03f, frames);
		setRotation(90);
	}
	
	public void resize(float widthCof, float heightCof){
		TextureRegion[] frames = animation.getKeyFrames();
		setSize(frames[0].getRegionWidth() * heightCof, frames[0].getRegionHeight() * heightCof);
		setOrigin(Align.center);
	}
	
	@Override
	public void act(float delta) {
		if (moving)
			time += delta * speedPercent;
		else
			time = 0.8f;
	}
	
	public void update(boolean isMoving, float speedPercent){
		moving = isMoving;
		this.speedPercent = speedPercent;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(animation.getKeyFrame(time, true), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}
}
