package world.objects.explosive;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;

import engine.Loader;
import engine.SpecialForces;
import engine.Style;
import engine.utils.Motion;
import world.gameplay.Shoot;
import world.gameplay.WeaponType;
import world.objects.MapObject;

public class Grenade extends MapObject{
	private TextureRegion texture;
	private MapObject exception;
	
	private float dx, dy, len, l, speed, time, damage;
	private boolean exploded;
	
	public Grenade(Loader loader, float x, float y, float toX, float toY, MapObject exception){
		texture = loader.getWeaponTile(7);
		this.exception = exception;
		damage = loader.getWeaponConfig(WeaponType.Grenade).getDamage();
		
		float scl = 1f / (float) Math.sqrt(toX * toX + toY * toY);
		this.dx = toX * scl;
		this.dy = toY * scl;
		
		len = Math.abs(toX) + Math.abs(toY);
		
		setPosition(x, y);
		setSize(texture.getRegionWidth()*Style.CHARACTER_SIZECOF, texture.getRegionHeight()*Style.CHARACTER_SIZECOF);
		setOrigin(Align.center);
		setToCollide(false);
		
		speed = Style.GRENADE_SPEED;
	}
	
	@Override
	public void act(float delta) {
		time += delta;
		
		if (speed > 0.01f){
			updateColliding();
			moveBy(dx * speed, dy * speed);
			rotateBy(speed*10);
			l += getLen();
			if (l > len)
				speed *= 0.8f;
		}
		
		if (time > Style.GRENADE_DELAY || exploded)
			explode();
	}
	
	private float getLen(){
		float x = dx * speed;
		float y = dy * speed;
		return Math.abs(x) + Math.abs(y);
	}
	
	public void updateColliding(){
		Motion motion = getWorld().physics().canIMove(getX(), getY(), getWidth(), getHeight(), dx * speed, dy * speed, exception);
		if (!motion.x)
			dx *= -0.5f;
		if (!motion.y)
			dy *= -0.5f;
		if (motion.isCollided())
			SpecialForces.getInstance().sounds().bounce();
		
	}
	
	private void explode(){
		getWorld().explode(getX(), getY(), Style.EXPLOSION_RADIUS, damage);
		removeGlobal();
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
		exploded = true;
	}
	
}
