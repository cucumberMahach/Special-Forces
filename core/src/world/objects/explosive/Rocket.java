package world.objects.explosive;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import engine.Loader;
import engine.Style;
import engine.utils.Maths;
import engine.utils.Motion;
import world.gameplay.Shoot;
import world.gameplay.WeaponType;
import world.objects.MapObject;

public class Rocket extends MapObject{
	private TextureRegion texture;
	private MapObject exception;
	
	private float dx, dy, speed, smFreq, damage;
	private boolean exploded;
	
	public Rocket(Loader loader, float x, float y, float dirX, float dirY, MapObject exception){
		texture = loader.getParticle("bullet5");
		this.dx = dirX;
		this.dy = dirY;
		this.exception = exception;
		damage = loader.getWeaponConfig(WeaponType.Bazooka).getDamage();
		
		setSize(texture.getRegionWidth()*Style.CHARACTER_SIZECOF, texture.getRegionHeight()*Style.CHARACTER_SIZECOF);
		setPosition(x, y);
		setOrigin(getWidth()/2, 0);
		setRotation(Maths.radiansToDegrees((float) Math.atan2(dy, dx)));
		
		setCollideBox(getWidth(), getWidth());
		setToCollide(false);
		speed = Style.ROCKET_SPEED;
	}
	
	@Override
	public void act(float delta) {
		if (exploded)
			return;
		if (smFreq > Style.ROCKET_SMOKE_FREQ){
			getWorld().effects().addSmoke(getCenterX(), getCenterY(), 50);
			smFreq = 0;
		}
		smFreq += delta;
		updateColliding();
		moveBy(dx * speed, dy * speed);
	}
	
	public void updateColliding(){
		Motion motion = getWorld().physics().canIMove(getX(), getY(), getCollideWidth(), getCollideHeight(), dx * speed, dy * speed, exception);
		if (motion.isCollided())
			explode();
	}
	
	private void explode(){
		getWorld().explode(getX(), getY(), Style.EXPLOSION_RADIUS, damage);
		removeGlobal();
		exploded = true;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isInView())
			batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
	}

	@Override
	public void getDamage(float damage, boolean isExplosion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getShot(Shoot shoot) {
		explode();
	}
	
}
