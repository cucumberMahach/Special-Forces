package world.effects.particles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import view.Actor;

public class DefaultParticle extends Actor{
	
	private Animation<TextureRegion> animation;
	private TextureRegion texture;
	
	private float rotationVel, fadeCof, smoothCof, scaleVel, scaleCof, lifeTime, time;
	private Vector2 vel;
	
	public DefaultParticle(){
		vel = new Vector2();
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		rotateBy(rotationVel);
		
		if (time > lifeTime){
			getColor().a -= fadeCof;
			if (getColor().a <= 0)
				remove();
		}
		
		if (scaleCof != 0){
			setScale(getScaleX() + scaleVel);
			scaleVel *= scaleCof;
		}
		
		time += delta;
		
		if (vel.len2() < 0.1f)
			return;
		moveBy(vel.x, vel.y);
		vel.scl(smoothCof);
	}
	
	public void setParams(TextureRegion texture, float x, float y, float rotationVel, float fadeCof, float width){
		this.texture = texture;
		animation = null;
		setParams(x, y, rotationVel, fadeCof, width, texture.getRegionWidth(), texture.getRegionHeight());
	}
	
	public void setParams(Animation<TextureRegion> animation, float x, float y, float rotationVel, float fadeCof, float width){
		this.animation = animation;
		TextureRegion frame = animation.getKeyFrame(0);
		setParams(x, y, rotationVel, fadeCof, width, frame.getRegionWidth(), frame.getRegionHeight());
	}
	
	public void setParams(float x, float y, float rotationVel, float fadeCof, float width, float texWidth, float texHeight){
		this.rotationVel = rotationVel;
		this.fadeCof = fadeCof;
		float sizeRatio = texHeight / texWidth;
		setSize(width, sizeRatio * width);
		setOrigin(Align.center);
		setPosition(x - getOriginX(), y - getOriginY());
		setColor(1, 1, 1, 1);
		setScale(1);
		setRotation(0);
		time = 0;
		lifeTime = 0;
		scaleCof = 0;
		scaleVel = 0;
	}
	
	public void setVelocity(float x, float y, float smoothCof){
		this.smoothCof = smoothCof;
		vel.set(x, y);
	}
	
	public void setScaleAction(float defScale, float scaleVel, float scaleCof){
		this.scaleVel = scaleVel;
		this.scaleCof = scaleCof;
		setScale(defScale);
		
	}
	
	public void setLifeTime(float value){
		lifeTime = value;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		batch.draw(animation == null ? texture : animation.getKeyFrame(time), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.setColor(1, 1, 1, 1);
	}
}
