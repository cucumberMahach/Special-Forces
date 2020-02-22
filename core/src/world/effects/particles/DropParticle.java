package world.effects.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import engine.Style;
import engine.utils.Motion;
import stages.World;
import view.Actor;
import world.objects.MapObject;

public class DropParticle extends Actor{
	private World world;
	private TextureRegion texture;
	private Vector2 vel;
	private float rotationVel;
	private boolean toHide;
	private float time, gravity;
	private MapObject exception;
	
	public DropParticle(World world){
		this.world = world;
		vel = new Vector2();
	}
	
	@Override
	public void act(float delta) {
		if (toHide){
			getColor().a -= Style.PARTICLE_FADE_COF;
			if (getColor().a <= 0)
				stop();
			return;
		}
		
		if (time > Style.PARTICLE_LIFETIME)
			toHide = true;
		
		time += delta;
		
		if (vel.len2() < Style.PARTICLE_MIN_VELOCITY)
			return;
		vel.scl(gravity);
		updateCollision();
		moveBy(vel.x, vel.y);
		rotateBy((vel.x + vel.y)*rotationVel);
	}
	
	private void updateCollision(){
		Motion motion = world.physics().canIMove(getX(), getY(), getWidth(), getHeight(), vel.x, vel.y, exception);
		if (!motion.x)
			vel.x *= -0.5f;
		if (!motion.y)
			vel.y *= -0.5f;
	}
	
	public void set(TextureRegion texture, float x, float y, float velX, float velY, float gravity, float rotationVel, float sizeCof, MapObject exception){
		this.texture = texture;
		this.rotationVel = rotationVel;
		this.exception = exception;
		this.gravity = gravity;
		setSize(texture.getRegionWidth() * sizeCof, texture.getRegionHeight() * sizeCof);
		setOrigin(Align.center);
		setPosition(x - getOriginX(), y - getOriginY());
		vel.set(velX, velY);
		start();
	}
	
	private void start(){
		toHide = false;
		time = 0;
		setColor(1, 1, 1, 1);
	}
	
	private void stop(){
		remove();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setColor(getColor());
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		batch.setColor(1, 1, 1, 1);
	}
	
}
